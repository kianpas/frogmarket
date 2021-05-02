<%@page import="market.model.vo.Product"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="ko_kr" />
<jsp:include page="/WEB-INF/views/common/header.jsp" />
<%
String searchKeyword = (String) request.getAttribute("header-search");
%>
<!-- section시작 -->
<section>
	<div class="section-body">
		<div class="board">
			<div class="section-title">
				<h3>중고거래${sessionScope.searchKeyword}</h3>
			</div>
			<c:choose>
				<c:when test="${empty list}">
					<p style="margin: 180px 0;">조회된 게시글이 없습니다.</p>
				</c:when>
				<c:otherwise>
					<c:forEach items="${list}" var="p">
						<div class="board-box1" style="cursor: pointer;"
							onclick="location.href='<%= request.getContextPath()%>/market/marketView?no=${p.no}';">
							<div class="thumbnail">
								<c:if test="${not empty p.attach}">
									<img
										src="<c:url value='/upload/market/${p.attach.renamedFileName}'/>"
										alt="${p.title}">
									<div class="product-title">
										<span>${p.title}</span>
										<c:if test="${p.status eq 'new'}">
											<p>판매중</p>
										</c:if>
										<c:if test="${p.status eq 'reserved'}">
											<p style="color: red;">예약중</p>
										</c:if>
										<c:if test="${p.status eq 'soldout'}">
											<p style="color: #b0b0b0;">판매완료</p>
										</c:if>
										<p>
											<fmt:formatNumber value="${p.price}" type="currency" />
										</p>
									</div>
								</c:if>
							</div>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
			<div class="more">${pageBar}</div>
		</div>
	</div>
</section>
<!-- section끝 -->
<!-- 게시글 쓰기 이동 버튼 시작 -->
<div class="post-btn"
	onclick="location.href='<c:url value="/market/marketForm"/>';"
	style="cursor: pointer;">
	<img src="<c:url value="/img/add.png"/>">
</div>
<!-- 게시글 쓰기 이동 버튼 끝 -->
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
