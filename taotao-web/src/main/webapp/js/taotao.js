var TT = TAOTAO = {
	checkLogin : function(){
		var _ticket = $.cookie("TT_TICKET");
		if(!_ticket){
			return ;
		}
		$.ajax({
			url : "http://sso.taotao.com/user/query/" + _ticket,
			dataType : "jsonp",
			type : "GET",
			success : function(data){
				if(data.status == 200){
					var _data = JSON.parse(data.data);
					var html =_data.username+"，欢迎来到淘淘！<a href=\"http://www.taotao.com/user/logout.html\" class=\"link-logout\">[退出]</a>";
					$("#loginbar").html(html);
				}
			}
		});
	}
}

$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
	TT.checkLogin();
});