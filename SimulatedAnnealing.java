package geneticAlgorithms;

import java.math.BigDecimal;
import java.util.ArrayList;

public class SimulatedAnnealing {

    public static void main(String[] args) {

        SimulatedAnnealing annealing = new SimulatedAnnealing(10,100);

        int n = annealing.getTotalParameters();
        FunctionDeJong deJong = new FunctionDeJong(n);
        FunctionSchewefel schewefel = new FunctionSchewefel(n);
        FunctionRastrigin rastrigin = new FunctionRastrigin(n);
        FunctionMichalewicz michalewicz = new FunctionMichalewicz(n);

        long startTime = System.nanoTime();

        System.out.println("(MAIN) Minimum for function is " + annealing.findMinimumAnnealing(michalewicz));

        long finishTime = System.nanoTime();
        long duration = (finishTime - startTime) / 1000000;

        System.out.println("********************************");
        System.out.println("Total duration for getting the minValue of the function is " + duration + " miliseconds");
    }

    private int totalParameters;
    private int numberOfIterations;
    private final double finalTemperature = 0.3;

    public SimulatedAnnealing(int totalParameters,int numberOfIterations){
        this.totalParameters = totalParameters;
        this.numberOfIterations = numberOfIterations;
    }

    public String findMinimumAnnealing(EvaluationFunction function) {
        int iterations = 0;
        int numberOfParameters = getTotalParameters();
        double temperature = 10000;
        double coolingRate = 0.003;


        String coefficient = function.setInitialSolution();
        Solution currentSolution = new Solution(parseStringToDouble(coefficient),numberOfParameters);
        BigDecimal initial = new BigDecimal(coefficient);

        Solution bestSolution = currentSolution;

        double minimum = function.evaluateForNewSolution(currentSolution);
        System.out.println("currentSolution is " + currentSolution.getCoefficient());
        System.out.println("currentSolution evaluates to " + minimum);

        while (temperature > finalTemperature && iterations < getNumberOfIterations()) {

            ArrayList<Byte> byteNeighbours = getHammingNeighbours(initial.toBigInteger().byteValue());
            ArrayList<BigDecimal> neighbours = convertToBigDecimalList(byteNeighbours);

            BigDecimal randomNeighbour;

            randomNeighbour = getBestNeighbour(neighbours, function.evaluateForNewSolution(bestSolution), function, numberOfParameters);
            //randomNeighbour = getRandomNeighbour(neighbours);

            Solution newSolution = new Solution(Double.parseDouble(randomNeighbour.toString()), numberOfParameters);

            double currentCost = function.evaluateForNewSolution(currentSolution);
            double newCost = function.evaluateForNewSolution(newSolution);

            if ( /*isBetter(function,numberOfParameters,randomNeighbour,initial)*/ newCost < currentCost
                    || Randomizer.getDoubleFromZeroToOne() < acceptanceProbability(currentCost, newCost, temperature)) {
                initial = randomNeighbour;
            }

            BigDecimal first = initial;
            String initalizedBest = String.valueOf(bestSolution.getCoefficient());
            BigDecimal compareTo = new BigDecimal(initalizedBest);

            if(isBetter(function,numberOfParameters,first,compareTo)){
                double vcDouble = Double.parseDouble(String.valueOf(initial));
                bestSolution.setCoefficient(vcDouble);
            }

            iterations++;
            temperature *= 1 - coolingRate;
            System.out.println("current solution : " + function.evaluateForNewSolution(bestSolution));
         }

       return String.valueOf(function.evaluateForNewSolution(bestSolution));
    }

    private static boolean isBetter(EvaluationFunction f,int numberOfParameters, BigDecimal i1, BigDecimal i2){

        if(String.valueOf(i1) == null || String.valueOf(i2) == null){
            return false;
        }

        double d1 = i1.doubleValue();
        double d2 = i2.doubleValue();

        Solution p1 = new Solution(d1,numberOfParameters);
        Solution p2 = new Solution(d2,numberOfParameters);

        return f.evaluateForNewSolution(p1) < f.evaluateForNewSolution(p2);
    }

    private static double parseStringToDouble(String value) {
        return value == null || value.isEmpty() ? Double.NaN : Double.parseDouble(value);
    }

    private BigDecimal getRandomNeighbour(ArrayList<BigDecimal> neighbours){

        int index = Randomizer.intBetween(0,neighbours.size());

        return neighbours.get(index);
    }


    private BigDecimal getBestNeighbour(ArrayList<BigDecimal> neighbours, double currentEvaluation,EvaluationFunction function,
                                        int parameters){

        int index = 0;
        double bestEvaluation = currentEvaluation;
        for (BigDecimal neighbour : neighbours) {

            String neighbourToString = String.valueOf(neighbour);
            Solution newSolution = new Solution(Double.parseDouble(neighbourToString),parameters);
            double actualNumber = function.evaluateForNewSolution(newSolution);

            if (actualNumber < bestEvaluation) {
                bestEvaluation = actualNumber;
                index = neighbours.indexOf(neighbour);
            }

        }

        return new BigDecimal(String.valueOf(neighbours.get(index)));
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


    private double acceptanceProbability(double currentCost,double newCost,double temperature) {

        if (newCost < currentCost) {
            return 1.0;
        } else {
            return Math.exp((currentCost - newCost) / temperature);
        }
    }

    public int getTotalParameters() {
        return totalParameters;
    }

    public void setTotalParameters(int totalParameters) {
        this.totalParameters = totalParameters;
    }

    public int getNumberOfIterations() {
        return numberOfIterations;
    }

    public void setNumberOfIterations(int numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
    }

    public double getFinalTemperature() {
        return finalTemperature;
    }
}
