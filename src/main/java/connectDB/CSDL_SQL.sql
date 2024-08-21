CREATE DATABASE QuanLyBanHang_Coffee_Nhom20
GO

USE QuanLyBanHang_Coffee_Nhom20
GO

CREATE TABLE LoaiSanPham
(
	maLoai NVARCHAR(10) PRIMARY KEY NOT NULL,
	tenLoai NVARCHAR(50) NULL
)
GO

	CREATE TABLE SanPham 
	(
		maSP NVARCHAR(10) PRIMARY KEY NOT NULL,
		tenSP NVARCHAR(50) NULL,
		hinhAnh NVARCHAR(500) NULL,
		trangThai NVARCHAR(10)  NULL,
		ngayCapNhat DATE NULL,
		maLoai NVARCHAR(10) NOT NULL FOREIGN KEY REFERENCES LoaiSanPham(maLoai)
	)
	GO

	CREATE TABLE DonGiaSanPham
	(
		maDGSP INT IDENTITY(1,1) PRIMARY KEY,
		donGia FLOAT NULL,
		size CHAR(1) NULL,
		maSP NVARCHAR(10) NOT NULL FOREIGN KEY REFERENCES SanPham(maSP) ON DELETE CASCADE
	)
	GO

CREATE TABLE KhachHang
(
	maKH NVARCHAR(10) PRIMARY KEY NOT NULL,
	tenKH NVARCHAR(50) NULL,
	sdt NVARCHAR(15) NULL,
	diemTL INT 
)
GO

CREATE Table NhanVien
(
	maNV NVARCHAR(10) PRIMARY KEY NOT NULL,
	hoTen NVARCHAR(50) NULL,
	email NVARCHAR(50) NULL,
	sdt CHAR(10) NULL,
	ngaySinh DATE NULL,
	chucVu NVARCHAR(50) NULL,
	diaChi NVARCHAR(255) NULL,
	luong FLOAT NULL,
	gioiTinh BIT NULL
)
GO

-- Add ràng buộc 
ALTER TABLE HoaDon
ADD CONSTRAINT FK_KhachHang_HoaDon
FOREIGN KEY (maKH)
REFERENCES KhachHang(maKH)
ON DELETE RESTRICT;
GO

ALTER TABLE HoaDon
ADD CONSTRAINT FK_NhanVien_HoaDon
FOREIGN KEY (maNV)
REFERENCES NhanVien(maNV)
ON DELETE RESTRICT;
GO

CREATE TABLE TaiKhoan 
(
	tenTaiKhoan NVARCHAR(20) PRIMARY KEY,
	matKhau NVARCHAR(50) NULL,
	maNV NVARCHAR(10) NOT NULL FOREIGN KEY REFERENCES NhanVien(maNV) ON DELETE CASCADE
)
GO

CREATE TABLE HoaDon --- Thong ke doanh thu
(
	maHD NVARCHAR(6) PRIMARY KEY NOT NULL,
	ngayLapHD DATE NULL,
	tongTien MONEY NULL,
	tongThanhToan MONEY,
	maKH NVARCHAR(10) NOT NULL FOREIGN KEY REFERENCES KhachHang(maKH),
	maNV NVARCHAR(10) NOT NULL FOREIGN KEY REFERENCES NhanVien(maNV),
	gioVao DATETIME NULL,
)
GO

CREATE TABLE Ban
(
    maBan NVARCHAR(5) PRIMARY KEY NOT NULL,
    tenBan NVARCHAR(5) NULL,
    maHD NVARCHAR(6) NULL,
    FOREIGN KEY (maHD) REFERENCES HoaDon(maHD)
)
GO

CREATE TABLE ChiTietHoaDon
(
	soLuong INT NULL,
	thanhTien MONEY NULL,
	tienGiam MONEY NULL,
	maHD NVARCHAR(6) NOT NULL FOREIGN KEY REFERENCES HoaDon(maHD),
	maSP NVARCHAR(10) NOT NULL FOREIGN KEY REFERENCES SanPham(maSP),
	PRIMARY KEY(maHD, maSP)		
)
GO

INSERT INTO LoaiSanPham(maLoai, tenLoai)
VALUES	('L1', N'Cà phê'),
		('L2', N'Trà sữa'),
		('L3', N'Trà trái cây'),
		('L4', N'Nước ép'),
		('L5', N'Soda'),
		('L6', N'Sinh tố')
GO


SELECT sp.maSP, sp.tenSP, lsp.tenLoai, sp.trangThai, sp.ngayCapNhat
FROM SanPham sp 
JOIN LoaiSanPham lsp ON sp.maLoai = lsp.maLoai
GO

-- Thủ tục:
CREATE PROCEDURE GetAllProduct
AS
BEGIN
	SELECT sp.maSP, sp.tenSP, sp.hinhAnh, sp.trangThai, sp.ngayCapNhat, lsp.tenLoai
	FROM SanPham sp 
    JOIN LoaiSanPham lsp ON sp.maLoai = lsp.maLoai;
END
GO

CREATE PROCEDURE GetProductInfo
AS
BEGIN
    SELECT sp.maSP, sp.tenSP, lsp.tenLoai, sp.trangThai, sp.ngayCapNhat
    FROM SanPham sp 
    JOIN LoaiSanPham lsp ON sp.maLoai = lsp.maLoai;
END;
GO

CREATE PROCEDURE GetProductPriceByProductID @maSP NVARCHAR(10)
AS 
BEGIN
	SELECT sp.maSP, dgsp.size, dgsp.donGia
	FROM SanPham sp 
	JOIN DonGiaSanPham dgsp ON sp.maSP = dgsp.maSP
	WHERE dgsp.maSP = @maSP
END
GO

EXEC GetProductPriceByProductID @maSP = 'K123'
GO

CREATE PROCEDURE XoaSanPham
    @MaSP NVARCHAR(10)
AS
BEGIN
    SET NOCOUNT ON;

    -- Xóa dòng từ bảng SanPham dựa trên mã sản phẩm
    DELETE FROM SanPham
    WHERE maSP = @MaSP;
END;
GO

CREATE PROCEDURE XoaNhanVien
    @MaNV NVARCHAR(10)
AS
BEGIN
    SET NOCOUNT ON;

    -- Xóa dòng từ bảng NhanVien dựa trên mã nhân viên
    DELETE FROM NhanVien
    WHERE maNV = @MaNV;
END;
GO

CREATE PROCEDURE GetAccountOfEmployeeByID @maNV NVARCHAR(10)
AS
BEGIN
	SELECT tk.tenTaiKhoan, tk.matKhau
	FROM NhanVien nv 
	JOIN TaiKhoan tk ON nv.maNV = tk.maNV
	WHERE tk.maNV = @maNV
END
GO

CREATE PROCEDURE GetNameOfEmployeeWhenLogin @tenTaiKhoan NVARCHAR(20)
AS
BEGIN
	SELECT nv.hoTen
	FROM TaiKhoan tk 
	JOIN NhanVien nv ON tk.maNV = nv.maNV
	WHERE tk.tenTaiKhoan = @tenTaiKhoan
END
GO

EXEC GetNameOfEmployeeWhenLogin @tenTaiKhoan = 'tranvu23405'
GO

SELECT *
FROM SanPham sp 
JOIN DonGiaSanPham dgsp ON sp.maSP = dgsp.maSP
JOIN ChiTietHoaDon cthd ON sp.maSP = cthd.maSP
JOIN HoaDon hd ON cthd.maHD = hd.maHD
GO

CREATE PROCEDURE GetAccountByIDEmp @maNV NVARCHAR(10)
AS
BEGIN
	SELECT tk.tenTaiKhoan, tk.matKhau
	FROM NhanVien nv 
	JOIN TaiKhoan tk ON nv.maNV = tk.maNV
	WHERE tk.maNV = @maNV
END
GO

EXEC GetAccountByIDEmp @maNV = 'NV1'
GO

INSERT INTO KhachHang
VALUES('KH001',N'Hồ Quang Nhân','0111111111',25),
	  ('KH002',N'Trần Long Vũ','0222222222',19),
	  ('KH003',N'Lê Nguyễn Quang Vinh','0333333333',18),
	  ('KH004',N'Hồ Quang Minh','0444444444',15),
	  ('KH005',N'Trần Tiểu MY','0555555555',35),
	  ('KH006',N'Lê Tuyết Như','0666666666',7),
	  ('KH007',N'Lê Hồng Ánh','0777777777',24),
	  ('KH008',N'Trần Ngọc Ân','0888888888',16),
	  ('KH009',N'Huỳnh Gia Quý','0999999999',5),
	  ('KH010',N'Đoàn Thanh Huy','0123456789',25),
	  ('KH011',N'Gia Long Phúc','0987654321',14),
	  ('KH012',N'Lý An Nhiên','0112345678',11),
	  ('KH013',N'Hạo Thần Long','0111234567',31),
	  ('KH014',N'Nguyễn Uyên My','0111123456',49),
	  ('KH015',N'Nguyễn Ái Trinh','0111112345',23),
	  ('KH016',N'Trần Xuân Nam','0111111234',76),
	  ('KH017',N'Huỳnh Nguyên Vũ','0111111123',85),
	  ('KH018',N'Hạo Thiên','0111111112',96),
	  ('KH019',N'Kim Yến','0122345678',25),
	  ('KH020',N'Trần Duy Khanh','0122234567',91),
	  ('KH021',N'Lâm Gia Huy','0122223456',82)
GO


CREATE PROCEDURE GetNhanVienByTaiKhoan @tenTK NVARCHAR(20)
AS
BEGIN
	SELECT nv.maNV, nv.hoTen, nv.email, nv.sdt, nv.gioiTinh, nv.ngaySinh, nv.chucVu, nv.diaChi, nv.luong
	FROM NhanVien nv 
	JOIN TaiKhoan tk ON nv.maNV = tk.maNV
	WHERE tk.tenTaiKhoan = @tenTK
END
GO

EXEC GetNhanVienByTaiKhoan @tenTK = 'tranvu23405'
GO

SELECT *
FROM NhanVien
WHERE hoTen = N'Trần Long Vũ'
GO

CREATE PROCEDURE GetHoaDonBanHang
AS 
BEGIN
    SELECT 
        hd.maHD, 
        nv.hoTen, 
        CASE 
            WHEN kh.maKH IS NOT NULL THEN kh.tenKH 
            ELSE NULL 
        END AS tenKH,
        hd.ngayLapHD, 
        hd.tongThanhToan, 
        hd.gioVao
    FROM 
        HoaDon hd
        JOIN NhanVien nv ON hd.maNV = nv.maNV
        LEFT JOIN KhachHang kh ON hd.maKH = kh.maKH
END
GO

CREATE PROCEDURE GetKhachHangBYSDT @sdt NVARCHAR(10)
AS
BEGIN
	SELECT *
	FROM KhachHang
	WHERE sdt = @sdt
END
GO

CREATE PROCEDURE GetKhachHangBYSDT1 @sdt NVARCHAR(10)
AS
BEGIN
	IF EXISTS (SELECT * FROM KhachHang WHERE sdt = @sdt)
	BEGIN
		SELECT *
		FROM KhachHang
		WHERE sdt = @sdt
	END
	ELSE 
	BEGIN
		SELECT *
		FROM KhachHang
		WHERE sdt = ''
	END
END
GO

EXEC GetKhachHangBYSDT1 @sdt = ''
GO

USE QuanLyBanHang_Coffee_Nhom20
GO

-- Thống kê doanh thu theo tháng
CREATE PROCEDURE ThongKeDoanhThuTheoThang
    @Thang INT,
    @Nam INT
AS
BEGIN
    SELECT
        MONTH(HD.ngayLapHD) AS Thang,
        YEAR(HD.ngayLapHD) AS Nam,
        SUM(HD.tongThanhToan) AS DoanhThu
    FROM
        HoaDon HD
    WHERE
        MONTH(HD.ngayLapHD) = @Thang
        AND YEAR(HD.ngayLapHD) = @Nam
    GROUP BY
        MONTH(HD.ngayLapHD),
        YEAR(HD.ngayLapHD)
END
GO

-- Thống kê doanh thu theo năm
CREATE PROCEDURE ThongKeDoanhThuTheoNam
    @Nam INT
AS
BEGIN
    SELECT
        YEAR(HD.ngayLapHD) AS Nam,
        SUM(HD.tongThanhToan) AS DoanhThu
    FROM
        HoaDon HD
    WHERE
        YEAR(HD.ngayLapHD) = @Nam
    GROUP BY
        YEAR(HD.ngayLapHD)
END
GO

-- Thống kê doanh thu theo ngày
CREATE PROCEDURE ThongKeDoanhThuTheoNgay
    @Ngay DATE
AS
BEGIN
    SELECT
        HD.ngayLapHD AS Ngay,
        SUM(HD.tongThanhToan) AS DoanhThu
    FROM
        HoaDon HD
    WHERE
        HD.ngayLapHD = @Ngay
    GROUP BY
        HD.ngayLapHD
END
GO

EXEC ThongKeDoanhThuTheoNgay @Ngay = '2024-04-23';
GO

SELECT hd.maHD, nv.hoTen, kh.tenKH, hd.ngayLapHD, SUM(hd.tongThanhToan) AS TongDoanhThu
FROM HoaDon hd
JOIN NhanVien nv ON hd.maNV = nv.maNV
JOIN KhachHang kh ON hd.maKH = kh.maKH
GROUP BY hd.maHD, nv.hoTen, kh.tenKH, hd.ngayLapHD
GO

CREATE PROCEDURE DanhSachDoanhThu
AS
BEGIN
	SELECT hd.maHD, nv.hoTen, kh.tenKH, hd.ngayLapHD, SUM(hd.tongThanhToan) AS TongDoanhThu
	FROM HoaDon hd
	JOIN NhanVien nv ON hd.maNV = nv.maNV
	JOIN KhachHang kh ON hd.maKH = kh.maKH
	GROUP BY hd.maHD, nv.hoTen, kh.tenKH, hd.ngayLapHD
END
GO

EXEC DanhSachDoanhThu
GO

SELECT kh.maKH, kh.tenKH, hd.ngayLapHD, hd.tongThanhToan
FROM HoaDon hd 
JOIN KhachHang kh ON hd.maKH = kh.maKH
ORDER BY hd.tongThanhToan DESC
GO

CREATE PROCEDURE GetKhachHangTop
AS
BEGIN
	SELECT kh.maKH, kh.tenKH, hd.ngayLapHD, hd.tongThanhToan
	FROM HoaDon hd 
	JOIN KhachHang kh ON hd.maKH = kh.maKH
	ORDER BY hd.tongThanhToan DESC
END
GO



CREATE PROCEDURE GetTop5SanPham
AS
BEGIN
	SELECT TOP 5 sp.maSP, sp.tenSP, SUM(cthd.soLuong) AS SoluongBan
	FROM SanPham sp 
	JOIN ChiTietHoaDon cthd ON sp.maSP = cthd.maSP
	JOIN HoaDon hd ON cthd.maHD = hd.maHD
	GROUP BY sp.maSP, sp.tenSP
	ORDER BY SUM(cthd.soLuong) DESC
END
GO