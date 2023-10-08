package com.entity;

public class Vehicle {
	private int vehicleId;
	private String regNo;
	private VehicleCategory vehicleCategory;
	private String manufacturer;
	private int dailyRent;
	private int mileage;
	private FuelType fuelType;
	private String desc;

	@Override
	public String toString() {
		return "Vehicle [vehicleId=" + vehicleId + "regNo=" + regNo + ", vehicleCategory=" + vehicleCategory
				+ ", manufacturer=" + manufacturer + ", dailyRent=" + dailyRent + ", mileage=" + mileage + ", fuelType="
				+ fuelType + ", desc=" + desc + "]";
	}


	public Vehicle() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Vehicle(int vehicleId, String regNo) {
		super();
		this.vehicleId = vehicleId;
		this.regNo = regNo;
	}

	public Vehicle(int vehicleId, String regNo, VehicleCategory vehicleCategory, String manufacturer, int dailyRent, int mileage, FuelType fuelType, String desc) {
		super();
		this.vehicleId = vehicleId;
		this.regNo = regNo;
		this.vehicleCategory = vehicleCategory;
		this.manufacturer = manufacturer;
		this.dailyRent = dailyRent;
		this.mileage = mileage;
		this.fuelType = fuelType;
		this.desc = desc;
	}


	public int getVehicleId() {
		return this.vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}
	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public VehicleCategory getVehicleCategory() {
		return vehicleCategory;
	}

	public void setVehicleCategory(VehicleCategory vehicleCategory) {
		this.vehicleCategory = vehicleCategory;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public int getDailyRent() {
		return dailyRent;
	}

	public void setDailyRent(int dailyRent) {
		this.dailyRent = dailyRent;
	}

	public int getMileage() {
		return mileage;
	}

	public void setMileage(int mileage) {
		this.mileage = mileage;
	}

	public FuelType getFuelType() {
		return fuelType;
	}

	public void setFuelType(FuelType fuelType) {
		this.fuelType = fuelType;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}