package entity;

import java.sql.Date;

public class ChiTietHoaDon {
    private int soLuong;
    private double thanhTien;
    private double tienGiam;
    private HoaDon hoaDon;
    private SanPham sanPham;

    
    


	public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(HoaDon hoaDon, SanPham sanPham) {
        this.hoaDon = hoaDon;
        this.sanPham = sanPham;
        this.thanhTien = 0;
        this.soLuong = 0;
        this.tienGiam = 0;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    public double getTienGiam() {
        return tienGiam;
    }

    public void setTienGiam(double tienGiam) {
        this.tienGiam = tienGiam;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    // method

    public int getSoLuong() {
        return this.soLuong;
    }

    public int tinhSoLuong(int quantity) {
        return this.soLuong = quantity;
    }

    public double tinhThanhTien(double donGia) {
        return this.thanhTien = this.soLuong * donGia;
    }

    public double tinhTienGiam() {
        return this.tienGiam = this.soLuong * 1000;
    }

    public double tinhTienGiam(int diemTL) {
        return this.tienGiam = diemTL * 1000;
    }

	@Override
	public String toString() {
		return "ChiTietHoaDon [soLuong=" + soLuong + ", thanhTien=" + thanhTien + ", tienGiam=" + tienGiam + ", hoaDon="
				+ hoaDon + ", sanPham=" + sanPham + "]";
	}
    
    

	
    
    
}
