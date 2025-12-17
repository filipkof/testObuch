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

    public static List<Integer> findDepartmentIdByName(String departmentName) {
        List<Integer> departmentIds = new ArrayList<>();
        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            PreparedStatement stm = con.prepareStatement("SELECT ID FROM Department WHERE NAME = ?");
            stm.setString(1, departmentName);
            ResultSet result = stm.executeQuery();

            while (result.next()) {
                departmentIds.add(result.getInt("ID"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return departmentIds;
    }

    public static void updateEmployeeDepartmentById(int departmentId, int employeeId) {
        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            PreparedStatement stm = con.prepareStatement("UPDATE Employee SET DepartmentID = ? WHERE ID = ?");
            stm.setInt(1, departmentId);
            stm.setInt(2, employeeId);
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void findOneEmployeeByNameAndMoveToDepartment(String employeeName, String departmentName) {
        List<Integer> employeeIds = findEmployeeIdByName(employeeName);

        if (employeeIds.size() != 1) {
            System.out.println(employeeIds.size() + " сотрудник(ов) с именем: " + employeeName + " Пожалуйста уточните поиск");
            return;
        }

        List<Integer> departmentIds = findDepartmentIdByName(departmentName);

        if (departmentIds.size() != 1) {
            System.out.println("Найден некорректное число ID отделов: " + departmentIds.size());
            return;
        }

        int departmentId = departmentIds.getFirst();
        updateEmployeeDepartmentById(departmentId, employeeIds.getFirst());
        System.out.println("Успешно обновили запись у сотрудника " + employeeIds.getFirst());
    }

    public static List<Employee> findAllEmployeeIdsAndNames() {
        List<Employee> employees = new ArrayList<>();

        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            PreparedStatement stm = con.prepareStatement("SELECT ID, NAME, DepartmentID FROM Employee");
            ResultSet result = stm.executeQuery();
            while (result.next()) {
                employees.add(new Employee(result.getInt("ID"),
                        result.getString("NAME"), result.getInt("DepartmentID")));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return employees;
    }

    public static int updateEmployeeNameById(String employeeName, int employeeId) {
        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office")) {
            PreparedStatement stm = con.prepareStatement("UPDATE Employee SET NAME = ? WHERE ID = ?");
            stm.setString(1, employeeName);
            stm.setInt(2, employeeId);
            return stm.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    public static void fixEmployeeName() {
        List<Employee> employees = findAllEmployeeIdsAndNames();

        int counter = employees.stream()
                .filter(e -> e.getName() != null && !e.getName().isEmpty())
                .filter(e -> Character.isLowerCase(e.getName().charAt(0)))
                .mapToInt(e -> {
                    String fixedName =
                            Character.toUpperCase(e.getName().charAt(0)) + e.getName().substring(1);
                    return updateEmployeeNameById(fixedName, e.getEmployeeId());
                })
                .sum();
        System.out.println("Исправлено имен: " + counter);
    }

}
