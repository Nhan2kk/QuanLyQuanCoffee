package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import connectDB.ConnectDB;
import dao.DonGiaSanPham_DAO;
import dao.LoaiSanPham_DAO;
import dao.SanPham_DAO;
import entity.DonGiaSanPham;
import entity.LoaiSanPham;
import entity.SanPham;

public class Form_CapNhatSanPham extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	JButton btnThem, btnSua, btnXoaTrang, btnXoa, btnImport, btnThemGia, btnXoaRongGIa, btnSuaGia, btnXoaGia;
	JTextField text_MaSP, text_TenSP, text_PathImage, text_Gia;
	private JPlaceholderTextField text_Search;
	JLabel jLabel_MaSP, jLabel_TenSP, jLabel_LoaiSP, jLabel_TrangThai, jLabel_imageImport, jLabel_Size, jLabel_Gia;
	JComboBox<String> cbo_LoaiSP, cbo_TrangThai, cbo_Size;
	JTable tableSanPham, tableDonGiaSanPham;
	DefaultTableModel modelTableSanPham, modelDonGiaSanPham;
	private String[] trangThai = { "Chọn trạng thái...", "Còn", "Hết" };
	private String[] size = { "Chọn size", "S", "M", "L" };
	SanPham_DAO sp_Dao;
	Form_Order formOrder;
	LoaiSanPham_DAO loaiSP_dao;
	DonGiaSanPham_DAO dg_Dao;

	public Form_CapNhatSanPham() {
		// Khởi tạo kết nối đến CSDL
		ConnectDB.getInstance().connect();
		loaiSP_dao = new LoaiSanPham_DAO();
		sp_Dao = new SanPham_DAO();
		dg_Dao = new DonGiaSanPham_DAO();

		// Panel chứa các thành phần
		JPanel jPanel_SanPham = new JPanel(new BorderLayout());

		// Danh mục - JPanel_SanPhamNorth
		JPanel JPanel_SanPhamNorth = new JPanel();
		JPanel_SanPhamNorth.setLayout(new BorderLayout());

		JPanel jPanel_tilte1 = new JPanel();
		JLabel jlb_title1 = new JLabel("Danh sách sản phẩm");
		jlb_title1.setFont(new Font("Tahoma", Font.BOLD, 20));
		jlb_title1.setForeground(Color.WHITE);
		jPanel_tilte1.add(jlb_title1);
		jPanel_tilte1.setBackground(new Color(51, 153, 255));
		jPanel_tilte1.setPreferredSize(new Dimension(900, 50));
		JPanel_SanPhamNorth.add(jPanel_tilte1, BorderLayout.NORTH);

		/// Table sản phẩm
		String[] columnsSanPham = { "ID sản phẩm", "Tên sản phẩm", "Loại", "Trạng thái", "Ngày cập nhật" };

		modelTableSanPham = new DefaultTableModel(columnsSanPham, 0);
		tableSanPham = new JTable(modelTableSanPham);
		tableSanPham.setFont(new Font("Tahoma", Font.BOLD, 13));
		tableSanPham.setRowHeight(30);
		tableSanPham.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				int row = tableSanPham.getSelectedRow();
				fillForm(row);

				if (row != -1) {
					// Lấy mã sản phẩm của dòng đã chọn
					String maSP = tableSanPham.getValueAt(row, 0).toString();

					clearTable(modelDonGiaSanPham);

					ArrayList<DonGiaSanPham> danhSachDonGia = dg_Dao.getDonGiaByProductID(maSP);
					for (DonGiaSanPham donGiaSanPham : danhSachDonGia) {
						Object[] rowData = { donGiaSanPham.getSanPham().getMaSP(), donGiaSanPham.getKichThuoc(),
								donGiaSanPham.getDonGia() };
						modelDonGiaSanPham.addRow(rowData);
					}
				}

			}

			private void clearTable(DefaultTableModel model) {
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}
			}
		});

		DefaultTableCellRenderer centerRecender1 = new DefaultTableCellRenderer();
		centerRecender1.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < tableSanPham.getColumnCount(); i++) {
			tableSanPham.getColumnModel().getColumn(i).setCellRenderer(centerRecender1);
		}

		JScrollPane pane_SanPham = new JScrollPane(tableSanPham);
		pane_SanPham.setPreferredSize(new Dimension(900, 345));
		JPanel_SanPhamNorth.add(pane_SanPham);

		Font headerFont = new Font("Tahoma", Font.BOLD, 14);
		for (int i = 0; i < columnsSanPham.length; i++) {
			TableColumn column = tableSanPham.getColumnModel().getColumn(i);
			column.setHeaderRenderer(new HeaderRenderer(headerFont));
			column.setPreferredWidth(150);
		}

		// Danh mục - JPanel_SanPhamSouth
		JPanel JPanel_SanPhamSouth = new JPanel();
		JPanel_SanPhamSouth.setLayout(new BorderLayout());

		// Input - jPanel_TacVuNorth
		JPanel jPanel_TacVuNorth = new JPanel();

		// Box 1
		Box box_1 = new Box(BoxLayout.X_AXIS);
		box_1.setPreferredSize(new Dimension(420, 180));

		Box box_1a = new Box(BoxLayout.Y_AXIS);
		box_1a.add(Box.createVerticalStrut(20));
		box_1a.add(jLabel_MaSP = new JLabel("Mã sản phẩm:"));
		jLabel_MaSP.setFont(new Font("Tahoma", Font.BOLD, 15));
		box_1a.add(Box.createVerticalStrut(21));
		box_1a.add(jLabel_TenSP = new JLabel("Tên sản phẩm:"));
		jLabel_TenSP.setFont(new Font("Tahoma", Font.BOLD, 15));
		box_1a.add(Box.createVerticalStrut(22));
		box_1a.add(jLabel_LoaiSP = new JLabel("Loại sản phẩm:"));
		jLabel_LoaiSP.setFont(new Font("Tahoma", Font.BOLD, 15));
		box_1a.add(Box.createVerticalStrut(25));
		box_1a.add(jLabel_TrangThai = new JLabel("Trạng thái:"));
		jLabel_TrangThai.setFont(new Font("Tahoma", Font.BOLD, 15));
		box_1a.add(Box.createVerticalStrut(20));

		Box box_1b = new Box(BoxLayout.Y_AXIS);
		box_1b.add(Box.createVerticalStrut(15));
		box_1b.add(text_MaSP = new JTextField(20));
		text_MaSP.setFont(new Font("Tahoma", Font.PLAIN, 15));
		box_1b.add(Box.createVerticalStrut(15));
		box_1b.add(text_TenSP = new JTextField(20));
		text_TenSP.setFont(new Font("Tahoma", Font.PLAIN, 15));
		box_1b.add(Box.createVerticalStrut(20));
		cbo_LoaiSP = new JComboBox<>();
		cbo_LoaiSP.setFont(new Font("Tahoma", Font.PLAIN, 15));
		box_1b.add(cbo_LoaiSP);
		updateLoaiSP();

		box_1b.add(Box.createVerticalStrut(20));
		cbo_TrangThai = new JComboBox<>(trangThai);
		cbo_TrangThai.setFont(new Font("Tahoma", Font.PLAIN, 15));
		box_1b.add(cbo_TrangThai);
		box_1b.add(Box.createVerticalStrut(20));

		box_1.add(box_1a);
		box_1.add(Box.createHorizontalStrut(10));
		box_1.add(box_1b);

		// Box 2 - Add image
		Box box_2 = new Box(BoxLayout.Y_AXIS);
		box_2.setPreferredSize(new Dimension(132, 180));

		JPanel jPanel_Box2 = new JPanel();
		JPanel jPanel_image = new JPanel();
		jPanel_image.setBorder(new LineBorder(new Color(0, 0, 0)));
		jPanel_image.add(jLabel_imageImport = new JLabel());
		jLabel_imageImport.setPreferredSize(new Dimension(120, 100));

		jPanel_Box2.add(jPanel_image);
		jPanel_Box2.add(text_PathImage = new JTextField(14));
		text_PathImage.setEditable(false);
		jPanel_Box2.add(btnImport = new JButton("Import"));
		btnImport.setBackground(new Color(51, 153, 255));
		btnImport.setForeground(new Color(255, 255, 255));
		btnImport.setContentAreaFilled(false);
		btnImport.setOpaque(true);
		btnImport.setFont(new Font("Tahoma", Font.BOLD, 15));

		box_2.add(jPanel_Box2);

		jPanel_TacVuNorth.add(box_1);
		jPanel_TacVuNorth.add(Box.createHorizontalStrut(120));
		jPanel_TacVuNorth.add(box_2);
		jPanel_TacVuNorth.add(Box.createHorizontalStrut(60));

		// Button - jPanel_TacVuSouth
		JPanel jPanel_TacVuSouth = new JPanel();
		jPanel_TacVuSouth.add(btnThem = new JButton("Thêm"));
		btnThem.setFont(new Font("Tahoma", Font.BOLD, 15));
		jPanel_TacVuSouth.add(btnSua = new JButton("Sửa"));
		btnSua.setFont(new Font("Tahoma", Font.BOLD, 15));
		jPanel_TacVuSouth.add(btnXoaTrang = new JButton("Làm mới"));
		btnXoaTrang.setFont(new Font("Tahoma", Font.BOLD, 15));
		jPanel_TacVuSouth.add(btnXoa = new JButton("Xóa"));
		btnXoa.setFont(new Font("Tahoma", Font.BOLD, 15));

		JPanel_SanPhamSouth.add(jPanel_TacVuNorth, BorderLayout.NORTH);
		JPanel_SanPhamSouth.add(jPanel_TacVuSouth, BorderLayout.CENTER);

		JPanel jPanel_SanPhamCenter = new JPanel();
		jPanel_SanPhamCenter.add(text_Search = new JPlaceholderTextField("Tìm kiếm theo tên sản phẩm/tên sản phẩm..."));
		text_Search.setFont(new Font("Tahoma", Font.PLAIN, 15));
		jPanel_SanPhamCenter.setPreferredSize(new Dimension(900, 40));
		text_Search.setPreferredSize(new Dimension(900, 33));

		text_Search.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				String searchText = text_Search.getText().trim().toLowerCase();
				filterProducts(searchText);
			}

		});

		// Add vào jPanel_SanPham
		jPanel_SanPham.add(JPanel_SanPhamNorth, BorderLayout.NORTH);
		jPanel_SanPham.add(jPanel_SanPhamCenter, BorderLayout.CENTER);
		jPanel_SanPham.add(JPanel_SanPhamSouth, BorderLayout.SOUTH);

		// JPanel đơn giá sản phẩm
		JPanel jPanel_DonGiaSanPham = new JPanel();
		jPanel_DonGiaSanPham.setLayout(new BorderLayout());

		JPanel jPanel_DonGiaNorth = new JPanel();
		jPanel_DonGiaNorth.setLayout(new BorderLayout());

		JPanel jPanel_tilte2 = new JPanel();
		JLabel jlb_title2 = new JLabel("Danh sách đơn giá sản phẩm");
		jlb_title2.setFont(new Font("Tahoma", Font.BOLD, 20));
		jlb_title2.setForeground(Color.WHITE);
		jPanel_tilte2.add(jlb_title2);
		jPanel_tilte2.setBackground(new Color(51, 153, 255));
		jPanel_tilte2.setPreferredSize(new Dimension(500, 50));
		jPanel_DonGiaNorth.add(jPanel_tilte2, BorderLayout.NORTH);

		// Table đơn giá sản phẩm
		String[] columnsDonGiaSanPham = { "Mã sản phẩm", "Size", "Giá" };
		modelDonGiaSanPham = new DefaultTableModel(columnsDonGiaSanPham, 0);
		tableDonGiaSanPham = new JTable(modelDonGiaSanPham);
		tableDonGiaSanPham.setFont(new Font("Tahoma", Font.BOLD, 13));
		tableDonGiaSanPham.setRowHeight(30);

		tableDonGiaSanPham.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) { // Đảm bảo sự kiện chỉ được xử lý một lần khi người dùng chọn
					int row = tableDonGiaSanPham.getSelectedRow();
					if (row >= 0) {
						fillFromGia(row);
					}
				}
			}
		});

		DefaultTableCellRenderer centerRecender2 = new DefaultTableCellRenderer();
		centerRecender2.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < tableDonGiaSanPham.getColumnCount(); i++) {
			tableDonGiaSanPham.getColumnModel().getColumn(i).setCellRenderer(centerRecender2);
		}

		JScrollPane pane_DonGiaSanPham = new JScrollPane(tableDonGiaSanPham);
		pane_DonGiaSanPham.setPreferredSize(new Dimension(getWidth(), 383));

		for (int i = 0; i < columnsDonGiaSanPham.length; i++) {
			TableColumn column = tableDonGiaSanPham.getColumnModel().getColumn(i);
			column.setHeaderRenderer(new HeaderRenderer(headerFont));
			column.setPreferredWidth(150);
		}

		jPanel_DonGiaNorth.add(pane_DonGiaSanPham, BorderLayout.CENTER);

		// Input - jPanel_DonGiaCenter
		JPanel jPanel_DonGiaCenter = new JPanel();
		Box box_inputGiaSize = new Box(BoxLayout.Y_AXIS);
		box_inputGiaSize.setPreferredSize(new Dimension(450, 100));

		Box box_inputSize = new Box(BoxLayout.X_AXIS);
		box_inputSize.add(jLabel_Size = new JLabel("Size:"));
		jLabel_Size.setFont(new Font("Tahoma", Font.BOLD, 15));
		box_inputSize.add(Box.createHorizontalStrut(20));
		box_inputSize.add(cbo_Size = new JComboBox<>(size));
		cbo_Size.setFont(new Font("Tahoma", Font.PLAIN, 15));
		box_inputSize.add(Box.createHorizontalStrut(500));

		Box box_inputGia = new Box(BoxLayout.X_AXIS);
		box_inputGia.add(jLabel_Size = new JLabel("Giá:"));
		jLabel_Size.setFont(new Font("Tahoma", Font.BOLD, 15));
		box_inputGia.add(Box.createHorizontalStrut(28));
		box_inputGia.add(text_Gia = new JTextField(20));
		text_Gia.setFont(new Font("Tahoma", Font.PLAIN, 15));

		box_inputGiaSize.add(Box.createVerticalStrut(22));
		box_inputGiaSize.add(box_inputSize);
		box_inputGiaSize.add(Box.createVerticalStrut(22));
		box_inputGiaSize.add(box_inputGia);

		jPanel_DonGiaCenter.add(box_inputGiaSize);

		// Button - jPanel_DonGiaSanPham
		JPanel jPanel_DonGiaSouth = new JPanel();
		jPanel_DonGiaSouth.setPreferredSize(new Dimension(getWidth(), 118));
		jPanel_DonGiaSouth.add(btnThemGia = new JButton("Thêm"));
		btnThemGia.setFont(new Font("Tahoma", Font.BOLD, 15));
		jPanel_DonGiaSouth.add(btnSuaGia = new JButton("Sửa"));
		btnSuaGia.setFont(new Font("Tahoma", Font.BOLD, 15));
		jPanel_DonGiaSouth.add(btnXoaRongGIa = new JButton("Làm mới"));
		btnXoaRongGIa.setFont(new Font("Tahoma", Font.BOLD, 15));
		jPanel_DonGiaSouth.add(btnXoaGia = new JButton("Xóa"));
		btnXoaGia.setFont(new Font("Tahoma", Font.BOLD, 15));

		// Add vào jPanel_DonGiaSanPham
		jPanel_DonGiaSanPham.add(jPanel_DonGiaNorth, BorderLayout.NORTH);
		jPanel_DonGiaSanPham.add(jPanel_DonGiaCenter, BorderLayout.CENTER);
		jPanel_DonGiaSanPham.add(jPanel_DonGiaSouth, BorderLayout.SOUTH);

		// Add vào Form_CapNhatSanPham
		add(jPanel_SanPham, BorderLayout.CENTER);
		add(jPanel_DonGiaSanPham, BorderLayout.WEST);

		// Khoi tao
		sp_Dao = new SanPham_DAO();
		formOrder = new Form_Order();

		// Đọc dữ liệu
		updateSanPhamVaoTable();
		// updateDonGiaSanPhamTable();

		// Add event
		btnThem.addActionListener(this);
		btnXoa.addActionListener(this);
		btnXoaTrang.addActionListener(this);
		btnSua.addActionListener(this);
		btnImport.addActionListener(this);
		btnThemGia.addActionListener(this);
		btnXoaGia.addActionListener(this);
		btnXoaRongGIa.addActionListener(this);
	}

	// Class HeaderRenderer để thiết lập font cho tiêu đề cột
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

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnImport)) {
			JFileChooser fileChooser = new JFileChooser();
			int returnValue = fileChooser.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				// Lấy kích thước của khung JLabel
				int labelWidth = jLabel_imageImport.getWidth();
				int labelHeight = jLabel_imageImport.getHeight();

				// Tạo một ImageIcon từ tập tin ảnh đã chọn
				ImageIcon originalIcon = new ImageIcon(selectedFile.getPath());

				// Lấy kích thước thực của ảnh
				int imageWidth = originalIcon.getIconWidth();
				int imageHeight = originalIcon.getIconHeight();

				// Tính tỉ lệ thu nhỏ cho ảnh sao cho vừa với khung JLabel
				double scaleFactor = Math.min((double) labelWidth / imageWidth, (double) labelHeight / imageHeight);

				// Tạo một ảnh mới với kích thước đã điều chỉnh
				int scaledWidth = (int) (imageWidth * scaleFactor);
				int scaledHeight = (int) (imageHeight * scaleFactor);
				Image scaledImage = originalIcon.getImage().getScaledInstance(scaledWidth, scaledHeight,
						Image.SCALE_SMOOTH);

				// Tạo một ImageIcon mới từ ảnh đã điều chỉnh kích thước
				ImageIcon scaledIcon = new ImageIcon(scaledImage);

				// Đặt ImageIcon mới vào JLabel để hiển thị ảnh đã điều chỉnh kích thước
				jLabel_imageImport.setIcon(scaledIcon);
				text_PathImage.setText(selectedFile.getAbsolutePath());

			}
		} else if (o.equals(btnXoaTrang)) {
			clearField();
		} else if (o.equals(btnThem)) {
			if (valiData()) {
				String maSP = text_MaSP.getText();
				String tenSP = text_TenSP.getText();
				String loaiSP = cbo_LoaiSP.getSelectedItem().toString();
				boolean trangThai = cbo_TrangThai.getSelectedItem().toString().equalsIgnoreCase("Còn");
				String image = text_PathImage.getText();

				java.util.Date utilDate = new java.util.Date();
				java.sql.Date ngay = new java.sql.Date(utilDate.getTime());

				boolean existed = false;
				for (SanPham sp : sp_Dao.getAllSanPham()) {
					if (sp.getMaSP().equalsIgnoreCase(maSP)) {
						existed = true;
						break;
					}
				}

				if (existed) {
					JOptionPane.showMessageDialog(this, "Mã sản phẩm đã tồn tại!");
				} else {
					LoaiSanPham loaiSPs = null;
					for (LoaiSanPham loai : loaiSP_dao.getAllLoaiSP()) {
						if (loai.getTenLoai().equals(loaiSP)) {
							loaiSPs = loai;
							break;
						}
					}
					if (loaiSPs == null) {
						JOptionPane.showMessageDialog(this, "Loại sản phẩm không tồn tại!");
						return;
					}

					SanPham sp = new SanPham(maSP, tenSP, image, trangThai, ngay, loaiSPs);

					// Thêm sản phẩm vào cơ sở dữ liệu
					boolean success = sp_Dao.create(sp);
					if (success) {
						modelTableSanPham
								.addRow(new Object[] { sp.getMaSP(), sp.getTenSP(), sp.getLoaiSP().getTenLoai(),
										sp.isTrangThai() ? "Còn" : "Hết", sp.getNgayCN() });
						clearField();
						JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!");
					} else {
						JOptionPane.showMessageDialog(this, "Thêm sản phẩm thất bại!");
					}
				}
			}
		} else if (o.equals(btnXoa)) {
			int row = tableSanPham.getSelectedRow();
			if (row != -1) {
				int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa sản phẩm này?",
						"Xác nhận xóa", JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					String maSP = tableSanPham.getValueAt(row, 0).toString();
					SanPham sp = new SanPham();
					sp.setMaSP(maSP);

					SanPham_DAO dao = new SanPham_DAO();
					boolean deleted = dao.delete(sp);

					if (deleted) {
						JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công!");
						modelTableSanPham.removeRow(row);
						modelDonGiaSanPham.setRowCount(0);
						clearField();
					} else {
						JOptionPane.showMessageDialog(this, "Xóa sản phẩm không thành công!");
					}
				}
			} else {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa!");
			}
		} else if (o.equals(btnSua)) {
			int row = tableSanPham.getSelectedRow();
			if (row != -1) {
				if (valiData()) {
					String maSP = tableSanPham.getValueAt(row, 0).toString();
					String tenSP = text_TenSP.getText();
					String loaiSP = cbo_LoaiSP.getSelectedItem().toString();
					boolean trangThai = cbo_TrangThai.getSelectedItem().toString().equalsIgnoreCase("Còn");
					String image = text_PathImage.getText();

					// Tạo một đối tượng java.sql.Date từ ngày hiện tại
					java.util.Date utilDate = new java.util.Date();
					java.sql.Date ngay = new java.sql.Date(utilDate.getTime());

					// Chuyển đổi loại sản phẩm từ chuỗi sang đối tượng LoaiSanPham
					LoaiSanPham loaiSPs = null;
					for (LoaiSanPham loai : loaiSP_dao.getAllLoaiSP()) {
						if (loai.getTenLoai().equals(loaiSP)) {
							loaiSPs = loai;
							break;
						}
					}
					if (loaiSPs == null) {
						// Xử lý trường hợp loại sản phẩm không tồn tại
						JOptionPane.showMessageDialog(this, "Loại sản phẩm không tồn tại!");
						return; // Không thực hiện thêm sản phẩm nữa
					}

					// Tạo đối tượng SanPham mới với thông tin đã được sửa đổi
					SanPham sp = new SanPham(maSP, tenSP, image, trangThai, ngay, loaiSPs);

					// Cập nhật thông tin sản phẩm trong cơ sở dữ liệu
					boolean success = sp_Dao.update(sp);
					if (success) {
						// Cập nhật thông tin sản phẩm trong bảng
						tableSanPham.setValueAt(tenSP, row, 1);
						tableSanPham.setValueAt(loaiSP, row, 2);
						tableSanPham.setValueAt(trangThai ? "Còn" : "Hết", row, 3);
						tableSanPham.setValueAt(ngay, row, 4);
						JOptionPane.showMessageDialog(this, "Cập nhật thông tin sản phẩm thành công!");
						clearField(); // Xóa trường nhập liệu sau khi cập nhật thành công
					} else {
						JOptionPane.showMessageDialog(this, "Cập nhật thông tin sản phẩm thất bại!");
					}
				}
			} else {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần sửa!");
			}
		} else if (o.equals(btnThemGia)) {
			int selectedRow = tableSanPham.getSelectedRow();
			if (selectedRow == -1) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn một sản phẩm từ bảng sản phẩm!");
				return;
			}

			String maSP = tableSanPham.getValueAt(selectedRow, 0).toString();
			String size = cbo_Size.getSelectedItem().toString();
			String giaText = text_Gia.getText();

			// Kiểm tra xem đã chọn size chưa và nếu không phải S, M, hoặc L thì thông báo
			// lỗi
			if (!size.equals("S") && !size.equals("M") && !size.equals("L")) {
				JOptionPane.showMessageDialog(this, "Size phải là S, M hoặc L!");
				return;
			}

			// Kiểm tra xem đã nhập đơn giá chưa, và nếu nhập đơn giá không hợp lệ thì thông
			// báo lỗi
			if (giaText.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Vui lòng nhập giá đơn giá sản phẩm!");
				return;
			}

			// Kiểm tra giá nhập vào phải là số và lớn hơn 0
			double gia = 0.0;
			try {
				gia = Double.parseDouble(giaText);
				if (gia <= 0) {
					JOptionPane.showMessageDialog(this, "Giá phải lớn hơn 0!");
					return;
				}
			} catch (NumberFormatException e2) {
				JOptionPane.showMessageDialog(this, "Giá phải là số và lớn hơn 0!");
				return;
			}

			// Kiểm tra xem size đã tồn tại cho sản phẩm này chưa
			if (dg_Dao.checkSizeExist(size, maSP)) {
				JOptionPane.showMessageDialog(this, "Size đã tồn tại cho sản phẩm này!");
			} else {
				DonGiaSanPham donGiaSanPham = new DonGiaSanPham(gia, size, new SanPham(maSP));
				boolean success = dg_Dao.create(donGiaSanPham);
				if (success) {
					Object[] rowData = { donGiaSanPham.getSanPham().getMaSP(), donGiaSanPham.getKichThuoc(),
							donGiaSanPham.getDonGia() };
					modelDonGiaSanPham.addRow(rowData);

					JOptionPane.showMessageDialog(this, "Thêm đơn giá sản phẩm thành công!");
					clearFieldGia();
				} else {
					JOptionPane.showMessageDialog(this, "Thêm đơn giá sản phẩm thất bại!");
				}
			}
		} else if (o.equals(btnXoaRongGIa)) {
			clearFieldGia();
		} else if (o.equals(btnXoaGia)) {
			int selectedRow = tableDonGiaSanPham.getSelectedRow();
			if (selectedRow == -1) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn một đơn giá sản phẩm từ bảng đơn giá sản phẩm!");
				return;
			}

			// Lấy mã sản phẩm và size từ hàng đã chọn trong bảng
			String maSP = tableDonGiaSanPham.getValueAt(selectedRow, 0).toString();
			String size = tableDonGiaSanPham.getValueAt(selectedRow, 1).toString();

			// Hiển thị hộp thoại xác nhận
			int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa đơn giá sản phẩm này?",
					"Xác nhận xóa", JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {
				// Xóa đơn giá sản phẩm từ CSDL
				boolean success = dg_Dao.delete(maSP, size);
				if (success) {
					// Xóa hàng khỏi bảng hiển thị
					modelDonGiaSanPham.removeRow(selectedRow);
					JOptionPane.showMessageDialog(this, "Xóa đơn giá sản phẩm thành công!");
				} else {
					JOptionPane.showMessageDialog(this, "Xóa đơn giá sản phẩm thất bại!");
				}
			}
		}

	}

	private boolean valiData() {
		String maSP = text_MaSP.getText().trim();
		String tenSP = text_TenSP.getText().trim();
		String loaiSP = (String) cbo_LoaiSP.getSelectedItem();
		String trangThai = (String) cbo_TrangThai.getSelectedItem();
		String image = text_PathImage.getText().trim();

		if (!(maSP.length() > 0 && maSP.matches("[A-Z]\\d{3}"))) {
			JOptionPane.showMessageDialog(this,
					"Mã sản phẩm không được để trống và phải bắt đầu bằng 1 ký tự in hoa, theo sau là 3 ký tự số!");
			text_MaSP.requestFocus();
			return false;
		}

		if (!(tenSP.length() > 0 && tenSP.matches("\\p{L}+[\\p{L}\\s]*"))) {
			JOptionPane.showMessageDialog(this,
					"Tên sản phẩm không được để trống và phải là các từ cách nhau bởi khoảng trắng!");
			text_TenSP.requestFocus();
			return false;
		}

		if (loaiSP.equalsIgnoreCase("Chọn loại sản phẩm...")) {
			JOptionPane.showMessageDialog(this, "Loại sản phẩm không được để trống!");
			return false;
		}

		if (trangThai.equalsIgnoreCase("Chọn trạng thái...")) {
			JOptionPane.showMessageDialog(this, "Trạng thái không được để trống!");
			return false;
		}

		if (image.length() <= 0) {
			JOptionPane.showMessageDialog(this, "Image phải được import!");
			return false;
		}

		return true;
	}

	private void clearField() {
		text_MaSP.setText("");
		text_TenSP.setText("");
		cbo_LoaiSP.setSelectedIndex(0);
		cbo_TrangThai.setSelectedIndex(0);
		jLabel_imageImport.setIcon(null);
		text_PathImage.setText("");
		text_MaSP.requestFocus();
		text_MaSP.setEditable(true);
	}

	private void clearFieldGia() {
		cbo_Size.setSelectedIndex(0);
		text_Gia.setText("");
	}

	// Placeholder - chữ nằm trong textfield
	public class PlaceholderTextField extends JTextField {
		private static final long serialVersionUID = 1L;
		private String placeholder;

		public PlaceholderTextField(String placeholder) {
			this.placeholder = placeholder;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			if (getText().isEmpty()) {
				g.setColor(Color.GRAY);
				g.setFont(new Font("Arial", Font.PLAIN, 14));
				g.drawString(placeholder, getInsets().left, g.getFontMetrics().getHeight() + getInsets().top);
			}
		}
	}

	private void fillFromGia(int row) {
		if (row != -1) {
			String size = (String) tableDonGiaSanPham.getValueAt(row, 1);
			String gia = tableDonGiaSanPham.getValueAt(row, 2).toString();

			// Hiển thị thông tin đơn giá sản phẩm lên các trường nhập liệu
			cbo_Size.setSelectedItem(size);
			text_Gia.setText(gia);
		}
	}

	private void fillForm(int row) {
		if (row != -1) {
			String maSP = (String) tableSanPham.getValueAt(row, 0);

			// Thực hiện truy vấn cơ sở dữ liệu để lấy thông tin chi tiết về sản phẩm
			ArrayList<SanPham> dsSP = sp_Dao.getAllProduct();
			for (SanPham sanPham : dsSP) {
				if (sanPham.getMaSP().equals(maSP)) {
					// Lấy thông tin chi tiết về sản phẩm từ cơ sở dữ liệu
					text_MaSP.setText(sanPham.getMaSP());
					text_TenSP.setText(sanPham.getTenSP());
					cbo_LoaiSP.setSelectedItem(sanPham.getLoaiSP().getTenLoai());
					cbo_TrangThai.setSelectedItem(sanPham.isTrangThai() ? "Còn" : "Hết");
					text_PathImage.setText(sanPham.getImage());
					text_MaSP.setEditable(false);

					// Hiển thị hình ảnh sản phẩm
					if (!sanPham.getImage().isEmpty()) {
						File imageFile = new File(sanPham.getImage());
						if (imageFile.exists()) {
							// Hiển thị hình ảnh trên JLabel
							ImageIcon imageIcon = new ImageIcon(sanPham.getImage());
							Image scaledImage = imageIcon.getImage().getScaledInstance(jLabel_imageImport.getWidth(),
									jLabel_imageImport.getHeight(), Image.SCALE_SMOOTH);
							jLabel_imageImport.setIcon(new ImageIcon(scaledImage));
						} else {
							// Nếu đường dẫn ảnh không tồn tại, đặt giá trị rỗng cho JLabel
							jLabel_imageImport.setIcon(null);
						}
					} else {
						// Nếu đường dẫn ảnh rỗng, đặt giá trị rỗng cho JLabel
						jLabel_imageImport.setIcon(null);
					}

					// Đã tìm thấy sản phẩm, thoát vòng lặp
					break;
				}
			}
		}
	}

	private void updateLoaiSP() {
		if (cbo_LoaiSP != null) {
			ArrayList<LoaiSanPham> dsLoaiSP = loaiSP_dao.getAllLoaiSP();
			cbo_LoaiSP.removeAllItems();
			for (LoaiSanPham loaiSP : dsLoaiSP) {
				cbo_LoaiSP.addItem(loaiSP.getTenLoai());
			}
		} else {
			System.err.println("cbo_LoaiSP is null");
		}
	}

	private void updateSanPhamVaoTable() {
		List<SanPham> dsSP = sp_Dao.getProductInfo();
		modelTableSanPham.setRowCount(0);

		// Hiển thị thông tin sản phẩm trên bảng với loại sản phẩm tương ứng
		for (SanPham sp : dsSP) {
			modelTableSanPham.addRow(new Object[] { sp.getMaSP(), sp.getTenSP(), sp.getLoaiSP().getTenLoai(),
					sp.isTrangThai() ? "Còn" : "Hết", sp.getNgayCN() });
		}
	}

	private void updateDonGiaSanPhamTable() {
		List<DonGiaSanPham> dsDonGia = dg_Dao.getAllDonGiaSanPham();
		modelDonGiaSanPham.setRowCount(0);

		for (DonGiaSanPham donGia : dsDonGia) {
			modelDonGiaSanPham.addRow(new Object[] { donGia.getSanPham().getMaSP(), donGia.getKichThuoc(),
					donGia.getDonGia() });
		}
	}

	// placeholder cho thanh tìm kiếm

	public class JPlaceholderTextField extends JTextField {
		private static final long serialVersionUID = 1L;
		private String placeholder;

		public JPlaceholderTextField(String placeholder) {
			this.placeholder = placeholder;
			setForeground(Color.GRAY);
			setFont(new Font(getFont().getName(), Font.ITALIC, getFont().getSize()));
			addFocusListener((FocusListener) new FocusListener() {
				@Override
				public void focusGained(FocusEvent e) {
					if (getText().isEmpty()) {
						setForeground(Color.BLACK);
						setFont(new Font(getFont().getName(), Font.PLAIN, getFont().getSize()));
					}
				}

				@Override
				public void focusLost(FocusEvent e) {
					if (getText().isEmpty()) {
						setForeground(Color.GRAY);
						setFont(new Font(getFont().getName(), Font.ITALIC, getFont().getSize()));
					}
				}
			});
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (getText().isEmpty() && !isFocusOwner()) {
				g.setColor(getForeground());
				g.setFont(getFont());
				g.drawString(placeholder, getInsets().left,
						(getHeight() + g.getFontMetrics().getAscent() - g.getFontMetrics().getDescent()) / 2);
			}
		}

		public String getPlaceholder() {
			return placeholder;
		}

		public void setPlaceholder(String placeholder) {
			this.placeholder = placeholder;
		}
	}

	// Hiển thị sản phẩm tìm kiếm
	private void filterProducts(String searchText) {
		// Xóa tất cả hàng hiện có trong bảng sản phẩm
		modelTableSanPham.setRowCount(0);

		// Lọc danh sách sản phẩm dựa trên chuỗi tìm kiếm
		ArrayList<SanPham> filteredProducts = new ArrayList<>();
		for (SanPham sp : sp_Dao.getProductInfo()) {
			String maSP = sp.getMaSP().toLowerCase();
			String tenSP = sp.getTenSP().toLowerCase();
			if (maSP.contains(searchText) || tenSP.contains(searchText)) {
				filteredProducts.add(sp);
			}
		}

		// Thêm các sản phẩm thỏa mãn điều kiện tìm kiếm vào bảng
		for (SanPham sp : filteredProducts) {
			modelTableSanPham.addRow(new Object[] {
					sp.getMaSP(),
					sp.getTenSP(),
					sp.getLoaiSP().getTenLoai(),
					sp.isTrangThai() ? "Còn" : "Hết",
					sp.getNgayCN()
			});
		}

		// Cuộn tới vị trí sản phẩm đầu tiên thỏa mãn điều kiện tìm kiếm
		if (!filteredProducts.isEmpty()) {
			tableSanPham.scrollRectToVisible(tableSanPham.getCellRect(0, 0, true));
		}
	}

	public void checkAdmin(boolean isAdmin) {
		btnThem.setEnabled(isAdmin);
		btnXoa.setEnabled(isAdmin);
		btnSua.setEnabled(isAdmin);
		btnXoaTrang.setEnabled(isAdmin);
		btnThemGia.setEnabled(isAdmin);
		btnSuaGia.setEnabled(isAdmin);
		btnXoaRongGIa.setEnabled(isAdmin);
		btnXoaGia.setEnabled(isAdmin);
	}

}
