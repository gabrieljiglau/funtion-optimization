package geneticAlgorithms.functionOptimization;

import geneticAlgorithms.Randomizer;

import java.util.ArrayList;
import java.util.List;

public class FunctionRastrigin implements EvaluationFunction {

    private int n;
    private List<Solution> parameters;
    private final double lowerDomain = -5.12;
    private double upperDomain = 5.12;

    public FunctionRastrigin(int n){
        this.n = n;
        this.parameters = initializeParameters(n);
    }

    @Override
    public double evaluateForNewSolution(Solution parameter) {
        double runningSum = 0;

        double evaluatedCos = Math.cos(2 * Math.PI * parameter.getCoefficient());
        double actualCoefficient = parameter.getCoefficient() * parameter.getCoefficient() - 10 * evaluatedCos;

        for (Solution p : parameters) {
            p.setCoefficient(actualCoefficient);
            runningSum += p.getCoefficient();
        }

        return runningSum + 10 * parameter.getNumberOfParameters();
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
