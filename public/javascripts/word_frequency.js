// Load the Visualization API and the corechart package.
google.charts.load('current', {'packages':['corechart', 'bar']});

// Set a callback to run when the Google Visualization API is loaded.
google.charts.setOnLoadCallback(drawWordFrequencyBarChart);

// Callback that creates and populates a data table,
// instantiates the pie chart, passes in the data and
// draws it.
function drawWordFrequencyBarChart() {
    var words = document.getElementsByClassName("word");

    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Word');
    data.addColumn('number', 'Word frequency');

    for (i = 0; i < words.length; i++) {
        data.addRow([words[i].dataset.text, Number(words[i].dataset.count)]);
    }

    var options = {
        title: 'Commonly associated terms',
        width: 800,
        height: 600,
        colors: ['red', 'blue', 'green'],
        legend: { position: 'top', maxLines: 3 },
        bar: { groupWidth: '35%' }
    };

    var chart = new google.visualization.BarChart(document.getElementById('word_frequency_chart'));
    chart.draw(data, options);
}