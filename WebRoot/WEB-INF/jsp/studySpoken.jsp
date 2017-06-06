<%@page import="com.company.dict.model.PhraseModel"%>
<%@page import="com.company.dict.model.Category"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<h2 class="sub-header">口语学习</h2>

<div class="container">
<div class="row">
<form class="navbar-form">
<!--  <button type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#myModal">添加</button>  -->
</form>
</div>

          
<!-- modal -->
<%-- <form id="addform" action="/addSpoken" method="post" enctype="multipart/form-data">
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
            <span class="input-group-addon" id="basic-addon1">汉语内容</span>
            <input type="text" name="content" class="form-control" placeholder="汉语内容" >
          </div>
          <div class="input-group">
            <span class="input-group-addon" id="basic-addon1">藏语内容</span>
            <input type="text" name="tibetcontent" class="form-control" placeholder="藏语内容" >
          </div>
          <div class="input-group">
            <span class="input-group-addon" id="basic-addon1">分类(汉语)</span>
            <select class="form-control" id="sel1">
             <%
                  List<Category> catrgories= (List<Category>)request.getAttribute("categories");
                for(int i = 0 ;i<catrgories.size();i++)
                {
                      Category catrgory = catrgories.get(i);
                     
                      out.println("<option value="+catrgory.getUuid()+">"+catrgory.getName()+"</option>"); 
                }

              %>
            </select>
                      
             <%
             if(catrgories.size()>0){
             	Category catrgory = catrgories.get(0);
              	out.println("<input id=cateid type=hidden name=cateid value="+catrgory.getUuid()+">"+"</input>"); 
                
             }
              %>
          </div>
          <div class="input-group">
            <span class="input-group-addon" id="basic-addon1">汉语文件</span>
            <input type="file" name="hanfile" id="hanfile" class="form-control" placeholder="汉语文件"   />
          </div>
          <div class="input-group">
            <span class="input-group-addon" id="basic-addon1">藏语文件</span>
            <input type="file" name="zangyufile" id="zangyufile" class="form-control" placeholder="藏语文件"   />
          </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
        <button  id="addSpokenBtn" class="btn btn-primary" type="submit">保存</button>
      </div>
    
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</form> --%>

<!--  -->
<!-- <form id="addphrase" action="/addSpoken" method="post" enctype="multipart/form-data">
 <span class="input-group-addon" >上传excel文件
 <input type="file" name="excel" id="hanfile" class="form-control" placeholder="excel文件"   />
 <input type="hidden" name="type" value=1 />
 </span>
</form> -->
 <div class="table-responsive">
            <table class="table table-striped">
              <thead>
                <tr>
                  <th>编号</th>
                  <th>汉语内容</th>
                  <th>藏语内容</th>
                  <th>分类(汉语)</th>
                  <th>分类(藏语)</th>
                  <th>录音文件(汉)</th>
                  <th>录音文件(藏)</th>
                  <th>操作</th>
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
                	"</td><td>"+phrase.getCategoryContent()+
                	"</td><td>"+phrase.getTibetCategoryContent()+
                	"</td><td><audio style='width:40px' controls src= "+phrase.getAudioPath()+" />"+
                	"</td><td><audio style='width:40px' controls src= "+phrase.getTibetAudioPath()+" />"+
                	"</td><td><a href='#'>编辑</a></td></tr>");
                }
                	
                %>
              </tbody>
            </table>      
</div>
 <div class="pull-right"><ul id="pagination-demo" class="pagination-sm"></ul></div>
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
	 var cateId;
	 $('#sel1').on('change', function (e) {
		    var optionSelected = $("option:selected", this);
		    cateId = this.value;
		    $("#cateid").val(cateId);
	});
	 var pn = getParameterByName('pn'); 
	 var pIndex = getParameterByName("page");
	 if (pn)
		 {
		 startPage = parseInt(pn)
		 }else{
			 startPage= 1;
		 }
			 
	 $('#pagination-demo').twbsPagination({
		    totalPages: 35,
		    visiblePages: 7,
		    startPage:startPage,
		    initiateStartPageClick:false,
		    onPageClick: function (event, page) {
				 window.open('/main?page='+pIndex+'&pn='+page, '_self');
				 console.log(event);
		    }
		});

	 $("tbody a").click(function(){
		 var phraseId =  $(this).parent().parent().attr('id');
		 window.open('/editSpoken?phraseId='+phraseId, '_self');
	 });
	 
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

/*     $('#addform').on('submit',(function(e){

      e.preventDefault();
      var formData = new FormData(this);
      var cateId =  $( "#sel1 option:selected" ).val(); 
      formData.append('cateId',cateId);
            debugger;
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
  }));  */


 });

</script>