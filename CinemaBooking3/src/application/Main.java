package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import application.Main.Screening;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	public Stage theStage;

	@Override
	public void start(Stage primaryStage) {
		try {
			// BorderPane root = new BorderPane();
			theStage = primaryStage;
			Parent root = FXMLLoader.load(getClass().getResource("/application/Main.fxml"));
			Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			theStage.setScene(scene);
			theStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String removeCommas(String mayHaveComma) {
		//this takes in a string, removes all commas and then sends them back out.
		String escapedCSV = "";
		for(int i = 0; i < mayHaveComma.length();i++ ) {
			escapedCSV = mayHaveComma.replaceAll(",", "");
		} return escapedCSV;
	}	
	
	public ArrayList<Screening> openScreeningFile() {
		// this creates an array list of screenings (i.e. filmID, dateTime, seatAlloc
		ArrayList<Screening> screeningList = new ArrayList<Screening>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader("Screenings"));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] splitLines = line.split(",");
				Screening screening = new Screening(Integer.parseInt(splitLines[0]), splitLines[1], splitLines[2]);
				screeningList.add(screening);
			}
			reader.close();
		} catch (Exception e) {
			System.err.format("Exception occurred trying to read film list");
			e.printStackTrace();
		}

		return screeningList;
	}

	public class Film {

		int iD;
		String title;
		String description;
		ArrayList<String> filmList;
		String relDate;
		// add DATE to this. AND PICTURE

		// constructor
		public Film(int id, String title, String relDate, String description) {
			// initialise the variables, handle validation on the other end!
			this.iD = id;
			this.title = title;
			this.description = description;
			this.relDate = relDate;
		}

		public void writeNewScreening(Screening screening) {
			String newLine = "\n" + this.iD + "," + screening.dateTime + "," + screening.seatAlloc;
			BufferedWriter writer;
			try {
				writer = new BufferedWriter(new FileWriter("Screenings", true));
				writer.write(newLine);
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void writeFilm() {
			String newLine = this.iD + "," + this.title + "," + this.relDate + "," + this.description;
			BufferedWriter writer;
			try {
				writer = new BufferedWriter(new FileWriter("filmsFile", true));
				writer.write(newLine);
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public boolean filmDoesntExist() {
			ArrayList<String> filmList = openFilmFile();
			boolean filmFound = false;
			int i = 0;
			while (filmFound == false) {
				String[] splitLines = filmList.get(i).split(",");
				if (splitLines[1].equals(this.title)) {
					if (splitLines[2].equals(this.relDate)) {
						filmFound = true;
					}
				}
				i++;
			}
			return !(filmFound);
		}

		// to open the list of films
		public ArrayList<String> openFilmFile() {
			ArrayList<String> filmList = new ArrayList<String>();
			try {
				BufferedReader reader = new BufferedReader(new FileReader("filmsFile"));
				String line;
				while ((line = reader.readLine()) != null) {
					filmList.add(line);
				}
				reader.close();
			} catch (Exception e) {
				System.err.format("Exception occurred trying to read film list");
				e.printStackTrace();
			}

			return filmList;

		}


		public Film getFilmByTitle(String filmTitle, ArrayList<String> filmList) {
			String title = "";
			String description = "";
			int iD = 0;
			String dateRel = "";
			boolean filmFound = false;
			int i = 0;
			while (filmFound == false) {
				String[] splitLines = filmList.get(i).split(",");
				if (splitLines[1].toLowerCase().equals(filmTitle.toLowerCase())) {
					filmFound = true;
					title = splitLines[1];
					description = splitLines[3];
					dateRel = splitLines[2];
					iD = Integer.parseInt(splitLines[0]);

				}
				i = i++;
			}
			if (filmFound == false) {
				title = "0";
				description = "0";
				iD = 99999999;
				dateRel = "0";
			}
			Film foundFilm = new Film(iD, title, description, dateRel);
			return foundFilm;
		}

		public ArrayList<String> openScreeningFile() {
			ArrayList<String> screeningList = new ArrayList<String>();
			try {
				BufferedReader reader = new BufferedReader(new FileReader("Screenings"));
				String line;
				while ((line = reader.readLine()) != null) {
					screeningList.add(line);
				}
				reader.close();
			} catch (Exception e) {
				System.err.format("Exception occurred trying to read film list");
				e.printStackTrace();
			}

			return screeningList;
		}

	}

	public ArrayList<String> openFilmFile() {
		ArrayList<String> filmList = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader("filmsFile"));
			String line;
			while ((line = reader.readLine()) != null) {
				filmList.add(line);
			}
			reader.close();
		} catch (Exception e) {
			System.err.format("Exception occurred trying to read film list");
			e.printStackTrace();
		}

		return filmList;

	}
	// for adding a new film
	public int getLastFilmID(ArrayList<String> filmList) {
		int countId = 0;
		for (int i = 0; i < filmList.size(); i++) {
			String[] splitLines = filmList.get(i).split(",");
			int filmId = Integer.parseInt(splitLines[0]);
			if (countId < filmId) {
				countId = filmId;
			}
		}
		return (countId + 1);
	}

	public class Screening {

		int filmID;
		String dateTime;
		String seatAlloc;

		public Screening(int filmID, String dateTime, String seatAlloc) {
			// Screenings are made via title, ID or relDate of the movie? and DateTime of
			// course.
			this.filmID = filmID;
			this.dateTime = dateTime;
			this.seatAlloc = seatAlloc;
		}

		public ArrayList<Seat> loadSeats(String seatsAlloc) {
			//takes a string of 72x 0 or 1 and turns it into arraylist of seats
			ArrayList<Seat> initialSeats = new ArrayList<Seat>();
			for (int i = 0; i < 71; i++) {
				if (seatsAlloc.substring(i, i + 1).equals("1")) {
					Seat newSeat = new Seat(Integer.toString(i), true);
					initialSeats.add(newSeat);
				} else {
					Seat newSeat = new Seat(Integer.toString(i), false);
					initialSeats.add(newSeat);
				}
			}
			return initialSeats;
		}

		public void editScreeningsFilm(Film newFilm) {
			// load into array, find what must be edited, edit, write new file, delete old,
			// rename new.
			BufferedWriter writer;
			ArrayList<Screening> screenings = openScreeningFile();
			//find what must be edited - in this case, the filmID of the screening
			try {
			writer = new BufferedWriter(new FileWriter("Screenings", true));
			for(int i = 0;i < screenings.size();i++) {
				if(screenings.get(i).equals(this.dateTime)) {
					//once found: replace the filmID, write to a file
					screenings.get(i).filmID = newFilm.iD;
					writer.write(newFilm.iD + "," + screenings.get(i).dateTime + "," + screenings.get(i).seatAlloc);
				} //if the line isnt the one to be changed, just write it.
				writer.write(screenings.get(i).filmID + "," + screenings.get(i).dateTime + "," + screenings.get(i).seatAlloc);
				} 	writer.close();
			} catch(IOException e) {
					e.printStackTrace();
				}
			    
			}
		}

		public Film getFilmFromID(int filmID) {
			String title = "";
			String description = "";
			String relDate = "";
			Film film;
			ArrayList<String> filmList = openFilmFile();
			for(int i = 0; i < filmList.size();i++) {
				String[] splitLines = filmList.get(i).split(",");
				if (Integer.parseInt(splitLines[0]) == (filmID)) {
					title = splitLines[1];
					description = splitLines[3];
					relDate = splitLines[2];
	
				} 
			}
			film = new Film(filmID, title, relDate, description);
			return film;
		}
		
		public Film getFilmFromTitle(String filmtitle) {
			String description = "";
			String relDate = "";
			int filmID = -1;
			Film film;
			ArrayList<String> filmList = openFilmFile();
			boolean found = false;
			for (int i = 0;i < filmList.size();i++) {
				String[] splitLines = filmList.get(i).split(",");
				if (splitLines[1].toLowerCase().equals(filmtitle.toLowerCase())) {
					filmID = Integer.parseInt(splitLines[0]);
					description = splitLines[3];
					relDate = splitLines[2];
				}
			}
			film = new Film(filmID, filmtitle, relDate, description);
			return film;

			// this one
		}
		
		
		public Screening getScreeningFromID(String timedate) {
			Screening chosenScreen = new Screening(0,"0","0");
			ArrayList<Screening> screenings = openScreeningFile();
			boolean found = false;
			int i = 0;
			while(found == false) {
				if(screenings.get(i).dateTime.equals(timedate)) {
					chosenScreen.dateTime = timedate;
					chosenScreen.filmID = screenings.get(i).filmID;
					chosenScreen.seatAlloc = screenings.get(i).seatAlloc;
					found = true;
				} else {
					i++;
				}
			} return chosenScreen; 
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
	public class Seat {
		public String seatID;
		public Boolean booked;
		public boolean selected = false;

		public Seat(String id, boolean booked) {
			this.seatID = id;
			this.booked = booked;
		}
		public void changeSelected() {
	    	if(this.selected == true) {
	    		this.selected = false;
	    	}
	    	else if(this.selected ==false) {
	    		this.selected = true;
	    	}
	    }
	}
	
	public class User {
		public String userEmail;
		public String firstName;
		public String lastName;
		public String password;
		public String[][] bookingHistory;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
