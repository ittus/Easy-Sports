package com.gec.easysports.model;

public class DrawerItem {
	/* Commented tags are expected in future updates.
	 */

	
	public static final int DRAWER_ITEM_TAG_LINKED_IN = 26;
	public static final int DRAWER_ITEM_TAG_BLOG = 27;
	public static final int DRAWER_ITEM_TAG_GIT_HUB = 28;
	public static final int DRAWER_ITEM_TAG_INSTAGRAM = 29;
	
	public static final int DRAWER_ITEM_TAG_DASHBOARD = 10;
	public static final int DRAWER_ITEM_TAG_PROFILE = 11;
	public static final int DRAWER_ITEM_TAG_TOPICS = 12;
	public static final int DRAWER_ITEM_TAG_SCHEDULED = 13;
	public static final int DRAWER_ITEM_TAG_LOGOUT = 14;
	
	public DrawerItem(int icon, int title, int tag) {
		this.icon = icon;
		this.title = title;
		this.tag = tag;
	}

	private int icon;
	private int title;
	private int tag;

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public int getTitle() {
		return title;
	}

	public void setTitle(int title) {
		this.title = title;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}
}
