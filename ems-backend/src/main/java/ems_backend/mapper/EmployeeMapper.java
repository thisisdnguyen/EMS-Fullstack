package ems_backend.mapper;
import ems_backend.dto.EmployeeDto;
import ems_backend.entity.Employee;

public class EmployeeMapper {
    public static EmployeeDto mapToEmployeeDto(Employee e){
        return new EmployeeDto(
            e.getId(), 
            e.getFirstName(), 
            e.getLastName(), 
            e.getEmail()
        );
    }

    public static Employee mapToEmployee(EmployeeDto dto){
        return new Employee(
            dto.getId(), 
            dto.getFirstName(), 
            dto.getLastName(), 
            dto.getEmail()
        );
    }
}
