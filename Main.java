package geneticAlgorithms;

import greedy.KnapsackItem;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        KnapsackItem i1 = new KnapsackItem(10,5,1);
        KnapsackItem i2 = new KnapsackItem(12,7,2);
        KnapsackItem i3 = new KnapsackItem(11,6,3);
        KnapsackItem i4 = new KnapsackItem(9,3,4);
        KnapsackItem i5 = new KnapsackItem(8,3,5);
        KnapsackItem i6 = new KnapsackItem(25,7,6);
        KnapsackItem i7 = new KnapsackItem(13,4,7);
        KnapsackItem i8 = new KnapsackItem(10,4,8);
        KnapsackItem i9 = new KnapsackItem(6,1,9);
        KnapsackItem i10 = new KnapsackItem(5,2,10);
        KnapsackItem i11 = new KnapsackItem(14,9,11);
        KnapsackItem i12 = new KnapsackItem(14,4,12);

        ArrayList<KnapsackItem> items = new ArrayList<>();
        items.add(i1);
        items.add(i2);
        items.add(i3);
        items.add(i4);
        items.add(i5);
        items.add(i6);
        items.add(i7);
        items.add(i8);
        items.add(i9);
        items.add(i10);
        items.add(i11);
        items.add(i12);


        GeneticAlgorithmEngine knapsackGenetic = new GeneticAlgorithmEngine(12,50,60);

        knapsackGenetic.findOptimalItems(items,11);
    }
}
