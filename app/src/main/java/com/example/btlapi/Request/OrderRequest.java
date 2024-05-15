package com.example.btlapi.Request;

public class OrderRequest {
    private String OrderStatus;
    private String PaymentStatus;
    private String TotalPrice;
    private String UserId;

    public OrderRequest(String userId, String orderStatus, String paymentStatus, String totalPrice) {
        this.UserId = userId;
        this.OrderStatus = orderStatus;
        this.PaymentStatus = paymentStatus;
        this.TotalPrice = totalPrice;
    }

    // Getters and setters
}
