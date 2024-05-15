package com.example.btlapi.Domain;

public class OrderItem {
    private int Id;
    private int OrderId;
    private double Price;
    private int ProductId;
    private int Quantity;
    public OrderItem(int productId, int quantity, double price) {
        Price = price;
        ProductId = productId;
        Quantity = quantity;
    }


    public OrderItem(int id, int orderId, int productId, int quantity, double price) {
        this.Id = id;
        this.OrderId = orderId;
        this.ProductId = productId;
        this.Quantity = quantity;
        this.Price = price;
    }


    // Getters and setters
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        this.OrderId = orderId;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        this.ProductId = productId;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        this.Quantity = quantity;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        this.Price = price;
    }
}

