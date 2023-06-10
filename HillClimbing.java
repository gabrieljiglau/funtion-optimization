package geneticAlgorithms;

import java.math.BigDecimal;
import java.util.ArrayList;

public class HillClimbing {

    public static void main(String[] args) {

        HillClimbing algorithm = new HillClimbing(20);

        int n = algorithm.getN();
        FunctionDeJong deJong = new FunctionDeJong(n);
        FunctionSchewefel schewefel = new FunctionSchewefel(n);
        FunctionRastrigin rastrigin = new FunctionRastrigin(n);
        FunctionMichalewicz michalewicz = new FunctionMichalewicz(n);

        long startTime = System.nanoTime();

        System.out.println("Minimum for schewefel is " + algorithm.findMinimum(schewefel,1));

        long finishTime = System.nanoTime();
        long duration = (finishTime - startTime) / 1000000;

        System.out.println("********************************");
        System.out.println("Total duration for getting the minValue of the function is " + duration + " miliseconds");
    }

    private int n;

    public HillClimbing(int n){
        this.n = n;
    }


    public String findMinimum(EvaluationFunction function,int type){

        int limit = n;

        String bestCoefficient = function.setInitialSolution();
        Solution best = new Solution(parseStringToDouble(bestCoefficient),n);

        double bestEvaluationSoFar = function.evaluateForNewSolution(best);

        int i = 0;
        while(i < limit){

            String candidateVC = function.setInitialSolution();
            BigDecimal vc = new BigDecimal(candidateVC);

            boolean local = true;
            while (local) {

                ArrayList<Byte> byteNeighbours = getHammingNeighbours(vc.toBigInteger().byteValue());
                ArrayList<BigDecimal> neighbours = convertToBigDecimalList(byteNeighbours);

                BigDecimal improveNeighbourhood;

                improveNeighbourhood = getBestNeighbour(neighbours, bestEvaluationSoFar,type);

                if(isBetter(function,limit,improveNeighbourhood,vc)){
                    vc = improveNeighbourhood;

                    Solution vcParameter = new Solution(Double.parseDouble(improveNeighbourhood.toString()),limit);
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

        double int1 = parseStringToDouble(String.valueOf(i1));
        double int2 = parseStringToDouble(String.valueOf(i2));

        Solution p1 = new Solution(int1,stopAtN);
        Solution p2 = new Solution(int2,stopAtN);

        return f.evaluateForNewSolution(p1) < f.evaluateForNewSolution(p2);
    }

    private static double parseStringToDouble(String value) {
        return value == null || value.isEmpty() ? Double.NaN : Double.parseDouble(value);
    }

    private BigDecimal getBestNeighbour(ArrayList<BigDecimal> neighbours, double bestSoFar,int type){

        //type -> 1 : firstImprovement, 2 : bestImprovement
        int index = 0;
        double bestEvaluation = bestSoFar; //evaluarea functiei
        for(BigDecimal neighbour : neighbours){

            String neighbourToString = String.valueOf(neighbour);
            double actualNumber = Double.valueOf(neighbourToString);
            double newEvaluation = 0;

            if(type == 1) {
                if (newEvaluation < bestEvaluation) {
                   return new BigDecimal(neighbourToString);
                }
            } else if(type == 2){
                if(newEvaluation < bestEvaluation){
                    bestEvaluation = newEvaluation;
                    index = neighbours.indexOf(neighbour);
                }
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
