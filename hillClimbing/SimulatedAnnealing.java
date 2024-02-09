package hillClimbingAndSimulatedAnnealing.functionOptimization.hcAndSa;

import java.util.logging.Logger;
import hillClimbingAndSimulatedAnnealing.Randomizer;

import java.util.ArrayList;

public class SimulatedAnnealing {

    private static final Logger LOGGER = Logger.getLogger(SimulatedAnnealing.class.getName());
    private int totalParameters;
    private int numberOfIterations;
    private final double finalTemperature = 0.001;

    public SimulatedAnnealing(int totalParameters, int numberOfIterations){
        this.totalParameters = totalParameters;
        this.numberOfIterations = numberOfIterations;
    }

    public static double calculateStandardDeviation(double[] array,double mean,int length) {

        double standardDeviation = 0.0;
        for (double num : array) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return Math.sqrt(standardDeviation / length);
    }

    //hill climbing la final

    public double findMinimumAnnealingHybridised(EvaluationFunction function, double temperature, double coolingRate) {

        int numberOfParameters = getTotalParameters();

        String currentBitString = function.setInitialBitStringSolution();
        Solution currentSolution = new Solution(currentBitString, numberOfParameters);
        Solution improvedNeighbourhood;

        while (temperature > finalTemperature) {

            for(int i = 0; i < numberOfIterations; i++) {

                String randomNeighbour = getRandomNeighbour(currentSolution.getBigBitString());
                improvedNeighbourhood = new Solution(randomNeighbour, numberOfParameters);

                double deltaE = function.evaluateForNewSolution(improvedNeighbourhood) - function.evaluateForNewSolution(currentSolution);

                if (deltaE < 0 || Math.exp(-deltaE / temperature) > Randomizer.getDoubleFromZeroToOne()) {
                    currentSolution = improvedNeighbourhood;
                }

                System.out.println("currentSolution evaluates to " + function.evaluateForNewSolution(currentSolution));
            }

            temperature *= 1 - coolingRate;
         }

        LOGGER.info("Current result = " + function.evaluateForNewSolution(currentSolution));
        double newMin = findMinimum(function, 1,numberOfParameters, 1000, currentSolution.getBigBitString());
        System.out.println("New min after hillClimbing = " + newMin);

        return newMin;
    }

    public double findMinimum(EvaluationFunction function, int type, int numberOfParameters, int maxIterations, String bestBitString) {

        Solution best = new Solution(bestBitString,numberOfParameters);
        double bestEvaluationSoFar = function.evaluateForNewSolution(best);

        int i = 0;
        while (i < maxIterations) {
            String candidateVC = bestBitString;

            boolean local = true;
            while (local) {
                ArrayList<String> byteNeighbours = getHammingNeighbours(candidateVC);
                String improvedVN = getBestNeighbour(byteNeighbours, bestEvaluationSoFar, function, numberOfParameters, type);

                if (isBetter(function, maxIterations, improvedVN, candidateVC)) {
                    candidateVC = improvedVN;

                    Solution vcParameter = new Solution(improvedVN, numberOfParameters);
                    double candidateEvaluation = function.evaluateForNewSolution(vcParameter);

                    if (candidateEvaluation < bestEvaluationSoFar) {
                        bestEvaluationSoFar = candidateEvaluation;
                        best.setBigBitString(candidateVC);
                    }
                } else {
                    local = false;
                }
            }

            i++;
        }

        return bestEvaluationSoFar;
    }

    private String getBestNeighbour(ArrayList<String> neighbours, double bestSoFar,EvaluationFunction function,
                                    int parameters,int type){

        boolean first = true;

        int index = 0;
        String worstImprovement = " ";
        double bestEvaluation = bestSoFar; //evaluarea functiei
        double worstEvaluation = bestEvaluation;

        for(String neighbour : neighbours){

            Solution newSolution = new Solution(neighbour,parameters);
            double currentEvaluation = function.evaluateForNewSolution(newSolution);

            if(type == 1) {
                if (currentEvaluation < bestEvaluation) {
                    return neighbour;
                }
            } else if(type == 2){
                if(currentEvaluation < bestEvaluation){
                    bestEvaluation = currentEvaluation;
                    index = neighbours.indexOf(neighbour);
                }
            } else if(type == 3){
                if(currentEvaluation < bestEvaluation){
                    if(first) {
                        worstEvaluation = currentEvaluation;
                        worstImprovement = neighbour;
                        first = false;
                    } else  {
                        worstEvaluation = Math.max(worstEvaluation,currentEvaluation);
                        worstImprovement = neighbour;
                    }
                }
            } else {
                System.out.println("Unknown type");
                return " ";
            }
        }

        if(type == 3){
            return worstImprovement;
        }
        return neighbours.get(index);
    }

    public static String getRandomNeighbour(String bitString){

        int bitStringSize = bitString.length();
        int numberGenerated = Randomizer.intBetween(0,bitStringSize -1);

        return getHammingNeighbours(bitString).get(numberGenerated);
    }

    private static ArrayList<String> getHammingNeighbours(String bitString) {
        int numberOfBits = bitString.length();

        ArrayList<String> neighbours = new ArrayList<>();

        for (int i = 0; i < numberOfBits; i++) {
            char[] bitArray = bitString.toCharArray();
            bitArray[i] = (bitArray[i] == '0') ? '1' : '0';
            neighbours.add(new String(bitArray));
        }
        return neighbours;
    }


    private static boolean isBetter(EvaluationFunction f,int stopAtN, String binaryString1, String binaryString2){

        if(binaryString1.isEmpty() || binaryString2.isEmpty()){
            return false;
        }

        Solution p1 = new Solution(binaryString1,stopAtN);
        Solution p2 = new Solution(binaryString2,stopAtN);

        return f.evaluateForNewSolution(p1) < f.evaluateForNewSolution(p2);
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
