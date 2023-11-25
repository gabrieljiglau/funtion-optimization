package hillClimbingAndSimulatedAnnealing.functionOptimization.hcAndSa;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        HillClimbing algorithm = new HillClimbing();

        int numberOfParameters = 5; //dimensions for the test function
        final int steepness = 10; //one extra parameter for michalewicz
        int precision = 5; //precision after decimal point when converting

        FunctionDeJong deJong = new FunctionDeJong(numberOfParameters, "DeJong", precision);
        FunctionSchewefel schewefel = new FunctionSchewefel(numberOfParameters, "Schewefel", precision);
        FunctionRastrigin rastrigin = new FunctionRastrigin(numberOfParameters, "Rastrigin", precision);
        FunctionMichalewicz michalewicz = new FunctionMichalewicz(numberOfParameters, steepness, "Michalewicz", precision);

        int type = 3; //type -> 1 : firstImprovement; 2 : bestImprovement; 3 : worstImprovement

        System.out.println(" " + algorithm.findMinimum(rastrigin,type,numberOfParameters,1500) + " ; ");

        HybridisedSimulatedAnnealing annealing = new HybridisedSimulatedAnnealing(numberOfParameters,3);

        long startTime;
        startTime = System.currentTimeMillis();
        double min = annealing.findMinimumAnnealingHybridised(michalewicz);
        long finishTime = System.currentTimeMillis();
        double duration =  ((double)(finishTime - startTime) / 1000);

        System.out.println("Min with hybridised SA " + min + " duration " + duration);
    }
}
