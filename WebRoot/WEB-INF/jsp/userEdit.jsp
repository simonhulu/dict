<%@page import="com.company.dict.model.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
		<script src="/static/js/vendor/jquery.min.js"></script>
		<script src="/static/js/bootstrap.min.js"></script>
<div id="myModal" class="modal fade">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
        <h4 class="modal-title">删除用户</h4>
      </div>
      <div class="modal-body">
      <input type="hidden" name="deluid" value="" />
      <p></p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
        <button  id="delUserBtn" class="btn btn-primary" type="button">删除</button>
      </div>
    
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div id="passwdModel" class="modal fade">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
        <h4 class="modal-title">修改密码</h4>
      </div>
      <div class="modal-body">
          <input type="hidden" name="deluid" value="" />
          用户:<p></p>
		  <div class="input-group">
            <span class="input-group-addon" id="basic-addon1">密码</span>
            <input  name="passwdcontext" type="password" class="form-control" placeholder="密码" >
          </div>
          <div class="input-group">
            <span class="input-group-addon" id="basic-addon1">确认密码</span>
            <input type="password" name="confirmpasswdcontext" class="form-control" placeholder="确认密码" >
          </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
        <button  id="modifyUserBtn" class="btn btn-primary" type="button">修改</button>
      </div>
    
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
                  <th>编号</th>
                  <th>用户</th>
                  <td>删除</td>
                  <td>密码</td>
                </tr>
              </thead>
              <tbody>     
                <%
                ArrayList<User> users= (ArrayList<User>)request.getAttribute("users");
	             for(int i = 0 ;i<users.size();i++)
	            {
	            	 User user = users.get(i);
		            	out.println("<tr id="+user.getId() +"><td>"+i+"</td><td>"+user.getUserName()+"</td><td><button id='deleteFeedBackBtn' type='button' class='btn btn-secondary' data-toggle='modal' data-target='#myModal'>删除</button></td><td><button id='modifyPassBtn' type='button' class='btn btn-secondary' data-toggle='modal' data-target='#passwdModel'>修改密码</button></td>"+
		            	"</tr>"); 
	            }
                %>
              </tbody>
            </table>
</div>

<script>

$(document).on("click", "#deleteFeedBackBtn", function(e){
	var uid = $(this).parent().parent().attr('id');
	var name = $($(this).parent().parent().children()[1]).text();
	$("input[name=deluid]").val(uid);
	$("#myModal p").text(name);

    // As pointed out in comments, 
    // it is superfluous to have to manually call the modal.
    // $('#addBookDialog').modal('show');
});
$("#delUserBtn").click(function(e){
	var uid = $("input[name=deluid]").val();
	$.ajax({url:"/delUser?uid="+uid,
	  success:function(data){
			var res =   JSON.parse(data);
  	    if (res["success"])
  	    	{
  	    	window.location.reload();
  	    	}			  
	  }}); 
});


$("#modifyUserBtn").click(function(e){
	var uid = $("input[name=deluid]").val();
	var passwd = $("input[name=passwdcontext]").val();
	var confirmpasswd = $("input[name=confirmpasswdcontext]").val();
	if (passwd != confirmpasswd)
	{
		alert("密码必须一致");
		return;
	}
	$.ajax({url:"/modifyUser?uid="+uid+"&passwd="+passwd,
	  success:function(data){
		var res =   JSON.parse(data);
  	    if (res["success"])
  	    	{
  			alert("修改成功");
  	    	window.location.reload();
  	    	}			  
	  }}); 
});

$(document).on("click", "#modifyPassBtn", function(e){
	var uid = $(this).parent().parent().attr('id');
	var name = $($(this).parent().parent().children()[1]).text();
	$("＃passwdModel input[name=deluid]").val(uid);
	$("#passwdModel p").text(name);

    // As pointed out in comments, 
    // it is superfluous to have to manually call the modal.
    // $('#addBookDialog').modal('show');
});
</script>
