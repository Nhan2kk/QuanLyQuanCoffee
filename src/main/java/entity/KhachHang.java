package entity;

import java.util.Objects;

public class KhachHang {
	private String maKH;
	private String tenKH;
	private String sdt;
	private int diemTL;

	public String getMaKH() {
		return maKH;
	}

	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}

	public String getTenKH() {
		return tenKH;
	}

	public void setTenKH(String tenKH) {
		this.tenKH = tenKH;
	}

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}

	public KhachHang() {
	}

	public KhachHang(String maKH, String tenKH, String sdt) {
		super();
		this.maKH = maKH;
		this.tenKH = tenKH;
		this.sdt = sdt;
		this.diemTL = 0;
	}

	public KhachHang(String tenKH) {
		this.tenKH = tenKH;
	}

	public void tinhDiemTL(int quantity) {
		diemTL += quantity;
	}

	public int getDiemTL() {
		return this.diemTL;
	}

	public void setDiemTL(int diemTL) {
		this.diemTL = diemTL;
	}

	@Override
	public int hashCode() {
		return Objects.hash(maKH);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KhachHang other = (KhachHang) obj;
		return Objects.equals(maKH, other.maKH);
	}

}
