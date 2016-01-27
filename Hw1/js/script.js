$(function () {
    "use strict";

    //skills module 
    (function () {
        //set up reference li node
        var $li = $("<li/>", {
            class: "skill-item"
        }).append($("<span/>"));

        //gather skill data from form and put it into skills chart
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
    })();

    //portfolio module
    (function () {
        //init isotope greed for thumbnails
        var greed = $('.portfolio-thumbnails').isotope();
        //animate filtering through portfolio categories using isotope
        $('.portfolio-categories').on('click', '.category-item', function () {
            var $this = $(this);
            $this.siblings('.category-item.active').removeClass('active');
            $this.addClass('active');
            greed.isotope({
                filter: $this.data('filter')
            });
        });
    })();

    //education module
    (function () {
        var ANIMATION_DURATION = 900,
            startAt = 0,
            limit = 5,
            //set up DB API object
            storage = new Firebase("https://blazing-inferno-145.firebaseio.com/education"),
            //set up reference li node in order to clone it 
            $li = (function () {
                var $li = $("<li/>", {
                        class: "education-item"
                    }),
                    $year = $("<div/>", {
                        class: "education-year"
                    }),
                    $wrapper = $("<div/>", {
                        class: "education-description-wrapper"
                    }),
                    $article = $("<article/>", {
                        class: "education-description"
                    }),
                    $title = $("<h4/>"),
                    $description = $("<p/>");
                $article.append($title).append($description);
                $wrapper.append($article);
                return $li.append($year).append($wrapper);
            })();

        //load next chunk of data and append it to aducation article 
        function loadElements(callback) {
            storage.orderByKey()
                .startAt(startAt.toString())
                .limitToFirst(limit)
                .on("value", function (snapshot) {
                    snapshot.forEach(function (data) {
                        var educationItem = data.val(),
                            $liClone = $li.clone();
                        $liClone.find('.education-year').text(educationItem.date);
                        $liClone.find('h4').text(educationItem.title);
                        $liClone.find('p').text(educationItem.someText);
                        $('.education-list').append($liClone);
                    });
                    if (callback) {
                        callback();
                    }
                    startAt += limit;
                });
        }

        //load the first chunk of data
        loadElements(function () {
            //hide spinner and show education list
            $('.education-spinner-wrapper').fadeTo(ANIMATION_DURATION, 0, function () {
                $('.education-list').fadeTo(ANIMATION_DURATION, 1);
            });
        });

        //load next chunk of data on education list scroll
        $('.education-list').on('scroll', function (event) {
            var target = event.currentTarget;
            //check whether list was scrolled to the bottom
            if (target.scrollHeight - target.scrollTop === $(target).outerHeight()) {
                loadElements();
            }
        });
    })();

    //navigation module
    (function () {
        var ARTICLE_MARGIN = 40,
            ANIMATION_DURATION = 900,
            ANIMATION_FUNCTION = 'swing',
            //generate navigation links array
            navLinks = (function () {
                var $links = $(".nav-link"),
                    result = [];
                for (var i = 0; i < $links.length; i++) {
                    var href = $links.eq(i).attr('href');
                    result.push(href);
                }
                return result;
            })();

        //open-close sidebar once burger button is clicked
        $('.sidebar-toggler').on('click', function () {
            var $this = $(this),
                $parent = $this.parent();
            $parent.toggleClass('hidden');
            $this.toggleClass('active');
            $('main').toggleClass('hidden-sidebar');
        });

        //scroll to the top of the page when go-top button is pressed
        $('.go-top').on('click', function (e) {
            e.preventDefault();
            $('html, body').stop()
                .animate({
                    'scrollTop': '0'
                }, ANIMATION_DURATION, ANIMATION_FUNCTION);
        });

        //scroll to coresponding article once navigation link is clicked
        $('nav a').on('click', function (e) {
            e.preventDefault();
            $('html, body').stop()
                .animate({
                    'scrollTop': $(this.hash).offset().top - ARTICLE_MARGIN
                }, ANIMATION_DURATION, ANIMATION_FUNCTION);
        });

        //highlight the navigation link coresponding to showing article 
        function highlightActiveNavLink() {
            var windowStart = $(window).scrollTop(),
                windowEnd = windowStart + $(window).height(),
                documentHeight = $(document).height(),
                $lastLink = $(".nav-item:last-child .nav-link");

            for (var i = 0; i < navLinks.length; i++) {
                var id = navLinks[i],
                    divStart = $(id).offset().top,
                    divEnd = divStart + $(id).height(),
                    $link = $("a[href='" + id + "']");

                //check whether window is currently on passed div
                if (windowStart >= divStart - ARTICLE_MARGIN && windowStart < divEnd) {
                    $link.addClass("active");
                } else {
                    $link.removeClass("active");
                }
            }
            //check whether window bottom is reached
            if (windowEnd === documentHeight && !$lastLink.hasClass("active")) {
                $(".active").removeClass("active");
                $lastLink.addClass("active");
            }
        }

        highlightActiveNavLink();
        $(window).scroll(highlightActiveNavLink);
    })();
});