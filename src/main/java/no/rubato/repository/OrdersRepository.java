package no.rubato.repository;

import no.rubato.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    Orders getByIdOrder(long idOrder);
    //void deleteByIdOrder(long idOrder);
}
