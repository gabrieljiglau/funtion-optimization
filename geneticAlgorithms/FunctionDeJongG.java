package onlyGeneticAlgorithms;


import java.util.ArrayList;
import java.util.List;

public class FunctionDeJongG extends FunctionG {

    private List<Boolean> isSelected;
    private int numberOfParameters;
    private final double lowerDomain = -5.12;
    private double upperDomain = 5.12;
    private int precision;


    public FunctionDeJongG(int dimensions,int precision) {
        this.numberOfParameters = dimensions;
        this.solutionList = initializeSolutionList(dimensions);
        this.solutionList = new ArrayList<>();
        this.precision = precision;
    }

    public FunctionDeJongG(){
        this.solutionList = new ArrayList<>();
    }

    public FunctionG createNextGeneration(int numberOfParameters,int precision){
        return new FunctionDeJongG(numberOfParameters,precision);
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

    @Override
    public String setInitialBitStringSolution(double lowerDomain, double upperDomain, int precision, int numberOfParameters) {
        return super.setInitialBitStringSolution(lowerDomain, upperDomain, precision, numberOfParameters);
    }

    public List<Solution> initializeSolutionList(int n) {

        List<Solution> initializedParameters = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            Solution newSolution = new Solution(setInitialBitStringSolution(lowerDomain,upperDomain,precision,numberOfParameters),n);
            initializedParameters.add(newSolution);
        }
        return initializedParameters;
    }

    public List<Solution> getSolutionList() {
        return solutionList;
    }

    public void setParameters(List<Solution> parameters) {
        this.solutionList = parameters;
    }

    public void setSolution(Solution parameter, int index){
        solutionList.add(index,parameter);
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getNumberOfParameters() {
        return numberOfParameters;
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

    public List<Boolean> getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(List<Boolean> isSelected) {
        this.isSelected = isSelected;
    }
}
