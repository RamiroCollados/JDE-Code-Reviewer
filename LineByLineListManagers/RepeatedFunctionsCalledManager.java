/*
Author: Ramiro Collados
This class will format the content of a list to avoid duplicate values and stack them
 */

package LineByLineListManagers;

import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class RepeatedFunctionsCalledManager {
    private final ObservableList<String>  repeatedFunctionsList = FXCollections.observableArrayList();
    private ObservableList<String> functionsList = FXCollections.observableArrayList();
    private final ObservableList<String> functionsListWithoutLineNumbers = FXCollections.observableArrayList();

    public ObservableList<String>  getRepeatedFunctionsList() {
        return repeatedFunctionsList;
    }

    public void setFunctionsList(ObservableList<String> functionsList) {
        this.functionsList = functionsList;
    }
    
    
    
    private void setFunctionsWithoutNumbersList(ObservableList<String> list){
        String trimmedLine;

        for (String aList : list) {
            trimmedLine = aList;
            trimmedLine = trimmedLine.substring(11).trim();

            functionsListWithoutLineNumbers.add(trimmedLine);
        }
        
        functionsListWithoutLineNumbers.sorted();
    }
    
    
    public void writeRepeatedFunctionsList (){
        setFunctionsWithoutNumbersList(functionsList);
        
        String fullLineToWrite;
        int lineTimes;
        
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