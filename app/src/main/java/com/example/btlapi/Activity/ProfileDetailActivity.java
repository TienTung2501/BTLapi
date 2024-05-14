package com.example.btlapi.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.InputType;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.btlapi.Domain.User;
import com.example.btlapi.GlobalVariable;
import com.example.btlapi.Interface.UserInterface;
import com.example.btlapi.R;
import com.example.btlapi.Utils.utils;
import com.example.btlapi.databinding.ActivityProfileDetailBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileDetailActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SELECT_IMAGE = 100;
    private Uri selectedImageUri; // Biến lưu trữ Uri của ảnh đã chọn

    public static boolean isChoseUpdate=false;
    private int userId;
    private String  userName,phone,gmail,passWord,imagePath;
    ActivityProfileDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityProfileDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getIntentExtra();
        loadInfo();
        eventListener();
    }

    private void eventListener() {
        binding.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 2. Cho phép nhập vào các EditText và click vào TextView "UploadAvatar" khi nhấn nút chỉnh sửa
                binding.uploadAvatarTxt.setVisibility(View.VISIBLE);
                binding.edittextUserName.setEnabled(true);
                binding.edittextPhoneNumber.setEnabled(true);
                binding.edittextGmail.setEnabled(true);
                binding.edittextPassword.setEnabled(true);
                binding.uploadAvatarTxt.setClickable(true);
                binding.uploadAvatarTxt.setFocusable(true);

                // Hiển thị nút Save
                binding.saveBtn.setVisibility(View.VISIBLE);
            }
        });
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name,phonee,gmaill,passwordd,imagePathh;
                name=binding.edittextUserName.getText().toString();
                phonee=binding.edittextPhoneNumber.getText().toString();
                gmaill=binding.edittextGmail.getText().toString();
                passwordd=binding.edittextPassword.getText().toString();
                if (selectedImageUri != null || (!name.equals(userName) || !phone.equals(phone) || !gmail.equals(gmail) || !passwordd.equals(passWord))) {
                    try {
                        // Lấy tên file từ Uri và sao chép vào thư mục
                        String fileName = getFileName(selectedImageUri);
                        copyImageToDrawable(selectedImageUri, fileName);
                        // Cập nhật đường dẫn ảnh cho biến imagePath
                        imagePath = fileName;
                        // Cập nhật thông tin người dùng
                        updateUser(name, phonee, gmaill, passwordd, imagePath);
                        isChoseUpdate=false;
                        binding.saveBtn.setClickable(false);
                        getUserFromApi(phonee,passwordd);
                        loadInfo();
                        Toast.makeText(ProfileDetailActivity.this, "Update Sucess", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        });
        binding.uploadAvatarTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            try {
                // Hiển thị ảnh trong ImageView
                binding.picUpdate.setImageURI(selectedImageUri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Phương thức để lấy tên file từ Uri
    @SuppressLint("Range")
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    // Phương thức để copy ảnh vào thư mục drawable
    private void copyImageToDrawable(Uri uri, String fileName) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        File file = new File(getFilesDir() + "/drawable", fileName);
        OutputStream outputStream = new FileOutputStream(file);
        byte[] buf = new byte[1024];
        int len;
        while ((len = inputStream.read(buf)) > 0) {
            outputStream.write(buf, 0, len);
        }
        inputStream.close();
        outputStream.close();
    }

    private void getIntentExtra() {
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void loadInfo(){
        userName=GlobalVariable.userName;
        phone=GlobalVariable.phone;
        gmail=GlobalVariable.email;
        passWord=GlobalVariable.password;
        imagePath=GlobalVariable.imagePath;
        userId=GlobalVariable.userId;
        binding.saveBtn.setVisibility(View.GONE);
        binding.userNameTxt.setText(userName);
        binding.edittextUserName.setText(userName);
        binding.edittextPhoneNumber.setText(phone);
        binding.edittextGmail.setText(gmail);
        binding.edittextPassword.setText(passWord);
        binding.edittextUserName.setEnabled(false);
        binding.edittextPhoneNumber.setEnabled(false);
        binding.edittextGmail.setEnabled(false);
        binding.edittextPassword.setEnabled(false);
        binding.uploadAvatarTxt.setVisibility(View.GONE);

        int imageResourceId = getImageResource(imagePath);

        if (imageResourceId != 0) {
            Glide.with(ProfileDetailActivity.this)
                    .load(imageResourceId)
                    .transform(new CenterCrop(), new RoundedCorners(30))
                    .into(binding.picAvatar);
            Glide.with(ProfileDetailActivity.this)
                    .load(imageResourceId)
                    .transform(new CenterCrop(), new RoundedCorners(30))
                    .into(binding.picUpdate);
        } else {
            binding.picAvatar.setImageResource(R.drawable.google);
        }
    }
    private int getImageResource(String imageName) {
        return getResources().getIdentifier(imageName, "drawable", getPackageName());
    }
    // Trong phương thức để gọi API cập nhật thông tin người dùng
    private void updateUser(String username,String phone,String gmail,String passWord,String imagePath) {
        User user = new User(userId,username,phone,gmail,passWord,imagePath);
        UserInterface userInterface;
        userInterface= utils.getUserService();
        Call<User> call = userInterface.updateUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    // Xử lý khi cập nhật thành công
                    Toast.makeText(ProfileDetailActivity.this, "Update Sucess", Toast.LENGTH_SHORT).show();
                } else {
                    // Xử lý khi cập nhật thất bại
                    Toast.makeText(ProfileDetailActivity.this, "Update Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Xử lý khi gặp lỗi trong quá trình gọi API
                Toast.makeText(ProfileDetailActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getUserFromApi(String mobile, String password) {
        UserInterface userInterface = utils.getUserService();
        userInterface.getUser(mobile, password).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User result=response.body();
                    GlobalVariable.islogin = true;
                    GlobalVariable.userId = result.getId();
                    GlobalVariable.userName = result.getNames();
                    GlobalVariable.phone = result.getMobile();
                    GlobalVariable.email = result.getAddresss();
                    GlobalVariable.imagePath=result.getImagePath();
                    GlobalVariable.password=result.getPasswords();
                } else {
                    Toast.makeText(ProfileDetailActivity.this, "No data user respone", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ProfileDetailActivity.this, "Fetch Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}