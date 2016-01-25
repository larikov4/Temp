(function ($) {
    "use strict";
    $('.portfolio-categories').on('click', '.category-item', function () {
        var $this = $(this);
        $this.siblings('.category-item.active').removeClass('active');
        $this.addClass('active');
        $('.portfolio-thumbnails').isotope({
            filter: $this.data('filter')
        });
    });
})(jQuery);