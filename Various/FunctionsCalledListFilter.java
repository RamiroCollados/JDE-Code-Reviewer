/*
Author: Ramiro Collados

This class filters list based on filtering list element selected.
 */

package Various;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FunctionsCalledListFilter {
    
    private ObservableList<String>  list;
    
    private final ObservableList<String>  listCopy;

    public FunctionsCalledListFilter(ObservableList<String>  list) {
        this.list = list;
        this.listCopy = list;
               
        this.list.sorted();
        this.listCopy.sorted();
    }
   
    
    //function spects selected line to be passed in
    public ObservableList<String> formatList(String listLine) {
        
        if(!listLine.isEmpty()){

            ObservableList<String> filteredsList = FXCollections.observableArrayList();
            
            if(!listLine.equals("*** All ***")){
                int lineLength=listLine.length()-4;
                listLine=listLine.substring(0,lineLength).trim();
                for (String aList : list) {
                    if (aList.contains(listLine)) {
                        filteredsList.add(aList);
                    }
                }         
                list = filteredsList;
                
            }else{
                list = listCopy;
            }    
        } 
        
        return list;
    }
}
