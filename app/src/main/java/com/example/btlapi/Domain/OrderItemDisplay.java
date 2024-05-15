package com.example.btlapi.Domain;

public class OrderItemDisplay {
    private String ImagePath;
    private double Price;
    private int Quantity;
    private String Title;
    private double TotalPrice;

    // Constructor
    public OrderItemDisplay(String imagePath, double price, int quantity, String title, double totalPrice) {
        this.ImagePath = imagePath;
        this.Price = price;
        this.Quantity = quantity;
        this.Title = title;
        this.TotalPrice = totalPrice;
    }

    // Getters and setters
    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        this.ImagePath = imagePath;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        this.Price = price;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        this.Quantity = quantity;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.TotalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "OrderItemDisplay{" +
                "imagePath='" + ImagePath + '\'' +
                ", price='" + Price + '\'' +
                ", quantity=" + Quantity +
                ", title='" + Title + '\'' +
                ", totalPrice='" + TotalPrice + '\'' +
                '}';
    }
}

