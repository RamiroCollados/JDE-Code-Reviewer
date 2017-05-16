//Author: Ramiro Collados

package Various;

import JDECodeReviewer.JDECodeReviewerCodeSceneController;
import JDECodeReviewer.JDECodeReviewerController;
import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;


public class CodeListCell extends ListCell<String>{
    @Override
    protected void updateItem(String s, boolean empty) {
        String lineToSee;
        String lineStartCompare;
        
        super.updateItem(s, empty);
        
        if (s == null || empty)
        {
            setText("");
        }
        else{
            setText(s); 
        }  
        
        setStyle("-fx-background-color: black; -fx-text-fill: white;");
        
        lineToSee=JDECodeReviewerCodeSceneController.getCodeLine();
        lineStartCompare=lineToSee.substring(0, 9);
        lineToSee=lineToSee.substring(10);
        lineToSee=lineToSee.trim();
        
        try {
            
            if(s.contains("If ") || s.contains("While ")|| s.contains("End If") || s.contains("End While")|| s.endsWith("Else")){
                setTextFill(Color.LIGHTBLUE);
                setStyle("-fx-background-color: black");
            }
            if(s.contains("// ") || s.contains(" //") || s.contains("---------") || s.contains("=========") || s.contains("**********") || s.contains("GLOBALS")
            || s.contains("OBJECT")|| s.contains("EVENT")|| s.contains("SECTION")|| s.contains("FORM")|| s.contains("CONTROL")){
                setTextFill(Color.LIGHTGREEN);
                setStyle("-fx-background-color: black");
            }
            if(s.contains(lineToSee) && s.startsWith(lineStartCompare)){       
                setTextFill(Color.BLACK);
                setStyle("-fx-background-color: white");
            }
            lineToSee=s.substring(10).trim();
            if(lineToSee.startsWith(JDECodeReviewerCodeSceneController.variable)
                && lineToSee.endsWith(JDECodeReviewerCodeSceneController.variable)
                    && JDECodeReviewerController.variableCodeScreen ){
                
                setTextFill(Color.BLACK);
                setStyle("-fx-background-color: white");
            }
            
            
        } catch (Exception e) {
        }
        
        this.setText(s);
        setGraphic(null);
    }   
    
}
