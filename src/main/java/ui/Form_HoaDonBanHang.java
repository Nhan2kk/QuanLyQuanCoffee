package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import connectDB.ConnectDB;
import dao.HoaDonBanHang_DAO;
import dao.KhachHang_DAO;
import entity.HoaDon;

public class Form_HoaDonBanHang extends JPanel {
	private static final long serialVersionUID = 1L;
	JPanel jPanel_North, jPanel_Center;
	JLabel jLabel_Tim;
	JTextField textNhap;
	JButton btnTim, btnXemChiTiet, btnXoa;
	JTable tableHoaDon;
	DefaultTableModel modelTableHoaDon;
	HoaDonBanHang_DAO HD_dao;
	KhachHang_DAO KH_dao;

	public Form_HoaDonBanHang() {
		HD_dao = new HoaDonBanHang_DAO();
		ConnectDB.getInstance().connect();
		jPanel_North = new JPanel();
		jPanel_Center = new JPanel();

		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(30, 70, 0, 70));

		// Dong yeu cau dau tien:
		Box box1 = new Box(BoxLayout.X_AXIS);
		box1.add(Box.createVerticalStrut(30));
		box1.add(jLabel_Tim = new JLabel("• Tìm Hóa Đơn Theo Mã Hóa Đơn:"));
		jLabel_Tim.setFont(new Font("Tahoma", Font.PLAIN, 16));
		box1.add(Box.createHorizontalStrut(1150));

		// Dong textField

		Box box2 = new Box(BoxLayout.X_AXIS);
		box2.add(Box.createHorizontalStrut(100));
		box2.add(textNhap = new JTextField());
		textNhap.setPreferredSize(new Dimension(10, 30));
		textNhap.setFont(new Font("Tahoma", Font.PLAIN, 16));
		box2.add(Box.createHorizontalStrut(1000));
		textNhap.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				String searchText = textNhap.getText().trim().toLowerCase();
				filterHoaDon(searchText);
			}

		});
		// Dong cac nut chuc nang
		Box box3 = new Box(BoxLayout.X_AXIS);
		box3.add(Box.createHorizontalStrut(100));
		box3.add(btnXemChiTiet = new JButton("Xem chi tiết hóa đơn"));
		btnXemChiTiet.setFont(new Font("Tahoma", Font.PLAIN, 16));
		box3.add(Box.createHorizontalStrut(15));
		box3.add(btnXoa = new JButton("Xóa"));
		btnXoa.setFont(new Font("Tahoma", Font.PLAIN, 16));
		box3.add(Box.createHorizontalStrut(1130));
		// Dong danh sach
		Box box4 = new Box(BoxLayout.X_AXIS);
		box4.add(jLabel_Tim = new JLabel("Danh Sách Đơn Hàng:"));
		jLabel_Tim.setFont(new Font("Tahoma", Font.BOLD, 22));
		//
		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(box1);
		box.add(Box.createVerticalStrut(10));
		box.add(box2);
		box.add(Box.createVerticalStrut(10));
		box.add(box3);
		box.add(Box.createVerticalStrut(50));
		box.add(box4);

		jPanel_North.add(box);
		// set mau nen
		setBackground(new Color(51, 204, 255));
		jPanel_North.setBackground(Color.WHITE);
		jPanel_Center.setBackground(Color.WHITE);
		// Table sản phẩm
		String[] columnsHoaDon = { "Mã Hóa Đơn", "Nhân Viên", "Khách Hàng", "Ngày Lập", "Tổng Thanh Toán", "Giờ Vào" };

		modelTableHoaDon = new DefaultTableModel(columnsHoaDon, 0);
		tableHoaDon = new JTable(modelTableHoaDon);
		tableHoaDon.setFont(new Font("Tahoma", Font.BOLD, 13));
		tableHoaDon.setRowHeight(30);
		DefaultTableCellRenderer centerRecender1 = new DefaultTableCellRenderer();
		centerRecender1.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < tableHoaDon.getColumnCount(); i++) {
			tableHoaDon.getColumnModel().getColumn(i).setCellRenderer(centerRecender1);
		}

		JScrollPane pane_HoaDon = new JScrollPane(tableHoaDon);
		pane_HoaDon.setPreferredSize(new Dimension(1300, 450));
		pane_HoaDon.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(51, 204, 255))));
		jPanel_Center.add(pane_HoaDon);

		Font headerFont1 = new Font("Tahoma", Font.BOLD, 14);
		for (int i = 0; i < columnsHoaDon.length; i++) {
			TableColumn column1 = tableHoaDon.getColumnModel().getColumn(i);
			column1.setHeaderRenderer(new HeaderRenderer(headerFont1));
			column1.setPreferredWidth(150);
		}

		add(jPanel_North, BorderLayout.NORTH);
		add(jPanel_Center, BorderLayout.CENTER);

		ReadDatabaseToTable2();
		btnXoa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(Form_HoaDonBanHang.this, "Quản Lý mới có quyền xóa hóa đơn!");
			}
		});
	}

	public void ReadDatabaseToTable2() {
		List<HoaDon> list = HD_dao.getAllHoaDon();

		for (HoaDon hd : list) {
			modelTableHoaDon.addRow(new Object[] { hd.getMaHoaDon(), hd.getNhanVien().getHoTen(),
					hd.getKhachHang().getTenKH(), hd.getNgayLapHD(), hd.getTongThanhToan(), hd.getGioVao() });
		}
	}

	private void filterHoaDon(String searchText) {
		// Xóa tất cả hàng hiện có trong bảng
		modelTableHoaDon.setRowCount(0);

		// Lọc danh sách hóa đơn dựa trên số điện thoại của khách hàng
		ArrayList<HoaDon> filteredHoaDonList = new ArrayList<>();
		for (HoaDon hd : HD_dao.getAllHoaDon()) {
			String maHD = hd.getMaHoaDon().toLowerCase();

			if (maHD.contains(searchText)) {
				filteredHoaDonList.add(hd);
			}
		}

		// Thêm các hóa đơn thỏa mãn điều kiện tìm kiếm vào bảng
		for (HoaDon hd : filteredHoaDonList) {
			modelTableHoaDon.addRow(new Object[] { hd.getMaHoaDon(), hd.getNhanVien().getHoTen(),
					hd.getKhachHang().getTenKH(), hd.getNgayLapHD(), hd.getTongThanhToan(), hd.getGioVao() });
		}

		// Cuộn tới vị trí hóa đơn đầu tiên thỏa mãn điều kiện tìm kiếm
		if (!filteredHoaDonList.isEmpty()) {
			tableHoaDon.scrollRectToVisible(tableHoaDon.getCellRect(0, 0, true));
		}
	}

	class HeaderRenderer implements TableCellRenderer {
		Font font;

		public HeaderRenderer(Font font) {
			this.font = font;
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			JLabel label = new JLabel();
			label.setText((String) value);
			label.setFont(font);
			label.setHorizontalAlignment(JLabel.CENTER); // Canh giữa tiêu đề cột
			return label;
		}

	}
}
