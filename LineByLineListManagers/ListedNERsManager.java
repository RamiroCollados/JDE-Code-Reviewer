
package LineByLineListManagers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class ListedNERsManager {
    
    private final ObservableList<String> listedNERsList = FXCollections.observableArrayList();

    public ObservableList<String> getListedNERsList() {
        return listedNERsList;
    }

    
    
    
    private boolean findListedNERsInString(String codeLine){
        boolean listedNERFound = false;
        
        if(codeLine.startsWith("Listing Named ER")){
            listedNERFound = true;
        }
        
        return listedNERFound;
    }
    
    private String formatLineToWriteInList(String codeLine){
        codeLine = codeLine.replace("Listing Named ER for ", "");
        
        return codeLine;
    }
    
    public void writeListedNERsList(String codeLine){
        if(findListedNERsInString(codeLine)){
            codeLine = formatLineToWriteInList(codeLine);
            listedNERsList.add(codeLine);
        }
    }
}
