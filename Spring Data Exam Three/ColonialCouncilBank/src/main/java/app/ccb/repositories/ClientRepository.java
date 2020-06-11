package app.ccb.repositories;

import app.ccb.domain.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Client findByFullName(String fullName);

    @Query("select c from Client as c " +
            "order by c.bankAccount.cards.size desc ")
    List<Client> clientByMostCards();
}
