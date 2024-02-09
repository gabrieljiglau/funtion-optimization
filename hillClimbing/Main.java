package hillClimbingAndSimulatedAnnealing.functionOptimization.hcAndSa;

import java.util.List;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(SimulatedAnnealing.class.getName());

    public static void main(String[] args) {

        int numberOfParameters = 30; //dimensions for the test function
        final int steepness = 10; //one extra parameter for michalewicz
        int precision = 5; //precision after decimal point when converting

        FunctionDeJong deJong = new FunctionDeJong(numberOfParameters, "DeJong", precision);
        FunctionSchewefel schewefel = new FunctionSchewefel(numberOfParameters, "Schewefel", precision);
        FunctionRastrigin rastrigin = new FunctionRastrigin(numberOfParameters, "Rastrigin", precision);
        FunctionMichalewicz michalewicz = new FunctionMichalewicz(numberOfParameters, steepness, "Michalewicz", precision);

        int type = 3; //type -> 1 : firstImprovement; 2 : bestImprovement; 3 : worstImprovement

        //LOGGER.info(" " + algorithm.findMinimum(rastrigin,type,numberOfParameters,1500) + " ; ");

        SimulatedAnnealing annealing = new SimulatedAnnealing(numberOfParameters,20);

        long startTime;
        startTime = System.currentTimeMillis();
        double min = annealing.findMinimumAnnealingHybridised(schewefel,15000,0.001);
        long finishTime = System.currentTimeMillis();
        double duration =  ((double)(finishTime - startTime) / 1000);

        LOGGER.info("Min with hybridised SA " + min + " duration " + duration);
    }
}
