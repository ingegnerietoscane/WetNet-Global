/* GC 04/11/2015*/
	/*
	 * previene la chiusura del dropdown menu su mappa
	 */
	$('.dropdown.keep-open').on({
	    "shown.bs.dropdown": function() { this.closable = true; },
	    "click":             function(e) { 
	        var target = $(e.target);
	        if(target.hasClass("btn-primary")) 
	            this.closable = true;
	        else 
	           this.closable = false; 
	    },
	    "hide.bs.dropdown":  function() { return this.closable; }
	});