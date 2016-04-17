package net.diogobohm.dvenn;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
        
        final LogicListener logicListener = new LogicListener();
        final LogicExpressionDecoder diagramPovider = new LogicExpressionDecoder();
        final BinaryToImageConverter converter = new BinaryToImageConverter(imageManager);
        
        Background okBackground = new Background(new BackgroundFill(
                Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY));
        Background invalidBackground = new Background(new BackgroundFill(
                Color.LIGHTSALMON, CornerRadii.EMPTY, Insets.EMPTY));
        final Image invalidDiagram = imageManager.getImage("1way-screen.png");
        
        vennImage.imageProperty().setValue(invalidDiagram);
        
        textField.textProperty().addListener(logicListener);
        
        textField.backgroundProperty().bind(Bindings.when(logicListener.validExpressionProperty)
                .then(okBackground)
                .otherwise(invalidBackground));
        
        vennImage.fitWidthProperty().bind(imagePane.widthProperty());
        vennImage.fitHeightProperty().bind(imagePane.heightProperty());
        
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observable, String oldValue, String newValue) {
                if (logicListener.validExpressionProperty.get()) {
                    vennImage.imageProperty().setValue(converter.render(diagramPovider.decodeDiagram(textField.getText())));
                } else {
                    vennImage.imageProperty().setValue(invalidDiagram);
                }
            }
        });
    }
}
