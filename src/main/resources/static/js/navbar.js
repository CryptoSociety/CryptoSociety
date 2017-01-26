$(document).ready(function () {
    var pathname = window.location.pathname;
    if (pathname.includes("/cryptos")) {
        $('.nav > li > a[href^="/../cryptos"]').parent().addClass('active');
    } else if (pathname.includes("/walkthroughs")) {
        $('.nav > li > a[href^="/../walkthroughs"]').parent().addClass('active');
    } else if (pathname.includes("/toolbox")) {
        $('.nav > li > a[href^="/../toolbox"]').parent().addClass('active');
    } else if (pathname.includes("/leaderboard")) {
        $('.nav > li > a[href^="/../leaderboard"]').parent().addClass('active');
    }
});