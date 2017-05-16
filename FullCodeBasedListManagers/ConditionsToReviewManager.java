/*
Author: Ramiro Collados
This class will fill a list with the conditions to review in code.
 */
package FullCodeBasedListManagers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class ConditionsToReviewManager {
    private  ObservableList<String> conditionsToReviewList = FXCollections.observableArrayList();
    private  ObservableList<String> codeList = FXCollections.observableArrayList();
    private  ObservableList<String> conditionsList = FXCollections.observableArrayList();
    private  ObservableList<String> setUserSelectionsList = FXCollections.observableArrayList();
    
    
    public ObservableList<String> getConditionsToReviewList() {
        return conditionsToReviewList;
    }

    public void setCodeList(ObservableList<String> codeList) {
        this.codeList = codeList;
    }

    public void setConditionsList(ObservableList<String> conditionsList) {
        this.conditionsList = conditionsList;
    }

    public void setSetUserSelectionsList(ObservableList<String> setUserSelectionsList) {
        this.setUserSelectionsList = setUserSelectionsList;
    }

    
    
    private void findConditionsInCode (ObservableList<String> codeList){
        for (String codeLine : codeList) {
            if(!codeLine.contains("!") && !codeLine.contains(" //") && !codeLine.contains("// ")){
                if(codeLine.contains("If ")){
                    conditionsList.add(codeLine);
                }
                if(codeLine.contains("While ")){
                    conditionsList.add(codeLine);
                } 
            }
        }
    }
    
    private int findConditionLineNumber (String condition){
        int lineNumber = 0;
        
        for (int i = 0; i < codeList.size(); i++) {
            if(codeList.get(i).equals(condition)){
                lineNumber = i+1;
                break;
            }
        }
        
        return lineNumber;
    }
    
    private ObservableList<String> retrieveVariablesFromCondition(String conditionLine){
        ObservableList<String> variablesInConditionList = FXCollections.observableArrayList();
        
//        conditionLine=conditionLine.substring(11).trim();
        int index1 = conditionLine.indexOf(" ");
        String lineAux=null;
        
        String line = conditionLine.substring(index1, conditionLine.length()).trim();
        String variable=null;

       // first variable in condition, then we will have to search for And or Or 

        variable= line.substring(line.indexOf(" ")+1, line.indexOf(" is "));
           
        line=line.substring(variable.length()+1, line.length());
        variablesInConditionList.add(variable);
        
        index1=line.indexOf(" And ");
        if(index1==-1){
            index1=line.indexOf(" Or ");
        }

        lineAux=line;
        //loop ands
        if(index1!=-1){
            while(line.indexOf(" And ")!=-1){
              line=line.substring(index1, line.length());
              variable=line.substring(4,line.indexOf(" is "));
              variablesInConditionList.add(variable.trim());  
              line=line.substring(4, line.length());
              index1=line.indexOf(" And ");
            }

            line=lineAux;
            //loop ors
            index1=line.indexOf(" Or ");
            while(index1!=-1){                 
              line=line.substring(index1, line.length());
              variable=line.substring(3,line.indexOf(" is "));
              variablesInConditionList.add(variable.trim());        
              line=line.substring(4, line.length());
              index1=line.indexOf(" Or ");
            }             
        } 
        
        return variablesInConditionList;
    }
    
    private String getEndOfCondition(String condition ){ //Finds the suitable End if/End While for that condition based on spaces
        String conditionLimitLine="nothing";
        
        if(condition.contains("If ")){ 
            conditionLimitLine=condition.substring(0, condition.indexOf("If "));
            conditionLimitLine=conditionLimitLine.concat("End If");
        }else{
            if(condition.contains("While ")){
                conditionLimitLine=condition.substring(0, condition.indexOf("While "));
                conditionLimitLine=conditionLimitLine.concat("End While");
            }    
        }

        return conditionLimitLine;
    }
    
    private String getElseOfCondition (String condition){
        String conditionLimitLine="nothing";
                
        if(condition.contains("If")){
            conditionLimitLine=condition.substring(0, condition.indexOf("If"));
            conditionLimitLine=conditionLimitLine.concat("Else");
        }                     

        return conditionLimitLine;
    }
    
    private ObservableList<String> getValidCodeInsideCondition (String condition){
        ObservableList<String> codeLinesInsideConditionList = FXCollections.observableArrayList();
        String endOfCondition = null;
        String elseOfCondition = null; 
       
        for (int i = 0; i < codeList.size(); i++) {

           if(codeList.get(i).contains(condition)){
               for (int j = i+1; j < codeList.size(); j++) {
                    endOfCondition = getEndOfCondition(condition);
                    elseOfCondition = getElseOfCondition(condition);
                   
                    if(!codeList.get(j).contains(endOfCondition) && !codeList.get(j).contains("!") 
//                      && !codeList.get(j).contains(elseOfCondition) 
                      && (!codeList.get(j).contains("// ") || !codeList.get(j).contains(" //"))){
                        codeLinesInsideConditionList.add(codeList.get(j));
                    }                                      
                    
                    if(codeList.get(j).startsWith(endOfCondition)){
                        break;
                    }                     
                }
            }
        }    
        
        return codeLinesInsideConditionList;
    }
    
    
    
    private void validateSetUserVariables() {
        //Validates if variables used in Set User Selections are used in conditions
        int index1 = 0;
        int index2 = 0;
        int lineNumber = 0;
        String variable = null;
        String fullLine = null;
               
        
        for (String setUserSelectionLine : setUserSelectionsList) {
            if(!setUserSelectionLine.contains("Append Flag")){
                index1 = setUserSelectionLine.indexOf("(")+1 ;
                index2 = setUserSelectionLine.indexOf("<")-2;
                variable= setUserSelectionLine.substring(index1, index2);

                for (String condition : conditionsList) {
                    if(condition.contains(variable)){
                        lineNumber = findConditionLineNumber(condition);
                        fullLine = "Line:"+lineNumber+"   "+ condition.trim()+"    - > Variable: "+ variable +" used in Set User Selection before. Analyze.";
                        if(!conditionsToReviewList.contains(fullLine)){
                            conditionsToReviewList.add(fullLine);
                        }    
                    }
                }
            }
        }
    }
    
    private void validateCommentedCodeInConditions(){
        ObservableList<String> codeInConditionList = FXCollections.observableArrayList(); 
        int conditionLineNumber = 0;
        
        for (String condition : conditionsList){
            codeInConditionList = getValidCodeInsideCondition(condition);
            
            if(codeInConditionList.isEmpty() || (codeInConditionList.get(0).contains("Else") && codeInConditionList.size()==1)){
                conditionLineNumber = findConditionLineNumber(condition);
                condition = "Line:"+conditionLineNumber+"   "+ condition.trim();
                conditionsToReviewList.add(condition+"  >>>>> Code inside condition might be commented out");
            }      
            
            codeInConditionList.clear();
        }
    }
    
    private void validateOnlyElseCode(){
        ObservableList<String> codeInConditionList = FXCollections.observableArrayList(); 
        int conditionLineNumber = 0;
        String fullLine = null;
        
        for (String condition : conditionsList){
            if(condition.contains("If ")){
                codeInConditionList = getValidCodeInsideCondition(condition);
            
                if(codeInConditionList.size()>0){
                    if(codeInConditionList.get(0).contains("Else")){
                        conditionLineNumber = findConditionLineNumber(condition);
                        fullLine = "Line:"+conditionLineNumber+"   "+ condition.trim()+"  >>>>> Invalid usage of Else";
                        if(!conditionsToReviewList.contains(fullLine)){
                            conditionsToReviewList.add(fullLine);
                        }
                    }
                }
                codeInConditionList.clear(); 
            }    
        }
    }
    
    private void validateWhileLoop(){ 
        ObservableList<String> codeInConditionList = FXCollections.observableArrayList(); 
        ObservableList<String> variablesInCondition = FXCollections.observableArrayList(); 
        
        boolean fetchFoundFlag = false;
        boolean variableUsedFlag = false;
        int conditionLineNumber = 0;
        String fullLine = null; 
        
        for (String condition : conditionsList){   
            if(condition.contains("While ")){
                codeInConditionList = getValidCodeInsideCondition(condition);
                variablesInCondition = retrieveVariablesFromCondition(condition);
                
                for (String variableInCondition : variablesInCondition) {
                    if(variableInCondition.contains("File_IO_Status")){
                        for (String codeLine : codeInConditionList) {
                            if(codeLine.contains(".Fetch Single.")|| codeLine.contains(".Fetch Next")){
                                fetchFoundFlag = true;
                            } 
                        }
                        if(!fetchFoundFlag){
                            conditionLineNumber = findConditionLineNumber(condition);
                            fullLine = "Line:"+conditionLineNumber+"   "+ condition.trim()+"  >>>>> Wrong usage of variable "+variableInCondition+" in condition";
                            conditionsToReviewList.add(fullLine);
                        }else{
                            break;
                        }
                    }else{ //other variables that are not IO Status ones
                        for (String codeLine : codeInConditionList) {
                            if(codeLine.contains(variableInCondition)){
                                variableUsedFlag = true;
                            } 
                        }
                        if(!variableUsedFlag){
                            conditionLineNumber = findConditionLineNumber(condition);
                            fullLine = "Line:"+conditionLineNumber+"   "+ condition.trim()+"  >>>>> Wrong usage of variable "+variableInCondition+" in condition";
                            conditionsToReviewList.add(fullLine);
                        }else{
                            break;
                        } 
                    }                             
                }
            }
        }     
    }
    
    
    public void writeConditionsToReviewList(){ 
        findConditionsInCode(codeList);
        validateSetUserVariables();
        validateCommentedCodeInConditions();
        validateOnlyElseCode();
        validateWhileLoop();        
    }
}
