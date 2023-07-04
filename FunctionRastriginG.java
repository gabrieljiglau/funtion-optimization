package geneticAlgorithms;

import java.util.ArrayList;
import java.util.List;

public class FunctionRastriginG extends FunctionG{

    private int numberOfParameters;
    private final double lowerDomain = -5.12;
    private double upperDomain = 5.12;

    public FunctionRastriginG(int numberOfParameters) {
        this.numberOfParameters = numberOfParameters;
        this.parameters = initializeParameters(numberOfParameters);

    }

    public FunctionRastriginG(){
        this.parameters = new ArrayList<>();
    }

    public double evaluateForNewSolution(Solution parameter) {
        double runningSum = 0;

        double evaluatedCos = Math.cos(2 * Math.PI * parameter.getCoefficient());
        double actualCoefficient = parameter.getCoefficient() * parameter.getCoefficient() - 10 * evaluatedCos;

        for (Solution p : parameters) {
            p.setCoefficient(actualCoefficient);
            runningSum += p.getCoefficient();
        }

        return runningSum + 10 * parameter.getStopAtN();
    }

    public List<Solution> initializeParameters(int n){

        List<Solution> initializedParameters = new ArrayList<>();

        for(int i = 0; i < n; i++){

            double d1 = Randomizer.getDoubleFromZeroToOne();
            int oneOrNegativeOne = 1;

            double doubleFromDomain = -lowerDomain + (upperDomain - (lowerDomain)) * Randomizer.getDoubleFromZeroToOne();
            double coefficient;

            if(d1 <= 0.5) {
                coefficient = -oneOrNegativeOne * doubleFromDomain;
            } else {
                coefficient = doubleFromDomain;
            }

            double evaluatedCos = Math.cos(2 * Math.PI * coefficient);
            double actualCoefficient = coefficient * coefficient - 10 * evaluatedCos;

            Solution initializedParameter = new Solution(actualCoefficient,n);
            initializedParameters.add(initializedParameter);
        }

        return initializedParameters;
    }

    public String setInitialSolution(){

        double d1 = Randomizer.getDoubleFromZeroToOne();
        int oneOrNegativeOne = 1;

        double doubleFromDomain = Randomizer.doubleBetween(lowerDomain,upperDomain);
        double coefficient;

        if(d1 <= 0.5) {
            coefficient = -oneOrNegativeOne * doubleFromDomain;
        } else {
            coefficient = doubleFromDomain;
        }

        return String.valueOf(coefficient);
    }

    @Override
    public void addPossibleMutation(Solution s, double mutationRate) {
        if (Randomizer.getDoubleFromZeroToOne() < mutationRate) {
            double doubleFrom0Point8To1Point2 = Randomizer.doubleBetween(0.8, 1.2);
            double currentCoefficient = s.getCoefficient();
            s.setCoefficient(currentCoefficient * doubleFrom0Point8To1Point2);
        }
    }

    protected FunctionG createNextGeneration() {

        return new FunctionRastriginG();
    }

    public void setParameter(Solution parameter,int index){
        parameters.add(index,parameter);
    }

    public int getNumberOfParameters() {
        return numberOfParameters;
    }

    public List<Solution> getParameters() {
        return parameters;
    }

    public void setParameters(List<Solution> parameters) {
        this.parameters = parameters;
    }

    public void setNumberOfParameters(int numberOfParameters) {
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
