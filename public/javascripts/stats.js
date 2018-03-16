// Load the Visualization API and the corechart package.
google.charts.load('current', {'packages':['corechart']});

// Set a callback to run when the Google Visualization API is loaded.
google.charts.setOnLoadCallback(drawChart);

// Callback that creates and populates a data table,
// instantiates the pie chart, passes in the data and
// draws it.
function drawChart() {

    var tweets = document.getElementsByClassName("tweet");
    // instantiate map representing sentiment (key) and their frequencies (value)
    var sentimentFrequency = new Map();
    sentimentFrequency.set("Positive", 0);
    sentimentFrequency.set("Neutral", 0);
    sentimentFrequency.set("Negative", 0);


    for (i = 0; i < tweets.length; i++) {
        if (Number(tweets[i].dataset.sentiment) > 2) {
            sentimentFrequency.set("Positive", (sentimentFrequency.get("Positive")+1));
        } else if (Number(tweets[i].dataset.sentiment) === 2) {
            sentimentFrequency.set("Neutral", (sentimentFrequency.get("Neutral")+1));
        } else if (Number(tweets[i].dataset.sentiment) < 2) {
            sentimentFrequency.set("Negative", (sentimentFrequency.get("Negative")+1));
        }
    }

    // Create the data table.
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Sentiment');
    data.addColumn('number', 'Number of tweets');
    data.addRow(['Positive', sentimentFrequency.get("Positive")]);
    data.addRow(['Neutral', sentimentFrequency.get("Neutral")]);
    data.addRow(['Negative', sentimentFrequency.get("Negative")]);

    // Set chart options
    var options = {'title':'Tweet sentiment breakdown',
        'colors': ['green', 'blue', 'red'],
        'width': 800,
        'height': 400,
        'is3D': true
    };

    // Instantiate and draw our chart, passing in some options.
    var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
    chart.draw(data, options);
}