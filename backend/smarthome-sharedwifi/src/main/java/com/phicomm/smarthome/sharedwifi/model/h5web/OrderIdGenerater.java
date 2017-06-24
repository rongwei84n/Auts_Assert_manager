/**
 * 
 */
package com.phicomm.smarthome.sharedwifi.model.h5web;

/**
 * @author wenhua.tang
 *
 */
public class OrderIdGenerater {
	// 自增长id
	private Long id;
	// 自增长id的md5值
	private String md5Id;
	// 创建时间
	private Long createTime;
	// 更新时间
	private Long updateTime;
	// 状态 0 正常 -1 删除
	private Byte status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMd5Id() {
		return md5Id;
	}

	public void setMd5Id(String md5Id) {
		this.md5Id = md5Id;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

}
