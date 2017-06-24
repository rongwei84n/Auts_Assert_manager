/**
 * 
 */
package com.phicomm.smarthome.sharedwifi.model.app;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author wenhua.tang
 *
 */
public class UserSharedWifiIncome {
	@JsonProperty("today_income")
	private String todayIncome;
	@JsonProperty("total_income")
	private String totalIncome;

	public String getTodayIncome() {
		return todayIncome;
	}

	public void setTodayIncome(String todayIncome) {
		this.todayIncome = todayIncome;
	}

	public String getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(String totalIncome) {
		this.totalIncome = totalIncome;
	}

}
