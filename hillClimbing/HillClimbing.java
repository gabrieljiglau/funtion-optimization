package geneticAlgorithms.functionOptimization;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HillClimbing {

    public static void main(String[] args) {

        HillClimbing algorithm = new HillClimbing(10);

        int n = algorithm.getN();
        int steepness = 5;

        FunctionDeJong deJong = new FunctionDeJong(n);
        FunctionSchewefel schewefel = new FunctionSchewefel(n);
        FunctionRastrigin rastrigin = new FunctionRastrigin(n);
        FunctionMichalewicz michalewicz = new FunctionMichalewicz(n,steepness);

        long startTime = System.nanoTime();
        int type = 3; //type -> 1 : firstImprovement; 2 : bestImprovement; 3 : worstImprovement
        System.out.println("Minimum for function is " + algorithm.findMinimum(schewefel,type));

        long finishTime = System.nanoTime();
        long duration = (finishTime - startTime) / 1000000;

        System.out.println("********************************8");
        System.out.println("Total duration for getting the minValue of the function is " + duration + " miliseconds");
    }

    private int n;

    public HillClimbing(int n){
        this.n = n;
    }


    public String findMinimum(EvaluationFunction function, int type){

        int limit = n * 100;

        String bestCoefficient = function.setInitialSolution();
        Solution best = new Solution(parseStringToDouble(bestCoefficient),n);

        double bestEvaluationSoFar = function.evaluateForNewSolution(best);

        System.out.println("Best so far is " + best.getCoefficient());
        System.out.println("Minimum evaluation so far is " + bestEvaluationSoFar);

        int i = 0;
        while(i < limit){

            String candidateVC = function.setInitialSolution();
            BigDecimal vc = new BigDecimal(candidateVC);

            System.out.println("Newly generated candidateVC is " + Double.valueOf(String.valueOf(vc)));

            boolean local = true;
            while (local) {

                ArrayList<Byte> byteNeighbours = getHammingNeighbours(vc.toBigInteger().byteValue());
                ArrayList<BigDecimal> neighbours = convertToBigDecimalList(byteNeighbours);

                BigDecimal improveNeighbourhood;

                improveNeighbourhood = getBestNeighbour(neighbours, bestEvaluationSoFar,function,limit,type);

                System.out.println("Best neighbour is " + parseStringToDouble(String.valueOf(improveNeighbourhood)));

                if(isBetter(function,limit,improveNeighbourhood,vc)){
                    vc = improveNeighbourhood;

                    Solution vcParameter = new Solution(Double.parseDouble(improveNeighbourhood.toString()),limit);
                    System.out.println("The function with bestVN evaluates to " + function.evaluateForNewSolution(vcParameter) +
                            " when firstCoefficient is " + vcParameter.getCoefficient());
                } else {
                    local = false;
                }
            }

            i++;
            BigDecimal first = vc;
            String initializedBest = String.valueOf(best.getCoefficient());
            BigDecimal compareTo = new BigDecimal(initializedBest);

            if(isBetter(function,limit,first,compareTo)){
                double vcDouble = Double.parseDouble(String.valueOf(vc));
                best.setCoefficient(vcDouble);
            }
        }

        return String.valueOf(function.evaluateForNewSolution(best));
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


     private static boolean isBetter(EvaluationFunction f,int stopAtN, BigDecimal i1, BigDecimal i2){

        if(String.valueOf(i1) == null || String.valueOf(i2) == null){
            return false;
        }

        double int1 = i1.doubleValue();
        double int2 = i2.doubleValue();

        Solution p1 = new Solution(int1,stopAtN);
        Solution p2 = new Solution(int2,stopAtN);

        return f.evaluateForNewSolution(p1) < f.evaluateForNewSolution(p2);
    }

    private static double parseStringToDouble(String value) {
        return value == null || value.isEmpty() ? Double.NaN : Double.parseDouble(value);
    }

    private BigDecimal getBestNeighbour(ArrayList<BigDecimal> neighbours, double bestSoFar,EvaluationFunction function,
                                        int parameters,int type){

        int index = 0;
        double bestEvaluation = bestSoFar; //evaluarea functiei

        BigDecimal worstImprovement = null;
        double worstEvaluation = Double.NEGATIVE_INFINITY;

        for(BigDecimal neighbour : neighbours){

            String neighbourToString = String.valueOf(neighbour);
            Solution newSolution = new Solution(Double.parseDouble(neighbourToString),parameters);
            double newEvaluation = function.evaluateForNewSolution(newSolution);

            if(type == 1) {
                if (newEvaluation < bestEvaluation) {
                   return new BigDecimal(neighbourToString);
                }
            } else if(type == 2){
                if(newEvaluation < bestEvaluation){
                    bestEvaluation = newEvaluation;
                    index = neighbours.indexOf(neighbour);
                }
            } else if(type == 3){
                /*List<Double> betterSolutions = new ArrayList<>();
                if(newEvaluation < bestEvaluation){
                    betterSolutions.add(newEvaluation);
                }

                Collections.sort(betterSolutions);
                return BigDecimal.valueOf(betterSolutions.get(0));*/
                if (newEvaluation < bestSoFar && newEvaluation > worstEvaluation) {
                    worstEvaluation = newEvaluation;
                    worstImprovement = new BigDecimal(neighbourToString);
                }
            } else {
                System.out.println("Unknown type");
                return new BigDecimal(-1);
            }
        }

        return new BigDecimal(String.valueOf(neighbours.get(index)));
    }

    private String moveMinusSymbol(String s){

        StringBuilder sb = new StringBuilder();
        if(s.contains("-")){
            sb.append("-");
        }

        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) != '-') {
                sb.append(s.charAt(i));
            }
        }

        return sb.toString();
    }

    private byte[] stringToBytes(String input){

        return input.getBytes();
    }

    private String decimalToBinary(double num, int precision){
        StringBuilder binary = new StringBuilder();

        int integral = (int) num;
        double fractional = num - integral;

        while (integral > 0) {
            int rem = integral % 2;
            binary.append((char) (rem + '0'));

            integral /= 2;
        }

        binary = new StringBuilder(binaryToDecimal(binary.toString()));

        binary.append('.');

        while (precision-- > 0) {
            // Find next bit in fraction
            fractional *= 2;
            int fract_bit = (int) fractional;

            if (fract_bit == 1) {
                fractional -= fract_bit;
                binary.append((char) (1 + '0'));
            }else {
                binary.append('0');
            }
        }

        return binary.toString();
    }

    static String binaryToDecimal(String input) {
        char[] tempArray = input.toCharArray();
        int left, right = 0;
        right = tempArray.length - 1;

        for (left = 0; left < right; left++, right--){
            // Swap values of left and right
            char temp = tempArray[left];
            tempArray[left] = tempArray[right];
            tempArray[right] = temp;
        }
        return String.valueOf(tempArray);
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

    public static ArrayList<BigDecimal> removeNullNeighbour(ArrayList<BigDecimal> input,BigDecimal nullNeighbour){

        input.remove(nullNeighbour);

        return input;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}
