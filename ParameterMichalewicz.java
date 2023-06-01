package geneticAlgorithms;

public class ParameterMichalewicz {

    private double coefficient;
    private final int m = 10;
    private int i = 1;

    public ParameterMichalewicz(double coefficient){

        double firstSin = Math.sin(Math.toRadians(coefficient));
        double secondSin = Math.pow(Math.sin((i * coefficient * coefficient)/Math.PI),2 * m);
        i++;

        this.coefficient = firstSin * secondSin;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public int getM() {
        return m;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}
