package geneticAlgorithms;

import greedy.KnapsackItem;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {


        FunctionManager functionManager = new FunctionManager();

        final int NUMBER_OF_PARAMETERS = 30;
        FunctionDeJongG deJongG = new FunctionDeJongG(NUMBER_OF_PARAMETERS);
        FunctionSchewefelG schewefelG = new FunctionSchewefelG(NUMBER_OF_PARAMETERS);
        FunctionMichalewiczG michalewiczG = new FunctionMichalewiczG(NUMBER_OF_PARAMETERS);
        FunctionRastriginG rastriginG = new FunctionRastriginG(NUMBER_OF_PARAMETERS);

        final int FUNCTION_INDEX_DEJONG = 0;
        final int FUNCTION_INDEX_SCHEWEFEl = 1;
        final int FUNCTION_INDEX_MICHALEWICZ = 2;
        final int FUNCTION_INDEX_RASTRIGIN = 3;

        functionManager.addFunction(deJongG);
        functionManager.addFunction(schewefelG);
        functionManager.addFunction(michalewiczG);
        functionManager.addFunction(rastriginG);

        GeneticMinima geneticAlgorithm = new GeneticMinima(400, 0.8, 0.16,
                                                            functionManager, FUNCTION_INDEX_DEJONG);

        long startTime = System.nanoTime();
        System.out.println("Minima is " + geneticAlgorithm.findMinimum(functionManager,
                FUNCTION_INDEX_DEJONG, NUMBER_OF_PARAMETERS));
        long finishTime = System.nanoTime();
        long duration = (finishTime - startTime) / 1000000;

        System.out.println("********************************");
        System.out.println("Total duration for getting the minValue of the function is " + duration + " miliseconds");

    }
}
