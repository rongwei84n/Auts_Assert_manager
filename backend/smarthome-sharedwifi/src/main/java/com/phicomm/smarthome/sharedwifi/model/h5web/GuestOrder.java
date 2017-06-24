/**
 * 
 */
package com.phicomm.smarthome.sharedwifi.model.h5web;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

/**
 * @author wenhua.tang
 * Modifiy:liang04.zhang
 *    Date:20170613
 *    Desc:将Order Bean添加JPA Gerepository属性，JPA与Mbeats通用当前Bean；
 */
@Entity
@Table(name = "sw_guest_order")
@ApiModel(value = "GuestOrderModel", description = "访问者订单集合")
public class GuestOrder {

	@ApiModelProperty(value = "主键ID")
	@Column(name = "id")
	@JsonIgnore
	private Long id;


	@ApiModelProperty(value = "订单ID")
	@Column(name = "orderId")
	@JsonProperty("order_id")
	private String orderId;

	@ApiModelProperty(value = "访问者ID")
	@Column(name = "guestId")
	@JsonIgnore
	private Long guestId;


	@ApiModelProperty(value = "打赏参数id")
	@Column(name = "userShareParaId")
	@JsonIgnore
	private Long userShareParaId;
	// ''

	@ApiModelProperty(value = "订单状态 1 待支付 2 消费中 3 已过期")
	@Column(name = "orderStatus")
	@JsonIgnore
	private Integer orderStatus;

	@ApiModelProperty(value = "订单支付状态，状态 0 待支付 1支付成功")
	@Column(name = "orderPayStatus")
	@JsonIgnore
	private Integer orderPayStatus;

	@ApiModelProperty(value = "购买的时间点起始时间戳")
	@Column(name = "buyTime")
	@JsonIgnore
	private Long buyTime;

	@ApiModelProperty(value = "订单的总上网时长")
	@Column(name = "onlineTimeTotal")
	@JsonIgnore
	private Double onlineTimeTotal;


	@ApiModelProperty(value = "订单的总上网时长总价")
	@Column(name = "onlineTimeTotalCost")
	@JsonIgnore
	private String onlineTimeTotalCost;

	@ApiModelProperty(value = "创建时间")
	@Column(name = "createTime")
	@JsonIgnore
	private Long createTime;

	@ApiModelProperty(value = "更新时间")
	@Column(name = "updateTime")
	@JsonIgnore
	private Long updateTime;

	@ApiModelProperty(value = "条目状态")
	@Column(name = "status")
	@JsonIgnore
	private Byte status;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getGuestId() {
		return guestId;
	}

	public void setGuestId(Long guestId) {
		this.guestId = guestId;
	}

	public Long getUserShareParaId() {
		return userShareParaId;
	}

	public void setUserShareParaId(Long userShareParaId) {
		this.userShareParaId = userShareParaId;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getOrderPayStatus() {
		return orderPayStatus;
	}

	public void setOrderPayStatus(Integer orderPayStatus) {
		this.orderPayStatus = orderPayStatus;
	}

	public Long getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(Long buyTime) {
		this.buyTime = buyTime;
	}

	public Double getOnlineTimeTotal() {
		return onlineTimeTotal;
	}

	public void setOnlineTimeTotal(Double onlineTimeTotal) {
		this.onlineTimeTotal = onlineTimeTotal;
	}

	public String getOnlineTimeTotalCost() {
		return onlineTimeTotalCost;
	}

	public void setOnlineTimeTotalCost(String onlineTimeTotalCost) {
		this.onlineTimeTotalCost = onlineTimeTotalCost;
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
