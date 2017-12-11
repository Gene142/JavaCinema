package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class AddScreeningController extends Main {
	String[][] dailyFilmList = new String[24][2]; //[0] = title, [1] = seats
	int currentIndex = 0;
	String strTimeDate;
	

	@FXML
	public ListView<String> listOne;
	public ListView<String> listTwo;
	public ListView<String> listThree;
	public DatePicker currentDate;
	public ComboBox<String> cmBoxFilms;
	public TextField searchText;
	public Button btnAddScreening;
	public Button btnViewScreening;
	public Button btnCancel;
	public Label sysMessage;

	public void initialize() {
		// fill in times
		// find the day, then fill up the times - if none then == ""
		// find the day: open the file, load into array
		ArrayList<String> screeningsList = openScreeningsList();
		LocalDate today = LocalDate.now();
		currentDate.setValue(today);
		updateToDate();
		updateComboBox();
	}

	public void initialLists(String[][] dailyFilmList) {
		// begin by emptying the lists
		listOne.getItems().clear();
		listTwo.getItems().clear();
		listThree.getItems().clear();
		// then ensure null doesn't get copied as a film title
		for (int i = 0; i < 24; i++) {
			if (dailyFilmList[i][0] == null) {
				dailyFilmList[i][0] = "";
				dailyFilmList[i][1] = "";
			}
		}
		// fill the array
		ObservableList<String> lstTimesOne = FXCollections.observableArrayList("00:00" + "-" + dailyFilmList[0][0] + dailyFilmList[0][1],
				"01:00" + "-" + dailyFilmList[1][0] + dailyFilmList[1][1], "02:00" + "-" + dailyFilmList[2][0] + dailyFilmList[2][1], "03:00" + "-" + dailyFilmList[3][0] + dailyFilmList[3][1],
				"04:00" + "-" + dailyFilmList[4][0] + dailyFilmList[4][1], "05:00" + "-" + dailyFilmList[5][0] + dailyFilmList[5][1], "06:00" + "-" + dailyFilmList[6][0] + dailyFilmList[6][1],
				"07:00" + "-" + dailyFilmList[7][0] + dailyFilmList[7][1]);
		ObservableList<String> lstTimesTwo = FXCollections.observableArrayList("00:08" + "-" + dailyFilmList[8][0] + dailyFilmList[8][1],
				"09:00" + "-" + dailyFilmList[9][0] + dailyFilmList[9][1], "10:00" + "-" + dailyFilmList[10][0] +dailyFilmList[10][1], "11:00" + "-" + dailyFilmList[11][0] + dailyFilmList[11][1],
				"12:00" + "-" + dailyFilmList[12][0]  + dailyFilmList[12][1], "13:00" + "-" + dailyFilmList[13][0] +  dailyFilmList[13][1], "14:00" + "-" + dailyFilmList[14][0] + dailyFilmList[14][1],
				"15:00" + "-" + dailyFilmList[15][0] + dailyFilmList[15][1]);
		ObservableList<String> lstTimesThree = FXCollections.observableArrayList("16:00" + "-" + dailyFilmList[16][0] + dailyFilmList[16][1],
				"17:00" + "-" + dailyFilmList[17][0] +  dailyFilmList[17][1], "18:00" + "-" + dailyFilmList[18][0] + dailyFilmList[18][1], "19:00" + "-" + dailyFilmList[19][0] + dailyFilmList[19][1],
				"20:00" + "-" + dailyFilmList[20][0] + dailyFilmList[20][1], "21:00" + "-" + dailyFilmList[21][0] +  dailyFilmList[21][1], "22:00" + "-" + dailyFilmList[22][0] + dailyFilmList[22][1],
				"23:00" + "-" + dailyFilmList[23][0] + dailyFilmList[23][1]);
		// populate the lists
		listOne.setItems(lstTimesOne);
		listTwo.setItems(lstTimesTwo);
		listThree.setItems(lstTimesThree);
	}

	public void updateToDate() {
		// update the oberved lists to the selected date: get date, find screenings on
		// date, put in appropriate slots
		// First clear the dailyFilmList
		for (int i = 0; i < 24; i++) {
			this.dailyFilmList[i][0] = "";
			this.dailyFilmList[i][1] = "";
		}
		// then get the date, turn it into a string
		String timeDate = currentDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yy"));
		String[] strTimeDateIntermediary = timeDate.split("/");
		this.strTimeDate = strTimeDateIntermediary[0].concat(strTimeDateIntermediary[1].concat(strTimeDateIntermediary[2]));
		currentDate.setValue(currentDate.getValue());
		// open screening file and then find the screenings of the selected date
		ArrayList<Screening> screeningsList = this.openScreeningFile();
		for (int i = 0; i < screeningsList.size(); i++) {
			if (screeningsList.get(i).dateTime.regionMatches(2, strTimeDate, 0, 6) == true) { // find all screenings on																			// that day
				String specificTime = screeningsList.get(i).dateTime.substring(0, 2);
				Film filmAtSpecTime = this.getFilmFromID(screeningsList.get(i).filmID);
				dailyFilmList[Integer.parseInt(specificTime)][0] = filmAtSpecTime.title;
				int totalbooked = screeningsList.get(i).seatAlloc.replaceAll("0", "").length();
				dailyFilmList[Integer.parseInt(specificTime)][1] = " - Seats remaining: " + Integer.toString(totalbooked);
			}
		}
		initialLists(dailyFilmList);

	}

	public ArrayList<String> openScreeningsList() {
		ArrayList<String> screeningsList = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader("Screenings"));
			String line;
			while ((line = reader.readLine()) != null) {
				screeningsList.add(line);
			}
			reader.close();
		} catch (Exception e) {
			System.err.format("Exception(al) error trying to load films");
			e.printStackTrace();
		}
		return screeningsList;
	}

	public void updateComboBox() {
		// get films
		ObservableList<String> filmsToLoad = FXCollections.observableArrayList();
		ArrayList<Film> filmList = new ArrayList<Film>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader("filmsFile"));
			String line;
			while ((line = reader.readLine()) != null) {
				String[] filmString = line.split(",");
				Film film = new Film(Integer.parseInt(filmString[0]), filmString[1], filmString[2], filmString[3]);
				filmList.add(film);
			}
			reader.close();
		} catch (Exception e) {
			System.err.format("Exception occurred trying to read film list");
			e.printStackTrace();
		}
		// search for films:
		String searchText = this.cmBoxFilms.valueProperty().getValue();
		for (int i = 0; i < filmList.size(); i++) {
		}
		if (searchText == null) {
			// if nothing is being searched, load all films
			for (int i = 0; i < filmList.size(); i++) {
				filmsToLoad.add(filmList.get(i).title);
			}
		} else { // something is being searched

			for (int i = 0; i < filmList.size(); i++) {
				if (filmList.get(i).title.toLowerCase().regionMatches(0, searchText.toLowerCase(), 0,
						searchText.length())) {
					filmsToLoad.add(filmList.get(i).title);
				}

			}
		}
		this.cmBoxFilms.getItems().clear();
		this.cmBoxFilms.getItems().addAll(filmsToLoad);
	}

	public void changeScreening(String timedate, Film newFilm) {
		// changes the current screenings film to a new one
		// first get the timedate requested, if empty new screening, if not then edit
		// screening
		// the timeDate is the selected index + the date.
	}

	public void ListOneIndex(MouseEvent mouseclick) {
		// clear the array, update the pointer of the currently chosen screening
		// just in case previously clicked
		listTwo.getSelectionModel().clearSelection();
		listThree.getSelectionModel().clearSelection();
		// list one: 0 to 7
		this.currentIndex = listOne.getSelectionModel().getSelectedIndex();
	}

	public void ListTwoIndex(MouseEvent mouseclick) {
		// clear the array, update the pointer of the currently chosen screening
		// just in case previously clicked
		listOne.getSelectionModel().clearSelection();
		listThree.getSelectionModel().clearSelection();
		// list two: 8 to 15
		this.currentIndex = (listTwo.getSelectionModel().getSelectedIndex() + 8);

	}

	public void ListThreeIndex(MouseEvent mouseclick) {
		// clear the array, update the pointer of the currently chosen screening
		// just in case previously clicked
		listTwo.getSelectionModel().clearSelection();
		listOne.getSelectionModel().clearSelection();
		// list three: 16 to 23
		this.currentIndex = listThree.getSelectionModel().getSelectedIndex() + 16;
	}

	public void ViewScreening() throws IOException {
		// whats the screening?
		if ((currentIndex >= 0 && currentIndex <= 23)) {
			if (dailyFilmList[currentIndex][0].equals("")) {
				// then its empty:
				sysMessage.setText("ErroR!");
			} else { // its not empty, so find the screening. We have dateTime.
				String chosentime = String.format("%02d", currentIndex).concat(this.strTimeDate);
				Screening screening = this.getScreeningFromID(chosentime);
				// and now load up the ViewScreening Page.
				FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewScreeningEmployee.fxml"));
				Stage stage = (Stage) (Stage) this.btnViewScreening.getScene().getWindow();
				Parent root = (Parent) loader.load();
				ViewScreeningEmployeeController controller = loader.<ViewScreeningEmployeeController>getController();
				controller.initScreenings(screening);
				Scene scene = new Scene(root, 400, 400);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				stage.setScene(scene);
			}
		}
	}

	public void AddScreening() {
		// get timeDate - if no current screening then get film title, write
		System.out.println("index: " + currentIndex);
		if (dailyFilmList[currentIndex][0] == "") { // i.e. its not booked
			// get the chosen film - if one is not chosen, write to choose.
			String searchText = this.cmBoxFilms.valueProperty().getValue();
			System.out.println(searchText);
			if (searchText == null) {
				sysMessage.setText("No movie chosen. Please choose a movie to make a screening");
			} else {
				Film newFilm = this.getFilmFromTitle(searchText);
				if(newFilm.iD == -1) {
						//this means it searched the file and didnt find the movie.
						sysMessage.setText("No film found. Please enter a valid film name or choose on from the list");
				} else {
				System.out.println("pay attention " + newFilm.iD + newFilm.title);
				String chosentime = String.format("%02d", currentIndex).concat(this.strTimeDate);
				String newline = newFilm.iD + "," + chosentime + ","
						+ "000000000000000000000000000000000000000000000000000000000000000000000000" + "\n";
				BufferedWriter writer;
				try {
					writer = new BufferedWriter(new FileWriter("Screenings", true));
					writer.write(newline);
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			}
		} else {
			sysMessage.setText("There is already a screening for this time. Please edit the screening, choose a different time.");
		}
		updateToDate();
	}

}
