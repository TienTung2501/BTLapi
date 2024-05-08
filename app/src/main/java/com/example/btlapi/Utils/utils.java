package com.example.btlapi.Utils;

import static com.example.btlapi.GlobalVariable.baseUrl;

import com.example.btlapi.Interface.CategoryInterface;
import com.example.btlapi.Interface.FoodInterface;
import com.example.btlapi.Interface.LocationInterface;
import com.example.btlapi.Interface.OrderInterface;
import com.example.btlapi.Interface.OrderItemInterface;
import com.example.btlapi.Interface.PriceInterface;
import com.example.btlapi.Interface.TimeInterface;
import com.example.btlapi.Interface.UserInterface;
import com.example.btlapi.Retrofit.RetrofitClient;
public class utils {
    public static FoodInterface getFoodService() {
        return RetrofitClient.getRetrofit(baseUrl).create(FoodInterface.class);
    }

    public static CategoryInterface getCategoryService() {
        return RetrofitClient.getRetrofit(baseUrl).create(CategoryInterface.class);
    }

    public static LocationInterface getLocationService() {
        return RetrofitClient.getRetrofit(baseUrl).create(LocationInterface.class);
    }
    public static TimeInterface getTimeService() {
        return RetrofitClient.getRetrofit(baseUrl).create(TimeInterface.class);
    }
    public static PriceInterface getPriceService() {
        return RetrofitClient.getRetrofit(baseUrl).create(PriceInterface.class);
    }
    public static OrderItemInterface getOrderItemService() {
        return RetrofitClient.getRetrofit(baseUrl).create(OrderItemInterface.class);
    }
    public static OrderInterface getOrderService() {
        return RetrofitClient.getRetrofit(baseUrl).create(OrderInterface.class);
    }
    public static UserInterface getUserService() {
        return RetrofitClient.getRetrofit(baseUrl).create(UserInterface.class);
    }
}
