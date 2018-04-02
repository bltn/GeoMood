$(function () {
    $('ul#toolbar li a').each(function () {
        if ($(this).prop('href') == window.location.href) {
            $(this).addClass('current');
        }
    });
});
