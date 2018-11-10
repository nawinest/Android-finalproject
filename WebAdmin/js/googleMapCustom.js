function initMap() {
    var mapOptions = {
        center: new google.maps.LatLng(13.736717, 100.523186),
        zoom: 13,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    var map = new google.maps.Map(document.getElementById('map_canvas'),
        mapOptions);

    var input = document.getElementById('searchTextField');

    var autocomplete = new google.maps.places.Autocomplete(input);

    autocomplete.bindTo('bounds', map);

    var infowindow = new google.maps.InfoWindow();
    var marker = new google.maps.Marker({
        map: map,
        icon: {
          url : "asset/icon-map.png"
        },
        draggable: true
    });

    google.maps.event.addListener(autocomplete, 'place_changed', function() {
        infowindow.close();
        var place = autocomplete.getPlace();

        if (place.geometry.viewport) {
            map.fitBounds(place.geometry.viewport);
        } else {
            map.setCenter(place.geometry.location);
            map.setZoom(17); // Why 17? Because it looks good.
        }


        marker.setPosition(place.geometry.location);

        var address = '';
        if (place.address_components) {
            address = [(place.address_components[0] && place.address_components[0].short_name || ''), (place.address_components[1] && place.address_components[1].short_name || ''), (place.address_components[2] && place.address_components[2].short_name || '')].join(' ');
        }

        updateTextFields(place.geometry.location.lat(), place.geometry.location.lng());
        infowindow.setContent('<div><strong>' + place.name + '</strong><br>' + address + "<br>" + place.geometry.location);
        infowindow.open(map, marker);
    });

    google.maps.event.addListener(marker, 'dragend', function() {
        console.log(marker.getPosition().lat());
        console.log(marker.getPosition().lng());
        var lat = marker.getPosition().lat();
        var lng = marker.getPosition().lng();
        // $('#lat').val(lat);
        // $('#lat').text = lat;
        // $('#lng').val(lng);
        // $('#lng').text = lng;
        updateTextFields(lat, lng);
    })




}
// google.maps.event.addDomListener(window, 'load', initMap());

function updateTextFields(lat, lng) {
    $('#lat').val(lat);
    $('#lng').val(lng);
}







// var map;

// function initMap() {
//     var uluru = { lat: -25.363, lng: 131.044 };
//     map = new google.maps.Map(document.getElementById('map'), {
//         zoom: 4,
//         center: uluru

//     });
//     var marker = new google.maps.Marker({
//         position: uluru,
//         map: map,
//         draggable: true
//     });
//     google.maps.event.addListener(marker, 'dragend', function() {
//         console.log(marker.getPosition().lat());
//         console.log(marker.getPosition().lng());
//         var lat = marker.getPosition().lat();
//         var lng = marker.getPosition().lng();
//         $('#lat').val(lat);
//         $('#lat').text = lat;
//         $('#lng').val(lng);
//         $('#lng').text = lng;
//     })


//     google.maps.event.addDomListener(window, 'load', function() {
//         var input = document.getElementById('searchTextField');
//         var again = new google.maps.places.Autocomplete(input);

//     });


//     // var searchmap = new google.maps.places.SearchBox(document.getElementById('autosearch'));
// }