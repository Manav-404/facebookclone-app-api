package com.facebookclone.utils;

import lombok.Builder;

public class RestResponse {
	
	private Object data;
	private boolean status;
	
	
	public RestResponse() {}
	
	@Builder
	public RestResponse(Object data, boolean status) {
		this.data = data;
		this.status = status;
	}
	

}
