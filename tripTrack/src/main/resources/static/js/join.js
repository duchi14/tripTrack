let chk = 0;

function joinForm(){
var user_id=document.join.user_id.value;
var user_pw=document.join.user_pw.value;
var email=document.join.email.value;
//var exptext = /^[A-Za-z0-9_\.\-]+[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
var nickname=document.join.nickname.value;
var addr=document.join.addr.value;
var gender=document.join.gender.value;
var tel=document.join.tel.value;


if(user_id==null||user_id==""){
   alert('아이디는 필수사항입니다.');
   document.join.user_id.focus();
   return;
}

if(user_pw==null||user_pw==""){
   alert('비밀번호는 필수사항입니다.');
   document.join.user_pw.focus();
   return;
}

if(email==null||email==""){
   alert('이메일은 필수사항입니다.');
   document.join.email.focus();
   return;
}

//if(exptext.test(email)==false){
   //이메일 형식이 알파벳+숫자@알파벳+숫자.알파벳+숫자 형식이 아닐경우         
 //  alert("이메일형식이 올바르지 않습니다.");

 //  document.join.email.focus();

 //  return false;
 //  }

if(nickname==null||nickname==""){
   alert('   닉네임은 필수사항입니다.');
   document.join.nickname.focus();
   return;
}

//if(addr==null||addr==""){
//   alert('주소는 필수사항입니다.');
//   document.join.addr.focus();
//   return;
//}

if(gender==null||gender==""){
   alert('성별은 필수사항입니다.');
   document.join.gender.focus();
   return;
}

if(tel==null||tel==""){
   alert('전화번호는 필수사항입니다.');
   document.join.tel.focus();
   return;
}
if(chk >= 5) {
document.join.submit();
alert('환영합니다!');
} else {
   alert('중복검사를 완료하지 않았습니다.');
}
}

function resetForm() {
   window.alert("정보를 지우고 처음부터 다시 입력합니다.");
   join.reset();
   join.user_id.focus();
}


function sample6_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    document.getElementById("sample6_extraAddress").value = extraAddr;
                
                } else {
                    document.getElementById("sample6_extraAddress").value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('sample6_postcode').value = data.zonecode;
                document.getElementById("sample6_address").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("sample6_detailAddress").focus();
            }
        }).open();
    }

//각종 중복검사 

$(function(){
    $("#checkId").click(function(){
    
        let user_id = $("#user_id").val();
        
           if (user_id === "") {
            $("#result_checkId").html("아이디를 입력해주세요.").css("color", "red");
            return;
        }
        
	     // 아이디 길이 확인
	    if (user_id.length > 30) {
	        $('#result_checkId').text("아이디를30자이내로 입력해주세요.").css("color", "red");
	        $('#user_id').val('').focus();
	        return false;
	    }
         
        $.ajax({
            type:'get', //get 형식으로 controller 에 보내기위함!!
            url:"checkId.do", // 컨트롤러로 가는 mapping 입력
            data: {"user_id":user_id}, // 원하는 값을 중복확인하기위해서  JSON 형태로 DATA 전송
            success: function(data){ 
                if(data == "N"){ // 만약 성공할시
                    result = "사용 가능한 아이디입니다.";
                    $("#result_checkId").html(result).css("color", "green");
                    $("#user_pw").trigger("focus");
                    chk += 1;       
             } else if(data == "Y") { // 만약 실패할시
                 let result="이미 사용중인 아이디입니다.";
                     $("#result_checkId").html(result).css("color","red");
                     $("#user_id").val("").trigger("focus");
             }
                 
         },
            error : function(error){alert(error);}
        });
        
    });
    
});

$(function() {
    $("#checkPassword").click(function() {
        let user_pw1 = $("#user_pw1").val();
        let user_pw2 = $("#user_pw2").val();
        let pw = /^(?=.*[a-zA-Z])(?=.*[!@#$%^&*+=-])(?=.*[0-9]).{8,16}$/; // 영문, 숫자, 특수문자를 모두 포함하고, 8~16자 길이를 허용합니다.
		
		if (user_pw1 === "" || user_pw2 === "") {
            $("#result_checkPassword").html("비밀번호를 입력해주세요.").css("color", "red");
            return;
        }
		
        if (!pw.test(user_pw1)) {
            $('#result_checkPassword').text("8~16자의 영문 대소문자 특수문자, 숫자로 입력하세요.").css("color", "red");
            $('#user_pw1').val('').focus();
            $('#user_pw2').val('');
            return false;
        }

        // 비밀번호를 서버로 전송하여 확인하는 로직을 추가합니다.
        $.ajax({
            type: 'get',
            url: 'checkPassword.do',
            data: { "user_pw1": user_pw1, "user_pw2": user_pw2 },
            success: function(data) {
                if (data === "Y") { // 비밀번호가 일치하는 경우
                    let result = "비밀번호가 일치합니다.";
                    $("#result_checkPassword").html(result).css("color", "green");
                    document.getElementById('user_pw1').readOnly = true;
                    document.getElementById('user_pw2').readOnly = true;
                     chk += 1; 
                } else if (data === "N") { // 비밀번호가 일치하지 않는 경우
                    let result = "비밀번호가 일치하지 않습니다.";
                    $("#result_checkPassword").html(result).css("color", "red");
                } else if (data === "Null") { // 비밀번호 입력이 비어 있는 경우
                    let result = "비밀번호를 입력해주세요.";
                    $("#result_checkPassword").html(result).css("color", "red");
                }
            },
            error: function(error) {
                alert("에러가 발생했습니다: " + error);
            }
        });
    });
});
// 버튼을 클릭하여 비밀번호를 표시하거나 숨기는 기능을 구현합니다.
$(document).ready(function() {
    const pwInput = $("#user_pw1");
    $('.pw_show input[type="checkbox"]').on('change', function() {
        if (this.checked) {
            pwInput.prop("type", "text");
        } else {
            pwInput.prop("type", "password");
        }
    });
});

$(function(){
    $("#checkEmail").click(function(){
    
        let email = $("#email").val();
        let exptext = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
        
        if (email === "") {
            $("#result_checkEmail").html("이메일을 입력해주세요.").css("color", "red");
            return;
        }
     
        if (!exptext.test(email)) {
            $('#result_checkEmail').text("@이랑.이 입력되지 않았습니다.").css("color", "red");
            $('#email').val('').focus();
            return false;
        }
         
        $.ajax({
            type:'get',
            url:"checkEmail.do",
            data: {"email" : email},
            success: function(data) { 
                if(data === "N"){ 
                    result = "사용 가능한 이메일입니다.";
                    $("#result_checkEmail").html(result).css("color", "green");
                     chk += 1;
             } else if(data == "Y") { 
                 let result = "이미 사용중인 이메일입니다.";
                 $("#result_checkEmail").html(result).css("color","red");
                 $("#email").val("").trigger("focus");
			 } 
                 
         },
            error : function(error){ alert(error); }
        });
        
    });
    
});

$(function(){
    $("#checkNickname").click(function(){
    
        let nickname = $("#nickname").val();
        let result;
        
       	// 닉네임 길이 확인
	    if (nickname.length > 15) {
	    $('#result_checkNickname').text("닉네임을15자이내로 입력해주세요.").css("color", "red");
	    $('#nickname').val('').focus();
	    return false;
	    }
         
        $.ajax({
            type:'get', //get 형식으로 controller 에 보내기위함!!
            url:"checkNickname.do", // 컨트롤러로 가는 mapping 입력
            data: {"nickname":nickname}, // 원하는 값을 중복확인하기위해서  JSON 형태로 DATA 전송
            success: function(data){ 
                if(data == "N"){ // 만약 성공할시
                    result = "사용 가능한 닉네임입니다.";
                    $("#result_checkNickname").html(result).css("color", "green");
                   chk += 1;
                 
             } else if (data == "Y") { // 만약 실패할시
                	let result="이미 사용중인 닉네임입니다.";
                     $("#result_checkNickname").html(result).css("color","red");
                     $("#nickname").val("").trigger("focus");
             } else if(data == "Null") {
					let result = "닉네임을 입력해주세요.";
				 	$("#result_checkNickname").html(result).css("color", "red");
			 }
                 
         },
            error : function(error){alert(error);}
        });
        
    });
    
});

$(function() {
    $("#checkTel").click(function() {
        let tel = $("#tel").val();
        let telPattern = /^01([0|1|6|7|8|9])(\d{4})(\d{4})$/;
		
		if (tel === "") {
            $("#result_checkTel").html("전화번호를 입력해주세요.").css("color", "red");
            return;
        }
        
        // 전화번호 길이 확인
        if (tel.length < 10 || tel.length > 11) {
            $('#result_checkTel').text("올바른 전화번호 형식이 아닙니다. (예: 01012345678)").css("color", "red");
            $('#tel').val('').focus();
            return false;
        }
		
        if (!telPattern.test(tel)) {
            $('#result_checkTel').text("올바른 전화번호 형식이 아닙니다. (예: 01012345678)").css("color", "red");
            $('#tel').val('').focus();
            return false;
        } else {
            // 전화번호를 서버로 전송하여 확인하는 로직을 추가합니다.
            $.ajax({
                type: 'get',
                url: 'checkTel.do',
                data: { "tel": tel },
                success: function(data) {
                    if (data === "Y") {
                        $('#result_checkTel').text("유효한 전화번호입니다.").css("color", "green");
                         chk += 1;
                         console.log(chk);
                    } else if(data == "N") {
                        $('#result_checkTel').text("유효하지 않은 전화번호입니다.").css("color", "red");
                    } 
                },
                error: function(error) {
                    alert("에러가 발생했습니다: " + error);
                }
            });
        }
    });
});

