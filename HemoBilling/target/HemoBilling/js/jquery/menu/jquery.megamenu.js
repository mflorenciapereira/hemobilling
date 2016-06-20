$(document).ready(function() {

	function megaHoverOver(){
		$(this).find(".sub").stop().fadeTo('fast', 1).show();

		//Calculate width of all ul's
		(function($) {
			jQuery.fn.calcSubWidth = function() {
				rowWidth = 0;
				//Calculate row
				$(this).find("ul").each(function() {
					rowWidth += $(this).width();
				});
			};
		})(jQuery);

		if ( $(this).find(".row").length > 0 ) { //If row exists...
			var biggestRow = 0;
			//Calculate each row
			$(this).find(".row").each(function() {
				$(this).calcSubWidth();
				//Find biggest row
				if(rowWidth > biggestRow) {
					biggestRow = rowWidth;
				}
			});
			//Set width
			$(this).find(".sub").css({'width' :biggestRow});
			$(this).find(".row:last").css({'margin':'0'});

		} else { //If row does not exist...

			$(this).calcSubWidth();
			//Set Width
			$(this).find(".sub").css({'width' : rowWidth});

		}
		$("ul#menuhorizontal li").unbind('click');
	}

	function megaHoverOut(){
	  /*$(this).find(".sub").stop().fadeTo('fast', 0, function() {
		  $(this).hide();
	  });*/
		$(this).find(".sub").stop().hide();
		$("ul#menuhorizontal li").unbind('click').click(megaHoverOver);
	}


	var config = {
		 sensitivity: 2, // number = sensitivity threshold (must be 1 or higher)
		 interval: 20, // number = milliseconds for onMouseOver polling interval
		 over: megaHoverOver, // function = onMouseOver callback (REQUIRED)
		 timeout: 50, // number = milliseconds delay before onMouseOut
		 out: megaHoverOut // function = onMouseOut callback (REQUIRED)
	};

	$("ul#menuhorizontal li .sub").css({'opacity':'0'});
	$("ul#menuhorizontal li").unbind('click').click(megaHoverOver);
	$("ul#menuhorizontal li").unbind('mouseleave').mouseleave(megaHoverOut);
	
	$("ul#menuhorizontal li .sub a").click(function (){
		$("ul#menuhorizontal li .sub").hide();
	});
	
	/*
	$("ul#menuhorizontal li .sub a").click(function (){
		console.log(this);
		console.log('hago unbind');
		$("ul#menuhorizontal li").unbind('click');
		console.log('oculto menu');
		$('.sub').hide();
		console.log('hago bind');
		$("ul#menuhorizontal li").unbind('click').click(megaHoverOver);
	})
	*/
	
		/*
			function (){
		$(this).find('.sub').hide();
		
		setTimeout (function (){
			console.log('timeout');
			if (!adentroMenu){
				console.log('no adentro');
				console.log(submenu);
				console.log(submenu.find('.sub'));
				submenu.find('.sub').hide();
			}
		},'100');
		
		
	});*/
	/*$("ul#menuhorizontal li .sub").mouseenter(function (){
		console.log('mouse enter');
		adentroMenu = true;
	});
	$("ul#menuhorizontal li .sub").mouseleave(function (){
		console.log('mouse leave');
		adentroMenu = false;
		$(this).hide();
	});*/
//	$("ul#menuhorizontal li").hoverIntent(config);
	
	



});