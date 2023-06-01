package geneticAlgorithms;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class iteratedHillClimbing {


   /* public static String justClimb(Function f, int lowerBound, int upperBound, int D, int p, int limit) {
        int range = (lowerBound + upperBound) / 2 + 1;

        String node = randomBits(range, new Random());
        String best = node;
        boolean local = true;

        for(int i = 0; i < limit; i++){

            System.out.println("Best solution so far " + node +
                    f.evaluateFunctionForNewVariable(Integer.parseInt(node)));

            ArrayList<Byte> neighbours;
            neighbours = getNeighbours(String.valueOf((byte)decodeString(node)));

            int n1 = selectSolution(lowerBound, upperBound, D, p);
            String xb = String.valueOf(n1);

            byte[] encoded = xb.getBytes(StandardCharsets.UTF_8);

            int randomNum = ThreadLocalRandom.current().nextInt(0, neighbours.size() +1);
            Byte value = neighbours.get(randomNum);

            f.evaluateFunctionForNewVariable(Integer.parseInt(String.valueOf(value)));


            while(local){
                Byte vn = -1;
                for(int j = 0; i < neighbours.size(); j++){
                    if(isBetter(f,neighbours.get(j+1),neighbours.get(j))){
                        vn = neighbours.get(j+1);
                        break;
                    }
                }
                if(f.evaluateFunctionForNewVariable(vn.intValue()) >  f.evaluateFunctionForNewVariable(value.intValue()) ){
                    value = vn;
                } else {
                    local = false;
                }
            }

            if(f.evaluateFunctionForNewVariable(value.intValue()) > f.evaluateFunctionForNewVariable(Integer.parseInt(best))){
                best = value.toString();
            }
        }

        return best;
    }


    public static boolean isBetter(Function f, byte i1, byte i2){
        String s1 = Byte.toString(i1);
        String s2 = Byte.toString(i2);

        int int1 = Integer.parseInt(s1);
        int int2 = Integer.parseInt(s2);

        return f.evaluateFunctionForNewVariable(int1) > f.evaluateFunctionForNewVariable(int2);
    }


    public static ArrayList<Byte> getNeighbours(String input){
        ArrayList<Byte> neighbours = new ArrayList<>();
        neighbours.add(Byte.decode(input));
        byte value;
        byte mask = 1;


        for (int i = 0; i < 8; i++) {
            value = (byte) (Byte.decode(input) ^mask);
            neighbours.add(value);
            mask = (byte) (mask << 1);

        }
        return neighbours;
    }

    public static String intToBinary(int n)
    {
        StringBuilder s = new StringBuilder();
        while (n > 0)
        {
            s.insert(0, ((n % 2) == 0 ? "0" : "1"));
            n = n / 2;
        }
        return s.toString();
    }


    public static int decodeString(String s){
        int a = Integer.parseInt(s,2);
        ByteBuffer bytes = ByteBuffer.allocate(2).putInt(a);

        byte[] array = bytes.array();

        return Arrays.hashCode(array);
    }

    public static String encodeByteArray(byte[] array){

        StringBuilder word = new StringBuilder();
        for (byte b : array) {
            word.append((char) Integer.parseInt(String.valueOf(b), 2));
        }

        System.out.println(word);

        return word.toString();
    }


    public static int selectSolution(int lowerBound,int upperBound,int D,int p){
        int eps = (int) Math.pow(10, -p);

        int range = (lowerBound + upperBound) / 2 + 1;

        int n = (int) ((upperBound - lowerBound) * Math.pow(10, p));

        int value = log2((int) ((range - 1) * Math.pow(10, p)));
        int fractional = 0;
        int ld = value - fractional;

        return ld * D;
    }

    public static String randomBits(int numBits, Random r){
        BigInteger candidate = new BigInteger(numBits,r);

        return candidate.toString();
    }

    public static int log2(int n){
           return (int) ((int) Math.log(n)/Math.log(2));
    }

    */
}
