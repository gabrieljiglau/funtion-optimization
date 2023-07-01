package geneticAlgorithms;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GeneticMinima {

    public static void main(String[] args) {
        GeneticMinima geneticAlgorithm = new GeneticMinima(400,0.12,0.13);

        int numberOfParameters = 100;
        FunctionDeJongG deJongG = new FunctionDeJongG(numberOfParameters);
        FunctionSchewefelG schewefelG = new FunctionSchewefelG(numberOfParameters);
        FunctionMichalewiczG michalewiczG = new FunctionMichalewiczG(numberOfParameters);
        FunctionRastriginG rastriginG = new FunctionRastriginG(numberOfParameters);

        System.out.println(geneticAlgorithm.findMinimum(deJongG,numberOfParameters));
    }

    private int populationSize;
    private double crossoverRate;
    private double mutationRate;
    private FunctionDeJongG currentGeneration;
    private static final int stop = 13;

    public GeneticMinima(int populationSize,double crossoverRate,double mutationRate){
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
    }

    public double findMinimum(EvaluationG function,int numberOfParameters){

        currentGeneration = new FunctionDeJongG(numberOfParameters);
        //initial random generation
        for(int i = 0 ; i < populationSize; i++){
            String bestCoefficient = function.setInitialSolution();
            Solution best = new Solution(Double.parseDouble(bestCoefficient),numberOfParameters);
            currentGeneration.setParameter(best,i);
            System.out.println("evaluation " + i + " is " + function.evaluateForNewSolution(best));
        }

        int generationNumber = 1;
        String bestCoefficient = function.setInitialSolution();
        Solution bestSolution = new Solution(Double.parseDouble(bestCoefficient),numberOfParameters);
        double bestFitnessScoreAllTime = Double.POSITIVE_INFINITY;

        while(true) {

            double bestFitnessScoreThisGeneration = Double.POSITIVE_INFINITY;
            int bestSolutionGenerationNumber = 0;

            Solution bestSolutionThisGeneration = null;

            //fitness testing
            double totalFitness = 0;
            for (Solution candidate : currentGeneration.getFunctionParameters()) {
                double fitness = function.evaluateForNewSolution(candidate);
                totalFitness += fitness;

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

            FunctionDeJongG nextGeneration = new FunctionDeJongG(numberOfParameters);

            while(nextGeneration.getFunctionParameters().size() < populationSize){
                //selection of parents
                Solution parent1 = selectCandidate(bestFitnessScoreThisGeneration); //inainte era totalFitness
                Solution parent2 = selectCandidate(bestFitnessScoreThisGeneration);

                //crossover
                List<Solution> candidateList = crossoverParents(parent1,parent2,function,numberOfParameters);
                Solution child1 = candidateList.get(0);
                Solution child2 = candidateList.get(1);

                //mutation
                function.addPossibleMutation(child1,mutationRate);
                function.addPossibleMutation(child2,mutationRate);

                nextGeneration.setParameter(child1,Randomizer.intBetween(0,numberOfParameters -1));
                nextGeneration.setParameter(child2,Randomizer.intBetween(0,numberOfParameters -1));
            }

            //replacement
            currentGeneration = nextGeneration;
            generationNumber++;
            System.out.println("Best solution so far " + function.evaluateForNewSolution(bestSolution));

        }

        return function.evaluateForNewSolution(bestSolution);
    }

    private List<Solution> crossoverParents(Solution parent1,Solution parent2,EvaluationG function,int n){

        List<Solution> parentList = new ArrayList<>();
        Solution child1 = parent1;
        Solution child2 = parent2;

        if(Randomizer.getDoubleFromZeroToOne() < crossoverRate){

            int numParameters = parent1.getStopAtN();
            int crossoverPoint = Randomizer.intLessThan(numParameters);

            Double child1Coefficient = child1.getCoefficient(); //astea erau inainte de tip Double
            Double child2Coefficient = child2.getCoefficient();

            ArrayList<Byte> byteNeighbours1 = getHammingNeighbours(child1Coefficient.byteValue());
            ArrayList<Byte> byteNeighbours2 = getHammingNeighbours(child2Coefficient.byteValue());

            ArrayList<BigDecimal> neighbours1 = convertToBigDecimalList(byteNeighbours1);
            ArrayList<BigDecimal> neighbours2 = convertToBigDecimalList(byteNeighbours2);

            for(int i = 0; i < crossoverPoint; i++){
                neighbours1.add(i,neighbours1.get(i));
                neighbours2.add(i,neighbours2.get(i));
                //child1.setIndividualIsSelected(i,parent1.getIsSelected().get(i));
                //child2.setIndividualIsSelected(i,parent2.getIsSelected().get(i));
            }

            for(int i = crossoverPoint; i < numParameters; i++){
                neighbours1.add(i,neighbours2.get(i));
                neighbours2.add(i,neighbours1.get(i));
                //child1.setIndividualIsSelected(i,parent2.getIsSelected().get(i));
                //child2.setIndividualIsSelected(i,parent1.getIsSelected().get(i));
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

        for (Solution individual : currentGeneration.getFunctionParameters()) {
            double fitness = currentGeneration.evaluateForNewSolution(individual);
            double probability = fitness / totalFitnessThisGeneration;
            selectionProbabilities.add(probability);
        }

        double randomValue = Randomizer.getDoubleFromZeroToOne();
        double cumulativeProbability = 0;
        int selectedIndividualIndex = 0;

        for (int i = 0; i < currentGeneration.getFunctionParameters().size(); i++) {
            cumulativeProbability += selectionProbabilities.get(i);

            if (cumulativeProbability >= randomValue) {
                selectedIndividualIndex = i;
                break;
            }
        }

        return currentGeneration.getFunctionParameters().get(selectedIndividualIndex);
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

    public EvaluationG getCurrentGeneration() {
        return currentGeneration;
    }

    public void setCurrentGeneration(FunctionDeJongG currentGeneration) {
        this.currentGeneration = currentGeneration;
    }

    public static int getStop() {
        return stop;
    }
}
