
package LineByLineListManagers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class ConditionsManager {
    private final ObservableList<String> conditionsList = FXCollections.observableArrayList();

    public ObservableList<String> getConditionsList() {
        return conditionsList;
    }
    
     
    private boolean findConditionInString (String codeLine){
        boolean conditionFound=false;
        
        if(codeLine.contains("If ") && !codeLine.contains("End")){
            conditionFound=true;
        }
        if(codeLine.contains("While ")){
            conditionFound=true;
        }       
        
        return conditionFound;
    }
    
    public void writeConditionsFoundInList(String lineNumber, String codeLine){
        if(findConditionInString(codeLine)){
            conditionsList.add("Line:"+lineNumber+"     "+codeLine.trim());
        }
    }
}
