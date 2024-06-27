package onlyGeneticAlgorithms;

public class Solution {

    private String bigBitString;
    private int numberOfParameters;
    private double mutationRate;
    private double fitnessScore;

    public Solution(String bigBitString, int numberOfParameters, double mutationRate){
        this.bigBitString = bigBitString;
        this.numberOfParameters = numberOfParameters;
        this.mutationRate = mutationRate;
    }

    public Solution(){
        this.bigBitString = " ";
        this.numberOfParameters = 0;
    }

    public int getNumberOfParameters() {
        return numberOfParameters;
    }

    public void setNumberOfParameters(int numberOfParameters) {
        this.numberOfParameters = numberOfParameters;
    }

    public String getBigBitString() {
        return bigBitString;
    }

    public void setBigBitString(String bigBitString) {
        this.bigBitString = bigBitString;
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    public double getFitnessScore() {
        return fitnessScore;
    }

    public void setFitnessScore(double fitnessScore) {
        this.fitnessScore = fitnessScore;
    }
}

