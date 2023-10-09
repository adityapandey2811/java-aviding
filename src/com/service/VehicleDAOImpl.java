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
import com.exception.DataAccessException;

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
			String fetchVehicle = "SELECT VEHICLE_ID , REG_NO , V.VEHICLE_CATEGORY_ID , MANUFACTURER , DAILY_RENT , MILEAGE , FT.FUEL_TYPE_ID , DESCRIPTION , VEHICLE_TYPE , FUEL_NAME FROM VEHICLE V, VEHICLE_CATEGORY VC, FUEL_TYPE FT WHERE V.VEHICLE_CATEGORY_ID = ? AND V.FUEL_TYPE_ID = FT.FUEL_TYPE_ID AND V.VEHICLE_CATEGORY_ID = VC.VEHICLE_CATEGORY_ID;";
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
        String bookingQuery = "INSERT INTO BOOKING VALUES(DEFAULT, ?,?,?,?,?,?,?);";
        try {
            con = bd.getConnection();
            PreparedStatement ps = con.prepareStatement(bookingQuery);
            ps.setInt(2, b.getVehicleCategory().getVehicleCategoryId());
            ps.setString(1, b.getCustomerName());
            ps.setString(3, b.getRegNo());
            ps.setDate(4, new java.sql.Date(b.getBookFrom().getTime()));
            ps.setDate(5, new java.sql.Date(b.getBookTo().getTime()));
            ps.setInt(6, b.getTotalRent());
            ps.setBoolean(7, false);
            int res = ps.executeUpdate();
            if(res == 1) {
                System.out.println("Booking Successful");
            } else {
                System.out.println("Booking Failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void generateReport() {
    	try {
			con = bd.getConnection();
			String fetchReport = "SELECT VC.VEHICLE_TYPE, COUNT(V.VEHICLE_CATEGORY_ID) AS TOAL_VEHICLE, \r\n"
    			+ "(SELECT COUNT(VEHICLE_CATEGORY_ID) FROM BOOKING WHERE current_date()>=BOOK_FROM AND current_date()<=BOOK_TO AND VEHICLE_CATEGORY_ID = V.VEHICLE_CATEGORY_ID) AS VEHICLE_RENTED,\r\n"
    			+ "(SELECT SUM(TOTAL_RENT) FROM BOOKING group by VEHICLE_CATEGORY_ID HAVING  VEHICLE_CATEGORY_ID = V.VEHICLE_CATEGORY_ID) AS PROFIT_EARNED\r\n"
    			+ "FROM VEHICLE V, VEHICLE_CATEGORY VC\r\n"
    			+ "WHERE V.VEHICLE_CATEGORY_ID = VC.VEHICLE_CATEGORY_ID\r\n"
    			+ "GROUP BY V.VEHICLE_CATEGORY_ID; ";
    		PreparedStatement pstmt = con.prepareStatement(fetchReport);
    		ResultSet result =  pstmt.executeQuery();
    		System.out.println("Vehicle Type\tNumber of Vehicles\tTotal Vehicle Rented\tTotal Rent Earned");
    		while(result.next()) {
    			System.out.println(result.getString(1)+ "\t\t\t" + result.getInt(2) + "\t\t\t" + result.getInt(3) + "\t\t\t" + result.getInt(4));
    		}
    		
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
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
            String availableVehicleListQuery = "SELECT * FROM VEHICLE V WHERE V.VEHICLE_CATEGORY_ID = ? AND NOT EXISTS (SELECT * FROM BOOKING B " +
                    "WHERE V.REG_NO = B.REG_NO AND ? <= B.BOOK_TO AND ? >= B.BOOK_FROM);";
//            String availableVehicleListQuery = "SELECT V.* FROM VEHICLE V LEFT JOIN BOOKING B" +
//                    " ON V.VEHICLE_ID = B.VEHICLE_ID AND ? <= B.BOOK_TO AND ? >= B.BOOK_FROM WHERE V.VEHICLE_CATEGORY_ID = ?;";
            PreparedStatement ps = con.prepareStatement(availableVehicleListQuery);
            ps.setInt(1,vehicleCategory.getVehicleCategoryId());
            ps.setDate(2, new java.sql.Date(fromDate.getTime()));
            ps.setDate(3, new java.sql.Date(toDate.getTime()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                availableVehicles.add(new Vehicle(rs.getInt(1), rs.getString(2)));
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
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dailyRent = rs.getInt(1);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return dailyRent;
    }
}