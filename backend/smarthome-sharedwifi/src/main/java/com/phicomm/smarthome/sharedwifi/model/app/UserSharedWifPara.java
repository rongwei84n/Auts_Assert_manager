/**
 * 
 */
package com.phicomm.smarthome.sharedwifi.model.app;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wenhua.tang
 *
 */
public class UserSharedWifPara {
	@JsonProperty("shared_para")
	public String sharedPara;

	public String getSharedPara() {
		return sharedPara;
	}

	public void setSharedPara(String sharedPara) {
		this.sharedPara = sharedPara;
	}

}
