package net.diogobohm.dvenn;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import net.diogobohm.dvenn.image.ImageManager;

public class FXMLController implements Initializable {
    
    private ImageManager imageManager;

    @FXML
    private Pane imagePane;
    @FXML
    private ImageView vennImage;
    @FXML
    private TextField textField;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imageManager = new ImageManager();
        
        LogicListener logicListener = new LogicListener();
        VennDiagramImageProvider diagramPovider = new VennDiagramImageProvider(imageManager);
        
        Background okBackground = new Background(new BackgroundFill(
                Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY));
        Background invalidBackground = new Background(new BackgroundFill(
                Color.LIGHTSALMON, CornerRadii.EMPTY, Insets.EMPTY));
        Image invalidDiagram = imageManager.getImage("1way-screen.png");
        
        textField.textProperty().addListener(logicListener);
        
        textField.backgroundProperty().bind(Bindings.when(logicListener.validExpressionProperty)
                .then(okBackground)
                .otherwise(invalidBackground));
        
        vennImage.fitWidthProperty().bind(imagePane.widthProperty());
        vennImage.fitHeightProperty().bind(imagePane.heightProperty());
        
        vennImage.imageProperty().bind(Bindings.when(logicListener.validExpressionProperty)
                .then(diagramPovider.decodeDiagram(textField.getText()))
                .otherwise(invalidDiagram));
    }
}
