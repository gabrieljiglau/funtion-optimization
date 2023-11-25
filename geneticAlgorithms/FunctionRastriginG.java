package onlyGeneticAlgorithms;

import java.util.ArrayList;
import java.util.List;


public class FunctionRastriginG extends FunctionG{

    private int numberOfParameters;
    private final double lowerDomain = -5.12;
    private double upperDomain = 5.12;
    private int precision;

    public FunctionRastriginG(int numberOfParameters,int precision) {
        this.numberOfParameters = numberOfParameters;
        this.solutionList = initializeSolutionList(numberOfParameters);
        this.solutionList = new ArrayList<>();
        this.precision = precision;
    }


    public List<Solution> initializeSolutionList(int n) {

        List<Solution> initializedParameters = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            Solution toAdd = new Solution(setInitialBitStringSolution(lowerDomain,upperDomain,precision,numberOfParameters),n);
            initializedParameters.add(toAdd);
        }

        return initializedParameters;
    }

    @Override
    public String setInitialBitStringSolution(double lowerDomain, double upperDomain, int precision, int numberOfParameters) {
        return super.setInitialBitStringSolution(lowerDomain, upperDomain, precision, numberOfParameters);
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

    protected FunctionG createNextGeneration(int numberOfParameters,int precision) {

        return new FunctionRastriginG(numberOfParameters,precision);
    }

    public void setSolution(Solution parameter, int index){
        solutionList.add(index,parameter);
    }

    public int getNumberOfParameters() {
        return numberOfParameters;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public List<Solution> getSolutionList() {
        return solutionList;
    }

    public void setParameters(List<Solution> parameters) {
        this.solutionList = parameters;
    }

    public void setNumberOfSolutions(int numberOfParameters) {
        this.numberOfParameters = numberOfParameters;
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
