
package JDECodeReviewer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;



public class JDECodeReviewerCodeScreen extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXMLs/FXMLJDECodeReviewerCodeScene.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        stage.setResizable(false);
        stage.setScene(scene);
           
        stage.setTitle("JDE Code Reviewer - Code Screen");
        
        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
              stage.close();
            }
        }); 

        try {
             stage.getIcons().add(new Image("/Images/Icon2.png"));
        } catch (Exception ignored) {
        }

        stage.show();
    }

}
