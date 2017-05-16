/*
Author: Ramiro Collados
This class will format the content of a list to avoid duplicate values and stack them
 */

package LineByLineListManagers;

import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class RepeatedFunctionsCalledManager {
    private ObservableList<String>  repeatedFunctionsList = FXCollections.observableArrayList();
    private ObservableList<String> functionsList = FXCollections.observableArrayList();
    private ObservableList<String> functionsListWithoutLineNumbers = FXCollections.observableArrayList();

    public ObservableList<String>  getRepeatedFunctionsList() {
        return repeatedFunctionsList;
    }

    public void setFunctionsList(ObservableList<String> functionsList) {
        this.functionsList = functionsList;
    }
    
    
    
    private void setFunctionsWithoutNumbersList(ObservableList<String> list){
        String trimmedLine=null;
        
        for (int i = 0; i < list.size(); i++) {
            trimmedLine=list.get(i);
            trimmedLine=trimmedLine.substring(11).trim();

            functionsListWithoutLineNumbers.add(trimmedLine);
        }
        
        functionsListWithoutLineNumbers.sorted();
    }
    
    
    public void writeRepeatedFunctionsList (){
        setFunctionsWithoutNumbersList(functionsList);
        
        String fullLineToWrite = null;
        int lineTimes = 0;
        
        repeatedFunctionsList.add("*** All ***");    
        for (String codeLine : functionsListWithoutLineNumbers) {
            
            lineTimes=Collections.frequency(functionsListWithoutLineNumbers, codeLine);
            
            fullLineToWrite = codeLine;
            fullLineToWrite = fullLineToWrite+"  X"+lineTimes;
            fullLineToWrite = fullLineToWrite.trim();
            
            if(!repeatedFunctionsList.contains(fullLineToWrite)){
                repeatedFunctionsList.add(fullLineToWrite);
            }                          

        }    
        
    }
 
}