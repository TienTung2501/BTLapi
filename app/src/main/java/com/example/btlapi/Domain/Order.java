package com.example.btlapi.Domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private int Id;
    private Date OrderDate;
    private String OrderStatus;
    private String PaymentStatus;
    private double TotalPrice;
    private int UserId;

    public Order(int id, int userId, Date orderDate, String paymentStatus, String orderStatus, double totalPrice) {
        this.Id = id;
        this.UserId = userId;
        this.OrderDate = orderDate;
        this.PaymentStatus = paymentStatus;
        this.OrderStatus = orderStatus;
        this.TotalPrice = totalPrice;
    }

    // Getters and setters
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        this.UserId = userId;
    }

    public Date getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.OrderDate = orderDate;
    }

    public String getPaymentStatus() {
        return PaymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.PaymentStatus = paymentStatus;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.OrderStatus = orderStatus;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.TotalPrice = totalPrice;
    }

}
