package com.group13.hffs.gui;

import android.graphics.Bitmap;

public class Item {
	Bitmap image;
	String title;
	String shortDescription;
	
	public Item(Bitmap image, String title, String shortDescription) {
		super();
		this.image = image;
		this.title = title;
		this.shortDescription = shortDescription;
	}
	public Bitmap getImage() {
		return image;
	}
	public void setImage(Bitmap image) {
		this.image = image;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	
}
