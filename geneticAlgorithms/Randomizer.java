package onlyGeneticAlgorithms;

import java.util.Random;

public class Randomizer {

    private static final Random randomizer = new Random();

    public static int intBetween(int lower,int upper){

        return randomizer.nextInt(upper - lower) + lower;
    }

    public static double doubleBetween(double lower,double upper) {

        return lower + (randomizer.nextDouble() * (upper - lower));
    }


    public static int intLessThan(int upper){

        return randomizer.nextInt(upper);
    }


    public static double getDoubleFromZeroToOne(){

        return randomizer.nextDouble();
    }
}
