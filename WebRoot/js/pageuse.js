		
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
