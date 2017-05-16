
package LineByLineListManagers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class FormsCalledManager {
    
    private ObservableList<String> formsCalledList = FXCollections.observableArrayList();

    public ObservableList<String> getFormsCalledList() {
        return formsCalledList;
    }

    
    
    
    private boolean findFormCalledInString(String codeLine){
        boolean formCalledFound = false;
        
        if(codeLine.contains("Call( App:")){
            formCalledFound = true;
        }
        
        return formCalledFound;
    }
    
    private String formatLineToWriteList (String codeLine){
        
        int index=codeLine.indexOf(",");
        
        codeLine = codeLine.substring(index);
        codeLine = codeLine.replace(")", "");
        codeLine = codeLine.replace(", Form:", "");

        index = codeLine.indexOf(" ");

        codeLine = codeLine.substring(0, index);
        codeLine.trim();
        
        return codeLine;
    }
    
    
    
    public void writeFormsCalledList (String codeLine){
        if(findFormCalledInString(codeLine)){
            if(!formsCalledList.contains(codeLine)){
                codeLine = formatLineToWriteList(codeLine);
                formsCalledList.add(codeLine);
            }
        }
    }
    
}
