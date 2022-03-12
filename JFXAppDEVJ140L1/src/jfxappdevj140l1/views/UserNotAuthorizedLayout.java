package jfxappdevj140l1.views;

import java.io.FileInputStream;
import java.io.IOException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import jfxappdevj140l1.controllers.MenuController;

/**
 *
 * @author Olga
 */
public class UserNotAuthorizedLayout extends BorderPane {
    private final static String LOGO = "src/img/logo.png";
    private final static String ICON = "/img/logo32x32.png";


    public UserNotAuthorizedLayout(){
        super();
    }

    public void InitLayout(Stage stage) {
        MenuBar menuBar = new MenuBar();
        Menu abcMenu = new Menu("_ABC");        
        MenuItem loginMenuItem = new MenuItem("Log In");

        menuBar.getMenus().add(abcMenu);
        abcMenu.getItems().add(loginMenuItem);
        loginMenuItem.setOnAction((e) -> {
            MenuController.handleLoginMenuAction(stage);
        });

        VBox content = new VBox();
        ImageView logo = new ImageView();

        try (FileInputStream fis = new FileInputStream(LOGO)){
            Image image = new Image(fis);
            logo.setImage(image);
            content.getChildren().add(logo);
        } catch (IOException e){
            System.out.println("Logo image file not found");
            Rectangle rectangle = new Rectangle(1200, 300);
            rectangle.setFill(Color.rgb(35, 65, 95));
            content.getChildren().add(rectangle);
        }
        
        try {
            stage.getIcons().add(new Image(
                        getClass().getResource(ICON).toString()));
        } catch (Exception ex) {
            System.out.println("Icon image file not found");
        }

        Label welcomeLabel = new Label("ABC welcomes you!");
        
        Label messageLabel = new Label("Please log in");
        content.getChildren().addAll(welcomeLabel, messageLabel);

        content.setBackground(new Background(
                                new BackgroundFill(Color.WHITE, null, null)));
        welcomeLabel.setAlignment(Pos.TOP_CENTER);
        welcomeLabel.setTextFill(Color.rgb(35, 65, 95));
        welcomeLabel.setFont(Font.font(100));
        welcomeLabel.setPadding(new Insets(110, 20, 20, 50));
        messageLabel.setAlignment(Pos.BOTTOM_CENTER);
        messageLabel.setTextFill(Color.rgb(35, 65, 95));
        messageLabel.setFont(Font.font(40));
        messageLabel.setPadding(new Insets(70, 20, 10, 50));

        this.setTop(menuBar);
        this.setCenter(content);
    }
}
