(function(win, doc){
	function checkBrowser() {
		var url= win.location.href,
			ua= win.navigator.userAgent.toLowerCase(),
			isIphone=ua.indexOf("iphone"),
			isAndroid=ua.indexOf("android"),
			isWP=ua.indexOf("iemobile"),
			ipad=ua.indexOf("ipad");
	    if(isIphone>-1 || isAndroid>-1 || isWP>-1 || ipad>-1){
			if(url.indexOf('www')>-1){
				url= url.replace(/www/g, 'm');
				win.location.href= url;
			}
	    }
	}
	checkBrowser();
})(window, document);