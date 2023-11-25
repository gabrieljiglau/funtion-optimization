package onlyGeneticAlgorithms;

import java.util.List;

public abstract class FunctionG {

    protected List<Solution> solutionList;


    public String setInitialBitStringSolution(double lowerDomain,double upperDomain,int precision,int numberOfParameters){
        StringBuilder sb = new StringBuilder();
        int size = BinaryDecimalConvertor.getNumberOfBits(lowerDomain, upperDomain, precision);

        for (int i = 0; i < size * numberOfParameters; i++) {

            byte b;
            double firstProbability = Randomizer.doubleBetween(0, 1);
            double secondProbability = Randomizer.doubleBetween(0, 1);
            if (firstProbability < secondProbability) {
                b = (byte) 1;
            } else {
                b = (byte) 0;
            }
            sb.append(b);
        }

        return sb.toString();
    }

    public abstract List<Solution> initializeSolutionList(int n);

    public String addPossibleMutation(Solution solution, double mutationRate){
        String bitString = solution.getBigBitString();
        StringBuilder sb = new StringBuilder(bitString);

        for(int i = 0; i < bitString.length(); i++){
            double generatedNumber = Randomizer.getDoubleFromZeroToOne();

            if(generatedNumber < mutationRate) {
                if (sb.charAt(i) == '1'){
                    sb.setCharAt(i,'0');
                } else {
                    sb.setCharAt(i,'1');
                }
            }
        }

        return sb.toString();
    }

    public int getNumberOfParameters(){
        return solutionList.size();
    }

    public abstract double evaluateForNewSolution(Solution solution);
    public abstract List<Solution> getSolutionList();
    public abstract void setSolution(Solution parameter, int index);
    public abstract void setNumberOfSolutions(int numberOfParameters);

    public abstract double getLowerDomain();
    public abstract double getUpperDomain();
    public abstract int getPrecision();
    protected abstract FunctionG createNextGeneration(int numberOfParameters,int precision);

}