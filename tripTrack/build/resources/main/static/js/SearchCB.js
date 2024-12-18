

var infowindow = new kakao.maps.InfoWindow({zIndex:1});
var ps = new kakao.maps.services.Places();
var markers = [];
var box = document.getElementById("latlngbox");
var newP = document.getElementById("ptag");

var lat = new Array();
var lng = new Array();
var search = new Array();

var container = document.getElementById('map');
var options = {
	center: new kakao.maps.LatLng(37.566309, 126.977207),
		level: 3
	};

	var map = new kakao.maps.Map(container, options);

function callSearch(){
	var search = document.getElementById('search').value;
	ps.keywordSearch(search, placeSearchCB)
	
	 if(markers.length > 0){
			 for(var i = 0; i < markers.length; i++){
				 markers[i].setMap(null);
			 }
			 markers = [];
			 markers.length = 0;
		 }

}

function placeSearchCB (data, status, pagination){
	
	
	if (status === kakao.maps.services.Status.OK) {

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
        // LatLngBounds 객체에 좌표를 추가합니다
        var bounds = new kakao.maps.LatLngBounds();

        for (var i=0; i<data.length; i++) {
            displayMarker(data[i]);    
            bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
        }       
		
        // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
        map.setBounds(bounds);
    } 
}




function displayMarker(place) {
   
    // 마커를 생성하고 지도에 표시합니다
    var marker = new kakao.maps.Marker({
        map: map,
        position: new kakao.maps.LatLng(place.y, place.x),
    });
    markers.push(marker);
        //마커위에 마우스를 올려 놓았을시 info이벤트 추가
	kakao.maps.event.addListener(marker, 'mouseover', function(){
		infowindow.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>');
        infowindow.open(map, marker);
	});
	
	//마커위에서 마우스가 벗어날시 info이벤트 삭제
	kakao.maps.event.addListener(marker, 'mouseout', function() {
   
   		infowindow.close();
	});

    // 마커에 클릭이벤트를 등록합니다
    kakao.maps.event.addListener(marker, 'click', function() {
        // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
        infowindow.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>');
        infowindow.open(map, marker);
        console.log(place.y);
         console.log(place.x);
		document.getElementsByName('lat')[0].value = place.y;
		document.getElementsByName('lng')[0].value = place.x;
		document.getElementsByName('placeName')[0].value = place.place_name;
		document.getElementsByName('title')[0].value = place.place_name;


});
}