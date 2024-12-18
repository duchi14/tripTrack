function loginForm(){
var username = document.login.username.value;
var password = document.login.password.value;

if(username==null||username==""){
	alert('아이디가 일치하지 않습니다!');
	document.login.username.focus();
	return;
}

if(password==null||password==""){
	alert('비밀번호가 일치하지않습니다!');
	document.login.password.focus();
	return;
}

document.login.submit();
}

// 버튼을 클릭하여 비밀번호를 표시하거나 숨기는 기능을 구현합니다.
$(document).ready(function() {
    const pwInput = $("#password");
    $('.pw_show input[type="checkbox"]').on('change', function() {
        if (this.checked) {
            pwInput.prop("type", "text");
        } else {
            pwInput.prop("type", "password");
        }
    });
});