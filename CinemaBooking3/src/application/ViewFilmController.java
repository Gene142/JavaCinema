package application;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.*;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import application.Main.Film;

public class ViewFilmController extends Main {
	Film currentFilm;
	@FXML
	public TextField txtTitle;
	public TextField txtReleaseDate;
	public TextArea txtDescription;
	public Button btnCancel;
	public Button btnEdit;
	public Button btnEditNow;
	public Label sysMessage;
	public Button btnCancelEdit;
	public ImageView myImageView;
	public Button btnLoad;
	BufferedImage bufferedImage;

	public void initialize() {
		btnCancelEdit.setVisible(false);
		btnEditNow.setVisible(false);
		btnLoad.setVisible(false);
	}

	public void initFilm(Film filmToInit) {
		// load up the selected film
		this.currentFilm = filmToInit;
		this.txtTitle.setText(filmToInit.title);
		this.txtReleaseDate.setText(filmToInit.relDate);
		this.txtDescription.setText(filmToInit.description);
		this.currentFilm = new Film(filmToInit.iD, filmToInit.title, filmToInit.relDate, filmToInit.description);
		//myImageView.
		File file = new File( "id" + filmToInit.iD + ".png");
		try {
            bufferedImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            myImageView.setImage(image);
            
        } catch (IOException ex) {
            Logger.getLogger(AddFilmController.class.getName()).log(Level.SEVERE, null, ex);
        }
	}

	public void editable() {
		txtTitle.setEditable(true);
		txtReleaseDate.setEditable(true);
		txtDescription.setEditable(true);
		btnEditNow.setVisible(true);
		btnEdit.setVisible(false);
		btnCancelEdit.setVisible(true);
		btnLoad.setVisible(true);
	}

	public void cancelEdit() {
		txtTitle.setEditable(false);
		txtReleaseDate.setEditable(false);
		txtDescription.setEditable(false);
		btnEditNow.setVisible(false);
		btnEdit.setVisible(true);
		btnCancelEdit.setVisible(false);
		this.initFilm(this.currentFilm);
	}

	public void editNow() throws Exception {
		editFilm(this.currentFilm.iD);
		this.currentFilm = this.getFilmFromID(this.currentFilm.iD);
		btnCancelEdit.setVisible(false);
		btnEditNow.setVisible(false);
		
		if (bufferedImage == null) {
		}
		else if (bufferedImage != null){
			String filename = "id"+Integer.toString(currentFilm.iD)+".png";
            File outputfile = new File(filename);
            try {
            	ImageIO.write(bufferedImage, "png", outputfile);
			}
            catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
		

	public void cancelPage() {
		// load back to scene.
		try {
			Stage primaryStage = (Stage) (Stage) this.btnCancel.getScene().getWindow();
			Parent root = FXMLLoader.load(getClass().getResource("/application/Main.fxml"));
			Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void editFilm(int filmID) throws Exception {
		// for a given filmID, change the values to be those that are in the textboxes
		// File tempFile = new File("myTempFile.txt");
		File tempFile = new File("temp");
		File filmFile = new File("filmsFile");
		BufferedReader reader = new BufferedReader(new FileReader(filmFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
		String filmToDelete = this.currentFilm.iD + "," + this.removeCommas(this.currentFilm.title) + ","
				+ this.removeCommas(this.currentFilm.relDate) + "," + this.removeCommas(this.currentFilm.description);
		String nextline;
		while ((nextline = reader.readLine()) != null) {
			// trim newline when comparing with lineToRemove
			String trimmedLine = nextline.trim();
			if (trimmedLine.equals(filmToDelete)) {
				writer.write(this.currentFilm.iD + "," + this.removeCommas(this.txtTitle.getText()) + ","
						+ this.removeCommas(this.txtReleaseDate.getText()) + ","
						+ this.removeCommas(this.txtDescription.getText()) + "\n");
			}
			;
			writer.write(nextline + "\n");
		}
		writer.close();
		reader.close();
		boolean successful = tempFile.renameTo(filmFile);

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
