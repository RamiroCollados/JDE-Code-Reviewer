
package LineByLineListManagers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class MissingNamesManager {
    
    private ObservableList<String>  missingNamesList = FXCollections.observableArrayList();

    public ObservableList<String> getMissingNamesList() {
        return missingNamesList;
    }

    
    
    //METHODS
    private boolean findMissingNamesInString(String codeLine){
        boolean missingNamesFound=false;
        if(codeLine.contains("MISSING_NAME")){
           missingNamesFound=true;                
        }
        return missingNamesFound;
    }
    
    public void writeMissingNamesList(String lineNumber, String codeLine){
        if(findMissingNamesInString(codeLine)){
            missingNamesList.add("Line:"+lineNumber+"     "+codeLine.trim());
        }
    }
}
