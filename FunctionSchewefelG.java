package geneticAlgorithms;

import java.util.ArrayList;
import java.util.List;

public class FunctionSchewefelG extends FunctionG {

    private int numberOfParameters;
    private final double lowerDomain = -500;
    private double upperDomain = 500;


    public FunctionSchewefelG(int numberOfParameters){
        this.numberOfParameters = numberOfParameters;
        this.parameters = initializeParameters(numberOfParameters);
    }

    public FunctionSchewefelG(){
        this.parameters = new ArrayList<>();
    }

    public FunctionG createNextGeneration(){
        return new FunctionSchewefelG();
    }

    public double evaluateForNewSolution(Solution parameter) {
        double runningSum = 0;
        double fistCoefficient = Math.sqrt(Math.abs(parameter.getCoefficient()));
        double secondCoefficient = Math.sin(fistCoefficient);

        for (Solution p : parameters) {
            p.setCoefficient(-secondCoefficient * (parameter.getCoefficient()));
            runningSum += p.getCoefficient();
        }

        return runningSum;
    }


    public void addPossibleMutation(Solution s,double mutationRate){

        if (Randomizer.getDoubleFromZeroToOne() < mutationRate) {
            double doubleFrom0Point8To1Point2 = Randomizer.doubleBetween(0.8, 1.2);
            double currentCoefficient = s.getCoefficient();
            s.setCoefficient(currentCoefficient * doubleFrom0Point8To1Point2);
        }

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

            double secondCoefficient = Math.sin(Math.sqrt(Math.abs(coefficient)));
            double actualCoefficient = -coefficient * secondCoefficient;

            Solution toAdd = new Solution(actualCoefficient,n);
            initializedParameters.add(toAdd);
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

    public List<Solution> getParameters() {
        return parameters;
    }

    public void setParameters(List<Solution> parameters) {
        this.parameters = parameters;
    }

    public int getNumberOfParameters() {
        return numberOfParameters;
    }

    public void setParameter(Solution parameter,int index){
        parameters.add(index,parameter);
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
