<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
    <!-- section시작 -->
    <section>
       <div class="section no1">
            <div class="first-introduce">
            <img src="<c:url value='/img/main1.jpg'/>" alt="">
            </div>
        </div>
        <div class="section no2">
            <div class="second-introduce">
            <img src="<c:url value='/img/main2.jpg'/>" alt="">
            </div>
        </div>
        <div class="section no3">
            <div class="third-introduce">
            <img src="<c:url value='/img/main3.jpg'/>" alt="">
            </div>
        </div>
        <div class="section no4">
            <div class="forth-introduce">
            <img src="<c:url value='/img/main4.jpg'/>" alt="">
            </div>
        </div>
    </section>
    <!-- section끝 -->
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>

