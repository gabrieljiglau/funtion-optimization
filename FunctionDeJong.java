package geneticAlgorithms;

import java.util.ArrayList;
import java.util.List;

public class FunctionDeJong implements EvaluationFunction{

    private int n;
    private List<Solution> parameters;
    private final double lowerDomain = -5.12;
    private double upperDomain = 5.12;


    public FunctionDeJong(int n){
        this.n = n;
        this.parameters = initializeParameters(n);
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

            Solution toAdd = new Solution(coefficient * coefficient,n);
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

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
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

