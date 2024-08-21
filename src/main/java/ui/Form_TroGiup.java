package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Form_TroGiup extends JPanel {
    private static final long serialVersionUID = 1L;
    JButton btnGhiChu1, btnGhiChu2, btnGhiChu3, btnGhiChu4, btnGhiChu5,btnGhiChu6;
    JLabel tieuDe, noiDung1,noiDung2,noiDung3,noiDung4,noiDung5,
    noiDung6,noiDung7,noiDung8,noiDung9,noiDung10,noiDung11;
    JPanel panelCauHoi, panelNoiDung;

    public Form_TroGiup() {
        setLayout(new BorderLayout());

        taoPanelButton();
        taoPanelNoiDung();

        add(panelCauHoi, BorderLayout.WEST);
        add(panelNoiDung, BorderLayout.CENTER);
    }

    private void taoPanelButton() {
    	panelCauHoi = new JPanel();
    	panelCauHoi.setLayout(new BoxLayout(panelCauHoi, BoxLayout.Y_AXIS));
    	panelCauHoi.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        tieuDe = new JLabel("                        Trợ Giúp");
        tieuDe.setFont(new Font("Segoe UI", Font.BOLD, 20));
        panelCauHoi.add(Box.createVerticalStrut(10));
        panelCauHoi.add(tieuDe);
        panelCauHoi.add(Box.createVerticalStrut(10));

        btnGhiChu1 = taoNut("• Điểm tích lũy được tính và sử dụng như thế nào ?");
        btnGhiChu2 = taoNut("• Tìm và xuất hóa đơn cũ ?");
        btnGhiChu3 = taoNut("• Các câu hỏi khác... ?");
        btnGhiChu4 = taoNut("• Tìm kiếm sản phẩm/ Tìm kiếm nhân viên ?");
        btnGhiChu5 = taoNut("• Vấn đề bản quyền");
        btnGhiChu6 = taoNut("• Hướng dẫn Order!");
        
        
        panelCauHoi.add(btnGhiChu6);
        panelCauHoi.add(Box.createVerticalStrut(10));
        panelCauHoi.add(btnGhiChu2);
        panelCauHoi.add(Box.createVerticalStrut(10));
        panelCauHoi.add(btnGhiChu4);
        panelCauHoi.add(Box.createVerticalStrut(10));
        panelCauHoi.add(btnGhiChu1);
        panelCauHoi.add(Box.createVerticalStrut(10));
        panelCauHoi.add(btnGhiChu5);
        panelCauHoi.add(Box.createVerticalStrut(10));
        panelCauHoi.add(btnGhiChu3);
    }
    

    private JButton taoNut(String text) {
        JButton nut = new JButton(text);
        nut.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        nut.addActionListener(langNgheSuKienNut);
        nut.setBorderPainted(false);
        return nut;
    }

    private void taoPanelNoiDung() {
        panelNoiDung = new JPanel();
        panelNoiDung.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //panelNoiDung.setLayout(new BorderLayout());
        panelNoiDung.setLayout(new BoxLayout(panelNoiDung, BoxLayout.Y_AXIS));
        panelNoiDung.add(Box.createVerticalStrut(10));
        panelNoiDung.add(noiDung1 = new JLabel("  ⁕⁕⁕.Welcome VNV Coffee App. Hãy chọn các mục bên để xem hướng dẫn.⁕⁕⁕"));
        noiDung1.setFont(new Font("Tahoma", Font.BOLD, 18));
    }

    private ActionListener langNgheSuKienNut = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton nut = (JButton) e.getSource();
            if (nut == btnGhiChu1) {
            	panelNoiDung.removeAll();
            	panelNoiDung.add(Box.createVerticalStrut(10));
                panelNoiDung.add(noiDung1 = new JLabel("  • Điểm tích lũy được tính và sử dụng như thế nào ?"));
                panelNoiDung.add(Box.createVerticalStrut(10));
                panelNoiDung.add(noiDung2 = new JLabel("  - Khi mua 1 món nước khách hàng sẽ nhận được 1 điểm. "
                + "Số lượng phần nước tương đương với số điểm khách hàng tích lũy được."));
                panelNoiDung.add(Box.createVerticalStrut(10));
                panelNoiDung.add(noiDung3 = new JLabel("  - Thu ngân hãy hỏi khách hàng có muốn sử dụng điểm không trước khi ấn nút 'Đổi ĐTL'. "));
                panelNoiDung.add(Box.createVerticalStrut(10));
                panelNoiDung.add(noiDung4 = new JLabel("  - Khi nhấn nút 1 điểm sẽ tương đương với 1000đ được giảm thẳng vào hóa đơn thanh toán của khách hàng."));
                noiDung1.setFont(new Font("Tahoma", Font.BOLD, 18));
                noiDung2.setFont(new Font("Tahoma", Font.PLAIN, 16));
                noiDung3.setFont(new Font("Tahoma", Font.PLAIN, 16));
                noiDung4.setFont(new Font("Tahoma", Font.PLAIN, 16));
            } else if (nut == btnGhiChu2) {
            	panelNoiDung.removeAll();
            	panelNoiDung.add(Box.createVerticalStrut(10));
                panelNoiDung.add(noiDung1 = new JLabel("  • Tìm và xuất hóa đơn cũ ?"));
                panelNoiDung.add(Box.createVerticalStrut(10));
                panelNoiDung.add(noiDung2 = new JLabel("  - Thu ngân có thể tìm và xuất lại những hóa đơn đã bán trong mục 'Hóa Đơn' hoặc mục 'Thống Kê'."));
                panelNoiDung.add(Box.createVerticalStrut(10));
                panelNoiDung.add(noiDung3 = new JLabel("  - Trong 'Hóa Đơn' sẽ hỗ trợ bạn xem lại hóa đơn bằng số điện thoại của khách hàng hoặc mã hóa đơn."));
                panelNoiDung.add(Box.createVerticalStrut(10));
                panelNoiDung.add(noiDung4 = new JLabel("  - Trong 'Thống Kê' sẽ hỗ trợ bạn xem lại các hóa đơn theo ngày, tháng, năm.'"));
                noiDung1.setFont(new Font("Tahoma", Font.BOLD, 18));
                noiDung2.setFont(new Font("Tahoma", Font.PLAIN, 16));
                noiDung3.setFont(new Font("Tahoma", Font.PLAIN, 16));
                noiDung4.setFont(new Font("Tahoma", Font.PLAIN, 16));
            } else if (nut == btnGhiChu3) {
            	panelNoiDung.removeAll();
            	panelNoiDung.add(Box.createVerticalStrut(10));
                panelNoiDung.add(noiDung1 = new JLabel("  • Các câu hỏi khác... ?"));
                panelNoiDung.add(Box.createVerticalStrut(10));
                panelNoiDung.add(noiDung2 = new JLabel("  - Xin lỗi vì sự bất tiện này!"));
                panelNoiDung.add(Box.createVerticalStrut(10));
                panelNoiDung.add(noiDung3 = new JLabel("  - Nếu danh sách câu hỏi có sẵn không hỗ trợ được bạn sử dụng các chứ năng của chương trình."));
                panelNoiDung.add(Box.createVerticalStrut(10));
                panelNoiDung.add(noiDung4 = new JLabel("  - Xin vui lòng liên hệ Zalo: Trần Long Vũ(admin lỏ) để được hỗ trợ tốt nhất. Xin cảm ơn."));
                noiDung1.setFont(new Font("Tahoma", Font.BOLD, 18));
                noiDung2.setFont(new Font("Tahoma", Font.PLAIN, 16));
                noiDung3.setFont(new Font("Tahoma", Font.PLAIN, 16));
                noiDung4.setFont(new Font("Tahoma", Font.PLAIN, 16));
            } else if (nut == btnGhiChu4) {
            	panelNoiDung.removeAll();
            	panelNoiDung.add(Box.createVerticalStrut(10));
                panelNoiDung.add(noiDung1 = new JLabel("  • Tìm kiếm sản phẩm/ Tìm kiếm nhân viên ?"));
                panelNoiDung.add(Box.createVerticalStrut(10));
                panelNoiDung.add(noiDung2 = new JLabel("  - Để tìm kiếm sản phẩm vui lòng vào mục 'Cập nhật sản phẩm'"));
                panelNoiDung.add(Box.createVerticalStrut(10));
                panelNoiDung.add(noiDung3 = new JLabel("    + Ngay dưới bảng danh sách sản phẩm có ô nhập để tìm kiếm sản phẩm theo mã hoặc tên sản phẩm."));
                panelNoiDung.add(Box.createVerticalStrut(10));
                panelNoiDung.add(noiDung4 = new JLabel("  - Để tìm kiếm nhân viên vui lòng vào mục 'Cập nhật nhân viên'"));
                panelNoiDung.add(Box.createVerticalStrut(10));
                panelNoiDung.add(noiDung5 = new JLabel("    + Tại trang này có mục 'Tìm kiếm' có thể hỗ trợ bạn tìm kiếm các nhân viên bằng tên hoặc mã nhân viên."));
                noiDung1.setFont(new Font("Tahoma", Font.BOLD, 18));
                noiDung2.setFont(new Font("Tahoma", Font.PLAIN, 16));
                noiDung3.setFont(new Font("Tahoma", Font.PLAIN, 16));
                noiDung4.setFont(new Font("Tahoma", Font.PLAIN, 16));
                noiDung5.setFont(new Font("Tahoma", Font.PLAIN, 16));
            }  else if (nut == btnGhiChu5) {
            	panelNoiDung.removeAll();
            	panelNoiDung.add(Box.createVerticalStrut(10));
                panelNoiDung.add(noiDung1 = new JLabel("  • Vấn đề bản quyền."));
                panelNoiDung.add(Box.createVerticalStrut(10));
                panelNoiDung.add(noiDung2 = new JLabel("  Bản quyền @2024 Nhóm 20 DHKTPM18C. Tất cả các quyền được bảo lưu."));
                panelNoiDung.add(Box.createVerticalStrut(10));
                panelNoiDung.add(noiDung3 = new JLabel("  - Thông tin về quyền lợi bản quyền: Ứng dụng này được bảo vệ bởi luật bản quyền và các hiệp định quốc tế về bản quyền. Mọi hành động sao chép, phân phối"));
                panelNoiDung.add(Box.createVerticalStrut(10));
                panelNoiDung.add(noiDung4 = new JLabel("  hoặc sử dụng ứng dụng hoặc một phần nào đó của ứng dụng mà không có sự đồng ý trước bằng văn bản từ chủ sở hữu bản quyền là vi phạm pháp luật và có"));
                panelNoiDung.add(Box.createVerticalStrut(10));
                panelNoiDung.add(noiDung5 = new JLabel("  thể bị truy cứu trách nhiệm dân sự và hình sự."));
                panelNoiDung.add(Box.createVerticalStrut(10));
                panelNoiDung.add(noiDung6 = new JLabel("  - Thông tin về việc sử dụng ứng dụng: Người dùng có quyền cài đặt và sử dụng ứng dụng này trên các thiết bị cá nhân của họ. Việc sao chép, sửa đổi hoặc "));
                panelNoiDung.add(Box.createVerticalStrut(10));
                panelNoiDung.add(noiDung7 = new JLabel("  phân phối ứng dụng này mà không có sự đồng ý trước bằng văn bản từ chủ sở hữu bản quyền là không được phép."));
                panelNoiDung.add(Box.createVerticalStrut(10));
                panelNoiDung.add(noiDung8 = new JLabel("  - Thông tin liên hệ: Nếu bạn có bất kỳ câu hỏi hoặc yêu cầu nào về việc sử dụng ứng dụng này hoặc vấn đề liên quan đến bản quyền, vui lòng liên hệ với "));
                panelNoiDung.add(Box.createVerticalStrut(10));
                panelNoiDung.add(noiDung9 = new JLabel("  chúng tôi tại địa chỉ email: nhom20appCoffee@gmail.com hoặc số điện thoại: 0879123123."));
                
                noiDung1.setFont(new Font("Tahoma", Font.BOLD, 18));
                noiDung2.setFont(new Font("Tahoma", Font.PLAIN, 16));
                noiDung3.setFont(new Font("Tahoma", Font.PLAIN, 16));
                noiDung4.setFont(new Font("Tahoma", Font.PLAIN, 16));
                noiDung5.setFont(new Font("Tahoma", Font.PLAIN, 16));
                noiDung6.setFont(new Font("Tahoma", Font.PLAIN, 16));
                noiDung7.setFont(new Font("Tahoma", Font.PLAIN, 16));
                noiDung8.setFont(new Font("Tahoma", Font.PLAIN, 16));
                noiDung9.setFont(new Font("Tahoma", Font.PLAIN, 16));
            } else if (nut == btnGhiChu6) {
            	panelNoiDung.removeAll();
            	panelNoiDung.add(Box.createVerticalStrut(10));
                panelNoiDung.add(noiDung1 = new JLabel("  • Hướng dẫn Order!"));
                panelNoiDung.add(Box.createVerticalStrut(10));
                panelNoiDung.add(noiDung2 = new JLabel("  - Vào trang 'Order' ở mục nhân viên → Chọn món mà khách hàng yêu cầu → Chọn size và số lượng → Ấn nút 'Thêm' nước sẽ hiển thị lên bảng Order Drink"));
                panelNoiDung.add(Box.createVerticalStrut(10));
                panelNoiDung.add(noiDung3 = new JLabel("  ngay bên cạnh → Hỏi số điện thoại của khách để kiểm xem đã là thành viên chưa để tích điểm hoặc sử dụng điểm tích lũy → Nhập số tiền khách đưa"));
                panelNoiDung.add(Box.createVerticalStrut(10));
                panelNoiDung.add(noiDung4 = new JLabel("  → Ấn nút 'Thanh Toán' để xuất hóa đơn đưa cho khách hàng hoặc nút 'Thanh Toán Không In' để thanh toán mà không cần in hóa đơn."));
                noiDung1.setFont(new Font("Tahoma", Font.BOLD, 18));
                noiDung2.setFont(new Font("Tahoma", Font.PLAIN, 16));
                noiDung3.setFont(new Font("Tahoma", Font.PLAIN, 16));
                noiDung4.setFont(new Font("Tahoma", Font.PLAIN, 16));
            } 
            panelNoiDung.revalidate();//Cập nhật giao diện
            panelNoiDung.repaint();
        }
    };
}