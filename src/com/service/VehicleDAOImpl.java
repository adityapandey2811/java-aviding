package com.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dao.VehicleDAO;
import com.dao.jdbc.BaseDAO;
import com.entity.Booking;
import com.entity.FuelType;
import com.entity.Vehicle;
import com.entity.VehicleCategory;
import com.exception.DataAccessException;
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

public class VehicleDAOImpl implements VehicleDAO {
	private BaseDAO bd = new BaseDAO();
	Connection con;
	private String vehicleCategoryQuery = "SELECT * FROM VEHICLE_CATEGORY;"; 
	private String fuelTypeQuery = "SELECT * FROM FUEL_TYPE;"; 
	private String addVehicle = "insert into vehicle values(DEFAULT,?,?,?,?,?,?,?);";
	private String fetchVehicle = "SELECT VEHICLE_ID , REG_NO , V.VEHICLE_CATEGORY_ID , MANUFACTURER , DAILY_RENT , MILEAGE , FT.FUEL_TYPE_ID , DESCRIPTION , VEHICLE_TYPE , FUEL_NAME FROM VEHICLE V, VEHICLE_CATEGORY VC, FUEL_TYPE FT WHERE V.VEHICLE_CATEGORY_ID = ? AND V.FUEL_TYPE_ID = FT.FUEL_TYPE_ID;";
    @Override
    public void addVehicle(Vehicle v) {
//    	System.out.println(v);
    	try {
			con = bd.getConnection();
			PreparedStatement pstmt = con.prepareStatement(addVehicle);
			pstmt.setString(1, v.getRegNo());
			pstmt.setInt(2, v.getVehicleCategory().getVehicleCategoryId());
			pstmt.setString(3, v.getManufacturer());
			pstmt.setInt(4, v.getDailyRent());
			pstmt.setInt(5, v.getMileage());
			pstmt.setInt(6, v.getFuelType().getFuelTypeId());
			pstmt.setString(7, v.getDesc());
			
			pstmt.executeUpdate();
			System.out.println("The new Vehicle has been saved Successfully...");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }
    
    public ArrayList<List<String>> getRegisteredVehicle(int vehicleCategory){
    	ArrayList<List<String>> vehicleList = new ArrayList<>();
    	
    	try {
			con = bd.getConnection();
			PreparedStatement stmt = con.prepareStatement(fetchVehicle);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				vehicleList.add(Arrays.asList(rs.getString(2),rs.getString(4)));
			}
			System.out.println(vehicleList);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    	
    	
    	return vehicleList;
    }

    @Override
    public void bookVehicle(Booking b) {
    	getRegisteredVehicle(1);
    	System.out.println(b);
    }

    @Override
    public void generateReport() {

    }

	@Override
	public ArrayList<VehicleCategory> getVehicleCategory() {
		ArrayList<VehicleCategory> vehicleCategory = new ArrayList<>();
		try {
			con = bd.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(vehicleCategoryQuery);
			while(rs.next()) {
				vehicleCategory.add(new VehicleCategory(rs.getInt(1), rs.getString(2)));
			}
			return vehicleCategory;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public ArrayList<FuelType> getFuelType() {
		ArrayList<FuelType> fuelType = new ArrayList<>();
		try {
			con = bd.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(fuelTypeQuery);
			while(rs.next()) {
				fuelType.add(new FuelType(rs.getInt(1), rs.getString(2)));
			}
			return fuelType;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
