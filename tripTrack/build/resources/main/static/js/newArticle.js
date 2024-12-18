  	function newArticleForm() {
  			var search = document.getElementById("search").value;
            var imageInput = document.getElementById("image");
            var image = imageInput.files[0]; 
            var category = document.querySelector('input[name="category_name"]:checked');
            var location = document.querySelector('input[name="city_name"]:checked');
            var tag = document.querySelector('input[name="tag_name"]:checked');
            var title = document.querySelector('input[name="title"]').value;
            var content = document.querySelector('textarea[name="content"]').value;

            if (!search) {
                alert('제목은 필수사항입니다.');
                document.getElementById("search").focus();
                return;
            }

            if (!image) {
                alert('이미지는 필수사항입니다.');
                imageInput.focus();
                return;
            }

            if (!category || !location || !tag) {
                alert('테마버튼은 필수사항입니다.');
                if (!category) document.querySelector('input[name="category_name"]').focus();
                else if (!location) document.querySelector('input[name="city_name"]').focus();
                else document.querySelector('input[name="tag_name"]').focus();
                return;
            }

            if (!title) {
                alert('제목은 필수사항입니다.');
                document.querySelector('input[name="title"]').focus();
                return;
            }

            if (!content) {
                alert('명소작성는 필수사항입니다.');
                document.querySelector('textarea[name="content"]').focus();
                return;
            }
			
            alert('작성이완료되었습니다!');
            document.forms['upload'].submit();
            }