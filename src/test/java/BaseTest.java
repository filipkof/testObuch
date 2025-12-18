import org.example.Department;
import org.example.Employee;
import org.example.Service;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class BaseTest {

    @Test
    public void testMoveOneEmployeePositive() {
        Service.addEmployee(new Employee(777, "Аннатестовая", 1));

        Service.findOneEmployeeByNameAndMoveToDepartment("Аннатестовая", "HR");
        Employee employeesAfter = Service.findEmployeeById(777);
        List<Integer> departmentIds = Service.findDepartmentIdByName("HR");
        int departmentId = departmentIds.getFirst();
        Assert.assertEquals(departmentId, employeesAfter.getDepartmentId());

        Service.removeEmployeeById(777);
    }

    @Test
    public void testMoveOneEmployeeNegative() {
        Service.addEmployee(new Employee(55, "Ивантестовый", 1));
        Service.addEmployee(new Employee(56, "Ивантестовый", 1));

        Service.findOneEmployeeByNameAndMoveToDepartment("Ивантестовый", "HR");
        List<Employee> employeesAfter = Service.findEmployeeByName("Ивантестовый");
        Assert.assertEquals(1, employeesAfter.get(0).getDepartmentId());
        Assert.assertEquals(1, employeesAfter.get(1).getDepartmentId());

        Service.removeEmployeeById(55);
        Service.removeEmployeeById(56);
    }

    @Test
    public void testFixEmployeeNamesPositive() {
        Service.addEmployee(new Employee(166, "олексий", 1));
        Service.fixEmployeeName();
        String fixName = Service.findEmployeeById(166).getName();
        Assert.assertEquals("Олексий", fixName);
        Service.removeEmployeeById(166);
    }

    @Test
    public void testCountOfEmployeeByDepartmentNamePositive() {
        Service.addDepartment(new Department(88888, "ТЕСТОВЫЙ"));
        Service.addEmployee(new Employee(155, "Ивантестовый", 88888));
        Service.addEmployee(new Employee(156, "Ивантестовый", 88888));
        Service.addEmployee(new Employee(157, "Ивантестовый", 88888));

        int count = Service.findCountOfEmployeeByDepartmentName("ТЕСТОВЫЙ");
        Assert.assertEquals(3, count);

        Service.removeEmployeeById(155);
        Service.removeEmployeeById(156);
        Service.removeEmployeeById(157);
        Service.removeDepartmentById(88888);
    }
}
