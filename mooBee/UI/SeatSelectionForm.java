package UI;

import javax.swing.*;
import reservation.ReservationBean;
import reservation.ReservationMgr;
import seat.SeatBean;
import seat.SeatMgr;
import user.UserBean;
import user.UserMgr;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class SeatSelectionForm extends JFrame {
    private static String userId, ViewDate, RSVSeat = "";
    private static int RSVdocid, RSVcinemaNum, YouthCount, AdultCount;
    private SeatBean seatbean;
    private SeatMgr seatmgr;
    private ReservationBean RSVbean;
    private ReservationMgr RSVmgr;
    private Set<Integer> selectedSeatIds; // 선택된 좌석 ID를 저장할 Set
    private int cinemaYouth, cinemaAdult;
    private JLabel youthLabel;
    private JLabel adultLabel;
    private JLabel TempLabel;
    private UserBean userbean;
    private UserMgr usermgr;
    private static double pay;
    private static int Amount;
    private static int Allcount;

    public SeatSelectionForm(String userId, int RSVdocid, int RSVcinemaNum, String ViewDate, int YouthCount,
                             int AdultCount) {
        this.userId = userId;
        this.ViewDate = ViewDate;
        this.RSVcinemaNum = RSVcinemaNum;
        this.RSVdocid = RSVdocid;
        this.YouthCount = YouthCount;
        this.AdultCount = AdultCount;
        RSVSeat = "";
        Allcount = YouthCount + AdultCount;
        seatbean = new SeatBean();
        seatmgr = new SeatMgr();
        RSVbean = new ReservationBean();
        RSVmgr = new ReservationMgr();
        userbean = new UserBean();
        usermgr = new UserMgr();
        selectedSeatIds = new HashSet<>(); // 선택된 좌석 ID 초기화

        setTitle("좌석 선택 화면");
        setSize(1400, 1000); // 창 크기
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JButton homeButton = new ControlButton("홈");
        homeButton.setBounds(30, 30, 120, 50);
        add(homeButton);

        JButton backButton = new ControlButton("이전 페이지");
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
        Vector<SeatBean> seatlist = seatmgr.listSeat(RSVcinemaNum);

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
        if (rows <= 0)
            rows = 1;
        if (cols <= 0)
            cols = 1;

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

                    // 좌석 ID 설정
                    int seatId = getSeatIdByLabel(seatLabel); // 좌석 ID를 찾는 메소드

                    // 해당 좌석의 SeatBean 가져오기
                    SeatBean currentSeat = seatlist.stream().filter(seat -> seat.getSeatNum().equals(seatLabel))
                            .findFirst().orElse(null);

                    // 기본 좌석 색상 설정
                    Color defaultColor;
                    if (i >= rows / 2 - 1 && i <= rows / 2 && j >= cols / 2 - 2 && j < cols / 2 + 2) {
                        defaultColor = Color.ORANGE; // 가운데 2행 4열을 황금색으로
                    } else {
                        defaultColor = Color.BLACK;
                    }

                    if (currentSeat.isSeatChk()) {
                        ReservationBean Temp = RSVmgr.getTemp(seatId);
                        if (Temp.getTemp() <= 30.0) {
                            // 온도가 30 이하인 경우 좌석을 빨간색으로 변경
                            seatButton.setBackground(Color.RED);

                        } else if (Temp.getTemp() > 30) {
                            // 온도가 30 초과인 경우 기본 회색으로 설정
                            seatButton.setBackground(Color.GRAY);
                        }
                        seatButton.setEnabled(false);
                    } else {
                        // seatChk가 false인 경우: 기본 색상과 활성화 상태
                        seatButton.setBackground(defaultColor);

                        seatButton.putClientProperty("seatId", seatId);
                        seatButton.putClientProperty("defaultColor", defaultColor); // 기본 색상 저장

                        // UIManager를 사용해 기본 선택된 배경색을 제거
                        seatButton.setContentAreaFilled(false);
                        seatButton.setOpaque(true);

                        seatButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                // 선택된 좌석 ID를 추적
                                JToggleButton button = (JToggleButton) e.getSource();
                                Integer id = (Integer) button.getClientProperty("seatId");
                                Color originalColor = (Color) button.getClientProperty("defaultColor");

                                if (button.isSelected()) {
                                    if (selectedSeatIds.size() < YouthCount + AdultCount) {
                                        button.setBackground(Color.BLUE); // 선택된 경우 파란색으로 변경
                                        selectedSeatIds.add(id);
                                    } else {
                                        button.setSelected(false); // 선택 수 초과 시 선택 해제
                                        JOptionPane.showMessageDialog(null, "최대 좌석 수를 초과했습니다. 좌석 수를 확인해 주세요.", "경고",
                                                JOptionPane.WARNING_MESSAGE);
                                    }
                                    System.out.println("선택된 좌석 ID들: " + selectedSeatIds);
                                } else {
                                    button.setBackground(originalColor); // 선택 해제 시 원래 색상으로 복원
                                    selectedSeatIds.remove(id);
                                }
                            }
                        });
                    }

                    seatButton.setPreferredSize(buttonSize);
                    seatButton.setHorizontalAlignment(SwingConstants.CENTER);
                    seatButton.setVerticalAlignment(SwingConstants.CENTER);
                    seatButton.setForeground(Color.WHITE);
                    seatButton.setFont(new Font("Arial", Font.BOLD, 10));
                    seatButton.setFocusPainted(false); // 선택 시 포커스 효과 제거
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
        JButton previewButton1 = new HoneyButton("좌석 미리보기");
        previewButton1.setBounds(20, 180, 120, 50);
        add(previewButton1);

        JButton previewButton2 = new HoneyButton("좌석 미리보기");
        previewButton2.setBounds(20, 450, 120, 50);
        add(previewButton2);

        JButton previewButton3 = new HoneyButton("좌석 미리보기");
        previewButton3.setBounds(1250, 180, 120, 50);
        add(previewButton3);

        JButton previewButton4 = new HoneyButton("좌석 미리보기");
        previewButton4.setBounds(1250, 450, 120, 50);
        add(previewButton4);

        // 각 미리보기 버튼에 이미지 표시 기능 추가
        previewButton1.addActionListener(e -> showImageInFrame("./UI/images/preview1.png"));
        previewButton2.addActionListener(e -> showImageInFrame("./UI/images/preview3.png"));
        previewButton3.addActionListener(e -> showImageInFrame("./UI/images/preview2.png"));
        previewButton4.addActionListener(e -> showImageInFrame("./UI/images/preview4.png"));

        JButton payButton = new HoneyButton("결제하기");
        payButton.setBounds(1250, 870, 120, 60);
        add(payButton);

        TempLabel = new JLabel("매너 온도가 낮은 좌석은 빨간색으로 표시됩니다.", JLabel.CENTER);
        TempLabel.setForeground(Color.RED);
        TempLabel.setFont(new Font("고딕", Font.BOLD, 16));
        TempLabel.setBounds(450, 900, 500, 30);
        add(TempLabel);
        // 청소년 및 성인 수를 표시할 JLabel 추가
        youthLabel = new JLabel("청소년 수: 0", JLabel.CENTER);
        youthLabel.setForeground(Color.MAGENTA);
        youthLabel.setBounds(20, 870, 200, 30);
        add(youthLabel);

        adultLabel = new JLabel("성인 수: 0", JLabel.CENTER);
        adultLabel.setBounds(20, 890, 200, 30);
        add(adultLabel);
        // 좌석 성인 청소년 수 업데이트
        updateAgeGroupCounts();

        homeButton.addActionListener(e -> {
            new MainForm(userId);
            dispose();
        });

        backButton.addActionListener(e -> {
            new ReservationForm(userId);
            dispose();
        });

        payButton.addActionListener(e -> {
            boolean hasConflicts = false;

            // 좌석 상태를 확인하고 충돌이 있는지 검사
            for (Integer seatId : selectedSeatIds) {
                SeatBean selectedSeat = seatmgr.getSeat(seatId); // 좌석 정보를 가져옴

                if (selectedSeat.isSeatChk()) {
                    hasConflicts = true;
                    break; // 이미 선택된 좌석이 발견되면 루프 종료
                }
            }

            if (hasConflicts) {
                JOptionPane.showMessageDialog(null, "이미 선택된 좌석이 있습니다. 다른 좌석을 선택해 주세요.", "경고",
                        JOptionPane.WARNING_MESSAGE);
                this.dispose(); // 현재 창을 닫음
                new SeatSelectionForm(userId, RSVdocid, RSVcinemaNum, ViewDate, YouthCount, AdultCount); // 새로고침을 위해
                // 페이지를 다시 엶
            } else if (selectedSeatIds.size() != YouthCount + AdultCount) {
                JOptionPane.showMessageDialog(null, "좌석 수가 일치하지 않습니다. 선택된 좌석 수를 확인해 주세요.", "경고",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                // 모든 좌석이 선택 가능한 상태인 경우 결제 처리
                int currentYouthCount = 0;
                int currentAdultCount = 0;

                for (Integer seatId : selectedSeatIds) {
                    RSVbean.setUserId(userId);
                    RSVbean.setDocid(RSVdocid);
                    RSVbean.setViewDate(ViewDate);
                    RSVbean.setCinemaNum(RSVcinemaNum);
                    RSVbean.setSeatId(seatId);
                    RSVbean.setPrice(15000); // 가격 설정
                    // 선택된 좌석에 따라 나이 그룹 설정
                    if (currentYouthCount < YouthCount) {
                        RSVbean.setAgeGroup("청소년");
                        currentYouthCount++;
                    } else {
                        RSVbean.setAgeGroup("성인");
                        currentAdultCount++;
                    }
                    RSVmgr.insertRsvn(RSVbean);
                    seatbean.setSeatId(seatId);
                    RSVSeat += seatId + ",";
                    seatbean.setSeatChk(true); // 좌석을 예약 상태로 변경
                    seatmgr.updateSeatChk(seatbean);
                    double discount = usermgr.SelectDiscount(usermgr.SelectGrade(userId));
                    Amount = usermgr.getpay(userId);
                    Amount += 15000 - (15000 * discount);
                    usermgr.updatepay(Amount, userId);
                    pay = (15000 - (15000 * discount)) * Allcount;
                }

                new ReservationCompleteForm(userId, RSVdocid, RSVcinemaNum, ViewDate, pay, Allcount, RSVSeat); // 예약 완료 화면으로 이동
                dispose(); // 현재 창을 닫음
                try {
                    // 열고자 하는 URL
                    URI url = new URI("http://113.198.238.93/pay/payForm.jsp");

                    // Desktop 객체를 얻어오기
                    Desktop desktop = Desktop.getDesktop();

                    // 웹 브라우저로 URL 열기
                    if (desktop.isSupported(Desktop.Action.BROWSE)) {
                        desktop.browse(url);
                    } else {
                        System.out.println("브라우저 열기를 지원하지 않습니다.");
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        setVisible(true);
    }

    private int getSeatIdByLabel(String seatLabel) {
        Vector<SeatBean> seatlist = seatmgr.listSeat(RSVcinemaNum);
        for (SeatBean seat : seatlist) {
            if (seat.getSeatNum().equals(seatLabel)) {
                return seat.getSeatId();
            }
        }
        return -1; // ID를 찾을 수 없는 경우
    }

    private void updateAgeGroupCounts() {
        // 청소년 및 성인 수 업데이트
        getAgeGroup(RSVcinemaNum);
        // JLabel에 청소년 및 성인 수 표시
        youthLabel.setText("청소년 수: " + cinemaYouth);
        adultLabel.setText("성인 수: " + cinemaAdult);
    }

    private void getAgeGroup(int RSVcinemaNum) {
        cinemaYouth = 0; // 초기화
        cinemaAdult = 0; // 초기화
        Vector<ReservationBean> agelist = RSVmgr.listAgeGroup(RSVcinemaNum);

        for (ReservationBean reservation : agelist) {
            String ageGroup = reservation.getAgeGroup();
            if (ageGroup.contains("청소년")) {
                cinemaYouth++; // 청소년 카운트 증가
            } else if (ageGroup.contains("성인")) {
                cinemaAdult++; // 성인 카운트 증가
            }
        }
    }

    // 이미지를 새로운 JFrame에 표시하는 메소드
    private void showImageInFrame(String imagePath) {
        JFrame imageFrame = new JFrame("미리보기");
        imageFrame.setSize(600, 400); // 이미지 프레임 크기 설정
        imageFrame.setLocationRelativeTo(null); // 화면 중앙에 위치
        imageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창 닫기 설정

        // 이미지 아이콘을 생성
        ImageIcon imageIcon = new ImageIcon(imagePath);

        // 이미지가 null이 아닌지 확인
        if (imageIcon.getImage() != null) {
            JLabel imageLabel = new JLabel(imageIcon);
            imageFrame.add(imageLabel);
        } else {
            JLabel errorLabel = new JLabel("이미지를 불러올 수 없습니다.", JLabel.CENTER);
            imageFrame.add(errorLabel);
        }

        imageFrame.setVisible(true); // 이미지 프레임 표시
    }

    public static void main(String[] args) {
        new SeatSelectionForm(userId, RSVcinemaNum, RSVcinemaNum, ViewDate, YouthCount, AdultCount);
    }
}
