/*
Author: Ramiro Collados
This class validates the variable names and return a list of poorly named variables.
 */
package FullCodeBasedListManagers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class VariablesPoorlyNamedManager {
    private ObservableList<String> variablesPoorlyNamedList = FXCollections.observableArrayList();
    private ObservableList<String> variablesFoundList = FXCollections.observableArrayList();

    
    public ObservableList<String> getVariablesPoorlyNamedList() {
        return variablesPoorlyNamedList;
    }

    public void setVariablesFoundList(ObservableList<String> variablesFoundList) {
        this.variablesFoundList = variablesFoundList;
    }
    
    
    
    //Returns false if wrong name found
    private boolean validateVariableName (String variable){ 
        boolean flag=true;
        int length= variable.length();
        char[] characters = variable.toCharArray();
        String[] varPrefix ={"c", "id", "sz", "mn", "jd"};
        String Prefix;
        int counter=0;
        
        Character ch = ' ';
        
        //validate first _
        if(characters[3]!='_'){
                flag=false;            
        }
        
        //validate prefixes
        Prefix=String.format("%s%s", characters[4], characters[5]);//valueOf(characters[4]+characters[5]);
        
        for (int i = 0; i < 5; i++) {
            if(Prefix.contains(varPrefix[i])){
                flag=true;
                 break;
            }else{
                flag=false;  
            }           
        }
            
        //validate last _ and DTAI    
        if(flag){
            //validate Upper Case start
            if(characters[4]=='c'){
                if(!Character.isUpperCase(characters[5])){
                    flag=false;
                }
            }else{
                if(!Character.isUpperCase(characters[6])){
                    flag=false;
                }
            }
            
            if(flag){
                for (int i = 6; i < length; i++) {
                    if(characters[i]=='_'){
                        flag=true;
                        for (int  j=i; j < length; j++) {
                            if(Character.isUpperCase(characters[j])) {
                                flag=true;  
                            }else{
                                if(!Character.isDigit((characters[j])))
                                    flag=false;
                            } 
                            counter=j;  
                        }    
                        i=counter;
                    }else{
                        flag=false;
                    }                  
                }  
            }    
        }
        
        return flag;
    }  
    
    
    
    public void writePoorlyNamedVariablesList(){
        
        for (String variable : variablesFoundList) {
            if(!validateVariableName(variable)){
                variablesPoorlyNamedList.add(variable);
            }
        }
        
    }
}
