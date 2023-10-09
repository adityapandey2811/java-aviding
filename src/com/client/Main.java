package com.client;

import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import com.dao.VehicleDAO;
import com.entity.Booking;
import com.entity.FuelType;
import com.entity.Vehicle;
import com.entity.VehicleCategory;
import com.keyutil.KeyBoardUtil;
import com.service.VehicleDAOImpl;

public class Main {
    public static void main(String[] args) {
        VehicleDAO ob = new VehicleDAOImpl();
        while (true) {
            System.out.println("Main Menu");
            System.out.println("1.Register a Vehicle");
            System.out.println("2.Book a Vehicle");
            System.out.println("3.Booking Report");
            System.out.println("4.Exit");
            int choice = KeyBoardUtil.getInt("Please choose an option [1, 2, 3, 4]:");
            switch (choice) {
                case 1:
                    try {
                        Vehicle vehicle;
                        System.out.println("Enter Vehicle details:");

                        String regNo = KeyBoardUtil.getString("Enter the Registration Number: ");

                        ArrayList<VehicleCategory> vehicleCat = ob.getVehicleCategory();
                        for (VehicleCategory d : vehicleCat) {
                            System.out.println(d.getVehicleCategoryId() + "\t" + d.getVehicleCategoryName());
                        }
                        int vehicleType = KeyBoardUtil.getInt("Choose a Vehicle Type [1,2,3]");
                        VehicleCategory vehicleCategory = vehicleCat.get(vehicleType - 1);

                        String manufacturer = KeyBoardUtil.getString("Enter Manufacturer Name: ");

                        int dailyRent = KeyBoardUtil.getInt("Enter daily rent details: ");

                        int mileage = KeyBoardUtil.getInt("Enter Mileage details (kmpl): ");

                        ArrayList<FuelType> fuelTypeList = ob.getFuelType();
                        for (FuelType d : fuelTypeList) {
                            System.out.println(d.getFuelTypeId() + "\t" + d.getFuelName());
                        }
                        int fuelTypeChoice = KeyBoardUtil.getInt("Choose a Vehicle Type [1,2,3]");
                        FuelType fuelType = fuelTypeList.get(fuelTypeChoice - 1);

                        String Description = KeyBoardUtil.getString("Enter Description about vehicle: ");

                        //0 because we have auto_increment property implemented in the db
                        vehicle = new Vehicle(0, regNo, vehicleCategory, manufacturer, dailyRent, mileage, fuelType,
                                Description);

                        ob.addVehicle(vehicle);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        String customerName = KeyBoardUtil.getString("Enter Customer Name: ");

                        ArrayList<VehicleCategory> vehicleCategoryList = ob.getVehicleCategory();
                        for (VehicleCategory d : vehicleCategoryList) {
                            System.out.println(d.getVehicleCategoryId() + "\t" + d.getVehicleCategoryName());
                        }
                        int vehicleTypeChoice = KeyBoardUtil.getInt("Choose a Vehicle Type [1,2,3]");
                        VehicleCategory vehicleCateChoice = vehicleCategoryList.get(vehicleTypeChoice - 1);

                        Date fromDate = KeyBoardUtil.getDate("Enter from date(YYYY-MM-DD) :");
                        if(fromDate.before(new Date())) {
                        	System.out.println("Invalid Dates");
                        	return;
                        }

                        Date toDate = KeyBoardUtil.getDate("Enter to date(YYYY-MM-DD) :");
                        if(toDate.before(fromDate)) {
                            System.out.println("Invalid Dates");
                            return;
                        }
                        ArrayList<Vehicle> vehicleList = ob.getAvailableVehicles(vehicleCateChoice, toDate, fromDate);
                        System.out.println("Index\tVehicle ID\tReg No");
                        for (Vehicle d : vehicleList) {
                            System.out.println(vehicleList.indexOf(d) + 1 + "\t\t" + d.getVehicleId() + "\t\t" + d.getRegNo());
                        }
                        int vehicleId = KeyBoardUtil.getInt("Enter the Vehicle Index: ");
                        Vehicle vehicle = vehicleList.get(vehicleId-1);

                        // Calculate the difference in days
                        LocalDate localDate1 = toDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        LocalDate localDate2 = fromDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        long daysDifference = Math.abs(Duration.between(localDate1.atStartOfDay(), localDate2.atStartOfDay()).toDays());

                        int totalRent = (int) (daysDifference*ob.getDailyRent(vehicle));
                        if (totalRent == 0) {
                            System.out.println("Rent Error");
                            return;
                        }
                        Booking booking = new Booking(customerName, vehicleCateChoice, vehicle.getRegNo(), fromDate, toDate, totalRent, true);
                        ob.bookVehicle(booking);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                case 3:
                    System.out.println("REPORT: ");
                    ob.generateReport();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Enter a valid choice");
            }
        }
    }
}