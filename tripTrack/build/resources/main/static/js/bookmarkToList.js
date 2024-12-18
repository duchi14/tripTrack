//북마크 관리
//페이지 로드 시 북마크 되어있는지 확인하고 버튼 색깔 미리 변경
document.addEventListener('DOMContentLoaded', (event) => {
            checkBookmarkStatus();
        });

function checkBookmarkStatus() {
    const userId = document.getElementById('bmchkLoginedUserId').value;
    const bookmarkButtons = document.querySelectorAll(".bookmark");
    
    bookmarkButtons.forEach(button => {
		const postId = button.getAttribute('value');
		$.ajax({
            type: "get",
            url: "bookmark.status",
            data: {
                loginedUserId: userId,
                bookMarkedPostId: postId,
            },
            success: function(isBookmarked) {
                if (isBookmarked) {
                    button.querySelector('i').style.color = '#4B89DC'; // 북마크 되어 있으면 색깔 변경
                } else {
                    button.querySelector('i').style.color = ''; // 북마크 안 되어 있으면 기본 색깔
                }
            },
            error: function() {
                alert("에러가 발생헀습니다.");
            }
        });
	})
}
//북마크 토글 기능
function toggleBookmark(button) {
	const bookMarkBtn = button;
    const bookMarkedPostId = bookMarkBtn.value;
    let loginuser = document.getElementById('bmchkLoginedUserId');
    
    if(loginuser == null){
		alert('로그인 후 이용하실 수 있습니다. ')
		return;
	} else {
		const loginedUserId = document.getElementById('bmchkLoginedUserId').value;
			$.ajax({
		type: "get",
		async: false,
		url: "bookmark.toggle",
		data: {
			bookMarkedPostId: bookMarkedPostId,
			loginedUserId: loginedUserId,
			lat: null,
			lng: null,
			placeName: null
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

}
