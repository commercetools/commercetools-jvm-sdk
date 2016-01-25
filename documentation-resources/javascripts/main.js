(function () {
    var headers = $("h3").not(".toc-ignore");

    var toc = "";

    headers.each(function() {
        var element = $(this);
        var title = element.text();
        var anker = "#" + element.attr("id");

        if(anker.length > 1 && anker != "#undefined" && title.length > 0) {
            toc += "<li><a href='" + anker + "'>" + title + "</a></li>";
        }
    });

    if(toc.length > 0) {
        $(".contentContainer", 0).prepend(
            "<nav role='navigation' id='page-toc' class='table-of-contents'><strong>Table of Contents</strong><ul>" +
            toc + "</ul></nav>");
    }

    //moves this blocks up before the package listings
    var pullUpClass = "pull-up";
    $("." + pullUpClass).appendTo(".subTitle", 0).removeClass(pullUpClass);

    $(".hide").hide();

    var importShowButton = $("button.reveal-imports");
    importShowButton.show();
    importShowButton.click(function(eventObject) {
        $(this).siblings(".code-example-imports").toggle();
    });

    $(".rn-hidden").css("display", "none");
})();

// ReleaseNotes - toggling hidden div

$(".rn-hidden").before('<div class="more-thing"><a class="show-more" href="#">more ...</a></div>');

$(".more-thing").click(function(event) {
    event.preventDefault();
    $(this).nextAll(".rn-hidden").first().slideToggle("fast", function() { });
    //$(this).hide();
});

// Expand all hidden divs

$(".expand-all").click(function () {
    var attr = $(this).attr("data-for");
    $("#" + attr).nextAll("ul .rn-hidden").first();
    $(".rn-hidden").css("display", "block");
});

