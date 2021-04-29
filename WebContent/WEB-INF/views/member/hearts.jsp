<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="member.model.vo.Member"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/xml; charset=UTF-8"
	pageEncoding="utf-8"%>
<%
	Member m = (Member) request.getAttribute("heartMember");
%>

<Hearts>
	<heart>
		<goodScore><%= m.getGoodScore() %></goodScore>
	</heart>
</Hearts>