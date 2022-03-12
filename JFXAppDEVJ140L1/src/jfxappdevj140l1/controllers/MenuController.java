package jfxappdevj140l1.controllers;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import jfxappdevj140l1.AppDemoException;
import jfxappdevj140l1.models.User;
import jfxappdevj140l1.views.UserAuthorizationDialog;
import jfxappdevj140l1.views.UserAuthorizedLayout;

/**
 *
 * @author Olga
 */
public class MenuController {
    private static final String DEFAULT_STYLE = "/resources/default.css";
    private static final String NEW_STYLE = "/resources/newstyle.css";
    
    public static void handleLoginMenuAction (Stage stage){
        UserAuthorizationDialog dialog = new UserAuthorizationDialog();
        User user = dialog.getUserData();
        try {
            UserController.checkUserData(user.getLogin(), user.getPassword());
            UserAuthorizedLayout root = new UserAuthorizedLayout();
            root.InitLayout(stage);

            Scene scene = new Scene(root, 1200, 800);

            stage.setTitle("ABC Company");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (AppDemoException ex) {
            Alert errorMessage = new Alert(Alert.AlertType.ERROR,
                    ex.getMessage(), ButtonType.OK);
            errorMessage.show();
        }
    }
    
    private static void setStyleCSS(Scene scene, String cssFileName) {
        scene.getStylesheets().clear();
        scene.getStylesheets().add(cssFileName);
    }
    
    public static void handleViewDefaultMenuAction(Scene scene){
        setStyleCSS(scene, DEFAULT_STYLE);
    }

    public static void handleViewStyleMenuAction(Scene scene){
        setStyleCSS(scene, NEW_STYLE);    
    }
}
