package geneticAlgorithms;

import greedy.KnapsackItem;

import java.util.*;

public class GeneticAlgorithmEngine {

    private int populationSize;
    private double crossoverRate;
    private double mutationRate;
    private List<CandidateSolution> currentGeneration;
    private static final int maxGenerationsWitoutChange = 3;


    public GeneticAlgorithmEngine(int populationSize,double crossoverPercentage,double mutationPercentage){
        this.populationSize = populationSize;
        this.crossoverRate = crossoverPercentage / 100;
        this.mutationRate = mutationPercentage /100;

    }

    public ArrayList<KnapsackItem> findOptimalItems(ArrayList<KnapsackItem> items,int capacity){

        currentGeneration = new ArrayList<>(populationSize);

        for(int i = 0; i < populationSize; i++){
            currentGeneration.add(new CandidateSolution(items,capacity));
        }

        int generationNumber = 1;
        CandidateSolution bestSolution = null;

        while(true){

            int bestFitnessScoreAllTime = Integer.MIN_VALUE;
            int bestFitnessScoreThisGeneration = Integer.MIN_VALUE;
            int bestSolutionGenerationNumber = 1;

            CandidateSolution bestSolutionThisGeneration = null;

            int totalFitnessThisGeneration = 1;
            for(CandidateSolution candidate : currentGeneration){
                candidate.repair();

                int fitness = candidate.calculateFitness();
                totalFitnessThisGeneration += fitness;

                if(fitness > bestFitnessScoreThisGeneration){
                    bestFitnessScoreThisGeneration = fitness;
                    bestSolutionThisGeneration = candidate;
                }
            }

            if(bestFitnessScoreThisGeneration > bestFitnessScoreAllTime){
                bestFitnessScoreAllTime = bestFitnessScoreThisGeneration;
                bestSolution = bestSolutionThisGeneration;
                bestSolutionGenerationNumber = generationNumber;
            } else {
                if( (generationNumber - bestSolutionGenerationNumber) > maxGenerationsWitoutChange){
                    break;
                }
            }

            List<CandidateSolution> nextGeneration = new ArrayList<>();

            while (nextGeneration.size() < populationSize) {
                CandidateSolution parent1 = selectCandidate(totalFitnessThisGeneration);
                CandidateSolution parent2 = selectCandidate(totalFitnessThisGeneration);


                List<CandidateSolution> candidateSolutions = crossoverParents(parent1, parent2);

                if(candidateSolutions.size() != 2){
                    continue;
                }

                CandidateSolution child1 = candidateSolutions.get(0);
                CandidateSolution child2 = candidateSolutions.get(1);

                child1.addPossibleMutation(mutationRate);
                child2.addPossibleMutation(mutationRate);

                nextGeneration.add(child1);
                nextGeneration.add(child2);
            }

            currentGeneration = nextGeneration;
            generationNumber++;
        }

        return bestSolution.getSelectedItems();
    }



    private List<CandidateSolution> crossoverParents(CandidateSolution parent1,CandidateSolution parent2){

        List<CandidateSolution> candidateList = new ArrayList<>();
        CandidateSolution child1 = parent1;
        CandidateSolution child2 = parent2;

        if(Randomizer.getDoubleFromZeroToOne() < crossoverRate){

            int numItems = parent1.getItems().size();
            int crossoverPoint = Randomizer.intLessThan(numItems);

            for(int i = 0; i < crossoverPoint; i++){
                child1.setIndividualIsSelected(i,parent1.getIsSelected().get(i));
                child2.setIndividualIsSelected(i,parent2.getIsSelected().get(i));
            }

            for(int i = crossoverPoint; i < numItems; i++){
                child1.setIndividualIsSelected(i,parent2.getIsSelected().get(i));
                child2.setIndividualIsSelected(i,parent1.getIsSelected().get(i));
            }

            candidateList.add(child1);
            candidateList.add(child2);

            return candidateList;
        }

        return new ArrayList<>();
    }


    private CandidateSolution selectCandidate(int totalFitnessThisGeneration){ //roulette wheel selection

        for(int i = 0; i < populationSize; i++){

            if(totalFitnessThisGeneration < 0){
                break;
            }

            int randomValue = Randomizer.intLessThan(totalFitnessThisGeneration);

            if(randomValue <= 0){
                return currentGeneration.get(i);
            }

            randomValue -= currentGeneration.get(i).calculateFitness();

            if(randomValue <= 0){
                return currentGeneration.get(i);
            }
        }

        return currentGeneration.get(populationSize -1);
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public double getCrossoverRate() {
        return crossoverRate;
    }

    public void setCrossoverRate(double crossoverRate) {
        this.crossoverRate = crossoverRate;
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }
}
