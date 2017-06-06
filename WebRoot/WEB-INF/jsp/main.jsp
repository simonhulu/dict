<%@page import="com.company.dict.ConfigInstance"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
int pageIndex = (Integer)request.getAttribute("page") ;
out.println(pageIndex);
String staticServer = ConfigInstance.getInstance().getStaticServer();
%>

<!DOCTYPE html>
<html lang="en">
  <head>
  	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title>藏文学习系统</title>

    <!-- Bootstrap core CSS -->
    <link href="/static/css/bootstrap.css" rel="stylesheet">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="/static/css/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="/static/css/dashboard.css" rel="stylesheet">
	<link href="/static/css/upload/fileinput.css" rel="stylesheet">
    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="{{ url_for('static', filename='js/ie8-responsive-file-warning.js')}}"></script><![endif]-->
    <script src="/static/js/vendor/jquery.min.js"></script>
    <script src="/static/js/bootstrap.min.js"></script>
    <script src="/static/js/ie-emulation-modes-warning.js"></script>
      <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="/static/js/upload/plugins/canvas-to-blob.min.js" type="text/javascript"></script>
	<script src="/static/js/upload/fileinput.min.js"></script>

    <script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script type="text/javascript" src="/static/js/page/jquery.twbsPagination.min.js"></script>
  </head>

  <body>

    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">藏文学习系统</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
<!--           <ul class="nav navbar-nav navbar-right">
            <li><a href="#">预览</a></li>
            <li><a href="#">配置</a></li>
            <li><a href="#">描述</a></li>
            <li><a href="#">帮助</a></li>
          </ul> -->

        </div>
      </div>
    </nav>

    <div class="container-fluid">
      <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
          <ul class="nav nav-sidebar">

            <li class=<% if(pageIndex == 0)
            {
            	out.println("active");
            }else
            {
            	out.println("deactive");
            }  %>><a href="#">启动界面</a></li>
            <li class=<% if(pageIndex == 1)
            {
            	out.println("active");
            }else
            {
            	out.println("deactive");
            } %>><a href="#">汉藏500句</a></li>
            <li class=<% if(pageIndex == 2)
            {
            	out.println("active");
            }else
            {
            	out.println("deactive");
            } %>><a href="#">搜索翻译</a></li>
            <li class=<% if(pageIndex == 3)
            {
            	out.println("active");
            }else
            {
            	out.println("deactive");
            } %>><a href="#">电子字典</a></li>
            <li class=<% if(pageIndex == 4)
            {
            	out.println("active");
            }else
            {
            	out.println("deactive");
            } %>><a href="#">藏文字学习</a></li>
              <li class=<% if(pageIndex == 5)
            {
            	out.println("active");
            }else
            {
            	out.println("deactive");
            } %>><a href="#">反馈管理</a></li>
            <li class=<% if(pageIndex == 6)
            {
            	out.println("active");
            }else
            {
            	out.println("deactive");
            } %>><a href="#">用户管理</a></li>
          </ul>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
     		<c:choose>
     			<c:when test="${page == 0}">	
     				<%@ include file="uploadImage.jsp" %>
     			</c:when>
     			<c:when test="${page == 1}">
     		     	<%@ include file="studySpoken.jsp" %>
     			</c:when>
     			<c:when test="${page == 2 }">
     		     	<%@ include file="translate.jsp" %>
     			</c:when>
     			<c:when test="${page == 3 }">
     		     	<%@ include file="dict.jsp" %>
     			</c:when>
     			<c:when test="${page == 4 }">
     		     	<%@ include file="study.jsp" %>
     			</c:when>
     			<c:when test="${page == 5 }">
     		     	<%@ include file="feedback.jsp" %>
     			</c:when>  
     			<c:when test="${page == 6}">
     			<%@ include file="userEdit.jsp" %>
     			</c:when>    			
     		</c:choose>
 
          
        </div>
      </div>
    </div>


  </body>
<script src="/static/js/jquery.form.js"></script>
    <!-- Just to make our placeholder images work. Don't actually copy the next line! -->
    <script src="/static/js/vendor/holder.min.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="/static/js/ie10-viewport-bug-workaround.js"></script>
  <script>
    $(".nav.nav-sidebar a").on('click',function(e){
        var index = $(".nav.nav-sidebar a").index(this);
        window.location = "/main?page="+index;
    }
    );
  </script>
  
  
  
  <!-- The jQuery UI widget factory, can be omitted if jQuery UI is already included -->
<script src="/static/js/upload/vendor/jquery.ui.widget.js"></script>
<!-- The Load Image plugin is included for the preview images and image resizing functionality -->
<script src="//blueimp.github.io/JavaScript-Load-Image/js/load-image.all.min.js"></script>
<!-- The Canvas to Blob plugin is included for image resizing functionality -->
<script src="//blueimp.github.io/JavaScript-Canvas-to-Blob/js/canvas-to-blob.min.js"></script>

<!-- The Iframe Transport is required for browsers without support for XHR file uploads -->
<script src="/static/js/upload/jquery.iframe-transport.js"></script>
<!-- The basic File Upload plugin -->
<script src="/static/js/upload/jquery.fileupload.js"></script>
<!-- The File Upload processing plugin -->
<script src="/static/js/upload/jquery.fileupload-process.js"></script>
<!-- The File Upload image preview & resize plugin -->
<script src="/static/js/upload/jquery.fileupload-image.js"></script>
<!-- The File Upload audio preview plugin -->
<script src="/static/js/upload/jquery.fileupload-audio.js"></script>
<!-- The File Upload video preview plugin -->
<script src="/static/js/upload/jquery.fileupload-video.js"></script>
<!-- The File Upload validation plugin -->
<script src="/static/js/upload/jquery.fileupload-validate.js"></script>
<script src="/static/js/jquery.isloading.min.js"></script>
</html>
