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
			alert("����ɹ�");
			return true;
			}else if(i==1){
			alert("�ļ�������");
			return false;
			}else if(i==2){
			alert("�ļ�δ�޸�");
			return false;
			}else if(i==3){ 
			alert("�����쳣��");
			return false;
			}else if(i==-1){
			alert("�ļ�����ʧ�ܣ�");
			return false;
			}
		}
			function openfile(p1,p2){
	    	var params=[p1,p2];
		    var str = params.join(";");
            var apt5 = document.getElementById("applet_a");
			var i=apt5.openFile(str);
			if(i==1){
			 var  ans=window.confirm("�ļ������ڣ��Ƿ�ȷ������");
			 if(ans==true)
			var status=apt5.downLoadDoc(str);
			if(status==-1){
			alert("����ʧ��");
			return false;
			}else if(status==-2){
			alert("�������˲����ڸ��ļ�");
			return false;
			}else if(status==-3){
			alert("�ļ���Ϣд��ʧ��");
			return false;
			}
			}else if(i==2){
			alert("�����쳣");
			return false;
			}
			return true;
		}
		
		
		function deletefile(p1,p2){
	    	var params=[p1,p2];
		    var str = params.join(";");
            var apt5 = document.getElementById("applet_a");
            // 0ɾ���ɹ� 1 ɾ��ʧ�� 2 �ļ�������
            if(confirm("ȷ��ɾ����")){
              var status=apt5.deleteFile(str);
              if(status==0){
			alert("ɾ���ɹ�");
			return true;
			}else if(status==1){
			alert("ɾ��ʧ��");
			return false;
			}else if(status==2){
			alert("�ļ�������");
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
			utf8:"�7�7",
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
		<a href="jsp/applet/file.jsp" onclick="downLoad('aaa', '8');">�������aaa</a>
		<br>
		<input type="text" name="filepath" id="filepath" />

		<input type="button" name="browser" id="browser" value="���" onclick="chooserfile();"/>
		<br>
		<input type="button" name="upload" id="upload" value="�ϴ�"	onclick="upload();"/>
		<br>
		<input type="button" name="open" id="open" value="���ļ�"	onclick="open1();"/>
		<br>
		<a href="#" onclick="return openfile('aaa','19');">���ļ�</a>
		<br>
		<a href="#" onclick="return save('aaa','9');">�����ļ�</a>
		<a href="#" onclick="return deletefile('aaa','7');">ɾ���ļ�</a>
	</body>
</html>
