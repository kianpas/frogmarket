<%@page import="market.model.vo.Product"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<%
List<Product> list = (List<Product>)request.getAttribute("list");
%>
<section>
 <div class="section-body">
            <div class="board">
            <div class="section-title">
                    <h3>장바구니</h3>
                </div>
			 <%if(list==null || list.isEmpty()){ %>
					<p style="margin: 120px 0;">조회된 게시글이 없습니다.</p>
				<%
					} else{ 
						for(Product p : list){
				%>
				<div style="display:inline-block;">
							<div class="board-box1" style="cursor:pointer;"
							onclick="location.href='<%= request.getContextPath()%>/market/marketView?no=<%=p.getNo() %>';">
			                    <div class="thumbnail">
			                    <%if(p.getAttach()!=null){ %>
			                    	<img src="<%=request.getContextPath() %>/upload/market/<%=p.getAttach().getRenamedFileName() %>"
		                			width="270px"  height="160px">
			                    <% } %>
			                    </div>
			                    <div class="product-title">
			                        <p><%=p.getTitle() %>, 글번호<%=p.getNo() %></p><input type="hidden" id="boardNo" value="<%=p.getNo() %>"/>
			                        <h3><%=p.getPrice() %>￦</h3>
			                    </div>
			                </div>		
			                <div>              
			         		 <button value="<%= p.getNo() %>" style="width: 100px; border-radius: 80px; background-color:#fff; border:1px solid #d4d4d4;" class="btn-delete">삭제</button>
			             	</div>	
			             	</div> 
				<%
						}
						%>
						<div class="more"><%=request.getAttribute("pageBar") %></div>
						<%
					}
				%>
				 
 		</div>
</div>
</section>
 <form action="<%= request.getContextPath()%>/member/deleteCart" method="post" name="deleteCartFrm" >
		<input type="hidden" name=id value="<%=loginMember.getMemberId() %>"/>
		<input type="hidden" name=no value="" />
	</form>
 <!-- section끝 -->
    <!-- 게시글 쓰기 이동 버튼 시작 -->
    <div class="post-btn" onclick="location.href='<%= request.getContextPath()%>/market/marketForm';" 
                		style="cursor:pointer;"><img src="<%= request.getContextPath() %>/img/add.png"></div>
    <!-- 게시글 쓰기 이동 버튼 끝 -->
    
<script>
$(".btn-delete").click(function(){
		var $frm = $(document.deleteCartFrm);
		var boardNo = $(this).val();
		$frm.find("[name=no]").val(boardNo);
		$frm.submit();
	
})
   
</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>