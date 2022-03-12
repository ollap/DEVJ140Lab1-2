package jfxappdevj140l1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxappdevj140l1.views.UserNotAuthorizedLayout;

/**
 *
 * @author Olga
 */
public class JFXAppDEVJ140L1 extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        setUserAgentStylesheet(STYLESHEET_MODENA);

        UserNotAuthorizedLayout root = new UserNotAuthorizedLayout();
        root.InitLayout(primaryStage);

        Scene scene = new Scene(root, 1200, 800);

        primaryStage.setTitle("ABC Company");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
