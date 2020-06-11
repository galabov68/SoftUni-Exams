package app.ccb.repositories;

import app.ccb.domain.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Employee findByFirstNameAndLastName(String firstName, String lastName);

    @Query("select e from Employee as e " +
            "where e.clients.size > 0 " +
            "order by e.clients.size desc, e.id asc")
    List<Employee> employeesWithAnyClients();
}
