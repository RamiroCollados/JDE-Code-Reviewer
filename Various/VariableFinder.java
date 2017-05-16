/*
Author: Ramiro Collados
This class is used to list all the VA variables from the code and return that list.
 */

package Various;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class VariableFinder {
    private ObservableList<String> codeList = FXCollections.observableArrayList();
    private ObservableList<String> variablesList = FXCollections.observableArrayList();

    public VariableFinder(ObservableList<String> codeList) {
        this.codeList = codeList;
    }

    
    public ObservableList<String> retrieveVariableList (){
        String[] prefix = {"evt", "sec", "rpt", "frm", "grd"};
        
        for (String codeLine : codeList) {
            if(!codeLine.contains("//") && !codeLine.contains("VA ")){
                
                for (String prefixToFind : prefix) {
                    if(codeLine.contains(prefixToFind)){
                        variablesList.add(codeLine);
                        break;
                    }               
                }
                
            }
        }
        
        return variablesList;
    }
    
}
