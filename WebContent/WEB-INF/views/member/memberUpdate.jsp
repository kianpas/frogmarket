<%@page import="member.model.vo.Member"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Member member = (Member)request.getAttribute("member");
%>
<!DOCTYPE html>
<html>
<head>
<title>Frog Market</title>
<link rel="stylesheet"
	href="<%= request.getContextPath() %>/css/style.css" />
<script src="<%= request.getContextPath() %>/js/jquery-3.6.0.js"></script>
</head>
<body>
<h1 class="mini-title">Update Profile</h1>
    <div class="update-container">
        <form 
        class="update-form" 
        name="updateFrm"
        action="<%= request.getContextPath() %>/member/memberUpdate"
        method="post">
        <label for="">닉네임</label><input type="text" name="nickId" id="nickId" value="<%= member.getNickId() %>">
        <label for="">비밀번호</label><input type="text" name="password" id="password" value="<%= member.getPassword() %>">
        <label for="">비밀번호 확인</label><input type="text" name="newPassword" id="newPassword" value="<%= member.getPassword() %>">
        <label for="">이메일</label><input type="email" name="email" id="email" value="<%= member.getEmail() %>">
        <label for="">전화번호</label><input type="tel" name="phone" id="phone" value="<%= member.getPhone() %>">
		 <hr>
        <div class="btns updateBtns">
            <input type="button" value="cancel" onclick="cancelFunction()">
            <input type="submit" value="submit">
        </div>
        </form>
    </div>

	<script>
	$(document.updateFrm).submit(function(){
		
		var $password = $("#password");
		var $newPassword = $("#newPassword");
		
		if($newPassword.val() != $password.val()){
			alert("입력한 비밀번호가 일치하지 않습니다.");
			$newPassword.select();
			return false;
		}
		
	});

	function cancelFunction() {
		alert("회원 정보 수정을 취소하셨습니다.");
        location.href="/frog/index.jsp";
    }    
	</script>
</body>
</html>