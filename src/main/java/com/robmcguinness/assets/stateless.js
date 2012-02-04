$(function() {

	$('#sessionLabel').popover({
		placement: 'below',
		trigger: 'hover',
		content: 'indicates if application is stateless or stateful'

	});

	$('body').on("ajax.highlight", function(o) {
		if(o.target) {
			$('#'+o.target).removeClass('highlight').addClass('highlight');
		}
	});
});