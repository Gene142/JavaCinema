package application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class EmployeeHomeController {
	@FXML
	public Button btnAddFilm;
	public Button btnAddScreening;
	public Button btnViewScreening;
	
	public void addFilm() {
		try {
		Stage primaryStage = (Stage) (Stage) btnAddFilm.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/application/AddFilm.fxml"));
		Scene scene = new Scene(root,400,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addScreening() {
		try {
		Stage primaryStage = (Stage) (Stage) btnAddScreening.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/application/AddScreening.fxml"));
		Scene scene = new Scene(root,400,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void viewScreening() {
			try {
			Stage primaryStage = (Stage) (Stage) btnAddScreening.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("/application/ViewScreeningEmployee.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

