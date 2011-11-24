package com.entos.applets.docManager;
import java.io.File;
import javax.swing.filechooser.FileFilter;
public class SelectFileFilter extends  FileFilter{
	private int length;
	private String[] filters=new String[100];
	private String desc;

	public SelectFileFilter(){

	}

	public SelectFileFilter(String str){
	   this.filters[length]=str;
	   length++;
	}
	public SelectFileFilter(String str,String desc){
	   this.filters[length]=str;
	   this.desc=desc;
	   length++;
	}

	public boolean accept(File f) {
	   String tmp=f.getName().toLowerCase();
	//   如果小于0 显示所有文件
	   if(length==0){
	    return true; 
	   }
	//   显示文件夹
	         if(f.isDirectory()){
	             return true; 
	         }
//	       循环过滤文件过滤
	         for(int i=0;i<length;i++){
	        if(tmp.endsWith(this.filters[i])){
	           return true;
	        }
	         }
	         return false;
	}

	/**
	* 
	* @param str 过滤器名称 例如:".jpg"
	*/
	public void addFilter(String str){
	   this.filters[length]=str;
	   this.length++;
	}
	/**
	* 
	* @param str 过滤器名称 例如:".jpg"
	* @param desc @param desc 此过滤器的描述。例如："JPG and GIF Images" 
	*/
	public void addFilter(String str,String desc){
	   this.filters[length]=str;
	   this.desc=desc;
	   length++;
	}

	/** 
	* @param str 传递多个 过滤器名称 例如:{".jpg",".gif"}
	* @param desc 此过滤器的描述。例如："JPG and GIF Images" 
	*/
	public void addFilter(String[] str,String desc){
	   this.filters=str;
	   this.desc=desc;
	   this.length=str.length;
	}

	/**
	* 
	* @param desc 此过滤器的描述。例如："JPG and GIF Images" 
	*/
	public void setDesc(String desc) {
	   this.desc=desc;
	} 


	@Override
	public String getDescription() {
	   return desc;
	} 
	
//	public boolean accept(File f) {
//	   String tmp=f.getName().toLowerCase();
//
//	         if(f.isDirectory() || tmp.endsWith(".jpg") || tmp.endsWith(".gif")    ){
//	             return true;
//	         }
//	         return false;
//	}
}
