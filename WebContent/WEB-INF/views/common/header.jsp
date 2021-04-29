<%@page import="member.model.vo.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String msg = (String) session.getAttribute("msg");
	if (msg != null)
		session.removeAttribute("msg");
	
	Member loginMember = (Member) session.getAttribute("loginMember");
	
	String keyword = (String) session.getAttribute("searchKeyword");
	String gKeyword = (String) session.getAttribute("graphKeyword");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Frog Market</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css" />
<script src="<%= request.getContextPath() %>/js/jquery-3.6.0.js"></script>
<script>
	<% if(msg != null) { %> 
		alert("<%= msg %>"); 
	<% } %>
</script>
</head>
<body>
	<!-- header시작 -->
	<header>
		<div class="header-container">
			<div class="head main-title">
				<img src="<%= request.getContextPath() %>/img/frog (1).png" class="header-img" alt="">
				<a href="<%= request.getContextPath() %>"><h1>깨꿀마켓</h1></a>
			</div>
			<div class="head search">
			<%if(request.getRequestURI().equals("/frog/WEB-INF/views/graph/graph.jsp")) {%>
				<form action="<%=request.getContextPath()%>/graph/graphView" method="get">
				<input type="text" placeholder="검색어를 입력" name="header-search" id="header-search" value="<%=gKeyword != null ? gKeyword : ""%>">
				</form>
			<%} else { %>
				<form action="<%=request.getContextPath()%>/market/marketFinder" method="get">
				<input type="text" placeholder="검색어를 입력" name="header-search" id="header-search" value="<%=keyword != null ? keyword : ""%>">
				<%} %>
				</form>
			</div>
			<div class="head menu">
				<div class="head market">
				<%if(keyword != null){ %>
					<a href="<%= request.getContextPath() %>/market/marketFinder?header-search=<%=keyword %>"><h1>마켓 게시판</h1></a>
				<%} else { %>
					<a href="<%= request.getContextPath() %>/market/marketList"><h1>마켓 게시판</h1></a>
				<%} %>
				</div>
				<div class="head graph">
					<a href="<%= request.getContextPath() %>/graph/graphView"><h1>한눈 그래프</h1></a>
				</div>
				<div class="head black-list">
					<a href="<%= request.getContextPath() %>/report/reportList"><h1>신고 게시판</h1></a>
				</div>
				<div class="head login">
					<% if(loginMember == null) { %>
					<input type="button" value="Login" onclick="location.href='<%= request.getContextPath() %>/member/login';">
					<% } else { %>
					<input type="button" value="My Page" onclick="location.href='<%= request.getContextPath() %>/member/memberView';">
					<% } %>
				</div>
			</div>
		</div>
	</header>
	<!-- header끝 -->