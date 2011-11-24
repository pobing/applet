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

		<title>My JSP 'file.jsp' starting page</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	</head>
	<body>
	   <script src="js/deployJava.js"></script>
<!--	<script src="js/appletInstall.js"></script>-->
	<script src="js/selectFile.js"></script>
<!--	  <applet code=com.netposa.applet.SelectFile.class  archive="<%=basePath%>appletJar/doc.jar" id="applet_a" name="applet_a"  width=0 height=0>-->
<!--	alt="Your browser understands the &lt;APPLET&gt; tag but isn't running the applet, for some reason."-->
<!--      	<param name="scriptable" value="true" />-->
<!--      </applet>-->
<script>

	var attributes = {
		codebase : '.',
		code : 'com.netposa.applets.docManager.SelectFile.class',
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
			<input type="button" value="ä¯ÀÀÎÄ¼þ" onClick="chooserfile();">
	</body>
</html>
