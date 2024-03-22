package com.dangchph33497.fpoly.dangchph33497_assignment1_and103;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.dangchph33497.fpoly.dangchph33497_assignment1_and103.Model.ApiService;
import com.dangchph33497.fpoly.dangchph33497_assignment1_and103.Model.Car;
import com.dangchph33497.fpoly.dangchph33497_assignment1_and103.Model.CarWithoutImage;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddCar extends AppCompatActivity {
    EditText edtTenXe,edtGia,edtLoai;
    Button btnThem;
    ApiService apiService;
    CarAdapter carAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_car);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiService.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

        edtTenXe = findViewById(R.id.edtTenXe);
        edtGia = findViewById(R.id.edtGia);
        edtLoai = findViewById(R.id.edtLoai);
        btnThem = findViewById(R.id.btnThem);

        btnThem.setOnClickListener(v -> addCar());
    }
    private void addCar() {
        String tenXe = edtTenXe.getText().toString();
        int gia = Integer.parseInt(edtGia.getText().toString());
        String loaiXe = edtLoai.getText().toString();

        CarWithoutImage car = new CarWithoutImage(tenXe, gia, loaiXe);

        Call<Response<Car>> call = apiService.addCar(car);
        carAdapter = new CarAdapter(new ArrayList<>(), apiService, this);
        call.enqueue(new Callback<Response<Car>>() {
            @Override
            public void onResponse(Call<Response<Car>> call, retrofit2.Response<Response<Car>> response) {
                if (response.isSuccessful()) {
                    Response<Car> responseData = response.body();
                    if (responseData != null) {
                        Car addedCar = responseData.body(); // Access the data directly from the response body
                        if (addedCar != null) {
                            carAdapter.carList.add(addedCar);
                            carAdapter.notifyDataSetChanged();
                            Toast.makeText(AddCar.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Log.e("TAG", "Server error: " + response.code());
                    Toast.makeText(AddCar.this, "Thêm không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response<Car>> call, Throwable t) {
                Toast.makeText(AddCar.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}