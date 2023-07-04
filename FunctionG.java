package geneticAlgorithms;

import java.util.ArrayList;
import java.util.List;

public abstract class FunctionG {

    protected List<Solution> parameters;

    public abstract String setInitialSolution();
    public abstract double evaluateForNewSolution(Solution solution);
    public abstract void addPossibleMutation(Solution solution, double mutationRate);
    public abstract List<Solution> getParameters();
    public abstract void setParameter(Solution parameter, int index);
    public abstract void setNumberOfParameters(int numberOfParameters);
    public abstract List<Solution> initializeParameters(int n);

    protected abstract FunctionG createNextGeneration();
}
