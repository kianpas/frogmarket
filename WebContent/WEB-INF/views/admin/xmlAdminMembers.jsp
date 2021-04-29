<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="member.model.vo.Member"%>
<%@page import="member.model.service.MemberService"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/xml; charset=UTF-8"
	pageEncoding="utf-8"%>
<%
	List<Member> alist = (List<Member>) request.getAttribute("adminlist");
%>
<style>
	table#tbl-member th, table#tbl-member td {border:1px solid gray; padding:10px 20px; }
</style>
<div class="section-title my-board-title"><h2>회원 관리 목록</h2></div>
<div class="my-container">

	<table id="tbl-member" style="margin: 0 80px;">
		<thead>
			<tr>
				<th>아이디</th>
				<th>비밀번호</th>
				<th>회원권한</th>
				<th>회원삭제</th>
			</tr>
		</thead>
		<tbody>
		<% if(alist == null || alist.isEmpty()){ %>
			<tr>
				<td colspan="10" style="text-align:center;"> 조회된 회원이 없습니다. </td>
			</tr>
		<% 
		   } 
		   else {
			   for(Member m : alist){
		%>
			<tr>
				<td><%= m.getMemberId() %></td>
				<td><%= m.getPassword() %></td>
				<td>
					<select class="member-role" data-member-id="<%= m.getMemberId() %>">
						<option 
							value="<%= MemberService.ADMIN_ROLE %>"
							<%= MemberService.ADMIN_ROLE.equals(m.getMemberRole()) ? "selected" : ""  %>>관리자</option>
						<option 
							value="<%= MemberService.MEMBER_ROLE %>"
							<%= MemberService.MEMBER_ROLE.equals(m.getMemberRole()) ? "selected" : ""  %>>일반</option>
					</select>
				</td>
				<td>
				<form id="memberDelFrm" method="post">
					<input type="button" value="회원 삭제" onclick="deleteMember();">
	        		<input type="hidden" name="memberId" value="<%= m.getMemberId() %>"/>
        		</form>
				</td>
			</tr>
		<% 
			   }
		   } 
		%>
		</tbody>
	</table>

</div>
<form 
	action="<%= request.getContextPath() %>/admin/memberRoleUpdate" 
	name="memberRoleUpdateFrm"
	method="POST">
	<input type="hidden" name="memberId" />
	<input type="hidden" name="memberRole" />
</form>
<script>

	$(".member-role").on("change", function(){
		var memberId = $(this).data("memberId");
		var memberRole = $(this).val();
		var memberRoleStr = (memberRole == "<%=MemberService.ADMIN_ROLE%>" ? "관리자" : "일반");
		if(confirm("[" + memberId + "] 회원의 권한을 [" + memberRoleStr + "]로 변경하시겠습니까?")){
			var $frm = $(document.memberRoleUpdateFrm);
	 		$frm.find("[name=memberId]").val(memberId);
	 		$frm.find("[name=memberRole]").val(memberRole);
	 		$frm.submit();
		}
		else {
			$(this).children("[selected]").prop("selected", true);
		}
	});

	function deleteMember(){
		if(confirm("정말로 회원 탈퇴를 진행하시겠습니까?")) {
			$("#memberDelFrm")
			.attr("action","<%=request.getContextPath()%>/member/memberDelete")
			.submit();
		}
	}
	
</script>

<%-- <Members>
<% 	for(Member m : list){ %>
	<member>
		<id><%= m.getMemberId() %></id>
		<password><%= m.getPassword() %></password>
		<memberRole><%= m.getMemberRole() %></memberRole>
		<email><%= m.getEmail() %></email>
		<phone><%= m.getPhone() %></phone>
		<enollDate><%= m.getEnrollDate() %></enrollDate>
		<nickId><%= m.getNickId() %></nickId>
		<goodScore><%= m.getGoodScore() %></goodScore>
		<icon><%= m.getIcon() %></icon>
	</member>
<% 	} %>
</Members> --%>
