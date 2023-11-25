package hillClimbingAndSimulatedAnnealing.functionOptimization.hcAndSa;

import hillClimbingAndSimulatedAnnealing.Randomizer;

import java.util.List;

public class FunctionDeJong implements EvaluationFunction {

    private int dimension;
    private Solution solution;
    private final double lowerDomain = -5.12;
    private double upperDomain = 5.12;
    private String name;
    private int precision;

    public FunctionDeJong(int numberOfParameters,String name,int precision){
        this.dimension = numberOfParameters;
        this.name = name;
        this.precision = precision;
        this.solution = new Solution(setInitialBitStringSolution(),numberOfParameters);
    }

    @Override
    public double evaluateForNewSolution(Solution solution) {
        double runningSum = 0;

        List<Double> evaluations = BinaryDecimalConvertor.bigBitStringToDecimal(solution,lowerDomain,upperDomain, precision);

        for (double evaluation : evaluations) {
            runningSum += evaluation * evaluation;
        }

        return runningSum;
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

    public Solution getSolution() {
        return solution;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
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

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

