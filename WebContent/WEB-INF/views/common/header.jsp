<%@page import="member.model.vo.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String msg = (String) session.getAttribute("msg");
if (msg != null)
	session.removeAttribute("msg");

String keyword = (String) session.getAttribute("searchKeyword");
String gKeyword = (String) session.getAttribute("graphKeyword");
%>
<c:set var="path" value="${requestScope['javax.servlet.forward.servlet_path']}" /> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Frog Market</title>
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap" rel="stylesheet">
<link rel="stylesheet"
	href="<c:url value="/css/style.css"/>" />
<script src="<c:url value="/js/jquery-3.6.0.js"/>"></script>
<script>
	<%if (msg != null) {%> 
		alert("<%=msg%>
	");
<%}%>
	
</script>
</head>
<body>
	<!-- header시작 -->
	<header>
		<div class="header-container">
			<div class="head main-title">
				<img src="<c:url value="/img/frog (1).png"/>" class="header-img"
					alt=""><a href="<c:url value="/"/>"><h1>깨꿀마켓</h1></a>
			</div>
			<div class="head search">
			<c:choose>
			<c:when test="${path eq '/graph/graphView'}">
				<form action="<c:url value="/graph/graphView"/>" method="get">
					<input type="text" placeholder="검색어를 입력" name="header-search"
						id="header-search" value="${sessionScope.searchKeyword}">
				</form>
				</c:when>
				<c:otherwise>
				<form action="<c:url value="/market/marketFinder"/>" method="get">
					<input type="text" placeholder="검색어를 입력" name="header-search"
						id="header-search" value="${sessionScope.searchKeyword}">
				</form>
				</c:otherwise>
				</c:choose>
			</div>
			<div class="head menu">
				<div class="head market">
					<c:if test="${not empty searchKeyword}">
						<a
							href="<c:url value='/market/marketFinder?header-search=${searchKeyword}'/>"><h1>마켓
								게시판</h1></a>
					</c:if>
					<c:if test="${empty searchKeyword}">
						<a href="<c:url value='/market/marketList'/>"><h1>마켓 게시판</h1></a>
					</c:if>

				</div>
				<div class="head graph">
					<a href="<c:url value='/graph/graphView'/>"><h1>한눈 그래프</h1></a>
				</div>
				<div class="head black-list">
					<a href="<c:url value='/report/reportList'/>"><h1>신고 게시판</h1></a>
				</div>
				<div class="head login">
					<c:choose>
						<c:when test="${loginMember!=null}">
							<input type="button" value="My Page"
								onclick="location.href='<c:url value="/member/memberView"/>';">
						</c:when>
						<c:otherwise>
							<input type="button" value="Login"
								onclick="location.href='<c:url value="/member/login"/>';">
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		</div>
	</header>
	<script type="text/javascript">
	
	</script>
	<!-- header끝 -->