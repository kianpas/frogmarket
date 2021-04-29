<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="market.model.vo.Product"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/xml; charset=UTF-8"
	pageEncoding="utf-8"%>
<%
	List<Product> list = (List<Product>) request.getAttribute("productList");
%>

<div class="board"  style="width: 775px;">
	<div class="section-title"><h3>중고거래</h3></div>
	<div class="board-container" style="width: 600px; margin: 0 auto; margin-top: 80px; margin-bottom: 80px;">
		<%
			if (list == null || list.isEmpty()) {
		%>
		<p style="margin: 180px 0;">조회된 게시글이 없습니다.</p>
		<%
			} else {
		for (int i = 0; i < list.size(); i++) {
			Product p = list.get(i);
		%>
		<div class="board-box1" style="cursor: pointer;"
			onclick="location.href='<%=request.getContextPath()%>/market/marketView?no=<%=p.getNo()%>';">
			<div class="thumbnail">
				<%
					if (p.getAttach() != null) {
				%>
				<img
					src="<%=request.getContextPath()%>/upload/market/<%=p.getAttach().getRenamedFileName()%>"
					alt="<%=p.getTitle()%>" width="270px" height="160px">
				<%
					}
				%>
			</div>
			<div class="product-title">
				<span><%=p.getTitle()%></span>
				<%if(("new").equals(p.getStatus())){ %>
				<p>판매중</p>
				<%}else if(("reserved").equals(p.getStatus())){ %>
				<p style="color: red;">예약중</p>
				<% }else{ %>
				<p style="color: #b0b0b0;">판매완료</p>
				<% } %>
				<p><%=p.getPrice()%>￦</p>
			</div>
		</div>
		<%
			}
		}
		%>
	</div>
</div>