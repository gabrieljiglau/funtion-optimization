package hillClimbingAndSimulatedAnnealing.functionOptimization.hcAndSa;

public interface EvaluationFunction {


    double evaluateForNewSolution(Solution parameter);

    String setInitialBitStringSolution();

    String getName();
}
