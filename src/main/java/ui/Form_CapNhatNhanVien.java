package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import connectDB.ConnectDB;
import dao.NhanVien_DAO;
import dao.TaiKhoan_DAO;
import entity.NhanVien;
import entity.TaiKhoan;

public class Form_CapNhatNhanVien extends JPanel implements ActionListener {
        private JLabel lbTimKiemNV, lbHoTen, lbGioiTinh, lbNgaySinh, lbChucVu, lbSDT, lbEmail, lbDiaChi, lbLuong,
                        lbTaiKhoan, lbMatKhau;
        private JTextField txtHoTen, txtNgaySinh, txtSDT, txtEmail, txtDiaChi, txtLuong, txtTaiKhoan,
                        txtMatKhau;
        private JPlaceholderTextField txtTimKiemNV;
        private JComboBox<String> cbo_GioiTinh, cboChucVu;
        private String[] chucVu = { "Chọn chức vụ", "Phục vụ", "Thu ngân", "Pha chế" };
        private String[] gioiTinh = { "Chọn giới tính", "Nam", "Nữ" };
        private JButton btnThem, btnXoa, btnSua, btLamMoi;
        private JTable tableNhanVien;
        private DefaultTableModel modelNhanVien;

        private NhanVien_DAO nv_DAO;
        private TaiKhoan_DAO tk_DAO;

        public Form_CapNhatNhanVien() {
                // Khởi tạo kết nối đến CSDL
                ConnectDB.getInstance().connect();
                nv_DAO = new NhanVien_DAO();
                tk_DAO = new TaiKhoan_DAO();

                setLayout(new BorderLayout());

                // JPanel north
                JPanel pnlNorth = new JPanel();
                JLabel lbTitle = new JLabel("THÔNG TIN NHÂN VIÊN");
                lbTitle.setFont(new Font("Tohama", Font.BOLD, 20));
                lbTitle.setFont(lbTitle.getFont().deriveFont(24.0f));
                lbTitle.setForeground(Color.WHITE);
                pnlNorth.setBackground(new Color(51, 153, 255));

                pnlNorth.add(lbTitle);

                // JPanel center
                JPanel pnlCenter = new JPanel();

                JPanel pnlNhanVien = new JPanel();
                TitledBorder boderNhanVien = new TitledBorder(BorderFactory.createLineBorder(new Color(51, 153, 255)),
                                "Nhân viên");
                boderNhanVien.setTitleFont(new Font("Tohama", Font.BOLD, 17));
                pnlNhanVien.setBorder(boderNhanVien);
                pnlNhanVien.setPreferredSize(new Dimension(1080, 300));
                Box box_NhanVien = new Box(BoxLayout.Y_AXIS);
                box_NhanVien.setPreferredSize(new Dimension(1037, 250));

                Box box_Line1 = new Box(BoxLayout.X_AXIS);
                box_Line1.setPreferredSize(new Dimension(20, 25));

                box_Line1.add(lbHoTen = new JLabel("Tên nhân viên: "));
                lbHoTen.setFont(new Font("Tohama", Font.BOLD, 15));
                box_Line1.add(Box.createHorizontalStrut(10));
                box_Line1.add(txtHoTen = new JTextField(20));
                txtHoTen.setFont(new Font("Tohama", Font.BOLD, 13));
                box_Line1.add(Box.createHorizontalStrut(10));
                box_Line1.add(lbGioiTinh = new JLabel("Giới tính: "));
                lbGioiTinh.setFont(new Font("Tohama", Font.BOLD, 15));
                box_Line1.add(Box.createHorizontalStrut(13));
                box_Line1.add(cbo_GioiTinh = new JComboBox<String>(gioiTinh));
                cbo_GioiTinh.setFont(new Font("Tohama", Font.BOLD, 13));
                cbo_GioiTinh.setPreferredSize(new Dimension(150, 10));
                box_Line1.add(Box.createHorizontalStrut(10));
                box_Line1.add(lbNgaySinh = new JLabel("Ngày sinh: "));
                lbNgaySinh.setFont(new Font("Tohama", Font.BOLD, 15));
                box_Line1.add(Box.createHorizontalStrut(10));
                box_Line1.add(txtNgaySinh = new JTextField(20));
                txtNgaySinh.setFont(new Font("Tohama", Font.BOLD, 13));

                Box box_Line2 = new Box(BoxLayout.X_AXIS);
                box_Line2.setPreferredSize(new Dimension(20, 25));

                box_Line2.add(lbEmail = new JLabel("Email: "));
                lbEmail.setFont(new Font("Tohama", Font.BOLD, 15));
                box_Line2.add(Box.createHorizontalStrut(75));
                box_Line2.add(txtEmail = new JTextField(20));
                txtEmail.setFont(new Font("Tohama", Font.BOLD, 13));
                box_Line2.add(Box.createHorizontalStrut(10));
                box_Line2.add(lbSDT = new JLabel("Số điện thoại: "));
                lbSDT.setFont(new Font("Tohama", Font.BOLD, 15));
                box_Line2.add(Box.createHorizontalStrut(10));
                box_Line2.add(txtSDT = new JTextField(20));
                txtSDT.setFont(new Font("Tohama", Font.BOLD, 13));
                box_Line2.add(Box.createHorizontalStrut(10));
                box_Line2.add(lbChucVu = new JLabel("Chức vụ: "));
                box_Line2.add(cboChucVu = new JComboBox<String>(chucVu));
                cboChucVu.setFont(new Font("Tohama", Font.BOLD, 13));
                lbChucVu.setFont(new Font("Tohama", Font.BOLD, 15));

                Box box_Line3 = new Box(BoxLayout.X_AXIS);
                box_Line3.setPreferredSize(new Dimension(20, 25));

                box_Line3.add(lbDiaChi = new JLabel("Địa chỉ: "));
                lbDiaChi.setFont(new Font("Tohama", Font.BOLD, 15));
                box_Line3.add(Box.createHorizontalStrut(65));
                box_Line3.add(txtDiaChi = new JTextField(20));
                txtDiaChi.setFont(new Font("Tohama", Font.BOLD, 13));
                box_Line3.add(Box.createHorizontalStrut(10));
                box_Line3.add(lbLuong = new JLabel("Lương: "));
                lbLuong.setFont(new Font("Tohama", Font.BOLD, 15));
                box_Line3.add(Box.createHorizontalStrut(10));
                box_Line3.add(txtLuong = new JTextField(20));
                txtLuong.setFont(new Font("Tohama", Font.BOLD, 13));

                Box box_Line7 = new Box(BoxLayout.X_AXIS);
                box_Line7.setPreferredSize(new Dimension(20, 35));

                box_Line7.add(btnThem = new JButton("Thêm nhân viên"));
                btnThem.setFont(new Font("Tohama", Font.BOLD, 15));
                box_Line7.add(Box.createHorizontalStrut(10));
                box_Line7.add(btnXoa = new JButton("Xóa nhân viên"));
                btnXoa.setFont(new Font("Tohama", Font.BOLD, 15));
                box_Line7.add(Box.createHorizontalStrut(10));
                box_Line7.add(btnSua = new JButton("Sửa thông tin"));
                btnSua.setFont(new Font("Tohama", Font.BOLD, 15));
                box_Line7.add(Box.createHorizontalStrut(10));
                box_Line7.add(btLamMoi = new JButton("Làm mới"));
                btLamMoi.setFont(new Font("Tohama", Font.BOLD, 15));

                Box box_Line4 = new Box(BoxLayout.X_AXIS);
                box_Line4.setPreferredSize(new Dimension(20, 25));

                box_Line4.add(lbTimKiemNV = new JLabel("Tìm kiếm: "));
                lbTimKiemNV.setFont(new Font("Tohama", Font.BOLD, 15));
                box_Line4.add(Box.createHorizontalStrut(48));
                txtTimKiemNV = new JPlaceholderTextField("Tìm kiếm bằng mã nhân viên hoặc tên nhân viên");
                box_Line4.add(txtTimKiemNV);
                txtTimKiemNV.setFont(new Font("Tohama", Font.BOLD, 13));
                box_Line4.add(Box.createHorizontalStrut(505));

                txtTimKiemNV.addKeyListener(new KeyListener() {
                        @Override
                        public void keyTyped(KeyEvent e) {

                        }

                        @Override
                        public void keyPressed(KeyEvent e) {

                        }

                        @Override
                        public void keyReleased(KeyEvent e) {
                                String searchText = txtTimKiemNV.getText().trim().toLowerCase();
                                filterEmployees(searchText);
                        }

                });

                box_NhanVien.add(box_Line1);
                box_NhanVien.add(Box.createVerticalStrut(20));
                box_NhanVien.add(box_Line2);
                box_NhanVien.add(Box.createVerticalStrut(20));
                box_NhanVien.add(box_Line3);
                box_NhanVien.add(Box.createVerticalStrut(20));
                box_NhanVien.add(box_Line7);
                box_NhanVien.add(Box.createVerticalStrut(50));
                box_NhanVien.add(box_Line4);

                pnlNhanVien.add(box_NhanVien);
                pnlCenter.add(pnlNhanVien);

                JPanel pnlTaiKhoan = new JPanel();
                pnlTaiKhoan.setPreferredSize(new Dimension(350, 150));
                TitledBorder boderTaiKhoan = new TitledBorder(BorderFactory.createLineBorder(new Color(51, 153, 255)),
                                "Tài khoản");
                boderTaiKhoan.setTitleFont(new Font("Tohama", Font.BOLD, 17));
                pnlTaiKhoan.setBorder(boderTaiKhoan);

                Box box_TaiKhoan = new Box(BoxLayout.Y_AXIS);
                box_TaiKhoan.setPreferredSize(new Dimension(300, 85));

                Box box_Line5 = new Box(BoxLayout.X_AXIS);

                box_Line5.add(lbTaiKhoan = new JLabel("Tài khoản: "));
                lbTaiKhoan.setFont(new Font("Tohama", Font.BOLD, 15));
                box_Line5.add(Box.createHorizontalStrut(20));
                box_Line5.add(txtTaiKhoan = new JTextField(20));
                txtTaiKhoan.setFont(new Font("Tohama", Font.BOLD, 13));

                Box box_Line6 = new Box(BoxLayout.X_AXIS);

                box_Line6.add(lbMatKhau = new JLabel("Mật khẩu: "));
                lbMatKhau.setFont(new Font("Tohama", Font.BOLD, 15));
                box_Line6.add(Box.createHorizontalStrut(25));
                box_Line6.add(txtMatKhau = new JTextField(20));
                txtMatKhau.setFont(new Font("Tohama", Font.BOLD, 13));

                box_TaiKhoan.add(box_Line5);
                box_TaiKhoan.add(Box.createVerticalStrut(20));
                box_TaiKhoan.add(box_Line6);

                pnlTaiKhoan.add(box_TaiKhoan);

                pnlCenter.add(pnlNhanVien);
                pnlCenter.add(pnlTaiKhoan);

                // JPanel south
                JPanel pnlSouth = new JPanel();
                JLabel jLabel_title2 = new JLabel("DANH SÁCH NHÂN VIÊN", JLabel.CENTER);
                jLabel_title2.setBackground(new Color(51, 153, 255));
                jLabel_title2.setOpaque(true);
                jLabel_title2.setFont(new Font("Tohama", Font.BOLD, 20));
                jLabel_title2.setForeground(Color.WHITE);
                jLabel_title2.setPreferredSize(new Dimension(1432, 35));

                pnlSouth.add(jLabel_title2);

                // Table nhân viên
                String[] columns = { "Mã nhân viên", "Tên nhân viên", "Giới tính", "Ngày Sinh", "Email",
                                "Số điện thoại",
                                "Chức vụ", "Địa chỉ", "Lương" };
                modelNhanVien = new DefaultTableModel(columns, 0);
                tableNhanVien = new JTable(modelNhanVien);
                tableNhanVien.setFont(new Font("Tahoma", Font.BOLD, 13));
                tableNhanVien.setRowHeight(30);

                tableNhanVien.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                        @Override
                        public void valueChanged(ListSelectionEvent e) {
                                if (!e.getValueIsAdjusting()) { // Đảm bảo sự kiện chỉ được xử lý một lần khi người dùng
                                                                // chọn
                                        int row = tableNhanVien.getSelectedRow();
                                        if (row >= 0) {
                                                fillForm(row);
                                        }
                                }
                        }
                });

                DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                for (int i = 0; i < tableNhanVien.getColumnCount(); i++) {
                        tableNhanVien.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                }

                JScrollPane paneNhanVien = new JScrollPane(tableNhanVien, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                paneNhanVien.setPreferredSize(new Dimension(1430, 350));
                paneNhanVien.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(51, 153, 255))));

                Font headerFont = new Font("Tahoma", Font.BOLD, 15);
                for (int i = 0; i < columns.length; i++) {
                        TableColumn column = tableNhanVien.getColumnModel().getColumn(i);
                        column.setHeaderRenderer(new HeaderRenderer(headerFont));
                        column.setPreferredWidth(150);
                }

                pnlSouth.add(paneNhanVien);

                add(pnlNorth, BorderLayout.NORTH);
                add(pnlCenter, BorderLayout.CENTER);
                add(pnlSouth, BorderLayout.SOUTH);

                // Update dữ liệu vào bảng
                updateDateTableNhanVien();

                // Add event
                btnThem.addActionListener(this);
                btnXoa.addActionListener(this);
                btnSua.addActionListener(this);
                btLamMoi.addActionListener(this);
                txtTimKiemNV.addActionListener(this);
        }

        // hiển thị nhân viên tìm kiếm
        private void filterEmployees(String searchText) {
                // Xóa tất cả hàng hiện có trong bảng
                modelNhanVien.setRowCount(0);

                // Lọc danh sách nhân viên dựa trên chuỗi tìm kiếm
                ArrayList<NhanVien> filteredEmployees = new ArrayList<>();
                for (NhanVien nv : nv_DAO.getAllNhanVien()) {
                        String maNV = nv.getMa().toLowerCase();
                        String hoTen = nv.getHoTen().toLowerCase();
                        if (maNV.contains(searchText) || hoTen.contains(searchText)) {
                                filteredEmployees.add(nv);
                        }
                }

                // Thêm các nhân viên thỏa mãn điều kiện tìm kiếm vào bảng
                for (NhanVien nv : filteredEmployees) {
                        modelNhanVien.addRow(new Object[] { nv.getMa(), nv.getHoTen(), nv.isGioiTinh() ? "Nam" : "Nữ",
                                        nv.getNgaySinh(), nv.getEmail(), nv.getSdt(), nv.getChucVu(), nv.getDiaChi(),
                                        nv.getLuong() });
                }

                // Cuộn tới vị trí nhân viên đầu tiên thỏa mãn điều kiện tìm kiếm
                if (!filteredEmployees.isEmpty()) {
                        tableNhanVien.scrollRectToVisible(tableNhanVien.getCellRect(0, 0, true));
                }
        }

        // Class HeaderRenderer để thiết lập font cho tiêu đề cột
        class HeaderRenderer implements TableCellRenderer {
                Font font;

                public HeaderRenderer(Font font) {
                        this.font = font;
                }

                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                boolean hasFocus,
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
                if (o.equals(btnThem)) {
                        if (valiDataInforNV()) {
                                // Sinh mã nhân viên tự động
                                String maNV = generateEmployeeID();

                                String hoTen = txtHoTen.getText().trim();
                                String gioiTinh = (String) cbo_GioiTinh.getSelectedItem();

                                String ngaySinhStr = txtNgaySinh.getText().trim();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                java.util.Date ngaySinhUtil = null;
                                java.sql.Date ngaySinhDate = null;

                                try {
                                        ngaySinhUtil = dateFormat.parse(ngaySinhStr);
                                        ngaySinhDate = new java.sql.Date(ngaySinhUtil.getTime());
                                } catch (ParseException e2) {
                                        e2.printStackTrace(); // Xử lý ngoại lệ nếu có lỗi khi chuyển đổi
                                }

                                String email = txtEmail.getText().trim();
                                String sdt = txtSDT.getText().trim();
                                String chucVu = (String) cboChucVu.getSelectedItem();
                                String diaChi = txtDiaChi.getText().trim();
                                String luongStr = txtLuong.getText().trim();

                                String taiKhoan = txtTaiKhoan.getText().trim();
                                String matKhau = txtMatKhau.getText().trim();

                                // Kiểm tra mã nhân viên đã tồn tại hay chưa
                                boolean isExist = false;
                                for (NhanVien nv : nv_DAO.getAllNhanVien()) {
                                        if (nv.getMa().equals(maNV)) {
                                                isExist = true;
                                                break;
                                        }
                                }

                                // Kiểm tra tài khoản đã tồn tại hay chưa
                                boolean isExistAccount = false;
                                for (TaiKhoan tk : tk_DAO.getAllTaiKhoan()) {
                                        if (tk.getTenTaiKhoan().equals(taiKhoan)) {
                                                isExistAccount = true;
                                                break;
                                        }
                                }

                                if (isExistAccount) {
                                        JOptionPane.showMessageDialog(this, "Tài khoản đã tồn tại!", "Thông báo",
                                                        JOptionPane.ERROR_MESSAGE);
                                }

                                if (isExist) {
                                        JOptionPane.showMessageDialog(this, "Mã nhân viên đã tồn tại!", "Thông báo",
                                                        JOptionPane.ERROR_MESSAGE);
                                } else {
                                        // Thêm nhân viên vào CSDL
                                        NhanVien nv = new NhanVien(maNV, hoTen, email, sdt,
                                                        gioiTinh.equals("Nam"), ngaySinhDate, chucVu, diaChi,
                                                        Double.parseDouble(luongStr));
                                        TaiKhoan tk = new TaiKhoan(taiKhoan, matKhau, nv);
                                        if (nv_DAO.create(nv) && tk_DAO.create(tk)) {
                                                // Thêm nhân viên vào bảng
                                                modelNhanVien.addRow(new Object[] { maNV, hoTen, gioiTinh, ngaySinhDate,
                                                                email, sdt, chucVu, diaChi, luongStr });
                                                JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!",
                                                                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                                        } else {
                                                JOptionPane.showMessageDialog(this, "Thêm nhân viên thất bại!",
                                                                "Thông báo", JOptionPane.ERROR_MESSAGE);
                                        }
                                }
                        }
                } else if (o.equals(btnXoa)) {
                        int row = tableNhanVien.getSelectedRow();
                        if (row >= 0) {
                                int confirm = JOptionPane.showConfirmDialog(this,
                                                "Bạn có chắc chắn muốn xóa nhân viên này không?", "Xác nhận",
                                                JOptionPane.YES_NO_OPTION);
                                if (confirm == JOptionPane.YES_OPTION) {
                                        String maNV = tableNhanVien.getValueAt(row, 0).toString();

                                        NhanVien nv = new NhanVien();
                                        nv.setMa(maNV);

                                        boolean deleted = nv_DAO.delete(nv);
                                        if (deleted) {
                                                JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!",
                                                                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                                                clearField();
                                                modelNhanVien.removeRow(row);
                                        } else {
                                                JOptionPane.showMessageDialog(this, "Xóa nhân viên thất bại!",
                                                                "Thông báo", JOptionPane.ERROR_MESSAGE);
                                        }
                                }
                        } else {
                                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa!", "Thông báo",
                                                JOptionPane.ERROR_MESSAGE);
                        }
                } else if (o.equals(btnSua)) {
                        int row = tableNhanVien.getSelectedRow();
                        if (row != -1) {
                                if (valiDataInforNV()) {
                                        String maNV = tableNhanVien.getValueAt(row, 0).toString();
                                        String hoTen = txtHoTen.getText().trim();
                                        String gioiTinh = cbo_GioiTinh.getSelectedItem().toString();
                                        String ngaySinh = txtNgaySinh.getText().trim();
                                        String email = txtEmail.getText().trim();
                                        String sdt = txtSDT.getText().trim();
                                        String chucVu = cboChucVu.getSelectedItem().toString();
                                        String diaChi = txtDiaChi.getText().trim();
                                        String luong = txtLuong.getText().trim();

                                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                        java.sql.Date ngaySinhDate = null;
                                        try {
                                                java.util.Date parsedDate = dateFormat.parse(ngaySinh);
                                                ngaySinhDate = new java.sql.Date(parsedDate.getTime());
                                        } catch (ParseException e2) {
                                                e2.printStackTrace(); // Xử lý ngoại lệ nếu có lỗi khi chuyển đổi
                                        }

                                        NhanVien nv = new NhanVien(maNV, hoTen, email, sdt,
                                                        gioiTinh.equals("Nam") ? true : false,
                                                        ngaySinhDate, chucVu, diaChi, Double.parseDouble(luong));

                                        if (nv_DAO.update(nv)) {
                                                modelNhanVien.setValueAt(hoTen, row, 1);
                                                modelNhanVien.setValueAt(gioiTinh, row, 2);
                                                modelNhanVien.setValueAt(ngaySinhDate, row, 3);
                                                modelNhanVien.setValueAt(email, row, 4);
                                                modelNhanVien.setValueAt(sdt, row, 5);
                                                modelNhanVien.setValueAt(chucVu, row, 6);
                                                modelNhanVien.setValueAt(diaChi, row, 7);
                                                modelNhanVien.setValueAt(luong, row, 8);
                                                JOptionPane.showMessageDialog(this,
                                                                "Cập nhật thông tin nhân viên thành công!", "Thông báo",
                                                                JOptionPane.INFORMATION_MESSAGE);
                                        } else {
                                                JOptionPane.showMessageDialog(this,
                                                                "Cập nhật thông tin nhân viên thất bại!", "Thông báo",
                                                                JOptionPane.ERROR_MESSAGE);
                                        }

                                }
                        } else {
                                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần cập nhật thông tin!",
                                                "Thông báo",
                                                JOptionPane.ERROR_MESSAGE);
                        }
                } else if (o.equals(btLamMoi)) {
                        clearField();
                }
        }

        private void clearField() {
                txtHoTen.setText("");
                cbo_GioiTinh.setSelectedIndex(0);
                txtNgaySinh.setText("");
                txtEmail.setText("");
                txtSDT.setText("");
                cboChucVu.setSelectedIndex(0);
                txtDiaChi.setText("");
                txtLuong.setText("");
                txtTaiKhoan.setText("");
                txtMatKhau.setText("");
                txtHoTen.requestFocus();
                tableNhanVien.clearSelection();
        }

        private String generateEmployeeID() {
                try {
                        ConnectDB.getInstance();
                        Connection con = ConnectDB.getConnection();
                        Statement statement = con.createStatement();

                        // Tạo mã nhân viên mặc định
                        String employeeID = null;
                        int employeeCount = 0;

                        // Lặp cho đến khi tìm được mã nhân viên chưa được sử dụng
                        while (true) {
                                // Tạo mã nhân viên mới dựa trên số lượng nhân viên hiện có
                                employeeCount++;
                                employeeID = "NV" + employeeCount;

                                // Kiểm tra xem mã nhân viên đã tồn tại trong cơ sở dữ liệu chưa
                                ResultSet rs = statement.executeQuery(
                                                "SELECT COUNT(*) FROM NhanVien WHERE maNV = '" + employeeID + "'");

                                // Lấy số lượng nhân viên từ kết quả truy vấn
                                if (rs.next()) {
                                        int count = rs.getInt(1);

                                        // Nếu số lượng nhân viên có mã như vậy là 0, tức là mã chưa được sử dụng, thoát
                                        // khỏi vòng lặp
                                        if (count == 0) {
                                                break;
                                        }
                                }
                        }

                        // Trả về mã nhân viên đã được tạo
                        return employeeID;
                } catch (SQLException e) {
                        e.printStackTrace();
                        // Xử lý ngoại lệ nếu có lỗi khi thực hiện truy vấn
                        return null; // hoặc throw một ngoại lệ để báo lỗi
                }
        }

        private boolean valiDataInforNV() {
                String hoTen = txtHoTen.getText().trim();
                String gioiTinh = cbo_GioiTinh.getSelectedItem().toString();
                String ngaySinh = txtNgaySinh.getText().trim();
                String email = txtEmail.getText().trim();
                String sdt = txtSDT.getText().trim();
                String chucVu = cboChucVu.getSelectedItem().toString();
                String diaChi = txtDiaChi.getText().trim();
                String luong = txtLuong.getText().trim();
                String taiKhoan = txtTaiKhoan.getText().trim();
                String matKhau = txtMatKhau.getText().trim();

                if (!(hoTen.length() > 0 && hoTen.matches("[A-Z\\p{L}][a-z\\p{L}]+(\\s[A-Z\\p{L}][a-z\\p{L}]+)+"))) {
                        txtHoTen.requestFocus();
                        JOptionPane.showMessageDialog(this,
                                        "Tên nhân viên không được để trống và phải bắt đầu bằng ký tự in hoa!",
                                        "Thông báo",
                                        JOptionPane.ERROR_MESSAGE);
                        return false;
                }

                if (gioiTinh.equals("Chọn giới tính")) {
                        cbo_GioiTinh.requestFocus();
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn giới tính!", "Thông báo",
                                        JOptionPane.ERROR_MESSAGE);
                        return false;
                }

                if (!(ngaySinh.length() > 0 && ngaySinh.matches("\\d{2}/\\d{2}/\\d{4}"))) {
                        txtNgaySinh.requestFocus();
                        JOptionPane.showMessageDialog(this,
                                        "Ngày sinh không được để trống và phải đúng định dạng dd/MM/yyyy!",
                                        "Thông báo", JOptionPane.ERROR_MESSAGE);
                        return false;
                }

                if (!(email.length() > 0 && email.matches("\\w+@\\w+\\.\\w+"))) {
                        txtEmail.requestFocus();
                        JOptionPane.showMessageDialog(this, "Email không được để trống và phải đúng định dạng",
                                        "Thông báo",
                                        JOptionPane.ERROR_MESSAGE);
                        return false;
                }

                if (!(sdt.length() > 0 && sdt.matches("0\\d{9}"))) {
                        txtSDT.requestFocus();
                        JOptionPane.showMessageDialog(this, "Số điện thoại không được để trống và phải đúng định dạng",
                                        "Thông báo", JOptionPane.ERROR_MESSAGE);
                        return false;
                }

                if (chucVu.equals("Chọn chức vụ")) {
                        cboChucVu.requestFocus();
                        JOptionPane.showMessageDialog(this, "Vui lòng chọn chức vụ!", "Thông báo",
                                        JOptionPane.ERROR_MESSAGE);
                        return false;
                }

                if (diaChi.length() == 0) {
                        txtDiaChi.requestFocus();
                        JOptionPane.showMessageDialog(this, "Địa chỉ không được để trống!", "Thông báo",
                                        JOptionPane.ERROR_MESSAGE);
                        return false;
                }

                if (!(luong.length() > 0 && luong.matches("\\d+"))) {
                        txtLuong.requestFocus();
                        JOptionPane.showMessageDialog(this, "Lương không được để trống và phải là số nguyên dương!",
                                        "Thông báo", JOptionPane.ERROR_MESSAGE);
                        return false;
                }

                if (taiKhoan.length() == 0) {
                        txtTaiKhoan.requestFocus();
                        JOptionPane.showMessageDialog(this, "Tài khoản không được để trống!", "Thông báo",
                                        JOptionPane.ERROR_MESSAGE);
                        return false;
                }

                if (matKhau.length() == 0) {
                        txtMatKhau.requestFocus();
                        JOptionPane.showMessageDialog(this, "Mật khẩu không được để trống!", "Thông báo",
                                        JOptionPane.ERROR_MESSAGE);
                        return false;
                }

                return true;
        }

        private void updateDateTableNhanVien() {
                ArrayList<NhanVien> listNhanVien = nv_DAO.getAllNhanVien();
                for (NhanVien nv : listNhanVien) {
                        modelNhanVien.addRow(new Object[] { nv.getMa(), nv.getHoTen(), nv.isGioiTinh() ? "Nam" : "Nữ",
                                        nv.getNgaySinh(), nv.getEmail(), nv.getSdt(), nv.getChucVu(), nv.getDiaChi(),
                                        nv.getLuong() });
                }
        }

        private void fillForm(int row) {
                txtHoTen.setText(modelNhanVien.getValueAt(row, 1).toString());
                cbo_GioiTinh.setSelectedItem(modelNhanVien.getValueAt(row, 2).toString());

                // Định dạng lại ngày sinh
                Date ngaySinh = (Date) modelNhanVien.getValueAt(row, 3);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String ngaySinhFormatted = dateFormat.format(ngaySinh);
                txtNgaySinh.setText(ngaySinhFormatted);

                txtEmail.setText(modelNhanVien.getValueAt(row, 4).toString());
                txtSDT.setText(modelNhanVien.getValueAt(row, 5).toString());
                cboChucVu.setSelectedItem(modelNhanVien.getValueAt(row, 6).toString());
                txtDiaChi.setText(modelNhanVien.getValueAt(row, 7).toString());
                txtLuong.setText(modelNhanVien.getValueAt(row, 8).toString());
        }

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
                                                setFont(new Font(getFont().getName(), Font.BOLD, getFont().getSize()));
                                        }
                                }

                                @Override
                                public void focusLost(FocusEvent e) {
                                        if (getText().isEmpty()) {
                                                setForeground(Color.GRAY);
                                                setFont(new Font(getFont().getName(), Font.ITALIC,
                                                                getFont().getSize()));
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
                                                (getHeight() + g.getFontMetrics().getAscent()
                                                                - g.getFontMetrics().getDescent()) / 2);
                        }
                }

                public String getPlaceholder() {
                        return placeholder;
                }

                public void setPlaceholder(String placeholder) {
                        this.placeholder = placeholder;
                }
        }

        public void checkAdmin(boolean isAdmin) {
                btnThem.setEnabled(isAdmin);
                btnXoa.setEnabled(isAdmin);
                btnSua.setEnabled(isAdmin);
                btLamMoi.setEnabled(isAdmin);
        }

}
