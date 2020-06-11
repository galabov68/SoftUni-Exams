package app.ccb.domain.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "bank_account")
public class BankAccount extends BaseEntity {
    @Column(name = "account_number", nullable = false)
    private String accountNumber;
    @Column
    private BigDecimal balance;
    @OneToOne(mappedBy = "bankAccount", targetEntity = Client.class,
            fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Client client;
    @OneToMany(mappedBy = "bankAccount", targetEntity = Card.class,
            fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Card> cards;

    public BankAccount() {
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }
}
