package geneticAlgorithms;

public class Solution {  // base class

    private double coefficient;
    private int stopAtN;

    public Solution(double coefficient, int stopAtN){
        this.coefficient = coefficient;
        this.stopAtN = stopAtN;
    }

    public Solution(double coefficient){
        this.coefficient = coefficient;
    }


    public int getStopAtN() {
        return stopAtN;
    }

    public void setStopAtN(int stopAtN) {
        this.stopAtN = stopAtN;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }
}
