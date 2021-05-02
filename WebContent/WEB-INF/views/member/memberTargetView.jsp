<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="/WEB-INF/views/common/header.jsp"/>


<!-- section시작 -->
<section class="member-container">
	<div class="my-container">
		<!-- 프로필뷰 시작 -->
		<div class="my-profile-view">
			<div class="my-profile-icon">
				<div class="my-icon-img">
				  <c:choose>
				  <c:when test="${empty targetMember.icon}">
				
					<img src="<c:url value="/img/icon1.jpg"/>" alt="">
					</c:when>
					<c:otherwise>
					
					<img src="<c:url value="/img/${targetMember.icon}"/>" alt="">
					
					</c:otherwise>
					</c:choose>
				</div>
				<div class="my-profile-nick">
					<h3>${targetMember.nickId}</h3>
				</div>
			</div>
			<div class="my-mini-container">
				<div class="my-profile-good">
					<div class="heart" onclick="addHeart();" style="cursor: pointer;">
						<img src="<c:url value="/img/free-icon-hearts-138533.png"/>" alt="">
					</div>
					<h3> : ${targetMember.goodScore}</h3>
				</div>
				<div class="my-profile-market-count">
					<h3>아이디 : @${targetMember.memberId}</h3>
				</div>
			</div>
		</div>
		
		<!-- 프로필뷰 끝 -->
		<!-- 사이드 메뉴판 시작 -->
		<div class="my-profile-menu">
			<h2>상품</h2>
			<p onclick="getProduct();" style="cursor: pointer;">상품 목록</p>
		</div>
		<!-- 사이드 메뉴판 끝 -->
		<!-- 게시판 시작 -->
		<div class="board my-board">
			
		</div>
		<!-- 게시판 끝 -->
	</div>
</section>
<!-- section끝 -->
<form action="<c:url value="/member/cartList"/>" method="post" name="cartListFrm">
	<input type="hidden" name="memberId" value="honggd"/>
</form>
<script>

	function getProduct(){

		$.ajax({
			url: "<c:url value='/market/marketTarget'/>",
			data: { "memberId" : "${targetMember.memberId}"},
			dataType: "text",
			success : function(data) {
				console.log(data);
				$(".my-board").html('');
				$(".my-board").append(data);
				},
			error : function(xhr, status,err) {
				console.log(xhr, status,err);
				}
			});
	};

</script>

<!-- 관리자 회원 목록 ajax -->
<script>

function addHeart(){
	var $id = "${targetMember.memberId}";
	$.ajax({
		url: "<c:url value='/member/AddHeart'/>",
		data: {"memberId" : $id},
		success : function(data) {
			
			var $root = $(data).find(":root");
			var $hearts = $root.find("heart");
			var gScore = $hearts.children("goodScore").text();
			console.log(gScore);
			var $goodScore = $(".my-profile-good").children("h3").html("");
			
			$goodScore.append(" : "+gScore);
			
			},
		error : function(xhr, status,err) {
			console.log(xhr, status,err);
			}
		});
};

</script>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
