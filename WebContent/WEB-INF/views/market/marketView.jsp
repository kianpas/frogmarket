<%@page import="oracle.net.aso.l"%>
<%@page import="member.model.service.MemberService"%>
<%@page import="market.model.vo.ProductCommentExt"%>
<%@page import="market.model.vo.pAttach"%>
<%@page import="java.util.List"%>
<%@page import="member.model.vo.Member"%>
<%@page import="market.model.vo.Product"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/lightbox2/2.11.1/css/lightbox.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/lightbox2/2.11.1/js/lightbox.min.js"></script>
<%
	Product product = (Product)request.getAttribute("product");
	Member member = (Member)request.getAttribute("member");
	List<pAttach> attachList = (List<pAttach>)request.getAttribute("attachList");
	List<ProductCommentExt> commentList = (List<ProductCommentExt>)request.getAttribute("commentList");

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
					<% if(member.getIcon() == null || member.getIcon().isEmpty()){ %>
					<img src="<%= request.getContextPath() %>/img/icon1.jpg" alt="">
					<% } else { %>
					<img src="<%= request.getContextPath() %>/img/<%= member.getIcon() %>" alt="">
					<% } %>
					<input type="hidden" name="memberId" value="<%= member.getMemberId() %>"/>
                </div>
                </form>
                <div class="seller-profile">
                    <div class="seller-profile-info">
                        <h3><%=member.getNickId() %></h3>
                    </div>
                    <div class="seller-good-info">
                    	<%if(("new").equals(product.getStatus())){ %>
						<h3 style="color: #ff8a3d;">판매중</h3>
						<%}else if(("reserved").equals(product.getStatus())){ %>
						<h3 style="color: red;">예약중</h3>
						<% }else{ %>
						<h3 style="color: #b0b0b0;">판매완료</h3>
						<% } %>
					</div><div class="seller-good-info" style="margin-right: 20px;">
                    	<h3 style="color: #b78f8f;"><%= product.getArea() %></h3>
					</div>
					<div class="seller-good-info" style="margin-right: 20px;">
                        <h3>좋아요 : <%=member.getGoodScore() %></h3>
                    </div>
                </div>
            </div>
            <div class="commet-container">
                <h1><%=product.getTitle() %></h1>
                <br>
                <span><%=product.getArea() %></span>
                <h3><%=product.getPrice() %>원</h3>
                <span><%=product.getDescription() %></span>
            </div>
			<%
				if (loginMember != null) {
			%>
			<div class="market-up-del-container">
				<input type="button" style="width: 100px; border-radius: 80px;" value="장바구니" onclick="addCart();">
			<%
				if (editable) {
			%>
				<input type="button" value="삭제" onclick="deleteProduct()">
				<input type="button" value="수정" onclick="updateProduct()">
				<%
					}
				%>
		</div>
		<%
			}
		%>
		<div class="comment-reader">
                <h3 style="margin: 10px 35px;">댓글란</h3>
                <%if(commentList!=null && !commentList.isEmpty()) {%>
                
               	 <%for(ProductCommentExt pc : commentList) {
               		boolean removable = loginMember != null && (loginMember.getMemberId().equals(pc.getWriter())
        					|| "A".equals(loginMember.getMemberRole()));
               	 %>
                <div class="reader-inbox">
            		<form id="targetMemberFrm" method="get" style="margin: 0; padding: 0;">
                    <div class="comment-reader-icon" onclick="targetPage();" style="cursor: pointer;">
                    	<% if(pc.getIcon() == null || pc.getIcon().isEmpty()){ %>
							<img src="<%= request.getContextPath() %>/img/icon1.jpg">
						<% } else { %>
							<img src="<%= request.getContextPath() %>/img/<%= pc.getIcon() %>">
						<% } %>
					<input type="hidden" name="memberId" value="<%= pc.getMemberId() %>"/>
                    </div>
                    </form>
                    <h4><%=pc.getNickId() %></h4>
                    <p><%=pc.getContent() %></p>
                    <p><%=pc.getRegDate() %></p>
                      <%if(removable) {%>
                   <input type="button" value="삭제" class="comment-delete"> 
                   <input type="hidden" value="<%=pc.getNo() %>" class="comment-no">
                   <%} %>
                 
                </div>
               	 <%} %>
                <%} %>
          
            </div>
            <div class="comment-writer">
                   <form action="<%= request.getContextPath() %>/market/marketCommentInsert" method="post" name="marketCommentFrm">
                <div class="comment_inbox">
                    <h3 class="comment_inbox_name"><%=loginMember != null ? loginMember.getNickId() : "" %></h3>
                    <input type="hidden" name="boardNo" value="<%=product.getNo()%>" />
               		<input type="hidden" name="writer" value="<%=loginMember!=null? loginMember.getMemberId():"" %>" />
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
    
   <%if(loginMember !=null) {%>
 <form action="<%=request.getContextPath() %>/member/addCart"  method="post" name="addCartFrm">
    	<input type="hidden" name="boardNo" value="<%=product.getNo() %>"/>
    	<input type="hidden" name="memberId" value="<%= loginMember.getMemberId() %>"/>
    </form>
    <%}; %>
    
<%
	if (editable) {
%>
<form action="<%=request.getContextPath()%>/market/marketDelete"
	method="post" name="productDelFrm">
	<input type="hidden" name="no" value="<%=product.getNo()%>" />
</form>
<script>
	function updateProduct(){
		location.href = "<%=request.getContextPath()%>/market/marketUpdate?no=<%=product.getNo()%>";
	}
	
	function deleteProduct(){
		if(confirm("게시글을 정말 삭제하시겠습니까?")){
			$(document.productDelFrm).submit();
		}
	}
	</script>
<%
	}
%>
<form action="<%=request.getContextPath()%>/market/marketCommentDelete"
	name="marketCommentDelFrm" method="post">
	<input type="hidden" name="no" /> <input type="hidden" name="boardNo"
		value="<%=product.getNo()%>" />
</form>
<script>

function targetPage() {
	$("#targetMemberFrm")
	.attr("action","<%=request.getContextPath()%>/member/memberTarget")
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
	<%if (loginMember == null) {%>
		loginAlert();
		return false;
	<%}%>
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