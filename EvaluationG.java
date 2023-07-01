package geneticAlgorithms;

import java.util.List;

public interface EvaluationG {

    void addPossibleMutation(Solution child1,double mutationRate);

    double evaluateForNewSolution(Solution parameter);

    List<Solution> getFunctionParameters();

    void setParameter(Solution parameter, int index);

    int getNumberOfParameters();

    String setInitialSolution();

    List<Solution> initializeParameters(int n);
}
