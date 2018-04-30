window.onload = function() {

    var words = document.getElementsByClassName("word");
    var wordsArray = new Array();

    for (i = 0; i < words.length; i++) {
        var txt = words[i].dataset.text;
        var count = Number(words[i].dataset.count);

        var option = {};
        option[text] = txt;
        option[size] = count;

        wordsArray.push(option);
    }

    d3.wordcloud()
        .size([800, 400])
        .selector('#wordcloud')
        .words(wordsArray)
        .start();
}