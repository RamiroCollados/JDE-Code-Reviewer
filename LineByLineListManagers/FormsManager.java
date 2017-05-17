
package LineByLineListManagers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class FormsManager {
    
    private final ObservableList<String> formsList = FXCollections.observableArrayList();

    public ObservableList<String> getFormsList() {
        return formsList;
    }

    
    
    
    
    private boolean findFormInString(String codeLine){
        boolean formFound = false;
        
        if(codeLine.startsWith("FORM")){
            formFound = true;
        }
        
        return formFound;
    }
    
    private String formatLineToWriteInList(String codeLine){
        codeLine = codeLine.replace("FORM:", "");
        
        return codeLine;
    }
    
    public void writeFormsInCodeList(String codeLine){
        if(findFormInString(codeLine)){
            codeLine = formatLineToWriteInList(codeLine);
            formsList.add(codeLine);
        }
        
    }
}
