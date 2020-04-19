package com.facebookclone.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
