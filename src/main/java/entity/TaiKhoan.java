package entity;

public class TaiKhoan {
    private String tenTaiKhoan;
    private String matKhau;
    private NhanVien nhanVien;

    public TaiKhoan(String tenTaiKhoan, String matKhau, NhanVien nhanVien) {
        this.tenTaiKhoan = tenTaiKhoan;
        this.matKhau = matKhau;
        this.nhanVien = nhanVien;
    }

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tenTaiKhoan == null) ? 0 : tenTaiKhoan.hashCode());
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
        TaiKhoan other = (TaiKhoan) obj;
        if (tenTaiKhoan == null) {
            if (other.tenTaiKhoan != null)
                return false;
        } else if (!tenTaiKhoan.equals(other.tenTaiKhoan))
            return false;
        return true;
    }

}
