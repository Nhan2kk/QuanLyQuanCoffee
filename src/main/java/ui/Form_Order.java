package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.Format;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import connectDB.ConnectDB;
import dao.Ban_DAO;
import dao.ChiTietHoaDon_DAO;
import dao.DonGiaSanPham_DAO;
import dao.HoaDonBanHang_DAO;
import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import dao.MyTableClass;
import dao.MyTextClass;
import dao.NhanVien_DAO;
import dao.SanPham_DAO;
import entity.Ban;
import entity.ChiTietHoaDon;
import entity.DonGiaSanPham;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.SanPham;

public class Form_Order extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    private Map<String, JPanel> tabPanels;
    private Map<String, Integer> searchResultsPositions;
    JTabbedPane tabbedPane;
    static DefaultTableModel modelOrder;
    JButton btnThanhToan, btnHuyOrder, btnThanhToanKhongIn, thanhVien;
    JTextField text_MaGiamGiam;
    static JTextField text_TienKhachDua;
    static JTextField text_TienThoi;
    static JTextField text_MaGiamGia;
    static JTextField text_TongTien;
    JTextField text_GiamGia;
    static JTextField text_TongThanhToan;
    JComboBox<String> cbo_ChonBan;
    String[] banOrder = {"","01", "02", "03", "04", "05", "06", "07", "08",
            "09", "10" };
    JLabel lbChonBan, lbTienKhachDua, lbTienTraLai, lbMaGiamGiam, lbTongTien, lbGiamGia, lbTongThanhToan;
    SanPham_DAO sp_Dao;
    Ban_DAO banDao;
    DonGiaSanPham_DAO donGia_DAO;
    private JLabel lbtenKH;
    private JTextField text_TenKH;
    private JLabel lbdtl;
    private static JTextField text_dtl;
    private JButton btdoiDTL;

    private NhanVien loggedInEmployee;
	private MyTextClass myTextClass;
	private PDPageContentStream contentStream;
	private double tienKhachDua;
	private ChiTietHoaDon_DAO cthd_dao;
	private HoaDonBanHang_DAO hdbhDao;
	private NhanVien_DAO nvdao;

    public Form_Order() {
        // Khởi tạo kết nối đến CSDL
        ConnectDB.getInstance().connect();
        sp_Dao = new SanPham_DAO();
        banDao = new Ban_DAO();
        donGia_DAO = new DonGiaSanPham_DAO();

        setLayout(new BorderLayout());
        tabPanels = new HashMap<>();
        searchResultsPositions = new HashMap<>();

        // Tạo các tab cho các loại sản phẩm
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Tahoma", Font.BOLD, 15));
        String[] categories = { "Tất cả", "Cà phê", "Trà sữa", "Trà trái cây", "Nước ép", "Soda", "Sinh tố" };
        String[] iconPaths = { "images\\Check-Mark-Button-icon.png", "images\\bxs-coffee-alt-icon.png",
                "images\\Bubble-Tea-icon.png",
                "images\\Food-Tea-icon.png", "images\\Juice-icon.png", "images\\bottle-soda-classic-icon.png",
                "images\\Ice-Cream-icon.png" };
        for (int i = 0; i < categories.length; i++) {
            JPanel panel = new JPanel(new BorderLayout()); // Sử dụng BorderLayout để chứa thanh tìm kiếm ở trên
            JPanel contentPanel = new JPanel(new GridLayout(0, 4, 10, 10));
            JScrollPane contentScrollPane = new JScrollPane(contentPanel);

            // Thêm thanh tìm kiếm
            JPlaceholderTextField searchField = new JPlaceholderTextField("Tìm kiếm bằng tên/Mã sản phẩm");
            searchField.setFont(new Font("Tahoma", Font.PLAIN, 14));
            searchField.setBorder(BorderFactory.createCompoundBorder(searchField.getBorder(),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            panel.add(searchField, BorderLayout.NORTH);

            panel.add(searchField, BorderLayout.NORTH);

            panel.add(contentScrollPane, BorderLayout.CENTER);

            ImageIcon icon = new ImageIcon(iconPaths[i]);
            tabbedPane.addTab(categories[i], icon, panel);
            tabPanels.put(categories[i], contentPanel);
        }

        // Bổ sung mã nguồn cho việc tìm kiếm sản phẩm
        for (String category : categories) {
            addSearchFunctionality(category);
        }

        // JPanel EAST
        JPanel jPanel_East = new JPanel(new BorderLayout());
        Box boxE = Box.createVerticalBox();

        Box boxE1 = Box.createHorizontalBox();

        JLabel lbTD = new JLabel("HÓA ĐƠN");
        lbTD.setFont(new Font("Tahoma", Font.BOLD, 22));
        lbTD.setPreferredSize(new Dimension(100, 68));
        lbTD.setForeground(Color.WHITE);
        boxE1.setBackground(new Color(51, 204, 255));
        boxE1.setOpaque(true);
        boxE1.add(Box.createRigidArea(new Dimension(226, 10)));
        boxE1.add(lbTD);
        boxE1.add(Box.createRigidArea(new Dimension(226, 10)));

        // Table order
        String[] columnNameOrder = { "STT", "Tên", "SL", "Size", "T.Tiền" };
        modelOrder = new DefaultTableModel(columnNameOrder, 0);
        final JTable tableOrder = new JTable(modelOrder);
        tableOrder.setFont(new Font("Tahoma", Font.PLAIN, 13));
        tableOrder.setRowHeight(30);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tableOrder.getColumnCount(); i++) {
            tableOrder.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane pane_OrderDrink = new JScrollPane(tableOrder);
        pane_OrderDrink.setPreferredSize(new Dimension(550, 320));

        Font headerFont = new Font("Tahoma", Font.BOLD, 16);
        for (int i = 0; i < columnNameOrder.length; i++) {
            TableColumn column = tableOrder.getColumnModel().getColumn(i);
            column.setHeaderRenderer((TableCellRenderer) new HeaderRenderer(headerFont));
            column.setPreferredWidth(150);

        }
        boxE.add(boxE1);
        boxE.add(pane_OrderDrink);

        // JPanel center
        JPanel jPanel_Center = new JPanel();

        Box box_Center = new Box(BoxLayout.Y_AXIS);

        Box box_Center_1 = new Box(BoxLayout.Y_AXIS);
        box_Center_1.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        box_Center_1.setPreferredSize(new Dimension(550, 195));

        Box box_8 = new Box(BoxLayout.X_AXIS);
        box_8.add(Box.createRigidArea(new Dimension(10, 10)));
        box_8.add(lbtenKH = new JLabel("Nhập sđt KH:"));
        lbtenKH.setFont(new Font("Tohama", Font.BOLD, 15));
        box_8.add(Box.createHorizontalStrut(39));
        box_8.add(text_TenKH = new JTextField(10));
        // text_TenKH.setEditable(false);
        text_TenKH.setFont(new Font("Tohama", Font.BOLD, 15));
        box_8.add(Box.createRigidArea(new Dimension(10, 10)));

        box_8.add(thanhVien = new JButton("Tìm"));
        thanhVien.setFont(new Font("Tohama", Font.BOLD, 15));
        box_8.add(Box.createRigidArea(new Dimension(10, 10)));

        Box box_1 = new Box(BoxLayout.X_AXIS);
        box_1.add(Box.createRigidArea(new Dimension(10, 10)));
        box_1.add(lbChonBan = new JLabel("Bàn:"));
        lbChonBan.setFont(new Font("Tohama", Font.BOLD, 15));
        box_1.add(Box.createHorizontalStrut(100));
        box_1.add(cbo_ChonBan = new JComboBox<String>(banOrder));
        cbo_ChonBan.setFont(new Font("Tohama", Font.BOLD, 15));
        cbo_ChonBan.setPreferredSize(new Dimension(120, 10));

        box_1.add(Box.createRigidArea(new Dimension(10, 10)));
        box_1.add(lbdtl = new JLabel("Điểm tích lũy:"));
        lbdtl.setFont(new Font("Tohama", Font.BOLD, 15));
        box_1.add(Box.createRigidArea(new Dimension(10, 10)));
        box_1.add(text_dtl = new JTextField(10));
        text_dtl.setEditable(false);
        text_dtl.setFont(new Font("Tohama", Font.BOLD, 15));
        box_1.add(Box.createRigidArea(new Dimension(10, 10)));
        box_1.add(btdoiDTL = new JButton("Đổi ĐTL"));
        btdoiDTL.setFont(new Font("Tohama", Font.BOLD, 15));
        box_1.add(Box.createRigidArea(new Dimension(10, 10)));

        Box box_4 = new Box(BoxLayout.X_AXIS);
        box_4.add(Box.createRigidArea(new Dimension(10, 10)));
        box_4.add(lbTongTien = new JLabel("Tổng tiền:"));
        lbTongTien.setFont(new Font("Tohama", Font.BOLD, 15));
        box_4.add(Box.createHorizontalStrut(59));
        box_4.add(text_TongTien = new JTextField(10));
        text_TongTien.setEditable(false);
        text_TongTien.setFont(new Font("Tohama", Font.BOLD, 15));
        box_4.add(Box.createRigidArea(new Dimension(10, 10)));

        Box box_7 = new Box(BoxLayout.X_AXIS);
        box_7.add(Box.createRigidArea(new Dimension(10, 10)));
        box_7.add(lbGiamGia = new JLabel("Giảm giá:"));
        lbGiamGia.setFont(new Font("Tohama", Font.BOLD, 15));
        box_7.add(Box.createHorizontalStrut(65));
        box_7.add(text_GiamGia = new JTextField(10));
        text_GiamGia.setEditable(false);
        text_GiamGia.setFont(new Font("Tohama", Font.BOLD, 15));
        box_7.add(Box.createRigidArea(new Dimension(10, 10)));

        Box box_6 = new Box(BoxLayout.X_AXIS);
        box_6.add(Box.createRigidArea(new Dimension(10, 10)));
        box_6.add(lbTongThanhToan = new JLabel("Tổng thanh toán:"));
        lbTongThanhToan.setFont(new Font("Tohama", Font.BOLD, 15));
        box_6.add(Box.createHorizontalStrut(7));
        box_6.add(text_TongThanhToan = new JTextField(10));
        text_TongThanhToan.setEditable(false);
        text_TongThanhToan.setFont(new Font("Tohama", Font.BOLD, 15));
        box_6.add(Box.createRigidArea(new Dimension(10, 10)));

        box_Center_1.add(Box.createVerticalStrut(10));
        box_Center_1.add(box_8);
        box_Center_1.add(Box.createVerticalStrut(10));
        box_Center_1.add(box_1);
        box_Center_1.add(Box.createVerticalStrut(10));
        box_Center_1.add(box_4);
        box_Center_1.add(Box.createVerticalStrut(10));
        box_Center_1.add(box_7);
        box_Center_1.add(Box.createVerticalStrut(10));
        box_Center_1.add(box_6);
        box_Center_1.add(Box.createVerticalStrut(10));

        Box box_Center_2 = new Box(BoxLayout.Y_AXIS);
        box_Center_2.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        box_Center_2.setPreferredSize(new Dimension(550, 50));

        Box box_2 = new Box(BoxLayout.X_AXIS);

        box_2.add(Box.createRigidArea(new Dimension(10, 10)));
        box_2.add(lbTienKhachDua = new JLabel("Tiền khách đưa:"));
        lbTienKhachDua.setFont(new Font("Tohama", Font.BOLD, 15));
        box_2.add(Box.createHorizontalStrut(13));
        box_2.add(text_TienKhachDua = new JTextField(10));

        text_TienKhachDua.setFont(new Font("Tohama", Font.BOLD, 15));
        box_2.add(Box.createHorizontalStrut(10));
        box_2.add(lbTienTraLai = new JLabel("Tiền trả:"));
        lbTienTraLai.setFont(new Font("Tohama", Font.BOLD, 15));
        box_2.add(Box.createHorizontalStrut(10));
        box_2.add(text_TienThoi = new JTextField(10));
        text_TienThoi.setFont(new Font("Tohama", Font.BOLD, 15));
        text_TienThoi.setEditable(false);
        box_2.add(Box.createRigidArea(new Dimension(10, 10)));

        box_Center_2.add(Box.createVerticalStrut(10));
        box_Center_2.add(box_2);
        box_Center_2.add(Box.createVerticalStrut(10));

        box_Center.add(box_Center_1);
        box_Center.add(Box.createVerticalStrut(10));
        box_Center.add(box_Center_2);

        jPanel_Center.add(box_Center);

        // JPanel button tác vụ
        JPanel jPanel_TacVuOrder = new JPanel();
        jPanel_TacVuOrder.setPreferredSize(new Dimension(500, 50));

        btnThanhToan = new JButton("Thanh Toán");
        btnThanhToan.setPreferredSize(new Dimension(150, 40));
        btnThanhToan.setForeground(new Color(255, 255, 255));
        btnThanhToan.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnThanhToan.setBackground(new Color(255, 153, 0));
        btnThanhToan.setContentAreaFilled(false);
        btnThanhToan.setOpaque(true);
        btnThanhToan.setBorder(null);

        btnHuyOrder = new JButton("Hủy Order");
        btnHuyOrder.setPreferredSize(new Dimension(150, 40));
        btnHuyOrder.setForeground(new Color(255, 255, 255));
        btnHuyOrder.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnHuyOrder.setBackground(new Color(255, 0, 51));
        btnHuyOrder.setContentAreaFilled(false);
        btnHuyOrder.setOpaque(true);

        btnThanhToanKhongIn = new JButton("T.Toán Không In");
        btnThanhToanKhongIn.setPreferredSize(new Dimension(170, 40));
        btnThanhToanKhongIn.setForeground(new Color(255, 255, 255));
        btnThanhToanKhongIn.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnThanhToanKhongIn.setBackground(new Color(0, 102, 0));
        btnThanhToanKhongIn.setContentAreaFilled(false);
        btnThanhToanKhongIn.setOpaque(true);

        jPanel_TacVuOrder.add(btnThanhToan);
        jPanel_TacVuOrder.add(btnHuyOrder);
        jPanel_TacVuOrder.add(btnThanhToanKhongIn);

        jPanel_East.add(boxE, BorderLayout.NORTH);
        jPanel_East.add(jPanel_Center, BorderLayout.CENTER);
        jPanel_East.add(jPanel_TacVuOrder, BorderLayout.SOUTH);

        add(tabbedPane, BorderLayout.CENTER);
        add(jPanel_East, BorderLayout.EAST);

        loadProductsAsync("Tất cả");
        loadProductsAsync("Cà phê");
        loadProductsAsync("Trà sữa");
        loadProductsAsync("Trà trái cây");
        loadProductsAsync("Nước ép");
        loadProductsAsync("Soda");
        loadProductsAsync("Sinh tố");

        // Tạo JPopupMenu
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setFont(new Font("Arial", Font.BOLD, 14)); // Đặt font cho JPopupMenu

        // Tạo JMenuItem "Xóa"
        JMenuItem deleteItem = new JMenuItem("Xóa");
        deleteItem.setFont(new Font("Arial", Font.BOLD, 14)); // Đặt font cho JMenuItem
        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý sự kiện xóa dòng được chọn
                int selectedRow = tableOrder.getSelectedRow();

                if (selectedRow != -1) {
                    modelOrder.removeRow(selectedRow);
                    // Cập nhật số hàng
                    for (int i = 0; i < modelOrder.getRowCount(); i++) {
                        modelOrder.setValueAt(i + 1, i, 0);// Giả sử cột đầu tiên chứa số hàng
                    }
                    updateTotal();
                    updateTextFields();
                }
            }
        });
        popupMenu.add(deleteItem);

        // Tạo JMenuItem "Xóa tất cả"
        JMenuItem deleteAllItem = new JMenuItem("Xóa tất cả");
        deleteAllItem.setFont(new Font("Arial", Font.BOLD, 14)); // Đặt font cho JMenuItem
        deleteAllItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Xử lý sự kiện xóa tất cả dòng
                modelOrder.setRowCount(0);
                updateTotal();
                updateTextFields();
            }
        });
        popupMenu.add(deleteAllItem);

        // Gán JPopupMenu cho bảng tableOrder
        tableOrder.setComponentPopupMenu(popupMenu);

        // Sự kiện
        thanhVien.addActionListener(this);
        btnHuyOrder.addActionListener(this);
        btdoiDTL.addActionListener(this);
        btnThanhToan.addActionListener(this);
        btnThanhToanKhongIn.addActionListener(this);

    }

    // Load sản phẩm từ CSDL bất đồng bộ
    private void loadProductsAsync(final String category) {
    	SwingWorker<List<SanPham>, Void> worker = new SwingWorker<List<SanPham>, Void>() {
            @Override
            protected List<SanPham> doInBackground() {
                if (category.equals("Tất cả")) {
                    // Nếu là tab "Tất cả", tải tất cả sản phẩm từ CSDL
                    return sp_Dao.getSanPhamByStatus();
                } else {
                    // Nếu không, tải sản phẩm theo loại từ CSDL
                    return sp_Dao.getSanPhamByCategory(category);
                }
            }

            @Override
            protected void done() {
                try {
                    List<SanPham> products = get();
                    addProductsToTab(category, products);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    // Lớp để tạo giao diện cho mỗi sản phẩm
    private static class ProductPanel extends JPanel implements ActionListener, ChangeListener {
        private static final long serialVersionUID = 1L;

        JLabel imageLabel, idLabel, nameLabel, priceLabel;
        JSpinner spinnerQuantity;
        JComboBox<String> comboBoxSize;
        JButton addButton;
        SanPham sp;
        private static int orderCount = 0;

        public ProductPanel(final SanPham sp) {
            this.sp = sp;

            setLayout(new GridBagLayout());
            setBorder(new EmptyBorder(10, 10, 10, 10));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(0, 0, 10, 0);

            idLabel = new JLabel("Mã: " + sp.getMaSP());
            idLabel.setFont(new Font("Arial", Font.BOLD, 15));
            add(idLabel, gbc);

            gbc.gridy++;
            nameLabel = new JLabel("Tên: " + truncateProductName(sp.getTenSP(), 20));
            nameLabel.setFont(new Font("Arial", Font.BOLD, 15));
            add(nameLabel, gbc);

            gbc.gridy++;
            // Thay đổi kích thước của hình ảnh
            ImageIcon imageIcon = new ImageIcon(sp.getImage());
            Image image = imageIcon.getImage();
            Image scaledImage = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(scaledImage);
            imageLabel = new JLabel(imageIcon);
            add(imageLabel, gbc);

            gbc.gridy++;
            Locale locale = new Locale("vi", "VN"); // Locale cho tiền tệ Việt Nam

            // Lấy giá của sản phẩm dựa trên mã sản phẩm và size mặc định "S"
            double defaultPrice = getPriceBySize(sp.getMaSP(), "S");

            final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
            // Format giá thành chuỗi với định dạng tiền tệ
            String formattedPrice = currencyFormat.format(defaultPrice);

            // Hiển thị giá lên label
            priceLabel = new JLabel("Giá: " + formattedPrice);
            priceLabel.setFont(new Font("Arial", Font.BOLD, 15));
            add(priceLabel, gbc);

            gbc.gridy++;
            gbc.gridwidth = 1;
            gbc.insets = new Insets(0, 0, 0, 5);
            spinnerQuantity = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
            spinnerQuantity.setPreferredSize(new Dimension(150, 25));
            spinnerQuantity.setFont(new Font("Arial", Font.BOLD, 15));
            add(spinnerQuantity, gbc);

            gbc.gridx++;
            comboBoxSize = new JComboBox<>();
            comboBoxSize.setPreferredSize(new Dimension(50, 25));
            comboBoxSize.setFont(new Font("Arial", Font.BOLD, 15));
            add(comboBoxSize, gbc);

            gbc.gridx = 0;
            gbc.gridy++;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(10, 0, 0, 0);
            addButton = new JButton("Thêm");
            addButton.setIcon(new ImageIcon("images\\plus.png"));
            addButton.setPreferredSize(new Dimension(getWidth(), 30));
            addButton.setFont(new Font("Arial", Font.BOLD, 15));
            add(addButton, gbc);

            // add event
            addButton.addActionListener(this);
            spinnerQuantity.addChangeListener(this);
            comboBoxSize.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    double price = getPriceBySize(sp.getMaSP(), (String) comboBoxSize.getSelectedItem());
                    priceLabel.setText("Giá: " + currencyFormat.format(price));
                    updateTotal();
                    updateTextFields();
                }
            });

            text_TienKhachDua.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    updateReturnMoney();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    updateReturnMoney();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    updateReturnMoney();
                }
            });

        }

        // Thêm một phương thức mới để cập nhật tiền trả lại
        private static void updateReturnMoney() {
            try {
                double tienKhachDua = Double.parseDouble(text_TienKhachDua.getText());
                double tongThanhToan = Double.parseDouble(text_TongThanhToan.getText());
                double tienTraLai = tienKhachDua - tongThanhToan;
                text_TienThoi.setText(String.valueOf(tienTraLai));
            } catch (NumberFormatException e) {
                text_TienThoi.setText("");
            }
        }

        public String truncateProductName(String name, int maxLength) {
            if (name.length() > maxLength) {
                return name.substring(0, maxLength - 3) + "...";
            }
            return name;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Object o = e.getSource();
            if (o.equals(addButton)) {
                int quantity = (int) spinnerQuantity.getValue();
                String size = (String) comboBoxSize.getSelectedItem();
                double price = getPriceBySize(sp.getMaSP(), size);

                int rowCount = modelOrder.getRowCount();
                boolean existed = false;
                int rowToUpdate = -1;

                // Kiểm tra xem sản phẩm đã tồn tại trong bảng đơn hàng chưa
                for (int i = 0; i < rowCount; i++) {
                    String productName = (String) modelOrder.getValueAt(i, 1);
                    String productSize = (String) modelOrder.getValueAt(i, 3);

                    if (productName.equals(sp.getTenSP()) && productSize.equals(size)) {
                        existed = true;
                        rowToUpdate = i;
                        break;
                    }
                }

                ChiTietHoaDon cthd = new ChiTietHoaDon();

                if (existed) {
                    // Nếu sản phẩm đã tồn tại, cập nhật số lượng và thành tiền
                    int oldQuantity = (int) modelOrder.getValueAt(rowToUpdate, 2);
                    double oldTotalPrice = (double) modelOrder.getValueAt(rowToUpdate, 4);

                    int newQuantity = cthd.tinhSoLuong(oldQuantity + quantity);
                    double newTotalPrice = cthd.tinhThanhTien(price);

                    modelOrder.setValueAt(newQuantity, rowToUpdate, 2);
                    modelOrder.setValueAt(newTotalPrice, rowToUpdate, 4);
                } else {
                    // Nếu sản phẩm chưa tồn tại, thêm hàng mới vào bảng đơn hàng
                    double totalPrice = quantity * price;
                    modelOrder.addRow(new Object[] { ++orderCount, sp.getTenSP(), quantity, size, totalPrice });
                }

                int totalQuantity = 0;
                for (int i = 0; i < rowCount; i++) {
                    totalQuantity += (int) modelOrder.getValueAt(i, 2);
                }

                // Cập nhật giá trị của text_dtl với tổng số lượng sản phẩm
                text_dtl.setText(String.valueOf(totalQuantity));

                // Cập nhật tổng tiền và các trường liên quan trên giao diện
                updateTotal();
                updateTextFields();
                spinnerQuantity.setValue(1); // Reset số lượng về 1
            }

        }

        @Override
        public void stateChanged(ChangeEvent e) {
            int quantity = (int) spinnerQuantity.getValue();
            String size = (String) comboBoxSize.getSelectedItem();

            Locale locale = new Locale("vi", "VN"); // Locale cho tiền tệ Việt Nam
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);

            double price = quantity * getPriceBySize(sp.getMaSP(), size);
            priceLabel.setText("Giá: " + currencyFormat.format(price));
        }

    }

    // Phương thức cập nhật tổng tiền
    private static void updateTotal() {
        double total = 0;
        int rowCount = modelOrder.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            total += (double) modelOrder.getValueAt(i, 4);
        }
        text_TongTien.setText(String.valueOf(total));
    }

    // Phương thức cập nhật các trường text_TongTien và text_TongThanhToan
    private static void updateTextFields() {
        double tongTien = Double.parseDouble(text_TongTien.getText());
        // Ở đây giả sử không có giảm giá nên tổng thanh toán bằng tổng tiền
        text_TongThanhToan.setText(String.valueOf(tongTien));
    }

    private void addProductsToTab(String category, List<SanPham> products) {
        JPanel panel = tabPanels.get(category);
        if (panel != null) {
            panel.removeAll();
            for (SanPham sp : products) {
                ProductPanel productPanel = new ProductPanel(sp);
                panel.add(productPanel);
                // Cập nhật ComboBox size và giá của sản phẩm
                updateComboBoxSizeAndPrice(productPanel);
            }
            panel.revalidate();
            panel.repaint();
        } else {
            System.out.println("Tab không tồn tại: " + category);
        }
    }

    private void updateComboBoxSizeAndPrice(final ProductPanel productPanel) {
        Locale locale = new Locale("vi", "VN"); // Locale cho tiền tệ Việt Nam
        final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        final String maSP = productPanel.sp.getMaSP();
        ArrayList<DonGiaSanPham> donGiaList = donGia_DAO.getAllDonGiaSanPham();
        for (DonGiaSanPham donGia : donGiaList) {
            if (donGia.getSanPham().getMaSP().equals(maSP)) {
                String size = donGia.getKichThuoc();
                productPanel.comboBoxSize.addItem(size);
                productPanel.comboBoxSize.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Khi người dùng chọn size mới, cập nhật giá cho sản phẩm
                        String selectedSize = (String) productPanel.comboBoxSize.getSelectedItem();
                        double newPrice = getPriceBySize(maSP, selectedSize);
                        productPanel.priceLabel.setText("Giá: " + currencyFormat.format(newPrice));
                    }
                });
            }
        }
    }

    // Phương thức lấy giá của sản phẩm dựa trên mã sản phẩm và size
    private static double getPriceBySize(String maSP, String size) {
        double price = 0.0;
        try {
            DonGiaSanPham_DAO donGiaDao = new DonGiaSanPham_DAO();
            price = donGiaDao.getPriceBySize(maSP, size);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return price;
    }

    // Class HeaderRenderer để thiết lập font cho tiêu đề cột
    static class HeaderRenderer implements TableCellRenderer {
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
            label.setHorizontalAlignment(JLabel.CENTER);
            return label;
        }
    }

    // Thêm chức năng tìm kiếm cho mỗi tab
    private void addSearchFunctionality(String category) {
        final JPanel panel = tabPanels.get(category);
        final JTextField searchField = ((JTextField) ((JPanel) tabbedPane.getComponentAt(tabbedPane.indexOfTab(category)))
                .getComponent(0));
        if (panel != null && searchField != null) {
            searchField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    search(searchField.getText(), panel);
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    search(searchField.getText(), panel);
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    search(searchField.getText(), panel);
                }
            });
        }
    }

    // Phương thức tìm kiếm
    private void search(String searchText, JPanel panel) {
        Component[] components = panel.getComponents();
        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof ProductPanel) {
                ProductPanel productPanel = (ProductPanel) components[i];
                String productName = productPanel.nameLabel.getText().toLowerCase();
                String productId = productPanel.idLabel.getText().toLowerCase();
                if (productName.contains(searchText.toLowerCase()) || productId.contains(searchText.toLowerCase())) {
                    productPanel.setVisible(true);
                    // Lưu vị trí của sản phẩm được tìm thấy
                    searchResultsPositions.put(panel.getName(), i);
                } else {
                    productPanel.setVisible(false);
                }
            }
        }
        panel.revalidate();
        panel.repaint();
        // Cuộn đến vị trí của sản phẩm được tìm thấy (nếu có)
        scrollToSearchResult(panel);
    }

    // Phương thức để cuộn đến sản phẩm được tìm thấy
    private void scrollToSearchResult(JPanel panel) {
        Integer position = searchResultsPositions.get(panel.getName());
        if (position != null) {
            Rectangle rect = panel.getComponent(position).getBounds();
            panel.scrollRectToVisible(rect);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        KhachHang_DAO kh_DAO = new KhachHang_DAO();
        ArrayList<KhachHang> danhSachKhachHang = kh_DAO.getAllKhachHang();

        if (o.equals(btnHuyOrder)) {
            int confirmed = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn hủy đơn hàng?",
                    "Xác nhận Hủy Order", JOptionPane.YES_NO_OPTION);
            if (confirmed == JOptionPane.YES_OPTION) {
                // Xóa hết dữ liệu trong bảng đơn hàng
                modelOrder.setRowCount(0);
                // Reset lại các trường tổng tiền, tiền trả lại về 0
                text_TongTien.setText("0");
                text_TongThanhToan.setText("0");
                text_TienThoi.setText("0");
                // Reset lại số lượng đơn hàng
                ProductPanel.orderCount = 0;
            }
        } else if (o.equals(thanhVien)) {
            String dt = text_TenKH.getText();

            // Kiểm tra định dạng số điện thoại
            if (!dt.matches("0[1-9]{1}[0-9]{8}")) {
                JOptionPane.showMessageDialog(this, "Số điện thoại bắt đầu bằng 0 và có 10 số!!!");
                text_TenKH.requestFocus();
                return; // Dừng việc xử lý tiếp theo nếu định dạng số điện thoại không hợp lệ
            }

            boolean khachHangTonTai = false;
            KhachHang khachHang = null;

            // Tìm kiếm khách hàng trong danh sách khách hàng
            for (KhachHang kh : danhSachKhachHang) {
                if (kh.getSdt().equals(dt)) {
                    khachHangTonTai = true;
                    khachHang = kh;
                    break; // Thoát khỏi vòng lặp nếu tìm thấy khách hàng
                }
            }

            if (khachHangTonTai) {
                // Hiển thị thông tin của khách hàng thành viên nếu tìm thấy
                text_dtl.setText(Integer.toString(khachHang.getDiemTL()));
            } else {
                // Nếu không tìm thấy khách hàng, sử dụng tổng số lượng sản phẩm làm điểm tích
                // lũy
                int totalQuantity = calculateTotalQuantity();
                text_dtl.setText(String.valueOf(totalQuantity));
            }
        } else if (o.equals(btdoiDTL)) {
            int diemTichLuy = Integer.parseInt(text_dtl.getText());
            SanPham sp = new SanPham();
            ChiTietHoaDon cthd = new ChiTietHoaDon(new HoaDon(), sp);
            double tiengiam = cthd.tinhTienGiam(diemTichLuy);
            text_GiamGia.setText(Double.toString(tiengiam));
            double tongTien = Double.parseDouble(text_TongTien.getText());
            text_TongThanhToan.setText(String.valueOf(tongTien - tiengiam));

            // Cập nhật điểm tích lũy mới cho khách hàng (nếu có)
            String dt = text_TenKH.getText();
            if (!dt.isEmpty()) {
                KhachHang khachHang = kh_DAO.getKhachHangBySDT(dt);
                if (khachHang != null) {
                    int diemTichLuyMoi = calculateTotalQuantity();
                    khachHang.setDiemTL(diemTichLuyMoi);
                    kh_DAO.updateKhachHang(khachHang);
                }
            }
        } else if (o.equals(btnThanhToan)) {
            // Kiểm tra xem đã chọn bàn chưa
            // if (cbo_ChonBan.getSelectedItem() == null) {
            // JOptionPane.showMessageDialog(this, "Vui lòng chọn bàn trước khi thanh
            // toán!!!");
            // return;
            // }
            // Kiểm tra xem đã chọn sản phẩm chưa
            if (modelOrder.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm trước khi thanh toán!!!");
                return;
            }
            // Kiểm tra xem đã nhập tiền khách đưa chưa
            if (text_TienKhachDua.getText().isEmpty()) {
            	
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tiền khách đưa!!!");
                return;
            }
           
            // Kiểm tra xem tiền khách đưa có đúng định dạng số không
            try {
                 tienKhachDua = Double.parseDouble(text_TienKhachDua.getText());
                if (tienKhachDua < 0) {
                    JOptionPane.showMessageDialog(this, "Tiền khách đưa phải lớn hơn hoặc bằng 0!!!");
                    return;
                }
            } catch (NumberFormatException ex) {
            
//                JOptionPane.showMessageDialog(this, "Tiền khách đưa phải là số!!!");
            	ex.printStackTrace();
                return;
            }
           

            // Khởi tạo đối tượng HoaDon
            HoaDon hoaDon = new HoaDon();
            // Khởi tạo đối tượng KhachHang
            KhachHang khachHang = kh_DAO.getKhachHangBySDT(text_TenKH.getText());
            // Khởi tạo đối tượng NhanVien (giả sử có thông tin nhân viên đăng nhập)

            // Gán mã hóa đơn (có thể sinh mã tự động hoặc lấy từ CSDL)
            hoaDon.setMaHoaDon(generateHoaDonID());
            // Gán ngày lập hóa đơn (lấy từ hệ thống)
            java.sql.Date ngayLapHD = new java.sql.Date(new java.util.Date().getTime());
            hoaDon.setNgayLapHD(ngayLapHD);
            // Gán thông tin khách hàng
            hoaDon.setKhachHang(khachHang);
            // Gán thông tin nhân viên

            hoaDon.setNhanVien(loggedInEmployee);

            // Gán giờ vào (lấy từ hệ thống)
            hoaDon.setGioVao(LocalDateTime.now());

            double tongTien = Double.parseDouble(text_TongTien.getText());
            hoaDon.setTongTien(tongTien);

            // Gán tổng tiền thanh toán (lấy từ textfield text_TongThanhToan)
            double tongThanhToan = Double.parseDouble(text_TongThanhToan.getText());
            hoaDon.setTongThanhToan(tongThanhToan);

            // Khởi tạo đối tượng HoaDon_DAO
            HoaDon_DAO hoaDonDAO = new HoaDon_DAO();
            // Gọi phương thức create để lưu HoaDon vào CSDL
            boolean result = hoaDonDAO.create(hoaDon);

            // Bàn - Bàn có thể không chọn
            String tenBan = (String) cbo_ChonBan.getSelectedItem();
            if (tenBan != null) {
                Ban ban = new Ban();
                ban.setTenBan(tenBan);
                ban.setHoaDon(hoaDon);
                banDao.create(ban);
            }

            if (result) {
                // Lưu hóa đơn thành công
                System.out.println("Lưu hóa đơn thành công!");
                // Duyệt qua các dòng trong bảng đơn hàng để lưu thông tin chi tiết hóa đơn
                for (int i = 0; i < modelOrder.getRowCount(); i++) {
                    // Lấy thông tin sản phẩm từ dòng hiện tại
                    String tenSanPham = (String) modelOrder.getValueAt(i, 1);
                    int soLuong = (int) modelOrder.getValueAt(i, 2);
                    String size = (String) modelOrder.getValueAt(i, 3);
                    double donGia = (double) modelOrder.getValueAt(i, 4) / soLuong; // Tính đơn giá

                    // Khởi tạo đối tượng SanPham (giả sử có thông tin sản phẩm trong CSDL)
                    SanPham sanPham = new SanPham();
                    sanPham.setTenSP(tenSanPham);
                    // Lấy mã sản phẩm từ CSDL dựa trên tên sản phẩm và size
                    String maSanPham = sp_Dao.getSanPhamByTenAndSize(tenSanPham, size);
                    sanPham.setMaSP(maSanPham);

                    // Khởi tạo đối tượng ChiTietHoaDon
                    ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(hoaDon, sanPham);
                    chiTietHoaDon.setSoLuong(soLuong);
                    // Tính tổng tiền cho chi tiết hóa đơn
                    double thanhTien = soLuong * donGia;
                    chiTietHoaDon.setThanhTien(thanhTien);

                    // Lấy tiền giảm từ text_GiamGia
                    
                   text_GiamGia.setText("0");
                    double tienGiam = Double.parseDouble(text_GiamGia.getText());

                    chiTietHoaDon.setTienGiam(tienGiam);

                    // Khởi tạo đối tượng ChiTietHoaDon_DAO
                    ChiTietHoaDon_DAO chiTietHoaDonDAO = new ChiTietHoaDon_DAO();
                    // Gọi phương thức create để lưu chi tiết hóa đơn vào CSDL
                    boolean resultCTHD = chiTietHoaDonDAO.create(chiTietHoaDon);
                    if (!resultCTHD) {
                        JOptionPane.showMessageDialog(this, "Lưu chi tiết hóa đơn không thành công!");
                    }
                }

                // Cập nhật điểm tích lũy mới cho khách hàng (nếu có)
                String dt = text_TenKH.getText();
                if (!dt.isEmpty()) {
                    KhachHang kh = kh_DAO.getKhachHangBySDT(dt);
                    if (khachHang != null) {
                        khachHang.setDiemTL(0); // Đặt điểm tích lũy về 0 sau khi thanh toán
                        kh_DAO.updateKhachHang(kh);
                    }
                }

                // Hiển thị thông báo thanh toán thành công
                JOptionPane.showMessageDialog(this, "Thanh toán thành công!");
                hdbhDao = new HoaDonBanHang_DAO();
                
                Ban_DAO bandao = new Ban_DAO();
                ArrayList<HoaDon> listHD = hdbhDao.getAllHoaDon();
                
                HoaDon hdfirst = listHD.get(0);
                String mahoadon = hdfirst.getMaHoaDon();
                LocalDateTime giovao = hdfirst.getGioVao();
                
                Ban ban = bandao.searchBanTheomaHD(mahoadon);
                String tenban = ban.getTenBan();
                 System.out.println("ten ban : "+tenban);
                
                String tenKH = kh_DAO.getTenTheoMaHD(mahoadon);
                nvdao = new NhanVien_DAO();
                String tenNV = nvdao.getTenNVTheoMaHD(mahoadon);
                
                
                
                
                PDDocument  document = new  PDDocument();
        		PDPage firstPage = new PDPage(PDRectangle.A5);
        		document.addPage(firstPage);
        		
        		
        	//	Format d_format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        		
        		int pageWidth = (int) firstPage.getTrimBox().getWidth();
        		int pageHeight = (int) firstPage.getTrimBox().getHeight();
        		
        		
				try {
					contentStream = new PDPageContentStream(document, firstPage);
					 myTextClass = new MyTextClass(document, contentStream);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		
        		
        	//	PDFont font = PDType0Font.load(document,Test_PDF.class.getResourceAsStream("/ArialUnicodeMS.ttf"));
        		
        		PDFont font = PDType1Font.HELVETICA;
        		PDFont italicFont = PDType1Font.HELVETICA_OBLIQUE;
        		
        		
        		// nội dung file pdf
        		
        		
        		
        		try {
        			myTextClass.addSingleLineText("SDT: 0399754203",170,pageHeight- 80,font, 10, Color.black);
            		myTextClass.addSingleLineText("------------------------------",160,pageHeight- 90,font, 10, Color.black);
            		
            		
         	// 	float dateTextWidth = myTextClass.getTextWidth(d_format.format(new java.util.Date()), font,16);
            	  myTextClass.addSingleLineText("Ma HD             : "+mahoadon   ,30, pageHeight- 170, font, 10,Color.black);
            		myTextClass.addSingleLineText("DateTime :    " + giovao,217, pageHeight- 170, font, 10,Color.black);
            		myTextClass.addSingleLineText("Ban                  : "+tenban,30, pageHeight- 185, font, 10,Color.black);
            		myTextClass.addSingleLineText("Khach hang     : "+removeVietnameseAccents(tenKH)    ,30, pageHeight- 200, font, 10,Color.black);
            		myTextClass.addSingleLineText("Thu ngan         : "+removeVietnameseAccents(tenNV),30, pageHeight- 215, font, 10,Color.black);
            		
            		
				} catch (Exception e2) {
					// TODO: handle exception
				}
        		
        		//table
        		cthd_dao = new ChiTietHoaDon_DAO();
        		
        		
        		MyTableClass myTable = new MyTableClass(document, contentStream);
        		
        		int[] cellWidth = {30,180,30,20,50,70};	
        		myTable.setTable(cellWidth, 30, pageHeight-270,25);
        		
        		myTable.setTableFont(font, 13, Color.BLACK);
        		
        		Color tableBodyColor = new Color(0,0,0);
        		

        		ArrayList<String> chitiet = cthd_dao.getAll_CTHDAtmaHD(mahoadon);
        		for (int j = 0; j<chitiet.size();j++) {
        			int n = j+1;
        			try {
						myTable.addCell(Integer.toString(n), tableBodyColor);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
        			String invoiceDetail = chitiet.get(j);
                    String[] attributes = invoiceDetail.split(",");
                    for (int i = 0; i< attributes.length;i++) {
                    	try {
							myTable.addCell(removeVietnameseAccents(attributes[i]), tableBodyColor);
	                		
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                		
                    }
                }
        		

        	try {        	
        		//Tiêu đề table
        		PDFont boldF = PDType1Font.HELVETICA_BOLD;
        		myTextClass.addSingleLineText("STT",30, pageHeight- 235, boldF, 10,Color.black);
        		myTextClass.addSingleLineText("Ten san pham",60, pageHeight- 235, boldF, 10,Color.black);
        		myTextClass.addSingleLineText("Size",234, pageHeight- 235, boldF, 10,Color.black);
        		myTextClass.addSingleLineText("SL",267, pageHeight- 235, boldF, 10,Color.black);
        		myTextClass.addSingleLineText("Don gia",290, pageHeight- 235, boldF, 10,Color.black);
        		myTextClass.addSingleLineText("Thanh tien",345, pageHeight- 235, boldF, 10,Color.black);
        		myTextClass.addSingleLineText("__________________________________________________________________"   ,30, pageHeight- 240, font, 10,Color.black);
        		
        		//hóa don ban hang
        		myTextClass.addSingleLineText("VNV Coffee",140,pageHeight- 60,boldF, 25, Color.black);
        		myTextClass.addSingleLineText("HOA DON BAN HANG",120,pageHeight- 140,boldF, 18, Color.black);
        		//dưới table
        		
        		myTextClass.addSingleLineText("__________________________________________________________________"   ,30, pageHeight- 470, font, 10,Color.black);
        		myTextClass.addSingleLineText("Tong: "   ,30, pageHeight- 490, font, 10,Color.black);
        		myTextClass.addSingleLineText( Integer.toString(cthd_dao.getSoLuongTheoMaHD(mahoadon)) ,270, pageHeight- 490, font, 10,Color.black);
        		myTextClass.addSingleLineText( Double.toString(cthd_dao.gettongtienTheoMaHD(mahoadon)),365, pageHeight- 490, font, 10,Color.black);
        		myTextClass.addSingleLineText("Tong tien(gom VAT): "   ,30, pageHeight- 505, font, 10,Color.black);
        		myTextClass.addSingleLineText(Double.toString(cthd_dao.gettongtienTheoMaHD(mahoadon)),365, pageHeight- 505, font, 10,Color.black);
        		
        		myTextClass.addSingleLineText("Giam gia: "   ,30, pageHeight- 520, font, 10,Color.black);
        		myTextClass.addSingleLineText(Double.toString(cthd_dao.getGiaGiamTheoMaHD(mahoadon)),365, pageHeight- 520, font, 10,Color.black);
        		
        		myTextClass.addSingleLineText("__________________________________________________________________"   ,30, pageHeight- 530, font, 10,Color.black);
        		
        		
        		myTextClass.addSingleLineText("Tong thanh toan: "   ,30, pageHeight- 545, boldF, 10,Color.black);
        		myTextClass.addSingleLineText(Double.toString(hdfirst.getTongThanhToan()),365, pageHeight- 545, font, 10,Color.black);
        		
        		myTextClass.addSingleLineText("VNV Coffee cam on - Hen gap lai Quy Khach"   ,120, pageHeight- 570, boldF, 9,Color.black);
			} catch (Exception e2) {
				// TODO: handle exception
			}
        		
        		
        		//
        	try {
        		contentStream.close();
        		// Save the document
                String filePath = "N:\\HoaDon.pdf";
                document.save(filePath);
                document.close();
                System.out.println("PDF created successfully");

                // Open the PDF file using the default PDF viewer
                Desktop desktop = Desktop.getDesktop();
                File file = new File(filePath);
                desktop.open(file);
			} catch (Exception e2) {
				// TODO: handle exception
			}
                resetGiaoDien();
            } else {
                // Hiển thị thông báo lỗi khi lưu hóa đơn không thành công
                JOptionPane.showMessageDialog(this, "Lưu hóa đơn thất bại!");
            }

        }else if(o.equals(btnThanhToanKhongIn)) {
        	// Kiểm tra xem đã chọn sản phẩm chưa
            if (modelOrder.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm trước khi thanh toán!!!");
                return;
            }
            // Kiểm tra xem đã nhập tiền khách đưa chưa
            if (text_TienKhachDua.getText().isEmpty()) {
            	
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tiền khách đưa!!!");
                return;
            }
           
            // Kiểm tra xem tiền khách đưa có đúng định dạng số không
            try {
                 tienKhachDua = Double.parseDouble(text_TienKhachDua.getText());
                if (tienKhachDua < 0) {
                    JOptionPane.showMessageDialog(this, "Tiền khách đưa phải lớn hơn hoặc bằng 0!!!");
                    return;
                }
            } catch (NumberFormatException ex) {
            
//                JOptionPane.showMessageDialog(this, "Tiền khách đưa phải là số!!!");
            	ex.printStackTrace();
                return;
            }
           

            // Khởi tạo đối tượng HoaDon
            HoaDon hoaDon = new HoaDon();
            // Khởi tạo đối tượng KhachHang
            KhachHang khachHang = kh_DAO.getKhachHangBySDT(text_TenKH.getText());
            // Khởi tạo đối tượng NhanVien (giả sử có thông tin nhân viên đăng nhập)

            // Gán mã hóa đơn (có thể sinh mã tự động hoặc lấy từ CSDL)
            hoaDon.setMaHoaDon(generateHoaDonID());
            // Gán ngày lập hóa đơn (lấy từ hệ thống)
            java.sql.Date ngayLapHD = new java.sql.Date(new java.util.Date().getTime());
            hoaDon.setNgayLapHD(ngayLapHD);
            // Gán thông tin khách hàng
            hoaDon.setKhachHang(khachHang);
            // Gán thông tin nhân viên

            hoaDon.setNhanVien(loggedInEmployee);

            // Gán giờ vào (lấy từ hệ thống)
            hoaDon.setGioVao(LocalDateTime.now());

            double tongTien = Double.parseDouble(text_TongTien.getText());
            hoaDon.setTongTien(tongTien);

            // Gán tổng tiền thanh toán (lấy từ textfield text_TongThanhToan)
            double tongThanhToan = Double.parseDouble(text_TongThanhToan.getText());
            hoaDon.setTongThanhToan(tongThanhToan);

            // Khởi tạo đối tượng HoaDon_DAO
            HoaDon_DAO hoaDonDAO = new HoaDon_DAO();
            // Gọi phương thức create để lưu HoaDon vào CSDL
            boolean result = hoaDonDAO.create(hoaDon);

            // Bàn - Bàn có thể không chọn
            String tenBan = (String) cbo_ChonBan.getSelectedItem();
            if (tenBan != null) {
                Ban ban = new Ban();
                ban.setTenBan(tenBan);
                ban.setHoaDon(hoaDon);
                banDao.create(ban);
            }

            if (result) {
                // Lưu hóa đơn thành công
                System.out.println("Lưu hóa đơn thành công!");
                // Duyệt qua các dòng trong bảng đơn hàng để lưu thông tin chi tiết hóa đơn
                for (int i = 0; i < modelOrder.getRowCount(); i++) {
                    // Lấy thông tin sản phẩm từ dòng hiện tại
                    String tenSanPham = (String) modelOrder.getValueAt(i, 1);
                    int soLuong = (int) modelOrder.getValueAt(i, 2);
                    String size = (String) modelOrder.getValueAt(i, 3);
                    double donGia = (double) modelOrder.getValueAt(i, 4) / soLuong; // Tính đơn giá

                    // Khởi tạo đối tượng SanPham (giả sử có thông tin sản phẩm trong CSDL)
                    SanPham sanPham = new SanPham();
                    sanPham.setTenSP(tenSanPham);
                    // Lấy mã sản phẩm từ CSDL dựa trên tên sản phẩm và size
                    String maSanPham = sp_Dao.getSanPhamByTenAndSize(tenSanPham, size);
                    sanPham.setMaSP(maSanPham);

                    // Khởi tạo đối tượng ChiTietHoaDon
                    ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon(hoaDon, sanPham);
                    chiTietHoaDon.setSoLuong(soLuong);
                    // Tính tổng tiền cho chi tiết hóa đơn
                    double thanhTien = soLuong * donGia;
                    chiTietHoaDon.setThanhTien(thanhTien);

                    // Lấy tiền giảm từ text_GiamGia
                    
                   text_GiamGia.setText("0");
                    double tienGiam = Double.parseDouble(text_GiamGia.getText());

                    chiTietHoaDon.setTienGiam(tienGiam);

                    // Khởi tạo đối tượng ChiTietHoaDon_DAO
                    ChiTietHoaDon_DAO chiTietHoaDonDAO = new ChiTietHoaDon_DAO();
                    // Gọi phương thức create để lưu chi tiết hóa đơn vào CSDL
                    boolean resultCTHD = chiTietHoaDonDAO.create(chiTietHoaDon);
                    if (!resultCTHD) {
                        JOptionPane.showMessageDialog(this, "Lưu chi tiết hóa đơn không thành công!");
                    }
                }

                // Cập nhật điểm tích lũy mới cho khách hàng (nếu có)
                String dt = text_TenKH.getText();
                if (!dt.isEmpty()) {
                    KhachHang kh = kh_DAO.getKhachHangBySDT(dt);
                    if (khachHang != null) {
                        khachHang.setDiemTL(0); // Đặt điểm tích lũy về 0 sau khi thanh toán
                        kh_DAO.updateKhachHang(kh);
                    }
                }

                // Hiển thị thông báo thanh toán thành công
                JOptionPane.showMessageDialog(this, "Thanh toán thành công!");
            }
        }
    }

    private void resetGiaoDien() {
        // Xóa dữ liệu trong bảng đơn hàng
        modelOrder.setRowCount(0);
        // Reset các trường dữ liệu
        text_TenKH.setText("");
        text_dtl.setText("");
        text_TongTien.setText("0");
        text_TongThanhToan.setText("0");
        text_TienKhachDua.setText("");
        text_TienThoi.setText("");
        text_GiamGia.setText("0");
        // Reset lại số lượng đơn hàng
        ProductPanel.orderCount = 0;
    }

    private String generateHoaDonID() {
        // Lấy thời gian hiện tại
        LocalDateTime now = LocalDateTime.now();

        // Tạo mã hóa đơn dựa trên thời gian và một số ngẫu nhiên
        String hoaDonID = "HD" + now.format(DateTimeFormatter.ofPattern("HHmm"));

        return hoaDonID;
    }

    public void setLoggedInEmployee(NhanVien employee) {
        this.loggedInEmployee = employee;
    }

    public NhanVien getLoggedInEmployee() {
        return loggedInEmployee;
    }

    private int calculateTotalQuantity() {
        int totalQuantity = 0;
        int rowCount = modelOrder.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            totalQuantity += (int) modelOrder.getValueAt(i, 2);
        }
        return totalQuantity;
    }
    //
    //
    //chuyển chuỗi có dấu thành chuỗi không dấu
    public static String removeVietnameseAccents(String str) {
        // Loại bỏ các dấu thanh từ chuỗi
        String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
        String result = temp.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        // Thay thế 'đ' thành 'd'
        result = result.replaceAll("đ", "d");
        
        return result;
    }
}
