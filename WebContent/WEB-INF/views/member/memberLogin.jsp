<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Frog Market</title>
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/css/style.css" />
	
<script src="<%= request.getContextPath() %>/js/jquery-3.6.0.js"></script>

</head>
<body>
	<h1 class="mini-title">LOGIN</h1>
	<div class="login-container">
		<div class="profile-icon">
			<img src="<%= request.getContextPath() %>/img/user.png" alt="">
		</div>
		<form id="loginForm" class="login-form" action="<%= request.getContextPath() %>/member/login" method="post">
			<div class="login-icon login-id">
				<input name="loginId" type="text" id="LoginId" placeholder="아이디" onKeypress="if(event.keyCode == 13) loginFunction()">
			</div>
			<div class="login-icon login-pwd">
				<input name="loginPw" type="text" id="LoginPw" placeholder="비밀번호" onkeypress="if(event.keyCode == 13) loginFunction()">
			</div>
		<hr>
		<div class="btns">
			<input type="button" name="cancle" id="cancelButton" value="cancel" onclick="cancelFunction()"> 
			<input type="button" name="" id="loginButton" value="submit" onclick="loginFunction()">
		</div>
		</form>
	</div>
	<div class="go-account">
		<a href="<%= request.getContextPath() %>/member/account">회원가입 하러가기</a>
	</div>
</body>

<script>
	
	// loginform submit
	function loginFunction() {
		let frm = document.getElementById('loginForm');
		let LoginId = document.getElementById('LoginId').value;
		let LoginPw = document.getElementById('LoginPw').value;
		console.dir(frm);
		/*유효성검사*/
		if (!LoginId) {
        alert("아이디를 입력해 주십시오.");
        frm.LoginId.focus();
        return;
    	}
 
   		if (!LoginPw) {
        alert("비밀번호를 입력해 주십시오.");
        frm.LoginPw.focus();
        return;
    	}
   		frm.submit();
   		
	}
	
	function cancelFunction() {
        location.href="/frog/index.jsp";
    }    
</script>
</html>