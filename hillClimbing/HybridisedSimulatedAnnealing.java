package hillClimbingAndSimulatedAnnealing.functionOptimization.hcAndSa;

import hillClimbingAndSimulatedAnnealing.Randomizer;

import java.util.ArrayList;

public class HybridisedSimulatedAnnealing {

    private int totalParameters;
    private int numberOfIterations;
    private final double finalTemperature = 0.00001;

    public HybridisedSimulatedAnnealing(int totalParameters, int numberOfIterations){
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

    public double findMinimumAnnealingHybridised(EvaluationFunction function) {

        int numberOfParameters = getTotalParameters();
        double temperature = 100; //10000
        double coolingRate = 0.03; // 0.003

        String currentBitString = function.setInitialBitStringSolution();
        Solution currentSolution = new Solution(currentBitString, numberOfParameters);
        Solution improvedNeighbourhood;

        while (temperature > finalTemperature) {

            for(int i = 0; i < numberOfIterations; i++) {

                int countNoImprovement = 0;
                improvedNeighbourhood = hillClimbing(function,currentSolution,numberOfParameters);

                 currentBitString = currentSolution.getBigBitString();
                 String bestBitString = improvedNeighbourhood.getBigBitString();
                 if (isBetter(function, numberOfParameters, currentBitString, bestBitString)) {
                    currentSolution = improvedNeighbourhood;
                } else if(acceptanceProbability(function.evaluateForNewSolution(currentSolution),
                        function.evaluateForNewSolution(currentSolution),temperature) > Randomizer.getDoubleFromZeroToOne()){
                    currentSolution = improvedNeighbourhood;
                    countNoImprovement++;
                }

                if(countNoImprovement > 5){
                    break;
                }

                //System.out.println("currentSolution evaluates to " + function.evaluateForNewSolution(currentSolution));
            }

            temperature *= 1 - coolingRate;
         }

       return function.evaluateForNewSolution(currentSolution);
    }

    //convertit asta la cel de sus
    private Solution hillClimbing(EvaluationFunction function,Solution currentSolution,int numberOfParameters) {

        int maxIterations = numberOfIterations;

        String bestBitString = currentSolution.getBigBitString();;
        Solution best = new Solution(bestBitString, numberOfParameters);
        double bestEvaluationSoFar = function.evaluateForNewSolution(best);

        //System.out.println("Best so far is " + BinaryDecimalConvertor.bitStringToDecimal(bestBitString,lowerBound,upperBound,precision));
        //System.out.println("Minimum evaluation so far is " + bestEvaluationSoFar);

        int i = 0;
        while (i < maxIterations) {
            String candidateVC = function.setInitialBitStringSolution();

            boolean local = true;
            while (local) {
                ArrayList<String> byteNeighbours = getHammingNeighbours(candidateVC);
                Solution improvedSolution = getBestNeighbour(byteNeighbours, best, function, numberOfParameters);
                String improvedVN = improvedSolution.getBigBitString();

                if (isBetter(function, maxIterations, improvedVN, candidateVC)) {
                    candidateVC = improvedVN;

                    Solution vcParameter = new Solution(improvedVN, numberOfParameters);
                    double candidateEvaluation = function.evaluateForNewSolution(vcParameter);

                    //System.out.println("The function with bestVN evaluates to " + candidateEvaluation);
                    if (candidateEvaluation < bestEvaluationSoFar) {
                        bestBitString = candidateVC;
                        bestEvaluationSoFar = candidateEvaluation;
                        best.setBigBitString(candidateVC);
                    }
                } else {
                    local = false;
                }
            }

            i++;
        }

        return best;
    }

    private static boolean isBetter(EvaluationFunction f,int stopAtN, String binaryString1, String binaryString2){

        if(binaryString1.isEmpty() || binaryString2.isEmpty()){
            return false;
        }

        Solution p1 = new Solution(binaryString1,stopAtN);
        Solution p2 = new Solution(binaryString2,stopAtN);

        return f.evaluateForNewSolution(p1) < f.evaluateForNewSolution(p2);
    }


    private Solution getBestNeighbour(ArrayList<String> neighbours, Solution bestSoFar, EvaluationFunction function,
                                    int parameters) {

        double bestEvaluation = function.evaluateForNewSolution(bestSoFar);

        for (String neighbour : neighbours) {

            Solution newSolution = new Solution(neighbour, parameters);
            double newEvaluation = function.evaluateForNewSolution(newSolution);

            if (newEvaluation < bestEvaluation) {
                return newSolution;
            }
        }

        return bestSoFar;
    }

    public static ArrayList<String> getHammingNeighbours(String bitString) {
        int numberOfBits = bitString.length();

        ArrayList<String> neighbours = new ArrayList<>();

        for (int i = 0; i < numberOfBits; i++) {
            char[] bitArray = bitString.toCharArray();
            bitArray[i] = (bitArray[i] == '0') ? '1' : '0';
            neighbours.add(new String(bitArray));
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
