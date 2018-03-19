// Load the Visualization API and the corechart package.
google.charts.load('current', {'packages':['corechart']});

// Set a callback to run when the Google Visualization API is loaded.
google.charts.setOnLoadCallback(drawOverviewChart);
google.charts.setOnLoadCallback(drawUKChart);

// Callback that creates and populates a data table,
// instantiates the pie chart, passes in the data and
// draws it.
function drawOverviewChart() {

    var sentimentFrequencies = document.getElementById("overview-sentiment-frequencies");

    var positiveFreq = Number(sentimentFrequencies.dataset.positive);
    var neutralFreq = Number(sentimentFrequencies.dataset.neutral);
    var negativeFreq = Number(sentimentFrequencies.dataset.negative);

    // Create the data table.
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Sentiment');
    data.addColumn('number', 'Number of tweets');
    data.addRow(['Positive', positiveFreq]);
    data.addRow(['Neutral', neutralFreq]);
    data.addRow(['Negative', negativeFreq]);

    // Set chart options
    var options = {'title':'Tweet breakdown by sentiment',
        'colors': ['green', 'blue', 'red'],
        'width': 500,
        'height': 300,
        'is3D': true
    };

    // Instantiate and draw our chart, passing in some options.
    var chart = new google.visualization.PieChart(document.getElementById('overview_chart_div'));
    chart.draw(data, options);
}

function drawUKChart() {

    var sentimentFrequencies = document.getElementById("uk-sentiment-frequencies");

    var positiveFreq = Number(sentimentFrequencies.dataset.positive);
    var neutralFreq = Number(sentimentFrequencies.dataset.neutral);
    var negativeFreq = Number(sentimentFrequencies.dataset.negative);

    // Create the data table.
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Sentiment');
    data.addColumn('number', 'Number of tweets');
    data.addRow(['Positive', positiveFreq]);
    data.addRow(['Neutral', neutralFreq]);
    data.addRow(['Negative', negativeFreq]);

    // Set chart options
    var options = {'title':'Tweet breakdown by sentiment',
        'colors': ['green', 'blue', 'red'],
        'width': 500,
        'height': 300,
        'is3D': true
    };

    // Instantiate and draw our chart, passing in some options.
    var chart = new google.visualization.PieChart(document.getElementById('uk_chart_div'));
    chart.draw(data, options);
}