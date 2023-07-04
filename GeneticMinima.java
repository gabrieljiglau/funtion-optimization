package geneticAlgorithms;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GeneticMinima {

    private int populationSize;
    private double crossoverRate;
    private double mutationRate;
    private FunctionG currentGeneration;
    private static final int stop = 9;

    public GeneticMinima(int populationSize,double crossoverRate,double mutationRate,FunctionManager f,int index){
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.currentGeneration = f.getFunctions().get(index).createNextGeneration();
    }

    public double findMinimum(FunctionManager functions,int functionIndex,int numberOfParameters){

        FunctionG function = functions.getFunctions().get(functionIndex);

        for(int i = 0 ; i < populationSize; i++){
            String bestCoefficient = function.setInitialSolution();
            Solution best = new Solution(Double.parseDouble(bestCoefficient),numberOfParameters);
            currentGeneration.setParameter(best,i);
            System.out.println("evaluation " + i + " is " + function.evaluateForNewSolution(best));
        }

        int generationNumber = 1;
        Solution bestSolution = null;
        double bestFitnessScoreAllTime = Double.POSITIVE_INFINITY;

        while(true) {

            double bestFitnessScoreThisGeneration = Double.POSITIVE_INFINITY;
            int bestSolutionGenerationNumber = 0;
            Solution bestSolutionThisGeneration = null;

            double totalFitnessThisGeneration = 0;

            for (Solution candidate : currentGeneration.getParameters()) {
               double fitness = function.evaluateForNewSolution(candidate);
               totalFitnessThisGeneration += fitness;

                if(fitness < bestFitnessScoreThisGeneration){
                    bestFitnessScoreThisGeneration = fitness;
                    bestSolutionThisGeneration = candidate;
                    bestSolution = candidate;
                }
            }

            if(bestFitnessScoreThisGeneration < bestFitnessScoreAllTime){
                bestFitnessScoreAllTime = bestFitnessScoreThisGeneration;
                bestSolution = bestSolutionThisGeneration;
            } else {
                if( (generationNumber - bestSolutionGenerationNumber) > stop){
                    break;
                }
            }

            FunctionG nextGeneration = function.createNextGeneration();

            int index = 0;
            while(nextGeneration.getParameters().size() < populationSize){

                Solution parent1 = selectCandidate(totalFitnessThisGeneration);
                Solution parent2 = selectCandidate(totalFitnessThisGeneration);

                List<Solution> candidateList = crossoverParents(parent1,parent2,function,numberOfParameters);
                Solution child1 = candidateList.get(0);
                Solution child2 = candidateList.get(1);

                function.addPossibleMutation(child1,mutationRate);
                function.addPossibleMutation(child2,mutationRate);

                nextGeneration.setParameter(child1,index);
                index++;
                nextGeneration.setParameter(child2,index);
                index++;
            }

            currentGeneration = nextGeneration;
            generationNumber++;
            System.out.println("Best fitnessScoreThisGeneration " + bestFitnessScoreThisGeneration);

        }
        return bestFitnessScoreAllTime;
    }

    private List<Solution> crossoverParents(Solution parent1,Solution parent2,FunctionG function,int n){

        List<Solution> parentList = new ArrayList<>();
        Solution child1 = parent1;
        Solution child2 = parent2;

        if(Randomizer.getDoubleFromZeroToOne() < crossoverRate){

            int numParameters = parent1.getStopAtN();
            int crossoverPoint = Randomizer.intLessThan(numParameters);

            double child1Coefficient = child1.getCoefficient();
            double child2Coefficient = child2.getCoefficient();

            ArrayList<Byte> byteNeighbours1 = getHammingNeighbours((byte) child1Coefficient);
            ArrayList<Byte> byteNeighbours2 = getHammingNeighbours((byte) child2Coefficient);

            ArrayList<BigDecimal> neighbours1 = convertToBigDecimalList(byteNeighbours1);
            ArrayList<BigDecimal> neighbours2 = convertToBigDecimalList(byteNeighbours2);

            for(int i = 0; i < crossoverPoint; i++){
                neighbours1.add(i,neighbours1.get(i));
                neighbours2.add(i,neighbours2.get(i));
            }

            for(int i = crossoverPoint; i < numParameters; i++){
                neighbours1.add(i,neighbours2.get(i));
                neighbours2.add(i,neighbours1.get(i));
            }
        } else {
            String coefficient1 = function.setInitialSolution();
            child1 = new Solution(Double.parseDouble(coefficient1),n);

            String coefficient2 = function.setInitialSolution();
            child2 = new Solution(Double.parseDouble(coefficient2),n);
        }

        parentList.add(child1);
        parentList.add(child2);

        return parentList;
    }

    public static ArrayList<Byte> getHammingNeighbours(Byte input){
        ArrayList<Byte> neighbours = new ArrayList<>();
        neighbours.add(input);
        byte value;
        byte mask = 1;


        for (int i = 0; i < 8; i++) {
            value = (byte) (input ^mask);
            neighbours.add(value);
            mask = (byte) (mask << 1);

        }
        return neighbours;
    }

    private ArrayList<BigDecimal> convertToBigDecimalList(ArrayList<Byte> byteList){

        ArrayList<BigDecimal> convertedList = new ArrayList<>();

        for(Byte b : byteList){
            String byteToString = String.valueOf(b);
            BigDecimal converted = new BigDecimal(byteToString);
            convertedList.add(converted);
        }

        return convertedList;
    }

    private Solution selectCandidate(double totalFitnessThisGeneration) {
        List<Double> selectionProbabilities = new ArrayList<>();

        for (Solution individual : currentGeneration.getParameters()) {
            double fitness = currentGeneration.evaluateForNewSolution(individual);
            double probability = fitness / totalFitnessThisGeneration;
            selectionProbabilities.add(probability);
        }

        double randomValue = Randomizer.getDoubleFromZeroToOne();
        double cumulativeProbability = 0;
        int selectedIndividualIndex = 0;

        for (int i = 0; i < currentGeneration.getParameters().size(); i++) {
            cumulativeProbability += selectionProbabilities.get(i);

            if (cumulativeProbability >= randomValue) {
                selectedIndividualIndex = i;
                break;
            }
        }

        return currentGeneration.getParameters().get(selectedIndividualIndex);
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

    public FunctionG getCurrentGeneration() {
        return currentGeneration;
    }

    public static int getStop() {
        return stop;
    }
}
