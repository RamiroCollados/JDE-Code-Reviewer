
package LineByLineListManagers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class FunctionsCalledManager {
    
    private final ObservableList<String> functionsCalledList = FXCollections.observableArrayList();

    public ObservableList<String> getFunctionsCalledList() {
        return functionsCalledList;
    }


    
    
    
    
    
    private boolean findFunctionsCalledInString (String codeLine){
        boolean functionFound=false;
        
        if(!(codeLine.contains("=") || codeLine.contains("If ") || codeLine.contains("While ") || codeLine.equals("Else") || codeLine.contains("(")
                                    || codeLine.contains(")") || codeLine.contains("->") || codeLine.contains("<-") || codeLine.contains("<>") || codeLine.contains(">")|| codeLine.contains("<")
                                    || codeLine.contains("evt_") || codeLine.contains("rpt_")  || codeLine.contains("grd_")|| codeLine.contains("sec_") || codeLine.contains("frm_")|| codeLine.contains(".Open")
                                    || codeLine.contains("End If") || codeLine.contains("End While")|| codeLine.contains("---") || codeLine.contains("===")|| codeLine.contains("***")
                                    || codeLine.isEmpty() || codeLine.contains(".Insert") || codeLine.contains(".Update") || codeLine.contains(".Fetch")|| codeLine.contains("EVENTS")
                                    || codeLine.contains(".Delete")|| codeLine.contains(".Select")|| codeLine.contains("OBJECT:")|| codeLine.contains("EVENT:")|| codeLine.contains(".Close")
                                    || codeLine.contains("OPT:")|| codeLine.contains("/")|| codeLine.contains(":")|| codeLine.contains("UNDEFINED")|| codeLine.contains("!")|| codeLine.contains("Else")
                                    || codeLine.contains(" BC ") || codeLine.contains(" RV ") || codeLine.contains(" FC ") || codeLine.contains(" SL ") || codeLine.contains(" VA ")
                                    || codeLine.contains(" PO ") || codeLine.contains(" PC ") || codeLine.contains(" RC ") || codeLine.contains(" SV ") || codeLine.contains(" GC ")
                                    || codeLine.contains(" GB ") || codeLine.contains(" TV ") || codeLine.contains(" RI ") || codeLine.contains(" FI ") || codeLine.contains(" HC ")
                                    || codeLine.contains(" QC ") || codeLine.contains(" X  ")) ){

            functionFound=true;
        }   
        
        return functionFound;
    }
    
    public void writeFunctionsCalledList (String lineNumber, String codeLine){
        if(findFunctionsCalledInString(codeLine)){
            functionsCalledList.add("Line:"+lineNumber+"     "+codeLine.trim());
        }
    }
}
