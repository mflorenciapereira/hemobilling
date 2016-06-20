/*
 * Version del proyecto HemoBilling de la jQuery Plugin jConfirmAction
 * La modificacion reside en que en vez de direccionar a el href, en caso
 * afirmativo, invoca a una callback definida
 * 
 * Original version by Hidayat Sagita
 * http://www.webstuffshare.com
 * Licensed Under GPL version 2 license.
 * 
 * Modified version by Alejandro Pettinelli
 *
 */
(function($){

	jQuery.fn.jConfirmAction = function ( okCallBack , noCallback , customMessageOptions) {
		
		//Si se indicaron mensajes personalizados, que los use. 
		//Sino que use los predeterminados nuestros 
		var theOptions = jQuery.extend ({
			question: "¿Confirma?",
			yesAnswer: "Sí",
			cancelAnswer: "No"
		}, customMessageOptions);
		
		return this.each (function () {
			
			$(this).bind('click', function(e) {

				e.preventDefault();
				
				var idElemento = $(this).attr('id');
				
				//Muestra los botones
				if($(this).next('.question').length <= 0)
					$(this).after('<div class="question">'+theOptions.question+'<br/> <span class="yes">'+theOptions.yesAnswer+'</span><span class="cancel">'+theOptions.cancelAnswer+'</span></div>');
				$(this).next('.question').animate({opacity: 1}, 300);
				
				//Metodo que se ejecuta cuando uno acepta
				$('.yes').bind('click', function()
				{
					if( okCallBack!=null && typeof okCallBack!="undefined")
					{
						setTimeout(function(){okCallBack(idElemento)},0);
					}
					
					$(this).parents('.question').fadeOut(300, function() {
						$(this).remove();
					});
				});
		
				//Metodo que se ejecuta cuando rechaza
				$('.cancel').bind('click', function(){
					
					if( noCallback!=null && typeof noCallback!="undefined")
					{
						setTimeout(function(){noCallback(idElemento)},0);
					}
					
					$(this).parents('.question').fadeOut(300, function() {
						$(this).remove();
					});
					
					
				});
				
			});
			
		});
	}
	
})(jQuery);