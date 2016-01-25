(function ($) {
    "use strict";
    var ARTICLE_MARGIN = 40,
        navLinks = (function () {
            var $links = $(".nav-link"),
                result = [];
            for (var i=0; i < $links.length; i++) {    
                var href = $links.eq(i).attr('href');
                result.push(href);
            }  
            return result;
        })();
    
    $('.sidebar-toggler').on('click', function () {
        var $this = $(this),
            $parent = $this.parent();
        $parent.toggleClass('hidden');
        $this.toggleClass('active');
        $('main').toggleClass('hidden-sidebar');
    });
    
    $('.go-top').on('click', function (e) {
        e.preventDefault();
        $('html, body').stop()
            .animate({'scrollTop': '0'}, 900, 'swing');
    });

    $('nav a').on('click', function (e) {
        e.preventDefault();
        $('html, body').stop()
            .animate({'scrollTop': $(this.hash).offset().top - ARTICLE_MARGIN }, 900, 'swing');
    });
    
    function highlightActiveNavLink(){
        var windowStart = $(window).scrollTop(),
            windowEnd = windowStart + $(window).height(),
            documentHeight = $(document).height(),
            $lastLink = $(".nav-item:last-child .nav-link");

        for (var i=0; i < navLinks.length; i++) {
            var id = navLinks[i],
                divStart = $(id).offset().top,
                divEnd = divStart + $(id).height(),
                $link = $("a[href='" + id + "']");
            
            if (windowStart >= divStart - ARTICLE_MARGIN && windowStart < divEnd) {
                $link.addClass("active");
            } else {
                $link.removeClass("active");
            }
        }
        if(windowEnd == documentHeight && !$lastLink.hasClass("active")) {
            $(".active").removeClass("active");
            $lastLink.addClass("active");
        }
    }
    
    highlightActiveNavLink();
    $(window).scroll(highlightActiveNavLink);    
})(jQuery);