package com.entity;

public class VehicleCategory {
	private int vehicleCategoryId;
	private String vehicleCategoryName;

	@Override
	public String toString() {
		return "VehicleCategory [vehicleCategoryId=" + vehicleCategoryId + ", vehicleCategoryName="
				+ vehicleCategoryName + "]";
	}

	public VehicleCategory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VehicleCategory(int vehicleCategoryId, String vehicleCategoryName) {
		super();
		this.vehicleCategoryId = vehicleCategoryId;
		this.vehicleCategoryName = vehicleCategoryName;
	}

	public int getVehicleCategoryId() {
		return vehicleCategoryId;
	}

	public void setVehicleCategoryId(int vehicleCategoryId) {
		this.vehicleCategoryId = vehicleCategoryId;
	}

	public String getVehicleCategoryName() {
		return vehicleCategoryName;
	}

	public void setVehicleCategoryName(String vehicleCategoryName) {
		this.vehicleCategoryName = vehicleCategoryName;
	}
}
