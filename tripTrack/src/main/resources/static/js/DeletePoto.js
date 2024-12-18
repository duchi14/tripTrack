/**
 * 
 */

 function deletePoto(button){
	 var PotoId = button.previousElementSibling;
	 var image = PotoId.previousElementSibling;
	 var potoForm = button.parentNode;
	 
	 console.log(potoForm);
	 
	 potoForm.submit();
	 
	 PotoId.remove();
	 image.remove();
	 button.remove();
	 
 }