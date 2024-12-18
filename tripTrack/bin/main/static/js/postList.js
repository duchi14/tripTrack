		    document.addEventListener("DOMContentLoaded", function() {
		        // 현재 페이지 번호를 가져옵니다.
		        var nowPage = document.getElementById("nowPage").value;
		        
		        // 모든 페이지 번호를 나타내는 span 요소를 가져옵니다.
		        var pageSpans = document.querySelectorAll(".page");
	
		        // 각 span 요소를 순회하면서 현재 페이지와 일치하는지 확인합니다.
		        pageSpans.forEach(function(span) {
		            var page = span.getAttribute("data-page");
		            if (page === nowPage) {
		                // 일치하는 경우 배경색을 변경합니다.
// 		                span.style.backgroundColor = "#0277BD"; // 원하는 배경색으로 변경
		                span.style.color = "#0277BD";
		                span.style.fontWeight = "bold";
		            }
		        });
		    });


const filters = {
    location: [],
    tag: []
};
const failImg = (imgTag) => {
	imgTag.onerror="";
	imgTag.src = "spring/repository/failImg.jpg";
	return true;
};
// 모든 체크박스 요소 가져오기
const checkboxes = document.querySelectorAll('.filter_cntr input[type="checkbox"]');

//별점 표기
var starPointlength = document.getElementsByClassName('star_point').length;

function viewStar(){
	for (var i = 0; i < starPointlength; i++) {
	var stardiv = document.getElementById(i).nextSibling;
	var starPoint = stardiv.nextElementSibling.getElementsByClassName('star_point')[0].textContent;
	
	for(var j = 1; j <= 20; j++){
		if(j%2 != 0){
			if(starPoint >= document.getElementById(i).querySelector('input:nth-child('+j+')').value){
				var ck = document.getElementById(i).querySelector('input:nth-child('+j+')');
				ck.checked = true;
			}	
		}
		
	}
    
    // 1에서 5까지의 숫자만 허용
}	

}
function TagSubmit (){
	tagSearch.submit();
}
function toggleAct(button) {
            button.classList.toggle("act");
        }	

// 체크박스가 변경될 때마다 호출될 함수 정의
function filterPosts() {
    const posts = document.querySelectorAll('.post');

    posts.forEach(post => {
        let shouldDisplay = true;

        // 위치 필터링
        if (filters.location.length > 0) {
            const location = post.querySelector('.location').textContent;
            if (!filters.location.includes(location)) {
                shouldDisplay = false;
            }
        }

        // 태그 필터링
        if (filters.tag.length > 0) {
            const tags = post.querySelector('.tag').textContent.split(',');
            const matchingTags = filters.tag.filter(tag => tags.includes(tag));
            if (matchingTags.length === 0) {
                shouldDisplay = false;
            }
        }

        if (shouldDisplay) {
            post.style.display = 'block';
        } else {
            post.style.display = 'none';
        }
    });
}

// 체크박스 변경 이벤트에 필터링 함수 연결
checkboxes.forEach(checkbox => {
    checkbox.addEventListener('change', function() {
        // 위치 체크박스일 때
        if (checkbox.closest('.location_filter_box')) {
            if (checkbox.checked) {
                filters.location.push(checkbox.value);
            } else {
                filters.location = filters.location.filter(location => location !== checkbox.value);
            }
        }
        // 태그 체크박스일 때
        else if (checkbox.closest('.tag_filter_box')) {
            if (checkbox.checked) {
                filters.tag.push(checkbox.value);
            } else {
                filters.tag = filters.tag.filter(tag => tag !== checkbox.value);
            }
        }
        // 필터링 적용
        filterPosts();
    });
});
// 페이지 로드 시 필터링 함수 실행하여 초기 상태 설정
filterPosts();

window.viewStar();

function bookmark(button) {
	let bookMarkedPostId = button.value;
	
	
	let loginedUserId = document
		.getElementById('loginedUserId').value;
	$.ajax({
		type: "get",
		async: false,
		url: "placeBm.do",
		data: {
			bookMarkedPostId: bookMarkedPostId,
			loginedUserId: loginedUserId,
		},
		success: function(message) {
			alert(message);
		},
		error: function() {
			alert("에러가 발생헀습니다.");
		},
		complete: function() {
		}
	});
}
	
function minPage(){
   var search = document.getElementById('submit');
   document.getElementById('NowPage').value = 1;
   search.submit();
}

function prevPage(){
   var prevPage = document.getElementById('prevPage').value;
   var search = document.getElementById('submit');
   document.getElementById('NowPage').value = prevPage;
   search.submit();
}

function selectPage(button){
   var search = document.getElementById('submit');
   var btnValue = button.value;
   document.getElementById('NowPage').value = btnValue;
   search.submit();
}
function maxPage(){
   var maxPage = document.getElementById('maxPage').value;
   var search = document.getElementById('submit');
   document.getElementById('NowPage').value = maxPage;
   search.submit();
}

function nextPage(){
   var nextPage = document.getElementById('nextPage').value;
   var search = document.getElementById('submit');
   document.getElementById('NowPage').value = nextPage;
   search.submit();
}

