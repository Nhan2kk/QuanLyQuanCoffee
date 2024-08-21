package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Form_TrangChu extends JPanel {

    public Form_TrangChu() {
        setLayout(new BorderLayout());

        // Tạo một JLabel để chứa hình ảnh làm nền
        JLabel background = new JLabel();
        background.setIcon(new ImageIcon("images\\bg.jpg"));
        background.setLayout(new BorderLayout());

        // Set kích thước cho JLabel bằng kích thước của JPanel
        background.setBounds(0, 0, getWidth(), getHeight());

        // Thêm clock vào JLabel background
        DigitalClock clock = new DigitalClock();
        clock.start();
        background.add(clock);

        // Thêm JLabel background vào JPanel
        add(background, BorderLayout.CENTER);
    }

    public class DigitalClock extends JPanel implements Runnable {
        private Thread thread;
        private JLabel hourLabel, minuteLabel, secondLabel;
        private JLabel dateLabel;

        public DigitalClock() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            // Tạo label hiển thị giờ
            hourLabel = new JLabel();
            hourLabel.setFont(new Font("Arial", Font.BOLD, 36));
            hourLabel.setForeground(Color.BLACK);
            hourLabel.setHorizontalAlignment(JLabel.CENTER);
            hourLabel.setPreferredSize(new Dimension(80, 80));
            hourLabel.setBackground(Color.white);
            hourLabel.setOpaque(true);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(5, 5, 5, 5);
            add(hourLabel, gbc);

            // Tạo label hiển thị phút
            minuteLabel = new JLabel();
            minuteLabel.setFont(new Font("Arial", Font.BOLD, 36));
            minuteLabel.setForeground(Color.BLACK);
            minuteLabel.setHorizontalAlignment(JLabel.CENTER);
            minuteLabel.setPreferredSize(new Dimension(80, 80));
            minuteLabel.setBackground(Color.white);
            minuteLabel.setOpaque(true);
            gbc.gridx = 1;
            gbc.gridy = 0;
            add(minuteLabel, gbc);

            // Tạo label hiển thị giây
            secondLabel = new JLabel();
            secondLabel.setFont(new Font("Arial", Font.BOLD, 36));
            secondLabel.setForeground(Color.BLACK);
            secondLabel.setHorizontalAlignment(JLabel.CENTER);
            secondLabel.setPreferredSize(new Dimension(80, 80));
            secondLabel.setBackground(Color.white);
            secondLabel.setOpaque(true);
            gbc.gridx = 2;
            gbc.gridy = 0;
            add(secondLabel, gbc);

            // Tạo label hiển thị ngày tháng năm
            dateLabel = new JLabel();
            dateLabel.setFont(new Font("Arial", Font.ITALIC, 20));
            dateLabel.setHorizontalAlignment(JLabel.CENTER);
            dateLabel.setForeground(Color.WHITE);
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 3;
            gbc.insets = new Insets(10, 5, 5, 5);
            add(dateLabel, gbc);

        }

        public void start() {
            thread = new Thread(this);
            thread.start();
        }

        public void stop() {
            thread.interrupt();
        }

        @Override
        public void run() {
            while (thread != null) {
                try {
                    Calendar calendar = Calendar.getInstance();
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int minute = calendar.get(Calendar.MINUTE);
                    int second = calendar.get(Calendar.SECOND);

                    hourLabel.setText(String.format("%02d", hour));
                    minuteLabel.setText(String.format("%02d", minute));
                    secondLabel.setText(String.format("%02d", second));

                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");
                    String date = dateFormat.format(calendar.getTime());
                    dateLabel.setText(date);

                    repaint(); // Vẽ lại component
                    Thread.sleep(1000); // Delay 1 second
                } catch (InterruptedException ex) {
                    break;
                }
            }
        }

        // Override phương thức paintComponent để vẽ nền gradient
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // Vẽ hình nền
            try {
                BufferedImage backgroundImage = ImageIO.read(new File("images/bg.jpg"));
                g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Tạo điểm bắt đầu và điểm kết thúc của gradient
            Point start = new Point(0, 0);
            Point end = new Point(0, getHeight());

            // Tạo màu cho gradient
            Color color1 = new Color(0, 0, 0, 50);
            Color color2 = new Color(0, 0, 0, 50);

            // Tạo gradient paint
            GradientPaint gradient = new GradientPaint(start, color1, end, color2);

            // Vẽ nền gradient
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }

    }

    public class Point2D {
        // Tọa độ x và y của điểm
        private double x;
        private double y;

        // Constructor để tạo một điểm với các tọa độ cho trước
        public Point2D(double x, double y) {
            this.x = x;
            this.y = y;
        }

        // Phương thức để lấy tọa độ x của điểm
        public double getX() {
            return x;
        }

        // Phương thức để lấy tọa độ y của điểm
        public double getY() {
            return y;
        }

        // Phương thức để đặt tọa độ của điểm
        public void setLocation(double x, double y) {
            this.x = x;
            this.y = y;
        }

        // Phương thức để tính khoảng cách giữa hai điểm
        public double distance(Point2D otherPoint) {
            double dx = this.x - otherPoint.x;
            double dy = this.y - otherPoint.y;
            return Math.sqrt(dx * dx + dy * dy);
        }

        // Phương thức để kiểm tra xem hai điểm có bằng nhau không
        public boolean equals(Point2D otherPoint) {
            return this.x == otherPoint.x && this.y == otherPoint.y;
        }

        // Phương thức để hiển thị thông tin về điểm
        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

}
