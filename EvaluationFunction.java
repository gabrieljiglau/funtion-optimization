package geneticAlgorithms;

import java.util.List;

public interface EvaluationFunction {

    double evaluateForNewSolution(Solution parameter);

    String setInitialSolution();

    List<Solution> initializeParameters(int n);


}
