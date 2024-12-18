
const failImg = (imgTag) => {
	imgTag.onerror="null";
	imgTag.src = "spring/repository/failImg.jpg";
	return true;
}

const main_slide = new Swiper('.main_slide', {
	spaceBetween: 0,
	centeredSlides: true,
	loop: true,
	autoplay: {
		delay: 50000,
		disableOnInteraction: false,
	},
	navigation: {
		nextEl: ".swiper-button-next",
		prevEl: ".swiper-button-prev",
	},
});

const swiper = new Swiper(".place_slide", {
  spaceBetween: 30,
  slidesPerView : '3',
  loop:true,
  navigation: {
    nextEl: ".swiper-button-next",
    prevEl: ".swiper-button-prev",
  },
});
	

function BPstarPoint(){
	var starPointlength = document.getElementsByName('bpStar').length;
	for (var i = 0; i < starPointlength; i++) {
	var totalPoint = document.getElementById('BP'+i).value;
	
	for(var j = 1; j <= 20; j++){
		if(j%2 != 0){
			if(totalPoint >= document.getElementById('bp'+i).querySelector('input:nth-child('+j+')').value){
				var ck = document.getElementById('bp'+i).querySelector('input:nth-child('+j+')');
				ck.checked = true;
			}	
		}
		
	}
}
}

function BAstarPoint(){
	var starPointlength = document.getElementsByName('baStar').length;
	for (var i = 0; i < starPointlength; i++) {
	var totalPoint = document.getElementById('BA'+i).value;
	
	for(var j = 1; j <= 20; j++){
		if(j%2 != 0){
			if(totalPoint >= document.getElementById('ba'+i).querySelector('input:nth-child('+j+')').value){
				var ck = document.getElementById('ba'+i).querySelector('input:nth-child('+j+')');
				ck.checked = true;
			}	
		}
		
	}
}
}

function BRstarPoint(){
	var starPointlength = document.getElementsByName('brStar').length;
	for (var i = 0; i < starPointlength; i++) {
	var totalPoint = document.getElementById('BR'+i).value;
	
	for(var j = 1; j <= 20; j++){
		if(j%2 != 0){
			if(totalPoint >= document.getElementById('br'+i).querySelector('input:nth-child('+j+')').value){
				var ck = document.getElementById('br'+i).querySelector('input:nth-child('+j+')');
				ck.checked = true;
			}	
		}
		
	}
}
}
window.BRstarPoint();
window.BPstarPoint();
window.BAstarPoint();