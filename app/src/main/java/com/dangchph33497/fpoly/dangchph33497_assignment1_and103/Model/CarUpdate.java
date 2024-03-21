package com.dangchph33497.fpoly.dangchph33497_assignment1_and103.Model;

public class CarUpdate {
    private String tenXe;
    private int gia;
    private String loaiXe;

    public CarUpdate() {
    }

    public CarUpdate(String tenXe, int gia, String loaiXe) {
        this.tenXe = tenXe;
        this.gia = gia;
        this.loaiXe = loaiXe;
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

    public String getLoaiXe() {
        return loaiXe;
    }

    public void setLoaiXe(String loaiXe) {
        this.loaiXe = loaiXe;
    }
}
