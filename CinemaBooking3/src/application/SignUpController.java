package application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class SignUpController {
@FXML
public TextField txtEmail;
public TextField txtPassword;
public TextField txtFirstName;
public TextField txtLastName;
public TextField txtConfirm;
public Button btnCancel;
public Button btnSignUp;
public Label lblSysMessage;

public void backToLogin() {
	//load back to scene.
	try {
	Stage primaryStage = (Stage) (Stage) this.btnCancel.getScene().getWindow();
	Parent root = FXMLLoader.load(getClass().getResource("/application/Main.fxml"));
	Scene scene = new Scene(root,400,400);
	scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	primaryStage.setScene(scene);
	primaryStage.show();
	} catch (Exception e) {
		e.printStackTrace();
	}
}

public boolean checkData() {
	if(txtFirstName.toString().isEmpty() == true || txtLastName.toString().isEmpty() == true || txtEmail.toString().isEmpty() == true || txtPassword.toString().isEmpty() == true || txtConfirm.toString().isEmpty() == true) {
		lblSysMessage.setText("Please fill out all fields");
		return false;
		
		
	} else if (txtPassword.getText().equals((txtConfirm.getText()))) {
		return true;
	} else {
		lblSysMessage.setText("Please ensure your passwords match");
		return false;
	
	}
}

public void confirmSignUp() {
	//verification
	if(checkData() == true) {
		//add data to file
		String newline = this.txtEmail.getText().toLowerCase() +","+this.txtPassword.getText()+","+"customer"+","+this.txtFirstName.getText()+","+this.txtLastName.getText()+"\n";
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter("Logins", true));
		    writer.write(newline);
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//wipe fields, update message to say 'success' OR return straight to login page.
		this.txtEmail.setText("");
		this.txtPassword.setText("");
		this.txtFirstName.setText("");
		this.txtConfirm.setText("");
		this.txtLastName.setText("");
		this.lblSysMessage.setText("Success: User Added!");
				
	}
	
	
}

}
