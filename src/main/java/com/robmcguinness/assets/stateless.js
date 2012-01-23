$(function() {
	
	$('[rel=twipsy]').popover({ live: true });
	
	$('body').on("ajax.highlight", function(o) {
		if(o.target)
			$('#'+o.target).removeClass('highlight').addClass('highlight');
	});
});