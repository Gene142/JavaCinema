package application;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class AddFilmController extends Main {
	@FXML
	public Button btnCancel;
	public Button btnAddFilm;
	public TextField inputTitle;
	public DatePicker inputDate;
	public TextArea inputDescription;
	public Label myMessage;
	public ImageView myImageView;
	public Button btnLoad;
	BufferedImage bufferedImage;
	
	public void addFilm() {
		
		String newTitle = inputTitle.getText();
		String newDescription = inputDescription.getText();
		String newDate;
		
		if (newTitle.isEmpty() || inputDate.getValue()==null || newDescription.isEmpty()) {
			myMessage.setText("please fill in all fields");
		}
		// else if check for other problems with input
		
		else {
			newDate = inputDate.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			String[] strTimeDateIntermediary = newDate.split("/");
			newDate = strTimeDateIntermediary[0].concat(strTimeDateIntermediary[1].concat(strTimeDateIntermediary[2]));

			// check if film already exists in  records
			ArrayList<String> films = new ArrayList<String>();
			try {
				BufferedReader reader = new BufferedReader(new FileReader("filmsFile"));
				String line;
				while ((line=reader.readLine()) != null) {
						films.add(line);
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			} 
				
			Boolean filmFound = false;
			ArrayList<Integer> filmIDs = new ArrayList<Integer>();
			for (int i=0; i<films.size();i++) {
				String[] fileline = films.get(i).split(",");
				String filmTitle = fileline[1];
				String filmDate = fileline[2];
				int filmID = Integer.parseInt(fileline[0]);
				filmIDs.add(filmID);
				if (filmTitle.equals(newTitle)) {
					if (filmDate.equals(newDate)){
						myMessage.setText("Film/release date combination already exists in database");
						filmFound = true;
					}
				}
			}
			if(filmFound == false) {
				// save details in file
				// make unique filmID by finding largest ID and adding 1
				int newFilmID = 1;
				if(filmIDs.size() > 0){
					newFilmID = Collections.max(filmIDs) + 1;
				}
				if (bufferedImage == null) {
					myMessage.setText("Please upload an image for the film before saving");
				}
				else if (bufferedImage != null){
					String filename = "id"+Integer.toString(newFilmID)+".png";
		            File outputfile = new File(filename);
		            try {
		            	ImageIO.write(bufferedImage, "png", outputfile);
					}
		            catch (IOException e1) {
						e1.printStackTrace();
					}
	               
					String newline = Integer.toString(newFilmID)+","+newTitle+","+newDate+","+newDescription+"\n";
					BufferedWriter writer;
					try {
						writer = new BufferedWriter(new FileWriter("filmsFile", true));
					    writer.write(newline);
					    writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					//display message,stay on page and empty fields!
					myMessage.setText("Great! The film was added!");
					inputTitle.setText("");
					inputDate.setValue(null);
					myImageView.setImage(null);
					
					inputDescription.setText("");
				}
					
			}
		}
		
		
	}
	
	public void backToEmployee() {
		try {
			Stage getstage = (Stage) (Stage) btnCancel.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("/application/EmployeeHome.fxml")); 
			Scene scene = new Scene(root,500,300);
		    getstage.setScene(scene);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			getstage.setScene(scene);	
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void loadImage(ActionEvent t) {
           FileChooser fileChooser = new FileChooser();
            
           //Set extension filter
           FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
           FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
           fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
             
           //Show open file dialog
           File file = fileChooser.showOpenDialog(null);
                       
           try {
               bufferedImage = ImageIO.read(file);
               Image image = SwingFXUtils.toFXImage(bufferedImage, null);
               myImageView.setImage(image);
               
           } catch (IOException ex) {
               Logger.getLogger(AddFilmController.class.getName()).log(Level.SEVERE, null, ex);
           }
           
	}

}
