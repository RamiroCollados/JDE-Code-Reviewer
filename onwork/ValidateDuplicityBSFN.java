/*Author: Ramiro Collados
 This class is used to validate duplicity on BSFN. This means some BSFNs could have same parameters without any change 
 beeing called more than once.
 */

package onwork;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

class ValidateDuplicityBSFN {
    //Class Variables
    private final ListView<String> functionsList = new ListView<>();
    private final ObservableList<String> codeList = FXCollections.observableArrayList();


    
    //METHOD: to filter functions list and get the duplicated ones only
    private ObservableList<String> FilterFunctionsList(ListView<String> functionsList){
        ObservableList<String> duppedFunctions = FXCollections.observableArrayList();
        boolean firstDupFound=false;
        
        //loop on full list of functions
        for (int i = 0; i < functionsList.getItems().size(); i++) {
            
            //loop again to compare between them, and add the duplicated ones to the list.
            for (int j = i++; j < functionsList.getItems().size(); j++) {

                if(functionsList.getItems().get(i).equals(functionsList.getItems().get(j))){
                    
                    if(firstDupFound){
                        duppedFunctions.add(functionsList.getItems().get(j));
                    }else{
                        firstDupFound=true;
                        duppedFunctions.add(functionsList.getItems().get(i));
                        duppedFunctions.add(functionsList.getItems().get(j));
                    }               
                }//end if
            }//end for
           
            
            firstDupFound=false;
        }//end for
         
        return duppedFunctions;
    }
   
    //METHOD: Retrieves a full list of function and their parameters below each function name
    private ObservableList<String> GetParameters(ObservableList<String> duppedFunctions){
        
        ObservableList<String> parameters = FXCollections.observableArrayList();
        String functionStrippedLine;
        int codeIndex=0;
        
        // Loop on functions in code list.
        for (int i = 0; i < duppedFunctions.size(); i++) {
            //remove code lines to compare
            functionStrippedLine=duppedFunctions.get(i).substring(10);
            //Compare against code lines to start looping
            while(codeIndex<=codeList.size()){
                if(codeList.get(codeIndex).equals(functionStrippedLine)){
                    //function line was found. Now loop to retrieve all parameters
                    parameters.add(duppedFunctions.get(i));
                    for (int k = codeIndex+1; k < codeList.size(); k++) {
                        if(codeList.get(i).contains(" -> ") || codeList.get(i).contains(" <- ") || codeList.get(i).contains(" <> ")){
                               parameters.add(codeList.get(k)); 
                        }else{
                            parameters.add("separator"); //for better processing
                            break;
                        }
                    }//end for
                }//end if

                //counter stays with value so it does not repeat a bsfn call.
                codeIndex++;
            }//end while
      
        }//end for   
        
        return parameters;
    }
    
    
    //***MAIN METHOD to be called ***//
    public ListView<String> CheckDuplicity (){
        ListView<String> duplicityResultList = new ListView<>();
        ObservableList<String> listToCompare=FXCollections.observableArrayList();
        
        //Call methods to retrieve dupped functions and parameters 
        ObservableList<String> duppedFunctions=FilterFunctionsList(functionsList);
        ObservableList<String> parameters=GetParameters(duppedFunctions);
        
        boolean duplicityFound=false;
        
        int indexOfComparisson=0;
        
        //Loop parameters list
        for (int i = 0; i < parameters.size(); i++) {
            //get first block of parameters
            if(!parameters.get(i).equals("separator") ){
                listToCompare.add(parameters.get(i));
            }else{
                
                //iterate parameters against block
                
                indexOfComparisson=indexOfComparisson+listToCompare.size();
                
                for (int j = indexOfComparisson; j < listToCompare.size(); j++) {
                    //do comparisson
                    if(listToCompare.get(j).equals(parameters.get(j))){
                        duplicityFound=true;
                    }else{
                        duplicityFound=false;
                        break;                        
                    }    
                }//end for  
            }//end if
            
            if(duplicityFound){
                duplicityResultList.getItems().add(parameters.get(i-indexOfComparisson));
            }
            
        } //end for 
        
        return duplicityResultList;       
    } 
    
}
