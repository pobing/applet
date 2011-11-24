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
	
		<a href="jsp/applet/file.jsp">applet ≤‚ ‘</a>
		<br>
		<a href="FileDown?filename=01.png">02png</a>
		<br>
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
		<script>
	var attributes = {
		codebase : '.',
		code : 'com.netposa.applets.docManager.CreateFile.class',
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
		<a href="#" onclick="downLoad('aaa', '1');">aaa</a>
	</body>
</html>
