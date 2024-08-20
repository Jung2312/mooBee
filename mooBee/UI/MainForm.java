package UI;

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
import java.net.MalformedURLException;
import java.net.URL;

public class MainForm extends JFrame {

    private JFXPanel fxPanel;
    private BackgroundPanel Trailer;
    private MediaPlayer mediaPlayer;
    private static String userId;
    private MovieMgr mMgr;
    private JButton btnNext;
    private JButton btnPrevious;

    public MainForm(String userId) {
        this.userId = userId;

        initialize();

    }

    private void initialize() {
        mMgr = new MovieMgr();
        // mMgr.insertMovie(); 최종에 데이터 다시 입력
        setTitle("MooBee");
        setSize(1000, 700);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        JPanel MainForm_Panel = new JPanel();
        MainForm_Panel.setBounds(0, 0, 984, 661);
        getContentPane().add(MainForm_Panel);
        MainForm_Panel.setLayout(null);

        JPanel logoPanel = new JPanel();
        logoPanel.setBounds(292, 20, 400, 150);
        MainForm_Panel.add(logoPanel);
        logoPanel.setLayout(null);

        ImageIcon logoIcon = new ImageIcon("./UI/images/mainlogo.png");
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setBounds(0, 0, 400, 200); // 패널 안에서 이미지 위치 및 크기 조정
        logoPanel.add(logoLabel);

        JButton btnMovieBooking = new HoneyButton("영화 예매");
        btnMovieBooking.setBounds(250, 180, 150, 50);

        MainForm_Panel.add(btnMovieBooking);

        JButton btnNowShowing = new HoneyButton("현재 상영작");
        btnNowShowing.setBounds(417, 180, 150, 50);
        MainForm_Panel.add(btnNowShowing);

        JButton btnNotices = new HoneyButton("공지사항");
        btnNotices.setBounds(584, 180, 150, 50);
        MainForm_Panel.add(btnNotices);
        
        btnNotices.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
                NoticeList NL = new NoticeList(userId);
                setVisible(true);
                dispose();
			}
		});

        Trailer = new BackgroundPanel();
        Trailer.setBounds(151, 251, 700, 300);
        MainForm_Panel.add(Trailer);
        Trailer.setLayout(new BorderLayout());
        Trailer.setBackground(Color.WHITE);
        
        updatePoster(); // 포스터 이미지 불러오기

        fxPanel = new JFXPanel();
        Trailer.add(fxPanel, BorderLayout.CENTER);
        createVideoPlayer();

        btnPrevious = new JButton("<");
        btnPrevious.setBorderPainted(false);
        btnPrevious.setContentAreaFilled(false);
        Trailer.add(btnPrevious, BorderLayout.WEST);

        btnNext = new JButton(">");
        btnNext.setBorderPainted(false);
        btnNext.setContentAreaFilled(false);
        Trailer.add(btnNext, BorderLayout.EAST);

        btnPrevious.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePoster();
            }
        });

        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePoster();
            }
        });
        
        JButton MenuTab = new ControlButton("메뉴");
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
                int response = JOptionPane.showConfirmDialog(MainForm.this, "로그아웃 하시겠습니까?", "로그아웃", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (response == JOptionPane.YES_OPTION) {
                    dispose(); // 현재 창을 닫고 로그아웃 처리 (예를 들어 로그인 화면으로 돌아가기)
                }
            }
        });

        btnMovieBooking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReservationForm(userId);
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

        validate();
        setVisible(true);
    }

    public void createVideoPlayer() {
        Platform.runLater(() -> {
            try {
            	btnNext.setVisible(false);
            	btnPrevious.setVisible(false);
            	Thread.sleep(1000); // 1초 후에 재생
                StackPane root = new StackPane();
                Scene scene = new Scene(root, 700, 300);

                String videoUrl = mMgr.randomVideoUrl();
                Media media = new Media(videoUrl);
                mediaPlayer = new MediaPlayer(media);
                MediaView mediaView = new MediaView(mediaPlayer);
                
                mediaPlayer.setOnReady(() -> {
                    // 비디오의 원본 크기를 가져와서 MediaView에 적용
                    double videoWidth = media.getWidth();
                    double videoHeight = media.getHeight();
                    mediaView.setFitWidth(videoWidth);
                    mediaView.setFitHeight(videoHeight);
                    mediaView.setPreserveRatio(true); // 비율 유지
                });
                
                root.getChildren().add(mediaView);
                mediaPlayer.setAutoPlay(true);
                mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(mediaPlayer.getStartTime())); // 비디오 끝나면 처음으로 되돌리기
                fxPanel.setScene(scene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void updatePoster() {
        try {
            // 이미지 URL에서 이미지 로딩
            ImageIcon imgIcon = new ImageIcon(new URL(mMgr.randomPosterUrl()), BorderLayout.CENTER);
            Image img = imgIcon.getImage();
            // 배경 이미지로 설정
            Trailer.setBackgroundImage(img);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        userId = "11"; // 이 부분에 적절한 userId 값을 설정하세요
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
