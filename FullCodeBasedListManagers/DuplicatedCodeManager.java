package FullCodeBasedListManagers;

import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DuplicatedCodeManager {
    private  ObservableList<String> codeList = FXCollections.observableArrayList();
    private final ObservableList<String> duplicatedCodeList = FXCollections.observableArrayList();
    private final ObservableList<String> codeListToReview = FXCollections.observableArrayList();
    
    private final String[] invalidStrings = {" //","// ","!" , "Else", "End If" , "End While", "***" , "---","===", "EVENT:",
                                        "EVENTS", "SECTION:", "->" , "<-", "<>", "TK ", "FORM:", "OBJECT:", "OPT:", "Listing of",
                                        "GLOBALS:" , " X " , "CONTROL:" , "NAMED ER:"};
    
    public ObservableList<String> getDuplicatedCodeList() {
        return duplicatedCodeList;
    }

    public void setCodeList(ObservableList<String> codeList) {
        this.codeList = codeList;
    }
    

    private boolean validLineFound(String codeLine){
        boolean validLineFlag = true;
        
        for (String invalidString : invalidStrings) {
            if(codeLine.contains(invalidString)){
                validLineFlag = false;
                break;
            }
        }
       
        if(codeLine.isEmpty() || codeLine.startsWith("//") || codeLine.startsWith("rpt_")
           || codeLine.startsWith("evt_") || codeLine.startsWith("grd_") || codeLine.startsWith("sec_")
           || codeLine.startsWith("rpt_") || codeLine.startsWith("frm_") ){
            validLineFlag = false;
        }
            
        return validLineFlag;   
    }
    
    private void prepareCodeListToEvaluate(){
        for (String codeLine : codeList) {
            if(validLineFound(codeLine)){
                codeListToReview.add(codeLine.trim());
            }
        }

    }

    public void writeDuplicatedCodeList (){     
        ObservableList<String> codeProcessedList = FXCollections.observableArrayList();
        int lineNumber = 0;
        int ocurrences;
        String fullLine;
 
        prepareCodeListToEvaluate();
        
        for (String codeLineToValidate : codeListToReview) {
            if(!codeProcessedList.contains(codeLineToValidate)){
                ocurrences = Collections.frequency(codeListToReview, codeLineToValidate);

                if(ocurrences >= 2){
                    for (String codeLine : codeList) {
                        lineNumber++;  
                        if(codeLine.contains(codeLineToValidate)){
                            fullLine = "Line:"+lineNumber+"   "+codeLineToValidate;
                            duplicatedCodeList.add(fullLine);            
                        }       
                    } 
                    lineNumber = 0;
                }
            }
            
            codeProcessedList.add(codeLineToValidate);
        }
        
    }
}
