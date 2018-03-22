// Load the Visualization API and the corechart package.
google.charts.load('current', {'packages':['corechart', 'bar']});

// Set a callback to run when the Google Visualization API is loaded.
google.charts.setOnLoadCallback(drawOverviewPieChart);
google.charts.setOnLoadCallback(drawUKPieChart);
google.charts.setOnLoadCallback(drawEUPieChart);
google.charts.setOnLoadCallback(drawUSCANPieChart);
google.charts.setOnLoadCallback(drawOverViewBarChart);

// Callback that creates and populates a data table,
// instantiates the pie chart, passes in the data and
// draws it.
function drawOverViewBarChart() {
    // Load data elements containing sentiment frequencies
    var ukSentimentFrequencies = document.getElementById("uk-sentiment-frequencies");
    var euSentimentFrequencies = document.getElementById("eu-sentiment-frequencies");
    var usCanSentimentFrequencies = document.getElementById("uscan-sentiment-frequencies");

    var ukNegativeFrequency = Number(ukSentimentFrequencies.dataset.negative);
    var ukNeutralFrequency = Number(ukSentimentFrequencies.dataset.neutral);
    var ukPositiveFrequency = Number(ukSentimentFrequencies.dataset.positive);

    var euNegativeFrequency = Number(euSentimentFrequencies.dataset.negative);
    var euNeutralFrequency = Number(euSentimentFrequencies.dataset.neutral);
    var euPositiveFrequency = Number(euSentimentFrequencies.dataset.positive);

    var usCanNegativeFrequency = Number(usCanSentimentFrequencies.dataset.negative);
    var usCanNeutralFrequency = Number(usCanSentimentFrequencies.dataset.neutral);
    var usCanPositiveFrequency = Number(usCanSentimentFrequencies.dataset.positive);

    var data = google.visualization.arrayToDataTable([
        ['Sentimnent', 'Negative', 'Neutral', 'Positive'],
        ['UK', ukNegativeFrequency, ukNeutralFrequency, ukPositiveFrequency],
        ['Europe', euNegativeFrequency, euNeutralFrequency, euPositiveFrequency],
        ['US & Canada', usCanNegativeFrequency, usCanNeutralFrequency, usCanPositiveFrequency]
    ]);

    var options = {
        title: 'Sentiment % composition by area',
        width: 400,
        height: 300,
        colors: ['red', 'blue', 'green'],
        legend: { position: 'top', maxLines: 3 },
        bar: { groupWidth: '35%' },
        isStacked: 'percent',
    };

    var chart = new google.visualization.ColumnChart(document.getElementById('bar_chart_div'));
    chart.draw(data, options);
}

function drawOverviewPieChart() {

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
        'width': 400,
        'height': 300,
        'is3D': true
    };

    // Instantiate and draw our chart, passing in some options.
    var chart = new google.visualization.PieChart(document.getElementById('overview_chart_div'));
    chart.draw(data, options);
}

function drawUKPieChart() {

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
    var options = {'title':'UK tweet breakdown by sentiment',
        'colors': ['green', 'blue', 'red'],
        'width': 500,
        'height': 300,
        'is3D': true
    };

    // Instantiate and draw our chart, passing in some options.
    var chart = new google.visualization.PieChart(document.getElementById('uk_chart_div'));
    chart.draw(data, options);
}

function drawEUPieChart() {

    var sentimentFrequencies = document.getElementById("eu-sentiment-frequencies");

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
    var options = {'title':'EU tweet breakdown by sentiment',
        'colors': ['green', 'blue', 'red'],
        'width': 500,
        'height': 300,
        'is3D': true
    };

    // Instantiate and draw our chart, passing in some options.
    var chart = new google.visualization.PieChart(document.getElementById('eu_chart_div'));
    chart.draw(data, options);
}

function drawUSCANPieChart() {

    var sentimentFrequencies = document.getElementById("uscan-sentiment-frequencies");

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
    var options = {'title':'EU tweet breakdown by sentiment',
        'colors': ['green', 'blue', 'red'],
        'width': 500,
        'height': 300,
        'is3D': true
    };

    // Instantiate and draw our chart, passing in some options.
    var chart = new google.visualization.PieChart(document.getElementById('uscan_chart_div'));
    chart.draw(data, options);
}