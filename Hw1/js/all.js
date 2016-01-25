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

    $('.sidebar-toggler').on('click', function () {
        var $this = $(this),
            $parent = $this.parent();
        $parent.toggleClass('hidden');
        $this.toggleClass('active');
        $('main').toggleClass('hidden-sidebar');
    });

    var $scrollObject = $('html, body');
    
    $('.go-top').on('click', function (e) {
        e.preventDefault();
        $scrollObject.stop()
            .animate({'scrollTop': '0'}, 900, 'swing');
    });

    $('nav a').on('click', function (e) {
        e.preventDefault();
        var target = this.hash,
            $target = $(target);
        $scrollObject.stop().animate({
            'scrollTop': $target.offset().top
        }, 900, 'swing', function () {
            window.location.hash = target;
        });
    });
//http://callmenick.com/post/single-page-site-with-smooth-scrolling-highlighted-link-and-fixed-navigation
})(jQuery);