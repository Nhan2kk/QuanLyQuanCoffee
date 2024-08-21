package ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JTextField;

import connectDB.ConnectDB;
import dao.DangNhap_DAO;
import entity.NhanVien;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.UIManager;

import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import java.awt.SystemColor;

public class DangNhap_GUI extends JFrame implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private JPanel jPanel_All;
	private JButton jButton_Login, jButton_clear;
	private PlaceholderTextField text_User;
	private PlaceholderPasswordField text_Password;
	private DangNhap_DAO dn_Dao;

	private boolean isAdmin = false;

	public DangNhap_GUI() {
		super("VNV Coffee Shop - Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1920, 1080);
		setLocationRelativeTo(null);
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		// Set logo
		ImageIcon icon = new ImageIcon("images\\logo2.jpg");
		this.setIconImage(icon.getImage());

		jPanel_All = new JPanel();
		jPanel_All.setForeground(new Color(255, 255, 255));
		jPanel_All.setBackground(new Color(255, 255, 255));
		jPanel_All.setBorder(null);

		getContentPane().add(jPanel_All);
		jPanel_All.setLayout(new BorderLayout(0, 0));

		JPanel jPanel_Login = new JPanel();
		jPanel_Login.setBackground(new Color(255, 255, 255));
		jPanel_All.add(jPanel_Login);
		jPanel_Login.setLayout(null);

		text_User = new PlaceholderTextField("Username");
		text_User.setBackground(new Color(192, 192, 192));
		text_User.setFont(new Font("Tahoma", Font.PLAIN, 15));
		text_User.setBounds(350, 232, 400, 36);
		jPanel_Login.add(text_User);
		text_User.setColumns(20);

		JCheckBox cbHidePassword = new JCheckBox("Hide Password !");
		cbHidePassword.setForeground(new Color(255, 255, 255));
		cbHidePassword.setBackground(new Color(255, 255, 255));
		cbHidePassword.setBounds(349, 352, 120, 34);
		cbHidePassword.setOpaque(false);
		jPanel_Login.add(cbHidePassword);

		// Trong constructor của lớp DangNhap_GUI
		cbHidePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Kiểm tra trạng thái của checkbox
				if (cbHidePassword.isSelected()) {
					// Nếu được chọn, ẩn mật khẩu
					text_Password.setEchoChar((char) 0);
				} else {
					// Nếu không được chọn, hiển thị mật khẩu
					text_Password.setEchoChar('\u25cf'); // Kí tự mặc định cho mật khẩu (hình tròn đen)
				}
			}
		});

		jButton_Login = new JButton("Login");
		jButton_Login.setForeground(new Color(255, 255, 255));
		jButton_Login.setContentAreaFilled(false);
		jButton_Login.setOpaque(true);

		jButton_Login.setBackground(new Color(255, 0, 51));
		jButton_Login.setFont(new Font("Tahoma", Font.BOLD, 18));
		jButton_Login.setBounds(396, 430, 120, 45);
		jPanel_Login.add(jButton_Login);

		text_Password = new PlaceholderPasswordField("Password");
		text_Password.setBounds(350, 300, 400, 36);
		jPanel_Login.add(text_Password);

		JSeparator separator = new JSeparator();
		separator.setBounds(264, 405, 558, 15);
		jPanel_Login.add(separator);

		jButton_clear = new JButton("Clear");
		jButton_clear.setForeground(new Color(255, 255, 255));
		jButton_clear.setContentAreaFilled(false);
		jButton_clear.setOpaque(true);

		jButton_clear.setFont(new Font("Tahoma", Font.BOLD, 18));
		jButton_clear.setBackground(new Color(51, 51, 51));
		jButton_clear.setBounds(574, 430, 120, 45);
		jPanel_Login.add(jButton_clear);

		JLabel jLabel_Title = new JLabel("Welcome to VNV Coffee !");
		jLabel_Title.setForeground(SystemColor.textHighlightText);
		jLabel_Title.setFont(new Font("Monotype Corsiva", Font.BOLD, 70));
		jLabel_Title.setBounds(202, 120, 730, 72);
		jPanel_Login.add(jLabel_Title);

		// Nền xám trong
		JPanel transparentPanel = new JPanel();
		transparentPanel.setBackground(new Color(0, 0, 0, 80)); // Màu xám trong suốt
		transparentPanel.setBounds(168, 67, 764, 477);
		jPanel_Login.add(transparentPanel);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("images\\bg.jpg"));
		lblNewLabel.setBounds(0, 0, 1540, 845);
		jPanel_Login.add(lblNewLabel);
		// Add event
		jButton_Login.addActionListener(this);
		jButton_clear.addActionListener(this);
		text_User.addKeyListener(this);
		text_Password.addKeyListener(this);

	}

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					DangNhap_GUI frame = new DangNhap_GUI();
					frame.setVisible(true);
					ConnectDB.getInstance().connect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DangNhap_DAO collection = new DangNhap_DAO();
		Object o = e.getSource();
		String checkTenDN = "";

		if (o.equals(jButton_Login)) {
			Boolean kqCheck = false;
			String username = text_User.getText().trim();
			String password = String.valueOf(text_Password.getPassword()).trim();

			if (username.isEmpty() && password.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Vui lòng điền thông tin");
				return;
			} else {
				checkTenDN = collection.containTen(username);
				if (checkTenDN == null) {
					JOptionPane.showMessageDialog(this, "Tài khoản không tồn tại");
					text_User.setText("");
					text_Password.setText("");
					text_User.requestFocus();
					text_Password.requestFocus();
				}
			}
			ArrayList<String> duLieu = collection.dangNhap(username, password);

			if (!(duLieu.get(0) == null)) {
				if (text_User.getText().equals(duLieu.get(0))) {
					kqCheck = true;
					if (String.valueOf(text_Password.getPassword()).equals(duLieu.get(1))) {
						kqCheck = true;
					} else {
						kqCheck = false;
					}
				} else {
					kqCheck = false;
					JOptionPane.showMessageDialog(this, "Tài khoản không tồn tại");
				}
				if (kqCheck == true) {
					setVisible(false);
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
						TrangChu_GUI frame = new TrangChu_GUI();
						frame.setVisible(true);
						String tenNV = collection.getNameOfEmployeeWhenLogin(text_User.getText());
						frame.updateUsername(tenNV);
						NhanVien nhanVienLogin = collection.getNhanVienByTenTaiKhoan(text_User.getText());
						frame.setLoggedInEmployee(nhanVienLogin);

						isAdmin = text_User.getText().equals("admin");
						frame.checkAdmin(isAdmin);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			} else {
				if (checkTenDN.equals(text_User.getText())) {
					JOptionPane.showMessageDialog(this, "Mật khẩu không đúng");
				} else {

					JOptionPane.showMessageDialog(this, "Tài khoản không tồn tại");
				}
			}
		} else if (o.equals(jButton_clear)) {
			text_User.setText("");
			text_Password.setText("");
		}
	}

	// Placeholder - chữ nằm trong JTextField
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
				g.setFont(new Font("Arial", Font.PLAIN, 16));
				g.drawString(placeholder, getInsets().left, g.getFontMetrics().getHeight() + getInsets().top);
			}
		}
	}

	// Placeholder - chữ nằm trong JPasswordField
	public class PlaceholderPasswordField extends JPasswordField {
		private static final long serialVersionUID = 1L;
		private String placeholder;

		public PlaceholderPasswordField(String placeholder) {
			this.placeholder = placeholder;
		}

		@SuppressWarnings("deprecation")
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

			if (getText().isEmpty()) {
				g.setColor(Color.GRAY);
				g.setFont(new Font("Arial", Font.PLAIN, 16));
				g.drawString(placeholder, getInsets().left, g.getFontMetrics().getHeight() + getInsets().top);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (e.getSource() == text_User) {
				text_Password.requestFocus();
			} else if (e.getSource() == text_Password) {
				jButton_Login.doClick();
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}
