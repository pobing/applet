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
	</head>
	<body>
		<script>
	var attributes = {
		codebase : '.',
		code : 'com.netposa.applets.docManager.test.HttpClientDownload.class',
		archive : '<%=basePath%>deploy/docApplets.jar',
		width : 0,
		height : 0,
		id : "applet_a",
		name : "applet_a"
	};
	var parameters = {
			scriptable:true
	};
	deployJava.runApplet(attributes, parameters, '1.6');
    </script>
			<script>
		var p;
		function downLoad(p){
		alert(p);
		var apl = document.getElementById("applet_a");
	    apl.downLoad(p);
		}
		</script>
			<a href="#" onclick="downLoad('03.png');">02.png</a>
	</body>
</html>
