(function ($) {
    "use strict";
    var startAt = 0,
        limit = 5,
        storage = new Firebase("https://blazing-inferno-145.firebaseio.com/education"),
        $li = $("<li/>", {class: "education-item"}),
        $year = $("<div/>", {class: "education-year"}),
        $wrapper = $("<div/>", {class: "education-description-wrapper"}),
        $article = $("<article/>", {class: "education-description"}),                    
        $title = $("<h4/>"),
        $description = $("<p/>");
    $article.append($title).append($description);
    $wrapper.append($article);
    $li.append($year).append($wrapper);

    function loadElements() {
        storage.orderByKey()
            .startAt(startAt.toString())
            .limitToFirst(limit)
            .on("value", function(snapshot) {
                snapshot.forEach(function(data) {
                    var educationItem = data.val(),
                        $liClone = $li.clone();
                    $liClone.find('.education-year').text(educationItem.date);
                    $liClone.find('h4').text(educationItem.title);
                    $liClone.find('p').text(educationItem.someText);
                    $('.education-list').append($liClone);
                });
            startAt += limit;
        });
    }

    loadElements();

    $('.education-list').on('scroll', function(event) {
        var target = event.currentTarget;
        if (target.scrollHeight - target.scrollTop === $(target).outerHeight()) {
            loadElements();
        }
    });    
})(jQuery);