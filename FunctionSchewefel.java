package geneticAlgorithms;

import java.util.ArrayList;
import java.util.List;

public class FunctionSchewefel implements EvaluationFunction{

    private int n;
    private List<Solution> parameters;
    private final double lowerDomain = -500;
    private double upperDomain = 500;

    public FunctionSchewefel(int n){
        this.n = n;
        this.parameters = initializeParameters(n);
    }

    @Override
    public double evaluateForNewSolution(Solution parameter) {
        double runningSum = 0;
        double specificRadians = Math.toRadians(parameter.getCoefficient());
        double secondCoefficient = Math.sin(Math.sqrt((Math.abs(specificRadians))));

        for (Solution p : parameters) {
            p.setCoefficient(secondCoefficient * (parameter.getCoefficient()));
            runningSum += p.getCoefficient();
        }

        return runningSum;
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

            double specificRadians = Math.toRadians(coefficient);
            double secondCoefficient = Math.sin(Math.sqrt((Math.abs(specificRadians))));
            double actualCoefficient = secondCoefficient * (-coefficient);

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
