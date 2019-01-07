//Michell Li
//Mli5
package hw2;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller {
	//Creates new person from text inputs and displays recommendednutrientslist in the tableview
	class RecommendNutrientsButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			//get values from text fields to create a new person
			float age = 0;
			float weight = 0;
			float height =0;
			try{
				age = Float.valueOf(NutriByte.view.ageTextField.getText());
			}catch(Exception e){
				age=0;
			}
			try{
				weight = Float.valueOf(NutriByte.view.weightTextField.getText());
			}catch(Exception e){
				weight = 0;
			}
			try{
				height = Float.valueOf(NutriByte.view.heightTextField.getText());
			}catch(Exception e){
				height =0;
			}
			String ing = NutriByte.view.ingredientsToWatchTextArea.getText();

			String activity = NutriByte.view.physicalActivityComboBox.getValue();
			if(activity == null){
				activity = "Sedentary";
			}
			float phys = 0;
			NutriProfiler.PhysicalActivityEnum np = null;
			for(NutriProfiler.PhysicalActivityEnum x: np.values()){
				//System.out.println(activity + " " + x.getName());
				if(activity.toLowerCase().contentEquals(x.getName().toLowerCase())){
					phys = x.getPhysicalActivityLevel();
				}
			}
			if(NutriByte.view.genderComboBox.getValue()!=null){
			if(NutriByte.view.genderComboBox.getValue().contentEquals("Female")){
				NutriByte.person = new Female(age, weight, height, phys, ing);

			}else{
				NutriByte.person = new Male(age, weight, height, phys, ing);

			}
			NutriProfiler.createNutriProfile(NutriByte.person);

			//Binding
			NutriByte.view.recommendedNutrientsTableView.setItems(NutriProfiler.recommendedNutrientsList);
			}
		}			
	}
	//fileopener opens, user chooses file
	//absolutepath used to get entire directory path
	class OpenMenuItemHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			//write your code here
			boolean success = false;
			Stage stage = new Stage();
			FileChooser fileChooser = new FileChooser();

			File file = fileChooser.showOpenDialog(stage);


			if (file != null && (file.getName().endsWith(".csv") || file.getName().endsWith(".xml"))) {
				success = NutriByte.model.readProfiles(file.getAbsolutePath().toString());
			}

			if(success){
				//changes screen to nutriTrackerPanr
				NutriByte.view.root.setCenter(NutriByte.view.nutriTrackerPane);
				//NutriByte.view.initializePrompts();
				NutriProfiler.createNutriProfile(NutriByte.person);
				NutriByte.view.ageTextField.setText(String.valueOf(NutriByte.person.age));
				NutriByte.view.weightTextField.setText(String.valueOf(NutriByte.person.weight));
				NutriByte.view.heightTextField.setText(String.valueOf(NutriByte.person.height));
				NutriByte.view.ingredientsToWatchTextArea.setText(NutriByte.person.ingredientsToWatch);
				if(NutriByte.person instanceof Female){
					NutriByte.view.genderComboBox.setValue("Female");

				}else{
					NutriByte.view.genderComboBox.setValue("Male");
				}

				NutriProfiler.PhysicalActivityEnum p = null;

				for(NutriProfiler.PhysicalActivityEnum pe : p.values()){

					if(NutriByte.person.physicalActivityLevel<=pe.getPhysicalActivityLevel()){
						NutriByte.view.physicalActivityComboBox.setValue(pe.getName());
					}
				}
				//populates tableview based on persons profile
				NutriByte.view.recommendedNutrientsTableView.setItems(NutriProfiler.recommendedNutrientsList);

			}


		}
	}

	//changes screen to nutriTrackerPane
	class NewMenuItemHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			//write your code here
			NutriByte.view.root.setCenter(NutriByte.view.nutriTrackerPane);
			NutriByte.view.initializePrompts();
			NutriByte.view.recommendedNutrientsTableView.getItems().clear();
		}
	}

	class AboutMenuItemHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("About");
			alert.setHeaderText("NutriByte");
			alert.setContentText("Version 2.0 \nRelease 1.0\nCopyleft Java Nerds\nThis software is designed purely for educational purposes.\nNo commercial use intended");
			Image image = new Image(getClass().getClassLoader().getResource(NutriByte.NUTRIBYTE_IMAGE_FILE).toString());
			ImageView imageView = new ImageView();
			imageView.setImage(image);
			imageView.setFitWidth(300);
			imageView.setPreserveRatio(true);
			imageView.setSmooth(true);
			alert.setGraphic(imageView);
			alert.showAndWait();
		}
	}
}
