/*
Author: Ramiro Collados
This class fills a list with variable poorly used. 
 */
package FullCodeBasedListManagers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class VariablesPoorlyUsedManager {
    private final ObservableList<String> variablesPoorlyUsedList = FXCollections.observableArrayList();
    private ObservableList<String> variablesFoundList = FXCollections.observableArrayList();

    public void setVariablesFoundList(ObservableList<String> variablesFoundList) {
        this.variablesFoundList = variablesFoundList;
    }

    public ObservableList<String> getVariablesPoorlyUsedList() {
        return variablesPoorlyUsedList;
    }
    
    //needs refactor. Return an array of int with flags that indicate how was used.
    private int[] returnHowVariableWasUsed(String variable, ObservableList<String> codeList){
        int [] timesUsed = {0,0,0,0,0};
        //position0= if, position1=while, position2=in parameter, position3=out parameter, position4=in out parameter
        // from timesUsed variable
        for (String codeLine : codeList) {
            if(codeLine.contains(variable)){

                int indexStartingVar;
                int indexOfCharacter;
                int indexFull;
                int indexOfCommaBegin;
                int indexOfCommaEnd;
                int VarNameLength;
                String lineToValidate;

                VarNameLength=variable.length();
                indexStartingVar=codeLine.indexOf(variable);
                indexFull=VarNameLength+indexStartingVar;

                lineToValidate=codeLine.substring(indexFull);

                if(lineToValidate.contains("=")){
                   if(lineToValidate.contains("TK ")){
                       timesUsed[3]=1;  //is assigning value
                   }else{
                       timesUsed[2]=1;  //is beeing assigned
                   }      
                }else{
                    if(lineToValidate.contains("<-")){
                        timesUsed[2]=1;
                    }    
                    else{   
                        indexOfCharacter=codeLine.indexOf("=");
                        if(indexOfCharacter!=-1){
                            if(indexOfCharacter<indexStartingVar){
                                    timesUsed[3]=1; // is assigning value to another var
                            }  
                        }else{
                            indexOfCharacter=codeLine.indexOf("->"); 
                            if(indexOfCharacter!=-1){
                                if(indexOfCharacter>indexStartingVar){
                                    timesUsed[3]=1; // is assigning value to another var
                                }    
                            }
                        } 
                    }    
                }
                //For Get system functions that assign a value to variable
                if(codeLine.contains("Get ") && codeLine.contains(",")){
                    timesUsed[2] = 1;
                }
                
                // For system functions parameters passing use the comas to determine if VA is beeing used   
                indexOfCommaBegin=indexStartingVar-5;
                if(indexOfCommaBegin<0){
                    indexOfCommaBegin=0;
                }

                indexOfCommaEnd=indexStartingVar+VarNameLength+1;

                if(indexOfCommaEnd>codeLine.length()){
                    indexOfCommaEnd=codeLine.length();
                }

                if(codeLine.charAt(indexOfCommaBegin)==(',')){
                    timesUsed[3]=1;
                }
                if(codeLine.charAt(indexOfCommaEnd-1)==(',')){
                    timesUsed[3]=1;
                }
                

                indexOfCharacter=codeLine.indexOf("<>");    
                if(indexOfCharacter!=-1){
                    timesUsed[4]=1;
                } 

                indexOfCharacter=codeLine.indexOf("is Like");
                if(indexOfCharacter != -1){
                    timesUsed[3]=1;
                }


                if(codeLine.contains("If ") || codeLine.contains(" Or ")  || codeLine.contains(" And ")
                    || codeLine.contains(" is equal ") || codeLine.contains(" is not equal ")
                    || codeLine.contains(" is less than ") || codeLine.contains(" is greater than ")){
                        timesUsed[0]=1;
                }
                if(codeLine.contains("While ")){
                        timesUsed[1]=1;
                }
            }    
        }
        
        return timesUsed;
    }
    
    private boolean validateVariableUsage (int[] timesUsed){
       boolean wasVariableWronglyUsed = false;
           
        if(timesUsed[2]==1 && timesUsed[0]==0 && timesUsed[1]==0 && timesUsed[3]==0){ // parameter recieved value but was not used on if whiles or assignation
            wasVariableWronglyUsed=true;
        }
        if(timesUsed[3]==1 && timesUsed[2]==0 && timesUsed[4]==0){// parameter assigns value but was never assigned                 
            wasVariableWronglyUsed=true;
        }
        if(timesUsed[0]==1 && timesUsed[1]==1 && timesUsed[2]==0 && timesUsed[4]==0){// Parameter used in condition but never recieved value
            wasVariableWronglyUsed=true;
        }
        if(timesUsed[2]==1 && timesUsed[3]==1 ){
            wasVariableWronglyUsed=false;
        }
        if(timesUsed[4]==1){
            wasVariableWronglyUsed=false;
        }
      
        //if wasVariableWronglyUsed true then variable was missused
        return wasVariableWronglyUsed;  
    }
    
    
    public void writeVariablesPoorlyUsedList (ObservableList<String> codeList){
        int [] timesUsed;
                
        for (String variable : variablesFoundList) {
            
            timesUsed = returnHowVariableWasUsed(variable, codeList);
            
            if(validateVariableUsage(timesUsed)){
               variablesPoorlyUsedList.add(variable);
            }
        }  
    }
    
}
