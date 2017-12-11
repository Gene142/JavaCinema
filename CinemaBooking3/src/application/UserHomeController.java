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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;


public class UserHomeController extends Main {
	User currentUser;
	@FXML
	public TextField txtFirstName;
	public TextField txtLastName;
	public TextField txtEmail;
	public Button btnBookings;
	public Button btnScreenings;
	public Button btnFilms;
	
	public void initialUser(User userLogged) {
		txtFirstName.setText(userLogged.firstName);
		txtLastName.setText(userLogged.lastName);
		txtEmail.setText(userLogged.userEmail);
		currentUser = userLogged;
	}
	
	public void goToViewScreenings() {
		try {
			Stage primaryStage = (Stage) (Stage) btnBookings.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("/application/MyBookings.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
	}
	
	public void goToViewBookings() {
		try {
			Stage primaryStage = (Stage) (Stage) btnFilms.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("/application/ViewFilmsUser.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
	}
	
	public void goToViewFilms() {
		try {
			Stage primaryStage = (Stage) (Stage) btnScreenings.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("/application/ViewScreeningsUser.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
	}

}
