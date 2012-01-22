$(function() {
	
	$('[rel=twipsy]').popover({ live: true });
	
	$('body').on("ajax.highlight", function(o) {
		$('#'+o.target).removeClass('highlight').addClass('highlight');
	});
});