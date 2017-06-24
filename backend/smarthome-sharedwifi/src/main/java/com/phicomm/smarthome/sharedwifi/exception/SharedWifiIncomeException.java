/**
 * 
 */
package com.phicomm.smarthome.sharedwifi.exception;

/**
 * @author levins
 *
 */
public class SharedWifiIncomeException extends RuntimeException {

	private static final long serialVersionUID = 1080102469818742266L;

	public SharedWifiIncomeException() {
		super("收入统一更新失败");
	}

}
