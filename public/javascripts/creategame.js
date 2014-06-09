var gameId = 0;


$(document).ready(function(){
	var panelTest = $("#panel-os").attr("id");
	if(panelTest === "panel-os"){
		gameId = $("#Game").data("id");
		console.log(gameId);
		refresch();
		$(".btn-choose").click(function(){
				var co = $(this).closest(".row").children(".col-md-6").children("p").attr("class");
				addPlayer(co);
		});
		setInterval(refresch(), 1000);
	}
});


function refresch(){
	$.ajax({
		type: "GET",
		url: "/ajaxPlayersList/"+gameId

	}).done(function(res){
		$.each(res, function(key, val){
			$("."+val.country).text(val.email);
			$("."+val.country).closest(".row").children(".col-md-3:eq(1)").css("display", "none");
		});
	})
	 .fail(function() {
		alert( "error" );
	});
}

function addPlayer(country){
	$.ajax({
		type: "GET",
		url: "/ajaxAddPlayer/"+gameId+"/"+country

	}).done(function(res){
		if(res.OK === "OK"){
			alert("You are in game now!");
		}else{
			alert("Something went wrong");
		}
		refresch();
	})
	 .fail(function() {
		alert( "error" );
	});
}