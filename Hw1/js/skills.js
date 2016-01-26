(function ($) {
    "use strict";
    var $li = $("<li/>", {class: "skill-item"}).append($("<span/>"));
    
    $('.skills-form').on('submit', function (e) {
        var skillRangeValue = $('#skill-range').val() + '%',
            skillNameValue = $('#skill-name').val(),
            $liClone = $li.clone();
        
        $liClone.width(skillRangeValue);
        $liClone.find('span').text(skillNameValue);
        $('#skill-range').val('');
        $('#skill-name').val('');
        $('.skill-list').append($liClone);
        e.preventDefault();
    });
})(jQuery);