
	function calculateOrderPrice(){
		var tbody=document.getElementById("cart");
		var rows=tbody.getElementsByTagName("tr");
		
		for(var i=0;i<rows.length;i++){
			//tr를 순서대로 하나씩 가져오기
			var tr=rows[i];
			//tr에서 가격이 표시된 td가져오기
			var priceTd=tr.firstElementChild.nextElementSibling.nextElementSibling;
			//가격표시된 td다음에 있는 구매수량이 표시된 td가져오기
			var qtyTd=priceTd.nextElementSibling;
			
			//구매가격(가격*구매수량)을 표시할 td가져오기
			var orderPriceTd=tr.lastElementChild;
			//td에서 가격 가져오기
			var price=parseInt(priceTd.textContent);
			//td에서 구매수량 가져오기(삼항연산자를 사용했음=>비교식? 참일때:거짓일때)
			var qty=qtyTd.firstElementChild.value== "" ? 0:parseInt(qtyTd.firstElementChild.value);
			//구매가격 계산하기
			var orderPrice=price * qty;
			//구매가격 표시 td에 구매가격 넣기
			orderPriceTd.textContent=orderPrice;
		}
		//총합계계산하는 함수 실행시키기
		calculateTotalPrice();
	}
	
	function calculateTotalPrice(){
		var span=document.getElementById("order-price");
		//tbody를 찾는다
		var tbody=document.getElementById("cart");
		//tbody안의 모든 tr를 찾는다
		var rows=tbody.getElementsByTagName("tr");
		//주문가격의 합계를 저장할 변수 만든다
		var total=0;
		//tr을 순서대로 하나씩 뽑아내서 주문가격을 가져오기
		for(var i=0;i<rows.length;i++){
			var tr=rows[i];
			//주문가격이 있는 td찾기
			var priceTd=tr.lastElementChild;
			//주문가격 가져오기
			var price=parseInt(priceTd.textContent);
			//합계 누적시키기
			total+=price;
		}
		//합계가 표시되는 span의 텍스트컨텐츠 변경하기
		span.textContent=total;
	}
	
	function deleteSelectedRow(){
		var tbody=document.getElementById("cart");
		var boxs=document.getElementsByName("ck");
		for(var i=boxs.length;i>0;i--){
			var checkbox=boxs[i-1];
			if(checkbox.checked){
				var tr=checkbox.parentNode.parentNode;
				tbody.removeChild(tr);
			}
		}
		calculateTotalPrice();
	}
