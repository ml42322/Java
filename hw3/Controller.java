//Michell Li
//Mli5
package hw3;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import hw3.Product.ProductNutrient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
	float age = 0;
	float weight = 0;
	float height =0;
	ObservableList<Product> localList = FXCollections.observableArrayList(); //used for add diet handler


	//validating textfields for the new button listeners
	public boolean validation(){
		boolean success = false;

		try{
			if(!NutriByte.view.ageTextField.getText().isEmpty()){
				age = Float.valueOf(NutriByte.view.ageTextField.getText());
				if(age<0) {
					throw(new Exception());
				}
			}
		}catch(Exception e){
			age = 0;
			return success;
		}

		try{
			if(!NutriByte.view.weightTextField.getText().isEmpty()){
				weight = Float.valueOf(NutriByte.view.weightTextField.getText());
				if(weight<0){
					throw(new Exception());
				}
			}
		}catch(Exception e){
			weight = 0;
			return success;

		}
		try{
			if(!NutriByte.view.heightTextField.getText().isEmpty()){

				height = Float.valueOf(NutriByte.view.heightTextField.getText());
				if(height<0) {
					throw(new Exception());
				}
			}


		}catch(Exception e){
			height = 0;
			return success;

		}

		String ing = NutriByte.view.ingredientsToWatchTextArea.getText();

		String activity = NutriByte.view.physicalActivityComboBox.getValue();
		if(activity == null){
			activity = "Sedentary";
		}
		float phys = 0;
		NutriProfiler.PhysicalActivityEnum np = null;
		for(NutriProfiler.PhysicalActivityEnum x: np.values()){
			if(activity.toLowerCase().contentEquals(x.getName().toLowerCase())){
				phys = x.getPhysicalActivityLevel();
			}
		}
		if(NutriByte.view.genderComboBox.getValue()!=null && age!=0 && weight!=0 && height !=0 && activity !=null){
			if(NutriByte.view.genderComboBox.getValue().contentEquals("Female")){
				NutriByte.person = new Female(age, weight, height, phys, ing);

			}else{
				NutriByte.person = new Male(age, weight, height, phys, ing);

			}
			success = true;
			localList.clear();
			NutriByte.view.productsComboBox.getSelectionModel().clearSelection();
			//NutriProfiler.createNutriProfile(NutriByte.person);
		}


		return success;
	}
	class RecommendNutrientsButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			//get values from text fields to create a new person
			age = 0;
			weight = 0;
			height = 0;
			try{
				NutriByte.view.ageTextField.setStyle("-fx-text-fill: black");
				if(!NutriByte.view.ageTextField.getText().isEmpty()){
					age = Float.valueOf(NutriByte.view.ageTextField.getText());
					if(age<=0) {
						throw (new InvalidProfileException("Age must be a positive number"));
					}
				}
			}catch(NumberFormatException m){
				age =0;
				try{

					throw (new InvalidProfileException("Incorrect age input. Must be a number") );
				}catch(InvalidProfileException e){
					NutriByte.view.ageTextField.setStyle("-fx-text-fill: red");
					return;

				}
			}catch(InvalidProfileException e){
				age=0;
				NutriByte.view.ageTextField.setStyle("-fx-text-fill: red");
				return;
			}

			try{
				NutriByte.view.weightTextField.setStyle("-fx-text-fill: black");
				if(!NutriByte.view.weightTextField.getText().isEmpty()){
					weight = Float.valueOf(NutriByte.view.weightTextField.getText());
					if(weight<=0) throw (new InvalidProfileException("Weight must be a positive number"));
				}
			}catch(InvalidProfileException e){
				weight = 0;
				NutriByte.view.weightTextField.setStyle("-fx-text-fill: red");
				return;


			}catch(NumberFormatException m){
				weight =0;
				NutriByte.view.weightTextField.setStyle("-fx-text-fill: red");
				try{
					throw (new InvalidProfileException("Incorrect weight input. Must be a number") );
				}catch(InvalidProfileException e){
					return;

				}

			}
			try{
				if(!NutriByte.view.weightTextField.getText().isEmpty()){

					height = Float.valueOf(NutriByte.view.heightTextField.getText());
					NutriByte.view.heightTextField.setStyle("-fx-text-fill: black");
					if(height<=0) throw (new InvalidProfileException("Height must be a positive number"));
				}

			}catch(InvalidProfileException e){
				height =0;
				NutriByte.view.heightTextField.setStyle("-fx-text-fill: red");
				return;

			}catch(NumberFormatException m){
				height = 0;
				NutriByte.view.heightTextField.setStyle("-fx-text-fill: red");
				try{
					throw (new InvalidProfileException("Incorrect height input. Must be a number") );
				}catch(InvalidProfileException e){
					return;
				}

			}

			try{
				if(NutriByte.view.genderComboBox.getValue() ==null){
					throw(new InvalidProfileException("Missing gender information"));
				}else
					if(NutriByte.view.ageTextField.getText().isEmpty()){
						throw(new InvalidProfileException("Missing age information"));
					}else
						if(NutriByte.view.weightTextField.getText().isEmpty()){
							throw(new InvalidProfileException("Missing weight information"));
						}else
							if(NutriByte.view.heightTextField.getText().isEmpty()){
								throw(new InvalidProfileException("Missing height information"));
							}
			}catch(InvalidProfileException e){

			}

			String ing = NutriByte.view.ingredientsToWatchTextArea.getText();

			String activity = NutriByte.view.physicalActivityComboBox.getValue();
			if(activity == null){
				activity = "Sedentary";
			}
			float phys = 0;
			NutriProfiler.PhysicalActivityEnum np = null;
			for(NutriProfiler.PhysicalActivityEnum x: np.values()){
				if(activity.toLowerCase().contentEquals(x.getName().toLowerCase())){
					phys = x.getPhysicalActivityLevel();
				}
			}
			if(NutriByte.view.genderComboBox.getValue()!=null && age!=0 && weight!=0 && height !=0 && activity !=null){
				if(NutriByte.view.genderComboBox.getValue().contentEquals("Female")){
					NutriByte.person = new Female(age, weight, height, phys, ing);

				}else{
					NutriByte.person = new Male(age, weight, height, phys, ing);

				}
				NutriProfiler.createNutriProfile(NutriByte.person);
				//Binding
				NutriByte.view.recommendedNutrientsTableView.setItems(NutriByte.person.recommendedNutrientsList);
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
				NutriByte.person = null;
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
				NutriByte.view.recommendedNutrientsTableView.setItems(NutriByte.person.recommendedNutrientsList);
				NutriByte.view.dietProductsTableView.setItems(NutriByte.person.dietProductsList);
				NutriByte.view.nutriChart.updateChart();
				if(NutriByte.person.dietProductsList.size() >0 ){
					NutriByte.view.searchResultSizeLabel.setText(NutriByte.person.dietProductsList.size() + " product(s) found");
					NutriByte.view.productsComboBox.setItems(NutriByte.person.dietProductsList);
					NutriByte.view.productsComboBox.setPromptText(NutriByte.person.dietProductsList.get(0).getProductName());
					NutriByte.view.productsComboBox.getSelectionModel().select(0);

					if(NutriByte.view.productsComboBox.getSelectionModel().getSelectedIndex()>-1){
						Product selectedp = NutriByte.view.productsComboBox.getSelectionModel().getSelectedItem();

						ObservableList<Product.ProductNutrient> prodNuts = FXCollections.observableArrayList();
						for(Entry<String, ProductNutrient> pn : selectedp.getProductNutrients().entrySet()){
							prodNuts.add(pn.getValue());

						}
						NutriByte.view.productNutrientsTableView.setItems(prodNuts);
						NutriByte.view.productIngredientsTextArea.setText("Product ingredients: " + selectedp.getIngredients());
						NutriByte.view.servingSizeLabel.setText(String.valueOf(selectedp.getServingSize())+selectedp.getServingUom());
						NutriByte.view.dietServingUomLabel.setText(selectedp.getServingUom());
						NutriByte.view.householdSizeLabel.setText(String.valueOf(selectedp.getHouseholdSize())+" " +selectedp.getHouseholdUom());
						NutriByte.view.dietHouseholdUomLabel.setText(selectedp.getHouseholdUom());

					}



				}

			}else{
				//if the csv file had invalid person, clear the nutribyte screen
				NutriByte.view.recommendedNutrientsTableView.getItems().clear();
				NutriByte.view.productSearchTextField.clear();
				NutriByte.view.nutrientSearchTextField.clear();
				NutriByte.view.ingredientSearchTextField.clear();
				NutriByte.view.productsComboBox.getSelectionModel().clearSelection();
				NutriByte.view.productsComboBox.setPromptText("Select Product");
				NutriByte.view.nutriChart.clearChart();
				NutriByte.view.dietServingSizeTextField.clear();
				NutriByte.view.dietHouseholdSizeTextField.clear();
				NutriByte.view.servingSizeLabel.setText("");
				NutriByte.view.householdSizeLabel.setText("");
				NutriByte.view.dietProductsTableView.getItems().clear();
				NutriByte.view.productNutrientsTableView.getItems().clear();
				NutriByte.view.ingredientsToWatchTextArea.setText("");
				NutriByte.view.productIngredientsTextArea.setText("");
				NutriByte.view.searchResultSizeLabel.setText("");
				if(NutriByte.person != null){
					NutriByte.person.dietProductsList.clear();
					NutriByte.person.dietNutrientsMap.clear();
				}
				NutriByte.view.initializePrompts();
				localList.clear();


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
			NutriByte.view.productSearchTextField.clear();
			NutriByte.view.nutrientSearchTextField.clear();
			NutriByte.view.ingredientSearchTextField.clear();
			NutriByte.view.productsComboBox.getSelectionModel().clearSelection();
			NutriByte.view.productsComboBox.setPromptText("Select Product");
			NutriByte.view.nutriChart.clearChart();
			NutriByte.view.dietServingSizeTextField.clear();
			NutriByte.view.dietHouseholdSizeTextField.clear();
			NutriByte.view.servingSizeLabel.setText("");
			NutriByte.view.householdSizeLabel.setText("");
			NutriByte.view.dietProductsTableView.getItems().clear();
			NutriByte.view.productNutrientsTableView.getItems().clear();
			NutriByte.view.ingredientsToWatchTextArea.setText("");
			NutriByte.view.productIngredientsTextArea.setText("");
			NutriByte.view.searchResultSizeLabel.setText("");

			if(NutriByte.person != null){
				NutriByte.person.dietProductsList.clear();
				NutriByte.person.dietNutrientsMap.clear();
			}
			//listener for textfields,calls validation method to ensure entries are valid
			NutriByte.view.genderComboBox.valueProperty().addListener((observable, oldValue, newValue)-> {
				boolean success= false;
				if(newValue != null){


					if(!newValue.equals(oldValue)){
						if(NutriByte.view.genderComboBox.getValue()!=null && !NutriByte.view.weightTextField.getText().isEmpty() && !NutriByte.view.heightTextField.getText().isEmpty() && !NutriByte.view.ageTextField.getText().isEmpty()){
							ObservableList<Product> localList = FXCollections.observableArrayList();

							if(NutriByte.person != null){
								localList= NutriByte.person.dietProductsList;
								success = validation();
								NutriByte.person.dietProductsList = localList;

							}else{
								success = validation();


							}
							if(success){
								NutriProfiler.createNutriProfile(NutriByte.person);
								NutriByte.person.populateDietNutrientsMap();
								NutriByte.view.nutriChart.updateChart();
								NutriByte.view.recommendedNutrientsTableView.setItems(NutriByte.person.recommendedNutrientsList);
								success = false;

							}
						}
					}
				}

			});
			NutriByte.view.physicalActivityComboBox.valueProperty().addListener((observable, oldValue, newValue)-> {
				boolean success = false;
				if(newValue!= null){
					if(!newValue.equals(oldValue)){
						if(NutriByte.view.genderComboBox.getValue()!=null && !NutriByte.view.weightTextField.getText().isEmpty() && !NutriByte.view.heightTextField.getText().isEmpty() && !NutriByte.view.ageTextField.getText().isEmpty()){
							if(NutriByte.person != null){
								String activity = NutriByte.view.physicalActivityComboBox.getValue();
								if(activity == null){
									activity = "Sedentary";
								}
								float phys = 0;
								NutriProfiler.PhysicalActivityEnum np = null;
								for(NutriProfiler.PhysicalActivityEnum x: np.values()){
									if(activity.toLowerCase().contentEquals(x.getName().toLowerCase())){
										phys = x.getPhysicalActivityLevel();
									}
								}
								NutriByte.person.physicalActivityLevel = phys;
								success = true;


							}else{
								success = validation();

							}
							if(success){
								NutriProfiler.createNutriProfile(NutriByte.person);
								NutriByte.person.populateDietNutrientsMap();
								NutriByte.view.nutriChart.updateChart();
								NutriByte.view.recommendedNutrientsTableView.setItems(NutriByte.person.recommendedNutrientsList);
								success = false;

							}
						}
					}
				}

			});
			NutriByte.view.ageTextField.textProperty().addListener((observable, oldValue, newValue)-> {
				boolean success = false;
				if(!newValue.equals(oldValue)){
					float age = 0;
					try{
						NutriByte.view.ageTextField.setStyle("-fx-text-fill: black");
						if(!NutriByte.view.ageTextField.getText().isEmpty()){
							age = Float.valueOf(NutriByte.view.ageTextField.getText());
							if(age<0) {
								NutriByte.view.ageTextField.setStyle("-fx-text-fill: red");
							}
						}
					}catch(NumberFormatException m){
						NutriByte.view.ageTextField.setStyle("-fx-text-fill: red");


					}catch(InvalidProfileException e){
						age=0;
						NutriByte.view.ageTextField.setStyle("-fx-text-fill: red");
					}
					if(NutriByte.view.genderComboBox.getValue()!=null && !NutriByte.view.weightTextField.getText().isEmpty() && !NutriByte.view.heightTextField.getText().isEmpty()){
						if(NutriByte.person != null){
							if(age>0){
								NutriByte.person.age = age;
								success = true;
							}

						}else{
							success = validation();
						}
						if(success){
							NutriProfiler.createNutriProfile(NutriByte.person);

							NutriByte.person.populateDietNutrientsMap();
							NutriByte.view.nutriChart.updateChart();
							NutriByte.view.recommendedNutrientsTableView.setItems(NutriByte.person.recommendedNutrientsList);
							success = false;

						}
					}
				}




			});
			NutriByte.view.ingredientsToWatchTextArea.textProperty().addListener((observable, oldValue, newValue)-> {
				if(!newValue.equals(oldValue)){
					if(NutriByte.view.genderComboBox.getValue()!=null && !NutriByte.view.weightTextField.getText().isEmpty() && !NutriByte.view.heightTextField.getText().isEmpty() && !NutriByte.view.ageTextField.getText().isEmpty()){
						if(NutriByte.person != null){
							NutriByte.person.ingredientsToWatch = NutriByte.view.ingredientsToWatchTextArea.getText().toString();

						}else{
							validation();
						}
					}
				}

			});
			NutriByte.view.weightTextField.textProperty().addListener((observable, oldValue, newValue)-> {
				boolean success = false;
				if(!newValue.equals(oldValue)){
					float weight =0;
					try{
						NutriByte.view.weightTextField.setStyle("-fx-text-fill: black");

						weight = Float.valueOf(NutriByte.view.weightTextField.getText());
						if(weight<0) {
							NutriByte.view.weightTextField.setStyle("-fx-text-fill: red");

						}

					}catch(InvalidProfileException e){
						weight = 0;
						NutriByte.view.weightTextField.setStyle("-fx-text-fill: red");

					}catch(NumberFormatException m){
						NutriByte.view.weightTextField.setStyle("-fx-text-fill: red");

					}
					if(NutriByte.view.genderComboBox.getValue()!=null && !NutriByte.view.ageTextField.getText().isEmpty() && !NutriByte.view.heightTextField.getText().isEmpty()){
						if(NutriByte.person != null){
							if(weight>0){
								NutriByte.person.weight = weight;
								success = true;
							}

						}else{
							success = validation();
						}
						if(success){
							NutriProfiler.createNutriProfile(NutriByte.person);

							NutriByte.person.populateDietNutrientsMap();
							NutriByte.view.nutriChart.updateChart();

							NutriByte.view.recommendedNutrientsTableView.setItems(NutriByte.person.recommendedNutrientsList);
							success = false;

						}
					}
				}

			});
			NutriByte.view.heightTextField.textProperty().addListener((observable, oldValue, newValue)-> {
				boolean success = false;
				if(!newValue.equals(oldValue)){
					float height =0;
					try{
						height = Float.valueOf(NutriByte.view.heightTextField.getText());
						NutriByte.view.heightTextField.setStyle("-fx-text-fill: black");
						if(height<0){
							NutriByte.view.heightTextField.setStyle("-fx-text-fill: red");
						}

					}catch(InvalidProfileException e){
						height =0;
						NutriByte.view.heightTextField.setStyle("-fx-text-fill: red");

					}catch(NumberFormatException m){
						NutriByte.view.heightTextField.setStyle("-fx-text-fill: red");

					}
					if(NutriByte.view.genderComboBox.getValue()!=null && !NutriByte.view.weightTextField.getText().isEmpty() && !NutriByte.view.ageTextField.getText().isEmpty()){
						if(NutriByte.person != null){
							if(height>0){
								NutriByte.person.height= height;
								success = true;
							}

						}else{
							success = validation();
						}
						if(success){
							NutriProfiler.createNutriProfile(NutriByte.person);
							NutriByte.person.populateDietNutrientsMap();
							NutriByte.view.nutriChart.updateChart();
							NutriByte.view.recommendedNutrientsTableView.setItems(NutriByte.person.recommendedNutrientsList);
							success = false;
						}
					}				
				}

			});
		}
	}
	
	class SaveMenuItemHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			//write your code here
			boolean success = false;
			Stage stage = new Stage();
			CSVFiler csvfiler = new CSVFiler();
			File file = null;
			FileChooser savefile = new FileChooser();
			//preserve the dietproducts list using the member variable
			if(NutriByte.person != null){
				localList = NutriByte.person.dietProductsList;
				NutriByte.person = null;
			}
			NutriByte.view.createProfileButton.fire();
			if(NutriByte.person!= null){
				success = true;
				NutriByte.person.dietProductsList = localList;
			}

			// Set extension filter
			if(success){
				FileChooser.ExtensionFilter extFilter = 
						new FileChooser.ExtensionFilter("CSV files", "*.csv");
				savefile.getExtensionFilters().add(extFilter);
				file = savefile.showSaveDialog(stage);

				if (file != null && file.getName().endsWith(".csv")){
					try {


						csvfiler.writeFile(file.getAbsolutePath());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}
	}

	class SearchButtonHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			//write your code here
			NutriByte.model.searchResultsList.clear();
			ObservableList<Product> prodsFound = FXCollections.observableArrayList();

			String productSearch = NutriByte.view.productSearchTextField.getText().toString();
			String nutrientSearch = NutriByte.view.nutrientSearchTextField.getText().toString();
			String ingredientSearch = NutriByte.view.ingredientSearchTextField.getText().toString();
			boolean boolProd = false;
			boolean boolIng = false;
			boolean boolNut = false;

			//if all three comboboxes are empty
			if(productSearch.isEmpty() && nutrientSearch.isEmpty() && ingredientSearch.isEmpty()){
				for(Entry<String, Product> p : Model.productsMap.entrySet()){
					NutriByte.model.searchResultsList.add(p.getValue());
				}
				//if at least one of the comboboxes is not empty
			}else{
				for(Entry<String, Product> p : Model.productsMap.entrySet()){

					Product current = p.getValue();
					Map<String, Product.ProductNutrient> currentNut = current.getProductNutrients();

					if(productSearch.length()>0){
						if(current.getProductName().toLowerCase().contains(productSearch.toLowerCase())){
							boolProd = true;

						}
					}else{
						boolProd = true;
					}

					if(ingredientSearch.length()>0){
						if(current.getIngredients().toLowerCase().contains(ingredientSearch.toLowerCase())){
							boolIng = true;
						}
					}else{
						boolIng = true;
					}

					if(nutrientSearch.length()>0){
						String code = null;
						for(Entry<String, Nutrient> n : Model.nutrientsMap.entrySet()){
							if(n.getValue().getNutrientName().toLowerCase().contains(nutrientSearch.toLowerCase())){
								code = n.getKey();
							}
						}
						if(code != null){
							if(currentNut.containsKey(code)){
								boolNut = true;
							}
						}
					}else{
						boolNut = true;

					}
					//when all three search boxes are checked, the product will be added to searchresults list
					if(boolProd && boolIng && boolNut){
						NutriByte.model.searchResultsList.add(current);						
					}
					boolProd = false;
					boolIng = false;
					boolNut = false;
				}
			}
			if(NutriByte.model.searchResultsList.size()>0){
				Product selected = NutriByte.model.searchResultsList.get(0);
				NutriByte.view.searchResultSizeLabel.setText(NutriByte.model.searchResultsList.size() + " product(s) found");
				NutriByte.view.productsComboBox.setItems(NutriByte.model.searchResultsList);
				NutriByte.view.productsComboBox.setPromptText(selected.getProductName());
				NutriByte.view.productsComboBox.getSelectionModel().select(0);
				NutriByte.view.productIngredientsTextArea.setText("Product ingredients: " + selected.getIngredients());
				NutriByte.view.servingSizeLabel.setText(String.valueOf(selected.getServingSize())+ " " + selected.getServingUom());
				NutriByte.view.dietServingUomLabel.setText(selected.getServingUom());
				NutriByte.view.householdSizeLabel.setText(String.valueOf(selected.getHouseholdSize())+ " " + selected.getHouseholdUom());
				NutriByte.view.dietHouseholdUomLabel.setText(selected.getHouseholdUom());

				ObservableList<Product.ProductNutrient> prodNuts = FXCollections.observableArrayList();
				Product selectedp = NutriByte.view.productsComboBox.getSelectionModel().getSelectedItem();
				for(Entry<String, ProductNutrient> pn : selectedp.getProductNutrients().entrySet()){
					prodNuts.add(pn.getValue());

				}
				NutriByte.view.productNutrientsTableView.setItems(prodNuts);
			}else{
				NutriByte.view.productIngredientsTextArea.setText("");
				NutriByte.view.servingSizeLabel.setText("");
				NutriByte.view.dietServingUomLabel.setText("");
				NutriByte.view.productsComboBox.getSelectionModel().clearSelection();
				NutriByte.view.householdSizeLabel.setText("");
				NutriByte.view.dietHouseholdUomLabel.setText("");
				NutriByte.view.productsComboBox.setPromptText("");
				NutriByte.view.productNutrientsTableView.getItems().clear();
				NutriByte.view.searchResultSizeLabel.setText(NutriByte.model.searchResultsList.size() + " product(s) found");

			}
			//listener to continuously update the ingredients text box and nutrient tableview
			NutriByte.view.productsComboBox.valueProperty().addListener((observable, oldValue, newValue)->{
				if(NutriByte.view.productsComboBox.getSelectionModel().getSelectedIndex()>-1){
					ObservableList<Product.ProductNutrient> prodNuts2 = FXCollections.observableArrayList();
					Product selectedp2 = NutriByte.view.productsComboBox.getSelectionModel().getSelectedItem();
					for(Entry<String, ProductNutrient> pn2 : selectedp2.getProductNutrients().entrySet()){
						prodNuts2.add(pn2.getValue());

					}
					NutriByte.view.productNutrientsTableView.setItems(prodNuts2);
				}

			});
			//listener to change the labels
			NutriByte.view.productsComboBox.valueProperty().addListener((observable,oldValue,newValue) ->{
				if(NutriByte.view.productsComboBox.getSelectionModel().getSelectedIndex()>-1 ){
					NutriByte.view.productIngredientsTextArea.setText("Product ingredients: " + newValue.getIngredients());
					NutriByte.view.servingSizeLabel.setText(String.valueOf(newValue.getServingSize()) + " " + newValue.getServingUom());
					NutriByte.view.dietServingUomLabel.setText(newValue.getServingUom());
					NutriByte.view.householdSizeLabel.setText(String.valueOf(newValue.getHouseholdSize()) + " " + newValue.getHouseholdUom());
					NutriByte.view.dietHouseholdUomLabel.setText(newValue.getHouseholdUom());

				}
			});


		}
	}
	class ClearButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			//write your code here
			NutriByte.view.productSearchTextField.clear();
			NutriByte.view.nutrientSearchTextField.clear();
			NutriByte.view.productIngredientsTextArea.setText("");
			NutriByte.view.productNutrientsTableView.getItems().clear();

			NutriByte.view.productsComboBox.getItems().clear();;
			NutriByte.view.productsComboBox.setPromptText("Select Product");

			NutriByte.view.servingSizeLabel.setText("");
			NutriByte.view.householdSizeLabel.setText("");
			NutriByte.view.ingredientSearchTextField.setText("");
			NutriByte.view.searchResultSizeLabel.setText("");
			NutriByte.view.dietServingUomLabel.setText("");
			NutriByte.view.dietHouseholdUomLabel.setText("");


		}
	}
	class AddDietButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {

			//write your code here
			Product selectedp = null;
			Product dietproduct = new Product();
			float householdsize = 0;
			float servingsize =0;
			float ratio= 0;
			//String ndbNumber, String productName, String manufacturer, String ingredients
			try{
				selectedp = NutriByte.view.productsComboBox.getSelectionModel().getSelectedItem();

			}catch(Exception e){
				selectedp = NutriByte.model.searchResultsList.get(0);
			}
			//accounts for 4 scenarios that the dietservingsize and diethouseholdsize have
			if(selectedp != null){

				String dietServingSize = NutriByte.view.dietServingSizeTextField.getText();
				String dietHouseHoldSize = NutriByte.view.dietHouseholdSizeTextField.getText();

				if(dietServingSize.isEmpty() && dietHouseHoldSize.isEmpty()){
					servingsize = selectedp.getServingSize();
					householdsize = selectedp.getHouseholdSize(); 

				}else if (!dietServingSize.isEmpty() && dietHouseHoldSize.isEmpty()){
					servingsize = Float.valueOf(dietServingSize);
					ratio = servingsize / selectedp.getServingSize();
					householdsize = selectedp.getHouseholdSize() * ratio;

				}else if(dietServingSize.isEmpty() && !dietHouseHoldSize.isEmpty()){
					if(selectedp.getHouseholdSize()==0){
						selectedp.setHouseholdSize(1);
					}
					ratio = Float.valueOf(dietHouseHoldSize)/selectedp.getHouseholdSize();
					servingsize = selectedp.getServingSize()*ratio;

					householdsize = selectedp.getHouseholdSize() * Float.valueOf(dietHouseHoldSize);

				}else{
					servingsize = Float.valueOf(dietServingSize);
					ratio = servingsize / selectedp.getServingSize();
					householdsize = selectedp.getHouseholdSize() * ratio;
				}

				dietproduct.setNdbNumber(selectedp.getNdbNumber());
				dietproduct.setProductName(selectedp.getProductName());
				dietproduct.setHouseholdSize(householdsize);
				dietproduct.setServingSize(servingsize);
				dietproduct.setHouseholdUom(selectedp.getHouseholdUom());
				dietproduct.setServingUoM(selectedp.getServingUom());
				dietproduct.setIngredients(selectedp.getIngredients());
				dietproduct.setManufacturer(selectedp.getManufacturer());
				dietproduct.setProductNutrients(selectedp.getProductNutrients());

				localList.add(dietproduct);

				if(NutriByte.person != null){
					NutriByte.person.dietProductsList = localList;
					NutriByte.person.populateDietNutrientsMap();
					NutriByte.view.dietProductsTableView.setItems(NutriByte.person.dietProductsList);

				}else{
					NutriByte.view.dietProductsTableView.setItems(localList);

				}

			}

		}
	}

	class RemoveDietButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			//write your code here
			int index = NutriByte.view.dietProductsTableView.getSelectionModel().getFocusedIndex();
			if(NutriByte.person == null){
				if(localList.size() > index){
					localList.remove(index);
				}
				if(localList.size()==0){
					NutriByte.view.dietProductsTableView.getSelectionModel().clearSelection();

				}

			}
			else if(NutriByte.person.dietProductsList.size() > index){
				NutriByte.person.dietProductsList.remove(index);
				if(NutriByte.person.dietProductsList.size() ==0){
					NutriByte.view.dietProductsTableView.getSelectionModel().clearSelection();
				}
				NutriByte.person.populateDietNutrientsMap();
			}

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
	class CloseButtonHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent arg0) {
			// TODO Auto-generated method stub
			NutriByte.view.root.setCenter(NutriByte.view.setupWelcomeScene());
			NutriByte.view.clearButton.fire();

		}

	}

}


