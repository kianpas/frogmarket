<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Date"%>
<%@page import="member.model.vo.Member"%>
<%@page import="member.model.service.MemberService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	Member member = (Member)request.getAttribute("targetMember");
%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>

<!-- section시작 -->
<section>
	<div class="my-container">
		<!-- 프로필뷰 시작 -->
		<div class="my-profile-view">
			<div class="my-profile-icon">
				<div class="my-icon-img">
					<% if(member.getIcon() == null || member.getIcon().isEmpty()){ %>
					<img src="<%= request.getContextPath() %>/img/icon1.jpg" alt="">
					<% } else { %>
					<img src="<%= request.getContextPath() %>/img/<%= member.getIcon() %>" alt="">
					<% } %>
				</div>
				<div class="my-profile-nick">
					<h3><%= member.getNickId() %></h3>
				</div>
			</div>
			<div class="my-mini-container">
				<div class="my-profile-good">
					<div class="heart" onclick="addHeart();" style="cursor: pointer;">
						<img src="<%= request.getContextPath() %>/img/free-icon-hearts-138533.png" alt="">
					</div>
					<h3> : <%= member.getGoodScore() %></h3>
				</div>
				<div class="my-profile-market-count">
					<h3>아이디 : @<%= member.getMemberId() %></h3>
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
<form action="<%= request.getContextPath() %>/member/cartList" method="post" name="cartListFrm">
	<input type="hidden" name="memberId" value="honggd"/>
</form>
<script>

	function getProduct(){

		$.ajax({
			url: "<%=request.getContextPath()%>/market/marketTarget",
			data: { "memberId" : "<%= member.getMemberId() %>"},
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
	var $id = "<%= member.getMemberId() %>";
	$.ajax({
		url: "<%=request.getContextPath()%>/member/AddHeart",
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

<%@ include file="/WEB-INF/views/common/footer.jsp" %>