package onlyGeneticAlgorithms;

import java.util.*;

public class GeneticMinima {

    private final int populationSize;
    private final double crossoverRate;
    private double mutationRate;
    private FunctionG currentGeneration;
    private final int numberOfElite;
    private static final int STOP = 160;

    public GeneticMinima(int populationSize, double crossoverRate, double mutationRate, FunctionManager f, int index
                        ,int numberOfParameters,int precision){
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.currentGeneration = f.getFunctions().get(index).createNextGeneration(numberOfParameters,precision,mutationRate);
        this.numberOfElite = (int) (populationSize * 0.085);
    }

    public double findMinimum(FunctionManager functions,int functionIndex,int numberOfParameters){

        FunctionG function = functions.getFunctions().get(functionIndex);

        double lowerDomain = function.getLowerDomain();
        double upperDomain = function.getUpperDomain();
        int precision = function.getPrecision();

        for(int i = 0 ; i < populationSize; i++){
            String randomCoefficient = function.setInitialBitStringSolution(lowerDomain,upperDomain,precision,numberOfParameters);
            Solution randomSolution = new Solution(randomCoefficient,numberOfParameters,mutationRate);
            currentGeneration.setSolution(randomSolution,i);
        }

        int generationNumber = 1;
        double bestFitnessScoreAllTime = Double.POSITIVE_INFINITY;

        while(true) {
            int MAX_GENERATIONS = 2050;
            if(generationNumber > MAX_GENERATIONS/2.02){
                mutationRate = 0.001;
            }

            double bestFitnessScoreThisGeneration = Double.POSITIVE_INFINITY;

            for (Solution candidate : currentGeneration.getSolutionList()) {
               double fitness = function.evaluateForNewSolution(candidate);
               candidate.setFitnessScore(fitness);

                if(fitness < bestFitnessScoreThisGeneration){
                    bestFitnessScoreThisGeneration = fitness;
                }
            }

            if(bestFitnessScoreThisGeneration < bestFitnessScoreAllTime){
                bestFitnessScoreAllTime = bestFitnessScoreThisGeneration;
            }

            if (generationNumber == MAX_GENERATIONS) {
                break;
            }

            FunctionG nextGeneration = function.createNextGeneration(numberOfParameters,precision,mutationRate);

            List<Solution> eliteIndividuals;
            if(generationNumber < MAX_GENERATIONS / 1.02){
                eliteIndividuals = selectElite(currentGeneration.getSolutionList(), numberOfElite);
                List<Solution> eliteGeneration = new ArrayList<>(eliteIndividuals);
                int listIndex = 0;
                for (Solution eliteIndividual : eliteGeneration) {
                    nextGeneration.setSolution(eliteIndividual, listIndex);
                    listIndex++;
                }
            }

            int index = 0;
            while(nextGeneration.getSolutionList().size() < populationSize){

                List<Solution> parentList = selectCandidates();

                Solution child1;
                Solution child2;

                if(Randomizer.getDoubleFromZeroToOne() < crossoverRate) {
                    List<Solution> candidateList = crossoverParents(parentList.get(0), parentList.get(1), numberOfParameters);
                     child1 = candidateList.get(0);
                     child2 = candidateList.get(1);
                } else {
                     child1 = parentList.get(0);
                     child2 = parentList.get(1);
                }

                String newBitString1 = function.addPossibleMutation(child1,mutationRate);
                String newBitString2 = function.addPossibleMutation(child2,mutationRate);

                child1.setBigBitString(newBitString1);
                child2.setBigBitString(newBitString2);

                nextGeneration.setSolution(child1,index);
                index++;
                nextGeneration.setSolution(child2,index);
                index++;
            }

            currentGeneration = nextGeneration;
            System.out.println("bestEvaluation " + bestFitnessScoreAllTime + " in generation " + generationNumber);
            generationNumber++;

        }

        return bestFitnessScoreAllTime;
    }

    private List<Solution> crossoverParents(Solution parent1, Solution parent2, int n) {

        List<Solution> parentList = new ArrayList<>();

        if (Randomizer.getDoubleFromZeroToOne() < crossoverRate) {
            int crossoverPoint = Randomizer.intLessThan(parent1.getBigBitString().length());

            String bitString1 = parent1.getBigBitString();
            String bitString2 = parent2.getBigBitString();

            StringBuilder sb1 = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();

            sb1.append(bitString1, 0, crossoverPoint);
            sb1.append(bitString2.substring(crossoverPoint));


            sb2.append(bitString2, 0, crossoverPoint);
            sb2.append(bitString1.substring(crossoverPoint));

            Solution child1 = new Solution(sb1.toString(), n, mutationRate);
            Solution child2 = new Solution(sb2.toString(), n, mutationRate);

            parentList.add(child1);
            parentList.add(child2);
        } else {
            parentList.add(parent1);
            parentList.add(parent2);
        }

        return parentList;
    }

    private List<Solution> selectElite(List<Solution> generation, int numberOfElite) {
        //generation.sort(Comparator.comparingDouble(currentGeneration::evaluateForNewSolution).reversed());
        generation.sort(Comparator.comparingDouble(Solution::getFitnessScore).reversed());
        return generation.subList(0, numberOfElite);
    }

    //rank based selection

    private List<Solution> selectCandidates() {
        int populationSize = currentGeneration.getSolutionList().size();

        List<Solution> sortedPopulation = new ArrayList<>(currentGeneration.getSolutionList());
        sortedPopulation.sort(Comparator.comparingDouble(Solution::getFitnessScore));

        Map<Solution, Integer> rankMap = new HashMap<>();
        for (int i = 0; i < populationSize; i++) {
            rankMap.put(sortedPopulation.get(i), i + 1); // Adding 1 to avoid rank 0
        }

        List<Double> selectionProbabilities = new ArrayList<>();
        for (Solution individual : currentGeneration.getSolutionList()) {
            int rank = rankMap.get(individual);
            double probability = 2.0 * (populationSize - rank + 1) / (populationSize * (populationSize + 1));
            selectionProbabilities.add(probability);
        }

        List<Solution> selectedCandidates = new ArrayList<>();
        for (int candidateIndex = 0; candidateIndex < 2; candidateIndex++) {
            double randomValue = Randomizer.getDoubleFromZeroToOne();
            double cumulativeProbability = 0;
            int selectedIndividualIndex = 0;

            for (int i = 0; i < populationSize; i++) {
                cumulativeProbability += selectionProbabilities.get(i);

                if (cumulativeProbability >= randomValue) {
                    selectedIndividualIndex = i;
                    break;
                }
            }

            selectedCandidates.add(currentGeneration.getSolutionList().get(selectedIndividualIndex));
        }

        return selectedCandidates;
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    public static int getStop() {
        return STOP;
    }
}
