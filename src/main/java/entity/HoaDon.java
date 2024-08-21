package entity;

import java.sql.Date;
import java.time.LocalDateTime;

public class HoaDon {
    private String maHoaDon;
    private Date ngayLapHD;
    private double tongTien;
    private double tongThanhToan;
    private KhachHang khachHang;
    private NhanVien nhanVien;
    private LocalDateTime gioVao;

    public HoaDon() {
    }

    public HoaDon(String maHoaDon, Date ngayLapHD, KhachHang khachHang, NhanVien nhanVien, LocalDateTime gioVao) {
        this.maHoaDon = maHoaDon;
        this.ngayLapHD = ngayLapHD;
        this.khachHang = khachHang;
        this.nhanVien = nhanVien;
        this.gioVao = gioVao;
        this.tongTien = 0;
        this.tongThanhToan = 0;
    }

    public HoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public Date getNgayLapHD() {
        return ngayLapHD;
    }

    public void setNgayLapHD(Date ngayLapHD) {
        this.ngayLapHD = ngayLapHD;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public LocalDateTime getGioVao() {
        return gioVao;
    }

    public void setGioVao(LocalDateTime gioVao) {
        this.gioVao = gioVao;
    }

    public double getTongTien() {
        return this.tongTien;
    }

    public void setTongTien(double thanhTien) {
        this.tongTien += thanhTien;
    }

    public double getTongThanhToan() {
        return this.tongThanhToan;
    }

    public double tinhTongThanhToan(double tienGiam) {
        return this.tongThanhToan = this.tongTien - tienGiam;
    }

    public void setTongThanhToan(double tongTT) {
        this.tongThanhToan = tongTT;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((maHoaDon == null) ? 0 : maHoaDon.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        HoaDon other = (HoaDon) obj;
        if (maHoaDon == null) {
            if (other.maHoaDon != null)
                return false;
        } else if (!maHoaDon.equals(other.maHoaDon))
            return false;
        return true;
    }

}