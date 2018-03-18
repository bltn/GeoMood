/**
 * Function called as a callback by the Google Maps Javascript API
 */
function initMap() {
    // Retrieve tweet data from html -data attributes
    var tweets = document.getElementsByClassName("tweet");

    var iconBase = 'https://maps.google.com/mapfiles/kml/shapes/';

    var icons = {
        parking: {
            name: 'Parking',
            icon: iconBase + 'parking_lot_maps.png'
        },
        library: {
            name: 'Library',
            icon: iconBase + 'library_maps.png'
        },
        info: {
            name: 'Info',
            icon: iconBase + 'info-i_maps.png'
        }
    }

    if (tweets.length > 0) {
        // Start the map focused on the first tweet
        var firstTweetLocation;
        firstTweetLocation = {lat: Number(tweets[0].dataset.lat), lng: Number(tweets[0].dataset.lng)}
        // construct the Google map
        var map = constructGoogleMap('map', firstTweetLocation);

        // fetch icons to represent negative, neutral and positive sentiment
        var negative_icon = "https://maps.gstatic.com/mapfiles/ms2/micons/red-dot.png";
        var neutral_icon = "https://maps.gstatic.com/mapfiles/ms2/micons/blue-dot.png";
        var positive_icon = "https://maps.gstatic.com/mapfiles/ms2/micons/green-dot.png";

        addTweetMarkersToMap(tweets, map);
    }

    var legend = document.getElementById('legend');

    var positiveIconDiv = document.createElement('div');
    positiveIconDiv.innerHTML = '<img src="' + positive_icon + '"> ' + "Positive sentiment";
    legend.appendChild(positiveIconDiv);

    var neutralIconDiv = document.createElement('div');
    neutralIconDiv.innerHTML = '<img src="' + neutral_icon + '"> ' + "Neutral sentiment";
    legend.appendChild(neutralIconDiv);

    var negativeIconDiv = document.createElement('div');
    negativeIconDiv.innerHTML = '<img src="' + negative_icon + '"> ' + "Negative sentiment";
    legend.appendChild(negativeIconDiv);

    map.controls[google.maps.ControlPosition.RIGHT_BOTTOM].push(legend);
}

function constructGoogleMap(htmlContainerId, firstTweetLocation) {
    var map = new google.maps.Map(document.getElementById(htmlContainerId), {
        zoom: 4,
        center: firstTweetLocation,
        mapTypeId: 'roadmap'
    });
    return map;
}

function addTweetMarkersToMap(tweets, map) {
    // fetch icons to represent negative, neutral and positive sentiment
    var negative_icon = "https://maps.gstatic.com/mapfiles/ms2/micons/red-dot.png";
    var neutral_icon = "https://maps.gstatic.com/mapfiles/ms2/micons/blue-dot.png";
    var positive_icon = "https://maps.gstatic.com/mapfiles/ms2/micons/green-dot.png";

    // overlay each tweet onto its coordinates on the map, with icons color-coded by sentiment
    for(var i=0; i < tweets.length; i++) {
        var tweetLatitude = Number(tweets[i].dataset.lat);
        var tweetLongitude = Number(tweets[i].dataset.lng);
        var sentiment = Number(tweets[i].dataset.sentiment);
        var tweetIcon;

        // assign icon based on sentiment value
        if (sentiment <= 1) {
            tweetIcon = negative_icon;
        } else if (sentiment === 2) {
            tweetIcon = neutral_icon;
        } else if (sentiment > 2) {
            tweetIcon = positive_icon;
        }

        new google.maps.Marker({
            position: {lat: tweetLatitude, lng: tweetLongitude},
            map: map,
            icon: tweetIcon,
            title: tweets[i].dataset.text
        });
    }
}