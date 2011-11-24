		
		var p1,p2;
		 function selectFilePath(){
		var filePath = document.getElementById("filepath").value;
		var appletFile = document.getElementById("applet_a");
		filePath=appletFile.getSelectFilePath();
         alert(filePath);		
		}
			function upload(){
		
			var fileFullPath=document.getElementById("filepath").value;
			alert(fileFullPath);
			var apt4 = document.getElementById("applet_a");
			apt4.uploadFile(fileFullPath);
			}
			function save(p1,p2){
			    var params=[p1,p2];
		    var str = params.join(";");
			var apt4 = document.getElementById("applet_a");
			var i=apt4.saveFile(str);
			if(i==0){
			alert("保存成功");
			return true;
			}else if(i==1){
			alert("文件不存在");
			return false;
			}else if(i==2){
			alert("文件未修改");
			return false;
			}else if(i==3){ 
			alert("操作异常！");
			return false;
			}else if(i==-1){
			alert("文件保存失败！");
			return false;
			}
		}
			function openfile(p1,p2){
	    	var params=[p1,p2];
		    var str = params.join(";");
            var apt5 = document.getElementById("applet_a");
			var i=apt5.openFile(str);
			if(i==1){
			 var  ans=window.confirm("文件不存在，是否确认下载");
			 if(ans==true)
			var status=apt5.downLoadDoc(str);
			if(status==-1){
			alert("下载失败");
			return false;
			}else if(status==-2){
			alert("服务器端不存在该文件");
			return false;
			}else if(status==-3){
			alert("文件信息写入失败");
			return false;
			}
			}else if(i==2){
			alert("操作异常");
			return false;
			}
			return true;
		}
		
		
		function deletefile(p1,p2){
	    	var params=[p1,p2];
		    var str = params.join(";");
            var apt5 = document.getElementById("applet_a");
            // 0删除成功 1 删除失败 2 文件不存在
            if(confirm("确定删除吗？")){
              var status=apt5.deleteFile(str);
              if(status==0){
			alert("删除成功");
			return true;
			}else if(status==1){
			alert("删除失败");
			return false;
			}else if(status==2){
			alert("文件不存在");
			return false;
			}
            }
		}
