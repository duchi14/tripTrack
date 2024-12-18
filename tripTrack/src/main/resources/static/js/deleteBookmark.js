
//북마크 토글 기능
function deleteBookmark(button) {
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
		url: "delBookmark.do",
		data: {
			bookMarkedPostId: bookMarkedPostId,
			loginedUserId: loginedUserId
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