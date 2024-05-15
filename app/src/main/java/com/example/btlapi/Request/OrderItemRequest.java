package com.example.btlapi.Request;

public class OrderItemRequest {
    private String OrderId;
    private String ProductId;
    private String Price;
    private String Quantity;

    public OrderItemRequest(String orderId, String productId, String price, String quantity) {
        this.OrderId = orderId;
        this.ProductId = productId;
        this.Price = price;
        this.Quantity = quantity;
    }

    // Getters and setters...


    public String getOrderId() {
        return OrderId;
    }

    public String getProductId() {
        return ProductId;
    }

    public String getPrice() {
        return Price;
    }

    public String getQuantity() {
        return Quantity;
    }
}
