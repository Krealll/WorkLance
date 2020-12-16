package com.krealll.worklance.model.entity;

import java.time.LocalDate;

public class Order extends Entity {

    private Long orderId;
    private String title;
    private LocalDate date;
    private String description;
    private OrderStatus orderStatus;
    private Long idOrderCreator;
    private Double approximateBudget;

    public Order(Long orderId, String title, LocalDate date, String description, OrderStatus orderStatus, Long idOrderCreator, Double approximateBudget) {
        this.orderId = orderId;
        this.title = title;
        this.date = date;
        this.description = description;
        this.orderStatus = orderStatus;
        this.idOrderCreator = idOrderCreator;
        this.approximateBudget = approximateBudget;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getIdOrderCreator() {
        return idOrderCreator;
    }

    public void setIdOrderCreator(Long idOrderCreator) {
        this.idOrderCreator = idOrderCreator;
    }

    public Double getApproximateBudget() {
        return approximateBudget;
    }

    public void setApproximateBudget(Double approximateBudget) {
        this.approximateBudget = approximateBudget;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        if(orderId != null ? !orderId.equals(order.orderId) : order.orderId != null ){
            return false;
        }
        if(title != null ? !title.equals(order.title) : order.title != null ){
            return false;
        }
        if(date != null ? !date.equals(order.date) : order.date != null ){
            return false;
        }
        if(description != null ? !description.equals(order.description) : order.description != null ){
            return false;
        }
        if(orderStatus != null ? !orderStatus.equals(order.orderStatus) : order.orderStatus != null ){
            return false;
        }
        if(approximateBudget != null ? Double.compare(approximateBudget,order.approximateBudget)!=0 : order.approximateBudget != null ){
            return false;
        }
        return idOrderCreator != null ? !idOrderCreator.equals(order.idOrderCreator) : order.idOrderCreator != null;
    }

    @Override
    public int hashCode() {
        int hashCode = orderId != null ? orderId.hashCode() : 0;
        hashCode = 31 * hashCode + (title != null ? title.hashCode() : 0);
        hashCode = 31 * hashCode + (date != null ? date.hashCode() : 0);
        hashCode = 31 * hashCode + (description != null ? description.hashCode() : 0);
        hashCode = 31 * hashCode + (orderStatus != null ? orderStatus.hashCode() : 0);
        hashCode = 31 * hashCode + (idOrderCreator != null ? idOrderCreator.hashCode() : 0);
        hashCode = 31 * hashCode + (approximateBudget != null ? approximateBudget.hashCode() : 0);
        return hashCode;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Order{");
        stringBuilder.append("orderId=").append(orderId);
        stringBuilder.append("title=").append(title);
        stringBuilder.append(", date='").append(date).append('\'');
        stringBuilder.append(", description='").append(description).append('\'');
        stringBuilder.append(", orderStatus='").append(orderStatus).append('\'');
        stringBuilder.append(", user='").append(idOrderCreator).append('\'');
        stringBuilder.append(", approximate budget='").append(approximateBudget).append('\'');
        return stringBuilder.toString();

    }
}
