<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.Date"%>
<%@page import="member.model.vo.Member"%>
<%@page import="member.model.service.MemberService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	Member member = (Member)request.getAttribute("loginMember");
	List<Member> alist = (List<Member>)request.getAttribute("adminlist");
%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>

<!-- section시작 -->
<section>
	<div class="my-container">
		<!-- 프로필뷰 시작 -->
		<div class="my-profile-view">
			<div class="my-profile-icon">
				<div class="my-icon-img">
					<% if(loginMember.getIcon() == null || loginMember.getIcon().isEmpty()){ %>
					<img src="<%= request.getContextPath() %>/img/icon1.jpg" alt="">
					<% } else { %>
					<img src="<%= request.getContextPath() %>/img/<%= loginMember.getIcon() %>" alt="">
					<% } %>
				</div>
				<div class="my-profile-nick">
					<h3><%= loginMember.getNickId() %></h3>
				</div>
			</div>
			<div class="my-mini-container">
				<div class="my-profile-good">
					<div class="heart">
						<img src="<%= request.getContextPath() %>/img/free-icon-hearts-138533.png" alt="">
					</div>
					<h3> : <%= loginMember.getGoodScore() %></h3>
				</div>
				<div class="my-profile-market-count">
					<h3>아이디 : @<%= loginMember.getMemberId() %></h3>
				</div>
			</div>
		</div>
		
		<!-- 프로필뷰 끝 -->
		<!-- 사이드 메뉴판 시작 -->
		<div class="my-profile-menu">
			<h2>회원 정보</h2>
			<form id="memberLogoutFrm" method="get">
				<p onclick="logoutMember();" style="cursor: pointer; color: red;">로그아웃</p>
        		<input type="hidden" name="memberId" value="<%= loginMember.getMemberId() %>"/>
        	</form>
			<form id="memberViewUpdateFrm" method="get">
				<p onclick="updateMember();" style="cursor: pointer;">회원정보 수정</p>
        		<input type="hidden" name="memberId" value="<%= loginMember.getMemberId() %>"/>
        	</form>
			<form id="memberDeleteFrm" method="post">
				<p onclick="deleteMember();" style="cursor: pointer;">회원 탈퇴</p>
        		<input type="hidden" name="memberId" value="<%= loginMember.getMemberId() %>"/>
        	</form>
			
			
			<h2>상품</h2>
			<p onclick="noticeList();" style="cursor: pointer; color: red;">댓글 알림</p>
			<form id="insertProductFrm" method="get">
				<p onclick="insertProduct();" style="cursor: pointer;">상품등록</p>
        	</form>
			<p onclick="getProduct();" style="cursor: pointer;">내 상품 목록</p>
			<form id="showCartListFrm" method="post">
				<p onclick="showCartList();" style="cursor: pointer;">장바구니</p>
        		<input type="hidden" name="memberId" value="<%= loginMember.getMemberId() %>"/>
        	</form>
			
			<% if(MemberService.ADMIN_ROLE.equals(loginMember.getMemberRole())) { %>
				<h2>관리자</h2>
				<p onclick="getMemberByAdmin();" style="cursor: pointer;">회원 목록</p>
			<% } %>
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
	<input type="hidden" name="memberId" value="<%= loginMember.getMemberId() %>"/>
</form>
<script>

	function logoutMember(){
		alert("로그아웃합니다.");
		$("#memberLogoutFrm")
			.attr("action","<%=request.getContextPath()%>/member/memberLogout")
			.submit();
	}

	function updateMember(){
		$("#memberViewUpdateFrm")
			.attr("action","<%=request.getContextPath()%>/member/memberUpdate")
			.submit();
	}

	function deleteMember(){
		 if(confirm("정말로 탈퇴를 진행하시겠습니까?")){
			 $("#memberDeleteFrm")
				.attr("action","<%=request.getContextPath()%>/member/memberDelete")
				.submit();
		  }
	}

	function showCartList(){
		$("#showCartListFrm").attr("action", "<%= request.getContextPath() %>/member/cartList")
		.submit();
	}
	

	function insertProduct(){
		$("#insertProductFrm").attr("action", "<%= request.getContextPath() %>/market/marketForm")
		.submit();
	}
	
</script>

<!-- 관리자 회원 목록 ajax -->
<script>
function getMemberByAdmin(){

	$.ajax({
		url: "<%=request.getContextPath()%>/member/XmlMemberList",
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

function getProduct(){

	$.ajax({
		url: "<%=request.getContextPath()%>/market/XmlProductList",
		data: { "memberId" : "<%= loginMember.getMemberId() %>"},
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

function noticeList(){
	$.ajax({
		url: "<%= request.getContextPath()%>/notice/noticeList",
		method: "get",
		data: {
			memberId : "<%= loginMember.getMemberId() %>"
		},
		
		success: function(data){
			//console.log(data);
			var $table = $("<table></table>");
			var url = '<%=request.getContextPath() %>/market/marketView?no=';
			
			$(data).each(function(index,obj){
				var no = obj.no;
				var boardNo = obj.boardNo;
				var senderId = obj.senderId;
				var senderNick = obj.senderNick;
				var receiverId = obj.receiverId;
				var title = obj.title;
				var content = obj.content;
				var tr = "<tr>";
				tr += "<td>" + no + "</td>";
				tr += "<td>" + boardNo + "</td>";
				tr += "<td>" + senderId + "</td>";
				tr += "<td>" + senderNick + "</td>";
				tr += "<td>" + receiverId + "</td>";
				tr += '<td><a href="'+ url + boardNo + '" style="color: #8ebf42">' + title + '</a></td>';
				tr += "<td>" + content + "</td>";
				tr +='<td><input type="button" value="삭제" name="'+no+'"></td>';
				tr +="</tr>";
				$table.append(tr);
			});
			
			$table.css({"border" : "1px solid #444444"});
			$table.find('td').css({"border" : "1px solid #444444"});

			$(".my-board").empty();
			$(".my-board").append($table);
			
		},
		error: function(xhr, status, err){
			console.log(xhr, status, err);
		}
	});
}
		
	//동적 생성 버튼 감지
	$(document).on('click',':button', function(){
		var $tr=$(this);
		console.log($tr.attr('name'));
	
		$.ajax({
			url: "<%= request.getContextPath()%>/notice/noticeDelete",
			method: "get",
			data: {
				no : $tr.attr('name')
			},
			
			success: function(){
				$tr.parent().parent().remove();
				
			},
			error: function(xhr, status, err){
				console.log(xhr, status, err);
			}
		});		
	});

</script>

<%@ include file="/WEB-INF/views/common/footer.jsp" %>