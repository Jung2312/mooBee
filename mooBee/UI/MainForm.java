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

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class MainForm extends JFrame {
	
	private JFrame frame;
	
	public MainForm() {
		initialize();
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

		JPanel Trailer = new JPanel();
		Trailer.setBounds(151, 251, 700, 300);
		MainForm_Panel.add(Trailer);
		Trailer.setBackground(Color.GRAY);

		// video(+박스오피스 랭킹 1위 찾아서 해당 영화 트레일러 출력)
        JFXPanel fxPanel = new JFXPanel();
        Trailer.setLayout(new BorderLayout());
        Trailer.add(fxPanel, BorderLayout.CENTER);

        Platform.runLater(() -> createVideoPlayer(fxPanel));
        
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
				MyPage mp = new MyPage();
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

		setVisible(true);
	}

	private void createVideoPlayer(JFXPanel fxPanel) {
	    Platform.runLater(() -> {
	        try {
	            
	            StackPane root = new StackPane();
	            Scene scene = new Scene(root, 700, 300);

	            // ++ 랭킹 1위 영상 링크 가져오기
	            String videoUrl = "https://www.kmdb.or.kr/trailer/play/MK061240_P02.mp4";
	            Media media = new Media(videoUrl);
	            media.setOnError(() -> {
	                System.out.println("Media error: " + media.getError());
	            });

	            MediaPlayer mediaPlayer = new MediaPlayer(media);
	            MediaView mediaView = new MediaView(mediaPlayer);
	            root.getChildren().add(mediaView);
	            mediaPlayer.setAutoPlay(true); // Start playing the video immediately

	            fxPanel.setScene(scene);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    });
	}

	public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainForm window = new MainForm();
                window.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
	}
}
