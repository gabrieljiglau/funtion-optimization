package onlyGeneticAlgorithms;

import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        FunctionManager functionManager = new FunctionManager();

        final int PRECISION = 5;
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

        /*GeneticMinima geneticAlgorithm = new GeneticMinima(85, 8.7, 0.004,
                functionManager,FUNCTION_INDEX_SCHEWEFEL,DIMENSIONS,PRECISION);*/

       /* double startTime = System.currentTimeMillis(); // Restart the timer for each run
        System.out.println(geneticAlgorithm.findMinimum(functionManager,FUNCTION_INDEX_RASTRIGIN,DIMENSIONS));
        long finishTime = System.currentTimeMillis();
        double duration =  ((double)(finishTime - startTime) / 1000);
        System.out.println("Secunde : " + duration);*/

        long startTime;
        int maxCount = 30;
        int count = 0;
        double result = 0;
        double totalDuration = 0;
        StringBuilder sb = new StringBuilder(" ");

        double []resultArray = new double[maxCount];
        double []timeArray = new double[maxCount];

        for(count = 0; count < maxCount; count++) {
            startTime = System.currentTimeMillis(); // Restart the timer for each run
            GeneticMinima geneticAlgorithm = new GeneticMinima(85, 8.7, 0.004,
                    functionManager,FUNCTION_INDEX_SCHEWEFEL,DIMENSIONS,PRECISION);

            double min = geneticAlgorithm.findMinimum(functionManager,FUNCTION_INDEX_SCHEWEFEL,DIMENSIONS);
            resultArray[count] = min;

            sb.append(count +1).append(".Minimum : ").append(min);
            long finishTime = System.currentTimeMillis();
            double duration =  ((double)(finishTime - startTime) / 1000);

            timeArray[count] = duration;
            result += min;
            totalDuration += duration;

            sb.append(" duration : ").append(duration).append(" s"); // Print time in seconds
            sb.append("\n");
        }
        try {
            FileWriter writer = new FileWriter("output.txt");
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred for FileWriter");
            e.printStackTrace();
        }

        double stdevResults = calculateStandardDeviation(resultArray,result / maxCount,maxCount);
        double stdevTimes = calculateStandardDeviation(timeArray,totalDuration/maxCount,maxCount);

        System.out.println("Average result : " + result / maxCount + "stdevResults : " + stdevResults);
        System.out.println("Average duration : " + totalDuration / maxCount + " stdevTimes : " + stdevTimes);

    }

    public static double calculateStandardDeviation(double[] array,double mean,int length) {

        double standardDeviation = 0.0;
        for (double num : array) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return Math.sqrt(standardDeviation / length);
    }
}
