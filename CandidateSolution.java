package geneticAlgorithms.functionOptimization;

import geneticAlgorithms.Randomizer;
import greedy.KnapsackItem;

import java.util.ArrayList;
import java.util.List;

public class CandidateSolution {

    private List<Boolean> isSelected;
    private List<KnapsackItem> items;
    private int containerCapacity;
    private int calculatedFitness;

    public CandidateSolution(List<KnapsackItem> items,int capacity){
        this.items = items;
        this.containerCapacity = capacity;
        this.isSelected = new ArrayList<>();
        this.calculatedFitness = 0;

        for(int i = 0; i < items.size(); i++){
            isSelected.add(Randomizer.getDoubleFromZeroToOne() <= 0.5);
        }
    }


    public int calculateFitness(){
        int totalValue = 0;

        for(int i = 0; i < items.size(); i++){
            if(isSelected.get(i)){
                totalValue += items.get(i).getValue();
            }
        }

        return totalValue;
    }

    public void addPossibleMutation(double mutationRate){

        for(int i = 0; i < items.size(); i++){
            if(Randomizer.getDoubleFromZeroToOne() < mutationRate){
                setIndividualIsSelected(i,!isSelected.get(i));
            }
        }

        calculateFitness();
    }


    public void setIndividualIsSelected(int index, Boolean b) {
        this.isSelected.add(index,b);
    }

    public int getCalculatedFitness() {
        return calculatedFitness;
    }

    public void setCalculatedFitness(int calculatedFitness) {
        this.calculatedFitness = calculatedFitness;
    }

    public void repair(){ //asta e putin fancy

        int numItems = items.size();
        int numSelected = getSelectedCandidates(isSelected);
        int sizeOfSelected = getSizeOfSelected();

        while(sizeOfSelected > containerCapacity){

            int deselectWhich = Randomizer.intLessThan(numSelected);

            for(int i = 0; i < numItems; i++){
                if(isSelected.get(i)){
                    if(deselectWhich == 0){
                        isSelected.set(i,false);
                        numSelected--;
                        sizeOfSelected = getSizeOfSelected();
                        break;
                    }
                    deselectWhich--;
                }
            }
        }
    }

    private int getSizeOfSelected(){

        int numItems = items.size();
        int totalSize = 0;

        for(int i = 0; i < numItems; i++){
            if(isSelected.get(i)){
                totalSize += items.get(i).getWeight();
            }
        }

        return totalSize;
    }

    private int getSelectedCandidates(List<Boolean> isSelected){

        int count = 0;
        for(int i = 0; i < isSelected.size(); i++){
            if(isSelected.get(i)){
                count++;
            }
        }

        return count;
    }

    public List<KnapsackItem> getItems() {
        return items;
    }

    public void setItems(List<KnapsackItem> items) {
        this.items = items;
    }

    public int getContainerCapacity() {
        return containerCapacity;
    }

    public void setContainerCapacity(int containerCapacity) {
        this.containerCapacity = containerCapacity;
    }

    public List<Boolean> getIsSelected() {
        return isSelected;
    }

    public ArrayList<KnapsackItem> getSelectedItems() {
        ArrayList<KnapsackItem> selectedItems = new ArrayList<>();

        for(int i = 0; i < isSelected.size(); i++){
            boolean currentBoolean = isSelected.get(i);

            if(currentBoolean){
                selectedItems.add(items.get(i));
            }
        }

        return selectedItems;
    }
}
