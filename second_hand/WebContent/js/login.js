$(function() {
	$("#login").unbind("click").click(function() {
		var account = $("#account").val();
		var password = $("#password").val();
		
		alert("js调用！");
		
		$.ajax({
		    url:'http://localhost:8080/second_hand/login',
		    data:{
		    	"account":account,
		    	"password":password
		    },
		    type:'post',
		    dataType:'json',
		    async:false,
		    success:function(data, status){
		    	if(data.code == 0) {
					alert(data.msg);
				} else { 
					alert(data.msg);
				}
		    },
		    error:function(data, status, e){
		      console.log(data);
		    }
		  })
		
		
	});
});