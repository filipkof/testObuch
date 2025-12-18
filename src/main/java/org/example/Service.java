package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Service {

    public static void createDB() {
        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            Statement stm = con.createStatement();
            stm.executeUpdate("DROP TABLE Department IF EXISTS");
            stm.executeUpdate("CREATE TABLE Department(ID INT PRIMARY KEY, NAME VARCHAR(255))");
            stm.executeUpdate("INSERT INTO Department VALUES(1,'Accounting')");
            stm.executeUpdate("INSERT INTO Department VALUES(2,'IT')");
            stm.executeUpdate("INSERT INTO Department VALUES(3,'HR')");

            stm.executeUpdate("DROP TABLE Employee IF EXISTS");
            stm.executeUpdate("CREATE TABLE Employee(ID INT PRIMARY KEY, NAME VARCHAR(255), DepartmentID INT)");
            stm.executeUpdate("INSERT INTO Employee VALUES(1,'Pete',1)");
            stm.executeUpdate("INSERT INTO Employee VALUES(2,'Ann',1)");

            stm.executeUpdate("INSERT INTO Employee VALUES(3,'Liz',2)");
            stm.executeUpdate("INSERT INTO Employee VALUES(4,'Tom',2)");

            stm.executeUpdate("INSERT INTO Employee VALUES(5,'Todd',3)");

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void addDepartment(Department d) {
        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            PreparedStatement stm = con.prepareStatement("INSERT INTO Department VALUES(?,?)");
            stm.setInt(1, d.departmentID);
            stm.setString(2, d.getName());
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void removeDepartment(Department d) {
        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            PreparedStatement deleteEmployees =
                    con.prepareStatement("DELETE FROM Employee WHERE DepartmentID = ?");
            deleteEmployees.setInt(1, d.departmentID);
            deleteEmployees.executeUpdate();

            PreparedStatement stm = con.prepareStatement("DELETE FROM Department WHERE ID=?");
            stm.setInt(1, d.departmentID);
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void addEmployee(Employee empl) {
        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            PreparedStatement stm = con.prepareStatement("INSERT INTO Employee VALUES(?,?,?)");
            stm.setInt(1, empl.getEmployeeId());
            stm.setString(2, empl.getName());
            stm.setInt(3, empl.getDepartmentId());
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void removeEmployee(Employee empl) {
        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            PreparedStatement stm = con.prepareStatement("DELETE FROM Employee WHERE ID=?");
            stm.setInt(1, empl.getEmployeeId());
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // тут мое
    public static void removeEmployeeById(int employeeId) {
        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            PreparedStatement stm = con.prepareStatement("DELETE FROM Employee WHERE ID=?");
            stm.setInt(1, employeeId);
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // тут мое
    public static void removeDepartmentById(int departmentId) {
        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            PreparedStatement stm = con.prepareStatement("DELETE FROM Department WHERE ID=?");
            stm.setInt(1, departmentId);
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // тут мое
    public static List<Integer> findEmployeeIdByName(String name) {
        List<Integer> employeeIds = new ArrayList<>();
        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            PreparedStatement stm = con.prepareStatement("SELECT ID FROM Employee WHERE NAME = ?");
            stm.setString(1, name);
            ResultSet result = stm.executeQuery();
            while (result.next()) {
                employeeIds.add(result.getInt(1));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return employeeIds;
    }

    // тут мое
    public static Department findDepartmentById(int departmentId) {
        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            PreparedStatement stm = con.prepareStatement("SELECT * FROM Department WHERE ID = ?");
            stm.setInt(1, departmentId);
            ResultSet result = stm.executeQuery();
            if (result.next()) {
                return new Department(result.getInt("ID"), result.getString("NAME"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    // тут мое
    public static Employee findEmployeeById(int employeeId) {
        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            PreparedStatement stm = con.prepareStatement("SELECT * FROM Employee WHERE ID = ?");
            stm.setInt(1, employeeId);
            ResultSet result = stm.executeQuery();
            if (result.next()) {
                return new Employee(result.getInt("ID"), result.getString("NAME"), result.getInt("DepartmentID"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
