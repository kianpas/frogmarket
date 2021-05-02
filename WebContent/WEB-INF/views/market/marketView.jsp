<%@page import="oracle.net.aso.l"%>
<%@page import="member.model.service.MemberService"%>
<%@page import="market.model.vo.ProductCommentExt"%>
<%@page import="market.model.vo.pAttach"%>
<%@page import="java.util.List"%>
<%@page import="member.model.vo.Member"%>
<%@page import="market.model.vo.Product"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/common/header.jsp"/>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/lightbox2/2.11.1/css/lightbox.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/lightbox2/2.11.1/js/lightbox.min.js"></script>
<%
	Product product = (Product)request.getAttribute("product");
	Member member = (Member)request.getAttribute("member");
	List<pAttach> attachList = (List<pAttach>)request.getAttribute("attachList");
	List<ProductCommentExt> commentList = (List<ProductCommentExt>)request.getAttribute("commentList");

%>

 <!-- section시작 -->
    <section>
        <div class="post-container">
            <div class="market-img-container">            
                <div class="slideshow-container">
                	<% if(attachList==null){ %>
                		<h1>첨부된 사진이 없습니다.</h1>
                	<% }else{ 
                		int slidePageNo=1;
                	%>
						<% for(pAttach attach : attachList){ %>
		                <div class="mySlides fade">
		                <a href="<%=request.getContextPath() %>/upload/market/<%=attach.getRenamedFileName() %>" 
		                		data-lightbox="example-set">
		                	<img src="<%=request.getContextPath() %>/upload/market/<%=attach.getRenamedFileName() %>">
		                	</a>
		                </div>
						<% } %>
		                <a class="prev" onclick="plusSlides(-1)">&#10094;</a>
						<a class="next" onclick="plusSlides(1)">&#10095;</a>
						
		                <div style="text-align:center">
						<% for(pAttach attach : attachList){ %>
							<span class="dot" style="margin: 20px 0;" onclick="currentSlide(<%=slidePageNo++%>)"></span> 
						<% } %>	
						</div>
               		<% } %>
				</div>
                <br>
       	        
            </div>
            <div class="seller-title">
            <form id="targetMemberFrm" method="get" style="margin: 0; padding: 0;">
                <div class="seller-icon" onclick="targetPage();" style="cursor: pointer;">
                <c:choose>
                <c:when test="${empty member.icon}">
					<img src="<c:url value="/img/icon1.jpg"/>" alt="">
					</c:when>
					<c:otherwise>
					<img src="<c:url value="/img/${member.icon}"/>" alt="">
					</c:otherwise>
					 </c:choose>
					<input type="hidden" name="memberId" value="${member.memberId}"/>
                </div>
                </form>
                <div class="seller-profile">
                    <div class="seller-profile-info">
                        <h3>${member.nickId}</h3>
                    </div>
                    <div class="seller-good-info">
                    	<c:choose>
                    	<c:when test="${product.status eq 'new'}">
						<h3 style="color: #ff8a3d;">판매중</h3>
						</c:when>
						<c:when test="${product.status eq 'reserved'}">
						<h3 style="color: red;">예약중</h3>
						</c:when>
						<c:otherwise>
						<h3 style="color: #b0b0b0;">판매완료</h3>
						</c:otherwise>
						</c:choose>
					</div><div class="seller-good-info" style="margin-right: 20px;">
                    	<h3 style="color: #b78f8f;">${product.area}</h3>
					</div>
					<div class="seller-good-info" style="margin-right: 20px;">
                        <h3>좋아요 : ${member.goodScore}</h3>
                    </div>
                </div>
            </div>
            <div class="commet-container">
                <h1>${product.title}</h1>
                <br>
                <span>${product.area}</span>
                <h3>${product.price}원</h3>
                <span>${product.description}</span>
            </div>
		
			<c:if test="${not empty loginMember}">
			
			<div class="market-up-del-container">
				<input type="button" style="width: 100px; border-radius: 80px;" value="장바구니" onclick="addCart();">
			<c:if test="${loginMember.memberId eq member.memberId or loginMember.memberRole eq MemberService.ADMIN_ROLE}">
				<input type="button" value="삭제" onclick="deleteProduct()">
				<input type="button" value="수정" onclick="updateProduct()">
				</c:if>
		</div>
	
		</c:if>
		<div class="comment-reader">
                <h3 style="margin: 10px 35px;">댓글란</h3>
                <c:if test="${not empty commentList}">
                
                
                <c:forEach items="${commentList}" var="pc">
               	 
                <div class="reader-inbox">
            		<form id="targetMemberFrm" method="get" style="margin: 0; padding: 0;">
                    <div class="comment-reader-icon" onclick="targetPage();" style="cursor: pointer;">
                    	<c:choose>
                    	<c:when test="${empty pc.icon}">
                    	
							<img src="<c:url value="/img/icon1.jpg"/>">
							</c:when>
						<c:otherwise>
							<img src="<c:url value="/img/${pc.icon}"/>">
						</c:otherwise>
						</c:choose>
					<input type="hidden" name="memberId" value="${pc.memberId}"/>
                    </div>
                    </form>
                    <h4>${pc.nickId}</h4>
                    <p>${pc.content}</p>
                    <p>${pc.regDate}</p>
                   
                      <c:if test="${not empty loginMember and loginMemeber.memberId eq pc.writer or loginMember.memberRole eq MemberService.ADMIN_ROLE}">
                   <input type="button" value="삭제" class="comment-delete"> 
                   <input type="hidden" value="${pc.no}" class="comment-no">
                   </c:if>
                   
                </div>
                </c:forEach>
               
                </c:if>
          
            </div>
            <div class="comment-writer">
                   <form action="<c:url value="/market/marketCommentInsert"/>" method="post" name="marketCommentFrm">
                <div class="comment_inbox">
                    <h3 class="comment_inbox_name">${not empty loginMember ? loginMember.nickId : ''}</h3>
                    <input type="hidden" name="boardNo" value="${product.no}"/>
               		<input type="hidden" name="writer" value="${not empty loginMember ? loginMember.memberId : ''}" />
                    <textarea placeholder="댓글을 남겨보세요" rows="1" class="comment_inbox_text" name="content"></textarea>
                </div>
                <div class="register_box">
                    <input class="register_btn" type="submit" name="" id="" value="등록">
                </div>
                </form>
            </div>
        </div>
    </section>  
    <!-- section끝 -->
    <c:if test="${empty loginMember}">

 <form action="<c:url value="/member/addCart"/>"  method="post" name="addCartFrm">
    	<input type="hidden" name="boardNo" value="${product.no}"/>
    	<input type="hidden" name="memberId" value="${loginMember.memberId}"/>
    </form>

    </c:if>

<c:if test="${loginMember.memberId eq member.memberId or loginMember.memberRole eq MemberService.ADMIN_ROLE}">

<form action="<c:url value="/market/marketDelete"/>"
	method="post" name="productDelFrm">
	<input type="hidden" name="no" value="${product.no}" />
</form>
<script>
	function updateProduct(){
		location.href = "<c:url value='/market/marketUpdate?no=${product.no}'/>";
	}
	
	function deleteProduct(){
		if(confirm("게시글을 정말 삭제하시겠습니까?")){
			$(document.productDelFrm).submit();
		}
	}
	</script>

</c:if>
<form action="<c:url value="/market/marketCommentDelete"/>"
	name="marketCommentDelFrm" method="post">
	<input type="hidden" name="no" /> <input type="hidden" name="boardNo"
		value="${product.no}" />
</form>
<script>

function targetPage() {
	$("#targetMemberFrm")
	.attr("action", "<c:url value="/member/memberTarget"/>")
	.submit();
}


  $(".comment-delete").click(function(){
	  if(confirm("해당 댓글을 삭제하시겠습니까?")){
		  var $frm = $(document.marketCommentDelFrm);
		  var marketCommentNo = $(".comment-no").val();
		  $frm.find("[name=no]").val(marketCommentNo);
		  $frm.submit();
	  }
	  
  });
//유효성 검사
//$(document.boardCommentFrm).submit(function(){
	//이벤트 버블링을 위해 전체 문서로 변화
	$(document).on('submit', '[name=marketCommentfrm]', function(e){
	<c:if test="${empty loginMember}">
		loginAlert();
		return false;
	</c:if>
	//댓글 내용
	var $content = $("[name=content]", e.target);
	if(/^(.|\n)+$/.test($content.val() == false)){
		alert("댓글 내용을 작성하세요.");
		$content.focus();
		return false;
	}
});
function loginAlert(){
	alert("로그인 이후 이용하실 수 있습니다.");
	$("#memberId").focus();
}
  </script>

<!-- slide gallery를 위한 script -->
<script>
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
		      dots[i].className = dots[i].className.replace(" active", "");
		  }
		  slides[slideIndex-1].style.display = "block";  
		  dots[slideIndex-1].className += " active";
		}
		</script>
		

<script>
function addCart() {
	if(confirm("장바구니에 담으시겠습니까?")){
	var $frm = $(document.addCartFrm);
	$frm.submit();
	}
};
</script>


<%@ include file="/WEB-INF/views/common/footer.jsp" %>