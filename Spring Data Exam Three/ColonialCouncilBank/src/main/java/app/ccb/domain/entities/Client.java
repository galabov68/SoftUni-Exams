package app.ccb.domain.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Client extends BaseEntity {
    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column
    private int age;
    @OneToOne
    @JoinColumn(name = "bank_account_id", referencedColumnName = "id")
    private BankAccount bankAccount;
    @ManyToMany(mappedBy = "clients", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Employee> employees;

    public Client() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}
