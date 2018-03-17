// Load the Visualization API and the corechart package.
google.charts.load('current', {'packages':['corechart']});

// Set a callback to run when the Google Visualization API is loaded.
google.charts.setOnLoadCallback(drawChart);

// Callback that creates and populates a data table,
// instantiates the pie chart, passes in the data and
// draws it.
function drawChart() {

    var positiveSentimentFreq = Number(document.getElementsByClassName("positive-sentiment")[0].dataset.frequency);
    var neutralSentimentFreq = Number(document.getElementsByClassName("neutral-sentiment")[0].dataset.frequency);
    var negativeSentimentFreq = Number(document.getElementsByClassName("negative-sentiment")[0].dataset.frequency);

    // Create the data table.
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Sentiment');
    data.addColumn('number', 'Number of tweets');
    data.addRow(['Positive', positiveSentimentFreq]);
    data.addRow(['Neutral', neutralSentimentFreq]);
    data.addRow(['Negative', negativeSentimentFreq]);

    // Set chart options
    var options = {'title':'Tweet breakdown by sentiment',
        'colors': ['green', 'blue', 'red'],
        'width': 500,
        'height': 300,
        'is3D': true
    };

    // Instantiate and draw our chart, passing in some options.
    var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
    chart.draw(data, options);
}