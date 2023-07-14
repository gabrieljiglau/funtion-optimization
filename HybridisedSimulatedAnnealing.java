package geneticAlgorithms;

import java.math.BigDecimal;
import java.util.ArrayList;

public class HybridisedSimulatedAnnealing {

    public static void main(String[] args) {

        HybridisedSimulatedAnnealing annealing = new HybridisedSimulatedAnnealing(10,50);

        int n = annealing.getTotalParameters();
        FunctionDeJong deJong = new FunctionDeJong(n);
        FunctionSchewefel schewefel = new FunctionSchewefel(n);
        FunctionRastrigin rastrigin = new FunctionRastrigin(n);
        FunctionMichalewicz michalewicz = new FunctionMichalewicz(n);

        long startTime = System.nanoTime();

        System.out.println("(MAIN) Minimum for function is " + annealing.findMinimumAnnealingHybridised(michalewicz,20));

        long finishTime = System.nanoTime();
        long duration = (finishTime - startTime) / 1000000;

        System.out.println("********************************8");
        System.out.println("Total duration for getting the minValue of the function is " + duration + " miliseconds");
    }

    private int totalParameters;
    private int numberOfIterations;
    private final double finalTemperature = 0.001;

    public HybridisedSimulatedAnnealing(int totalParameters, int numberOfIterations){
        this.totalParameters = totalParameters;
        this.numberOfIterations = numberOfIterations;
    }

    public String findMinimumAnnealingHybridised(EvaluationFunction function, int hillClimbingIterations) {

        int mainIterations = 0;
        int numberOfParameters = getTotalParameters();
        double temperature = 10000;
        double coolingRate = 0.003;

        String coefficient = function.setInitialSolution();
        Solution currentSolution;
        Solution bestSolution = new Solution(parseStringToDouble(function.setInitialSolution()),numberOfParameters);

        while (temperature > finalTemperature && mainIterations < getNumberOfIterations()) {

            for(int i = 0; i < hillClimbingIterations; i++) {
                currentSolution = hillClimbing(bestSolution, function);

                BigDecimal currentBigDecimal = BigDecimal.valueOf(currentSolution.getCoefficient());
                BigDecimal bestBigDecimal = BigDecimal.valueOf(bestSolution.getCoefficient());
                if (isBetter(function, numberOfParameters, currentBigDecimal, bestBigDecimal)) {
                    bestSolution = currentSolution;
                } else if(acceptanceProbability(function.evaluateForNewSolution(currentSolution),
                        function.evaluateForNewSolution(bestSolution),temperature) > Randomizer.getDoubleFromZeroToOne()){
                    bestSolution = currentSolution;
                }

                System.out.println("bestSolution evaluates to " + function.evaluateForNewSolution(bestSolution));
            }

            temperature *= 1 - coolingRate;
            mainIterations++;
         }

        System.out.println("Solution in " + bestSolution.getCoefficient());
       return String.valueOf(function.evaluateForNewSolution(bestSolution));
    }

    private Solution hillClimbing(Solution initialSolution,EvaluationFunction function){

        int limit = numberOfIterations;
        double bestEvaluationSoFar = function.evaluateForNewSolution(initialSolution);

        int i = 0;
        while(i < limit){
            String candidateVC = function.setInitialSolution();
            BigDecimal vc = new BigDecimal(candidateVC);

            boolean local = true;
            while (local) {

                ArrayList<Byte> byteNeighbours = getHammingNeighbours(vc.toBigInteger().byteValue());
                ArrayList<BigDecimal> neighbours = convertToBigDecimalList(byteNeighbours);

                BigDecimal improveNeighbourhood;

                improveNeighbourhood = getBestNeighbour(neighbours, bestEvaluationSoFar,function,limit);

                if(isBetter(function,limit,improveNeighbourhood,vc)){
                    vc = improveNeighbourhood;
                } else {
                    local = false;
                }
            }

            i++;
            BigDecimal first = vc;
            String initializedBest = String.valueOf(initialSolution.getCoefficient());
            BigDecimal compareTo = new BigDecimal(initializedBest);

            if(isBetter(function,limit,first,compareTo)){
                double vcDouble = Double.parseDouble(String.valueOf(vc));
                initialSolution.setCoefficient(vcDouble);
            }
        }

        return initialSolution;
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
