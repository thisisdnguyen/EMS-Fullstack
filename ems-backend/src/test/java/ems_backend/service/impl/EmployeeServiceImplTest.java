package ems_backend.service.impl;

import ems_backend.dto.EmployeeDto;
import ems_backend.entity.Employee;
import ems_backend.exception.ResourceNotFoundException;
import ems_backend.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;
    private EmployeeDto employeeDto;

    @BeforeEach
    void setUp() {
        employee = new Employee(1L, "Nguyen", "Van A", "vana@example.com");
        employeeDto = new EmployeeDto(1L, "Nguyen", "Van A", "vana@example.com");
    }

    @Test
    void createEmployee_ShouldReturnSavedEmployeeDto() {
        // Arrange: định nghĩa hành vi "giả" của repository
        // -> "khi ai gọi save() với BẤT KỲ Employee nào, hãy trả về biến 'employee' ở trên"
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        // Act: gọi hàm THẬT mà ta đang muốn test
        EmployeeDto result = employeeService.createEmployee(employeeDto);

        // Assert: kiểm tra kết quả có đúng như kỳ vọng không
        assertNotNull(result);
        assertEquals(employee.getId(), result.getId());
        assertEquals(employee.getEmail(), result.getEmail());

        // Kiểm tra thêm: đảm bảo repository.save() thực sự được gọi đúng 1 lần
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void getEmployeeById_WhenFound_ShouldReturnEmployeeDto() {
        // Arrange: giả lập "tìm thấy" -> trả về Optional CÓ chứa employee
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        // Act
        EmployeeDto result = employeeService.getEmployeeById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(employee.getId(), result.getId());
        assertEquals(employee.getFirstName(), result.getFirstName());
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void getEmployeeById_WhenNotFound_ShouldThrowResourceNotFoundException() {
        // Arrange: giả lập "không tìm thấy" -> trả về Optional RỖNG
        Long nonExistentId = 99L;
        when(employeeRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act + Assert: gọi hàm và khẳng định nó PHẢI ném ra đúng loại exception
        ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class,
            () -> employeeService.getEmployeeById(nonExistentId)
        );

        // Assert thêm: kiểm tra message của exception có đúng nội dung mong đợi không
        assertEquals("Employee not found with id: " + nonExistentId, exception.getMessage());
        verify(employeeRepository, times(1)).findById(nonExistentId);
    }

    @Test
    void updateEmployee_WhenFound_ShouldReturnUpdatedEmployeeDto() {
        // Arrange
        EmployeeDto updateData = new EmployeeDto(null, "Tran", "Thi B", "tranb@example.com");
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        // Sau khi setter chạy, "employee" đã bị đổi giá trị ngay trong bộ nhớ,
        // nên save() chỉ cần trả lại chính object đó
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        // Act
        EmployeeDto result = employeeService.updateEmployee(1L, updateData);

        // Assert
        assertEquals("Tran", result.getFirstName());
        assertEquals("Thi B", result.getLastName());
        assertEquals("tranb@example.com", result.getEmail());
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void updateEmployee_WhenNotFound_ShouldThrowException_AndNeverCallSave() {
        // Arrange
        Long nonExistentId = 99L;
        when(employeeRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
            ResourceNotFoundException.class,
            () -> employeeService.updateEmployee(nonExistentId, employeeDto)
        );

        // Assert thêm: vì tìm không thấy nên save() KHÔNG BAO GIỜ được gọi
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void deleteEmployee_WhenFound_ShouldCallDeleteById() {
        // Arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        // Act
        employeeService.deleteEmployee(1L);

        // Assert: deleteEmployee không trả về gì (void), nên không có "result" để assertEquals
        // -> ta chỉ có thể kiểm tra HÀNH VI: deleteById(1L) có thực sự được gọi không
        verify(employeeRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteEmployee_WhenNotFound_ShouldThrowException_AndNeverCallDeleteById() {
        // Arrange
        Long nonExistentId = 99L;
        when(employeeRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
            ResourceNotFoundException.class,
            () -> employeeService.deleteEmployee(nonExistentId)
        );

        // Assert thêm: tìm không thấy thì KHÔNG ĐƯỢC PHÉP xóa nhầm gì cả
        verify(employeeRepository, never()).deleteById(any());
    }

    @Test
    void getAllEmployees_WhenDataExists_ShouldReturnListOfEmployeeDto() {
        // Arrange: tạo thêm 1 employee thứ 2 để danh sách có >1 phần tử, test thực tế hơn
        Employee employee2 = new Employee(2L, "Le", "Van C", "levanc@example.com");
        when(employeeRepository.findAll()).thenReturn(List.of(employee, employee2));

        // Act
        List<EmployeeDto> result = employeeService.getAllEmployees();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Nguyen", result.get(0).getFirstName());
        assertEquals("Le", result.get(1).getFirstName());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void getAllEmployees_WhenNoData_ShouldReturnEmptyList() {
        // Arrange: giả lập DB chưa có employee nào -> repository trả về list rỗng
        when(employeeRepository.findAll()).thenReturn(List.of());

        // Act
        List<EmployeeDto> result = employeeService.getAllEmployees();

        // Assert
        assertNotNull(result);          // phải là list rỗng, KHÔNG được null
        assertTrue(result.isEmpty());
        verify(employeeRepository, times(1)).findAll();
    }
}