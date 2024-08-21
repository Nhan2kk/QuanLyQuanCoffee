package entity;

public class Ban {
	private String maBan;
	private String tenBan;
	private HoaDon hoaDon;

	public Ban() {
	}

	public Ban(String maBan, String tenBan, HoaDon hoaDon) {
		this.maBan = maBan;
		this.tenBan = tenBan;
		this.hoaDon = hoaDon;
	}

	public String getMaBan() {
		return maBan;
	}

	public void setMaBan(String maBan) {
		this.maBan = maBan;
	}

	public String getTenBan() {
		return tenBan;
	}

	public void setTenBan(String tenBan) {
		this.tenBan = tenBan;
	}

	public HoaDon getHoaDon() {
		return hoaDon;
	}

	public void setHoaDon(HoaDon hoaDon) {
		this.hoaDon = hoaDon;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((maBan == null) ? 0 : maBan.hashCode());
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
		Ban other = (Ban) obj;
		if (maBan == null) {
			if (other.maBan != null)
				return false;
		} else if (!maBan.equals(other.maBan))
			return false;
		return true;
	}

}
