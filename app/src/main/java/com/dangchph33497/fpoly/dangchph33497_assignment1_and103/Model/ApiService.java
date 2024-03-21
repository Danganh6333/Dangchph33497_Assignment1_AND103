package com.dangchph33497.fpoly.dangchph33497_assignment1_and103.Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    String DOMAIN = "http://192.168.1.154:3000/";

    @GET("/")
    Call<List<Car>> getCars();

    @DELETE("xoa/{id}")
    Call<Response<Void>> deleteCarById(@Path("id") String id);
}
