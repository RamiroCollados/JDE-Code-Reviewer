
package JDECodeReviewer;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;


public class JDECodeReviewerHelpScreenController implements Initializable {

    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private TextArea GenCommandText;
    @FXML
    private TextArea TabInfoText;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        AnchorPane.requestFocus();
    }    
    
}
