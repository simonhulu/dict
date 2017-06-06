<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<div class="container-fluid" style="padding-top:30px">
<div class="row pull-right">
	<button id="create">添加</button>
</div>
<div class="row " style="padding-top:50px">
<div class="table-responsive">
  <table class="table">
   <thread>
                   <tr>
                  <th>编号</th>
                  <th>汉语内容</th>
                  <th>藏语内容</th>
                  <th>分类(汉语)</th>
                  <th>分类(藏语)</th>
                  <th>注释(汉语)</th>
                  <th>注释(藏语)</th>
                  <th>录音文件(汉)</th>
                  <th>录音文件(藏)</th>
                  <th>保存</th>
                </tr>
    </thread>
   <tbody id="databody"></tbody>
  </table>
</div>
</div>
</div>
<script>
$('#create').click(function(){
	var tt = "<tr><td></td>"+
			 "<td><input style='width:80px' /></td>"+
			 "<td><input style='width:80px' /></td>"+
			 "<td><input style='width:80px' /></td>"+
			 "<td><input style='width:80px' /></td>"+
			 "<td><input style='width:80px' /></td>"+
			 "<td><input style='width:80px' /></td>"+
			 "<td><span class='btn btn-default btn-file'>上传<input type='file'   /></span></td>"+
			 "<td><span class='btn btn-default btn-file'>上传<input type='file'   /></span></td>"+
			 "<td><a href='#'>保存</a></td>"+
			 "</tr>";
	$('#databody').append(tt);
});
</script>