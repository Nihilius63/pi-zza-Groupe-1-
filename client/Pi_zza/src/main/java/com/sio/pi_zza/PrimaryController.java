import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;

public class PrimaryController implements Initializable {

    @FXML
    private ScrollPane srollView;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        for(int i = 0; i < 20; i++) {
            HBox hbox = new HBox();
            
            hbox.getStyleClass().add("hbox");
            
            Label label = new Label("Ligne numéro : " + i);
            hbox.getChildren().add(label);
            
            Button button1 = new Button("Modifier");
            Button button2 = new Button("Supprimer");
            
            hbox.getChildren().add(button1);
            hbox.getChildren().add(button2);
            
            srollView.setContent(hbox);
        }
        
    

    }
}