
package LineByLineListManagers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class QueriesInCodeManager {
    private final ObservableList<String> queriesInCodeList = FXCollections.observableArrayList();

    public ObservableList<String> getQueriesInCodeList() {
        return queriesInCodeList;
    }

    
    private boolean findTableQueriesInString(String codeLine){
        boolean tableFound=false;
        
        if(codeLine.contains(".Fetch Single")){
            tableFound=true;
        }
        if(codeLine.contains(".Insert")){
            tableFound=true;
        }
        if(codeLine.contains(".Update")){
            tableFound=true;
        }
        if(codeLine.contains(".Fetch Next")){
            tableFound=true;
        }
        if(codeLine.contains(".Delete")){
            tableFound=true;
        }       
        
        return tableFound;
    }
    
    public void writeQueriesInCodeList (String lineNumber, String codeLine){
        if(findTableQueriesInString(codeLine)){
            queriesInCodeList.add("Line:"+lineNumber+"     "+codeLine.trim());  
        }
    }
}
