
package JDECodeReviewer;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;



public class JDECodeReviewer extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/FXMLs/FXMLJDECodeReviewer.fxml"));
        Scene scene = new Scene(root);
            
        stage.setResizable(false); 
        stage.setScene(scene);
        stage.setTitle("JDE Code Reviewer");
        
        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
              stage.close();
            }
        }); 
        
        try {
             stage.getIcons().add(new Image("/Images/Icon2.png"));
        } catch (Exception ignored) {
        }

        // Dropping over surface
        scene.setOnDragOver(event -> {
            Dragboard db = event.getDragboard();
            if (db.hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
            } else {
                event.consume();
            }
        });

        scene.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                success = true;
                for (File file : db.getFiles()) {
                    JDECodeReviewerController.setFilePath(file.getAbsolutePath());
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });
        
        stage.show();
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }


    
}
