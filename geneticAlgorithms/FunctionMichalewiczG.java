package onlyGeneticAlgorithms;

import java.util.ArrayList;
import java.util.List;

public class FunctionMichalewiczG extends FunctionG {

    private int numberOfParameters;
    private final double lowerDomain = 0;
    private final double upperDomain = Math.PI;
    private int precision;


    public FunctionMichalewiczG(int numberOfParameters,int precision){
        this.numberOfParameters = numberOfParameters;
        //this.solutionList = initializeSolutionList(numberOfParameters);
        this.solutionList = new ArrayList<>();
        this.precision = precision;
    }

    public FunctionMichalewiczG(){
        this.solutionList = new ArrayList<>();
    }

    public FunctionG createNextGeneration(int numberOfParameters,int precision){
        return new FunctionMichalewiczG(numberOfParameters,precision);
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

    @Override
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

    public List<Solution> getSolutionList() {
        return solutionList;
    }

    public void setSolution(Solution parameter, int index){
        solutionList.add(index,parameter);
    }

    public void setParameters(List<Solution> parameters) {
        this.solutionList = parameters;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public double getLowerDomain() {
        return lowerDomain;
    }

    public double getUpperDomain() {
        return upperDomain;
    }

    public int getSteepness() {
        return 10;
    }

    public int getNumberOfParameters() {
        return numberOfParameters;
    }

    public void setNumberOfSolutions(int numberOfParameters) {
        this.numberOfParameters = numberOfParameters;
    }
}
