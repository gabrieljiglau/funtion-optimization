package geneticAlgorithms;

import java.util.ArrayList;
import java.util.List;

public class FunctionDeJongG extends FunctionG {

    private List<Boolean> isSelected;
    private int numberOfParameters;
    private final double lowerDomain = -5.12;
    private double upperDomain = 5.12;


    public FunctionDeJongG(int n) {
        this.numberOfParameters = n;
        this.parameters = initializeParameters(n);
    }

    public FunctionDeJongG(){
        this.parameters = new ArrayList<>();
    }

    public FunctionG createNextGeneration(){
        return new FunctionDeJongG();
    }

    @Override
    public double evaluateForNewSolution(Solution parameter) {
        double runningSum = 0;
        for (Solution p : parameters) {
            p.setCoefficient(parameter.getCoefficient());
            runningSum += p.getCoefficient() * p.getCoefficient();
        }

        return runningSum;
    }

    public void addPossibleMutation(Solution s,double mutationRate){

        if (Randomizer.getDoubleFromZeroToOne() < mutationRate) {
            double doubleFrom0Point8To1Point2 = Randomizer.doubleBetween(0.8, 1.2);
            double currentCoefficient = s.getCoefficient();
            s.setCoefficient(currentCoefficient *doubleFrom0Point8To1Point2);
        }

    }


    public List<Solution> initializeParameters(int n) {

        List<Solution> initializedParameters = new ArrayList<>();

        for (int i = 0; i < n; i++) {

            double d1 = Randomizer.getDoubleFromZeroToOne();
            int oneOrNegativeOne = 1;

            double doubleFromDomain = -lowerDomain + (upperDomain - (lowerDomain)) * Randomizer.getDoubleFromZeroToOne();
            double coefficient;

            if (d1 <= 0.5) {
                coefficient = -oneOrNegativeOne * doubleFromDomain;
            } else {
                coefficient = doubleFromDomain;
            }

            Solution toAdd = new Solution(coefficient * coefficient, n);
            initializedParameters.add(toAdd);
        }

        return initializedParameters;
    }

    public String setInitialSolution() {

        double d1 = Randomizer.getDoubleFromZeroToOne();
        int oneOrNegativeOne = 1;

        double doubleFromDomain = Randomizer.doubleBetween(lowerDomain, upperDomain);
        double coefficient;

        if (d1 <= 0.5) {
            coefficient = -oneOrNegativeOne * doubleFromDomain;
        } else {
            coefficient = doubleFromDomain;
        }

        return String.valueOf(coefficient);
    }

    public List<Solution> getParameters() {
        return parameters;
    }

    public void setParameters(List<Solution> parameters) {
        this.parameters = parameters;
    }

    public void setParameter(Solution parameter,int index){
        parameters.add(index,parameter);
    }

    public int getNumberOfParameters() {
        return numberOfParameters;
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

    public List<Boolean> getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(List<Boolean> isSelected) {
        this.isSelected = isSelected;
    }
}
