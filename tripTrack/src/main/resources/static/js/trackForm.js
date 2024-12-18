function trackForm() {
    var title = document.querySelector('input[name="title"]').value;
    var content = document.querySelector('textarea[name="content"]').value;
    var bookmarked = false;
    var gpsSaveButtons = document.querySelectorAll('input.GPSsave');

    gpsSaveButtons.forEach(button => {
        if (button.dataset.bookmarked === "true") {
            bookmarked = true;
        }
    });

    if (!title) {
        alert('제목은 필수사항입니다.');
        document.querySelector('input[name="title"]').focus();
        return;
    }

    if (!content) {
        alert('내용은 필수사항입니다.');
        document.querySelector('textarea[name="content"]').focus();
        return;
    }
    
//    if (!bookmarked) {
//        alert('북마크는 필수사항입니다.');
//        return;
//    }
    
    alert('작성이완료되었습니다!');
    document.forms['upload'].submit();
}


function delBookmark(button) {
	const parentElement = button.parentElement;
	const postIdInput = parentElement.querySelector('input[name="post_id"]');
	const loginedUserId = document.getElementById('loginedUserId').value;
	const postIdValue = postIdInput.value;
	console.log(loginedUserId)
    	$.ajax({
		type: "get",
		async: false,
		url: "delBookmark.do",
		data: {
			postIdValue : postIdValue,
			loginedUserId: loginedUserId,
		},
		success: function(message) {
			alert(message);
			parentElement.remove();
		},
		error: function() {
			alert("에러가 발생헀습니다.");
		},
		complete: function() {
		}
	});
}
