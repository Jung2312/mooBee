package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm extends JFrame {

    public MainForm() {
     
        setTitle("MooBee");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
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
        
        JPanel Trailer = new JPanel();
        Trailer.setBackground(Color.GRAY);
        Trailer.setBounds(150, 250, 700, 300); 
        add(Trailer);
        
        JButton MenuTab = new JButton("메뉴");
        MenuTab.setBounds(839, 36, 97, 34);
        add(MenuTab);

        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem GoTicket = new JMenuItem("영화 예매");
        popupMenu.add(GoTicket);

        JMenuItem GoMyPage = new JMenuItem("마이페이지");
        popupMenu.add(GoMyPage);

        JMenuItem GoNotice = new JMenuItem("공지사항");
        popupMenu.add(GoNotice);

        JMenuItem Logout = new JMenuItem("로그아웃");
        popupMenu.add(Logout);

        MenuTab.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                popupMenu.show(MenuTab, MenuTab.getWidth(), MenuTab.getHeight());
            }
        });


        btnMovieBooking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReservationForm();  
                dispose();  
            }
        });


        GoTicket.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReservationForm();  
                dispose();  
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainForm();
    }
}
