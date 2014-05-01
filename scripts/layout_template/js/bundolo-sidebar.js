$(document).ready(function() {
	displaySidebar();
});

function preventSidebarToggle(element, event) {
	var thisAccordion = element.closest('.panel');
	if (thisAccordion.hasClass('active')) {
		event.stopPropagation();
	}
}

function displaySidebar() {
	$.get('templates/sidebar.html', function(template) {
	    var rendered = Mustache.render(template, {
			  "collapsibles": [
			 			    { "title": "Texts", "id" : "texts", "icon" : "file-text-o", "slides" : [{"content" : "Anemonefish murray cod", "index" : "0"}, {"content" : "Anemonefish murray cod", "index" : "1", "active_slide" : true}, {"content" : "Anemonefish murray cod", "index" : "2"}, {"content" : "Anemonefish murray cod", "index" : "3"} ] },
			 			    { "title": "Serials", "id" : "serials", "icon" : "book", "slides" : [{"content" : "Anemonefish murray cod", "index" : "0"}, {"content" : "Anemonefish murray cod", "index" : "1", "active_slide" : true}, {"content" : "Anemonefish murray cod", "index" : "2"}, {"content" : "Anemonefish murray cod", "index" : "3"} ] },
			 			    { "title": "Authors", "id" : "authors", "icon" : "user", "slides" : [{"content" : "Anemonefish murray cod", "index" : "0"}, {"content" : "Anemonefish murray cod", "index" : "1", "active_slide" : true}, {"content" : "Anemonefish murray cod", "index" : "2"}, {"content" : "Anemonefish murray cod", "index" : "3"} ] },
			 			    { "title": "News", "id" : "news", "icon" : "bullhorn", "slides" : [{"content" : "Anemonefish murray cod", "index" : "0"}, {"content" : "Anemonefish murray cod", "index" : "1", "active_slide" : true}, {"content" : "Anemonefish murray cod", "index" : "2"}, {"content" : "Anemonefish murray cod", "index" : "3"} ] },
			 			    { "title": "Forum", "id" : "forum", "icon" : "comments-o", "slides" : [{"content" : "Anemonefish murray cod", "index" : "0"}, {"content" : "Anemonefish murray cod", "index" : "1", "active_slide" : true}, {"content" : "Anemonefish murray cod", "index" : "2"}, {"content" : "Anemonefish murray cod", "index" : "3"} ] },
			 			    { "title": "Contests", "id" : "contests", "icon" : "eye", "slides" : [{"content" : "Anemonefish murray cod", "index" : "0"}, {"content" : "Anemonefish murray cod", "index" : "1", "active_slide" : true}, {"content" : "Anemonefish murray cod", "index" : "2"}, {"content" : "Anemonefish murray cod", "index" : "3"} ] },
			 			    { "title": "Connections", "id" : "connections", "icon" : "link", "slides" : [{"content" : "Anemonefish murray cod", "index" : "0"}, {"content" : "Anemonefish murray cod", "index" : "1", "active_slide" : true}, {"content" : "Anemonefish murray cod", "index" : "2"}, {"content" : "Anemonefish murray cod", "index" : "3"} ] },
			 			  ]
			 			});
	    $(".sidebar").html(rendered);
	    //TODO assign event handlers if any
	    $(".tablesorter").tablesorter({
			theme : 'bootstrap',
			headerTemplate : '{content} {icon}',
			widgets : [ 'zebra', 'columns', 'uitheme', 'filter' ],
			sortList : [ [ 0, 0 ], [ 1, 0 ] ],
			filter_cssFilter  : 'tablesorter-filter',
	        filter_startsWith : false,
	        filter_ignoreCase : true
		});
		$('#sidebarAccordion').on('show.bs.collapse', function(e) {
			$(e.target).parent('.panel-default')
					.addClass('active');
			if (!$('.row-offcanvas.active').length) {
				$('.row-offcanvas').addClass('active');
			}
		});
		$('#sidebarAccordion').on('shown.bs.collapse', function(e) {
			$(e.target).parent('.panel-default')
					.addClass('active');
			if (!$('.row-offcanvas.active').length) {
				$('.row-offcanvas').addClass('active');
			}
		});
		$('#sidebarAccordion').on('hidden.bs.collapse', function(e) {
			$(this).find('.panel-default').not(
					$(e.target)).removeClass('active');
			
			if (!$('.panel-default.active').length) {
				$('.row-offcanvas').removeClass('active');
			}
			
		});
		$('.sidebar input[type="search"]').focus(function(event) {
			preventSidebarToggle($(this), event);
		});
		$('.sidebar input[type="search"]').click(function(event) {
			preventSidebarToggle($(this), event);
		});
		$('.sidebar .table>tbody>tr').click(function() {
	    	displayDummyText();
	    });
	  });
}