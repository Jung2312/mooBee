package UI;

import javax.swing.*;
import seat.SeatBean;
import seat.SeatMgr;
import java.awt.*;
import java.util.Vector;

public class SeatSelectionForm extends JFrame {
    private static String userId, RSVDate;
    private static int RSVdocid, RSVcinemaNum;
    private SeatBean seatbean;
    private SeatMgr seatmrg;

    public SeatSelectionForm(int RSVdocid, int RSVcinemaNum, String RSVDate) {
        this.RSVDate = RSVDate;
        this.RSVcinemaNum = RSVcinemaNum;
        this.RSVdocid = RSVdocid;
        seatbean = new SeatBean();
        seatmrg = new SeatMgr();

        setTitle("좌석 선택 화면");
        setSize(1400, 1000); // 창 크기
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JButton homeButton = new JButton("홈");
        homeButton.setBounds(30, 30, 120, 50);
        add(homeButton);

        JButton backButton = new JButton("이전 페이지");
        backButton.setBounds(1250, 30, 120, 50);
        add(backButton);

        JLabel screenLabel = new JLabel("SCREEN", JLabel.CENTER);
        screenLabel.setBounds(200, 50, 1000, 40);
        screenLabel.setOpaque(true);
        screenLabel.setBackground(Color.LIGHT_GRAY);
        add(screenLabel);

        // 좌석 패널 설정
        JPanel seatPanel = new JPanel();
        seatPanel.setLayout(null); // 좌석 패널은 null 레이아웃을 사용하여 직접 위치를 설정합니다.
        seatPanel.setBounds(150, 100, 1100, 750);
        add(seatPanel);

        // 각 사각형 패널 설정
        JPanel[] borders = new JPanel[4];
        Color[] borderColors = {Color.RED, Color.BLUE, new Color(128, 0, 128), Color.GREEN};

        // 좌석 패널을 4등분
        for (int i = 0; i < borders.length; i++) {
            borders[i] = new JPanel();
            borders[i].setBorder(BorderFactory.createLineBorder(borderColors[i], 3));
            borders[i].setOpaque(false);
            seatPanel.add(borders[i]);
        }

        // 좌석 패널의 각 사각형 크기 설정
        int panelWidth = 1100 / 2; // 좌석 패널의 너비를 2로 나눈 크기
        int panelHeight = 750 / 2; // 좌석 패널의 높이를 2로 나눈 크기

        borders[0].setBounds(0, 0, panelWidth, panelHeight); // 좌측 상단
        borders[1].setBounds(panelWidth, 0, panelWidth, panelHeight); // 우측 상단
        borders[2].setBounds(0, panelHeight, panelWidth, panelHeight); // 좌측 하단
        borders[3].setBounds(panelWidth, panelHeight, panelWidth, panelHeight); // 우측 하단

        // 좌석 데이터 가져오기
        Vector<SeatBean> seatlist = seatmrg.listSeat(RSVcinemaNum);

        // 총 좌석 수 계산
        int totalSeats = seatlist.size();

        // 좌석 목록에서 최대 행과 열 계산
        int maxRowIndex = -1;
        int maxColIndex = -1;
        for (SeatBean seat : seatlist) {
            String seatLabel = seat.getSeatNum();
            if (seatLabel.length() > 1) {
                char rowChar = seatLabel.charAt(0); // 행 문자 (예: 'A')
                int colNum = Integer.parseInt(seatLabel.substring(1)); // 열 번호 (예: 1)

                int rowIndex = rowChar - 'A'; // 행 인덱스 계산
                int colIndex = colNum - 1; // 열 인덱스 계산

                maxRowIndex = Math.max(maxRowIndex, rowIndex);
                maxColIndex = Math.max(maxColIndex, colIndex);
            }
        }

        // 행과 열 계산
        int rows = maxRowIndex + 1;
        int cols = maxColIndex + 1;

        // 좌석 그리드 배열 초기화
        String[][] seatGrid = new String[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                seatGrid[i][j] = ""; // 빈 문자열로 초기화
            }
        }

        // 좌석 목록을 그리드에 배치
        for (SeatBean seat : seatlist) {
            String seatLabel = seat.getSeatNum(); // 좌석 번호 가져오기
            // 좌석 번호가 알파벳과 숫자로 구성되어 있다고 가정
            char rowChar = seatLabel.charAt(0); // 행 문자
            int colNum = Integer.parseInt(seatLabel.substring(1)); // 열 번호
            int rowIndex = rowChar - 'A'; // 행 인덱스 계산
            int colIndex = colNum - 1; // 열 인덱스 계산
            if (rowIndex >= 0 && rowIndex < rows && colIndex >= 0 && colIndex < cols) {
                seatGrid[rowIndex][colIndex] = seatLabel;
            }
        }

        // 좌석 버튼 생성 및 추가
        Dimension buttonSize = new Dimension(60, 50);
        Font buttonFont = new Font("Arial", Font.BOLD, 10);

        // 황금색 배경 적용할 좌석 구역
        for (int quadrant = 0; quadrant < 4; quadrant++) {
            JPanel quadrantPanel = borders[quadrant];
            int rowOffset = (quadrant / 2) * (rows / 2);
            int colOffset = (quadrant % 2) * (cols / 2);
            int numRowsPerQuadrant = (rows + 1) / 2; // 각 사각형의 행 수
            int numColsPerQuadrant = (cols + 1) / 2; // 각 사각형의 열 수

            quadrantPanel.setLayout(new GridLayout(numRowsPerQuadrant, numColsPerQuadrant, 5, 5));

            int centerRowStart = (rows / 2) - 1;
            int centerRowEnd = centerRowStart + 2;
            int centerColStart = (cols / 2) - 2;
            int centerColEnd = centerColStart + 4;

            for (int i = 0; i < numRowsPerQuadrant; i++) {
                for (int j = 0; j < numColsPerQuadrant; j++) {
                    int rowIndex = rowOffset + i;
                    int colIndex = colOffset + j;
                    String seatLabel = "";
                    boolean isCenterSeat = rowIndex >= centerRowStart && rowIndex < centerRowEnd &&
                                           colIndex >= centerColStart && colIndex < centerColEnd;
                    if (rowIndex < rows && colIndex < cols) {
                        seatLabel = seatGrid[rowIndex][colIndex];
                    }

                    JToggleButton seatButton;
                    if (!seatLabel.isEmpty()) {
                        seatButton = new JToggleButton(seatLabel);
                        seatButton.setFont(buttonFont); // 기본 글꼴 설정
                        seatButton.setHorizontalAlignment(SwingConstants.CENTER);
                        seatButton.setVerticalAlignment(SwingConstants.CENTER);
                    } else {
                        seatButton = new JToggleButton(); // 빈 버튼 생성
                        seatButton.setBackground(Color.WHITE); // 빈 좌석은 흰색 배경
                        seatButton.setEnabled(false); // 빈 자리로 표시
                    }
                    seatButton.setPreferredSize(buttonSize);
                    seatButton.setBackground(isCenterSeat ? Color.ORANGE : Color.BLACK); // 황금색 배경
                    seatButton.setForeground(Color.WHITE);
                    quadrantPanel.add(seatButton);
                }
            }
        }

        // 패널 업데이트
        seatPanel.revalidate();
        seatPanel.repaint();

        // 좌석 미리보기 버튼들
        JButton previewButton1 = new JButton("좌석 미리보기");
        previewButton1.setBounds(20, 180, 120, 50);
        add(previewButton1);

        JButton previewButton2 = new JButton("좌석 미리보기");
        previewButton2.setBounds(20, 450, 120, 50);
        add(previewButton2);

        JButton previewButton3 = new JButton("좌석 미리보기");
        previewButton3.setBounds(1250, 180, 120, 50);
        add(previewButton3);

        JButton previewButton4 = new JButton("좌석 미리보기");
        previewButton4.setBounds(1250, 450, 120, 50);
        add(previewButton4);

        JButton payButton = new JButton("결제하기");
        payButton.setBounds(1250, 750, 120, 60);
        add(payButton);

        homeButton.addActionListener(e -> {
            new MainForm(userId); 
            dispose(); 
        });

        backButton.addActionListener(e -> {
            new ReservationForm(); 
            dispose(); 
        });

        setVisible(true);
    }
    public static void main(String[] args) {
    	new SeatSelectionForm(RSVcinemaNum, RSVcinemaNum, RSVDate);
    }
}
