package geneticAlgorithms;

public class ParameterDeJong{
    private double coefficient;
    private double x;
    private double power;


    public ParameterDeJong(double coefficient){
        this.coefficient = coefficient;
        this.x = 1;
        this.power = 2;
    }


    @Override
    public String toString() {
        return "Parameter{" +
                "coefficient=" + coefficient +
                ", x=" + x +
                ", power=" + power +
                '}';
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }
}
