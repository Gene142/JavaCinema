package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import application.Main.Screening;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * Authors: Gene and Sophia The purpose of this page is to allow the employee to
 * view an individual screening, the seats that have and haven't been booked. On
 * Load: load screenings, find specified time and date, get seatAlloc string
 * (72x 1 or 0) and then fill up the ArrayList of <Rectangle> to assign colours
 * for booked or unbooked seats.
 * 
 * 
 */
public class ViewScreeningEmployeeController extends Main {
	@FXML
	public Rectangle A1, A2, A3, A4, A5, A6, A7, A8, A9, A10, A11, A12;
	public Rectangle B1, B2, B3, B4, B5, B6, B7, B8, B9, B10, B11, B12;
	public Rectangle C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12;
	public Rectangle D1, D2, D3, D4, D5, D6, D7, D8, D9, D10, D11, D12;
	public Rectangle E1, E2, E3, E4, E5, E6, E7, E8, E9, E10, E11, E12;
	public Rectangle F1, F2, F3, F4, F5, F6, F7, F8, F9, F10, F11, F12;
	public ArrayList<Rectangle> RectangleArray = new ArrayList<Rectangle>();
	public Button btnCancel;
	public Button btnDelete;
	public Button btnViewFilm;

	String ScreeningID = "03251217";
	String screenTitle;
	Screening thisScreening;

	public void initialize() {
		// fill up the boxes
		fillArray();
	}

	public void initScreenings(Screening initScreen) {
		// this gets the screening to be viewed, will be set from the AddScreenController 
		this.thisScreening = new Screening(initScreen.filmID,initScreen.dateTime,initScreen.seatAlloc);
		ArrayList<Seat> currentSeats = this.thisScreening.loadSeats(this.thisScreening.seatAlloc);
		// currentSeats(0-71) = the seats.
		for (int j = 0; j < 70; j++) {
			if (currentSeats.get(j).booked == true) {
				this.RectangleArray.get(j).setFill(Color.RED); // if booked, red, otherwise it stays blue.
			}
		}
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
	
	public void cancelPage() {
		//load back to scene.
		try {
		Stage primaryStage = (Stage) (Stage) this.btnCancel.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/application/AddScreening.fxml"));
		Scene scene = new Scene(root,400,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void clickDelete() {
		try {
			deleteScreening(this.thisScreening);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			cancelPage();
		
	}
	
	public void deleteScreening(Screening screening) throws Exception {
		File tempFile = new File("myTempFile.txt");
		File screenFile = new File("Screenings");
		BufferedReader reader = new BufferedReader(new FileReader(screenFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));	
		String screenToDelete = screening.filmID + "," + screening.dateTime + "," + screening.seatAlloc;
		String nextline;

		while((nextline = reader.readLine()) != null) {
		    // trim newline when comparing with lineToRemove
		    String trimmedLine = nextline.trim();
		    if(trimmedLine.equals(screenToDelete)) continue;
		    writer.write(nextline + System.getProperty("line.separator"));
		}
		writer.close(); 
		reader.close(); 
		boolean successful = tempFile.renameTo(screenFile);
		
	}
	public void viewFilm() throws IOException {
		//view the currently chosen screenings film in more depth
		//get film, open film page.
		Film filmToView = this.getFilmFromID(this.thisScreening.filmID);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewFilm.fxml"));
		Stage stage = (Stage) (Stage) this.btnViewFilm.getScene().getWindow();
		Parent root = (Parent) loader.load();
		ViewFilmController controller = loader.<ViewFilmController>getController();
		controller.initFilm(filmToView);
		Scene scene = new Scene(root, 400, 400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
	}


}
