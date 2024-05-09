package com.example.btlapi.Domain;

import android.view.View;

import com.example.btlapi.Adapter.BestFoodAdapter;

public class Category {
    private int Id;
    private String CreateAt;
    private String ImagePath;
    private boolean IsDeleted;
    private String Names;
    private String UpdateAt;

    public Category(int id, String createAt, String imagePath, boolean isDeleted, String names, String updateAt) {
        Id = id;
        CreateAt = createAt;
        ImagePath = imagePath;
        IsDeleted = isDeleted;
        Names = names;
        UpdateAt = updateAt;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCreateAt() {
        return CreateAt;
    }

    public void setCreateAt(String createAt) {
        CreateAt = createAt;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }

    public String getName() {
        return Names;
    }

    public void setName(String name) {
        Names = name;
    }

    public String getUpdateAt() {
        return UpdateAt;
    }

    public void setUpdateAt(String updateAt) {
        UpdateAt = updateAt;
    }
}
