package com.ustc.prlib.vo;


public class PhoneVo  extends BaseVo{
	private String content;
    private int position;
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
	public int getPositon() {
		return position;
	}
	public void setPositon(int positon) {
		this.position = positon;
	}
   
    
}
