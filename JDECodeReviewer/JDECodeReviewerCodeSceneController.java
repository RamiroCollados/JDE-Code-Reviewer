
package JDECodeReviewer;

import Various.CodeListCellFactory;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;


public class JDECodeReviewerCodeSceneController implements Initializable  {
    public static String variable=null;
    private static String codeLine;
       
    @FXML
    public ListView<String> Code;
   

    public static void setCodeLine(String codeLine) {
        JDECodeReviewerCodeSceneController.codeLine = codeLine;
    }
    
    public static String getCodeLine() {
        return codeLine;
    }
    @FXML
    private AnchorPane stage2;
    
    private int getCodeLineNumber(String codeLine){
        int lineNumber;
        
        codeLine = codeLine.trim();
        codeLine = codeLine.substring(codeLine.indexOf(":")+1, codeLine.indexOf(" "));

        lineNumber = Integer.parseInt(codeLine);
        
        return lineNumber;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        String lineCompare;
        int lineNumber=0;

        String path = JDECodeReviewerController.getFilePath();      
        //if event called from variable line then process is different.
        if(!JDECodeReviewerController.variableCodeScreen){
            try {
                FileInputStream fstream = new FileInputStream(path);
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

                ObservableList<String> CodeLines = FXCollections.observableArrayList();

                while((lineCompare = br.readLine()) != null) {
                    lineNumber++;
                    CodeLines.add("Line:"+lineNumber+"    "+lineCompare);    
                }

                CodeLines.remove(null);
                Code.setItems(CodeLines);

                Code.setCellFactory(new CodeListCellFactory()); 
                Code.setEditable(true);

                Code.scrollTo(getCodeLineNumber(codeLine));

            } catch (Exception ignored) {
            } 
            
        }else{
            variable = JDECodeReviewerController.line;
            
            try {
                FileInputStream fstream = new FileInputStream(path);
                BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

                ObservableList<String> CodeLines = FXCollections.observableArrayList();
                
                while((lineCompare = br.readLine()) != null) {
                    lineNumber++;
                    if(lineCompare.contains(variable+" ")|| lineCompare.endsWith(variable) ||
                            lineCompare.contains(variable+")") || lineCompare.contains(variable+"")){ //concat space or ) for variables that share parts. This led to errors
                         CodeLines.add("Line:"+lineNumber+"    "+lineCompare); 
                    }                     
                }
                
                CodeLines.remove(null);
                Code.setItems(CodeLines);
                Code.setCellFactory(new CodeListCellFactory()); 
                Code.setEditable(true);
                
            } catch (IOException ex) {
                Logger.getLogger(JDECodeReviewerCodeSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
           
    }   
}

