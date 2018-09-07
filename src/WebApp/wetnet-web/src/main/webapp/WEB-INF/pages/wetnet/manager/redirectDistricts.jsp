<input type="hidden" name="hiddenId" id="hiddenId" value="<%= request.getAttribute("idRedirectDistricts") %>">
<script>
	window.onload = function(){
		var id = document.getElementById("hiddenId").value;
		var url = "/wetnet/manager/district?id=" +id;
		document.location.href = url;
	}
</script>