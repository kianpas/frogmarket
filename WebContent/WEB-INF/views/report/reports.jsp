<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="report.model.vo.Report"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/xml; charset=UTF-8" 
	pageEncoding="utf-8" %>
<%
	List<Report> list = (List<Report>) request.getAttribute("reportList"); 
%>
<Reports>
<% 	for(Report report : list){ %>
	<report>
		<no><%= report.getReportNo() %></no>
		<id><%= report.getMemberId() %></id>
		<target><%= report.getMemberReportId() %></target>
		<date><%= report.getRegDate() %></date>
		<content><%= report.getContent() %></content>
	</report>
<% 	} %>
</Reports>