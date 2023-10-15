function fadeIn(no1){
	var level=0;
	var inTimer=null;
	inTimer=setInterval(function(){
		level=fadeInAction(no1, level, inTimer);
	}, 200);
}
function fadeInAction(no1, level, inTimer){
	level=level + 0.1;
	changeOpacity(no1, level);
	if(level>1) clearInterval(inTimer);
	return level;
}
function fadeOut(no2){
	var level=1;
	var outTimer=null;
	outTimer=setInterval(function(){
		level=fadeOutAction(no2, level, outTimer);
	}, 50);
}
function fadeOutAction(no2, level, outTimer){
	level=level-0.1;
	changeOpacity(no2, level);
	if(level < 0){
		clearInterval(outTimer);
	}
	return level;
}

function changeOpacity(no3, level){
	var obj=no3;
	obj.style.opacity=level;
	obj.style.MozOpacity=level;
	obj.style.KhtmlOpacity=level;
	obj.style.MsFilter="'progid: DXImageTransform.Microsoft.Alpha(Opacity="+(level * 100)+")'";
	obj.style.filter="alpha(opacity="+(level * 100)+");";
}

//fadeIn 필요한 부분에서
var targetElement1=document.getElementsByClassName('no3')[0];
fadeIn(targetElement1);

//fadeOut 필요한 부분에서
var targetElement2=document.getElementsByClassName('no1')[1];
fadeOut(targetElement2);

//fadeIn 필요한 부분에서
var targetElement3=document.getElementsByClassName('no3')[2];
fadeIn(targetElement3);

//반대 fadeOut no1
var targetElement1=document.getElementsByClassName('no1')[0];
fadeOut(targetElement1);

//반대 fadeIn 필요한 부분에서
var targetElement2=document.getElementsByClassName('no2')[1];
fadeIn(targetElement2);

//반대fadeOut 필요한 부분에서
var targetElement3=document.getElementsByClassName('no3')[2];
fadeOut(targetElement3);
