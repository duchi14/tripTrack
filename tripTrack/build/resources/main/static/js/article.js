
var fileNo = 1;
var filesArr = new Array();
const imgbox = document.getElementById("file-imgList");
const imgnewP = document.createElement('p');
// 삭제 기능
const deleteButton = document.getElementById('delete-btn');
const totalPoint = document.getElementById('TStar').value;


var starPointlength = document.getElementsByName('reviewStar').length;

function starRview() {
	for (var i = 0; i < starPointlength; i++) {
		var starPoint = document.getElementById(i).value;

		for (var j = 1; j <= 20; j++) {
			if (j % 2 != 0) {
				if (starPoint >= document.getElementById('re' + i).querySelector('input:nth-child(' + j + ')').value) {
					var ck = document.getElementById('re' + i).querySelector('input:nth-child(' + j + ')');
					ck.checked = true;
				}
			}

		}
	}
	for (var l = 1; l <= 20; l++) {
		if (l % 2 != 0) {
			if (totalPoint >= document.getElementById('totalStar').querySelector('input:nth-child(' + l + ')').value) {
				var checked = document.getElementById('totalStar').querySelector('input:nth-child(' + l + ')');
				checked.checked = true;
			}
		}
	}
}



function newfile() {
	for (var i = 0; i < filesArr.size; i++) {
		console.log(filesArr[i]);
	}
}


function addfile(event) {
	const file = event.files[0];

	var length = document.getElementsByName('file')[0].files.length;
	if (validation(file)) {

		console.log(file);
		for (var i = 0; i < length; i++) {
			var reader = new FileReader();
			reader.onload = function(event) {
				var img = document.createElement("img");
				img.setAttribute("src", event.target.result);
				document.querySelector("div#image_container").appendChild(img);

			};
			reader.readAsDataURL(document.getElementsByName('file')[0].files[i]);
		}
	}

}

function validation(obj) {
	const fileTypes = ['image/gif', 'image/jpeg', 'image/png'];
	if (!fileTypes.includes(obj.type)) {
		alert("첨부가 불가능한 파일입니다.");
		document.querySelector("input[type=file]").value = "";
		return false;
	} else {
		alert("등록");
		return true;
	}
}

// 수정 기능
const modifyButton = document.getElementById('modify-btn');



function sendReview() {


	var flag = false;
	for (var i = 1; i <= 20; i++) {
		if (i % 2 != 0) {
			chk = document.getElementById('rate').querySelector('input:nth-child(' + i + ')')
			if (chk.checked == true) {
				flag = true;
			}
		}
	}
	if (frm.title.value == "") {
		alert('제목을 입력해주세요');
		frm.Reviewtitle.focus();
	} else if (frm.content.value == "") {
		alert('내용을 입력해주세요');
		frm.Reviewcontent.focus();
	} else if (!flag) {
		alert('별점을 입력해주세요');
	} else {
		frm.submit();
	}

}
window.starRview();

function sendReview() {


	var flag = false;
	var Idchk = false;
	for (var i = 1; i <= 20; i++) {
		if (i % 2 != 0) {
			chk = document.getElementById('rate').querySelector('input:nth-child(' + i + ')')
			if (chk.checked == true) {
				flag = true;
			}
		}
	}
	//
	for (var i = 0; i < starPointlength; i++) {
		if (document.getElementById('user' + i).value == document.getElementById('session').value) {
			Idchk = true;
			break;
		}
	}
	if (frm.title.value == "") {
		alert('제목을 입력해주세요');
		frm.Reviewtitle.focus();
	} else if (frm.content.value == "") {
		alert('내용을 입력해주세요');
		frm.Reviewcontent.focus();
	} else if (!flag) {
		alert('별점을 입력해주세요');
	} else if (Idchk) {
		alert('이미 리뷰를 등록하셨습니다.')
	}
	else {
		frm.submit();
	}
}

//슬라이드 관리
var thumbSlide = new Swiper('.img_slide_thumbs', {
	spaceBetween: 3,
	direction: "vertical",
	slidesPerView: 3,
	freeMode: true,
	loopedSlides: 3, //looped slides should be the same  
	watchSlidesVisibility: true,
	watchSlidesProgress: true,
});
var topSlide = new Swiper('.img_slide_top', {
	spaceBetween: 10,
	loop: true,
	loopedSlides: 3, //looped slides should be the same  
	navigation: {
		nextEl: '.swiper-button-next',
		prevEl: '.swiper-button-prev',
	},
	thumbs: {
		swiper: thumbSlide,
	},
});