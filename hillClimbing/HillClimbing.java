package hillClimbingAndSimulatedAnnealing.functionOptimization.hcAndSa;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HillClimbing {

    private int dimension;

    public HillClimbing(){
    }

    public static double calculateStandardDeviation(double[] array,double mean,int length) {

        double standardDeviation = 0.0;
        for (double num : array) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return Math.sqrt(standardDeviation / length);
    }

    public double findMinimum(EvaluationFunction function, int type, int numberOfParameters, int maxIterations) {

        String bestBitString = function.setInitialBitStringSolution();
        Solution best = new Solution(bestBitString, numberOfParameters);
        double bestEvaluationSoFar = function.evaluateForNewSolution(best);

        int i = 0;
        while (i < maxIterations) {
            String candidateVC = function.setInitialBitStringSolution();

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


    private int getFunctionIndex(EvaluationFunction function){

        switch (function.getName()) {
            case "DeJong":
                return 0;
            case "Schewefel":
                return 1;
            case "Rastrigin":
                return 2;
            case "Michalewicz":
                return 3;
        }

        System.out.println("Invalid input for function name");
        return -1;
    }

     private static boolean isBetter(EvaluationFunction f,int stopAtN, String binaryString1, String binaryString2){

        if(binaryString1.isEmpty() || binaryString2.isEmpty()){
            return false;
        }

        Solution p1 = new Solution(binaryString1,stopAtN);
        Solution p2 = new Solution(binaryString2,stopAtN);

        return f.evaluateForNewSolution(p1) < f.evaluateForNewSolution(p2);
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

    public List<List<Double>> setBounds(){
        List<List<Double>> bounds = new ArrayList<>();
        List<Double> deJongBounds = new ArrayList<>();
        deJongBounds.add(-5.12);
        deJongBounds.add(5.12);
        bounds.add(0,deJongBounds);

        List<Double> schewefelBounds = new ArrayList<>();
        schewefelBounds.add(-500.0);
        schewefelBounds.add(500.0);
        bounds.add(1,schewefelBounds);

        List<Double> rastriginBounds = new ArrayList<>();
        rastriginBounds.add(-5.12);
        rastriginBounds.add(5.12);
        bounds.add(2,rastriginBounds);

        List<Double> michalewiczBounds = new ArrayList<>();
        michalewiczBounds.add(0.0);
        michalewiczBounds.add(Math.PI);
        bounds.add(3,michalewiczBounds);

        return bounds;
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

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }
}
