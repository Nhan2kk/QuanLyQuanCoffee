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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import connectDB.ConnectDB;
import dao.KhachHang_DAO;
import entity.ChiTietHoaDon;
import entity.KhachHang;
import entity.NhanVien;
import ui.Form_CapNhatNhanVien.HeaderRenderer;



public class Form_KhachHangThanhVien extends JPanel implements ActionListener,MouseListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DefaultTableModel bang;
	JTextField ma, ten, sdt, dtl, search;
	JButton them, xoa, sua, tim, lammoi;
	JTable table;
	JScrollPane scroll;
	KhachHang_DAO KH_dao;
	DefaultTableCellRenderer headerRenderer;
	public Form_KhachHangThanhVien() {
		
		
		KH_dao = new KhachHang_DAO();
		//thiết lập kết nối
		ConnectDB.getInstance().connect();
		
		setLayout(new BorderLayout());
		//north
		JPanel jpS1 = new JPanel();
        JPanel jpS2 = new JPanel();
        
        // Thiết lập màu nền cho panel để phân biệt
       
        
        // Tạo JSplitPane và đặt các panel vào
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jpS1, jpS2);
        
        // Thiết lập tỷ lệ kích thước ban đầu của split pane
        splitPane.setResizeWeight(0.5);
        splitPane.setDividerLocation(650);
        
        JPanel jp = new JPanel();
        
        JLabel title = new JLabel("THÔNG TIN KHÁCH HÀNG");
        title.setForeground(Color.WHITE);
        title.setBackground(getBackground());
        title.setFont(new Font("Segoe UI", Font.BOLD, 25));
        jp.add(title);
        jp.setBackground(new Color(51, 204, 255));
        jp.setPreferredSize(new Dimension(700,50));
        add(jp,BorderLayout.NORTH);
        
        //jpS1
        TitledBorder boderKhachHang = new TitledBorder(BorderFactory.createLineBorder(new Color(51, 204, 255)),"Khách hàng");
        boderKhachHang.setTitleFont(new Font("Tohama", Font.BOLD, 17));
        Box a = Box.createVerticalBox();
        a.setBorder(boderKhachHang);
        a.setPreferredSize(new Dimension(540,350));
        Box  a1 = Box.createHorizontalBox();
        Box  a2 = Box.createHorizontalBox();
        Box  a3 = Box.createHorizontalBox();
        Box  a4 = Box.createHorizontalBox();
        Box  a5 = Box.createHorizontalBox();
      
        
        JLabel lbma = new JLabel("Mã khách hàng:");
        lbma.setFont(new Font("Segoe UI", Font.BOLD, 15));
        a2.add(Box.createRigidArea(new Dimension(20,10)));
        a2.add(lbma);
        a2.add(Box.createRigidArea(new Dimension(14,10)));
        a2.add(ma = new JTextField());
        ma.setFont(new Font("Tahoma",Font.BOLD,13));
        ma.setPreferredSize(new Dimension(50,30));
        a2.add(Box.createRigidArea(new Dimension(20,10)));
       
        
        JLabel lbten = new JLabel("Tên khách hàng:");
        lbten.setFont(new Font("Segoe UI", Font.BOLD, 15));
        a3.add(Box.createRigidArea(new Dimension(20,10)));
        a3.add(lbten);
        a3.add(Box.createRigidArea(new Dimension(10,10)));
        a3.add(ten = new JTextField());
        ten.setFont(new Font("Tahoma",Font.BOLD,13));
        ten.setPreferredSize(new Dimension(50,30));
        a3.add(Box.createRigidArea(new Dimension(20,10)));
        
        JLabel lbsdt = new JLabel("Số điện thoại:");
        lbsdt.setFont(new Font("Segoe UI", Font.BOLD, 15));
        a4.add(Box.createRigidArea(new Dimension(20,10)));
        a4.add(lbsdt);
        a4.add(Box.createRigidArea(new Dimension(28,10)));
        a4.add(sdt = new JTextField());
        sdt.setFont(new Font("Tahoma",Font.BOLD,13));
        sdt.setPreferredSize(new Dimension(50,30));
        a4.add(Box.createRigidArea(new Dimension(20,10)));
        
        JLabel lbdtl =  new JLabel("Điểm tích lũy: ");
        lbdtl.setFont(new Font("Segoe UI", Font.BOLD, 15));
        a5.add(Box.createRigidArea(new Dimension(20,10)));
        a5.add(lbdtl);
        a5.add(Box.createRigidArea(new Dimension(26,10)));
        a5.add(dtl = new JTextField());
        dtl.setFont(new Font("Tahoma",Font.BOLD,13));
        dtl.setPreferredSize(new Dimension(50,30));
        dtl.setEditable(false);
        a5.add(Box.createRigidArea(new Dimension(20,10)));
        
        TitledBorder boderCN = new TitledBorder(BorderFactory.createLineBorder(new Color(51, 204, 255)),"Chức năng");
        boderCN.setTitleFont(new Font("Tohama", Font.BOLD, 17));
        Box b = Box.createVerticalBox();
        b.setBorder(boderCN);
        
        Box  b1 = Box.createHorizontalBox();
        Box  b2 = Box.createHorizontalBox();
        Box  b3 = Box.createHorizontalBox();
        
        b1.add(them = new JButton("Thêm khách hàng"));
        them.setFont(new Font("Tohama", Font.BOLD, 15));
        b1.add(Box.createRigidArea(new Dimension(30,10)));
        b1.add(xoa = new JButton("Xóa khách hàng"));
        xoa.setFont(new Font("Tohama", Font.BOLD, 15));
        
        b2.add(sua = new JButton("Sửa khách hàng"));
        sua.setFont(new Font("Tohama", Font.BOLD, 15));
        b2.add(Box.createRigidArea(new Dimension(42,10)));
        b2.add(lammoi = new JButton("Làm mới"));
        lammoi.setFont(new Font("Tohama", Font.BOLD, 15));
        b2.add(Box.createRigidArea(new Dimension(57,10)));
      
        
        JLabel lbtim = new JLabel("Tìm kiếm theo số điện thoại:");
        lbtim.setFont(new Font("Tohama", Font.BOLD, 15));

        b3.add(Box.createRigidArea(new Dimension(20,10)));
        b3.add(lbtim);
        b3.add(Box.createRigidArea(new Dimension(10,10)));
        b3.add(search = new JTextField());
        search.setPreferredSize(new Dimension(50,30));
        search.setFont(new Font("Tohama", Font.BOLD, 15));
        search.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                    String searchText = search.getText().trim().toLowerCase();
                    filterKhachHang(searchText);
            }

    });
        b3.add(Box.createRigidArea(new Dimension(10,10)));
       
        
        a.add(Box.createRigidArea(new Dimension(10,35)));
        a.add(a1);
        a.add(Box.createRigidArea(new Dimension(10,20)));
        a.add(a2);
        a.add(Box.createRigidArea(new Dimension(10,25)));
        a.add(a3);
        a.add(Box.createRigidArea(new Dimension(10,25)));
        a.add(a4);
        a.add(Box.createRigidArea(new Dimension(10,25)));
        a.add(a5);
        a.add(Box.createRigidArea(new Dimension(10,60)));
        
        b.add(Box.createRigidArea(new Dimension(10,25)));
        b.add(b1);
        b.add(Box.createRigidArea(new Dimension(10,25)));
        b.add(b2);
        b.add(Box.createRigidArea(new Dimension(10,25)));
        b.add(b3);
        b.add(Box.createRigidArea(new Dimension(10,25)));
    
        Box boxchung = Box.createVerticalBox();
        
        boxchung.add(Box.createRigidArea(new Dimension(10,25)));
        boxchung.add(a);
        boxchung.add(Box.createRigidArea(new Dimension(10,20)));
        boxchung.add(b);
        jpS1.add(boxchung);
        
        
        //jpS2
        Box c = Box.createVerticalBox();
        c.setPreferredSize(new Dimension(870,760));
        Box c1 = Box.createVerticalBox();
       
        
        TitledBorder boderDSKhachHang = new TitledBorder(BorderFactory.createLineBorder(new Color(51, 204, 255)),"DANH SÁCH KHÁCH HÀNG");
        boderDSKhachHang.setTitleFont(new Font("Tohama", Font.BOLD, 17));
        c1.setBorder(boderDSKhachHang);
        
		String[] column  = {"Mã khách hàng","Tên Khách hàng","Số điện thoại","Điểm tích lũy"};
		bang = new DefaultTableModel(column,0);
		table = new JTable(bang);
		
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        scroll = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setPreferredSize(new Dimension(500,550));
        scroll.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(51, 204, 255))));

        
		Font headerFont = new Font("Tahoma", Font.BOLD, 15);
        for (int i = 0; i < column.length; i++) {
                TableColumn columns = table.getColumnModel().getColumn(i);
                columns.setHeaderRenderer(new HeaderRenderer(headerFont));
                columns.setPreferredWidth(150);
        }
        
		table.setRowHeight(40);
		table.setFont(new Font("Tahoma",Font.BOLD,13));
		c1.add(Box.createRigidArea(new Dimension(10,50)));
		c1.add(scroll);
		c1.add(Box.createRigidArea(new Dimension(20,10)));
		 
		c.add(Box.createRigidArea(new Dimension(10,25)));
		c.add(c1);
		jpS2.add(c);
		add(splitPane,BorderLayout.CENTER);
		
		them.addActionListener(this);
		xoa.addActionListener(this);
		sua.addActionListener(this);
		lammoi.addActionListener(this);
		
		table.addMouseListener(this);
		
		ReadDatabaseToTable1();
	}
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
	
	public void ReadDatabaseToTable1() {
		List<KhachHang> list = KH_dao.getAllKhachHang();
		
		for(KhachHang kh : list) {
			bang.addRow(new Object[] {kh.getMaKH(),kh.getTenKH(),kh.getSdt(),kh.getDiemTL()});
		}
	}
	// hiển thị khách hàng thành viên viên tìm kiếm
    private void filterKhachHang(String searchText) {
            // Xóa tất cả hàng hiện có trong bảng
            bang.setRowCount(0);

            // Lọc danh sách khách hàng dựa trên chuỗi tìm kiếm
            ArrayList<KhachHang> filteredKhachHang = new ArrayList<>();
            for (KhachHang kh : KH_dao.getAllKhachHang()) {
                    String sdt = kh.getSdt().toLowerCase();
                    String hoTen = kh.getTenKH().toLowerCase();
                    if (sdt.contains(searchText) || hoTen.contains(searchText)) {
                            filteredKhachHang.add(kh);
                    }
            }

            // Thêm các khách hàng thỏa mãn điều kiện tìm kiếm vào bảng
            for (KhachHang kh : filteredKhachHang) {
                    bang.addRow(new Object[] { kh.getMaKH(), kh.getTenKH(), kh.getSdt()});
            }

            // Cuộn tới vị trí khách hàng đầu tiên thỏa mãn điều kiện tìm kiếm
            if (!(filteredKhachHang.isEmpty())) {
                    table.scrollRectToVisible(table.getCellRect(0, 0, true));
            }
    }
	
	public boolean checkdata() {
		if (ma.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this,"Vui lòng nhập mã khách hàng !!!");
			ma.requestFocus();
			return false;
		}else if(!ma.getText().matches("KH[0-9]{3}")) {
			JOptionPane.showMessageDialog(this,"Mã khách hàng gồm KH và 3 ký sô (Vd: KH001)");
			ma.requestFocus();
			return false;
		}else if(ten.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this,"Vui lòng nhập tên khách hàng !!!");
			ten.requestFocus();
			return false;
		}else if(!ten.getText().matches("[a-zA-Z\\p{L} ]+")) {
			JOptionPane.showMessageDialog(this,"Tên khách hàng gồm nhiều từ ngăn cách bởi khoảng trắng !!!");
			ten.requestFocus();
			return false;
		}else if(sdt.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this,"Vui lòng nhập số điện thoại khách hàng !!!");
			sdt.requestFocus();
			return false;
		}else if(!sdt.getText().matches("0[1-9]{1}[0-9]{8}")) {
			JOptionPane.showMessageDialog(this,"Số điện thoại bắt đầu bằng 0 bao gồm 10 số!!!");
			sdt.requestFocus();
			return false;
		}else {
			String sdtText = sdt.getText().trim();
	        // Kiểm tra xem số điện thoại đã tồn tại trong cơ sở dữ liệu hay chưa
	        if (KH_dao.kiemtraMa(sdtText)) {
	            JOptionPane.showMessageDialog(this, "Số điện thoại đã được sử dụng!!!");
	            sdt.requestFocus();
	            return false;
	        }
		}
		return true;
	}
	public void clearFields() {
		ma.setText("");
		ten.setText("");
		sdt.setText("");
		dtl.setText("");
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(them)) {
			if(checkdata()) {
				String makh = ma.getText();
				String tenkh = ten.getText();
				String dt = sdt.getText();
				
				int diemTL = 0;
				KhachHang kh = new KhachHang(makh, tenkh, dt);
				kh.setDiemTL(diemTL);
				KH_dao.create(kh);
					bang.addRow(new Object[] {
						kh.getMaKH(),kh.getTenKH(),kh.getSdt(),kh.getDiemTL()});
					clearFields();
					JOptionPane.showMessageDialog(this,"Thêm khách hàng thành công !!!");
			}
		}else if(o.equals(xoa)) {
			int r = table.getSelectedRow();
			if(r > -1) {
					int mess = JOptionPane.showConfirmDialog(this,"Bạn chắc chắn xóa nhân viên này ?","Xác nhận",JOptionPane.YES_NO_OPTION);
					if(mess == JOptionPane.YES_OPTION) {
						KH_dao.delete(sdt.getText().trim());
						bang.removeRow(r); // xóa trong table model
						clearFields();
						JOptionPane.showMessageDialog(this,"Xóa thành công !!!");
					}else {
						JOptionPane.showMessageDialog(this, "Xóa không thành công !!!");
					}
			}else {
				JOptionPane.showMessageDialog(this,"Vui lòng chọn một nhân viên để xóa !!!");
			}
		}else if(o.equals(sua)) {
			int ind = table.getSelectedRow();
			if(ind != -1) {
				if (checkdata()) {
					String makh = ma.getText();
					String tenkh = ten.getText();
					String dt = sdt.getText();
					int diem = Integer.parseInt(dtl.getText().trim());
					KhachHang kh = new KhachHang(makh, tenkh, dt);
					try {
						KH_dao.update(kh);
						JOptionPane.showMessageDialog(this,"Cập nhật nhân viên thành công !!!");
						bang.setValueAt(kh.getMaKH(),ind,0);
						bang.setValueAt(kh.getTenKH(),ind,1);
						bang.setValueAt(kh.getSdt(),ind,2);
						clearFields();
						table.clearSelection();;
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(this, "Cập nhật nhân viên không thành công !!!");
					}
				}
			}else {
				JOptionPane.showMessageDialog(this,"Vui lòng chọn 1 khách hàng để sửa !!!");
			}

		}else if(o.equals(lammoi)) {
			clearFields();
			table.clearSelection();
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		 int row = table.getSelectedRow();
		    if (row >= 0) { // Ensure a row is selected
		        ma.setText(table.getValueAt(row, 0).toString());
		        ten.setText(table.getValueAt(row, 1).toString());
		        sdt.setText(table.getValueAt(row, 2).toString());
		        dtl.setText(table.getValueAt(row, 3).toString());
		    }
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub	
	}
	
}