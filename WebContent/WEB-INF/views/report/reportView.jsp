<%@page import="member.model.vo.Member"%>
<%@page import="report.model.vo.Report"%>
<%@page import="report.model.vo.RAttach"%>
<%@page import="member.model.service.MemberService"%>
<%@page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/common/header.jsp" %>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/lightbox2/2.11.1/css/lightbox.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/lightbox2/2.11.1/js/lightbox.min.js"></script>
<%
	Report report = (Report)request.getAttribute("report");
	Member member = (Member)request.getAttribute("member");
	List<RAttach> attachList = (List<RAttach>)request.getAttribute("attachList");

	boolean editable = 
				loginMember != null &&
				(
					loginMember.getMemberId().equals(member.getMemberId())
					|| MemberService.ADMIN_ROLE.equals(loginMember.getMemberRole())
					
				);
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
						<% for(RAttach attach : attachList){ %>
							<span class="dot" onclick="currentSlide(<%= slidePageNo++ %>)"></span> 
						<% } %>	
						</div>
               		<% } %>
				</div>
                <br>
       	        
            </div>
            <div class="reporter-title">
            	<form id="targetMemberFrm" method="get" style="margin: 0; padding: 0;">
	                <div class="reporter-icon" onclick="targetPage();" style="cursor: pointer;">
						<% if(member.getIcon() == null || member.getIcon().isEmpty()){ %>
						<img src="<%= request.getContextPath() %>/img/icon1.jpg" alt="">
						<% } else { %>
						<img src="<%= request.getContextPath() %>/img/<%= member.getIcon() %>" alt="">
						<% } %>
						<input type="hidden" name="memberId" value="<%= member.getMemberId() %>"/>
	                </div>
                </form>
                <div class="reporter-profile">
                    <div class="reporter-profile-info">
                        <h3><%= member.getNickId() %></h3>
                    </div>
                    <div class="target-profile-info">
                        <h3>신고 대상 : <%= report.getMemberReportId() %></h3>
                    </div>
                </div>
            </div>
            <div class="commet-container">
                <span><%= report.getContent() %></span>
            </div>
             <%if(editable){ %>
            <div class="market-up-del-container">
                <input type="button" value="삭제" onclick="deleteReport()">
            </div>
            <%} %>
            
        </div>
    </section>
    <!-- section끝 -->
    
<%if(editable){ %>
	<form 
		action="<%=request.getContextPath()%>/report/reportDelete" 
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
<%} %>
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

<%@ include file="/WEB-INF/views/common/footer.jsp" %>