<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
		<script src="/static/js/vendor/jquery.min.js"></script>
		<script src="/static/js/bootstrap.min.js"></script>

<form id="uploadimage" action="/uploadlanuchimage" method="post" enctype="multipart/form-data">

<hr id="line">
<div id="selectImage">
<label>选择你的文件</label><br/>
<input type="file" name="file" id="file"  required />
<br>
<input type="submit" value="上传" class="submit"  >

</div>
</form>
<img id="preview" src="#" style="display:none;" alt="upload img"/>
<script>
$(document).ready(function(){



    function readURL(input) {

        if (input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                $('#preview')
                    .attr('src', e.target.result);
            };
            reader.readAsDataURL(input.files[0]);
            $('#preview').show();
        }
    }
            $("#file").on('change',function(){

                readURL(this);

        })
	$('#uploadimage').on('submit',(function(e){
		e.preventDefault();
        var formData = new FormData(this);

        $.ajax({
            type:'POST',
            url: $(this).attr('action'),
            data:formData,
            cache:false,
            contentType: false,
            processData: false,
            success:function(data){
				alert("上传成功");
            },
            error: function(data){
				alert("请重新上传");
            }
        });
		
	}));
	
})
</script>