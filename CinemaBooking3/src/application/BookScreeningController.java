package application;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.shape.Rectangle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class BookScreeningController extends Main {
	@FXML
	public Rectangle A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12;
	public Rectangle B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12;
	public Rectangle C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12;
	public Rectangle D1, D2, D3, D4, D5, D6, D7, D8, D9, D10, D11, D12;
	public Rectangle E1, E2, E3, E4, E5, E6, E7, E8, E9, E10, E11, E12;
	public Rectangle F1, F2, F3, F4, F5, F6, F7, F8, F9, F10, F11, F12;
	@FXML
	public Label seatIds;
	@FXML
	public Label noOfSeats;
	//public Rectangle RectangleArray[] = {A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12, B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12, D1, D2, D3, D4, D5, D6, D7, D8, D9, D10, D11, D12, E1, E2, E3, E4, E5, E6, E7, E8, E9, E10, E11, E12, F1, F2, F3, F4, F5, F6, F7, F8, F9, F10, F11, F12};
	public ArrayList<Rectangle> RectangleArray = new ArrayList<Rectangle>();
	public ArrayList<Seat> Seats = new ArrayList<Seat>();
	String bookings;
	String ScreeningID = "03251217";
	String screenTitle;
	Screening thisScreening;
	
	public void initialize() {
		
		fillArray();
		
	//load up screenings, find the screening

		System.out.println("this is initialising");
		ArrayList<Screening> screenings = this.openScreeningFile();
		for(int i = 0;i<screenings.size();i++) {
			//find the matching dateTime
			Screening screeningx = screenings.get(i);
			if(screeningx.dateTime.equals(ScreeningID)) {
				//then the screening is found, load up Name of the film??
				thisScreening = screeningx;
				System.out.println("Data: " + thisScreening.dateTime + " , " + thisScreening.filmID + " , " + thisScreening.seatAlloc);
				bookings = screeningx.seatAlloc;
			}	
		}
		if (this.thisScreening.dateTime != null) {
			String[] seatNames = {"A1","A2","A3","A4","A5","A6","A7","A8","A9","A10","A11","A12","B1","B2","B3","B4","B5","B6","B7","B8","B9","B10","B11","B12","C1","C2","C3","C4","C5","C6","C7","C8","C9","C10","C11","C12","D1","D2","D3","D4","D5","D6","D7","D8","D9","D10","D11","D12","E1","E2","E3","E4","E5","E6","E7","E8","E9","E10","E11","E12","F1","F2","F3","F4","F5","F6","F7","F8","F9","F10","F11","F12"};
			for (int i = 0; i < bookings.length() ; i++) {
				
				char booked = bookings.charAt(i);
				if (booked == '0') {
					Seats.add(new Seat(seatNames[i], false));
				}
				else if (booked == '1') {
					Seats.add(new Seat(seatNames[i], true));
				}
			}
			
			//RectangleArray[0].setFill(Color.RED);
			//A1.getId()
			for (int j = 0; j < RectangleArray.size(); j++) {
				if (Seats.get(j).booked == true) {
					RectangleArray.get(j).setFill(Color.RED);
				}
				else if (Seats.get(j).booked == false) {
					RectangleArray.get(j).setFill(Color.GREEN);
				}
			}
				
		}
	}
	
	ArrayList<String> seatsSelected = new ArrayList<String>();
	public void getSelected(MouseEvent event) {
		String id = ((Rectangle)event.getSource()).getId().toString();
		//System.out.println(id);
		//A1.setFill(Color.RED);
		//change(id, Color.BLUE);
		Scene scene = A1.getScene();
		Rectangle rect = (Rectangle) scene.lookup("#"+id);
		
		// for item seat in Arraylist Seats
		
		for (Seat seat: Seats) {
			if (seat.seatID.equals(id)){
				if (seat.booked == true) {
					break;
				}
				else if (seat.booked == false && seat.selected == false) {
					rect.setFill(Color.BLUE);
					seat.selected = true;
					seatsSelected.add(id);
					break;
				}
				else if (seat.booked == false && seat.selected == true) {
					rect.setFill(Color.GREEN);
					seat.selected = false;
					seatsSelected.remove(id);
					break;
				}
				
			}
		}
		System.out.println(seatsSelected);
		updateLabels();			
	}
	String printSeats;
	
	public void updateLabels() {
		noOfSeats.setText(Integer.toString(seatsSelected.size()));
		printSeats = "";
		for (String seat: seatsSelected) {
			printSeats += seat+" ";
		}
		seatIds.setText(printSeats);
		
	}
	
	public void book() {
		String screeningId = "0021002";
		String username = "sophia.botz@gmail.com";
		// write to bookings
		// screeningid, username, booked seats
		
		String newLine = screeningId + "," + username + "," + printSeats; 
		//write this to file "bookings"
		
		updateScreenings();
	}
	
	public void updateScreenings() {
		//update screening file - update number of free seats... and which seats are free string
		// read in screenings
		// if (screeningID = screeningID) {
		//make newLine --> update number of seats free booked / using SeatsBooked.size()
		//update string 01010110 - by taking appropriate Index - and changing it to 1
		getNewString(bookings);
	}
	
	public void getNewString (String bookings) {
		StringBuilder newbookings = new StringBuilder(bookings);
		System.out.println(newbookings);
		for (int i=0; i<seatsSelected.size(); i++) {
			for (int j=0; j<Seats.size(); j++) {
				if (Seats.get(j).seatID.equals(seatsSelected.get(i))) {
					if (newbookings.charAt(j) == '0') {
						newbookings.setCharAt(j, '1');
					}
					else {
						System.out.println("Error");
					}
				}
			}	
		}
		System.out.println(newbookings);
			
	}
	
	public void fillArray() {
		RectangleArray.add(A1);
		RectangleArray.add(A2);
		RectangleArray.add(A3);
		RectangleArray.add(A4);
		RectangleArray.add(A5);
		RectangleArray.add(A6);
		RectangleArray.add(A7);
		RectangleArray.add(A8);
		RectangleArray.add(A9);
		RectangleArray.add(A10);
		RectangleArray.add(A11);
		RectangleArray.add(A12);
		RectangleArray.add(B1);
		RectangleArray.add(B2);
		RectangleArray.add(B3);
		RectangleArray.add(B4);
		RectangleArray.add(B5);
		RectangleArray.add(B6);
		RectangleArray.add(B7);
		RectangleArray.add(B8);
		RectangleArray.add(B9);
		RectangleArray.add(B10);
		RectangleArray.add(B11);
		RectangleArray.add(B12);
		RectangleArray.add(C1);
		RectangleArray.add(C2);
		RectangleArray.add(C3);
		RectangleArray.add(C4);
		RectangleArray.add(C5);
		RectangleArray.add(C6);
		RectangleArray.add(C7);
		RectangleArray.add(C8);
		RectangleArray.add(C9);
		RectangleArray.add(C10);
		RectangleArray.add(C11);
		RectangleArray.add(C12);
		RectangleArray.add(D1);
		RectangleArray.add(D2);
		RectangleArray.add(D3);
		RectangleArray.add(D4);
		RectangleArray.add(D5);
		RectangleArray.add(D6);
		RectangleArray.add(D7);
		RectangleArray.add(D8);
		RectangleArray.add(D9);
		RectangleArray.add(D10);
		RectangleArray.add(D11);
		RectangleArray.add(D12);
		RectangleArray.add(E1);
		RectangleArray.add(E2);
		RectangleArray.add(E3);
		RectangleArray.add(E4);
		RectangleArray.add(E5);
		RectangleArray.add(E6);
		RectangleArray.add(E7);
		RectangleArray.add(E8);
		RectangleArray.add(E9);
		RectangleArray.add(E10);
		RectangleArray.add(E11);
		RectangleArray.add(E12);
		RectangleArray.add(F1);
		RectangleArray.add(F2);
		RectangleArray.add(F3);
		RectangleArray.add(F4);
		RectangleArray.add(F5);
		RectangleArray.add(F6);
		RectangleArray.add(F7);
		RectangleArray.add(F8);
		RectangleArray.add(F9);
		RectangleArray.add(F10);
		RectangleArray.add(F11);
		RectangleArray.add(F12);
	}

}


