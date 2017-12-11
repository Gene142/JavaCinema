package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class MainController extends Main {
	@FXML
	public TextField txtUsername;
	public TextField txtPassword;
	public Label loginMessage;

	public void SignUp(ActionEvent event) {
		try {
		Stage primaryStage = (Stage) (Stage) txtUsername.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("/application/SignUp.fxml"));
		Scene scene = new Scene(root,400,400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
		
		public void openEmployeeHome() {
			try {
				Stage getStage = (Stage) (Stage) txtUsername.getScene().getWindow();
				Parent root= FXMLLoader.load(getClass().getResource("/application/EmployeeHome.fxml"));
				Scene scene = new Scene(root,400,400);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				getStage.setScene(scene);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		public void openUserHome() {
			try {
				Stage getStage = (Stage) (Stage) txtUsername.getScene().getWindow();
				Parent root= FXMLLoader.load(getClass().getResource("/application/UserHome.fxml"));
				Scene scene = new Scene(root,400,400);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				getStage.setScene(scene);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	
	
	public void Login(ActionEvent event) {
		/**
		 * the algorithm is: get login, password open file search for username if found
		 * { check if pw matches { determine user status + login appropriately} else
		 * "wrong password" else "no user with that username"
		 *
		 */
		// first get the login, password
		String username = this.txtUsername.getText();
		String password = this.txtPassword.getText();
		// now open the file
		ArrayList<String> userPass = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader("Logins"));
			String line;
			while ((line = reader.readLine()) != null) {
				userPass.add(line);
			}
			reader.close();
		} catch (Exception e) {
			System.err.format("Exception occurred trying to read login details");
			e.printStackTrace();
		}
		// now search for the username
		// but first ensure its not empty
		if (username.isEmpty() || password.isEmpty()) {
			loginMessage.setText("empty field");
		} else {
			boolean foundUsername = false;
			for (int i = 0; i < userPass.size(); i++) {
				String[] splitLines = userPass.get(i).split(",");
				if (splitLines[0].equals(username)) {
					foundUsername = true; // username found, now compare password
					if (splitLines[1].equals(password)) {
						loginMessage.setText("match!"); // matched, so check usertype
						if (splitLines[2].equals("Employee")) {
							loginMessage.setText("this is opening the employee page");
							openEmployeeHome();
						
						} else {
							openUserHome();
							loginMessage.setText("this is opening the customer page");
						}

					} else {
						loginMessage.setText("bad Password ");
					}
				}
			}
			if (foundUsername == false) {
				loginMessage.setText("Bad username. Please Sign Up with ODEON below.");
			}

		}
	}

}
