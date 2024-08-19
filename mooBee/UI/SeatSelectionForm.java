package UI;

import javax.swing.*;

import reservation.ReservationBean;
import reservation.ReservationMgr;
import seat.SeatBean;
import seat.SeatMgr;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class SeatSelectionForm extends JFrame {
    private static String userId, RSVDate;
    private static int RSVdocid, RSVcinemaNum;
    private SeatBean seatbean;
    private SeatMgr seatmrg;
    private ReservationBean RSVbean;
    private ReservationMgr RSVmgr;
    private Set<Integer> selectedSeatIds; // 선택된 좌석 ID를 저장할 Set

    public SeatSelectionForm(String userId,int RSVdocid, int RSVcinemaNum, String RSVDate) {
    	this.userId = userId;
        this.RSVDate = RSVDate;
        this.RSVcinemaNum = RSVcinemaNum;
        this.RSVdocid = RSVdocid;
        seatbean = new SeatBean();
        seatmrg = new SeatMgr();
        RSVbean = new ReservationBean();
        RSVmgr = new ReservationMgr();
        selectedSeatIds = new HashSet<>(); // 선택된 좌석 ID 초기화

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
        seatPanel.setBackground(Color.WHITE);
        seatPanel.setBounds(150, 100, 1100, 750);
        add(seatPanel);

        // 좌석 데이터 가져오기
        Vector<SeatBean> seatlist = seatmrg.listSeat(RSVcinemaNum);

        // 총 좌석 수 계산
        int totalSeats = seatlist.size();

        // 최대 열 수 설정
        int maxCols = 18;

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
        int cols = Math.min(maxCols, maxColIndex + 1);

        // 행과 열이 0이 되는 것을 방지
        if (rows <= 0) rows = 1;
        if (cols <= 0) cols = 1;

        // GridLayout을 사용하여 좌석 배치
        seatPanel.setLayout(new GridLayout(rows, cols, 5, 5)); // 5px 간격

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
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String seatLabel = seatGrid[i][j];
                JToggleButton seatButton;
                if (!seatLabel.isEmpty()) {
                    seatButton = new JToggleButton(seatLabel);
                    if (i >= rows / 2 - 1 && i <= rows / 2 && j >= cols / 2 - 2 && j < cols / 2 + 2) {
                        seatButton.setBackground(Color.ORANGE); // 가운데 2행 4열을 황금색으로
                    } else {
                        seatButton.setBackground(Color.BLACK);
                    }
                    
                    // 좌석 ID 설정
                    int seatId = getSeatIdByLabel(seatLabel); // 좌석 ID를 찾는 메소드
                    seatButton.putClientProperty("seatId", seatId);
                    seatButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // 선택된 좌석 ID를 추적
                            JToggleButton button = (JToggleButton) e.getSource();
                            Integer id = (Integer) button.getClientProperty("seatId");
                            if (button.isSelected()) {
                                selectedSeatIds.add(id);
                                System.out.println("선택된 좌석 ID들: " + selectedSeatIds);
                            } else {
                                selectedSeatIds.remove(id);
                            }
                        }
                    });
                    seatButton.setPreferredSize(buttonSize);
                    seatButton.setHorizontalAlignment(SwingConstants.CENTER);
                    seatButton.setVerticalAlignment(SwingConstants.CENTER);
                    seatButton.setForeground(Color.WHITE);
                    seatButton.setFont(new Font("Arial", Font.BOLD, 10));
                    seatButton.setFocusPainted(false); // 선택시 포커스 효과 제거
                } else {
                    seatButton = new JToggleButton(); // 빈 버튼 생성
                    seatButton.setBackground(Color.WHITE); // 빈 좌석은 흰색 배경
                    seatButton.setEnabled(false); // 빈 자리로 표시
                }
                seatButton.setPreferredSize(buttonSize);
                seatButton.setHorizontalAlignment(SwingConstants.CENTER);
                seatButton.setVerticalAlignment(SwingConstants.CENTER);
                seatButton.setForeground(Color.WHITE);
                seatButton.setFont(new Font("Arial", Font.BOLD, 10));
                seatPanel.add(seatButton);
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
            new ReservationForm(userId); 
            dispose(); 
        });
        
        payButton.addActionListener(e -> {
        	for (Integer seatId :selectedSeatIds ) {
        		RSVbean.setUserId("root");
            	RSVbean.setDocid(RSVdocid);
            	RSVbean.setRSVDATE(RSVDate);
            	RSVbean.setCinemaNum(RSVcinemaNum);
            	RSVbean.setSeatId(seatId);
            	RSVbean.setPrice(30000);
            	RSVbean.setAgeGroup("성인");
            	RSVmgr.insertRsvn(RSVbean);
            	
			}
            new ReservationCompleteForm(); 
            dispose(); 
        });

        setVisible(true);
    }

    private int getSeatIdByLabel(String seatLabel) {
        Vector<SeatBean> seatlist = seatmrg.listSeat(RSVcinemaNum);
        for (SeatBean seat : seatlist) {
            if (seat.getSeatNum().equals(seatLabel)) {
                return seat.getSeatId();
            }
        }
        return -1; // ID를 찾을 수 없는 경우
    }

    public static void main(String[] args) {
    	new SeatSelectionForm(userId, RSVcinemaNum, RSVcinemaNum, RSVDate);
    }
}
