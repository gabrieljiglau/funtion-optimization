package onlyGeneticAlgorithms;

import java.util.ArrayList;
import java.util.List;

public class FunctionManager {

    private List<FunctionG> functions;

    public FunctionManager(){
        this.functions = new ArrayList<>();
    }

    public void addFunction(FunctionG f){
        this.functions.add(f);
    }

    public List<FunctionG> getFunctions() {
        return functions;
    }

    public void setFunctions(List<FunctionG> functions) {
        this.functions = functions;
    }
}
