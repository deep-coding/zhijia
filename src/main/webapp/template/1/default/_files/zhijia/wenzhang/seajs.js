seajs.config({
    base: "http://assets.cheyun.com/",
    alias: {
        // public
        postiframe: 'public/js/modules/postiframe/postiframe',
        // web
        header: 'v2/official/web/js/modules/header/header',
        aside: 'v2/official/web/js/modules/aside/aside',
        api: 'v2/official/web/js/modules/api/api',
        comments: 'v2/official/web/js/modules/comment/comment',
		usercoment: 'v2/official/web/js/modules/comment/usercomment',
        feed: 'v2/official/web/js/modules/feed/feed',
        cookie: 'framework/jquery/plugin/cookie/1.4.1/jquery.cookie',
        lazyload: 'framework/jquery/plugin/lazyload/1.9.3/jquery.lazyload.min',
        userinfo: 'v2/official/web/js/modules/userinfo/userinfo',
        uploader: 'v2/official/web/js/modules/uploader/webuploader.min',
        liveAside: 'v2/official/web/js/modules/live/liveAside',
        liveHead: 'v2/official/web/js/modules/live/liveHead',
        banner: 'v2/official/web/js/modules/banner/banner',
        dealtext: 'v2/official/web/js/modules/utils/dealtext',
//      liveFeed: 'modules/live/liveFeed',
        statistics: 'v2/common/js/statistic/statistics',
		bannerShowP: 'v2/common/js/statistic/bannerShowP'
    }
});

// 输入框提示
(function() {
    var inputtips, constants,
        wordsSelector = '.widget-inputtips-words',
        HTML = '<span class="widget-inputtips-icon"></span><span class="widget-inputtips-words"></span>';

    constants = {
        TIPS_CLASS: 'widget-inputtips',
        ERROR_CLASS: 'widget-inputerror',
        BORDER_CLASS: 'widget-inputtips-error'
    }

    inputtips = {
        showError: function(input, words) {
            input.parent().addClass(constants.BORDER_CLASS);
            this.createHtml(input, constants.ERROR_CLASS, words);
        },
        showTips: function(input, words) {
            this.createHtml(input, constants.TIPS_CLASS, words);
        },
        createHtml: function(input, classname, words) {
            var parent = input.parent(),
                pWidth = parent.width(),
                element = $('<div class="' + classname + '">' + HTML + '</div>'),
                width;

            parent.append($(element));
            parent.find(wordsSelector).html(words);
            width = $(element).outerWidth();
            element.css({
                left: (pWidth - width) / 2
            });
        },
        removeTips: function(input) {
            input.parent().removeClass(constants.BORDER_CLASS);
            input.parent().find('.' + constants.TIPS_CLASS).remove();
            input.parent().find('.' + constants.ERROR_CLASS).remove();
        },
        removeAll: function() {
            $('.' + constants.BORDER_CLASS).removeClass(constants.BORDER_CLASS);
            $('.' + constants.TIPS_CLASS).remove();
            $('.' + constants.ERROR_CLASS).remove();
        }
    }

    window.inputtips = inputtips;
})();

(function(){
    var globalTips, auto,
        selector = '.widget-tips',
        HTML = '<div class="widget-tips"></div>';

    globalTips = {
        show: function(msg) {
            if ($(selector).length == 0) {
                this.createHtml();
            }

            $(selector).html(msg).stop().fadeIn();

            clearTimeout(auto);
            auto = setTimeout(function(){
                $(selector).fadeOut();
            },2000);
        },
        createHtml: function(){
            $('body').append($(HTML));
        }
    }

    window.globalTips = globalTips;
})();