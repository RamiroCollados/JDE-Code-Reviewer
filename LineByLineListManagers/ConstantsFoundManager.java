
package LineByLineListManagers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class ConstantsFoundManager {
    
    private ObservableList<String> constantsFoundList = FXCollections.observableArrayList();

    public ObservableList<String> getConstantsFoundList() {
        return constantsFoundList;
    }

    
    
    
    //Methods
    
    private boolean findConstantsInString(String line){
        boolean flag=false;
        int position1=0;
        int position2=0;
        String lineToEvaluate=null;
        char[] lineToEvaluateArray=null;
        String constantFound=null;
        
        int count = 0;
        
        if(line.contains("\"")){
               
            for (char ch: line.toCharArray()) { 
                if (ch == '\"') {
                    count++;
                }
            }

            if(count<=2){
                if(line.contains("\"1")||line.contains("\"0") ||line.contains("\"2") ||line.contains("\"3") ||line.contains("\"4") ||line.contains("\"5") ||line.contains("\"6")
                         ||line.contains("\"7") ||line.contains("\"8") ||line.contains("\"9")||line.contains("\"R")||line.contains("\"W")||line.contains("\"C")
                        ||line.contains("\"S")||line.contains("\"N")||line.contains("\"Y")||line.contains("\"N")||line.contains("\"/")
                        ||line.contains("\"\"")){
                    flag=false;
                }else{
                    flag=true;
                }
                
            }else{
                if(count>2){


                    position1=line.indexOf("\"");                 
                    lineToEvaluate=line.substring(position1);
                    lineToEvaluateArray=lineToEvaluate.toCharArray();
                    
                    while(lineToEvaluate.contains("\"") && !flag){
                        
                        position2=0;
                        
                        for (int i = 1; i < lineToEvaluate.length(); i++) {
                            if(lineToEvaluateArray[i]!='\"'){
                                position2++;
                            }else{
                                position2=position2+3;
                                break;
                            }
                        }
                        if(position2>lineToEvaluate.length()){
                            position2=lineToEvaluate.length();
                        }
                        
                        constantFound=lineToEvaluate.substring(0,position2);

                        if(constantFound.contains("\"1")||constantFound.contains("\"0")||constantFound.contains("\"2")||constantFound.contains("\"3")||constantFound.contains("\"4")
                                ||constantFound.contains("\"5")||constantFound.contains("\"6")||constantFound.contains("\"7")||constantFound.contains("\"8")||constantFound.contains("\"9")
                                ||constantFound.contains("\"S")||constantFound.contains("\"N")||constantFound.contains("\"Y")||constantFound.contains("\"R")||constantFound.contains("\"W")
                                ||constantFound.contains("\"C")||constantFound.contains("\"/")
                                ||constantFound.contains("\"N")){
                            flag=false;
                        }else{
                            flag=true;
                                
                        }
                        
                        lineToEvaluate=lineToEvaluate.substring(position2,lineToEvaluate.length());
                        position1=lineToEvaluate.indexOf("\"");
                        try {                            
                            lineToEvaluate=lineToEvaluate.substring(position1,lineToEvaluate.length());
                            lineToEvaluateArray=lineToEvaluate.toCharArray();
                        } catch (Exception e) {   
                        }
 
                    }
                    
                }
            }

        }
        return flag;
    }
   
    
    public void writeConstantFoundInList(String lineNumber, String codeLine){
        if(findConstantsInString(codeLine)){
            constantsFoundList.add("Line:"+lineNumber+"     "+codeLine.trim());
        }
    }    
}
