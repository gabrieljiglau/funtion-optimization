package hillClimbingAndSimulatedAnnealing.functionOptimization.hcAndSa;

import java.util.ArrayList;
import java.util.List;

public class BinaryDecimalConvertor {

    public static void main(String[] args) {

    }

    public BinaryDecimalConvertor() {
    }

    public static int getNumberOfBits(double lowerBound, double upperBound, int precision) {

        double numberOfSubIntervals = (upperBound - lowerBound) * Math.pow(10,precision);

        double logeN = Math.log(numberOfSubIntervals);
        double loge2 = Math.log(2);

        return (int) Math.ceil(logeN / loge2);
    }

    public static List<Double> bigBitStringToDecimal(Solution solution, double lowerBound, double upperBound, int precision){

        List<Double> decimalList = new ArrayList<>();
        int bitsPerValue = getNumberOfBits(lowerBound, upperBound, precision);
        String bitString = solution.getBigBitString();

        for (int i = 1; i <= bitString.length(); i++) {

            if( i % bitsPerValue == 0) {
                int startIndex = i - bitsPerValue +1;
                String substring = bitString.substring(startIndex -1, i);
                decimalList.add(bitStringToDecimal(substring, lowerBound, upperBound, precision));
            }
        }

        return decimalList;
    }

    /*public static List<Double> bigBitStringToDecimal(Solution solution, double lowerBound, double upperBound, int precision) {
        List<Double> decimalList = new ArrayList<>();
        int bitsPerValue = getNumberOfBits(lowerBound, upperBound, precision);
        String bitString = solution.getBigBitString();

        for (int i = 1; i <= bitString.length() - bitsPerValue + 1; i++) {
            if (i % bitsPerValue == 0) {
                int startIndex = i - bitsPerValue + 1;
                String substring = bitString.substring(startIndex - 1, i);
                decimalList.add(bitStringToDecimal(substring, lowerBound, upperBound, precision));
            }
        }

        return decimalList;
    }*/

    public static double bitStringToDecimal(String bitString, double lowerBound, double upperBound, int precision) {
        double convertedResult = 0;
        int bitsPerValue = getNumberOfBits(lowerBound, upperBound, precision);

        for (int i = 0; i < bitString.length(); i++) {
            char bit = bitString.charAt(i);
            convertedResult = convertedResult * 2 + Character.getNumericValue(bit);
        }

        // Scale the converted result to the desired range
        convertedResult = convertedResult * (upperBound - lowerBound) / (Math.pow(2, bitsPerValue) - 1);
        convertedResult += lowerBound;

        return convertedResult;
    }

    /*public static double bitStringToDecimal(String bitString, double lowerBound, double upperBound, int dimension, int precision) {
        double convertedResult = 0;

        for (int i = 0; i < bitString.length() - 1; i++) {

            convertedResult *= 2;
            String justOneByte = bitString.substring(i, i + 1);
            convertedResult += Integer.parseInt(justOneByte);

            convertedResult *= (upperBound - lowerBound);
            convertedResult += lowerBound;
        }

        return convertedResult;
    }*/
}
