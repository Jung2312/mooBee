package UI;

import javax.swing.*;
import java.awt.*;

public class MainForm extends JFrame {

    public MainForm() {
        setTitle("MooBee");
        
        setSize(1000, 700);
        
        setLayout(null);
        
        JLabel titleLabel = new JLabel("MooBee", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setBounds(400, 50, 200, 50); 
        add(titleLabel);
        
        JButton btnMovieBooking = new JButton("영화 예매");
        btnMovieBooking.setBounds(300, 150, 100, 50); 
        add(btnMovieBooking);
        
        JButton btnNowShowing = new JButton("현재 상영작");
        btnNowShowing.setBounds(450, 150, 100, 50);
        add(btnNowShowing);
        
        JButton btnNotices = new JButton("공지사항");
        btnNotices.setBounds(600, 150, 100, 50); 
        add(btnNotices);
        
        JPanel grayBox = new JPanel();
        grayBox.setBackground(Color.GRAY);
        grayBox.setBounds(150, 250, 700, 300); 
        add(grayBox);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }

    public static void main(String[] args) {

        new MainForm();
    }
}
