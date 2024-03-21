package com.dangchph33497.fpoly.dangchph33497_assignment1_and103.Model;

import com.google.gson.annotations.SerializedName;

public class Car {
    @SerializedName("_id")
    private String id;
    private String tenXe;
    private int gia;
    private String anh;
    private String loaiXe;

    public Car() {
    }

    public Car(String tenXe, int gia, String anh, String loaiXe) {
        this.tenXe = tenXe;
        this.gia = gia;
        this.anh = anh;
        this.loaiXe = loaiXe;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenXe() {
        return tenXe;
    }

    public void setTenXe(String tenXe) {
        this.tenXe = tenXe;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getLoaiXe() {
        return loaiXe;
    }

    public void setLoaiXe(String loaiXe) {
        this.loaiXe = loaiXe;
    }
}
