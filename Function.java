package geneticAlgorithms;
import java.util.ArrayList;
import java.util.List;

public class Function {

    private int stopAtN;
    private int functionNumber;
    private List<ParameterDeJong> parametersJong;
    private double evaluatedSum;
    private ParameterDeJong solution;
    private final double lowerDomainJ = -5.12;
    private final double upperDomainJ = 5.12;

    private List<ParameterSchewefel> parametersSchewefel;
    private final double lowerDomainS = -500;
    private final double upperDomainS = 500;

    private List<ParameterRastrigin> parametersRastrigin;
    private final double lowerDomainR = -5.12;
    private final double upperDomainR = 5.12;

    private List<ParameterMichalewicz> parameterMichalewicz;
    private final double lowerDomainM = 0;
    private final double upperDomainM = Math.PI;


    public Function(int stopAtN,int functionNumber){
        if(functionNumber == 1) {
            this.parametersJong = initializeParametersJong(stopAtN);
        } else if(functionNumber == 2) {
            this.parametersSchewefel = initializeParametersSchewefel(stopAtN);
        } else if(functionNumber == 3){
            this.parametersRastrigin = initializeParametersRastrigin(stopAtN);
        } else if(functionNumber == 4){
            this.parameterMichalewicz = initializeParametersMichalewicz(stopAtN);
        }
        this.evaluatedSum = 0;
        this.functionNumber = functionNumber;
        this.stopAtN = stopAtN;
    }

    public List<ParameterRastrigin> initializeParametersRastrigin(int n) {

        List<ParameterRastrigin> initializedParameters = new ArrayList<>();

        for (int i = 0; i < n; i++) {

            double d1 = Randomizer.getDoubleFromZeroToOne();
            int oneOrNegativeOne = 1;

            double doubleFromDomain = -lowerDomainR + (upperDomainR - (lowerDomainR)) * Randomizer.getDoubleFromZeroToOne();
            double coefficient;

            if (d1 <= 0.5) {
                coefficient = -oneOrNegativeOne * doubleFromDomain;
            } else {
                coefficient = doubleFromDomain;
            }

            ParameterRastrigin toAdd = new ParameterRastrigin(coefficient,n);
            initializedParameters.add(toAdd);
        }

        return initializedParameters;
    }

    public List<ParameterMichalewicz> initializeParametersMichalewicz(int n){

        List<ParameterMichalewicz> initializedParameters = new ArrayList<>();

        for (int i = 0; i < n; i++) {

            double d1 = Randomizer.getDoubleFromZeroToOne();
            int oneOrNegativeOne = 1;

            double doubleFromDomain = -lowerDomainM + (upperDomainM - (lowerDomainM)) * Randomizer.getDoubleFromZeroToOne();
            double coefficient;

            if (d1 <= 0.5) {
                coefficient = -oneOrNegativeOne * doubleFromDomain;
            } else {
                coefficient = doubleFromDomain;
            }

            ParameterMichalewicz toAdd = new ParameterMichalewicz(coefficient);
            initializedParameters.add(toAdd);
        }

        return initializedParameters;
    }

    public List<ParameterDeJong> initializeParametersJong(int n){

        List<ParameterDeJong> initializedParameters = new ArrayList<>();

        for(int i = 0; i < n; i++){

            double d1 = Randomizer.getDoubleFromZeroToOne();
            int oneOrNegativeOne = 1;

            double doubleFromDomain = -lowerDomainJ + (upperDomainJ - (lowerDomainJ)) * Randomizer.getDoubleFromZeroToOne();
            double coefficient;

            if(d1 <= 0.5) {
                coefficient = -oneOrNegativeOne * doubleFromDomain;
            } else {
                coefficient = doubleFromDomain;
            }

            ParameterDeJong toAdd = new ParameterDeJong(coefficient * coefficient);
            initializedParameters.add(toAdd);
        }

        return initializedParameters;
    }


    public List<ParameterSchewefel> initializeParametersSchewefel(int n){

        List<ParameterSchewefel> initializedParameters = new ArrayList<>();

        for(int i = 0; i < n; i++){

            double d1 = Randomizer.getDoubleFromZeroToOne();
            int oneOrNegativeOne = 1;

            double doubleFromDomain = -lowerDomainS + (upperDomainS
                                            - (lowerDomainS)) * Randomizer.getDoubleFromZeroToOne();
            double coefficient;

            if(d1 <= 0.5) {
                coefficient = -oneOrNegativeOne * doubleFromDomain;
            } else {
                coefficient = doubleFromDomain;
            }

            ParameterSchewefel toAdd = new ParameterSchewefel(coefficient);
            initializedParameters.add(toAdd);
        }

        return initializedParameters;
    }

    public ParameterRastrigin setInitialSolutionRastrigin(){

        double d1 = Randomizer.getDoubleFromZeroToOne();
        int oneOrNegativeOne = 1;

        double doubleFromDomain =  Randomizer.doubleBetween(lowerDomainR,upperDomainR);
        double coefficient;

        if(d1 <= 0.5) {
            coefficient = -oneOrNegativeOne * doubleFromDomain;
        } else {
            coefficient = doubleFromDomain;
        }

        return new ParameterRastrigin(coefficient,getStopAtN());
    }

    public ParameterMichalewicz setInitialSolutionMichalewicz(){

        double d1 = Randomizer.getDoubleFromZeroToOne();
        int oneOrNegativeOne = 1;

        double doubleFromDomain = Randomizer.doubleBetween(lowerDomainM, upperDomainM);
        double coefficient;

        if (d1 <= 0.5) {
            coefficient = -oneOrNegativeOne * doubleFromDomain;
        } else {
            coefficient = doubleFromDomain;
        }

        return new ParameterMichalewicz(coefficient);

    }

    public ParameterDeJong setInitialSolutionJong(){

        double d1 = Randomizer.getDoubleFromZeroToOne();
        int oneOrNegativeOne = 1;

        double doubleFromDomain =  Randomizer.doubleBetween(lowerDomainJ, upperDomainJ);
        double coefficient;

        if(d1 <= 0.5) {
            coefficient = -oneOrNegativeOne * doubleFromDomain;
        } else {
            coefficient = doubleFromDomain;
        }

        return new ParameterDeJong(coefficient);
    }

    public double evaluateFunctionForNewVariableJ(double newVariable){
        double runningSum = 0;
        for(ParameterDeJong p : parametersJong){
            p.setX(newVariable);
            runningSum += Math.pow((p.getCoefficient() * p.getX()),p.getPower());
        }

        return runningSum;
    }

    public double evaluateFunctionForNewVariableM(double newVariable){
        double runningSum = 0;

        for(ParameterMichalewicz p : parameterMichalewicz){
            double firstSin = Math.sin(Math.toRadians(newVariable));
            double secondSin = Math.pow(Math.sin((p.getI() * newVariable * newVariable)/Math.PI),2 * p.getM());

            double coefficient = firstSin * secondSin;
            p.setCoefficient(coefficient);
            runningSum -= p.getCoefficient();
        }

        return runningSum;
    }

    public ParameterSchewefel setInitialSolutionSchewefel(){

        double lower = lowerDomainS;
        double upper = upperDomainS;

        double d1 = Randomizer.getDoubleFromZeroToOne();
        int oneOrNegativeOne = 1;

        double doubleFromDomain =  Randomizer.doubleBetween(lower, upper);
        double coefficient;

        if(d1 <= 0.5) {
            coefficient = -oneOrNegativeOne * doubleFromDomain;
        } else {
            coefficient = doubleFromDomain;
        }

        return new ParameterSchewefel(coefficient);
    }

    public double evaluateFunctionForNewVariableS(double newVariable){
        double runningSum = 0;
        double specificRadians = Math.toRadians(newVariable);
        double secondCoefficient = Math.sin(Math.sqrt((Math.abs(specificRadians))));

        double coefficient = -newVariable * secondCoefficient;
        for(ParameterSchewefel p : parametersSchewefel){
            p.setCoefficient(coefficient);
            runningSum += p.getCoefficient();
        }

        return runningSum;
    }

    public double evaluateFunctionForNewVariableR(double newVariable){
        double runningSum = 0;
        double specificRadians = Math.toRadians(2 * newVariable * Math.PI);
        double coefficient = newVariable * newVariable  - 10 * Math.cos(specificRadians);

        for(ParameterRastrigin p : parametersRastrigin){
            p.setCoefficient(coefficient);
            runningSum += p.getCoefficient();
        }

        return 10 * getStopAtN() + runningSum;
    }

    public List<ParameterSchewefel> getParametersSchewefel() {
        return parametersSchewefel;
    }

    public void setParametersSchewefel(List<ParameterSchewefel> parameters) {
        this.parametersSchewefel = parameters;
    }

    public double getUpperDomainS() {
        return upperDomainS;
    }

    public double getLowerDomainS() {
        return lowerDomainS;
    }

    public int getFunctionNumber() {
        return functionNumber;
    }

    public void setFunctionNumber(int functionNumber) {
        this.functionNumber = functionNumber;
    }

    public List<ParameterDeJong> getParametersJong() {
        return parametersJong;
    }

    public void setParametersJong(List<ParameterDeJong> parametersJong) {
        this.parametersJong = parametersJong;
    }

    public double getEvaluatedSum() {
        return evaluatedSum;
    }

    public void setEvaluatedSum(double evaluatedSum) {
        this.evaluatedSum = evaluatedSum;
    }

    public int getStopAtN() {
        return stopAtN;
    }

    public void setStopAtN(int stopAtN) {
        this.stopAtN = stopAtN;
    }

    public List<ParameterRastrigin> getParametersRastrigin() {
        return parametersRastrigin;
    }

    public void setParametersRastrigin(List<ParameterRastrigin> parametersRastrigin) {
        this.parametersRastrigin = parametersRastrigin;
    }

    public double getLowerDomainR() {
        return lowerDomainR;
    }

    public double getUpperDomainR() {
        return upperDomainR;
    }

    public ParameterDeJong getSolution() {
        return solution;
    }

    public void setSolution(ParameterDeJong solution) {
        this.solution = solution;
    }

    public double getLowerDomainJ() {
        return lowerDomainJ;
    }


    public double getUpperDomainJ() {
        return upperDomainJ;
    }

    public List<ParameterMichalewicz> getParameterMichalewicz() {
        return parameterMichalewicz;
    }

    public void setParameterMichalewicz(List<ParameterMichalewicz> parameterMichalewicz) {
        this.parameterMichalewicz = parameterMichalewicz;
    }

    public double getLowerDomainM() {
        return lowerDomainM;
    }

    public double getUpperDomainM() {
        return upperDomainM;
    }
}
