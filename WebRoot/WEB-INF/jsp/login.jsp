<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@include file="header.jsp" %>
<body>
<!--login modal-->
<div id="loginModal" class="modal show" tabindex="-1" role="dialog" aria-hidden="true">
  <div class="modal-dialog">
  <div class="modal-content">
      <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
          <h1 class="text-center">登录</h1>
      </div>
      <div class="modal-body">
          <form class="form col-md-12 center-block" action="/userLogin" method="post" role="form">
            <div class="form-group">
              <input type="text" name="username" class="form-control input-lg" placeholder="Email">
            </div>
            <div class="form-group">
              <input type="password" name="passwd" class="form-control input-lg" placeholder="Password">
            </div>
            <div class="form-group">
              <button type="submit" class="btn btn-primary btn-lg btn-block">进入藏文学习系统</button>
              
            </div>
          </form>
      </div>
      <div class="modal-footer">
          <div class="col-md-12">
          
		  </div>	
      </div>
  </div>
  </div>
</div>
	<!-- script references -->

		<script>
			$(document).ready(function(){
				
				
				$('.btn.btn-primary.btn-lg.btn-block').click(function(){
					
				});
				
			})
		</script>
</body>
</html>
