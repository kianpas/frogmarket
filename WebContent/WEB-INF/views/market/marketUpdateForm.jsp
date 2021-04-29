<%@page import="market.model.vo.pAttach"%>
<%@page import="java.util.List"%>
<%@page import="market.model.vo.Product"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%
	Product product = (Product)request.getAttribute("product");
	List<pAttach> attachList = (List<pAttach>)request.getAttribute("attachList"); 
%>
    <!-- section시작 -->
    <section>
        <div class="add-product-container">
            <form
			name="marketUpdateFrm"
			action="<%=request.getContextPath() %>/market/marketUpdate" 
			method="post" enctype="multipart/form-data">
                <div class="add_product-top">
                    <h1>상품수정</h1>
                    <!-- writer의 value는 oneman대신 loginMember의 getId를 해야 함. -->
                    <input type="hidden" name="writer" value="oneman"/>
                    <input type="hidden" name="no" value="<%=product.getNo()%>"/>
                    
                    <select name="status">
                        <option value="new" <% if("new".equals(product.getStatus()) ){ %>selected <%} %>>판매중</option>
                        <option value="reserved" <% if("reserved".equals(product.getStatus()) ){ %>selected <%} %>>예약중</option>
                        <option value="soldout" <% if("soldout".equals(product.getStatus()) ){ %>selected <%} %>>판매 완료</option>
                    </select>
                    <select name="local">
                        <option value="">지역 선택</option>
                        <option value="서울" <% if("서울".equals(product.getArea()) ){ %>selected <%} %>>서울</option>
                        <option value="인천" <% if("인천".equals(product.getArea()) ){ %>selected <%} %>>인천</option>
                        <option value="대구" <% if("대구".equals(product.getArea()) ){ %>selected <%} %>>대구</option>
                        <option value="대전" <% if("대전".equals(product.getArea()) ){ %>selected <%} %>>대전</option>
                        <option value="부산" <% if("부산".equals(product.getArea()) ){ %>selected <%} %>>부산</option>
                        <option value="순천" <% if("순천".equals(product.getArea()) ){ %>selected <%} %>>순천</option>
                        <option value="강원도" <% if("강원도".equals(product.getArea()) ){ %>selected <%} %>>강원도</option>
                    </select>
                </div>
                <div class="add-product-title">
                    <input value="<%=product.getTitle() %>" type="text" name="title" id="title" required>
                </div>
                
                <div class="add-product-photo">
					<%for(pAttach attach : attachList){ %>
					<label class="input-file-button" for="prev-<%=attach.getNo() %>">
						<span class="add-product-img<%=attach.getNo() %>">
							<img id="<%=attach.getNo() %>" src="<%=request.getContextPath() %>/upload/market/<%=attach.getRenamedFileName() %>"
							 width="82px" height="82px">
							 <button type="button" onclick="deleteFile(<%=attach.getNo() %>)">삭제</button>
		                </span>
					</label>
					<input type="file" name="prev-<%=attach.getNo() %>" id="prev-<%=attach.getNo() %>" style="display:none;" accept="image/*" onchange="setThumbnail(event)"/>
					<% } %>
					
                    <label class="input-file-button" for="newBox0"><span class="add-product-img-new0">클릭하면 사진추가 [+]</span></label>
					<input type="file" name="newBox0" id="newBox0" style="display:none;" accept="image/*" onchange="setThumbnail(event)"/>
					<!-- <div class="newly-added-img">
	                    <label class="input-file-button" for="input-file0"><span class="add-product-img0">클릭하면 사진추가 [+]</span></label>
						<input type="file" name="upFile0" id="input-file0" style="display:none;" accept="image/*" onchange="setThumbnail(event)"/>
					</div> -->
				</div>

                <div class="add-product-price">
                    <input type="number" name="price" value="<%=product.getPrice()%>" required>
                </div>
                <div class="add-product-comment">
                    <textarea name="description" id="description" cols="30" rows="10" required><%=product.getDescription() %></textarea>
                </div>
            	<input class="add-product-submit" type="submit" name="" id="" value="등록">	
            </form>
        </div>
    </section>
    <!-- section끝 -->
<script>
function deleteFile(prevAttNo){
	console.log(prevAttNo);
	//삭제하는 파일 번호를 제출
	var $delFile = $('<input type="hidden" name="delFile" value="'+prevAttNo +'" >');
	$frm = $(document.marketUpdateFrm);
	$frm.append($delFile);
	//label이하와 input을 삭제
	$label = $('label[for="prev-'+prevAttNo+'"]');
	$input = $('#prev-'+prevAttNo);
	$input.remove();
	$label.remove();
	
}
//제출시 유효성 검사
$(document.marketUpdateFrm).submit(function(){
	if(!(confirm("정말 등록하시겠습니까?")))
		return false;
	
	$local = $('[name=local]');
	if($local.val()==""){
		alert("지역을 입력하십시오.");
		return false;
	}
	$title = $('[name=title]');
	if($title.val()==""){
		alert("제목을 입력하십시오.");
		return false;
	}
	
	$description = $('[name=description]');
	if($description.val()==""){
		alert("내용을 입력하십시오.");
		return false;
	}
	
	var $img = $('input[type="file"]');
	$img.each(function(index,elem){
		$(elem).attr('disabled', false);
	});
});
var upFileNum=0;
var apiNum=0;
function setThumbnail(e){
	var num = $('div.add-product-photo img').length;
	console.log("childnum : "+num);
	var reader = new FileReader(); 
	reader.onload = function(event) { 
		var img = document.createElement("img"); 
		img.setAttribute("src", event.target.result); 
		img.setAttribute("width","82px");
		img.setAttribute("height","82px");
		//e.target is input:file
		var imgNo = (e.target.id).substring(5);
		console.log("e.target.name : "+(e.target.name));
		console.log("imgNo : "+imgNo);
		
		//기존 파일 삭제 후 새로운 파일로 대체하는 경우
		if((e.target.name).includes("prev")){
			console.log("1번case");
			//삭제하는 파일 번호를 제출
			var $delFile = $('<input type="hidden" name="delFile" value="'+imgNo +'" >');
			$frm = $(document.marketUpdateFrm);
			$frm.append($delFile);
			
			$span = $("span.add-product-img"+imgNo);
			$span.children('img').remove();
			$span.prepend($(img));
						
			e.target.name="upFile"+upFileNum;	//name을 제출용 upFile로 변경
			upFileNum++;
		}
		//새 파일로 대체한 박스를 다시 누른 경우 : name에 upFile에 포함된 경우
		else if((e.target.name).includes("upFile")){
			console.log("2번case");
			//기존박스를 다시 누른 경우
			if((e.target.id).includes("prev")){
				$span = $("span.add-product-img"+imgNo);
				$span.children('img').remove();
				$span.prepend($(img));
			}
			//새 박스를 다시 누른 경우
			else if((e.target.id).includes("new")){
				$span = $("span.add-product-img-new"+(e.target.id).substring(6));
				$span.children('img').remove();
				$span.prepend($(img));
				
			}
			
		}
		//사진추가 박스를 누른 경우
		else if(num>=5){
			alert("사진은 5개까지 첨부할 수 있습니다.")
		}
		else{
			//새로운 파일을 업로드하는 경우
			console.log("새로운 파일을 업로드하는 경우");
				document.querySelector("span.add-product-img-new"+apiNum).innerText="";
			document.querySelector("span.add-product-img-new"+apiNum).appendChild(img);
			apiNum++;
			
			e.target.name="upFile"+upFileNum;
			upFileNum++;
			console.log("e.target.name : "+e.target.name); 
			
			if(num<5)
				createNewBox();
		}
	};
	reader.readAsDataURL(event.target.files[0]);
}
function createNewBox(){
	var num = $('div.add-product-photo img').length+1;
	
	var $label = $('<label class="input-file-button" for="newBox'+apiNum+'"></label>');
	var $span = $('<span class="add-product-img-new'+apiNum+'">클릭하면 사진추가 [+]</span>');
	var $input = $('<input type="file" name="newBox'+apiNum+'" id="newBox'+apiNum+'" style="display:none;" accept="image/*" onchange="setThumbnail(event)"/>');
	$label.append($span);
	
	$('div.add-product-photo').append($label);
	$('div.add-product-photo').append($input);
}
</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>