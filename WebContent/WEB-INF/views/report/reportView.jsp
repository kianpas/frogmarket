<%@page import="member.model.vo.Member"%>
<%@page import="report.model.vo.Report"%>
<%@page import="report.model.vo.RAttach"%>
<%@page import="member.model.service.MemberService"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/lightbox2/2.11.1/css/lightbox.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/lightbox2/2.11.1/js/lightbox.min.js"></script>
<%
	Report report = (Report)request.getAttribute("report");
	Member member = (Member)request.getAttribute("member");
	List<RAttach> attachList = (List<RAttach>)request.getAttribute("attachList");

%>

  <!-- section시작 -->
    <section>
        <div class="post-container">
            <div class="report-img-container">
                <div class="slideshow-container">
                	<% if(attachList == null || attachList.isEmpty()){ %>
                		<h1 style="margin: 0 auto; padding: 300px 0 0 0; text-align: center;">No Photo</h1>
                	<% }else{ 
                		int slidePageNo = 1;
                		for(RAttach attach : attachList){ %>
		                <div class="mySlides fade">
		                	<a href="<%=request.getContextPath() %>/upload/report/<%=attach.getRenamedFileName() %>" 
		                		data-lightbox="example-set">
		                		<img src="<%=request.getContextPath() %>/upload/report/<%=attach.getRenamedFileName() %>">
		                	</a>
		                </div>
						<% } %>
		                <a class="prev" onclick="plusSlides(-1)">&#10094;</a>
						<a class="next" onclick="plusSlides(1)">&#10095;</a>
						
		                <div style="text-align:center; margin: 20px 0;">
		               <c:forEach items="${attachList}" var="attach">
							<span class="dot" onclick="currentSlide(<%= slidePageNo++ %>)"></span> 
						</c:forEach>
						</div>
               		<% } %>
				</div>
                <br>
       	        
            </div>
            <div class="reporter-title">
            	<form id="targetMemberFrm" method="get" style="margin: 0; padding: 0;">
	                <div class="reporter-icon" onclick="targetPage();" style="cursor: pointer;">
	                <c:choose>
	                <c:when test="${empty member.icon}">
						<img src="<%= request.getContextPath() %>/img/icon1.jpg" alt="">
						</c:when>
						<c:otherwise>
						<img src="<%= request.getContextPath() %>/img/${member.icon}" alt="">
						</c:otherwise>
						</c:choose>
						<input type="hidden" name="memberId" value="${member.memberId}"/>
	                </div>
                </form>
                <div class="reporter-profile">
                    <div class="reporter-profile-info">
                        <h3>${member.nickId}</h3>
                    </div>
                    <div class="target-profile-info">
                        <h3>신고 대상 : ${report.memberReportId}</h3>
                    </div>
                </div>
            </div>
            <div class="commet-container">
                <span>${report.content}</span>
            </div>
<c:if test="${not empty loginMember and loginMember.memberId eq member.memberId or loginMember.memberRole eq MemberService.ADMIN_ROLE}"> 
            <div class="market-up-del-container">
                <input type="button" value="삭제" onclick="deleteReport()">
            </div>
</c:if>
            
        </div>
    </section>
    <!-- section끝 -->
    

<c:if test="${not empty loginMember and loginMember.memberId eq member.memberId or loginMember.memberRole eq MemberService.ADMIN_ROLE}">
	<form 
		action="<c:url value="/report/reportDelete"/>" 
		method="post"
		name="reportDelFrm">
		<input type="hidden" name="no" value="<%= report.getReportNo() %>"/>
	</form>
	
	<script>
	function deleteReport(){
		if(confirm("게시글을 정말 삭제하시겠습니까?")) {
			$(document.reportDelFrm).submit();
		}
	}
	</script>

</c:if>
<script>

function targetPage() {
	$("#targetMemberFrm")
	.attr("action","<%=request.getContextPath()%>/member/memberTarget")
	.submit();
}


		var slideIndex = 1;
		showSlides(slideIndex);
		
		function plusSlides(n) {
		  showSlides(slideIndex += n);
		}
		
		function currentSlide(n) {
		  showSlides(slideIndex = n);
		}
		
		function showSlides(n) {
		  var i;
		  var slides = document.getElementsByClassName("mySlides");
		  var dots = document.getElementsByClassName("dot");
		  if (n > slides.length) {slideIndex = 1}    
		  if (n < 1) {slideIndex = slides.length}
		  for (i = 0; i < slides.length; i++) {
		      slides[i].style.display = "none";  
		  }
		  for (i = 0; i < dots.length; i++) {
		      dots[i].className = dots[i].className.replace(" sactive", "");
		  }
		  slides[slideIndex-1].style.display = "block";  
		  dots[slideIndex-1].className += " active";
		}
		
		</script>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
