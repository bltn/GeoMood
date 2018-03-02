function initMap() {
    // Start the map focused on Birmingham
    var birmingham = {lat: 52.4862, lng: 1.8904};
    var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 4,
        center: birmingham,
        mapTypeId: 'roadmap'
    });

    // fetch icons to represent negative, neutral and positive sentiment
    var negative_icon = "https://maps.gstatic.com/mapfiles/ms2/micons/red-dot.png";
    var neutral_icon = "https://maps.gstatic.com/mapfiles/ms2/micons/blue-dot.png";
    var positive_icon = "https://maps.gstatic.com/mapfiles/ms2/micons/green-dot.png";

    var tweets = document.getElementsByClassName("tweet");

    // loop over all tweet coordinates and overlay them onto the map
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
            icon: tweetIcon
        });
    }
}