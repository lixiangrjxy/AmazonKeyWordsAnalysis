package com.movann.amz.bean;

import java.util.ArrayList;
import java.util.List;

public class AmzListing {
	private String title;
	private String brandName;
	private String seller;
	private List <String> keyPoints;
	private String aplus;
	private String desc;

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public List<String> getKeyPoints() {
		return keyPoints;
	}
	public void setKeyPoints(List<String> keyPoints) {
		this.keyPoints = keyPoints;
	}
	public void addKeyPoint (String keyPoint) {
		if(this.keyPoints == null) {
			keyPoints = new ArrayList();
		}
		keyPoints.add(keyPoint);
	}
	public String getKeyPointsAsString() {
		StringBuffer sb = new StringBuffer();
		if (keyPoints != null) {
			for (String keyPoint : keyPoints) {
				sb.append(keyPoint).append("\n\t");
			}
		}
		return sb.toString();
	}
	public String getAplus() {
		return aplus;
	}
	public void setAplus(String aplus) {
		this.aplus = aplus;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public String getAllDescriptions() {
		StringBuffer sb = new StringBuffer();
		if(aplus != null) {
			sb.append(aplus);
		}
		if(desc != null) {
			sb.append(desc);
		}
		return sb.toString();
	}

}
