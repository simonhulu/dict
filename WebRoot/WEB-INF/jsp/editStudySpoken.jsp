<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>  
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.company.dict.model.PhraseModel"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>编辑</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="Content-Type" content="text/html charset=UTF-8"/>
<!-- Bootstrap core CSS -->
    <link href="/static/css/bootstrap.css" rel="stylesheet">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="/static/css/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="/static/css/dashboard.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="{{ url_for('static', filename='js/ie8-responsive-file-warning.js')}}"></script><![endif]-->
    <script src="/static/js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
    input.form-control {
  width: auto;
}
    </style>
  </head>
  
  <body>
        <div class="container">
<h2 class="sub-header">口语学习</h2>
<div class="row">
 <div class="col-xs-12">
 <div class="table-responsive">
<form id='uploadPhrase' action='/saveSpoken' enctype="multipart/form-data">
            <table class="table">
              <thead>
                <tr>
                  <th>编号</th>
                  <th>汉语内容</th>
                  <th>藏语内容</th>
                  <th>分类(汉语)</th>
                  <th>分类(藏语)</th>
                  <th>录音文件(汉)</th>
                  <th>录音文件(藏)</th>
                  <th>保存</th>
                  <th>删除</th>
                </tr>
              </thead>
              <tbody>
                <%               	
                PhraseModel phrase= (PhraseModel)request.getAttribute("phrase");
                if(phrase !=null)
                {
                	 String echo = "<tr>"+
            				  "<input type='hidden' name='id' value="+phrase.getUuid()+" />"+
            					 "<td>"+phrase.getUuid()+"</td>"+  
            					 "<td ><input  class='col-sm-12' type='text' name='content' value="+phrase.getContent()+"></td>"+ 
            					"<td ><input class='col-sm-12' type='text' name='tibetconent' value="+phrase.getTibetContent()+"></td>"+ 
            		 			"<td>"+phrase.getCategoryContent()+"</td>"+ 
             					"<td >"+phrase.getTibetContent()+"</td>"+
            					"<td><input class='col-sm-12' type='file' name=cn></td>"+
            					"<td ><input class='col-sm-12' type='file' name=ti></td>"+
            					"<td ><input class='btn btn-default btn-file' type='submit' value='保存'/></td>"+ 
            					"<td ><span id='deleteBtn' class='btn btn-default btn-file'>删除</span></td>"+
            				  
            		"</tr>";
            	out.println(echo); 
                }
               
                %>

                
              </tbody>
            </table>
             </form>
            </div>
</div>
</div>
</div>
  </body>
   <script src="/static/js/vendor/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
    <script src="/static/js/bootstrap.min.js"></script>
    <!-- Just to make our placeholder images work. Don't actually copy the next line! -->
    <script src="/static/js/vendor/holder.min.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="/static/js/ie10-viewport-bug-workaround.js"></script>
    <script type="text/javascript">
    $(document).ready(function(){
    	$('#deleteBtn').click(function(){
			var id =  $(this).parent().parent().find("td").eq(0).html() ;
			
    		var data = {"id":id};
    		
			$.post("/delete",data,function(result){
				console.log(result);
				window.location.reload();
			});
    	});
     	 $('#uploadPhrase').on('submit',(function(e){
    		e.preventDefault();
            var formData = new FormData(this);
			var id =  $(this).find("td").eq(0).html() ;
            formData.append('id',id);
            $.ajax({
                type:'POST',
                url: $(this).attr('action'),
                data:formData,
                cache:false,
                contentType: false,
                processData: false,
                success:function(data){
                    console.log("success");
                    alert("上传成功");
                },
                error: function(data){
                    console.log("error");
                    console.log(data);
                }
            });
    		
    	})); 
    	
    })
    
    </script>
</html>
