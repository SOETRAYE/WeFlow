package com.cmmobi.railwifi.event;

public enum RequestEvent {
	TIME_OUT, LOADING_START, LOADING_END;
	private String value;
	
	public String getValue() {
        return value;
    }
	
	public void setValue(String value){
		this.value = value;
	}
}
