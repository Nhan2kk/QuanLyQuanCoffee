package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Calendar;
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
import entity.HoaDon;

public class Form_ThongKeDoanhThu extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    JPanel jPanel_North, jPanel_Center, jPanel_South;
    JLabel jLabel_Title, jLabel_Title2, jLabel_Ngay, jLabel_Thang, jLabel_Nam;
    JTextField text_Ngay, text_Thang, text_Nam;
    JButton btnThongKe, btnXemCt, btnRef;
    JCheckBox cbNgay, cbThang, cbNam;
    JTable tableTKDoanhThu;
    DefaultTableModel modelTableTKDoanhThu;
    HoaDonBanHang_DAO HD_dao;
    JComboBox<String> comboBoxNgay, comboBoxThang, comboBoxNam;

    public Form_ThongKeDoanhThu() {

        HD_dao = new HoaDonBanHang_DAO();
        ConnectDB.getInstance().connect();

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 50, 0, 50));

        jPanel_North = new JPanel();
        jPanel_North.setBackground(new Color(51, 153, 255));
        jPanel_Center = new JPanel();
        jPanel_South = new JPanel();

        // Title 1

        jPanel_North.add(jLabel_Title = new JLabel("THỐNG KÊ DOANH THU"));
        jLabel_Title.setFont(new Font("Arial", Font.BOLD, 20));
        jLabel_Title.setForeground(Color.WHITE);
        add(jPanel_North, BorderLayout.NORTH);

        // Vung center

        jPanel_Center.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(51, 153, 255)), "Thống kê",
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.BOLD, 18)));
        //
        // Tao cac comboBox
        String[] arrNgay = new String[32];
        String[] arrThang = new String[13];
        String[] arrNam = new String[11];
        // Thêm phần tử trống vào đầu danh sách dữ liệu của combobox
        arrNgay[0] = "";
        arrThang[0] = "";
        arrNam[0] = "";
        //
        for (int i = 1; i < 32; i++) {
            arrNgay[i] = String.valueOf(i);
        }

        for (int i = 1; i < 13; i++) {
            arrThang[i] = String.valueOf(i);
        }

        // Lấy năm hiện tại và 9 năm trước đó
        int currentYear = java.time.Year.now().getValue();
        for (int i = 1; i < 11; i++) {
            arrNam[i] = String.valueOf(currentYear - (i - 1));
        }

        comboBoxNgay = new JComboBox<>(arrNgay);
        comboBoxThang = new JComboBox<>(arrThang);
        comboBoxNam = new JComboBox<>(arrNam);
        //
        Box box1 = new Box(BoxLayout.X_AXIS);
        box1.add(jLabel_Ngay = new JLabel("Ngày:   "));
        jLabel_Ngay.setFont(new Font("Arial", Font.PLAIN, 16));
        box1.add(comboBoxNgay);
        box1.add(Box.createHorizontalStrut(50));
        box1.add(jLabel_Thang = new JLabel("Tháng:   "));
        jLabel_Thang.setFont(new Font("Arial", Font.PLAIN, 16));
        box1.add(comboBoxThang);
        box1.add(Box.createHorizontalStrut(50));
        box1.add(jLabel_Nam = new JLabel("Năm:   "));
        jLabel_Nam.setFont(new Font("Arial", Font.PLAIN, 16));
        box1.add(comboBoxNam);
        box1.add(Box.createHorizontalStrut(50));

        Box box2 = new Box(BoxLayout.X_AXIS);
        box2.add(cbNgay = new JCheckBox("Thống kê theo ngày"));
        cbNgay.setFont(new Font("Arial", Font.PLAIN, 13));
        box2.add(Box.createHorizontalStrut(220));
        box2.add(cbThang = new JCheckBox("Thống kê theo tháng"));
        cbThang.setFont(new Font("Arial", Font.PLAIN, 13));
        box2.add(Box.createHorizontalStrut(220));
        box2.add(cbNam = new JCheckBox("Thống kê theo năm"));
        cbNam.setFont(new Font("Arial", Font.PLAIN, 13));
        box2.add(Box.createHorizontalStrut(100));

        Box box3 = new Box(BoxLayout.X_AXIS);
        box3.add(btnThongKe = new JButton("Thống kê"));
        btnThongKe.setIcon(new ImageIcon("images\\findpage.png"));
        box3.add(Box.createHorizontalStrut(100));
        box3.add(btnXemCt = new JButton("Xem chi tiết hóa đơn"));
        btnXemCt.setIcon(new ImageIcon("images\\findpage.png"));
        box3.add(Box.createHorizontalStrut(100));
        box3.add(btnRef = new JButton("Refresh"));
        btnRef.setIcon(new ImageIcon("images\\findpage.png"));

        Box box4 = new Box(BoxLayout.Y_AXIS);

        box4.add(box1);
        box4.add(Box.createVerticalStrut(30));
        box4.add(box2);
        box4.add(Box.createVerticalStrut(30));
        box4.add(box3);
        box4.add(Box.createVerticalStrut(30));

        jPanel_Center.add(box4);

        jPanel_Center.setPreferredSize(new Dimension(1000, 250));

        add(jPanel_Center, BorderLayout.CENTER);

        // Table sản phẩm
        String[] columnsHoaDon = { "Mã Hóa Đơn", "Nhân Viên", "Khách Hàng", "Ngày Lập", "Tổng Thanh Toán" };

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
        pane_HoaDon.setPreferredSize(new Dimension(1434, 280));
        pane_HoaDon.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(51, 153, 255))));

        Box box5 = new Box(BoxLayout.Y_AXIS);
        box5.add(Box.createVerticalStrut(30));
        box5.add(jLabel_Title2 = new JLabel("DANH SÁCH HÓA ĐƠN BÁN HÀNG"));
        jLabel_Title2.setFont(new Font("Arial", Font.BOLD, 20));
        jLabel_Title2.setAlignmentX(Component.CENTER_ALIGNMENT);
        jLabel_Title2.setForeground(Color.WHITE);
        jLabel_Title2.setBackground(new Color(51, 153, 255));
        jLabel_Title2.setOpaque(true);
        jLabel_Title2.setPreferredSize(new Dimension(1000, 50));

        box5.add(Box.createVerticalStrut(30));
        box5.add(pane_HoaDon, BorderLayout.SOUTH);

        // set kích thước cho combobox
        comboBoxNgay.setPreferredSize(new Dimension(60, 30));
        comboBoxThang.setPreferredSize(new Dimension(60, 30));
        comboBoxNam.setPreferredSize(new Dimension(60, 30));
        // set font chữ và cỡ chữ cho nội dung combo box
        Font font = new Font("Arial", Font.PLAIN, 13);
        comboBoxNgay.setFont(font);
        comboBoxThang.setFont(font);
        comboBoxNam.setFont(font);
        //
        jPanel_South.add(box5);
        add(jPanel_South, BorderLayout.SOUTH);

        ReadDatabaseToTable2();

        // add event
        btnThongKe.addActionListener(this);
        btnRef.addActionListener(this);
    }

    private void updateTableWithFilteredData(List<HoaDon> filteredList) {
        // Xóa dữ liệu hiện có trong bảng
        modelTableTKDoanhThu.setRowCount(0);

        // Thêm dữ liệu mới vào bảng từ danh sách hóa đơn đã lọc
        for (HoaDon hd : filteredList) {
            modelTableTKDoanhThu.addRow(new Object[] {
                    hd.getMaHoaDon(),
                    hd.getNhanVien().getHoTen(),
                    hd.getKhachHang().getTenKH(),
                    hd.getNgayLapHD(),
                    hd.getTongThanhToan()
            });
        }
    }

    private double calculateTotalRevenue(List<HoaDon> filteredList) {
        double totalRevenue = 0.0;
        for (HoaDon hd : filteredList) {
            totalRevenue += hd.getTongThanhToan();
        }
        return totalRevenue;
    }

    // Hàm lọc hóa đơn theo ngày, tháng, năm
    private List<HoaDon> filterByDayMonthYear(String day, String month, String year) {
        List<HoaDon> list = HD_dao.getAllHoaDon();
        List<HoaDon> filteredList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        for (HoaDon hd : list) {
            cal.setTime(hd.getNgayLapHD());
            int dayOfHoaDon = cal.get(Calendar.DAY_OF_MONTH);
            int monthOfHoaDon = cal.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0
            int yearOfHoaDon = cal.get(Calendar.YEAR);
            if (String.valueOf(dayOfHoaDon).equals(day) && String.valueOf(monthOfHoaDon).equals(month)
                    && String.valueOf(yearOfHoaDon).equals(year)) {
                filteredList.add(hd);
            }
        }
        return filteredList;
    }

    // Hàm lọc hóa đơn theo ngày, tháng
    private List<HoaDon> filterByDayMonth(String day, String month) {
        List<HoaDon> list = HD_dao.getAllHoaDon();
        List<HoaDon> filteredList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        for (HoaDon hd : list) {
            cal.setTime(hd.getNgayLapHD());
            int dayOfHoaDon = cal.get(Calendar.DAY_OF_MONTH);
            int monthOfHoaDon = cal.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0
            if (String.valueOf(dayOfHoaDon).equals(day) && String.valueOf(monthOfHoaDon).equals(month)) {
                filteredList.add(hd);
            }
        }
        return filteredList;
    }

    // Hàm lọc hóa đơn theo ngày, năm
    private List<HoaDon> filterByDayYear(String day, String year) {
        List<HoaDon> list = HD_dao.getAllHoaDon();
        List<HoaDon> filteredList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        for (HoaDon hd : list) {
            cal.setTime(hd.getNgayLapHD());
            int dayOfHoaDon = cal.get(Calendar.DAY_OF_MONTH);
            int yearOfHoaDon = cal.get(Calendar.YEAR);
            if (String.valueOf(dayOfHoaDon).equals(day) && String.valueOf(yearOfHoaDon).equals(year)) {
                filteredList.add(hd);
            }
        }
        return filteredList;
    }

    // Hàm lọc hóa đơn theo tháng, năm
    private List<HoaDon> filterByMonthYear(String month, String year) {
        List<HoaDon> list = HD_dao.getAllHoaDon();
        List<HoaDon> filteredList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        for (HoaDon hd : list) {
            cal.setTime(hd.getNgayLapHD());
            int monthOfHoaDon = cal.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0
            int yearOfHoaDon = cal.get(Calendar.YEAR);
            if (String.valueOf(monthOfHoaDon).equals(month) && String.valueOf(yearOfHoaDon).equals(year)) {
                filteredList.add(hd);
            }
        }
        return filteredList;
    }

    // Hàm lọc danh sách hóa đơn theo năm
    private List<HoaDon> filterByYear(String year) {
        List<HoaDon> list = HD_dao.getAllHoaDon();
        List<HoaDon> filteredList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        for (HoaDon hd : list) {
            cal.setTime(hd.getNgayLapHD());
            int yearOfHoaDon = cal.get(Calendar.YEAR);
            if (String.valueOf(yearOfHoaDon).equals(year)) {
                filteredList.add(hd);
            }
        }
        return filteredList;
    }

    // Hàm lọc hóa đơn theo tháng
    private List<HoaDon> filterByMonth(String month) {
        List<HoaDon> list = HD_dao.getAllHoaDon();
        List<HoaDon> filteredList2 = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        for (HoaDon hd : list) {
            cal.setTime(hd.getNgayLapHD());
            int monthOfHoaDon = cal.get(Calendar.MONTH) + 1;
            if (String.valueOf(monthOfHoaDon).equals(month)) {
                filteredList2.add(hd);
            }
        }
        return filteredList2;
    }

    // Hàm lọc hóa đơn theo ngày
    private List<HoaDon> filterByDay(String day) {
        List<HoaDon> list = HD_dao.getAllHoaDon();
        List<HoaDon> filteredList3 = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        for (HoaDon hd : list) {
            cal.setTime(hd.getNgayLapHD());
            int dayOfHoaDon = cal.get(Calendar.DAY_OF_MONTH);
            if (String.valueOf(dayOfHoaDon).equals(day)) {
                filteredList3.add(hd);
            }
        }
        return filteredList3;
    }

    //
    private void resetForm() {
        // Reset các giá trị của combobox về giá trị mặc định
        comboBoxNgay.setSelectedIndex(0);
        comboBoxThang.setSelectedIndex(0);
        comboBoxNam.setSelectedIndex(0);

        // Bỏ chọn các checkbox
        cbNgay.setSelected(false);
        cbThang.setSelected(false);
        cbNam.setSelected(false);

        // Xóa dữ liệu hiện có trong bảng
        modelTableTKDoanhThu.setRowCount(0);
        ReadDatabaseToTable2();
    }

    //
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

    public void ReadDatabaseToTable2() {
        List<HoaDon> list = HD_dao.getAllHoaDon();

        for (HoaDon hd : list) {
            modelTableTKDoanhThu.addRow(new Object[] { hd.getMaHoaDon(), hd.getNhanVien().getHoTen(),
                    hd.getKhachHang().getTenKH(), hd.getNgayLapHD(), hd.getTongThanhToan(), hd.getGioVao() });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o.equals(btnThongKe)) {
            // Xóa dữ liệu hiện có trong bảng
            modelTableTKDoanhThu.setRowCount(0);

            // Kiểm tra các checkbox đã được chọn
            boolean isNgaySelected = cbNgay.isSelected();
            boolean isThangSelected = cbThang.isSelected();
            boolean isNamSelected = cbNam.isSelected();

            // Nếu không có checkbox nào được chọn, không thực hiện thống kê
            if (!isNgaySelected && !isThangSelected && !isNamSelected) {
                JOptionPane.showMessageDialog(Form_ThongKeDoanhThu.this,
                        "Vui lòng chọn ít nhất một tiêu chí thống kê.");
                return;
            }

            // Lấy giá trị từ các combobox
            String selectedDay = comboBoxNgay.getSelectedItem().toString();
            String selectedMonth = comboBoxThang.getSelectedItem().toString();
            String selectedYear = comboBoxNam.getSelectedItem().toString();

            List<HoaDon> filteredList = new ArrayList<>();
            double totalRevenue = 0.0;

            // Lọc danh sách hóa đơn và tính tổng doanh thu theo tiêu chí đã chọn
            if (isNgaySelected && isThangSelected && isNamSelected) {
                filteredList = filterByDayMonthYear(selectedDay, selectedMonth, selectedYear);
                totalRevenue = calculateTotalRevenue(filteredList);
            } else if (isNgaySelected && isThangSelected) {
                filteredList = filterByDayMonth(selectedDay, selectedMonth);
                totalRevenue = calculateTotalRevenue(filteredList);
            } else if (isNgaySelected && isNamSelected) {
                filteredList = filterByDayYear(selectedDay, selectedYear);
                totalRevenue = calculateTotalRevenue(filteredList);
            } else if (isThangSelected && isNamSelected) {
                filteredList = filterByMonthYear(selectedMonth, selectedYear);
                totalRevenue = calculateTotalRevenue(filteredList);
            } else if (isNgaySelected) {
                filteredList = filterByDay(selectedDay);
                totalRevenue = calculateTotalRevenue(filteredList);
            } else if (isThangSelected) {
                filteredList = filterByMonth(selectedMonth);
                totalRevenue = calculateTotalRevenue(filteredList);
            } else if (isNamSelected) {
                filteredList = filterByYear(selectedYear);
                totalRevenue = calculateTotalRevenue(filteredList);
            }

            // Hiển thị tổng doanh thu
            updateTableWithFilteredData(filteredList);
            JOptionPane.showMessageDialog(Form_ThongKeDoanhThu.this, "Tổng doanh thu: " + totalRevenue);
        } else if (o.equals(btnRef)) {
            resetForm();
        }
    }

}
