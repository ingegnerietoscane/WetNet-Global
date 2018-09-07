<input type="hidden" name="hiddenId" id="hiddenId" value="<%= request.getAttribute("idRedirectMeasures") %>">
<script>
	window.onload = function(){
		var id = document.getElementById("hiddenId").value;
		var url = "/wetnet/manager/measure?id=" +id;
		document.location.href = url;
	}
</script>