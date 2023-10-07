package com.client;

import java.text.ParseException;
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
					VehicleCategory vehicleCategory = vehicleCat.get(vehicleType-1);
					
					String manufacturer = KeyBoardUtil.getString("Enter Manufacturer Name: ");
					
					int dailyRent = KeyBoardUtil.getInt("Enter daily rent details: ");
					
					int mileage = KeyBoardUtil.getInt("Enter Mileage deatils (kmpl): ");

					ArrayList<FuelType> fuelTypeList = ob.getFuelType();
					for (FuelType d : fuelTypeList) {
						System.out.println(d.getFuelTypeId() + "\t" + d.getFuelName());
					}
					int fuelTypechoice = KeyBoardUtil.getInt("Choose a Vehicle Type [1,2,3]");
					FuelType fuelType = fuelTypeList.get(fuelTypechoice-1);

					String Description = KeyBoardUtil.getString("Enter Description about vehicle: ");

					vehicle = new Vehicle(regNo, vehicleCategory, manufacturer, dailyRent, mileage, fuelType,
							Description);
					
					ob.addVehicle(vehicle);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case 2:
				try {
					Booking book = new Booking();
					ob.bookVehicle(book);
					String customerName = KeyBoardUtil.getString("Enter Customer Name: ");

					ArrayList<VehicleCategory> vehicleCategoryList = ob.getVehicleCategory();
					for (VehicleCategory d : vehicleCategoryList) {
						System.out.println(d.getVehicleCategoryId() + "\t" + d.getVehicleCategoryName());
					}
					int vehicleTypeChoice = KeyBoardUtil.getInt("Choose a Vehicle Type [1,2,3]");
					VehicleCategory vehicleCateChoice = vehicleCategoryList.get(vehicleTypeChoice-1);
					
					String regNoChoice = KeyBoardUtil.getString("Enter the Registration Number: ");

					int totalRent = KeyBoardUtil.getInt("Enter total rent: ");

					Date toDate = KeyBoardUtil.getDate("Enter from date(YYYY-MM-DD) :");
					
					Date fromDate = KeyBoardUtil.getDate("Enter to date(YYYY-MM-DD) :");
					
					Booking booking = new Booking(customerName, vehicleCateChoice, regNoChoice, fromDate, toDate,
							totalRent, true);

				} catch (ParseException e) {
					e.printStackTrace();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

				break;
			case 3:
				System.out.println("REPORT: ");
				return;
			default:
				System.out.println("Enetr a valid choice");
			}
		}
	}
}
