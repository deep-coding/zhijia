// JavaScript Document
$(function(){

    $(".cmTabTit a").on('touchend',function(){
		$(this).siblings().removeClass("cur");
		$(this).addClass("cur");
		});
	$(".catTabTit a").unbind().on('click',function(){
		$('.swiper-wrapper .cur').removeClass("cur");
		$(this).addClass("cur");
		});
		
	$(".cmsTabTit a").unbind().on('click',function(){
		$(this).siblings().removeClass("cur");
		$(this).addClass("cur");
		$(this).parents('.alls').find('.cmsTbCon').removeClass('shows');
		$(this).parents('.alls').find('.cmsTbCon').eq($(this).index()).addClass('shows');
		});
	
	//显示筛选
	$('.showMenus').click(function () {
	    $('.cmsMenuChi').show();
	  });
	//关闭筛选
	$('.cmsMenuChi .closeBtn').click(function () {
	    $('.cmsMenuChi').hide();
	  });
	//返回顶部
	$('.goTop').click(function () {
	    $('body,html').animate({scrollTop: 0}, 300);
	  });
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
});



//公共提示
var cmsErTimer;
function cmsErTips(ErInfo,duration){
	if(!ErInfo)ErInfo="操作错误！";
	if($(".commonErTips").length!=0)$(".commonErTips").remove();
	var ele='<div class="commonErTips notapBg"><span>'+ErInfo+'</span></div>';
	$("body").append(ele);
	window.clearTimeout(cmsErTimer);
	//公共错误信息提示框点击自关闭
	$(".commonErTips").on("click",function(){
	    $(".commonErTips").remove();
	  });
	cmsErTimer=window.setTimeout(function(){
		$(".commonErTips").remove();
	  },duration);
	}