(function ($) {
    "use strict"; 
    var startAt = 0;
    var limit = 5;

    loadElements();

    $('.education-list').on('scroll', function(event) {
        var target = event.currentTarget;
        if (target.scrollHeight - target.scrollTop == $(target).outerHeight()) {
            loadElements();
        }
    });

    function loadElements() {
        var storage = new Firebase("https://blazing-inferno-145.firebaseio.com/education"),
            $li = $("<li/>", {class: "education-item"}),
            $year = $("<div/>", {class: "education-year"}),
            $wrapper = $("<div/>", {class: "education-description-wrapper"}),
            $article = $("<article/>", {class: "education-description"}),                    
            $title = $("<h4/>"),                   
            $description = $("<p/>");  
        
        $li.append($year).append(
            $wrapper.append(
                $article.append($title).append($description)
            )
        );
        storage.orderByKey().startAt(startAt + "").limitToFirst(limit).on("value", function(snapshot) {
            snapshot.forEach(function(message) {
                var educationItem = message.val(),
                    $liClone = $li.clone();
                $liClone.find('.education-year').text(educationItem.date);
                $liClone.find('h4').text(educationItem.title);
                $liClone.find('p').text(educationItem.someText);
                $('.education-list').append($liClone);
            });
            startAt += limit;
        });
    }
    
})(jQuery);