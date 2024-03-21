package com.dangchph33497.fpoly.dangchph33497_assignment1_and103;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dangchph33497.fpoly.dangchph33497_assignment1_and103.Model.ApiService;
import com.dangchph33497.fpoly.dangchph33497_assignment1_and103.Model.Car;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    RecyclerView lvMain;
    List<Car> carList;
    CarAdapter carAdapter;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        fab = findViewById(R.id.fab);
        lvMain = findViewById(R.id.listviewMain);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,AddCar.class);
            startActivity(intent);
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        lvMain.setLayoutManager(linearLayoutManager);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService.DOMAIN).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<Car>> call = apiService.getCars();

        call.enqueue(new Callback<List<Car>>() {
            @Override
            public void onResponse(Call<List<Car>> call, Response<List<Car>> response) {
                if(response.isSuccessful()){
                    carList = response.body();
                    ApiService apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                    carAdapter = new CarAdapter(carList,apiService,MainActivity.this);
                    lvMain.setAdapter(carAdapter);
                }
            }
            @Override
            public void onFailure(Call<List<Car>> call, Throwable t) {
                Log.e("Main", t.getMessage());
            }
        });
    }
}