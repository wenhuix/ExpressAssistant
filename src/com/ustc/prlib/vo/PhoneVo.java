package com.ustc.prlib.vo;


public class PhoneVo  extends BaseVo{
	private String content;
    private String positon;
    private boolean isDefault = false;
    
	public boolean isDefault() {
		return isDefault;
	}
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPositon() {
		return positon;
	}
	public void setPositon(String positon) {
		this.positon = positon;
	}
   
    
}
