package geneticAlgorithms;

public class ParameterSchewefel {

    private double coefficient;
    private double secondCoefficient;

    public ParameterSchewefel(double coefficient){

        double specificRadians = Math.toRadians(coefficient);
        this.secondCoefficient = Math.sin(Math.sqrt((Math.abs(specificRadians))));

        this.coefficient = -coefficient * secondCoefficient;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public double getSecondCoefficient() {
        return secondCoefficient;
    }

    public void setSecondCoefficient(double secondCoefficient) {
        this.secondCoefficient = secondCoefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }
}
