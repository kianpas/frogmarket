<%@page import="market.model.vo.Product"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/common/header.jsp"%>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
<%
List<Product> list = (List<Product>) request.getAttribute("list");
%>
<section>
	<div class="section-body">
		<div class="board">
			<div class="section-title">
				<h3>장바구니</h3>
			</div>
			<c:choose>
				<c:when test="${empty list}">
					<p style="margin: 120px 0;">조회된 게시글이 없습니다.</p>
				</c:when>
				<c:otherwise>
					<c:forEach items="${list}" var="p">
						<div style="display: inline-block;">
							<div class="board-box1" style="cursor: pointer;"
								onclick="location.href='<c:url value="/market/marketView?no=${p.no}"/>';">
								<div class="thumbnail">
									<c:if test="${not empty p.attach}">
										<img
											src="<c:url value="/upload/market/${p.attach.renamedFileName}"/>"
											width="270px" height="160px">
									</c:if>
								</div>
								<div class="product-title">
									<p>${p.title}글번호 ${p.no}</p>
									<input type="hidden" id="boardNo" value="${p.no}" />
									<h3>￦ ${p.price}</h3>
								</div>
							</div>
							<div>
								<button value="${p.no}"
									style="width: 100px; border-radius: 80px; background-color: #fff; border: 1px solid #d4d4d4;"
									class="btn-delete">삭제</button>
							</div>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
			<div class="more">${pageBar}</div>
		</div>
	</div>
</section>
<form action="<c:url value="/member/deleteCart"/>" method="post"
	name="deleteCartFrm">
	<input type="hidden" name=id value="${loginMember.memberId}" /> <input
		type="hidden" name=no value="" />
</form>
<!-- section끝 -->
<!-- 게시글 쓰기 이동 버튼 시작 -->
<div class="post-btn"
	onclick="location.href='<c:url value="/market/marketForm"/>';"
	style="cursor: pointer;">
	<img src="<c:url value="/img/add.png"/>">
</div>
<!-- 게시글 쓰기 이동 버튼 끝 -->

<script>
	$(".btn-delete").click(function() {
		var $frm = $(document.deleteCartFrm);
		var boardNo = $(this).val();
		$frm.find("[name=no]").val(boardNo);
		$frm.submit();

	})
</script>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
