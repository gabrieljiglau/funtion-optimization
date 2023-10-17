package geneticAlgorithms.functionOptimization;

public class Solution {

    private double coefficient;
    private int numberOfParameters;

    public Solution(double coefficient, int numberOfParameters){
        this.coefficient = coefficient;
        this.numberOfParameters = numberOfParameters;
    }

    public Solution(double coefficient){
        this.coefficient = coefficient;
    }

    public int getNumberOfParameters() {
        return numberOfParameters;
    }

    public void setNumberOfParameters(int numberOfParameters) {
        this.numberOfParameters = numberOfParameters;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }
}
