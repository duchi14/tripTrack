var lat = new Array();
var lng = new Array();
var placeName = new Array();
var latlngsize = document.getElementsByName('lat').length;
var coloridx = 0;
var markers = [];
var idx = 0;
var start = 0;
var bounds = new kakao.maps.LatLngBounds();
var infowindow = new kakao.maps.InfoWindow({zIndex:1});
var strokeColor = ['#4169E1','#DA70D6','#008080','#00FA9A'];
var container = document.getElementById('map');
var options = {
         center: new kakao.maps.LatLng(37.566309, 126.977207),
         level: 3
   };
var map = new kakao.maps.Map(container, options);


function view(){
	for(var i = 0; i < latlngsize; i++){         
         lat.push(document.getElementsByName('lat')[i].value);
         lng.push(document.getElementsByName('lng')[i].value);   
         placeName.push(document.getElementsByName('placeName')[i].value);
          
          displayMarker(lat[i], lng[i], placeName[i]);
          bounds.extend(new kakao.maps.LatLng(Number(lat[i]), Number(lng[i])));
          
          if(markers.length > 1){
             getCarDirection();
             start += 1;
          }
}
map.setBounds(bounds);
}
window.view();
      
      
      
      
      
function displayMarker(lat, lng, placeName) {
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
        position: new kakao.maps.LatLng(lat, lng),
        image: markerImage,
    });
    markers.push(marker);
      marker.setMap(map);
      idx += 1;
      
      kakao.maps.event.addListener(marker, 'click', function() {
         infowindow.setContent('<div style="padding:5px;font-size:12px;">' + placeName + '</div>');
           infowindow.open(map, marker);
      });
}

async function getCarDirection() {
    const REST_API_KEY = '5ec8b6426bb92159822fc0685f9c0a1e';
    // 호출방식의 URL을 입력합니다.
    const url = 'https://apis-navi.kakaomobility.com/v1/directions';
   
   
   // 출발지(origin), 목적지(destination)의 좌표를 문자열로 변환합니다.
    const origin = `${lng[start]},${lat[start]}`; 
    const destination = `${lng[lng.length -1]},${lat[lat.length -1]}`;
   
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
  var polyline = new kakao.maps.Polyline({
    path: linePath,
    strokeWeight: 5,
    strokeColor: strokeColor[coloridx],
    strokeOpacity: 0.7,
    strokeStyle: 'solid'
  }); 
  polyline.setMap(map);
  coloridx += 1;
  if(coloridx >= markers.length){
     coloridx = 0;
  }
    } catch (error) {
      console.error('Error:', error);
    }
  }