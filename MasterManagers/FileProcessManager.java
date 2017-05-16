/*
Author: Ramiro Collados 
This class will manage the file information loop.
 */

package MasterManagers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class FileProcessManager {
    private String pathInput = null; 
    private ListMasterManager listMasterManager = new ListMasterManager();
    private ObservableList<String> codeList = FXCollections.observableArrayList();
    private ObservableList<String> codeListWithLineNumbers = FXCollections.observableArrayList();
    private ObservableList<String> comparedCodeList = FXCollections.observableArrayList();

    public FileProcessManager(String pathInput, ListMasterManager listMasterManager) {
        this.pathInput = pathInput;
        this.listMasterManager = listMasterManager;
    }

    public ObservableList<String> getCodeList() {
        return codeList;
    }

    public ObservableList<String> getCodeListWithLineNumbers() {
        return codeListWithLineNumbers;
    }

    
    
    public void initializeCodeLists () throws FileNotFoundException, IOException{
        FileInputStream fstream = new FileInputStream(pathInput);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        
        String codeLine = null; 
        int lineNumber = 0; 
        
        while ((codeLine = br.readLine()) != null){  
            lineNumber++;
            
            codeListWithLineNumbers.add("Line:"+lineNumber+"     "+codeLine);
            codeList.add(codeLine);
        }  
        
        fstream.close();
        br.close();
    }
    
    public void initializeCodeListForCompare(ObservableList<String> codeList) throws FileNotFoundException, IOException{
        FileInputStream fstream = new FileInputStream(pathInput);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        
        String codeLine = null; 
        int lineNumber = 0; 
        
        while ((codeLine = br.readLine()) != null){  
            lineNumber++;
  

            if( !codeList.contains(codeLine) ||
                codeLine.contains("SECTION:") || codeLine.contains("EVENT:") || codeLine.contains("FORM:") || 
                codeLine.contains("CONTROL:")||  codeLine.contains("----------")||  codeLine.contains("==========")|| 
                codeLine.contains("*****")|| codeLine.startsWith("Listing of")){
                
                comparedCodeList.add(codeLine);
                codeListWithLineNumbers.add("Line:"+lineNumber+"     "+codeLine);
            }
        }  
        fstream.close();
        br.close();
        
        this.codeList = comparedCodeList;
    }
    
    
    
    private void processLineByLineLists(){   
        int lineNumber = 0;
        
        for (String codeLine : codeList) {
            lineNumber++;
            listMasterManager.writeLineByLineLists(String.valueOf(lineNumber), codeLine);
        }    
    }
    //A full based list is the one that needs the whole code lines to analyze something.
    private void processFullCodeBasedLists(){
        listMasterManager.writeFullCodeBasedManagerLists(codeList, codeListWithLineNumbers);  
    }
    
    
    public void processListManagers() throws IOException{
        processLineByLineLists();
        processFullCodeBasedLists();
    }
    
    
    
}
