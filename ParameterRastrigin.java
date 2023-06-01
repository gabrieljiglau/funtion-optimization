package geneticAlgorithms;

public class ParameterRastrigin {

    private double coefficient;
    private int stopAtN;

    public ParameterRastrigin(double coefficient,int stopAtN){

        double specificRadians = Math.toRadians(2 * coefficient * Math.PI);

        this.coefficient = coefficient * coefficient - 10 * Math.cos(specificRadians);
        this.stopAtN = stopAtN;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public int getStopAtN() {
        return stopAtN;
    }

    public void setStopAtN(int stopAtN) {
        this.stopAtN = stopAtN;
    }
}
