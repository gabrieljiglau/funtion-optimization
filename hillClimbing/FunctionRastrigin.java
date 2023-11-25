package hillClimbingAndSimulatedAnnealing.functionOptimization.hcAndSa;

import hillClimbingAndSimulatedAnnealing.Randomizer;

import java.util.List;

public class FunctionRastrigin implements EvaluationFunction {

    private int dimension;
    private Solution solution;
    private final double lowerDomain = -5.12;
    private double upperDomain = 5.12;
    private String name;
    private int precision;

    public FunctionRastrigin(int numberOfParameters,String name,int precision){
        this.dimension = numberOfParameters;
        this.solution = new Solution(setInitialBitStringSolution(),numberOfParameters);
        this.name = name;
        this.precision = precision;
    }

    public double evaluateForNewSolution(Solution solution) {
        double runningSum = 0;

        List<Double> evaluations = BinaryDecimalConvertor.bigBitStringToDecimal(solution,lowerDomain,upperDomain, precision);

        for (double evaluation : evaluations) {
            double firstCoefficient = evaluation * evaluation;
            double secondCoefficient = 10 * Math.cos(2 * Math.PI * evaluation);
            double result = firstCoefficient - secondCoefficient;
            runningSum += result;
        }
        return runningSum + 10 * solution.getNumberOfParameters();
    }

    public String setInitialBitStringSolution() {

        StringBuilder sb = new StringBuilder();
        int size = BinaryDecimalConvertor.getNumberOfBits(lowerDomain,upperDomain,precision);

        for(int i = 0; i < size * dimension; i++){

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

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public Solution getSolution() {
        return solution;
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

}
