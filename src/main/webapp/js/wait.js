$('document').ready(function(){
	var count = 5;
	countdown = setInterval(function(){
		$(".countdown").html(count);
		if (count==0){
			window.location = $("#url").val();
		}
		count--;		
	}, 1000);
});