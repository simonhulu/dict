<%@page import="com.company.dict.model.PhraseModel"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<h2 class="sub-header">藏语学习</h2>
<div class="row">
<form class="navbar-form">
<!-- 	<input type="text" class="form-control" placeholder="搜索..."> -->
  <button type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#myModal">添加</button>
</form>
</div>

<!-- modal -->
<form id="addform" action="/addStudy" method="post" enctype="multipart/form-data">
<div id="myModal" class="modal fade">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
        <h4 class="modal-title">添加</h4>
      </div>
      <div class="modal-body">
          <div class="input-group">
            <span class="input-group-addon" id="basic-addon1">藏语内容</span>
            <input type="text" name="tibetcontent" class="form-control" placeholder="藏语内容" >
          </div>
          <div class="input-group">
            <span class="input-group-addon" id="basic-addon1">拉丁转写</span>
            <input type="text" name="tibetcomment" class="form-control" placeholder="拉丁转写" >
          </div>
          <div class="input-group">
            <span class="input-group-addon" id="basic-addon1">藏语发音</span>
            <input type="file" name="tibetaudiopath" class="form-control" placeholder="藏语发音" >
          </div>
          <div class="input-group">
            <span class="input-group-addon" id="basic-addon1">视频文件</span>
            <input type="file" name="videopath" id="videopath" class="form-control" placeholder="视频文件"   />
          </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
        <button  id="addSpokenBtn" class="btn btn-primary" type="submit">保存</button>
      </div>
    
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</form>
 <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
                  <th>编号</th>
                  <th>藏文字母</th>
                  <th>藏文发音</th>
                  <th>注释(汉语)</th>
                  <th>注释(藏语)</th>
                  <th>录音文件</th>
                  <th>视频文件</th>
                </tr>
              </thead>
              <tbody>
                
                	
                	

                <%
            		ArrayList<PhraseModel> phrases= (ArrayList<PhraseModel>)request.getAttribute("phrases");
 		             for(int i = 0 ;i<phrases.size();i++)
		            {
		            	PhraseModel phrase = phrases.get(i);
		            	out.println("<tr id="+phrase.getUuid()+"><td>"+
		            	phrase.getUuid()+"</td><td>"+phrase.getContent()+
		            	"</td><td>"+phrase.getTibetContent()+
		            	"</td><td>"+phrase.getComment()+
		            	"</td><td>"+phrase.getTibetComment()+
		            	"</td><td><audio style='width:40px' controls src= "+phrase.getAudioPath()+" />"+
		            	"</td><td><video style='width:60px;height=60px' src= "+phrase.getVideopath()+" controls='controls'"+" />"+
		            	"</tr>");
		            } 
                %>

                
                
              </tbody>
            </table>
</div>

<script>
function getParameterByName(name, url) {
    if (!url) {
      url = window.location.href;
    }
    name = name.replace(/[\[\]]/g, "\\$&");
    var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}
$(document).ready(function(){
 $("form#addform").submit(function(e) {
		
	    var formData = new FormData($(this)[0]);
	    $.ajax({
	    	  url: $(this).attr("action"),
	    	  data: formData,
	    	  type: 'POST',
	            cache:false,
	            contentType: false,
	            processData: false,
	    	  success: function(data){
	    		  
	    		var res =   JSON.parse(data);
	    	    if (res["success"])
	    	    	{
	    	    	
	    	    	
	    	    	window.location.reload();
	    	    	}
	    	  }
	    	});
	    e.preventDefault();
	}); 

});
</script>