
package LineByLineListManagers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class ParametersNotPassedManager {
    private  ObservableList<String> parametersNotPassedList = FXCollections.observableArrayList();

    public ObservableList<String> getParametersNotPassedList() {
        return parametersNotPassedList;
    }


    
    private boolean findParametersNotPassedInString(String codeLine){
        boolean parameterNotPassedFound=false;
        
        if((codeLine.contains(" X  TK")||codeLine.contains(" X  BF"))&&!codeLine.contains("UNDEFINED")){
            parameterNotPassedFound=true;
        }
        
        return parameterNotPassedFound;    
    }    
    
    public void writeParametersNotPassedList(String lineNumber, String codeLine){
        if(findParametersNotPassedInString(codeLine)){
            parametersNotPassedList.add("Line:"+lineNumber+"     "+codeLine.trim());
        }
    }
    
}
