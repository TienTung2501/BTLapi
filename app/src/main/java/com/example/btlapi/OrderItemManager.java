package com.example.btlapi;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.btlapi.Domain.OrderItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderItemManager {

    private static final String PREF_NAME = "OrderItemPreferences";

    // Lưu trữ các danh sách OrderItem theo key là OrderId
    private static Map<Integer, List<OrderItem>> orderItemMap = new HashMap<>();
    public static void addNewOrderItem(Context context, int userId, List<OrderItem> orderItem) {
        // Thêm OrderItem mới vào bộ nhớ đệm
        orderItemMap.put(userId, orderItem);
        // Lưu OrderItem mới vào SharedPreferences
        saveOrderItems(context, userId, orderItem);
    }
//    public static void updateOrderItemQuantity(Context context, int userId,OrderItem orderItemId, int productId, int quantity) {
//        // Kiểm tra xem OrderItem đã tồn tại cho người dùng này chưa
//        if (orderItemMap.containsKey(userId)) {
//            // Lấy OrderItem tương ứng từ bộ nhớ đệm
//            List<OrderItem> orderItems = orderItemMap.get(userId);
//            if (orderItems != null) {
//                // Duyệt qua danh sách OrderItem để tìm OrderItem có productId cần lấy
//                for (OrderItem orderItem : orderItems) {
//                    if (orderItem.getProductId() == productId && orderItem.getOrderId()==) {
//                        orderItem.setQuantity(quantity);
//                    }
//                }
//            }
//            // Cập nhật số lượng sản phẩm cho OrderItem này
//
//            // Lưu OrderItem cập nhật vào bộ nhớ đệm
//            orderItemMap.put(userId, orderItem);
//            // Lưu cập nhật vào SharedPreferences
//            saveOrderItem(context, userId, orderItem);
//        }
//    }
    public static void removeOrderItem(Context context, int userId, int productId) {
        // Kiểm tra xem OrderItem đã tồn tại cho người dùng này chưa
        if (orderItemMap.containsKey(userId)) {
            List<OrderItem> orderItems = orderItemMap.get(userId);
            if (orderItems != null) {
                // Duyệt qua danh sách OrderItem để tìm OrderItem có productId cần lấy
                for (OrderItem orderItem : orderItems) {
                    if (orderItem.getProductId() == productId) {
                        orderItems.remove(orderItem);
                        break;
                    }
                }
                editOrderItems(context,userId,orderItems);
            }

        }
    }
    public static void clearOrderItems(Context context, int userId) {
        // Xóa OrderItem khỏi bộ nhớ đệm
        orderItemMap.remove(userId);

        // Lấy SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // Xóa OrderItem khỏi SharedPreferences
        sharedPreferences.edit().remove(String.valueOf(userId)).apply();
    }

    public static void saveOrderItems(Context context, int userId, List<OrderItem> orderItems) {
        // Lấy SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // Chuyển đổi danh sách OrderItem thành chuỗi JSON
        Gson gson = new Gson();
        String orderItemsJson = gson.toJson(orderItems);

        // Lưu chuỗi JSON vào SharedPreferences với key là userId
        sharedPreferences.edit().putString(String.valueOf(userId), orderItemsJson).apply();

        // Lưu danh sách OrderItem vào bộ nhớ đệm tạm thời để truy cập nhanh hơn
        orderItemMap.put(userId, orderItems);
    }
    public static  boolean findOrderUser (Context context, int userId){
        // Kiểm tra xem OrderItem đã được lưu trong bộ nhớ đệm tạm thời chưa
        if (orderItemMap.containsKey(userId)) {
            return true;
        }

        // Lấy SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // Lấy chuỗi JSON từ SharedPreferences dựa trên userId
        String savedOrderItemsJson = sharedPreferences.getString(String.valueOf(userId),"x");
        if (savedOrderItemsJson =="x") return false;
        else return true;
    }
    public static void editOrderItems(Context context, int userId, List<OrderItem> newOrderItems) {
        // Lấy SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // Lấy chuỗi JSON từ SharedPreferences dựa trên userId
        String savedOrderItemsJson = sharedPreferences.getString(String.valueOf(userId), "");

        // Chuyển đổi chuỗi JSON thành danh sách OrderItem
        Gson gson = new Gson();
        List<OrderItem> savedOrderItems = gson.fromJson(savedOrderItemsJson, new TypeToken<List<OrderItem>>(){}.getType());

        if (savedOrderItems != null) {
            // Thực hiện thay đổi trong danh sách OrderItem
            // Ví dụ: Gán danh sách OrderItem mới
            savedOrderItems = newOrderItems;

            // Chuyển đổi danh sách OrderItem thành chuỗi JSON mới
            String newOrderItemsJson = gson.toJson(savedOrderItems);

            // Lưu chuỗi JSON mới vào SharedPreferences với key là userId
            sharedPreferences.edit().putString(String.valueOf(userId), newOrderItemsJson).apply();

            // Cập nhật lại danh sách OrderItem trong bộ nhớ đệm tạm thời (nếu cần thiết)
            orderItemMap.put(userId, savedOrderItems);
        }
    }

    public static List<OrderItem> getOrderItems(Context context, int userId) {
        // Kiểm tra xem OrderItem đã được lưu trong bộ nhớ đệm tạm thời chưa
        if (orderItemMap.containsKey(userId)) {
            return orderItemMap.get(userId);
        }

        // Lấy SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // Lấy chuỗi JSON từ SharedPreferences dựa trên userId
        String savedOrderItemsJson = sharedPreferences.getString(String.valueOf(userId), "");

        // Chuyển đổi chuỗi JSON thành danh sách OrderItem
        Gson gson = new Gson();
        List<OrderItem> listOrderItems = gson.fromJson(savedOrderItemsJson, new TypeToken<List<OrderItem>>(){}.getType());


        // Lưu OrderItem vào bộ nhớ đệm tạm thời để truy cập nhanh hơn
        orderItemMap.put(userId, listOrderItems);

        return listOrderItems;
    }
}
