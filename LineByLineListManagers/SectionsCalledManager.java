
package LineByLineListManagers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class SectionsCalledManager {
    
    private  ObservableList<String> sectionsCalledList = FXCollections.observableArrayList();

    public ObservableList<String> getSectionsCalledList() {
        return sectionsCalledList;
    }

    
    
    
    private boolean findDoCustomSectionInString(String codeLine){
        boolean doCustomSectionFound = false;
        
        if(codeLine.contains("Do Custom Section")){
            doCustomSectionFound = true;
        }     
        
        return doCustomSectionFound;
    }
    
    
    private String formatLineToWriteList (String codeLine){
        codeLine = codeLine.substring(codeLine.indexOf("(") + 1, codeLine.indexOf(")"));
        codeLine = codeLine.replace("RS ", "");
        
        return codeLine;
    }

    
    public void writeSectionsCalledList (String codeLine){
        if(findDoCustomSectionInString(codeLine)){
            codeLine = formatLineToWriteList(codeLine);
            if(!sectionsCalledList.contains(codeLine)){       
                sectionsCalledList.add(codeLine);
            }
        }
    }
    
}
