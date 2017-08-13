package com.alacriti.imdbportal.models;

public class SearchModel {
	private String searchKey;
	private String searchOption;
	
	public SearchModel(String searchKey, String searchOption) {
		super();
		this.searchKey = searchKey;
		this.searchOption = searchOption;
	}
	
	public SearchModel() {
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public String getSearchOption() {
		return searchOption;
	}

	public void setSearchOption(String searchOption) {
		this.searchOption = searchOption;
	}
	
	
	
}
