
package LineByLineListManagers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class ReportsAndApplCallsManager {
    private  ObservableList<String> reportsAndAppllCalledList = FXCollections.observableArrayList();

    public ObservableList<String> getReportsAndAppllCalledList() {
        return reportsAndAppllCalledList;
    }

    
    
    
    
    
    private boolean findReportAndApplCallsInString (String codeLine){
        boolean callFound=false;
        
        if(codeLine.contains("Call( ")){
            callFound=true;
        }
        
        return callFound;
    }
    
    public void writeReportsAndApplCalledList (String lineNumber, String codeLine){
        if(findReportAndApplCallsInString(codeLine)){
            reportsAndAppllCalledList.add("Line:"+lineNumber+"     "+codeLine.trim());
        }
    }
}
