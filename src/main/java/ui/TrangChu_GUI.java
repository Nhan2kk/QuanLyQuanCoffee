package ui;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import dao.DangNhap_DAO;
import entity.NhanVien;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TrangChu_GUI extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    JMenuItem menuItem, menuItem_1, menuItem_2, menuItem_4, menuItem_5, menuItem_6, menuItem_7, menuItem_8, menuItem_9,
            menuItem_10, menuItem_11;
    JButton btnLogout, btnExit, btnBackTT;
    JPanel bottomPanel, centerPanel;
    JLabel lbUsername, lbUsernameVal;
    boolean isHomePage = true;
    private boolean isAdmin = false;

    private NhanVien loggedInEmployee;
    DangNhap_DAO dn_Dao;

    public TrangChu_GUI() {
        setTitle("VNV Coffee");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        dn_Dao = new DangNhap_DAO();

        // Set logo
        ImageIcon icon = new ImageIcon("images\\logo2.jpg");
        this.setIconImage(icon.getImage());

        UIManager.put("MenuBar.background", Color.orange);
        // Tạo thanh menu
        JMenuBar menuBar = new JMenuBar();
        menuBar.setFont(new Font("Segoe UI", Font.BOLD, 15));

        // Menu Nhân viên
        JMenu menuNhanVien = new JMenu("Nhân viên");
        menuNhanVien.setPreferredSize(new Dimension(130, 50));
        menuNhanVien.setIcon(new ImageIcon("images\\Users-Administrator-icon.png"));
        menuNhanVien.setFont(new Font("Segoe UI", Font.BOLD, 17));
        menuItem = new JMenuItem("Order");
        menuItem.setIcon(new ImageIcon("images\\clipboard.png"));
        menuItem.setFont(new Font("Segoe UI", Font.BOLD, 14));
        menuNhanVien.add(menuItem);
        menuItem_1 = new JMenuItem("Cập nhật và tìm kiếm");
        menuItem_1.setIcon(new ImageIcon("images\\changes.png"));
        menuItem_1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        menuNhanVien.add(menuItem_1);
        menuBar.add(menuNhanVien);

        menuNhanVien.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                menuNhanVien.setForeground(Color.WHITE);
                menuNhanVien.setBackground(new Color(51, 153, 255));
                menuNhanVien.setOpaque(true);
                menuNhanVien.setContentAreaFilled(false);
            }

            public void mouseExited(MouseEvent e) {
                menuNhanVien.setForeground(UIManager.getColor("Menu.foreground"));
                menuNhanVien.setBackground(UIManager.getColor("Menu.background"));
            }
        });

        // Menu Khách hàng
        JMenu menuKhachHang = new JMenu("Khách hàng");
        menuKhachHang.setPreferredSize(new Dimension(140, 50));
        menuKhachHang.setIcon(new ImageIcon("images\\users-icon.png"));
        menuKhachHang.setFont(new Font("Segoe UI", Font.BOLD, 17));
        menuItem_4 = new JMenuItem("Khách hàng thành viên");
        menuItem_4.setIcon(new ImageIcon("images\\customer.png"));
        menuItem_4.setFont(new Font("Segoe UI", Font.BOLD, 15));
        menuKhachHang.add(menuItem_4);
        menuBar.add(menuKhachHang);

        menuKhachHang.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                menuKhachHang.setForeground(Color.WHITE);
                menuKhachHang.setBackground(new Color(51, 153, 255));
                menuKhachHang.setOpaque(true);
                menuKhachHang.setContentAreaFilled(false);
            }

            public void mouseExited(MouseEvent e) {
                menuKhachHang.setForeground(UIManager.getColor("Menu.foreground"));
                menuKhachHang.setBackground(UIManager.getColor("Menu.background"));
            }
        });

        // Menu Sản phẩm
        JMenu menuSanPham = new JMenu("Sản phẩm");
        menuSanPham.setPreferredSize(new Dimension(130, 50));
        menuSanPham.setIcon(new ImageIcon("images\\Tropical-Drink-icon.png"));
        menuSanPham.setFont(new Font("Segoe UI", Font.BOLD, 17));
        menuItem_5 = new JMenuItem("Cập nhật và tìm kiếm");
        menuItem_5.setIcon(new ImageIcon("images\\changes (1).png"));
        menuItem_5.setFont(new Font("Segoe UI", Font.BOLD, 15));
        menuSanPham.add(menuItem_5);
        menuBar.add(menuSanPham);

        menuSanPham.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                menuSanPham.setForeground(Color.WHITE);
                menuSanPham.setBackground(new Color(51, 153, 255));
                menuSanPham.setOpaque(true);
                menuSanPham.setContentAreaFilled(false);
            }

            public void mouseExited(MouseEvent e) {
                menuSanPham.setForeground(UIManager.getColor("Menu.foreground"));
                menuSanPham.setBackground(UIManager.getColor("Menu.background"));
            }
        });

        // Menu Hóa đơn
        JMenu menuHoaDon = new JMenu("Hóa đơn");
        menuHoaDon.setPreferredSize(new Dimension(130, 50));
        menuHoaDon.setIcon(new ImageIcon("images\\Finance-Purchase-Order-icon.png"));
        menuHoaDon.setFont(new Font("Segoe UI", Font.BOLD, 17));
        menuItem_7 = new JMenuItem("Bán hàng");
        menuItem_7.setIcon(new ImageIcon("images\\bill.png"));
        menuItem_7.setFont(new Font("Segoe UI", Font.BOLD, 15));
        menuHoaDon.add(menuItem_7);
        menuBar.add(menuHoaDon);

        menuHoaDon.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                menuHoaDon.setForeground(Color.WHITE);
                menuHoaDon.setBackground(new Color(51, 153, 255));
                menuHoaDon.setOpaque(true);
                menuHoaDon.setContentAreaFilled(false);
            }

            public void mouseExited(MouseEvent e) {
                menuHoaDon.setForeground(UIManager.getColor("Menu.foreground"));
                menuHoaDon.setBackground(UIManager.getColor("Menu.background"));
            }
        });

        // Menu Thống kê
        JMenu menuThongKe = new JMenu("Thống kê");
        menuThongKe.setPreferredSize(new Dimension(130, 50));
        menuThongKe.setIcon(new ImageIcon("images\\Food-List-Ingredients-icon.png"));
        menuThongKe.setFont(new Font("Segoe UI", Font.BOLD, 17));
        menuItem_8 = new JMenuItem("Doanh thu");
        menuItem_8.setIcon(new ImageIcon("images\\increase.png"));
        menuItem_8.setFont(new Font("Segoe UI", Font.BOLD, 15));

        menuItem_9 = new JMenuItem("Khách hàng");
        menuItem_9.setIcon(new ImageIcon("images\\statistics.png"));
        menuItem_9.setFont(new Font("Segoe UI", Font.BOLD, 15));

        menuItem_10 = new JMenuItem("Sản phẩm");
        menuItem_10.setIcon(new ImageIcon("images\\product-selling.png"));
        menuItem_10.setFont(new Font("Segoe UI", Font.BOLD, 15));

        menuThongKe.add(menuItem_8);
        menuThongKe.add(menuItem_9);
        menuThongKe.add(menuItem_10);
        menuBar.add(menuThongKe);

        menuThongKe.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                menuThongKe.setForeground(Color.WHITE);
                menuThongKe.setBackground(new Color(51, 153, 255));
                menuThongKe.setOpaque(true);
                menuThongKe.setContentAreaFilled(false);
            }

            public void mouseExited(MouseEvent e) {
                menuThongKe.setForeground(UIManager.getColor("Menu.foreground"));
                menuThongKe.setBackground(UIManager.getColor("Menu.background"));
            }
        });

        // Menu trợ giúp
        JMenu menuTroGiup = new JMenu("Trợ giúp");
        menuTroGiup.setIcon(new ImageIcon("images\\help-circle-icon.png"));
        menuTroGiup.setFont(new Font("Segoe UI", Font.BOLD, 17));

        menuItem_11 = new JMenuItem("Hướng dẫn sử dụng");
        menuItem_11.setIcon(new ImageIcon("images\\instructions.png"));
        menuItem_11.setFont(new Font("Segoe UI", Font.BOLD, 15));
        menuTroGiup.add(menuItem_11);

        menuBar.add(menuTroGiup);
        menuBar.setPreferredSize(new Dimension(10, 60));

        menuTroGiup.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                menuTroGiup.setForeground(Color.WHITE);
                menuTroGiup.setBackground(new Color(51, 153, 255));
                menuTroGiup.setOpaque(true);
                menuTroGiup.setContentAreaFilled(false);
            }

            public void mouseExited(MouseEvent e) {
                menuTroGiup.setForeground(UIManager.getColor("Menu.foreground"));
                menuTroGiup.setBackground(UIManager.getColor("Menu.background"));
            }
        });

        // Thêm thanh menu vào frame
        setJMenuBar(menuBar);

        getContentPane().add(new Form_TrangChu(), BorderLayout.CENTER);

        // Center Panel
        initCenterPanel();

        // Bottom Panel
        initBottomPanel();

        // Add event
        menuItem.addActionListener(this);
        btnExit.addActionListener(this);
        btnLogout.addActionListener(this);
        btnBackTT.addActionListener(this);
        menuItem_1.addActionListener(this);
        menuItem_4.addActionListener(this);
        menuItem_5.addActionListener(this);
        menuItem_7.addActionListener(this);
        menuItem_4.addActionListener(this);
        menuItem_8.addActionListener(this);
        menuItem_9.addActionListener(this);
        menuItem_10.addActionListener(this);
        menuItem_11.addActionListener(this);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    TrangChu_GUI frame = new TrangChu_GUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Phương thức khởi tạo CenterPanel
    private void initCenterPanel() {
        centerPanel = new JPanel();

    }

    // Phương thức khởi tạo bottomPanel
    private void initBottomPanel() {
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));

        bottomPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        bottomPanel.add(lbUsername = new JLabel("Nhân viên trực: "));
        lbUsername.setForeground(Color.WHITE);
        lbUsername.setFont(new Font("Segoe UI", Font.BOLD, 15));
        bottomPanel.add(lbUsernameVal = new JLabel(""));
        lbUsernameVal.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lbUsernameVal.setForeground(Color.WHITE);

        btnLogout = new JButton("Đăng xuất");
        btnLogout.setOpaque(false);
        btnLogout.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnLogout.setIcon(new ImageIcon("images\\switch.png"));
        btnExit = new JButton("Thoát");
        btnExit.setOpaque(false);
        btnExit.setIcon(new ImageIcon("images\\logout2.png"));
        btnExit.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnBackTT = new JButton("Trang chủ");
        btnBackTT.setOpaque(false);
        btnBackTT.setIcon(new ImageIcon("images\\previous.png"));
        btnBackTT.setFont(new Font("Tahoma", Font.BOLD, 13));
        bottomPanel.add(Box.createHorizontalGlue());

        bottomPanel.add(btnBackTT);
        bottomPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        bottomPanel.add(btnLogout);
        bottomPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        bottomPanel.add(btnExit);

        bottomPanel.setBackground(new Color(51, 153, 255));
        bottomPanel.setOpaque(true);

        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
    }

    // Hiển thị bottomPanel
    private void showBottomPanel() {
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        revalidate();
        repaint();
    }

    // Update username
    public void updateUsername(String username) {
        lbUsernameVal.setText(username);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object o = e.getSource();
        if (o.equals(menuItem)) {
            getContentPane().removeAll();
            Form_Order formOrder = new Form_Order();
            getContentPane().add(formOrder);
            revalidate();
            repaint();
            showBottomPanel();
            formOrder.setLoggedInEmployee(loggedInEmployee);
            isHomePage = false;
        } else if (o.equals(menuItem_5)) {
            getContentPane().removeAll();
            Form_CapNhatSanPham form_sp = new Form_CapNhatSanPham();
            getContentPane().add(form_sp);
            revalidate();
            repaint();
            showBottomPanel();
            form_sp.checkAdmin(isAdmin);
            isHomePage = false;
        } else if (o.equals(menuItem_1)) {
            getContentPane().removeAll();
            Form_CapNhatNhanVien form_NV = new Form_CapNhatNhanVien();
            getContentPane().add(form_NV);
            revalidate();
            repaint();
            showBottomPanel();
            form_NV.checkAdmin(isAdmin);
            isHomePage = false;
        } else if (o.equals(menuItem_4)) {
            getContentPane().removeAll();
            getContentPane().add(new Form_KhachHangThanhVien());
            revalidate();
            repaint();
            showBottomPanel();
            isHomePage = false;
        } else if (o.equals(menuItem_7)) {
            getContentPane().removeAll();
            getContentPane().add(new Form_HoaDonBanHang());
            revalidate();
            repaint();
            showBottomPanel();
            isHomePage = false;
        } else if (o.equals(menuItem_8)) {
            getContentPane().removeAll();
            getContentPane().add(new Form_ThongKeDoanhThu());
            revalidate();
            repaint();
            showBottomPanel();
            isHomePage = false;
        } else if (o.equals(menuItem_9)) {
            getContentPane().removeAll();
            getContentPane().add(new Form_ThongKeKhachHang());
            revalidate();
            repaint();
            showBottomPanel();
            isHomePage = false;
        } else if (o.equals(menuItem_10)) {
            getContentPane().removeAll();
            getContentPane().add(new Form_ThongKeSanPham());
            revalidate();
            repaint();
            showBottomPanel();
            isHomePage = false;
        } else if (o.equals(menuItem_11)) {
            getContentPane().removeAll();
            getContentPane().add(new Form_TroGiup());
            revalidate();
            repaint();
            showBottomPanel();
            isHomePage = false;
        } else if (o.equals(btnLogout)) {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn đăng xuất?", "Xác nhận đăng xuất",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                DangNhap_GUI loginFrame = new DangNhap_GUI();
                loginFrame.setVisible(true);
                this.dispose();
            }
        } else if (o.equals(btnExit)) {
            // Xử lý sự kiện thoát chương trình
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn thoát chương trình?", "Xác nhận thoát",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        } else if (o.equals(btnBackTT)) {
            if (!isHomePage) {
                getContentPane().removeAll();
                getContentPane().add(new Form_TrangChu());
                revalidate();
                repaint();
                showBottomPanel();
            }
        }

    }

    public void setLoggedInEmployee(NhanVien employee) {
        this.loggedInEmployee = employee;
    }

    public NhanVien getLoggedInEmployee() {
        return loggedInEmployee;
    }

    public void checkAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
