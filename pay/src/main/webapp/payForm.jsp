<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>결제시스템</title>
<style>
    body {
        font-family: 'Arial', sans-serif;
        background-color: #ffffff; /* 하얀색 배경 */
        color: #333;
        text-align: center;
        margin: 0;
        padding: 0;
    }
    h2 {
        color: #f7c20d; /* 꿀벌 노란색 */
        font-size: 2em;
        margin: 20px 0;
    }
    form {
        display: inline-block;
        text-align: left;
        border: 2px solid #f7c20d;
        border-radius: 8px;
        padding: 20px;
        background-color: #ffeb3b; /* 밝은 노란색 배경 */
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
    }
    span {
        width: 80px;
        display: inline-block;
        color: #333;
        font-weight: bold;
    }
    input[type="text"], input[type="email"] {
        width: 250px;
        padding: 5px;
        border: 1px solid #f7c20d;
        border-radius: 4px;
        margin-bottom: 10px;
    }
    input[type="submit"], input[type="reset"] {
        background-color: #f7c20d; /* 꿀벌 노란색 */
        color: #333;
        border: none;
        border-radius: 4px;
        padding: 10px 20px;
        cursor: pointer;
        font-size: 1em;
        margin: 5px;
    }
    input[type="submit"]:hover, input[type="reset"]:hover {
        background-color: #f2b505; /* 조금 더 어두운 노란색 */
    }
    .bee-icon {
        display: block;
        margin: 20px auto;
        width: 200px; /* 이미지 크기 키우기 */
    }
</style>
</head>
<body>
    <img src="MooBee_Logo.png" alt="Bee Icon" class="bee-icon"> <!-- 꿀벌 아이콘 이미지 URL로 변경 -->
    <form action="payProc.jsp" method="post">
        <span>이름</span><input name="name" value="MooBee" type="text"><br> 
        <span>이메일</span><input name="email" value="MooBee@naver.com" type="email"><br>
        <span>전화번호</span><input name="phone" value="010-1234-1234" type="text"><br>
        <span>주소</span><input name="address" value="부산광역시 부산진구 엄광로 176 동의대학교" type="text"><br>
        <span>결제금액</span><input name="totalPrice" value="15000" type="text"><br> 
        <input type="submit" value="결제하기"> 
        <input type="reset" value="취소하기">
    </form>
</body>
</html>
