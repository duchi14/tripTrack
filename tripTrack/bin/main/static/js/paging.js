		    document.addEventListener("DOMContentLoaded", function() {
		        // 현재 페이지 번호를 가져옵니다.
		        var nowPage = document.getElementById("nowPage").value;
		        
		        // 모든 페이지 번호를 나타내는 span 요소를 가져옵니다.
		        var pageSpans = document.querySelectorAll(".page");
	
		        // 각 span 요소를 순회하면서 현재 페이지와 일치하는지 확인합니다.
		        pageSpans.forEach(function(span) {
		            var page = span.getAttribute("data-page");
		            if (page === nowPage) {
		                // 일치하는 경우 배경색을 변경합니다.
// 		                span.style.backgroundColor = "#0277BD"; // 원하는 배경색으로 변경
		                span.style.color = "#0277BD";
		                span.style.fontWeight = "bold";
		            }
		        });
		    });
