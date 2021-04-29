<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
    <!-- section시작 -->
    <section>
        <div class="add-product-container">
            <form
			name="marketEnrollFrm"
			action="<%=request.getContextPath() %>/market/marketEnroll" 
			method="post" enctype="multipart/form-data">
                <div class="add_product-top">
                    <h1>상품등록</h1>
                    <!-- writer의 value는 loginMember의 getId를 해야 함. -->
                    <input type="hidden" name="writer" value="<%= loginMember.getMemberId() %>"/>
                    
                    <select name="status" style="display:none">
                        <option value="new" selected="selected">판매중</option>
                        <option value="reserved">예약중</option>
                        <option value="soldout">판메 완료</option>
                    </select>
                    <select name="local">
                        <option value="" selected="selected" required>지역 선택</option>
                        <option value="서울">서울</option>
                        <option value="인천">인천</option>
                        <option value="대구">대구</option>
                        <option value="대전">대전</option>
                        <option value="부산">부산</option>
                        <option value="순천">순천</option>
                        <option value="강원도">강원도</option>
                    </select>
                </div>
                <div class="add-product-title">
                    <input placeholder="게시글 제목을 정해주세요" type="text" name="title" id="title" required>
                </div>

                <div class="add-product-photo">
                    <label class="input-file-button" for="input-file0"><span class="add-product-img0">클릭하면 사진추가 [+]</span></label>
					<input type="file" name="upFile0" id="input-file0" style="display:none;" accept="image/*" onchange="setThumbnail(event)"/>
					
                </div>
                <div class="add-product-price">
                    <input type="number" name="price" placeholder="￦ 가격 기제" required>
                </div>
                <div class="add-product-comment">
                    <textarea name="description" id="description" cols="30" rows="10" placeholder="상품에 대해 글을 써주세요" required></textarea>
                </div>
            	<input class="add-product-submit" type="submit" name="" id="" value="등록">	
            </form>
        </div>
    </section>
    <!-- section끝 -->
<script>
//제출시 유효성 검사
$(document.marketEnrollFrm).submit(function(){
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
function setThumbnail(e){
	
	var total = $('div.add-product-photo img').length;
	var num = (e.target.name).substring(6);
	console.log("total : "+total);
	console.log("thisNum : "+num);

	var reader = new FileReader(); 
	reader.onload = function(event) { 
		var img = document.createElement("img"); 
		img.setAttribute("src", event.target.result); 
		img.setAttribute("width","82px");
		img.setAttribute("height","82px");
		
		var newImg = document.querySelector("span.add-product-img"+num);
		//새박스를 만드는 경우
		if(newImg.firstChild.tagName!='IMG' && total<4)
			createNewBox();		
		
		newImg.innerText="";	//이전 내용물 삭제
		newImg.appendChild(img);	//세 썸네일 추가
	};
	reader.readAsDataURL(e.target.files[0]);	
}

function createNewBox(){
	var num = $('div.add-product-photo img').length+1;
	
	var $label = $('<label class="input-file-button" for="input-file'+num+'"></label>');
	var $span = $('<span class="add-product-img'+num+'">클릭하면 사진추가 [+]</span>');
	var $input = $('<input type="file" name="upFile'+num+'" id="input-file'+num+'" style="display:none;" accept="image/*" onchange="setThumbnail(event)"/>');
	$label.append($span);
	
	$('div.add-product-photo').append($label);
	$('div.add-product-photo').append($input);
}


</script>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>