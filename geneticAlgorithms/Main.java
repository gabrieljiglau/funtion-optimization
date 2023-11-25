package onlyGeneticAlgorithms;

import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        FunctionManager functionManager = new FunctionManager();

        final int PRECISION = 5; //after decimal point, when converting from binary to decimal
        final int DIMENSIONS = 5; 
        FunctionDeJongG deJongG = new FunctionDeJongG(DIMENSIONS,PRECISION);
        FunctionSchewefelG schewefelG = new FunctionSchewefelG(DIMENSIONS,PRECISION);
        FunctionMichalewiczG michalewiczG = new FunctionMichalewiczG(DIMENSIONS,PRECISION);
        FunctionRastriginG rastriginG = new FunctionRastriginG(DIMENSIONS,PRECISION);

        final int FUNCTION_INDEX_DEJONG = 0;
        final int FUNCTION_INDEX_SCHEWEFEL = 1;
        final int FUNCTION_INDEX_RASTRIGIN = 2;
        final int FUNCTION_INDEX_MICHALEWICZ = 3;

        functionManager.addFunction(deJongG);
        functionManager.addFunction(schewefelG);
        functionManager.addFunction(rastriginG);
        functionManager.addFunction(michalewiczG);

        GeneticMinima geneticAlgorithm = new GeneticMinima(85, 8.7, 0.004,functionManager,FUNCTION_INDEX_SCHEWEFEL,DIMENSIONS,PRECISION);
        
        double startTime = System.currentTimeMillis(); 
        System.out.println(geneticAlgorithm.findMinimum(functionManager,FUNCTION_INDEX_RASTRIGIN,DIMENSIONS));
        long finishTime = System.currentTimeMillis();
        double duration =  ((double)(finishTime - startTime) / 1000);
        System.out.println("Secunde : " + duration);
    
    }

    public static double calculateStandardDeviation(double[] array,double mean,int length) {

        double standardDeviation = 0.0;
        for (double num : array) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return Math.sqrt(standardDeviation / length);
    }
}
