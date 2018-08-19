package com.ticonsys.kidscontent.data;

public class ContentItem {

	private String content_name;

	public ContentItem() {
	}

	public ContentItem(String content_name) {
		super();

		this.content_name = content_name;

	}


	public String getContentName() {
		return content_name;
	}

	public void setContentName(String content_name) {
		this.content_name = content_name;
	}

}
