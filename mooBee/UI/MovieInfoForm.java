package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MovieInfoForm extends JFrame {

    private List<String> reviews;
    private int currentPage;
    private int reviewsPerPage;
    private JPanel reviewsContainer;
    private int currentPageGroup; // 현재 페이지 그룹 (예: 1~5)

    public MovieInfoForm() {
        reviews = new ArrayList<>();
        // 예시로 몇 개의 리뷰 추가
        for (int i = 1; i <= 23; i++) {
            reviews.add("nickname" + i + ": 리뷰 내용 " + i);
        }

        currentPage = 1;
        reviewsPerPage = 5;
        currentPageGroup = 1;

        setTitle("영화 정보 및 리뷰");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel movieInfoPanel = new JPanel();
        movieInfoPanel.setLayout(null);
        movieInfoPanel.setPreferredSize(new Dimension(1000, 450));
        movieInfoPanel.setBackground(Color.WHITE);

        JButton homeButton = new JButton("홈");
        homeButton.setBounds(30, 30, 80, 30);
        movieInfoPanel.add(homeButton);

        JButton previousPageButton = new JButton("이전 페이지");
        previousPageButton.setBounds(830, 30, 100, 30);
        movieInfoPanel.add(previousPageButton);

        JLabel titleLabel = new JLabel("영화제목", JLabel.CENTER);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 28));
        titleLabel.setBounds(250, 30, 500, 40);
        movieInfoPanel.add(titleLabel);

        JLabel moviePoster = new JLabel();
        moviePoster.setBounds(150, 100, 250, 350);
        moviePoster.setOpaque(true);
        moviePoster.setBackground(Color.GRAY);
        movieInfoPanel.add(moviePoster);

        JLabel movieDetails = new JLabel("<html>개봉일: 2024.08.14<br><br>관람시간: 120분<br><br>감독: 홍길동<br><br>주연 배우: 김철수, 이영희<br></html>");
        movieDetails.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        movieDetails.setBounds(450, 100, 400, 250);
        movieInfoPanel.add(movieDetails);

        JButton goToTrailerButton = new JButton("예고편 보러 가기");
        goToTrailerButton.setBounds(450, 360, 150, 40);
        movieInfoPanel.add(goToTrailerButton);

        JButton bookTicketButton = new JButton("예매하러 가기");
        bookTicketButton.setBounds(620, 360, 150, 40);
        movieInfoPanel.add(bookTicketButton);

        JPanel reviewPanel = new JPanel();
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

        JButton submitReviewButton = new JButton("리뷰 작성");
        submitReviewButton.setBounds(850, 80, 100, 40);
        reviewTitlePanel.add(submitReviewButton);

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

        JButton prevGroupButton = new JButton("<");
        prevGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPageGroup > 1) {
                    currentPageGroup--;
                    updatePagination(paginationPanel);
                    currentPage = (currentPageGroup - 1) * 5 + 1;
                    updateReviews();
                }
            }
        });
        paginationPanel.add(prevGroupButton);

        for (int i = 1; i <= 5; i++) {
            JButton pageButton = new JButton(String.valueOf(i));
            pageButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    currentPage = Integer.parseInt(pageButton.getText()) + (currentPageGroup - 1) * 5;
                    updateReviews();
                }
            });
            paginationPanel.add(pageButton);
        }

        JButton nextGroupButton = new JButton(">");
        nextGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((currentPageGroup * 5 * reviewsPerPage) < reviews.size()) {
                    currentPageGroup++;
                    updatePagination(paginationPanel);
                    currentPage = (currentPageGroup - 1) * 5 + 1;
                    updateReviews();
                }
            }
        });
        paginationPanel.add(nextGroupButton);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(movieInfoPanel);
        mainPanel.add(reviewPanel);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setContentPane(scrollPane);

        updateReviews(); // 초기 리뷰 표시
        updatePagination(paginationPanel); // 초기 페이지네이션 설정
        setVisible(true);
    }

    private void updateReviews() {
        reviewsContainer.removeAll();
        int start = (currentPage - 1) * reviewsPerPage;
        int end = Math.min(start + reviewsPerPage, reviews.size());

        for (int i = start; i < end; i++) {
            addReview(reviewsContainer, reviews.get(i));
        }

        reviewsContainer.revalidate();
        reviewsContainer.repaint();
    }

    private void updatePagination(JPanel paginationPanel) {
        // 현재 페이지 그룹(1~5, 6~10 등)의 버튼 텍스트 업데이트
        Component[] components = paginationPanel.getComponents();
        for (int i = 1; i <= 5; i++) {
            JButton pageButton = (JButton) components[i];
            int pageNumber = (currentPageGroup - 1) * 5 + i;
            pageButton.setText(String.valueOf(pageNumber));
            pageButton.setEnabled(pageNumber <= Math.ceil((double) reviews.size() / reviewsPerPage));
        }
    }

    private void addReview(JPanel container, String reviewText) {
        JPanel reviewPanel = new JPanel();
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
        new MovieInfoForm();
    }
}
