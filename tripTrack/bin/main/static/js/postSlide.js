//슬라이드 관리
var thumbSlide = new Swiper('.img_slide_thumbs', {
	spaceBetween: 3,
	direction: "vertical",
	slidesPerView: 3,
	freeMode: true,
	loopedSlides: 3, //looped slides should be the same  
	watchSlidesVisibility: true,
	watchSlidesProgress: true,
});
var topSlide = new Swiper('.img_slide_top', {
	spaceBetween: 10,
	loop: true,
	loopedSlides: 3, //looped slides should be the same  
	navigation: {
		nextEl: '.swiper-button-next',
		prevEl: '.swiper-button-prev',
	},
	thumbs: {
		swiper: thumbSlide,
	},
});