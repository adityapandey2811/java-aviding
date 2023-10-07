package com.dao;

import java.util.ArrayList;

import com.entity.Booking;
import com.entity.FuelType;
import com.entity.Vehicle;
import com.entity.VehicleCategory;

public interface VehicleDAO {
    public void addVehicle(Vehicle v);
    public void bookVehicle(Booking b);
    public void generateReport();
	public ArrayList<VehicleCategory> getVehicleCategory();
	public ArrayList<FuelType> getFuelType();
}
