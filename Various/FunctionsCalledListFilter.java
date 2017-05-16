/*
Author: Ramiro Collados

This class filters list based on filtering list element selected.
 */

package Various;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FunctionsCalledListFilter {
    
    private ObservableList<String>  list;
    
    private ObservableList<String>  listCopy;

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
                for (int i = 0; i < list.size(); i++) {
                    if(list.get(i).contains(listLine)){
                        filteredsList.add(list.get(i));
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
