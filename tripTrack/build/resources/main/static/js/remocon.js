const rollUpBtn = document.querySelector('#rollUp');
const rollDownBtn = document.querySelector('#rollDown');

rollUpBtn.onclick = () => {
	window.scrollTo({top:0, behavior: "smooth"});
};

rollDownBtn.onclick = () => {
	  window.scrollTo({ top: document.body.scrollHeight, behavior: "smooth" });
	};
