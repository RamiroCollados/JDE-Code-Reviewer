
package LineByLineListManagers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class QueriesToReviewManager {
    
    private final ObservableList<String>  queriesToReviewList = FXCollections.observableArrayList();


    public ObservableList<String> getQueriesToReviewList() {
        return queriesToReviewList;
    }

    
    
    
    private  boolean findQueryToReviewInString (String codeLine){
         boolean queryToRevFound;
         
         ObservableList<String> ForbiddenTables = FXCollections.observableArrayList();
         //add all tables as much as we need
         ForbiddenTables.add("F4211");
         ForbiddenTables.add("F4311");
         ForbiddenTables.add("F4201");
         ForbiddenTables.add("F4301");
         ForbiddenTables.add("F4101");
         ForbiddenTables.add("F0911");
         ForbiddenTables.add("F0901");
         ForbiddenTables.add("F0101");
         ForbiddenTables.add("F0005");
         ForbiddenTables.add("F0411");
         ForbiddenTables.add("F0010");
         ForbiddenTables.add("F0018");
         ForbiddenTables.add("F4102");
         ForbiddenTables.add("F0006");
         ForbiddenTables.add("F4102");
         ForbiddenTables.add("F03012");
         ForbiddenTables.add("F03B11");
         ForbiddenTables.add("F0902");
         ForbiddenTables.add("F42119");
         ForbiddenTables.add("F42199");
         ForbiddenTables.add("F4801");
         ForbiddenTables.add("F03B13");
         ForbiddenTables.add("F41021");
         

        if(codeLine.contains(".Insert")){
            codeLine=codeLine.replace(".Insert","").trim();
        }
        if(codeLine.contains(".Update")){
            codeLine=codeLine.replace(".Update","").trim();
        }
        if(codeLine.contains(".Delete")){
            codeLine=codeLine.replace(".Delete","").trim();
        }    
         
        queryToRevFound = ForbiddenTables.contains(codeLine);
        
        return queryToRevFound;
     }
    
    //needs refactoring . Call it after having all lines written. 
    public void validateAuditFieldsInTables (ObservableList<String> queriesInCode, ObservableList<String> codeLinesListWithLineNumbers){
        //1. USER, 2. UPMJ  3.UPMT  4.PID
        boolean auditFound[]={false, false, false, false};
        int counter;
        String tableQuery;
        String lineNumber;
        
        //Search in QueriesInCode list for querys udpate or insert
        for (String query : queriesInCode) {
            if (query.contains(".Insert") || query.contains(".Update")) {

                //Search in general list that contains all code
                for (int j = 0; j < codeLinesListWithLineNumbers.size(); j++) {

                    tableQuery = query.substring(11);
                    lineNumber = query.substring(5, 10);
                    //if full code line current contains the line searched then loop to find the audit
                    if (codeLinesListWithLineNumbers.get(j).contains(tableQuery) && !codeLinesListWithLineNumbers.get(j).contains("!")
                            && codeLinesListWithLineNumbers.get(j).contains(lineNumber)) {

                        //loop from the j count to search
                        counter = j + 2;
                        while ((codeLinesListWithLineNumbers.get(counter).contains("->") || codeLinesListWithLineNumbers.get(counter).contains("="))
                                && !(auditFound[0] && auditFound[1] && auditFound[2] && auditFound[3])) {

                            if (codeLinesListWithLineNumbers.get(counter).contains("User ID")) {
                                auditFound[0] = true;
                            }
                            if (codeLinesListWithLineNumbers.get(counter).contains("Date - Updated")) {
                                auditFound[1] = true;
                            }
                            if (codeLinesListWithLineNumbers.get(counter).contains("Time - Last Updated")
                                    || codeLinesListWithLineNumbers.get(counter).contains("Time of Day")) {
                                auditFound[2] = true;
                            }
                            if (codeLinesListWithLineNumbers.get(counter).contains("Program ID")) {
                                auditFound[3] = true;
                            }
                            counter++;
                        }

                        //if some audit field missing then review code
                        if (!auditFound[0] || !auditFound[1] || !auditFound[2] || !auditFound[3]) {
                            lineNumber = "Line:" + lineNumber + "     ";
                            queriesToReviewList.add(lineNumber + tableQuery);
                        }

                        auditFound[0] = false;
                        auditFound[1] = false;
                        auditFound[2] = false;
                        auditFound[3] = false;
                    }
                }
            }
        }        
    }
    //needs refactoring . Call it after having all lines written. 
    public void validateOpenCloseInTables(ObservableList<String> codeLinesList){
        String table;
        String tableClose=null;
        char[] tableArrayFormat;
        int tableLineLength;
        int codeLineNumb;
        boolean pairFound;
        
        // Read all code lines to search for Open Close
        for (int i = 0; i < codeLinesList.size(); i++) {
            pairFound = false;
            if(codeLinesList.get(i).contains(".Open")){
                codeLineNumb=i+1;
                table=codeLinesList.get(i);
                tableLineLength=table.length();
                
                tableArrayFormat=table.toCharArray();
                
                //Generate Close line to search later
                for (int s = 0; s < tableLineLength; s++) {
                    if(tableArrayFormat[s]== '.'){
                        tableClose=table.substring(0,s+1);
                        tableClose=tableClose.concat("Close");
                        break;
                    }
                }
                
                //Search for Close pair on code
                for (int j = i; j < codeLinesList.size(); j++) {
                    assert tableClose != null;
                    if(codeLinesList.get(j).contains(tableClose)){
                        pairFound=true;
                    }
                    
                }                
              
                if(!pairFound){
                    queriesToReviewList.add("Line:"+codeLineNumb+"     "+table);
                }        
            }   
        }

        // Read all code lines to search for Close Open
        for (int i = 0; i < codeLinesList.size(); i++) {
            pairFound = false;
            if(codeLinesList.get(i).contains(".Close")){
                codeLineNumb=i+1;
                table=codeLinesList.get(i);
                tableLineLength=table.length();
                
                tableArrayFormat=table.toCharArray();
                
                //Generate Close line to search later
                for (int s = 0; s < tableLineLength; s++) {
                    if(tableArrayFormat[s]== '.'){
                        tableClose=table.substring(0,s+1);
                        tableClose=tableClose.concat("Open");
                        break;
                    }
                }
                
                //Search for Close pair on code
                for (String aCodeLinesList : codeLinesList) {
                    assert tableClose != null;
                    if (aCodeLinesList.contains(tableClose)) {
                        pairFound = true;
                    }

                }                
              
                if(!pairFound){
                    queriesToReviewList.add("Line:"+codeLineNumb+"     "+table);
                }        
            }   
        }
   
    }
    
    
    
    public void writeQueryToReviewList (String lineNumber, String codeLine){
        if(findQueryToReviewInString(codeLine)){
            queriesToReviewList.add("Line:"+lineNumber+"     "+codeLine.trim());  
        }    
    }
    
    
}
