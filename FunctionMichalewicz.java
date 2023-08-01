package geneticAlgorithms.functionOptimization;

import geneticAlgorithms.Randomizer;

import java.util.ArrayList;
import java.util.List;

public class FunctionMichalewicz implements EvaluationFunction {

    private int n;
    private List<Solution> parameters;
    private final double lowerDomain = 0;
    private final double upperDomain = Math.PI;

    public FunctionMichalewicz(int n){
        this.n = n;
        this.parameters = initializeParameters(n);
    }

    @Override
    public double evaluateForNewSolution(Solution parameter) {
        double runningSum = 0;

        int i = Randomizer.intBetween(1,parameter.getNumberOfParameters());

        double firstSin = Math.sin(parameter.getCoefficient());
        int m = 10;
        double secondSin = Math.pow(Math.sin(i * Math.pow(parameter.getCoefficient(),2) / Math.PI), 2 * m);
        double actualCoefficient = firstSin * secondSin;

        for (Solution p : parameters) {
            p.setCoefficient(actualCoefficient);
            runningSum += p.getCoefficient();
        }

        return runningSum;
    }

    public List<Solution> initializeParameters(int n){
        List<Solution> initializedParameters = new ArrayList<>();

        for(int i = 1; i <= n; i++){
            double doubleFromDomain = -lowerDomain + (upperDomain - (lowerDomain)) * Randomizer.getDoubleFromZeroToOne();

            double firstSin = Math.sin(doubleFromDomain);
            int m = 10;
            double secondSin = Math.pow((Math.sin(i * doubleFromDomain * doubleFromDomain) / Math.PI),2 * m);
            double actualCoefficient = firstSin * secondSin;

            Solution initializedParameter = new Solution(actualCoefficient,n);
            initializedParameters.add(initializedParameter);
        }

        return initializedParameters;
    }

    public String setInitialSolution(){

        double doubleFromDomain = Randomizer.doubleBetween(lowerDomain,upperDomain);

        return String.valueOf(doubleFromDomain);
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

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}
