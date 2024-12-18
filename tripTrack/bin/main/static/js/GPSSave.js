
var ps = new kakao.maps.services.Places();
var markers = [];
var Smarkers = [];
var infoflag = true;
var infoflag2 = true;
var points = [];
var Totlat = new Array();
var Totlng = new Array();
var search = new Array();
var coloridx = 0;
var Polyline = [];
var box = document.getElementById("latlngbox");
var newP = document.getElementById("ptag");
var postIdBox = document.getElementById("postIdBox");
var start = 0;
var latsize = document.getElementsByName('Booklat').length;
var idx = 0;
var strokeColor = ['#4169E1','#DA70D6','#008080','#00FA9A'];
var bounds = new kakao.maps.LatLngBounds();
var infowindow = new kakao.maps.InfoWindow({zIndex:1});
var container = document.getElementById('map');
var options = {
	center: new kakao.maps.LatLng(37.566309, 126.977207),
		level: 3
	};
var map = new kakao.maps.Map(container, options);

function GPSsave(button){

	var placeName = button.previousElementSibling;
	var lngtest = placeName.previousElementSibling;
	var lattest = lngtest.previousElementSibling;
	
	
	var lng = lngtest.value;
	var lat = lattest.value;
	var flag = true;
	
	if(markers.length >= 10){
		alert('입력될수 있는 경로를 초과하였습니다.')
	}else {
		
		if(markers.length >= 1){
			for(var i = 0; i < markers.length; i++){
				if(placeName.value === search[i]){
					alert("지도 상에 마커가 존재합니다.");
					flag = false;
				}
			}
			if(flag) {
				displayMarker(lat, lng, placeName);
				bounds.extend(new kakao.maps.LatLng(lat, lng));
				map.setBounds(bounds);
			}	
		}else{
			
			displayMarker(lat, lng, placeName);
			bounds.extend(new kakao.maps.LatLng(lat, lng));
			map.setBounds(bounds);
		}
		if(markers.length > 1 && flag){
			getCarDirection();
			start += 1;
		}
	}
	
	 if(Smarkers.length > 0){
			 for(var i = 0; i < Smarkers.length; i++){
				 Smarkers[i].setMap(null);
			 }
			Smarkers = [];
			Smarkers.length = 0;
	}
    var bookmarkDiv = button.closest('.bookmark');
    var postId = bookmarkDiv.getAttribute('data-postId');
  
    postIdBox.innerHTML += '<input type="hidden" id="selectedPostId" name="post_id" value="' + postId + '">';
	
	button.dataset.bookmarked = "true";
}



async function getCarDirection() {
	
    const REST_API_KEY = '5ec8b6426bb92159822fc0685f9c0a1e';
    // 호출방식의 URL을 입력합니다.
    const url = 'https://apis-navi.kakaomobility.com/v1/directions';
	
	
   // 출발지(origin), 목적지(destination)의 좌표를 문자열로 변환합니다.
    const origin = `${Totlng[start]},${Totlat[start]}`; 
    const destination = `${Totlng[Totlng.length -1]},${Totlat[Totlat.length -1]}`;
   
    // 요청 헤더를 추가합니다.
    const headers = {
      Authorization: `KakaoAK ${REST_API_KEY}`,
      'Content-Type': 'application/json'
    };
  
    // 표3의 요청 파라미터에 필수값을 적어줍니다.
    const queryParams = new URLSearchParams({
      origin: origin,
      destination: destination

       
    });
    
    const requestUrl = `${url}?${queryParams}`; // 파라미터까지 포함된 전체 URL

    try {
      const response = await fetch(requestUrl, {
        method: 'GET',
        headers: headers
      });

      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }

      const data = await response.json();
      const linePath = [];
  
  	  data.routes[0].sections[0].roads.forEach(router => {
      router.vertexes.forEach((vertex, index) => {
       // x,y 좌표가 우르르 들어옵니다. 그래서 인덱스가 짝수일 때만 linePath에 넣어봅시다.
       // 저도 실수한 것인데 lat이 y이고 lng이 x입니다.
      if (index % 2 === 0) {
        linePath.push(new kakao.maps.LatLng(router.vertexes[index + 1], router.vertexes[index]));
      }
    });
  });
    polyline = new kakao.maps.Polyline({
    path: linePath,
    map: map,
    strokeWeight: 5,
    strokeColor: strokeColor[coloridx],
    strokeOpacity: 0.7,
    strokeStyle: 'solid'
  }); 
  Polyline.push(polyline);
  coloridx += 1;
  if(coloridx >= markers.length){
	  coloridx = 0;
  }
    } catch (error) {
      console.error('Error:', error);
    }
  }
function displayMarker(lat, lng, placeName) {
	Totlat.push(lat);
	Totlng.push(lng);
	 var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(36, 37),  // 마커 이미지의 크기
        imgOptions =  {
            spriteSize : new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
            spriteOrigin : new kakao.maps.Point(0, (idx*46)+10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
            offset: new kakao.maps.Point(13, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
    // 마커를 생성하고 지도에 표시합니다
      markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
      marker = new kakao.maps.Marker({
        map: map,
        position: new kakao.maps.LatLng(lat, lng),
        image: markerImage,
    });
    markers.push(marker);
    newP.innerHTML += "<input name='lat' type='hidden'>"+"<input name='lng' type='hidden'>"+"<input name='placeName' type='hidden'><span>"+placeName.value+"</span>";
   	box.appendChild(newP);
   	var Rutelatlng = document.getElementsByName('lat').length;
   	search.push(placeName.value);
   	for(var i = 0; i < Rutelatlng; i++){
				if(document.getElementsByName('lat')[i].value === "" || document.getElementsByName('lng')[i].value === "" || document.getElementsByName('placeName')[i].value === ""){
					 document.getElementsByName('lat')[i].value = Totlat[i];
					 document.getElementsByName('lng')[i].value = Totlng[i];
					 document.getElementsByName('placeName')[i].value = search[i];		
				 }
			 }
    idx +=1;

    	// 마커에 클릭이벤트를 등록합니다
		 kakao.maps.event.addListener(marker, 'click', function() {
			 if(infoflag){
				 infowindow.setContent('<div style="padding:5px;font-size:12px;">' + placeName.value + '</div>');
        		 infowindow.open(map, marker);
        		 infoflag = false;
			 }else if(!infoflag){
				 infowindow.close();
				 infoflag = true;
			 }
		
		});
}

function deleteRute (){
	for(var i = 0; i < markers.length; i++){
		  markers[i].setMap(); 
	}
	for(var i = 0; i < Polyline.length; i++){
		Polyline[i].setMap();
	}
	while(newP.firstChild){
			newP.removeChild(newP.firstChild);	
		}
		infowindow.close();
	  	markers.length = 0;
	  	console.log(markers.length);
	  	start = 0;
	  	Totlat.length = 0;
	  	Totlng.length = 0;
	  	search.length = 0;
	  	idx = 0;
}


function callSearch(){
	var Callsearch = document.getElementById('search').value;
	var Cflag = true;
	//등록된 마커가 1개 이상있을시 실행
	if(search.length >= 1){
		//검색한 위치가 이미 등록된 위치인지 확인
	for(var i = 0; i < search.length; i++ ){
		if(Callsearch === search[i]){
				 if(markers.length > 0){
					 alert("지도 상에 마커가 존재합니다.");
					//이미 등록된 위치가 존재하면 검색후 등록한 마커 삭제
				 for(var i = 0; i < Smarkers.length; i++){
					 Smarkers[i].setMap(null);
				 }
				 Smarkers = [];
				 Smarkers.length = 0;
				 Cflag = false;
				 break;
		 		}
			}
		}
		//지도상에 등록된 마커가 존재하지 않으면 검색
		if(Cflag){
			ps.keywordSearch(Callsearch, placeSearchCB);
		}
	}
	//등록된 마커가 없을시 실행
	else{
		ps.keywordSearch(Callsearch, placeSearchCB);
	}
	 
	 //지도위 다른 마커가 있다면 다른 마커들 삭제
	 if(Smarkers.length > 0){
			 for(var i = 0; i < Smarkers.length; i++){
				 Smarkers[i].setMap();
			 }
			 Smarkers = [];
			Smarkers.length = 0;
		 }
	document.getElementById('search').value = "";
}

function placeSearchCB (data, status, pagination){
	
	
	if (status === kakao.maps.services.Status.OK) {

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
        // LatLngBounds 객체에 좌표를 추가합니다
        var bounds = new kakao.maps.LatLngBounds();

        for (var i=0; i<data.length; i++) {
            SearchMarker(data[i]);    
            bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
        }       
		
        // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
        map.setBounds(bounds);
    } 
}

function SearchMarker(place) {
   var Sflag = true;
    // 마커를 생성하고 지도에 표시합니다
    var Smarker = new kakao.maps.Marker({
        map: map,
        position: new kakao.maps.LatLng(place.y, place.x),
    });
    Smarkers.push(Smarker);
    
    //마커위에 마우스를 올려 놓았을시 info이벤트 추가
	kakao.maps.event.addListener(Smarker, 'mouseover', function(){
		infowindow.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>');
        infowindow.open(map, Smarker);
	});
	
	//마커위에서 마우스가 벗어날시 info이벤트 삭제
	kakao.maps.event.addListener(Smarker, 'mouseout', function() {
   
   		infowindow.close();
	});
   
    // 마커에 클릭이벤트를 등록합니다
    kakao.maps.event.addListener(Smarker, 'click', function() {
		//마커 클릭시 등록 되있던 마커를 찾는다.
		 for(var i = 0; i < Smarkers.length; i++){
			if(place.place_name === search[i]){
					 alert('마커를 이미 등록하셨습니다.');
					 Sflag = false;
					 break;
					
			} 
		 }
		 //등록된 마커가 존재 할 시 모든 검색 마커를 삭제
		 if(!Sflag){
			 for(var i =0; i < Smarkers.length; i++){
				 Smarkers[i].setMap();
			 }
			  Smarkers = [];
			  Smarkers.length = 0;
			  infowindow.close();
		 }
		 //등록된 마커가 없을시 마커를 지도상에 표시
		if(Sflag){
		infowindow.close();
		Totlat.push(place.y);
		Totlng.push(place.x);
		
		var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(36, 37),  // 마커 이미지의 크기
        imgOptions =  {
            spriteSize : new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
            spriteOrigin : new kakao.maps.Point(0, (idx*46)+10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
            offset: new kakao.maps.Point(13, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        }
   		 // 마커를 생성하고 지도에 표시합니다
      	markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
     	marker = new kakao.maps.Marker({
        map: map,
        position: new kakao.maps.LatLng(place.y, place.x),
        image: markerImage,
    	});
    	
    	//검색 마커가 아닌 클릭하여 등록한 마커를 저장
		markers.push(marker);
    	newP.innerHTML += "<input name='lat' type='hidden'>"+"<input name='lng' type='hidden'>"+"<input name='placeName' type='hidden'><span>"+place.place_name+"</span>";
     	
     	//클릭시 마커위에 인포 생성
        //새로운 마커위에 클릭이벤트 추가
        kakao.maps.event.addListener(marker, 'click', function() {
			if(infoflag){
				infowindow.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>');
	        	infowindow.open(map, marker);
	        	infoflag = false;
			}else if(!infoflag){
				infowindow.close();
				infoflag = true;
			}
		});
		
		  //클릭시 다른 검색 했던마크를 삭제
         if(Smarkers.length > 0){
			 for(var i = 0; i < Smarkers.length; i++){
				 Smarkers[i].setMap();
			 }
			 Smarkers = [];
			Smarkers.length = 0;
			 
		}
		
		//지도상에 마커가 하나 이상있을시 길찾기 기능 작동
		if(markers.length > 1){
			getCarDirection();
			infowindow.close();
			start += 1;
		}
		//해당 위치로 맵 이동 및 맵 줌 단계 조정
		bounds.extend(new kakao.maps.LatLng(place.y, place.x));
		map.setBounds(bounds);

		//클릭한 좌표의 좌표값을 가져오는 기능
        var Rutelatlng = document.getElementsByName('lat').length;
   		search.push(place.place_name);
   		for(var i = 0; i < Rutelatlng; i++){
				if(document.getElementsByName('lat')[i].value === "" || document.getElementsByName('lng')[i].value === "" || document.getElementsByName('placeName')[i].value === ""){
					 document.getElementsByName('lat')[i].value = Totlat[i];
					 document.getElementsByName('lng')[i].value = Totlng[i];
					 document.getElementsByName('placeName')[i].value = search[i];		
				 }
			 }
   		idx +=1;
   		}
});

}

