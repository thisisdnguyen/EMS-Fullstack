package ems_backend.service;

import java.util.List;

import ems_backend.dto.EmployeeDto;

public interface EmployeeService {

    EmployeeDto createEmployee(EmployeeDto employeeDto);

    EmployeeDto getEmployeeById(Long id);

    List<EmployeeDto> getAllEmployees();

    EmployeeDto updateEmployee(Long id, EmployeeDto updateEmployee);

    void deleteEmployee(Long employeeId);
}
