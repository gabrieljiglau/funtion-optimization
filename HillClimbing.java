package geneticAlgorithms;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HillClimbing {

    public static void main(String[] args) {

        Function function = new Function(20,4);

        List<Integer> selectedFunction = new ArrayList<>();
        selectedFunction.add(function.getFunctionNumber());

        HillClimbing climbing = new HillClimbing(function,selectedFunction);

        long startTime = System.nanoTime();

        System.out.println("Minimum for michalewicz is " + climbing.climbMethod(selectedFunction));

        long finishTime = System.nanoTime();
        long duration = (finishTime - startTime) / 1000000;

        System.out.println("********************************8");
        System.out.println("Total duration for getting the minValue of the function is " + duration + " miliseconds");
    }

    private Function function;
    private List<Integer> selectedFunction;

    public HillClimbing(Function function, List<Integer> selectedFunction){

        this.function = function;
        this.selectedFunction = selectedFunction;
    }

    public String climbMethod(List<Integer> selectedFunction){

        int limit = function.getParameterMichalewicz().size();

        ParameterMichalewicz initializedBest = function.setInitialSolutionMichalewicz();
        double bestEvaluationSoFar = function.evaluateFunctionForNewVariableM(initializedBest.getCoefficient());
        System.out.println("Best so far is " + initializedBest.getCoefficient());
        System.out.println("Minimum evaluation so far is " + bestEvaluationSoFar);

        int i = 0;
        while(i < limit){

            String candidateVC = selectRandomSolution(selectedFunction);
            BigDecimal vc = new BigDecimal(candidateVC);

            System.out.println("Newly generated candidateVC is " + Double.valueOf(String.valueOf(vc)));

            boolean local = true;
            while (local) {

                ArrayList<Byte> byteNeighbours = getHammingNeighbours(vc.toBigInteger().byteValue());
                ArrayList<BigDecimal> neighbours = convertToBigDecimalList(byteNeighbours);

                BigDecimal bestNeighbourVN;

                bestNeighbourVN = getBestNeighbour(neighbours, bestEvaluationSoFar,2);

                System.out.println("Best neighbour is " + parseStringToDouble(String.valueOf(bestNeighbourVN)));

                if(isBetter(function,bestNeighbourVN,vc)){
                    vc = bestNeighbourVN;

                    ParameterMichalewicz vcParameter = new ParameterMichalewicz(Double.
                            parseDouble(bestNeighbourVN.toString()));
                    System.out.println("The function with bestVN evaluates to " + function.evaluateFunctionForNewVariableM(Double.
                            parseDouble(vc.toString())) + " when firstCoefficient is " + vcParameter.getCoefficient());
                           // " and the secondCoefficient(the evaluated sinus) is " + vcParameter.getSecondCoefficient()) ; //pt type2
                } else {
                    local = false;
                }
            }

            i++;
            BigDecimal first = vc;
            BigDecimal compareTo = BigDecimal.valueOf(initializedBest.getCoefficient());

            if(isBetter(function,first,compareTo)){
                double vcDouble = Double.parseDouble(String.valueOf(vc));
                initializedBest.setCoefficient(vcDouble);
            }
        }

        return String.valueOf(function.evaluateFunctionForNewVariableM(initializedBest.getCoefficient()));
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

    private String selectRandomSolution(List<Integer> selectedFunction){

        double lower = 0;
        double upper = 0;

        if(selectedFunction.get(0) == 1){
            lower = function.getLowerDomainJ();
            upper = function.getUpperDomainJ();
        } else if(selectedFunction.get(0) == 2){
            lower = function.getLowerDomainS();
            upper = function.getUpperDomainS();
        } else if(selectedFunction.get(0) == 3){
            lower = function.getLowerDomainR();
            upper = function.getUpperDomainR();
        }

        double d1 = Randomizer.getDoubleFromZeroToOne();
        int oneOrNegativeOne = 1;

        double doubleFromDomain = Randomizer.doubleBetween(lower,upper);
        double coefficient;

        if(d1 <= 0.5) {
            coefficient = -oneOrNegativeOne * doubleFromDomain;
        } else {
            coefficient = doubleFromDomain;
        }

        return String.valueOf(coefficient);
    }


    private static boolean isBetter(Function f, BigDecimal i1, BigDecimal i2){

        int type = f.getFunctionNumber();

        if(String.valueOf(i1) == null || String.valueOf(i2) == null){
            return false;
        }

        double int1 = parseStringToDouble(String.valueOf(i1));
        double int2 = parseStringToDouble(String.valueOf(i2));

        if(type == 1){
            return f.evaluateFunctionForNewVariableJ(int1) < f.evaluateFunctionForNewVariableJ(int2);
        } else if(type == 2) {
            return f.evaluateFunctionForNewVariableS(int1) < f.evaluateFunctionForNewVariableS(int2);
        } else if(type == 3){
            return f.evaluateFunctionForNewVariableR(int1) < f.evaluateFunctionForNewVariableR(int2);
        } else if(type == 4){
            return f.evaluateFunctionForNewVariableM(int1) > f.evaluateFunctionForNewVariableM(int2);
        }

        return false;
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
            //String numberToString = moveMinusSymbol(binaryToDecimal(neighbourToString));
            //double actualNumber = Double.parseDouble(numberToString);

            double actualNumber = Double.valueOf(neighbourToString);

            double newEvaluation = function.evaluateFunctionForNewVariableM(actualNumber);

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

    private ParameterDeJong selectNeighbour(ParameterDeJong current){

        return null;
    }

    public List<Integer> getSelectedFunction() {
        return selectedFunction;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }


    public void setSelectedFunction(List<Integer> selectedFunction) {
        this.selectedFunction = selectedFunction;
    }
}
