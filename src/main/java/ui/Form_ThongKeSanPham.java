package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import connectDB.ConnectDB;
import dao.SanPham_DAO;

public class Form_ThongKeSanPham extends JPanel {
	private static final long serialVersionUID = 1L;
	JPanel jPanel_North, jPanel_Center, jPanel_South;
	JLabel jLabel_Title, jLabel_Title2, jLabel_Ngay, jLabel_Thang, jLabel_Nam;
	JTextField text_Ngay, text_Thang, text_Nam;
	JButton btnThongKe, btnXemCt;
	JCheckBox cbNgay, cbThang, cbNam;
	JTable tableTKDoanhThu;
	DefaultTableModel modelTableTKDoanhThu;
	SanPham_DAO sp_Dao;

	public Form_ThongKeSanPham() {
		ConnectDB.getInstance().connect();
		sp_Dao = new SanPham_DAO();

		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(30, 70, 70, 70));

		jPanel_North = new JPanel();
		jPanel_Center = new JPanel();
		jPanel_South = new JPanel();

		// Title 1

		jPanel_North.add(jLabel_Title = new JLabel("THỐNG KÊ TOP 5 ĐỒ UỐNG BÁN CHẠY"));
		jPanel_North.setBackground(new Color(51, 153, 255));
		jPanel_North.setOpaque(true);
		jLabel_Title.setFont(new Font("Arial", Font.BOLD, 20));
		jLabel_Title.setForeground(Color.WHITE);
		add(jPanel_North, BorderLayout.NORTH);

		// Vung center

		jPanel_Center.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(new Color(51, 153, 255)), "Thống kê",
				TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.BOLD, 18)));

		Box box1 = new Box(BoxLayout.X_AXIS);
		box1.add(jLabel_Ngay = new JLabel("Ngày:   "));
		jLabel_Ngay.setFont(new Font("Arial", Font.PLAIN, 16));
		box1.add(text_Ngay = new JTextField(10));
		text_Ngay.setFont(new Font("Arial", Font.PLAIN, 16));
		box1.add(Box.createHorizontalStrut(50));
		box1.add(jLabel_Thang = new JLabel("Tháng:   "));
		jLabel_Thang.setFont(new Font("Arial", Font.PLAIN, 16));
		box1.add(text_Thang = new JTextField(10));
		text_Thang.setFont(new Font("Arial", Font.PLAIN, 16));
		box1.add(Box.createHorizontalStrut(50));
		box1.add(jLabel_Nam = new JLabel("Năm:   "));
		jLabel_Nam.setFont(new Font("Arial", Font.PLAIN, 16));
		box1.add(text_Nam = new JTextField(10));
		text_Nam.setFont(new Font("Arial", Font.PLAIN, 16));
		box1.add(Box.createHorizontalStrut(50));
		box1.add(btnThongKe = new JButton("Thống kê"));
		btnThongKe.setIcon(new ImageIcon("images\\timkiem.png"));

		Box box2 = new Box(BoxLayout.X_AXIS);
		box2.add(cbNgay = new JCheckBox("Thống kê theo ngày"));
		cbNgay.setFont(new Font("Arial", Font.PLAIN, 13));
		box2.add(Box.createHorizontalStrut(140));
		box2.add(cbThang = new JCheckBox("Thống kê theo tháng"));
		cbThang.setFont(new Font("Arial", Font.PLAIN, 13));
		box2.add(Box.createHorizontalStrut(160));
		box2.add(cbNam = new JCheckBox("Thống kê theo năm"));
		cbNam.setFont(new Font("Arial", Font.PLAIN, 13));
		box2.add(Box.createHorizontalStrut(90));
		box2.add(btnThongKe = new JButton("Xem chi tiết hóa đơn"));
		btnThongKe.setIcon(new ImageIcon("images\\findpage.png"));

		Box box3 = new Box(BoxLayout.Y_AXIS);
		box3.add(Box.createVerticalStrut(20));
		box3.add(box1);
		box3.add(Box.createVerticalStrut(20));
		box3.add(box2);

		jPanel_Center.add(box3);

		add(jPanel_Center, BorderLayout.CENTER);

		// vung south
		// Table sản phẩm
		String[] columnsHoaDon = { "Mã Sản Phẩm", "Tên Sản Phẩm", "Số Lượng Bán" };

		modelTableTKDoanhThu = new DefaultTableModel(columnsHoaDon, 0);
		tableTKDoanhThu = new JTable(modelTableTKDoanhThu);
		tableTKDoanhThu.setFont(new Font("Tahoma", Font.BOLD, 13));
		tableTKDoanhThu.setRowHeight(30);
		DefaultTableCellRenderer centerRecender1 = new DefaultTableCellRenderer();
		centerRecender1.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < tableTKDoanhThu.getColumnCount(); i++) {
			tableTKDoanhThu.getColumnModel().getColumn(i).setCellRenderer(centerRecender1);
		}
		Font headerFont1 = new Font("Tahoma", Font.BOLD, 14);
		for (int i = 0; i < columnsHoaDon.length; i++) {
			TableColumn column1 = tableTKDoanhThu.getColumnModel().getColumn(i);
			column1.setHeaderRenderer(new HeaderRenderer(headerFont1));
			column1.setPreferredWidth(150);
		}

		JScrollPane pane_HoaDon = new JScrollPane(tableTKDoanhThu);
		pane_HoaDon.setPreferredSize(new Dimension(1390, 350));
		pane_HoaDon.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(51, 153, 255))));

		Box box4 = new Box(BoxLayout.Y_AXIS);
		box4.add(Box.createVerticalStrut(30));
		box4.add(jLabel_Title2 = new JLabel("Top 5 Đồ Uống Bán Chạy"));
		jLabel_Title2.setFont(new Font("Arial", Font.BOLD, 20));
		jLabel_Title2.setAlignmentX(Component.CENTER_ALIGNMENT);
		jLabel_Title2.setForeground(Color.WHITE);
		jLabel_Title2.setBackground(new Color(51, 153, 255));
		jLabel_Title2.setOpaque(true);
		box4.add(Box.createVerticalStrut(30));
		box4.add(pane_HoaDon, BorderLayout.SOUTH);

		jPanel_South.add(box4);
		add(jPanel_South, BorderLayout.SOUTH);

		docDanhSachTop5();
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

	public void docDanhSachTop5() {
		ArrayList<Object[]> top5SanPham = sp_Dao.getTop5SanPham();

		modelTableTKDoanhThu.setRowCount(0);

		for (Object[] row : top5SanPham) {
			modelTableTKDoanhThu.addRow(row);
		}
	}
}
