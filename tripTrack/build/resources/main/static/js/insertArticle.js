
var fileNo = 1;
const dataTranster = new DataTransfer();
const imgbox = document.getElementById("file-imgList");
const imgnewP = document.createElement('p');
// 삭제 기능
const deleteButton = document.getElementById('delete-btn');
const dataTranster2 = new DataTransfer();
function newfile(){
	for(var i = 0; i < filesArr.size; i++){
		console.log(filesArr[i]);
	}
}

function removefile(button){
   var image = button.dataset.index;
   var imageId = document.getElementById(image);
   
   const filesArr = Array.from(document.getElementById('image').files);
   for(var i = 0; i < document.getElementById('image').files.length; i++){
      //버튼눌렀을시 버튼에 할당된 파일 마지막 업데이트 날짜와 input안에 있는 마지막날자를 비교
      if(image == filesArr[i].lastModified){
      //아이디 값이랑 파일안에 있는 마지막날짜가 같으면 dataTranster안에있는 파일 삭제
         dataTranster.items.remove(i);
         dataTranster2.items.remove(i);
         
         //dataTranster안에 있는 파일을 삭제한후 input에 덮어쓰기
         document.getElementById('image').files = dataTranster.files;
         //버튼 눌렀을떄 아이디값이랑 일치하는 p태그를 삭제
         imageId.remove();
      }
      
   }
}

function addfile(event){
      const file = event.files;
      const filebox = document.querySelector('#image_container');
      const fileInput = document.getElementById('image').files;
      const voidImg = document.getElementById('void_img');
      //파일 input 파일들을 배열로 변환
      const filesArr = Array.from(fileInput);
      var idx = 0;
      filesArr
      .forEach(file => {
         //파일에 접근하여 dataTranster에 추가
         dataTranster2.items.add(file);
      });   
      
      //dataTranster에 있는 파일 리스트를 input에 저장
   if(dataTranster2.items.length < 11){
      if(validation(file)){
      filesArr
      .forEach(file => {
         //파일에 접근하여 dataTranster에 추가
         dataTranster.items.add(file);
      });   
      document.getElementById('image').files = dataTranster.files;
      //반복문을 배열로 만든 파일리스트 만큼 돌면서 파일을 읽어옴
      for(var i =0; i< filesArr.length; i++){
         const reader = new FileReader();
         const url = URL.createObjectURL(fileInput[i]);
      /*
      filebox.innerHTML += '<p id="'+filesArr[i].lastModified+'">'+filesArr[i].name
      +'<button data-index="'+filesArr[i].lastModified+'" class="file-remove" onclick="removefile(this)"></button>'+
      '</p>';*/
      
      //파일을 읽어오는데 성공하였을시 동작하는 함수
      reader.onload = function(event){
         if(voidImg != null) {
         voidImg.remove();   
         }
         filebox.innerHTML += '<span class="img_span" id="'+filesArr[idx].lastModified+'">'
         +'<img width="100%" height="100%" src="'+event.target.result+'"/>'+
         '<button data-index="'
         +filesArr[idx].lastModified+
         '" class="file-remove" onclick="removefile(this)">'+'<i class="fa-solid fa-trash-can"></i>'+'</button>';
         idx += 1;
      }
         //배열안에 들어있는 파일에 있는 URL을 읽어온다.
         reader.readAsDataURL(filesArr[i]);
         
      }
               
      }
      }else {
      alert("최대 첨부 가능한 파일 갯수는 10개입니다.");
      dataTranster2.items.clear();
      document.querySelector("input[type=file]").value = "";
      document.getElementById('image').files = dataTranster.files;
      const filesArr2 = Array.from(document.getElementById('image').files);
      filesArr2
      .forEach(file => {
         //파일에 접근하여 dataTranster에 추가
         dataTranster2.items.add(file);
      });      
      return false;
      }
   }

function validation(obj){
   const fileTypes = ['image/gif','image/jpeg','image/png'];
   var maxfile = dataTranster2.files.length;
   for(var i = 0; i < obj.length; i++){
      if(!fileTypes.includes(obj[i].type)){
      alert("첨부가 불가능한 파일입니다.");
      document.querySelector("input[type=file]").value = "";
      return false;
   }else if(obj.length > 10){
      alert("최대 첨부 가능한 파일 갯수는 10개입니다.");
      document.querySelector("input[type=file]").value = "";
      return false;
   }else {
      alert("등록");
      return true;
   }
   }
   
}


// 수정 기능
const modifyButton = document.getElementById('modify-btn');



function sendReview(){
	
	
	var flag = false;
	var Idchk = false;
	for(var i = 1; i <= 20; i++){
			if(i%2 != 0){
				chk = document.getElementById('rate').querySelector('input:nth-child('+i+')')
				if(chk.checked == true){
					flag = true;
				}
			}
		}
		
	for(var i = 0; i < starPointlength; i++){
		if(document.getElementById('user' + i).value == document.getElementById('session').value){
			Idchk = true;
			break;
		}
	}
	if(frm.title.value ==""){
		alert('제목을 입력해주세요');
		frm.Reviewtitle.focus();
	}else if(frm.content.value == ""){
			alert('내용을 입력해주세요');
			frm.Reviewcontent.focus();
	}else if(!flag){
		alert('별점을 입력해주세요');
	}else if(Idchk){
		alert('이미 리뷰를 등록하셨습니다.')
	}
	else{
		frm.submit();
	}
	
	
}
window.starRview();


