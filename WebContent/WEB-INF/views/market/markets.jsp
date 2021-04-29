<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="market.model.vo.Product"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/xml; charset=UTF-8" 
	pageEncoding="utf-8" %>
<%
	List<Product> list = (List<Product>) request.getAttribute("productList"); 
%>
<Products>
<% 	for(Product p : list){ %>
	<product>
		<no><%= p.getNo() %></no>
		<id><%= p.getId() %></id>
		<title><%= p.getTitle() %></title>
		<status><%= p.getStatus() %></status>
		<price><%= p.getPrice() %></price>
		<description><%= p.getDescription() %></description>
		<regDate><%= p.getRegDate() %></regDate>
		<area><%= p.getArea() %></area>
		<attach><%= p.getAttach() %></attach>
	</product>
<% 	} %>
</Products>