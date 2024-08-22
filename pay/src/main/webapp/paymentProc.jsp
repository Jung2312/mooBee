<%@ page contentType="text/html; charset=UTF-8"%>
<%
    request.setCharacterEncoding("UTF-8");

    // 파라미터를 가져와서 처리
    String applyNumStr = request.getParameter("apply_num");
    String paidAmountStr = request.getParameter("paid_amount");

    int applynum = 0;
    int paid_amount = 0;

    // 예외 처리를 위한 기본값 설정
    try {
        if (applyNumStr != null && !applyNumStr.trim().isEmpty()) {
            applynum = Integer.parseInt(applyNumStr);
        }
        if (paidAmountStr != null && !paidAmountStr.trim().isEmpty()) {
            paid_amount = Integer.parseInt(paidAmountStr);
        }
    } catch (NumberFormatException e) {
        // 로그에 에러를 기록하거나 사용자에게 오류 메시지를 보여줄 수 있습니다.
        e.printStackTrace();
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>결제 완료</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #fff7e5;
            color: #333;
            text-align: center;
            padding: 50px;
        }
        .container {
            background-color: #ffffff;
            border: 2px solid #f7c20d;
            border-radius: 8px;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            display: inline-block;
            max-width: 600px;
            margin: auto;
        }
        h2 {
            color: #f7c20d;
        }
        .button {
            background-color: #f7c20d;
            color: #333;
            border: none;
            border-radius: 4px;
            padding: 10px 20px;
            cursor: pointer;
            font-size: 1em;
            margin-top: 20px;
            text-decoration: none;
        }
        .button:hover {
            background-color: #f2b505;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>결제 완료</h2>
        <p>결제가 성공적으로 완료되었습니다.</p>
        <p>카드 승인 번호: <%= applynum %></p>
        <p>승인 금액: <%= paid_amount %> 원</p>
        <a href="<%=request.getContextPath()%>/payForm.jsp" class="button">홈으로 돌아가기</a>
    </div>
</body>
</html>
