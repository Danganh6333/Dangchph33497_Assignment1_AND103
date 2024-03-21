package com.dangchph33497.fpoly.dangchph33497_assignment1_and103;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.dangchph33497.fpoly.dangchph33497_assignment1_and103.Model.ApiService;
import com.dangchph33497.fpoly.dangchph33497_assignment1_and103.Model.Car;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolderOfCars> {

    private List<Car> carModelList;
    Context context;


    public CarAdapter(List<Car> carModelList, Context context) {
        this.carModelList = carModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderOfCars onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_car,parent,false);
        return new ViewHolderOfCars(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderOfCars holder, int position) {
        holder.tvTenXe.setText(carModelList.get(position).getTenXe());
        holder.tvGiaXe.setText(String.valueOf(carModelList.get(position).getGia()));
        holder.tvLoaiXe.setText(carModelList.get(position).getLoaiXe());
        String imageUrl = ApiService.DOMAIN + carModelList.get(position).getAnh();
        Picasso.get().load(imageUrl).into(holder.imgAvatar);
        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCar(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return carModelList.size();
    }

    public static class ViewHolderOfCars extends RecyclerView.ViewHolder {
        TextView tvID,tvTenXe,tvGiaXe,tvLoaiXe;
        ImageView imgAvatar;
        ImageButton btnXoa,btnSua;
        public ViewHolderOfCars(@NonNull View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.ivAnhXe);
            tvTenXe = itemView.findViewById(R.id.tvTenXe);
            tvGiaXe =  itemView.findViewById(R.id.tvGiaXe);
            tvLoaiXe =  itemView.findViewById(R.id.tvLoaiXe);
            btnSua = itemView.findViewById(R.id.btnSua);
            btnXoa = itemView.findViewById(R.id.btnXoa);

        }
    }
    public void deleteCar(int position) {
        Car car = carModelList.get(position);
        String carId = car.getId(); // Assuming there's a method getId() in CarModel
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Bạn có muốn xóa?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ApiService apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                        Call<Response<Void>> call = apiService.deleteCarById(carId);
                        call.enqueue(new Callback<Response<Void>>() {
                            @Override
                            public void onResponse(Call<Response<Void>> call, Response<Response<Void>> response) {
                                if (response.isSuccessful()) {
                                    carModelList.remove(position);
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Xóa không thành công", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Response<Void>> call, Throwable t) {
                                Log.e("Delete Error", t.getMessage());
                                Toast.makeText(context, "Lỗi khi xóa dữ liệu", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }
    public void updateCar (){

    }

}
