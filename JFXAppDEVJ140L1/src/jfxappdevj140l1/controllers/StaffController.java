package jfxappdevj140l1.controllers;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import jfxappdevj140l1.AppDemoException;
import jfxappdevj140l1.models.Employee;
import jfxappdevj140l1.views.UserAuthorizedLayout;

/**
 *
 * @author Olga
 */
public class StaffController {
    private final UserAuthorizedLayout root;
    private final StaffDBController dbController;
    private ObservableList<Employee> staffList; 

    public StaffController(UserAuthorizedLayout root) {
        this.root = root;
        staffList = FXCollections.observableArrayList();
        dbController = new StaffDBController();
    }
    
    public void getTableRow(Employee employee) {
        clearMessage();
        if (employee != null) {
            root.getNameField().setText(employee.getName());
            root.getPositionField().setText(employee.getPosition());
            root.getProjectField().setText(employee.getProject());
        }
        else {
            root.getNameField().setText("");
            root.getPositionField().setText("");
            root.getProjectField().setText("");
        }
    }

    private void clearMessage() {
        root.getMessageLabel().setText("");
    }

    private boolean isCorrectEmployee() {
        return !(root.getNameField().getText().isEmpty() 
                || root.getPositionField().getText().isEmpty()
                || root.getProjectField().getText().isEmpty());
    }

    private int getSelectedRowIndex() {
        return root.getStaffTable().getSelectionModel().getSelectedIndex();
    }
    
    public void newEmployee() {
        clearMessage();
        if (isCorrectEmployee()) {
            Employee employee = new Employee(0,
                    root.getNameField().getText(),
                    root.getPositionField().getText(),
                    root.getProjectField().getText());
            try {
                dbController.addNewEmployeeToDB(employee);
            } catch (AppDemoException ex) {
                Alert errorMessage = new Alert(Alert.AlertType.ERROR,
                        ex.getMessage(), ButtonType.OK);
                errorMessage.show();
            }
            staffList.add(employee);
        }
        else {
            root.getMessageLabel().setText("All fields must be filled!");
        }
    }
    
    public void editEmployee() {
        clearMessage();
        int selectedIndex = getSelectedRowIndex();
        if (selectedIndex >= 0) {
            if (isCorrectEmployee()){
                Employee employee = new Employee(staffList.get(selectedIndex).getId(),
                                                root.getNameField().getText(),
                                                root.getPositionField().getText(),
                                                root.getProjectField().getText());
                
                try {
                    dbController.updateEmployeeInDB(employee);
                } catch (AppDemoException ex) {
                    Alert errorMessage = new Alert(Alert.AlertType.ERROR,
                            ex.getMessage(), ButtonType.OK);
                    errorMessage.show();
                }
                staffList.get(selectedIndex).setName(employee.getName());
                staffList.get(selectedIndex).setPosition(employee.getPosition());
                staffList.get(selectedIndex).setProject(employee.getProject());
            }
            else {
                root.getMessageLabel().setText("All fields must be filled!");
            }
        }
        else {
            root.getMessageLabel().setText("Row not selected!");
        }
    }

    public void deleteEmployee() {
        clearMessage();
        int selectedIndex = getSelectedRowIndex();
        if (selectedIndex >= 0) {
            try {
                dbController.deleteEmployeeFromDB(staffList.get(selectedIndex).getId());
            } catch (AppDemoException ex) {
                Alert errorMessage = new Alert(Alert.AlertType.ERROR,
                        ex.getMessage(), ButtonType.OK);
                errorMessage.show();

            }
            staffList.remove(selectedIndex);
        }
        else {
            root.getMessageLabel().setText("Row not selected!");
        }
    }

    public ObservableList<Employee> initStaffList() throws AppDemoException {

        ArrayList<Employee> staffL = dbController.getEmloyeesDBData();
        staffList = FXCollections.observableArrayList(staffL);
        return staffList;
    }
}
