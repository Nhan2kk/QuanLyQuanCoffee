package entity;

import java.sql.Date;
import java.util.Objects;

public class SanPham {
	private String maSP;
	private String tenSP;
	private String image;
	private boolean trangThai;
	private Date ngayCN;
	private LoaiSanPham loaiSP;

	public SanPham() {
	}

	public SanPham(String maSP) {
		this.maSP = maSP;
	}

	public SanPham(String maSP, String tenSP, boolean trangThai, Date ngayCN, LoaiSanPham loaiSP) {
		this.maSP = maSP;
		this.tenSP = tenSP;
		this.trangThai = trangThai;
		this.ngayCN = ngayCN;
		this.loaiSP = loaiSP;
	}

	public SanPham(String maSP, String tenSP, String image, boolean trangThai, Date ngayCN, LoaiSanPham loaiSP) {
		this.maSP = maSP;
		this.tenSP = tenSP;
		this.image = image;
		this.trangThai = trangThai;
		this.ngayCN = ngayCN;
		this.loaiSP = loaiSP;
	}

	public String getMaSP() {
		return maSP;
	}

	public void setMaSP(String maSP) {
		this.maSP = maSP;
	}

	public String getTenSP() {
		return tenSP;
	}

	public void setTenSP(String tenSP) {
		this.tenSP = tenSP;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}

	public Date getNgayCN() {
		return ngayCN;
	}

	public void setNgayCN(Date ngayCN) {
		this.ngayCN = ngayCN;
	}

	public LoaiSanPham getLoaiSP() {
		return loaiSP;
	}

	public void setMLoaiSP(LoaiSanPham loaiSP) {
		this.loaiSP = loaiSP;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maSP);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SanPham other = (SanPham) obj;
		return Objects.equals(maSP, other.maSP);
	}

	@Override
	public String toString() {
		return "SanPham [maSP=" + maSP + ", tenSP=" + tenSP + ", image=" + image + ", trangThai=" + trangThai
				+ ", ngayCN=" + ngayCN + ", loaiSP=" + loaiSP + "]";
	}
	
	

}
