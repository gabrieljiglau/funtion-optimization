package hillClimbingAndSimulatedAnnealing.functionOptimization.hcAndSa;

import hillClimbingAndSimulatedAnnealing.Randomizer;

import java.util.List;

public class FunctionSchewefel implements EvaluationFunction {

    private int dimensions;
    private Solution solution;
    private final double lowerDomain = -500.0;
    private double upperDomain = 500.0;
    private String name;
    private int precision;

    public FunctionSchewefel(int numberOfParameters,String name,int precision){
        this.dimensions = numberOfParameters;
        this.solution = new Solution(setInitialBitStringSolution(),numberOfParameters);
        this.name = name;
        this.precision = precision;
    }

    public double evaluateForNewSolution(Solution solution) {
        double runningSum = 0;

        String bitString = solution.getBigBitString();
        List<Double> evaluations = BinaryDecimalConvertor.bigBitStringToDecimal(new Solution(bitString,dimensions),
                                lowerDomain, upperDomain, precision);

        for (double evaluation : evaluations) {
            double coefficient = Math.sin(Math.sqrt(Math.abs(evaluation)));
            runningSum += coefficient * (-1) * evaluation;
        }

        return runningSum;
    }

    /*@Override
    public double evaluateForNewSolution(Solution parameter) {

        double runningSum = 0;

        String bitString = parameter.getBitString();
        double convertedNumber = BinaryDecimalConvertor.bitStringToDecimal(bitString,lowerDomain,upperDomain,getN(),precision);

        for (int i = 0; i < parameter.getNumberOfParameters(); i++) {
            double coefficient = Math.sin(Math.sqrt(Math.abs(convertedNumber)));
            runningSum += -convertedNumber * coefficient;
        }

        return runningSum;
    }*/

    public String setInitialBitStringSolution() {
        StringBuilder sb = new StringBuilder();
        int size = BinaryDecimalConvertor.getNumberOfBits(lowerDomain,upperDomain,precision);

        for(int i = 0; i < size * dimensions; i++){

            byte b;
            double firstProbability = Randomizer.doubleBetween(0,1);
            double secondProbability = Randomizer.doubleBetween(0,1);
            if(firstProbability < secondProbability){
                b = (byte) 1;
            } else {
                b = (byte) 0;
            }
            sb.append(b);
        }

        return sb.toString();
    }

    /*public String setInitialBitStringSolution() {

        StringBuilder sb = new StringBuilder();
        int size = BinaryDecimalConvertor.getNumberOfBits(lowerDomain, upperDomain, precision);

        for (int i = 0; i < size; i++) {

            byte b;
            double firstProbability = Randomizer.doubleBetween(0, 1);
            double secondProbability = Randomizer.doubleBetween(0, 1);
            if (firstProbability < secondProbability) {
                b = (byte) 1;
            } else {
                b = (byte) 0;
            }
            sb.append(b);
        }

        return sb.toString();
    }*/

    public Solution getParameters() {
        return solution;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }

    public double getLowerDomain() {
        return lowerDomain;
    }

    public double getUpperDomain() {
        return upperDomain;
    }

    public void setUpperDomain(double upperDomain) {
        this.upperDomain = upperDomain;
    }

    public int getN() {
        return dimensions;
    }

    public void setN(int n) {
        this.dimensions = n;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
