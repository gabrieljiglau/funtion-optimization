package geneticAlgorithms;

import java.util.ArrayList;
import java.util.List;

public class FunctionMichalewiczG extends FunctionG {

    private int numberOfParameters;
    private final double lowerDomain = 0;
    private double upperDomain = Math.PI;

    public FunctionMichalewiczG(int numberOfParameters){
        this.numberOfParameters = numberOfParameters;
        this.parameters = initializeParameters(numberOfParameters);
    }

    public FunctionMichalewiczG(){
        this.parameters = new ArrayList<>();
    }

    public FunctionG createNextGeneration(){
        return new FunctionMichalewiczG();
    }

    public void addPossibleMutation(Solution s,double mutationRate){
        if (Randomizer.getDoubleFromZeroToOne() < mutationRate) {
            double doubleFrom0Point8To1Point2 = Randomizer.doubleBetween(0.8, 1.2);
            double currentCoefficient = s.getCoefficient();
            s.setCoefficient(currentCoefficient *doubleFrom0Point8To1Point2);
        }
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

    @Override
    public double evaluateForNewSolution(Solution parameter) {
        double runningSum = 0;

        int i = Randomizer.intBetween(1,parameter.getStopAtN());

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

    public String setInitialSolution(){

        double doubleFromDomain = Randomizer.doubleBetween(lowerDomain,upperDomain);

        return String.valueOf(doubleFromDomain);
    }

    public List<Solution> getParameters() {
        return parameters;
    }

    public void setParameter(Solution parameter,int index){
        parameters.add(index,parameter);
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

    public int getNumberOfParameters() {
        return numberOfParameters;
    }

    public void setNumberOfParameters(int numberOfParameters) {
        this.numberOfParameters = numberOfParameters;
    }
}
