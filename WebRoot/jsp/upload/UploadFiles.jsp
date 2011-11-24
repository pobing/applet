<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<HEAD>
    <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=windows-1252"/>
    <TITLE>File Upload Page</TITLE>
  </HEAD>
  <BODY>Upload Files
    <FORM name="filesForm" action="ProcessFileUpload.jsp"
    method="post" enctype="multipart/form-data">
        File 1:<input type="file" name="file1"/><br/>
        File 2:<input type="file" name="file2"/><br/>
        File 3:<input type="file" name="file3"/><br/>
        <input type="submit" name="Submit" value="Upload Files"/>
    </FORM>
  </BODY>
</html>
