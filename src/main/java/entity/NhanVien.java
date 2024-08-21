package entity;

import java.sql.Date;

public class NhanVien {
	private String maNV, hoTen, email, sdt;
	private boolean gioiTinh;
	private Date ngaySinh;
	private String chucVu;
	private String diaChi;
	private double luong;

	public NhanVien() {

	}

//	public NhanVien(String maNV) {
//		this.maNV = maNV;
//	}
	

	public NhanVien(String hoTen) {
		super();
		this.hoTen = hoTen;
	}

	public NhanVien(String maNV, String hoTen, String email, String sdt, boolean gioiTinh, Date ngaySinh, String chucVu,
			String diaChi, double luong) {
		this.maNV = maNV;
		this.hoTen = hoTen;
		this.email = email;
		this.sdt = sdt;
		this.gioiTinh = gioiTinh;
		this.ngaySinh = ngaySinh;
		this.chucVu = chucVu;
		this.diaChi = diaChi;
		this.luong = luong;
	}

	public String getMa() {
		return maNV;
	}

	public void setMa(String maNV) {
		this.maNV = maNV;
	}

	public String getHoTen() {
		return hoTen;
	}

	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}

	public boolean isGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getChucVu() {
		return chucVu;
	}

	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public double getLuong() {
		return luong;
	}

	public void setLuong(double luong) {
		this.luong = luong;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((maNV == null) ? 0 : maNV.hashCode());
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
		NhanVien other = (NhanVien) obj;
		if (maNV == null) {
			if (other.maNV != null)
				return false;
		} else if (!maNV.equals(other.maNV))
			return false;
		return true;
	}

}
