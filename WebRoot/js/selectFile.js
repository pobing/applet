function chooserfile() {

	var fileTypes = [ [ "*.txt", "�ı��ļ�(*.txt)" ], [ "*.doc", "Wrod�ļ�(*.doc)" ],
			[ "*.docx", "Wrod�ļ�(*.docx)" ], [ "*.xls", "����ļ�(*.xls)" ],
			[ "*.xlsx", "����ļ�(*.xlsx)" ], [ "*.ppt", "��ʾ�ĸ�(*.ppt)" ],
			[ "*.html", "��ҳ�ļ�(*.html)" ], [ "*.jpg", "ͼ���ļ�(*.jpg)" ],
			[ "*.gif", "ͼ���ļ�(*.gif)" ] ];
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
