/*
Author: Ramiro Collados
This class will fill a list with variables not used used in code.
 */
package FullCodeBasedListManagers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class VariablesNotUsedManager {
    private final ObservableList<String> variablesNotUsedList = FXCollections.observableArrayList();
    private ObservableList<String> variablesFoundList = FXCollections.observableArrayList();

    
    public ObservableList<String> getVariablesNotUsedList() {
        return variablesNotUsedList;
    }
    
    public void setVariablesFoundList(ObservableList<String> variablesFoundList) {
        this.variablesFoundList = variablesFoundList;
    }
    
    
    private boolean validateVariableUsage(ObservableList<String> codeList, String variable){
        boolean variableNotUsedFlag = false;
        int numberOfTimesUsed = 0;
        
        for (String codeLine : codeList) {
            if(codeLine.contains(variable) && !codeLine.contains("//")){
                numberOfTimesUsed++;
                
                if(numberOfTimesUsed>1){
                    break;
                }
            }
        }
        
        if(numberOfTimesUsed == 1){
            variableNotUsedFlag = true;
        }
        
        return variableNotUsedFlag;
    }
    
    
    public void writeVariablesNotUsedList(ObservableList<String> codeList){
        
        for (String variable : variablesFoundList) {     
            if(validateVariableUsage(codeList, variable)){
                variablesNotUsedList.add(variable);
            }  
        }  
    }
    
    
}
