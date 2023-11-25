package hillClimbingAndSimulatedAnnealing.functionOptimization.hcAndSa;
import hillClimbingAndSimulatedAnnealing.Randomizer;

import java.util.List;

public class FunctionMichalewicz implements EvaluationFunction {

    private int dimensions;
    private Solution solution;
    private final double lowerDomain = 0;
    private final double upperDomain = Math.PI;
    private int steepness;
    private String name;
    private int precision;

    public FunctionMichalewicz(int numberOfParameters,int steepness,String name,int precision){
        this.dimensions = numberOfParameters;
        this.solution = new Solution(setInitialBitStringSolution(),numberOfParameters);
        this.steepness = steepness;
        this.name = name;
        this.precision = precision;
    }

    public double evaluateForNewSolution(Solution solution) {
        double runningSum = 0;
        List<Double> evaluations = BinaryDecimalConvertor.bigBitStringToDecimal(solution, lowerDomain, upperDomain, precision);

        for (int i = 0; i < evaluations.size(); i++) {
            double evaluation = evaluations.get(i);
            double firstCoefficient = Math.sin(evaluation);
            double secondCoefficient = Math.pow(Math.sin(((i + 1) * Math.pow(evaluation, 2)) / Math.PI), 2 * getSteepness());
            double result = firstCoefficient * secondCoefficient;
            runningSum -= result;
        }

        return runningSum;
    }

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

    public int getDimensions() {
        return dimensions;
    }

    public void setDimensions(int dimensions) {
        this.dimensions = dimensions;
    }

    public int getSteepness() {
        return steepness;
    }

    public void setSteepness(int steepness) {
        this.steepness = steepness;
    }

    public String getName() {
        return name;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public void setName(String name) {
        this.name = name;
    }
}
