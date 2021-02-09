package com.krealll.worklance.model.entity;

import com.krealll.worklance.model.entity.type.OrderStatus;

import java.time.LocalDate;

public class Order {

    private Long id;
    private String name;
    private LocalDate date;
    private String description;
    private OrderStatus status;
    private Long customer;
    private Double budget;

    public Order(Long orderId, String title, LocalDate date, String description, OrderStatus status, Long idOrderCreator, Double budget) {
        this.id = orderId;
        this.name = title;
        this.date = date;
        this.description = description;
        this.status = status;
        this.customer = idOrderCreator;
        this.budget = budget;
    }

    public Order(String title, LocalDate date, Double budget, OrderStatus status) {
        this.name = title;
        this.date = date;
        this.status = status;
        this.budget = budget;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Long getCustomer() {
        return customer;
    }

    public void setCustomer(Long customer) {
        this.customer = customer;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        if(id != null ? !id.equals(order.id) : order.id != null ){
            return false;
        }
        if(name != null ? !name.equals(order.name) : order.name != null ){
            return false;
        }
        if(date != null ? !date.equals(order.date) : order.date != null ){
            return false;
        }
        if(description != null ? !description.equals(order.description) : order.description != null ){
            return false;
        }
        if(status != null ? !status.equals(order.status) : order.status != null ){
            return false;
        }
        if(budget != null ? Double.compare(budget,order.budget)!=0 : order.budget != null ){
            return false;
        }
        if(customer != null ? !customer.equals(order.customer) : order.customer != null ){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = id != null ? id.hashCode() : 0;
        hashCode = 31 * hashCode + (name != null ? name.hashCode() : 0);
        hashCode = 31 * hashCode + (date != null ? date.hashCode() : 0);
        hashCode = 31 * hashCode + (description != null ? description.hashCode() : 0);
        hashCode = 31 * hashCode + (status != null ? status.hashCode() : 0);
        hashCode = 31 * hashCode + (customer != null ? customer.hashCode() : 0);
        hashCode = 31 * hashCode + (budget != null ? budget.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Order{");
        stringBuilder.append("orderId=").append(id);
        stringBuilder.append("title=").append(name);
        stringBuilder.append(", date=").append(date).append('\'');
        stringBuilder.append(", description=").append(description).append('\'');
        stringBuilder.append(", orderStatus=").append(status).append('\'');
        stringBuilder.append(", customer=").append(customer).append('\'');
        stringBuilder.append(", budget=").append(budget).append('\'');
        stringBuilder.append("}");
        return stringBuilder.toString();

    }
}
