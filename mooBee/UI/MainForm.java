package UI;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import movie.MovieMgr;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm extends JFrame {
	
	private JFrame frame;
	private JFXPanel fxPanel; 
	private JPanel Trailer;
	private MediaPlayer mediaPlayer;
	private String userId;
	MovieMgr mMgr;
    
	public MainForm(String userId) {
		this.userId = userId;
		initialize();
		mMgr = new MovieMgr();
	}

	private void initialize() {

		setTitle("MooBee");
		setSize(1000, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		
		JPanel MainForm_Panel = new JPanel();
		MainForm_Panel.setBounds(0, 0, 984, 661);
		getContentPane().add(MainForm_Panel);
		MainForm_Panel.setLayout(null);

		JLabel titleLabel = new JLabel("MooBee", JLabel.CENTER);
		titleLabel.setBounds(401, 51, 200, 50);
		MainForm_Panel.add(titleLabel);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 36));

		JButton btnMovieBooking = new JButton("영화 예매");
		btnMovieBooking.setBounds(301, 151, 100, 50);
		MainForm_Panel.add(btnMovieBooking);

		JButton btnNowShowing = new JButton("현재 상영작");
		btnNowShowing.setBounds(451, 151, 100, 50);
		MainForm_Panel.add(btnNowShowing);

		JButton btnNotices = new JButton("공지사항");
		btnNotices.setBounds(601, 151, 100, 50);
		MainForm_Panel.add(btnNotices);

	        Trailer = new JPanel();
	        Trailer.setBounds(151, 251, 700, 300);
	        MainForm_Panel.add(Trailer);
	        Trailer.setBackground(Color.GRAY);
	        Trailer.setLayout(new BorderLayout());
	
	        fxPanel = new JFXPanel();
	        Trailer.add(fxPanel, BorderLayout.CENTER);
	        Platform.runLater(this::createVideoPlayer);

		JButton MenuTab = new JButton("메뉴");
		MenuTab.setBounds(826, 36, 97, 34);
		MainForm_Panel.add(MenuTab);

		JPopupMenu popupMenu = new JPopupMenu();

		MenuTab.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				popupMenu.show(MenuTab, MenuTab.getWidth(), MenuTab.getHeight());
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

		JMenuItem Logout = new JMenuItem("로그아웃");
		popupMenu.add(Logout);

		Logout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane.showConfirmDialog(frame, "로그아웃 하시겠습니까?", "로그아웃", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);

				if (response == JOptionPane.YES_OPTION) {
					// 로그아웃 처리
					dispose(); // 현재 창을 닫고 로그아웃 처리 (예를 들어 로그인 화면으로 돌아가기)
				}
				// 아니오를 누르면 아무 일도 하지 않음 (팝업 창만 닫힘)
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
		
		validate();
		setVisible(true);
	}

	public void createVideoPlayer() {
	    Platform.runLater(() -> {
	        try {
	            StackPane root = new StackPane();
	            Scene scene = new Scene(root, 700, 300);

	            String videoUrl = mMgr.randomVideoUrl();
	            Media media = new Media(videoUrl);
	            mediaPlayer = new MediaPlayer(media);
	            MediaView mediaView = new MediaView(mediaPlayer);
	            root.getChildren().add(mediaView);
	            mediaPlayer.setAutoPlay(true);
	            mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(mediaPlayer.getStartTime())); // 비디오 끝나면 처음으로 되돌리기
	            fxPanel.setScene(scene);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    });
	}
	
	public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainForm window = new MainForm(userId);
                window.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
	}
}
