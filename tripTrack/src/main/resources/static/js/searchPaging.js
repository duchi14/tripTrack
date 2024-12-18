document.addEventListener("DOMContentLoaded", function() {
    // 현재 페이지 번호를 가져옵니다.
    var nowPage = document.getElementById("nowPage").value;
    
    // 모든 페이지 번호를 나타내는 span 요소를 가져옵니다.
    var pageSpans = document.querySelectorAll(".page");

    // 각 span 요소를 순회하면서 현재 페이지와 일치하는지 확인합니다.
    pageSpans.forEach(function(span) {
        var page = span.getAttribute("value");
        if (page === nowPage) {
            span.style.color = "#0277BD";
            span.style.fontWeight = "bold";
        }
    });
});
