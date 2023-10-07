package com.entity;

import java.util.Date;

public class Booking {
	private String customerName;
	private VehicleCategory vehicleCategory;
	private String regNo;
	private Date bookFrom;
	private Date bookTo;
	private int totalRent;
	private boolean payment;

	public Booking() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Booking(String customerName, VehicleCategory vehicleCategory, String regNo, Date bookFrom, Date bookTo,
			int totalRent, boolean payment) {
		super();
		this.customerName = customerName;
		this.vehicleCategory = vehicleCategory;
		this.regNo = regNo;
		this.bookFrom = bookFrom;
		this.bookTo = bookTo;
		this.totalRent = totalRent;
		this.payment = payment;
	}

	@Override
	public String toString() {
		return "Booking [customerName=" + customerName + ", vehicleCategory=" + vehicleCategory + ", regNo=" + regNo
				+ ", bookFrom=" + bookFrom + ", bookTo=" + bookTo + ", totalRent=" + totalRent + ", payment=" + payment
				+ "]";
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public VehicleCategory getVehicleCategory() {
		return vehicleCategory;
	}

	public void setVehicleCategory(VehicleCategory vehicleCategory) {
		this.vehicleCategory = vehicleCategory;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public Date getBookFrom() {
		return bookFrom;
	}

	public void setBookFrom(Date date) {
		this.bookFrom = date;
	}

	public Date getBookTo() {
		return bookTo;
	}

	public void setBookTo(Date date) {
		this.bookTo = date;
	}

	public int getTotalRent() {
		return totalRent;
	}

	public void setTotalRent(int totalRent) {
		this.totalRent = totalRent;
	}

	public boolean getPayment() {
		return payment;
	}

	public void setPayment(boolean payment) {
		this.payment = payment;
	}
}
