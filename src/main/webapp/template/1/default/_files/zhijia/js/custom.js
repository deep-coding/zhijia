// JavaScript Document

var _hmt = _hmt || [];
(function() {
    var hm = document.createElement("script");
    hm.src = "https://hm.baidu.com/hm.js?381043f76fbf0c4024a0f8e31d0076fd";
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(hm, s);
})();

$(function(){
	$(".cmsHdmTit a").click(function(){
		$(this).siblings().removeClass("on");
		$(this).addClass("on");
		$(this).parents(".alls").find(".cmsHdmCon:eq("+$(this).index()+")").addClass('show');
		$(this).parents(".alls").find(".cmsHdmCon:eq("+$(this).index()+")").siblings(".cmsHdmCon").removeClass('show');
		});
		
	$(".cms-hdm-tit a").click(function(){
		$(this).siblings().removeClass("on");
		$(this).addClass("on");
		$(this).parents(".alls").find(".cms-hdm-conts:eq("+$(this).index()+")").css("display","block");
		$(this).parents(".alls").find(".cms-hdm-conts:eq("+$(this).index()+")").siblings(".cms-hdm-conts").css("display","none");
		});

	//关闭广告
	$('.JS-tpAdCloseBtn').click(function(){
		  $('.topAdRow').slideUp();
		});
	
	//智驾排行
	$('.cmzjddMd li').mouseenter(function(){
	    $(this).siblings().removeClass("shs");
		$(this).addClass("shs");
	  });
	
	//点击发表评论
	$('.wenzhangMain .wydps').click(function(){
	    $('.wenzhangMain .lTCc').focus();
	  });
	
	
	
	
	
	
	
	
	
  //monitor select evt
  $(".monitor-select").click(function(e){
	  e.stopPropagation();
	  $(this).find(".chi-con").slideToggle();
	});
  $(".monitor-select .opts").click(function(e){
	  e.stopPropagation();
	  var ele = $(this).parent().parent().parent().find('.values');
	  ele.text($(this).text());
	  $(this).parent().parent().slideToggle(300);
	});
  
  //monitor blur evt
  $("body").click(function(){
	  try{
		  $(".monitor-select .chi-con").slideUp();
		  }catch(e){}
	});
	
	//我们的优势
	$(".indexPage .mod2 .md").mouseenter(function(){
		var idx = $(this).index();
		$(".indexPage .mod2 .hover").removeClass('hover');
		$(this).addClass('hover');
		$('.indexPage .mod2 .cirImg img').eq((idx - 1)).siblings().hide();
		$('.indexPage .mod2 .cirImg img').eq((idx - 1)).show();
	  });
	
	
	//返回顶部
	$('.JS-goTop').click(function(){
		  $('html,body').stop().animate({scrollTop: 0});
		});
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//Please Add Other Code
	
	});
	
	
	
//index page pic fade flash monitor
var jd;
$.extend({
	pic_fade:function(out_pl,control_pl,sh_tit,delay){
		$(out_pl).attr("cur_idx",0);
		$(out_pl).attr("ct_img",0);
		$(out_pl).attr("timers","");
		$(out_pl).addClass(sh_tit);
      $(function(){
	        $(out_pl).attr("ct_img",$(out_pl+"  ul li").length);
			$(out_pl+" ul li").not($(out_pl+" ul li:eq(0)")).css({left:"100%"});
			$(out_pl+" ul li:eq(0)").css({left:0})
			if($(out_pl).hasClass("show_title")){
			$(out_pl).css({height:$(out_pl+"  li img").outerHeight()+$(out_pl+"  li span").outerHeight()-2+"px"});
			$(control_pl).addClass("show-tit");
			}
			if($(out_pl).hasClass("hide_title")){
			$(out_pl).css({height:$(out_pl+"  li a img").outerHeight()+"px"});
			$(control_pl).removeClass("show-tit");
			}
			for(var i=0;i<parseInt($(out_pl).attr("ct_img"));i++){//add control btn method
			if(i==0)
			$(out_pl+" "+control_pl).append("<a class='on'></a>");
			else
			$(out_pl+" "+control_pl).append("<a></a>");
			}
		if(delay) {
			$(out_pl).mouseenter(function(){
				window.clearInterval($(out_pl).attr("timers"));
				});
				$(out_pl).mouseleave(function(){
				$(out_pl).attr("timers",window.setInterval(fade_pic,delay));
				});
			}
		$(document).on("click",control_pl+" a",function(){//control click evt
		    if(!$(out_pl+" ul li:eq("+parseInt($(out_pl).attr("cur_idx"))+")").is(":animated")&&!$(out_pl+" ul li:eq("+($(this).index())+")").hasClass("show")){
				$(out_pl).attr("cur_idx",($(this).index()-1));
				fade_pic();
			}
			});
			if(delay)
				$(out_pl).attr("timers",window.setInterval(fade_pic,delay));
			})
			
		$(out_pl + " .gl").click(function(){
			if(!$(out_pl+" ul li:eq("+parseInt($(out_pl).attr("cur_idx"))+")").is(":animated")){
		      jd="gl";
			  fade_pic();
			}
		  });
			
		$(out_pl + " .gr").click(function(){
			if(!$(out_pl+" ul li:eq("+parseInt($(out_pl).attr("cur_idx"))+")").is(":animated")){
		      jd="gr";
			  fade_pic();
			}
		  });
			
		function fade_pic(){//do fade action evt
		  try{
			  if(jd=="gr"){
				$(out_pl).attr("cur_idx",(parseInt($(out_pl).attr("cur_idx"))+1));
				if(parseInt($(out_pl).attr("cur_idx"))>(parseInt($(out_pl).attr("ct_img"))-1))
				    parseInt($(out_pl).attr("cur_idx",0));
					
					
					$(out_pl+" ul .show").removeClass("show").stop(true,true).animate({left:"100%"},{duration:1000,easing:"easeOutExpo"});
				$(out_pl+" ul li:eq("+parseInt($(out_pl).attr("cur_idx"))+")").css({left:"-100%"}).addClass("show").stop(true,true).animate({left:0},{duration:1000,easing:"easeOutExpo"});
					
			    }else if(jd=="gl"){
				$(out_pl).attr("cur_idx",(parseInt($(out_pl).attr("cur_idx"))-1));
				if(parseInt($(out_pl).attr("cur_idx"))<0)
				    parseInt($(out_pl).attr("cur_idx",(parseInt($(out_pl).attr("ct_img"))-1)));
					
					$(out_pl+" ul .show").removeClass("show").stop(true,true).animate({left:"-100%"},{duration:1000,easing:"easeOutExpo"});
				    $(out_pl+" ul li:eq("+parseInt($(out_pl).attr("cur_idx"))+")").css({left:"100%"}).addClass("show").stop(true,true).animate({left:0},{duration:1000,easing:"easeOutExpo"});
					
					
			    }else if(jd!="gr"&&jd!="gl"){
					  $(out_pl).attr("cur_idx",(parseInt($(out_pl).attr("cur_idx"))+1));
				     if(parseInt($(out_pl).attr("cur_idx"))>(parseInt($(out_pl).attr("ct_img"))-1))
				     parseInt($(out_pl).attr("cur_idx",0));
					 $(out_pl+" ul .show").removeClass("show").stop(true,true).animate({left:"-100%"},{duration:1000,easing:"easeOutExpo"});
				$(out_pl+" ul li:eq("+parseInt($(out_pl).attr("cur_idx"))+")").css({left:"100%"}).addClass("show").stop(true,true).animate({left:0},{duration:1000,easing:"easeOutExpo"});
					}
				
				$(control_pl+" a:eq("+parseInt($(out_pl).attr("cur_idx"))+")").addClass("on");
				$(control_pl+" a:eq("+parseInt($(out_pl).attr("cur_idx"))+")").siblings().removeClass("on");
		     }catch(e){}
			}
		}
	
	});

var loadTimes = 0;
function tabSwitch(nodeNumber, listNumber) {
    var url;
    loadTimes = 0;
    if (nodeNumber == "newest") {
        url = "${ctx}/zuixin/list_news.jspx?nodeNumber=newest";
    } else {
        url = "${ctx}/tab/list_news.jspx?nodeNumber=" + nodeNumber + "&listNumber=" + listNumber;
    }
    $.ajax({
        url : url,
        type : 'post',
        dataType : 'text',
        success : function (result) {
            $("#tabInfoList").html(result);
        }
    });
}

function loadMore(nodeNumber, nodeUrl) {
    var url;
    if (nodeNumber == "newest") {
        if (loadTimes == 0) {
            url = "${ctx}/zuixin/load_more/list_news.jspx?nodeNumber=newest&offset=7&listNumber=7";
        } else if (loadTimes == 1) {
            url = "${ctx}/zuixin/load_more/list_news.jspx?nodeNumber=newest&offset=14&listNumber=7";
        } else {
            window.open(nodeUrl);
            return
        }
    } else {
        if (loadTimes == 0) {
            url = "${ctx}/tab/list_news.jspx?nodeNumber=" + nodeNumber + "&offset=8&listNumber=7";
        } else if (loadTimes == 1) {
            url = "${ctx}/tab/list_news.jspx?nodeNumber=" + nodeNumber + "&offset=15&listNumber=7";
        } else {
            window.open(nodeUrl);
            return
        }
    }
    $.ajax({
        url : url,
        type : 'post',
        dataType : 'text',
        success : function (result) {
            $(".cmLoadMore").replaceWith(result)
            loadTimes++;
        }
    });
}