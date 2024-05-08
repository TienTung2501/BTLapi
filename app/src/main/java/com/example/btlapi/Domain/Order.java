package com.example.btlapi.Domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private int id;
    private int userId;
    private Date orderDate;
    private String paymentStatus;
    private String orderStatus;
    private double totalPrice;
    private List<OrderItem> orderItems;

    public Order(int id, int userId, Date orderDate, String paymentStatus, String orderStatus, double totalPrice) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.paymentStatus = paymentStatus;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.orderItems = new ArrayList<>();
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
    }

    public void removeOrderItem(OrderItem orderItem) {
        this.orderItems.remove(orderItem);
    }
}
