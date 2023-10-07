package com.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.dao.VehicleDAO;
import com.dao.jdbc.BaseDAO;
import com.entity.Booking;
import com.entity.FuelType;
import com.entity.Vehicle;
import com.entity.VehicleCategory;

public class VehicleDAOImpl implements VehicleDAO {
    private BaseDAO bd = new BaseDAO();
    Connection con;

	@Override
    public void addVehicle(Vehicle v) {
//    	System.out.println(v);
        try {
            con = bd.getConnection();
			String addVehicle = "insert into vehicle values(DEFAULT,?,?,?,?,?,?,?);";
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

    public ArrayList<List<String>> getRegisteredVehicle(int vehicleCategory) {
        ArrayList<List<String>> vehicleList = new ArrayList<>();

        try {
            con = bd.getConnection();
			String fetchVehicle = "SELECT VEHICLE_ID , REG_NO , V.VEHICLE_CATEGORY_ID , MANUFACTURER , DAILY_RENT , MILEAGE , FT.FUEL_TYPE_ID , DESCRIPTION , VEHICLE_TYPE , FUEL_NAME FROM VEHICLE V, VEHICLE_CATEGORY VC, FUEL_TYPE FT WHERE V.VEHICLE_CATEGORY_ID = ? AND V.FUEL_TYPE_ID = FT.FUEL_TYPE_ID;";
			PreparedStatement stmt = con.prepareStatement(fetchVehicle);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                vehicleList.add(Arrays.asList(rs.getString(2), rs.getString(4)));
            }
            System.out.println(vehicleList);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        return vehicleList;
    }

    @Override
    public void bookVehicle(Booking b) {
//        getRegisteredVehicle(1);
//        System.out.println(b);
        String bookingQuery = "";
        try {
            con = bd.getConnection();
            PreparedStatement ps = con.prepareStatement(bookingQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
			String vehicleCategoryQuery = "SELECT * FROM VEHICLE_CATEGORY;";
			ResultSet rs = stmt.executeQuery(vehicleCategoryQuery);
            while (rs.next()) {
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
			String fuelTypeQuery = "SELECT * FROM FUEL_TYPE;";
			ResultSet rs = stmt.executeQuery(fuelTypeQuery);
            while (rs.next()) {
                fuelType.add(new FuelType(rs.getInt(1), rs.getString(2)));
            }
            return fuelType;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<Vehicle> getAvailableVehicles(VehicleCategory vehicleCategory, Date toDate, Date fromDate) {
        ArrayList<Vehicle> availableVehicles = new ArrayList<>();
        try {
            con = bd.getConnection();
            String availableVehicleListQuery = "SELECT * FROM VEHICLE WHERE VEHICLE_CATEGORY_ID = ? AND FUEL_TYPE_ID = ? " +
                    "AND BOOK_FROM NOT IN (SELECT * FROM BOOKING WHERE )";
            PreparedStatement ps = con.prepareStatement(availableVehicleListQuery);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                availableVehicles.add(new Vehicle());
            }
            return availableVehicles;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int getDailyRent(Vehicle vehicle) {
        int dailyRent = 0;
        try {
            con = bd.getConnection();
            String dailyRentQuery = "SELECT DAILY_RENT FROM VEHICLE WHERE VEHICLE_ID = ?";
            PreparedStatement ps = con.prepareStatement(dailyRentQuery);
            ps.setInt(1,vehicle.getVehicleId());
            ResultSet rs = ps.executeQuery(dailyRentQuery);
            if (rs.next()) {
                dailyRent = rs.getInt(1);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return 0;
    }
}