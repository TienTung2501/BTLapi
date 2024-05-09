package com.example.btlapi;

import com.example.btlapi.Domain.Food;
import com.example.btlapi.Domain.Order;
import com.example.btlapi.Domain.OrderItem;

import java.util.ArrayList;

public class GlobalVariable {
    public static final String baseUrl="http://192.168.0.101:333";
    public static int userId=-1;
    public static String userName="";
    public static String phone="";
    public static String email="";
    public static boolean islogin=false;
    public static ArrayList<OrderItem> listOrderItem;
    public static ArrayList<Order> listOrder;
    public static ArrayList<Food> listFood;

}
