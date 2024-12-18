const main_slide = new Swiper('.main_slide', {
				spaceBetween : 30,
				centeredSlides : true,
				loop : true,
				autoplay : {
					delay : 50000,
					disableOnInteraction : false,
				},
				pagination : {
					el : ".swiper-pagination",
					clickable : true,
				},
				navigation : {
					nextEl : ".swiper-button-next",
					prevEl : ".swiper-button-prev",
				},
			});
						const placeList_slide = new Swiper(".placeList_slide", {
				  spaceBetween: 30,
				  slidesPerView : '3',
				  loop:true,
				  navigation: {
				    nextEl: ".swiper-button-next",
				    prevEl: ".swiper-button-prev",
				  },
				});	
							const accList_slide = new Swiper(".accList_slide", {
				  spaceBetween: 30,
				  slidesPerView : '3',
				  loop:true,
				  navigation: {
				    nextEl: ".swiper-button-next",
				    prevEl: ".swiper-button-prev",
				  },
				});	
			
			const restList_slide = new Swiper(".restList_slide", {
				  spaceBetween: 30,
				  slidesPerView : '3',
				  loop:true,
				  navigation: {
				    nextEl: ".swiper-button-next",
				    prevEl: ".swiper-button-prev",
				  },
				});	