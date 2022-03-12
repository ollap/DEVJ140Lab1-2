package jfxappdevj140l1.views;

import java.util.Optional;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

import jfxappdevj140l1.models.User;

/**
 *
 * @author Olga
 */
public class UserAuthorizationDialog extends Dialog<Pair <String, String>>{
    private final static String IMG = "/img/login120x120.png";
    private final static String ICON = "/img/login16x16.png";

    public UserAuthorizationDialog() {
        super();
    }

    public User getUserData() {
        setTitle("User Authorization");
        setHeaderText("Please log in to access ABC company information");
        DialogPane dialogRoot = getDialogPane();
        Stage stage = (Stage) dialogRoot.getScene().getWindow();
        
        try {
            setGraphic(new ImageView(
                    getClass().getResource(IMG).toExternalForm()));
        } catch (Exception ex) {
            setGraphic(new Circle(50, Color.rgb(35, 65, 95)));
        }
        
        try {
            stage.getIcons().add(new Image(
                        getClass().getResource(ICON).toString()));
        } catch (Exception ex) {
            initStyle(StageStyle.UTILITY);
        }

        VBox content = new VBox();
        content.setBackground(new Background(
                                new BackgroundFill(Color.WHITE, null, null)));

        
        GridPane gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        Label loginLabel = new Label("User name");
        Label passwordLabel = new Label("Password");
        TextField loginField = new TextField();
        loginField.setPromptText("Your login");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Your password");
        gridPane.add(loginLabel, 0, 0);
        gridPane.add(loginField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        
        content.getChildren().addAll(gridPane);
        dialogRoot.setContent(content);

        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialogRoot.getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        Node loginButton = dialogRoot.lookupButton(loginButtonType);
        loginButton.setDisable(true);

        loginField.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });
        
        setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(loginField.getText(), passwordField.getText());
            }
            return null;
        });
        
        Optional<Pair<String, String>> result = showAndWait();
        
        return ( (result.isPresent()) ?
            new User(result.get().getKey(), result.get().getValue()) : null );
    }
}
