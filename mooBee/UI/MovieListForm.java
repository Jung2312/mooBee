package UI;

import javax.swing.*;

import movie.MovieBean;
import movie.MovieMgr;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MovieListForm extends JFrame {
	private static String userId;
	MovieMgr mMgr;
	List<MovieBean> vlist;
	List<MovieBean> searchList;
	private JPanel moviePanel;

    public MovieListForm(String userId) {

        setTitle("현재 상영작");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

		mMgr = new MovieMgr();
		
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setPreferredSize(new Dimension(1000, 1600)); // 스크롤을 위해 패널 크기 설정

        JButton homeButton = new JButton("홈");
        homeButton.setBounds(30, 20, 100, 40);
        mainPanel.add(homeButton);

        JButton menuButton = new JButton("메뉴");
        menuButton.setBounds(870, 20, 100, 40);
        mainPanel.add(menuButton);

        JLabel titleLabel = new JLabel("현재 상영작", JLabel.CENTER);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        titleLabel.setBounds(400, 20, 200, 40);
        mainPanel.add(titleLabel);

        JTextField searchField = new JTextField();
        searchField.setBounds(250, 80, 450, 40);
        mainPanel.add(searchField);

		JButton searchButton = new JButton("검색");
		searchButton.setBounds(700, 80, 60, 40);
		mainPanel.add(searchButton);
		searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				searchList = mMgr.searchMovie(searchField.getText());
				updateMoviePanel(searchList);
				searchField.setText("");
			}
		});

		moviePanel = new JPanel();
        moviePanel.setLayout(new GridLayout(0, 4, 20, 20)); // n행 4열로 설정
        moviePanel.setLocation(EXIT_ON_CLOSE, ABORT);
        moviePanel.setBounds(100, 150, 800, 1400);
        mainPanel.add(moviePanel);

        vlist = mMgr.listMovie();
        moviePanel.removeAll();
        
		updateMoviePanel(vlist);
        
        // 스크롤 패널 설정
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setContentPane(scrollPane);

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainForm(userId); 
                dispose(); 
            }
        });

        setVisible(true);
    }
    
	private void updateMoviePanel(List<MovieBean> movies) {
		moviePanel.removeAll();
		for (int i = 0; i < Math.min(movies.size(), 16); i++) {
			MovieBean bean = movies.get(i);

			JPanel posterPanel = new JPanel();
			posterPanel.setPreferredSize(new Dimension(220, 330));
			JLabel posterLabel = new JLabel();
			try {
				ImageIcon imgIcon = new ImageIcon(new URL(bean.getPosterUrl()));
				Image img = imgIcon.getImage();
				Image scaledImg = img.getScaledInstance(220, 330, Image.SCALE_SMOOTH);
				posterLabel.setIcon(new ImageIcon(scaledImg, BorderLayout.NORTH));
				posterLabel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						MovieInfoForm movieInfoForm = new MovieInfoForm(bean.getDocid(), userId);
						movieInfoForm.setVisible(true);
						dispose();
					}
				});
			} catch (Exception e) {
				posterLabel.setText("Image not available");
			}
			posterPanel.add(posterLabel);
			moviePanel.add(posterPanel);
		}
	    moviePanel.revalidate(); // 레이아웃 재계산
	    moviePanel.repaint(); // UI 업데이트
	}

    public static void main(String[] args) {
        new MovieListForm(userId);
    }
}
