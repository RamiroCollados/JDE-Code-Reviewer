
package LineByLineListManagers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class SetUserSelectionManager {
    
    private final ObservableList<String> setUserSelectionList = FXCollections.observableArrayList();

    public ObservableList<String> getSetUserSelectionList() {
        return setUserSelectionList;
    }
    
    
    
    
    //Methods
    
    private boolean findSetUserSelectionsInString(String codeLine){
        boolean flag =false;
        
        if(codeLine.contains("Set User Selection")){
            flag=true;
        }
        if(codeLine.contains("Set Selection Append Flag")){
            flag=true;
        }
        
        return flag;
    }
    
    public void writeSetUserFoundInList (String lineNumber, String codeLine){
        if(findSetUserSelectionsInString(codeLine)){
            setUserSelectionList.add("Line:" + lineNumber + "      " + codeLine.trim());
        }
    }
}
