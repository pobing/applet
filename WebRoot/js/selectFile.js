function chooserfile() {

	var fileTypes = [ [ "*.txt", "文本文件(*.txt)" ], [ "*.doc", "Wrod文件(*.doc)" ],
			[ "*.docx", "Wrod文件(*.docx)" ], [ "*.xls", "表格文件(*.xls)" ],
			[ "*.xlsx", "表格文件(*.xlsx)" ], [ "*.ppt", "演示文稿(*.ppt)" ],
			[ "*.html", "网页文件(*.html)" ], [ "*.jpg", "图像文件(*.jpg)" ],
			[ "*.gif", "图像文件(*.gif)" ] ];
	var arr = [];
	for ( var i = 0; i < fileTypes.length; i++) {
		arr.push(fileTypes[i].join(";"));
	}
	var str = arr.join(":");
	var helloApplet = document.getElementById("applet_a");
	var filePath = document.getElementById("filepath").value;
	filePath=helloApplet.getSelectFilePath(str);
	document.getElementById("filepath").value=filePath;
}
