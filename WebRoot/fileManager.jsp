<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>My JSP 'index.jsp' starting page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<script src="js/deployJava.js"></script>
		<script language="JavaScript" src="<%=basePath%>js/selectFile.js"></script>
		<script>
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
		</script>
	</head>
	<body>
		<script>
	var attributes = {
		codebase : '.',
		code : 'com.entos.applets.docManager.FileManagerApplet.class',
		archive : '<%=basePath%>deploy/docApplets.jar',
		width : 0,
		height : 0,
		id : "applet_a",
		name : "applet_a"
	};
	var parameters = {
			scriptable:true,
			id:"aaa",
			utf8:"✓",
			token:"",
			digest:"",
			editor:"",
			version:"12",
			UID:"0",
			itemId:"101",
			Server_URL:"http://192.168.10.109:3000/files/"
	};
	deployJava.runApplet(attributes, parameters, '1.6');
    </script>
		<script>
		var p1,p2;
		function downLoad(p1,p2){
		var params=[p1,p2];
		var str = params.join(";");
		alert(str);
		var apl = document.getElementById("applet_a");
	    apl.downLoadDoc(str);
		}
		</script>
		<a href="jsp/applet/file.jsp" onclick="downLoad('aaa', '8');">点此下载aaa</a>
		<br>
		<input type="text" name="filepath" id="filepath" />

		<input type="button" name="browser" id="browser" value="浏览" onclick="chooserfile();"/>
		<br>
		<input type="button" name="upload" id="upload" value="上传"	onclick="upload();"/>
		<br>
		<input type="button" name="open" id="open" value="打开文件"	onclick="open1();"/>
		<br>
		<a href="#" onclick="return openfile('aaa','19');">打开文件</a>
		<br>
		<a href="#" onclick="return save('aaa','9');">保存文件</a>
		<a href="#" onclick="return deletefile('aaa','7');">删除文件</a>
	</body>
</html>
