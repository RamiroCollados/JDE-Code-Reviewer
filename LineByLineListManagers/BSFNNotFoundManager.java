
package LineByLineListManagers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class BSFNNotFoundManager {
    
    private final ObservableList<String> bSFNNotFoundList = FXCollections.observableArrayList();

    public ObservableList<String> getbSFNNotFoundList() {
        return bSFNNotFoundList;
    }

  
    private boolean findBSFNNotFoundInString(String codeLine){
        boolean BSFNNotFound=false;
        
        if(codeLine.contains("** Business Function NOT FOUND")){
            BSFNNotFound=true;
        }
        
        return BSFNNotFound;
    }
        
    
    public void writeBSFNNotFoundList(String lineNumber, String codeLine){
        if(findBSFNNotFoundInString(codeLine)){
            bSFNNotFoundList.add("Line:"+lineNumber+"      "+codeLine.trim());
        }    
    }                        
}
