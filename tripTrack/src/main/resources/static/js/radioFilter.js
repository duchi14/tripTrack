const categoryItems = document.querySelectorAll('.category_item');

		//태그 요소들을 가져옵니다.
		var tagItems = document.querySelectorAll('.tag_item');
		//각 카테고리 라디오 버튼에 대해 클릭 이벤트 리스너를 추가합니다.
		categoryItems.forEach(function(categoryItem) {
			categoryItem.addEventListener('click', function() {
				// 클릭한 카테고리 라디오 버튼의 값(value)을 가져옵니다.
				var selectedCategory = this.value;

				// 모든 태그 요소에 대해 반복합니다.
				tagItems.forEach(function(tagItem) {
					// 태그 요소의 data-catName 속성을 가져옵니다.
					var catName = tagItem.getAttribute('data-catName');

					// 만약 클릭한 카테고리와 태그의 data-catName이 다르다면 숨깁니다.
					if (selectedCategory !== catName) {
						tagItem.style.display = 'none';
					} else {
						tagItem.style.display = 'inline';
					}
				});
			});
		});

		tagItems.forEach(function(tagItem) {
			let catName = tagItem.getAttribute('data-catName');

			if (catName !== 'place') {
				tagItem.style.display = 'none';
			}
		});