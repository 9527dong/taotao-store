package com.taotao.store.order.pojo;


public class OrderItem {
	
	private Long itemId;//商品id
	private String orderId;//订单id
	private Integer num;//商品购买数量
	private String title;//商品标题
	private Long price;//商品单价
	private Long totalFee;//商品总价
	private String picPath;//图片路径
	
	public Long getItemId() {
		return itemId;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Long getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(Long totalFee) {
		this.totalFee = totalFee;
	}
	public Integer getNum() {
		return num;
	}
	
}
