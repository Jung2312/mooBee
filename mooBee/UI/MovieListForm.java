package UI;

import javax.swing.*;

import movie.MovieBean;
import movie.MovieMgr;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MovieListForm extends JFrame {
	private static String userId;
	MovieMgr mMgr;
	Vector<MovieBean> vlist;
	Vector<MovieBean> searchList;
	private JPanel moviePanel;

	public MovieListForm(String userId) {
		this.userId = userId;
		setTitle("현재 상영작");
		setSize(1000, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		mMgr = new MovieMgr();

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setPreferredSize(new Dimension(1000, 1600)); // 스크롤을 위해 패널 크기 설정

		JButton homeButton = new ControlButton("홈");
		homeButton.setBounds(30, 20, 100, 40);
		mainPanel.add(homeButton);

		JButton menuButton = new ControlButton("메뉴");
		menuButton.setBounds(826, 20, 100, 40);
		mainPanel.add(menuButton);

		JLabel titleLabel = new JLabel("현재 상영작", JLabel.CENTER);
		titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
		titleLabel.setBounds(400, 20, 200, 40);
		mainPanel.add(titleLabel);

		JTextField searchField = new JTextField();
		searchField.setBounds(250, 80, 450, 40);
		mainPanel.add(searchField);

		JButton searchButton = new ControlButton("검색");
		searchButton.setBounds(700, 80, 60, 40);
		mainPanel.add(searchButton);
		
		ActionListener searchAction = new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        searchList = mMgr.searchMovie(searchField.getText());
		        updateMoviePanel(searchList);
		        searchField.setText("");
		    }
		};

		searchButton.addActionListener(searchAction);
		searchField.addActionListener(searchAction);

		moviePanel = new JPanel();
		//moviePanel.setLayout(new GridLayout(0, 4, 20, 20)); // n행 4열로 설정
		moviePanel.setLocation(EXIT_ON_CLOSE, ABORT);
		moviePanel.setBounds(100, 150, 800, 1400);
		mainPanel.add(moviePanel);

		vlist = mMgr.listMovie();
		moviePanel.removeAll();

		updateMoviePanel(vlist);

		// 메뉴 기능 추가
		JPopupMenu popupMenu = new JPopupMenu();

		menuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				popupMenu.show(menuButton, menuButton.getWidth(), menuButton.getHeight());
			}
		});

		JMenuItem GoTicket = new JMenuItem("영화 예매");
		popupMenu.add(GoTicket);

		JMenuItem GoMyPage = new JMenuItem("마이페이지");
		popupMenu.add(GoMyPage);

		GoMyPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyPage mp = new MyPage(userId);
				setVisible(true);
				dispose();
			}
		});

		JMenuItem GoNotice = new JMenuItem("공지사항");
		popupMenu.add(GoNotice);

		GoNotice.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				NoticeList NL = new NoticeList(userId);
				setVisible(true);
				dispose();
			}
		});

		JMenuItem Logout = new JMenuItem("로그아웃");
		popupMenu.add(Logout);

		Logout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane.showConfirmDialog(MovieListForm.this, "로그아웃 하시겠습니까?", "로그아웃",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (response == JOptionPane.YES_OPTION) {
					dispose(); // 현재 창을 닫고 로그아웃 처리 (예를 들어 로그인 화면으로 돌아가기)
				}
			}
		});

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

		GoTicket.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ReservationForm(userId);
				dispose();
			}
		});

		setVisible(true);
	}

	private void updateMoviePanel(Vector<MovieBean> movies) {
	    moviePanel.removeAll();
	    
	    moviePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20)); // Align left, with gaps between components

	    for (MovieBean bean : movies) {
	        JPanel posterPanel = new JPanel();
	        posterPanel.setPreferredSize(new Dimension(220, 330));
	        JLabel posterLabel = new JLabel();
	        try {
	            ImageIcon imgIcon = new ImageIcon(new URL(bean.getPosterUrl()));
	            Image img = imgIcon.getImage();
	            Image scaledImg = img.getScaledInstance(220, 330, Image.SCALE_SMOOTH);
	            posterLabel.setIcon(new ImageIcon(scaledImg));
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

	    moviePanel.revalidate(); // Recalculate layout
	    moviePanel.repaint(); // Update UI
	}


	public static void main(String[] args) {
		new MovieListForm(userId);
	}
}
