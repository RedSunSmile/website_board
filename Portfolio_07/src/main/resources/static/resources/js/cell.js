const formform=document.getElementsByName("form1"),
	  sell_price=document.form1.sell_price,
	  amount=document.form1.amount,
	  add=document.form1.add,
	  minus=document.form1.minus,
	  sum=document.form1.sum;
	  

if(formform){
	sum.value=sell_price.value;
	
	let amountval=amount.value,
		sumval=sum.value,
		priceval=sell_price.value;
		
	if(add){
		add.addEventListener('click',function(){
			amountval++;
			sumval=amountval*priceval;
			amount.value=amountval;
			sum.value=sumval;
			console.log(amountval,sumval,priceval);
		})
	}
	
	if(minus){
		minus.addEventListener('click', function(){
			if(amountval > 1){
				amountval--;
				sumval=amountval*priceval;
				amount.value=amountval;
				sum.value=sumval;
				console.log(amountval, sumval, priceval);
			}else{
				amountval=1;
			}
		})
	}
}