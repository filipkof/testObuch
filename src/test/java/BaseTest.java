import org.example.Department;
import org.example.Employee;
import org.example.Service;
import org.junit.Assert;
import org.junit.Test;

public class BaseTest {


    @Test
    public void testDeleteAllEmployeesByDepartmentPositive() {
        Service.addDepartment(new Department(44, "JZ"));
        Service.addEmployee(new Employee(255, "Ивантестовый", 44));
        Service.addEmployee(new Employee(256, "Ивантестовый", 44));
        Service.removeDepartment(new Department(44, ""));

        int employeesIds = Service.findEmployeeIdByName("Ивантестовый").size();
        Assert.assertEquals(0, employeesIds);

        Service.removeEmployeeById(255);
        Service.removeEmployeeById(256);
        Service.removeDepartmentById(44);
    }

    @Test
    public void testAddDepartmentPositive() {
        Service.addDepartment(new Department(88, "JZ"));
        Department department = Service.findDepartmentById(88);
        Assert.assertEquals(88, department.getDepartmentID());

        Service.removeDepartmentById(88);
    }

    @Test
    public void testAddEmployeePositive() {
        Service.addEmployee(new Employee(188, "Александртестовый", 1));
        Employee employee = Service.findEmployeeById(188);
        Assert.assertEquals(188, employee.getEmployeeId());

        Service.removeEmployeeById(188);
    }

    @Test
    public void testRemoveEmployeePositive() {
        Service.addEmployee(new Employee(199, "Михаилтестовый", 1));
        Service.removeEmployee(new Employee(199,"Михаилтестовый",1));
        Employee employee = Service.findEmployeeById(199);
        Assert.assertNull(employee);
    }
}
