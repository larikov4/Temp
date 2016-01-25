(function ($) {
    "use strict";
    $('.skills-form').on('submit', function (e) {
        var skillRange = $('#skill-range').val() + '%',
            $li = $("<li/>", {
                class: "skill-item",
                width: skillRange
            }),
            $span = $("<span/>", {
                text: $('#skill-name').val()
            });
        
        $li.append($span);
        $('#skill-range').val('');
        $('#skill-name').val('');
        $('.skill-list').append($li);
        e.preventDefault();
    });
})(jQuery);