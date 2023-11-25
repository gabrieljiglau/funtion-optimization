package hillClimbingAndSimulatedAnnealing.functionOptimization.hcAndSa;

public class Solution {

    private String bigBitString;
    private int numberOfParameters;

    public Solution(String bigBitString, int numberOfParameters){
        this.bigBitString = bigBitString;
        this.numberOfParameters = numberOfParameters;
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

}
