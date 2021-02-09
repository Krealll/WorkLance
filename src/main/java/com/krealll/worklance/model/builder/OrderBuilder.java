package com.krealll.worklance.model.builder;

import com.krealll.worklance.model.entity.Order;
import com.krealll.worklance.model.entity.type.OrderStatus;

import java.time.LocalDate;

public class OrderBuilder  {
    private Long id;
    private String name;
    private LocalDate date;
    private String description;
    private OrderStatus status;
    private Long customer;
    private Double budget;


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCustomer(Long customer) {
        this.customer = customer;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }


    public Order build(){
        Order order = new Order(id, name, date, description, status, customer,  budget);
        id =null;
        name =null;
        description=null;
        status=null;
        customer =null;
        budget=null;
        return order;
    }

}
