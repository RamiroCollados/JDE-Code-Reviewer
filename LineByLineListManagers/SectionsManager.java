
package LineByLineListManagers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class SectionsManager {
    
    private  ObservableList<String> sectionsInCodeList = FXCollections.observableArrayList();

    public ObservableList<String> getSectionsInCodeList() {
        return sectionsInCodeList;
    }

    
    
    
    private boolean findSectionInString(String codeLine){
        boolean sectionFound = false;
        
        if(codeLine.startsWith("SECTION")){
            sectionFound = true;
        }
        
        return sectionFound;
    }
    
    private String formatLineToWriteInList(String codeLine){
        codeLine = codeLine.replace("SECTION:", "");
        
        return codeLine;
    }
    
    
    
    public void writeSectionsInCodeList (String codeLine){
        if(findSectionInString(codeLine)){
            codeLine = formatLineToWriteInList(codeLine);
            sectionsInCodeList.add(codeLine);
        }
    }
}
