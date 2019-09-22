package no.rubato.service;

import no.rubato.model.Orders;
import no.rubato.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrdersRepository ordersRepository;

    @Autowired
    public OrderService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }
    //Get Order by ID
    public Orders getByIdOrder(long id){
        return ordersRepository.getByIdOrder(id);
    }
    //Create new Order
    public Orders saveOrder(Orders orders) {
        return ordersRepository.save(orders);
    }
    //Delete Order
    public void deleteOrderById(long id){
        ordersRepository.deleteById(id);
    }
    //Find By SearchId
    public Orders findBySearchId(long id){
        return ordersRepository.getByIdOrder(id);
    }
    public List<Orders> getAll(){
        return ordersRepository.findAll();
    }
}
