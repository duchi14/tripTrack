//북마크 관리
//페이지 로드 시 북마크 되어있는지 확인하고 버튼 색깔 미리 변경
document.addEventListener('DOMContentLoaded', (event) => {
            checkBookmarkStatus();
        });
function checkBookmarkStatus() {
    const userId = document.getElementById('loginedUserId').value;
    const postId = document.getElementById('bookmarkBtn').value;

    $.ajax({
        type: "get",
        url: "bookmark.status",
        data: {
            loginedUserId: userId,
            bookMarkedPostId: postId
        },
        success: function(isBookmarked) {
            const bookMarkBtn = document.getElementById('bookmarkBtn');
            if (isBookmarked) {
                bookMarkBtn.style.color = '#4B89DC';
            } else {
                bookMarkBtn.style.color = '';
            }
        },
        error: function() {
            alert("에러가 발생헀습니다.");
        }
    });
}
//북마크 토글 기능
function toggleBookmark(button) {
	const bookMarkBtn = button;
    const bookMarkedPostId = bookMarkBtn.value;
    const loginedUserId = document.getElementById('loginedUserId').value;

    if(loginedUserId == 'notLogined'){
		alert('로그인 후 이용하실 수 있습니다. ')
		return;
	}
	$.ajax({
		type: "get",
		async: false,
		url: "bookmark.toggle",
		data: {
			bookMarkedPostId: bookMarkedPostId,
			loginedUserId: loginedUserId,
			lat: lat,
			lng: lng,
			placeName: placeName
		},
		success: function(message) {
			alert(message);
			checkBookmarkStatus();
		},
		error: function() {
			alert("에러가 발생헀습니다.");
		},
		complete: function() {
		}
	});
}
//버튼 눌렀을 때 북마크하기
function bookmark() {
	const bookMarkBtn = document.getElementById('bookmarkBtn');
	let bookMarkedPostId = document.getElementById('bookmarkBtn').value;

	let lat = document.getElementById('lat').value;
	let lng = document.getElementById('lng').value;
	let placeName = document.getElementById('placeName').value;
	let loginedUserId = document.getElementById('loginedUserId').value;
	
	if(loginedUserId == 'notLogined'){
		alert('로그인 후 이용하실 수 있습니다.')
		return;
	}
	
	$.ajax({
		type: "get",
		async: false,
		url: "bookmark.do",
		data: {
			bookMarkedPostId: bookMarkedPostId,
			loginedUserId: loginedUserId,
			lat: lat,
			lng: lng,
			placeName: placeName
		},
		success: function(message) {
			bookMarkBtn.style.color = '#4B89DC';
			alert(message);
		},
		error: function() {
			alert("에러가 발생헀습니다.");
		},
		complete: function() {
		}
	});
}