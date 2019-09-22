package no.rubato.controller;

import no.rubato.model.Orders;
import no.rubato.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/order/")
public class OrderController {

    private  final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    ///Give Orders, Book
    @PostMapping("/create")
    public ResponseEntity<?> uploadImage(@RequestBody Orders orders){
        Orders newOrder = orderService.saveOrder(orders);
        return new ResponseEntity<>(newOrder, HttpStatus.OK);
    }
    //Delete/Cancel Order/Booking
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteImage(@PathVariable("id") long id, Orders orders){
        Orders currentOrder = orderService.findBySearchId(id);
        if(currentOrder == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        orderService.deleteOrderById(id);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //List All Images
    @GetMapping("/list-all")//Show all Users from Database
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>(orderService.getAll(), HttpStatus.OK);
    }
}
