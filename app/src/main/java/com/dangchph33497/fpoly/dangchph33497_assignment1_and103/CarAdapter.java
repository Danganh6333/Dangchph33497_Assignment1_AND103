package com.dangchph33497.fpoly.dangchph33497_assignment1_and103;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.dangchph33497.fpoly.dangchph33497_assignment1_and103.Model.ApiService;
import com.dangchph33497.fpoly.dangchph33497_assignment1_and103.Model.Car;
import com.dangchph33497.fpoly.dangchph33497_assignment1_and103.Model.CarUpdate;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolderOfCars> {

    private List<Car> carList;
    private ApiService apiService;
    private Context context;

    public CarAdapter(List<Car> carList, ApiService apiService, Context context) {
        this.carList = carList;
        this.apiService = apiService;
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
        holder.tvTenXe.setText(carList.get(position).getTenXe());
        holder.tvGiaXe.setText(String.valueOf(carList.get(position).getGia()));
        holder.tvLoaiXe.setText(carList.get(position).getLoaiXe());
        String imageUrl = ApiService.DOMAIN + carList.get(position).getAnh();
        Picasso.get().load(imageUrl).into(holder.imgAvatar);
        holder.btnXoa.setOnClickListener(v -> {
            int position1 = holder.getBindingAdapterPosition();
            if (position1 != RecyclerView.NO_POSITION) {
                deleteCar(position1);
            }
        });
        holder.btnSua.setOnClickListener(v -> {
            int position12 = holder.getBindingAdapterPosition();
            if (position12 != RecyclerView.NO_POSITION) {
                updateCar(position12);
            }
        });
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public static class ViewHolderOfCars extends RecyclerView.ViewHolder {
        TextView tvTenXe,tvGiaXe,tvLoaiXe;
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
        Car car = carList.get(position);
        String carId = car.getId();
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
                                    carList.remove(position);
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
    public void updateCar(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.update_car, null);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        Button btnUpdate = view.findViewById(R.id.btnUpdate);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        EditText edtTenXe = view.findViewById(R.id.edtTenXe);
        EditText edtGia = view.findViewById(R.id.edtGia);
        Spinner spinnerLoai = view.findViewById(R.id.spinnerLoai);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.loai_xe_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLoai.setAdapter(adapter);
        edtTenXe.setText(""+carList.get(position).getTenXe());
        edtGia.setText(""+carList.get(position).getGia());
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenXe = edtTenXe.getText().toString();
                int gia = Integer.parseInt(edtGia.getText().toString());
                String loaiXe = spinnerLoai.getSelectedItem().toString();
                if (tenXe.isEmpty() || gia == 0) {
                    Toast.makeText(context, "Mời không để trống trường dữ liệu", Toast.LENGTH_SHORT).show();
                    return;
                }
                CarUpdate carUpdateRequest = new CarUpdate(
                        tenXe,
                        gia,
                        loaiXe
                );
                Call<Response<Car>> call = apiService.updateCarById(carList.get(position).getId(), carUpdateRequest);

                call.enqueue(new Callback<Response<Car>>() {
                    @Override
                    public void onResponse(Call<Response<Car>> call, Response<Response<Car>> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(context, "Thành công cập nhật", Toast.LENGTH_SHORT).show();
                            Car updatedCar = carList.get(position);
                            updatedCar.setTenXe(tenXe);
                            updatedCar.setGia(gia);
                            updatedCar.setLoaiXe(loaiXe);
                            notifyItemChanged(position);
                        } else {
                            Toast.makeText(context, "Cập Nhật thất bại", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<Response<Car>> call, Throwable t) {
                        Toast.makeText(context, "Cập Nhật thất bại", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
