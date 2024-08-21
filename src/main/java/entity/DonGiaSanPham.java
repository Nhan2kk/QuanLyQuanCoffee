package entity;

public class DonGiaSanPham {
	private double donGia;
	private String kichThuoc;
	private SanPham sanPham;

	public DonGiaSanPham(double donGia, String kichThuoc, SanPham sanPham) {
		this.donGia = donGia;
		this.kichThuoc = kichThuoc;
		this.sanPham = sanPham;
	}

	public double getDonGia() {
		return donGia;
	}

	public void setDonGia(double donGia) {
		this.donGia = donGia;
	}

	public String getKichThuoc() {
		return kichThuoc;
	}

	public void setKichThuoc(String kichThuoc) {
		this.kichThuoc = kichThuoc;
	}

	public SanPham getSanPham() {
		return sanPham;
	}

	public void setSanPham(SanPham sanPham) {
		this.sanPham = sanPham;
	}

}
