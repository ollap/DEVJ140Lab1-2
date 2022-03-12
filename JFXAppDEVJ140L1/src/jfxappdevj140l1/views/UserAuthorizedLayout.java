package jfxappdevj140l1.views;

import java.io.FileInputStream;
import java.io.IOException;
import javafx.geometry.Orientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import jfxappdevj140l1.AppDemoException;
import jfxappdevj140l1.controllers.MenuController;
import jfxappdevj140l1.controllers.StaffController;

import jfxappdevj140l1.models.Employee;

/**
 *
 * @author Olga
 */
public class UserAuthorizedLayout extends BorderPane {
    
    private final static String LOGO_SMALL = "src/img/logosmall.png";
    private final static String ICON = "/img/logo32x32.png";

    private TableView<Employee> staffTable;

    private final TextField nameField;
    private final TextField positionField;
    private final TextField projectField;
    private final Label messageLabel;

    public UserAuthorizedLayout() {
        super();
        nameField = new TextField();
        positionField = new TextField();
        projectField = new TextField();
        messageLabel = new Label("");
    }

    public TextField getNameField() {
        return nameField;
    }

    public TextField getPositionField() {
        return positionField;
    }

    public TextField getProjectField() {
        return projectField;
    }

    public Label getMessageLabel() {
        return messageLabel;
    }

    public TableView<Employee> getStaffTable() {
        return staffTable;
    }
    
    public void InitLayout(Stage stage) {
        MenuBar menuBar = new MenuBar();
        Menu viewMenu = new Menu("_View");

        MenuItem defaultMenuItem = new MenuItem("Default");
        defaultMenuItem.setOnAction((e) -> {
            MenuController.handleViewDefaultMenuAction(getScene());
        });
        
        MenuItem newStyleMenuItem = new MenuItem("New style");
        newStyleMenuItem.setOnAction((e) -> {
            MenuController.handleViewStyleMenuAction(getScene());
        });
        
        viewMenu.getItems().addAll(defaultMenuItem, newStyleMenuItem);
        menuBar.getMenus().add(viewMenu);
        
        this.setTop(menuBar);
       
        StaffController staffController = new StaffController(this);

        try {
            staffTable = new TableView(staffController.initStaffList());
//        staffTable.getSelectionModel().selectFirst();
        } catch (AppDemoException ex) {
            Alert errorMessage = new Alert(Alert.AlertType.ERROR,
                    ex.getMessage(), ButtonType.OK);
            errorMessage.show();
        }
        
        TableColumn<Employee, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        TableColumn<Employee, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        TableColumn<Employee, String> positionColumn = new TableColumn<>("Position");
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));

        TableColumn<Employee, String> projectColumn = new TableColumn<>("Project");
        projectColumn.setCellValueFactory(new PropertyValueFactory<>("project"));
        
        staffTable.getColumns().add(idColumn);
        staffTable.getColumns().add(nameColumn);
        staffTable.getColumns().add(positionColumn);
        staffTable.getColumns().add(projectColumn);
        
        staffTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> staffController.getTableRow(newValue));
        
        VBox rightPane = new VBox();
        Label headerLabel = new Label("Employee details");
        headerLabel.getStyleClass().add("header");
        
        GridPane detailsGridPane = new GridPane();
        Label nameLabel = new Label("Name");
        detailsGridPane.add(nameLabel, 0, 0);
        Label positionLabel = new Label("Position");
        detailsGridPane.add(positionLabel, 0, 1);
        Label projectLabel = new Label("Project");
        detailsGridPane.add(projectLabel, 0, 2);
        detailsGridPane.add(nameField, 1, 0);
        detailsGridPane.add(positionField, 1, 1);
        detailsGridPane.add(projectField, 1, 2);
        
        messageLabel.getStyleClass().add("message");
        
        HBox buttonBar = new HBox();
        buttonBar.getStyleClass().add("buttonbar");
        Button newButton = new Button("New");
        newButton.setOnAction((event) -> {
            staffController.newEmployee();
        });
        Button editButton = new Button("Edit");
        editButton.setDefaultButton(true);
        editButton.setOnAction((event) -> {
            staffController.editEmployee();
        });
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction((event) -> {
            staffController.deleteEmployee();
        });
        buttonBar.getChildren().addAll(newButton, editButton, deleteButton);
        
        rightPane.getChildren().addAll(headerLabel, detailsGridPane,
                                                    buttonBar, messageLabel);
        ImageView logo = new ImageView();

        try (FileInputStream fis = new FileInputStream(LOGO_SMALL)){
            Image image = new Image(fis);
            logo.setImage(image);
            rightPane.getChildren().add(logo);
        } catch (IOException e){
            System.out.println("Logo image file not found");
            Rectangle rectangle = new Rectangle(400, 214);
            rectangle.setFill(Color.rgb(35, 65, 95));
            rightPane.getChildren().add(rectangle);
        }

        try {
            stage.getIcons().add(new Image(
                        getClass().getResource(ICON).toString()));
        } catch (Exception ex) {
            System.out.println("Icon image file not found");
        }
        
        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.HORIZONTAL);
        splitPane.getItems().addAll(staffTable, rightPane);
        splitPane.setDividerPositions(0.6);
        SplitPane.setResizableWithParent(rightPane, false);
        
        this.setCenter(splitPane);
    }
}
