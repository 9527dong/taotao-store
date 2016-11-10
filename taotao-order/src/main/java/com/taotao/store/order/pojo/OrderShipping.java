package com.taotao.store.order.pojo;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

public class OrderShipping {

	private String orderId; // 订单ID
	@Length(max = 20)
	private String receiverName; // 收货人全名
	@Length(max = 20)
	private String receiverPhone; // 固定电话
	@Length(max = 30)
	private String receiverMobile; // 移动电话
	@Length(max = 10)
	private String receiverState; // 省份
	@Length(max = 10)
	private String receiverCity; // 城市
	@Length(max = 20)
	private String receiverDistrict; // 区/县
	@Length(max = 200)
	private String receiverAddress; // 收货地址，如：xx路xx号
	@Length(max = 6)
	private String receiverZip; // 邮政编码,如：310001
	
	private Date created;
	private Date updated;
	
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}
	public String getReceiverMobile() {
		return receiverMobile;
	}
	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}
	public String getReceiverState() {
		return receiverState;
	}
	public void setReceiverState(String receiverState) {
		this.receiverState = receiverState;
	}
	public String getReceiverCity() {
		return receiverCity;
	}
	public void setReceiverCity(String receiverCity) {
		this.receiverCity = receiverCity;
	}
	public String getReceiverDistrict() {
		return receiverDistrict;
	}
	public void setReceiverDistrict(String receiverDistrict) {
		this.receiverDistrict = receiverDistrict;
	}
	public String getReceiverAddress() {
		return receiverAddress;
	}
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}
	public String getReceiverZip() {
		return receiverZip;
	}
	public void setReceiverZip(String receiverZip) {
		this.receiverZip = receiverZip;
	}
}
