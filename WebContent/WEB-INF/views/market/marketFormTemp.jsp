<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<section id="form-container">
<h2>게시판 작성</h2>
<form
	name="marketEnrollFrm"
	action="<%=request.getContextPath() %>/market/marketEnroll" 
	method="post" enctype="multipart/form-data">
	<table id="tbl-market-view">
		<tr>
			<th>원래 이자리에 있어야 하는데 밀려서 안보이는 부분</th>
			<td><input type="text" name="wastitle" ></td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>
				<%-- <input type="text" name="writer" value="<%=loginMember.getMemberId() %>" readonly/> --%>
				<input type="text" name="writer" value="">
			</td>
		</tr>
		<tr>
			<th>상품제목</th>
			<td><input type="text" name="title" required></td>
		</tr>
		<tr>
			<th>상품상태</th>
			<td><input type="text" name="status" value="new" readonly></td>
		</tr>
		<tr>
			<th>가 격</th>
			<td><input type="number" name="price" required></td>
		</tr>
		<tr>
			<th>내 용</th>
			<td><textarea rows="5" cols="40" name="content"></textarea></td>
		</tr>
		<tr>
			<th>지 역</th>
			<td><input type="text" name="area" required></td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td>			
				<input type="file" name="upFile">
			</td>
		</tr>
		
		<tr>
			<th colspan="2">
				<input type="submit" value="등록하기">
			</th>
		</tr>
	</table>
</form>
</section>
   
<%@ include file="/WEB-INF/views/common/footer.jsp" %>