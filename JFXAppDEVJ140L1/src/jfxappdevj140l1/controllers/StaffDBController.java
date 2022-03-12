package jfxappdevj140l1.controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import jfxappdevj140l1.AppDemoException;
import jfxappdevj140l1.models.Employee;

/**
 *
 * @author Olga
 */
public class StaffDBController implements AutoCloseable{
    private final String SERVER_URL = "jdbc:derby://localhost:1527/javafxdemo";
    private final String SERVER_LOGIN = "test";
    private final String SERVER_PASSWORD = "test";

    public StaffDBController() {
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(
                SERVER_URL, SERVER_LOGIN, SERVER_PASSWORD);
    } 

    private int getMaxEmployeesDBIndex() throws AppDemoException {
        int maxIndx = -1;
        try (Connection conn = createConnection()){

            try (PreparedStatement pstmt = conn.prepareStatement(
                    "select max (ID) as MAX_ID\n" +
                    "    from EMPLOYEES")){
                ResultSet rs = pstmt.executeQuery();
                if (rs != null && rs.next()) {
                    maxIndx = rs.getInt("MAX_ID");
                }
            } catch (SQLException ex) {
                throw new AppDemoException(ex.getMessage());
            } 
        
        } catch (SQLException ex) {
            throw new AppDemoException("Database connection error: "
                                                        + ex.getMessage());
        }
        return maxIndx;
    }

    public ArrayList<Employee> getEmloyeesDBData() throws AppDemoException {
        ArrayList<Employee> emplList = new ArrayList<>();

        try (Connection conn = createConnection()){

            try (PreparedStatement pstmt = conn.prepareStatement(
                    "select *\n" +
                    "    from EMPLOYEES")){
                ResultSet rs = pstmt.executeQuery();
                while (rs != null && rs.next()) {
                    emplList.add(new Employee(rs.getInt("ID"),
                                        rs.getString("EMPL_NAME"),
                                        rs.getString("EMPL_POSITION"),
                                        rs.getString("EMPL_PROJECT")));
                }
            } catch (SQLException ex) {
                throw new AppDemoException(ex.getMessage());
            } 
            
        } catch (SQLException ex) {
            throw new AppDemoException("Database connection error: "
                                                        + ex.getMessage());
        }
        return emplList;
    }
    
    public void addNewEmployeeToDB(Employee employee) throws AppDemoException {
        int maxIndx = getMaxEmployeesDBIndex();
        maxIndx = ((maxIndx >= 0) ? (maxIndx + 1) : 1);
        
        try (Connection conn = createConnection()){

            try (PreparedStatement pstmt = conn.prepareStatement(
                    "insert into EMPLOYEES\n" +
                    "    values (?, ?, ?, ?)")){
                pstmt.setInt(1, maxIndx);
                pstmt.setString(2, employee.getName());
                pstmt.setString(3, employee.getPosition());
                pstmt.setString(4, employee.getProject());
                pstmt.executeUpdate();
            } catch (SQLException ex) {
                throw new AppDemoException(ex.getMessage());
            } 
       
        } catch (SQLException ex) {
            throw new AppDemoException("Database connection error: "
                                                        + ex.getMessage());
        }
        employee.setId(maxIndx);
    }
    
    public void updateEmployeeInDB(Employee employee) throws AppDemoException {

        try (Connection conn = createConnection()){

            try (PreparedStatement pstmt = conn.prepareStatement(
                    "update EMPLOYEES\n" +
                    "    set EMPL_NAME = ?,"
                         + " EMPL_POSITION = ?,"
                         + " EMPL_PROJECT = ?" +
                    "    where ID = ?")){
                pstmt.setString(1, employee.getName());
                pstmt.setString(2, employee.getPosition());
                pstmt.setString(3, employee.getProject());
                pstmt.setInt(4, employee.getId());
                pstmt.executeUpdate();
            } catch (SQLException ex) {
                throw new AppDemoException(ex.getMessage());
            } 
        
        } catch (SQLException ex) {
            throw new AppDemoException("Database connection error: "
                                                        + ex.getMessage());
        }
    }
    
    public void deleteEmployeeFromDB(int id) throws AppDemoException {

        try (Connection conn = createConnection()){

            try (PreparedStatement pstmt = conn.prepareStatement(
                    "delete\n" +
                    "    from EMPLOYEES\n" +
                    "    where ID = ?")){
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            } catch (SQLException ex) {
                throw new AppDemoException(ex.getMessage());
            } 
        
        } catch (SQLException ex) {
            throw new AppDemoException("Database connection error: "
                                                        + ex.getMessage());
        }
    }

    @Override
    public void close() throws Exception {
    }    
}