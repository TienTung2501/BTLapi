package com.example.btlapi;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.example.btlapi.Domain.Food;
import com.example.btlapi.Domain.Order;
import com.example.btlapi.Domain.OrderItem;

import java.io.File;
import java.util.ArrayList;

public class GlobalVariable {
    public static final String baseUrl="http://192.168.0.102:333";
    public static int userId=-1;
    public static String userName="";
    public static String phone="";
    public static String email="";
    public static String imagePath="";
    public static boolean islogin=false;
    public static String password="";
    public static ArrayList<OrderItem> listOrderItem=new ArrayList<>();
    public static ArrayList<Order> listOrder=new ArrayList<>();
    public static ArrayList<Food> listFood=new ArrayList<>();

    public static String address="";
    public static  void displayImageFromDirectory(Context context, String imageName) {
        // Get the directory for saving images (external storage)
        File directory = new File(Environment.getExternalStorageDirectory(), "MyAppImages");

        // Create the file object for the image with the specified name
        File imageFile = new File(directory, imageName + ".jpg");

        // Check if the image file exists
        if (imageFile.exists()) {
            // Create a Uri from the file path
            Uri imageUri = Uri.fromFile(imageFile);

            // Display the image using the Uri
            // For example: imageView.setImageURI(imageUri);
        } else {
            // Image file not found, handle accordingly
            Toast.makeText(context, "Image not found", Toast.LENGTH_SHORT).show();
        }
    }

}
