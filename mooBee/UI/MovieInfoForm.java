package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import movie.MovieBean;
import movie.MovieMgr;
import review.ReviewBean;
import review.ReviewMgr;

public class MovieInfoForm extends JFrame {

    private List<String> reviews;
    private int currentPage;
    private int reviewsPerPage;
    private JPanel reviewsContainer;
    private int currentPageGroup; // 현재 페이지 그룹 (예: 1~5)
    private JPanel reviewPanel;
    MovieMgr mMgr;
    MovieBean mBean;
    ReviewMgr rMgr;
    ReviewBean rBean;
    Vector<ReviewBean> vlist;
    
    public MovieInfoForm(int docid, String userId) {
        rMgr = new ReviewMgr();
        mMgr = new MovieMgr();
        mBean = mMgr.getMovie(docid);
        
        currentPage = 1;
        reviewsPerPage = 4; // 페이지당 4개의 리뷰 표시
        currentPageGroup = 1;
        
        setTitle("영화 정보 및 리뷰");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel movieInfoPanel = new JPanel();
        movieInfoPanel.setLayout(null);
        movieInfoPanel.setPreferredSize(new Dimension(1000, 450));
        movieInfoPanel.setBackground(Color.WHITE);

        JButton homeButton = new ControlButton("홈");
        homeButton.setBounds(30, 36, 97, 34);
        movieInfoPanel.add(homeButton);

        // 이전 페이지 버튼의 위치를 MainForm의 메뉴 위치와 동일하게 조정
        JButton backButton = new ControlButton("이전 페이지");
        backButton.setBounds(826, 36, 110, 34);  // 위치를 MainForm의 메뉴 버튼 위치와 동일하게 조정
        movieInfoPanel.add(backButton);

        JLabel titleLabel = new JLabel(mBean.getTitle(), JLabel.CENTER);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 28));
        titleLabel.setBounds(250, 30, 500, 40);
        movieInfoPanel.add(titleLabel);

        JLabel moviePoster = new JLabel();
        moviePoster.setBounds(150, 100, 250, 350);
        moviePoster.setOpaque(true);
        ImageIcon imgIcon;
        try {
            imgIcon = new ImageIcon(new URL(mBean.getPosterUrl()));
            Image img = imgIcon.getImage();  
            Image scaledImg = img.getScaledInstance(250, 350, Image.SCALE_SMOOTH); 
            moviePoster.setIcon(new ImageIcon(scaledImg, BorderLayout.NORTH)); 
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        movieInfoPanel.add(moviePoster);

        JLabel movieDetails = new JLabel("<html>개봉일: " + mBean.getReleaseDate() + "<br><br>관람시간: " + mBean.getRuntime() + "분<br><br>감독: " + mBean.getDirectorNm() + "<br><br>주연 배우: " + mBean.getActorNm() + "<br></html>");
        movieDetails.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        movieDetails.setBounds(450, 100, 400, 250);
        movieInfoPanel.add(movieDetails);

        // "예고편 보러 가기" 버튼 크기와 위치 조정
        JButton goToTrailerButton = new HoneyButton("예고편 보러 가기");
        goToTrailerButton.setBounds(450, 360, 180, 40); // 가로 크기를 늘림
        movieInfoPanel.add(goToTrailerButton);

        // "영화 예매" 버튼 크기와 위치 조정
        JButton bookTicketButton = new HoneyButton("예매하러 가기");
        bookTicketButton.setBounds(640, 360, 180, 40); // 가로 크기를 늘림
        movieInfoPanel.add(bookTicketButton);

        // 영화 줄거리 패널 추가 - JLabel 사용
        JLabel storyLabel = new JLabel("<html><body style='width: 720px;'>" + mBean.getPlot() + "</body></html>");
        storyLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        storyLabel.setVerticalAlignment(JLabel.TOP);

        // 패널 높이 설정
        JPanel storyPanel = new JPanel();
        storyPanel.setLayout(new BorderLayout());
        storyPanel.setBounds(30, 450, 826, storyLabel.getPreferredSize().height + 20); // 높이를 텍스트 내용에 따라 조정
        storyPanel.setBackground(Color.WHITE);
        storyPanel.setBorder(BorderFactory.createTitledBorder("영화 줄거리"));

        storyPanel.add(storyLabel, BorderLayout.CENTER);

        reviewPanel = new JPanel();
        reviewPanel.setLayout(new BorderLayout());
        reviewPanel.setPreferredSize(new Dimension(1000, 550));
        reviewPanel.setBackground(Color.WHITE);

        JPanel reviewTitlePanel = new JPanel();
        reviewTitlePanel.setLayout(null);
        reviewTitlePanel.setPreferredSize(new Dimension(1000, 140));
        reviewTitlePanel.setBackground(Color.WHITE);

        JLabel reviewTitleLabel = new JLabel("영화리뷰");
        reviewTitleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        reviewTitleLabel.setBounds(30, 20, 200, 40);
        reviewTitlePanel.add(reviewTitleLabel);

        JTextField reviewInputField = new JTextField();
        reviewInputField.setBounds(30, 80, 800, 40);
        reviewTitlePanel.add(reviewInputField);

        JButton submitReviewButton = new ControlButton("리뷰 작성");
        submitReviewButton.setBounds(850, 80, 100, 40);
        reviewTitlePanel.add(submitReviewButton);
        
        reviews = new ArrayList<>();

        JRadioButton latestOrderButton = new JRadioButton("최신순");
        latestOrderButton.setSelected(true);
        latestOrderButton.setBounds(30, 140, 100, 30);
        reviewTitlePanel.add(latestOrderButton);

        JRadioButton recommendedOrderButton = new JRadioButton("추천순");
        recommendedOrderButton.setBounds(150, 140, 100, 30);
        reviewTitlePanel.add(recommendedOrderButton);

        ButtonGroup orderGroup = new ButtonGroup();
        orderGroup.add(latestOrderButton);
        orderGroup.add(recommendedOrderButton);

        reviewPanel.add(reviewTitlePanel, BorderLayout.NORTH);

        reviewsContainer = new JPanel();
        reviewsContainer.setLayout(new BoxLayout(reviewsContainer, BoxLayout.Y_AXIS));

        JScrollPane reviewScrollPane = new JScrollPane(reviewsContainer);
        reviewScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        reviewPanel.add(reviewScrollPane, BorderLayout.CENTER);

        JPanel paginationPanel = new JPanel();
        paginationPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
        reviewPanel.add(paginationPanel, BorderLayout.SOUTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(movieInfoPanel);
        mainPanel.add(storyPanel); // 영화 줄거리 패널 추가
        mainPanel.add(reviewPanel);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setContentPane(scrollPane);

        // 각 버튼의 동작 추가
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainForm(""); // MainForm으로 이동
                dispose(); // 현재 창 닫기
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MovieListForm(); // MovieListForm으로 이동
                dispose(); // 현재 창 닫기
            }
        });

        bookTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReservationForm(userId); // ReservationForm으로 이동
                dispose(); // 현재 창 닫기
            }
        });

        goToTrailerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = mMgr.getVideoUrl(docid);
                if (url.equals("")) {
                    goToTrailerButton.setBackground(Color.GRAY);
                } else {
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            URI uri = new URI(url);
                            desktop.browse(uri);
                        } catch (IOException | URISyntaxException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
        
        submitReviewButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                rBean = new ReviewBean();
                rBean.setUserId(userId);
                rBean.setTitle(titleLabel.getText());
                rBean.setScope(5);
                rBean.setRecommend(5);
                rBean.setDocid(docid);
                rBean.setContent(reviewInputField.getText());
                rMgr.insertReview(rBean);

                updateReviews();
                updatePagination(paginationPanel); // 초기 페이지네이션 설정
            }
        });

        updateReviews(); // 초기 리뷰 표시
        updatePagination(paginationPanel); // 초기 페이지네이션 설정
        setVisible(true);
    }

    private void updateReviews() {
        reviewsContainer.removeAll();
        reviews.clear();
        
        vlist = rMgr.listReview();

        // 리뷰 추가 (각 페이지에 4개씩 배치)
        for (int i = 0; i < vlist.size(); i++) {
            ReviewBean rBean = vlist.get(i);
            reviews.add("   "+rBean.getUserId() + " : " + rBean.getContent());
        }
        int start = (currentPage - 1) * reviewsPerPage;
        int end = Math.min(start + reviewsPerPage, reviews.size());
        
        for (int i = start; i < end; i++) {
            addReview(reviewsContainer, reviews.get(i));
        }

        // 빈 공간을 남겨두기 위한 로직 추가
        int emptySlots = reviewsPerPage - (end - start);
        for (int i = 0; i < emptySlots; i++) {
            JPanel emptyPanel = new JPanel();
            emptyPanel.setPreferredSize(new Dimension(900, 80));
            reviewsContainer.add(emptyPanel);
        }

        reviewsContainer.revalidate();
        reviewsContainer.repaint();
    }

    private void updatePagination(JPanel paginationPanel) {
        paginationPanel.removeAll();

        int totalPages = (int) Math.ceil((double) reviews.size() / reviewsPerPage);
        int maxPageInGroup = 5;
        int startPage = (currentPageGroup - 1) * maxPageInGroup + 1;
        int endPage = Math.min(currentPageGroup * maxPageInGroup, totalPages);

        ButtonGroup pageButtonGroup = new ButtonGroup(); // 버튼 그룹으로 묶기

        if (currentPageGroup > 1) {
            JButton prevGroupButton = new ControlButton("<");
            prevGroupButton.addActionListener(e -> {
                currentPageGroup--;
                updatePagination(paginationPanel);
                currentPage = (currentPageGroup - 1) * maxPageInGroup + 1;
                updateReviews();
            });
            paginationPanel.add(prevGroupButton);
        }

        for (int i = startPage; i <= endPage; i++) {
            JToggleButton pageButton = new ControlToggleButton(String.valueOf(i));
            if (i == currentPage) {
                pageButton.setSelected(true); // 현재 페이지에 해당하는 버튼을 선택 상태로 설정
            }
            pageButton.addActionListener(e -> {
                currentPage = Integer.parseInt(pageButton.getText());
                updateReviews();
            });
            pageButtonGroup.add(pageButton); // 버튼 그룹에 추가
            paginationPanel.add(pageButton);
        }

        if (endPage < totalPages) {
            JButton nextGroupButton = new ControlButton(">");
            nextGroupButton.addActionListener(e -> {
                currentPageGroup++;
                updatePagination(paginationPanel);
                currentPage = (currentPageGroup - 1) * maxPageInGroup + 1;
                updateReviews();
            });
            paginationPanel.add(nextGroupButton);
        }

        paginationPanel.revalidate();
        paginationPanel.repaint();
    }

    private void addReview(JPanel container, String reviewText) {
        reviewPanel = new JPanel();
        reviewPanel.setLayout(new BorderLayout());
        reviewPanel.setPreferredSize(new Dimension(900, 80));

        JLabel reviewLabel = new JLabel(reviewText);
        reviewLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 18));

        reviewPanel.add(reviewLabel, BorderLayout.CENTER);

        JSeparator separator = new JSeparator();
        separator.setForeground(Color.LIGHT_GRAY);

        container.add(reviewPanel);
        container.add(separator);
    }

    public static void main(String[] args) {
        new MovieInfoForm(1,"11");
    }
}
