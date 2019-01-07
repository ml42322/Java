//Michell Li
//MLi5

package hw3;

import hw3.Product.ProductNutrient;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

public class NutriByte extends Application{
	static Model model = new Model();  	//made static to make accessible in the controller
	static View view = new View();		//made static to make accessible in the controller
	static Person person;				//made static to make accessible in the controller
	
	
	Controller controller = new Controller();	//all event handlers 

	/**Uncomment the following three lines if you want to try out the full-size data files */
	//static final String PRODUCT_FILE = "data/Products.csv";
	//static final String NUTRIENT_FILE = "data/Nutrients.csv";
	//static final String SERVING_SIZE_FILE = "data/ServingSize.csv";
	
	/**The following constants refer to the data files to be used for this application */
	static final String PRODUCT_FILE = "data/Nutri2Products.csv";
	static final String NUTRIENT_FILE = "data/Nutri2Nutrients.csv";
	static final String SERVING_SIZE_FILE = "data/Nutri2ServingSize.csv";
	
	static final String NUTRIBYTE_IMAGE_FILE = "NutriByteLogo.png"; //Refers to the file holding NutriByte logo image 

	static final String NUTRIBYTE_PROFILE_PATH = "profiles";  //folder that has profile data files

	static final int NUTRIBYTE_SCREEN_WIDTH = 1015;
	static final int NUTRIBYTE_SCREEN_HEIGHT = 675;

	@Override
	public void start(Stage stage) throws Exception {
		model.readProducts(PRODUCT_FILE);
		model.readNutrients(NUTRIENT_FILE);
		model.readServingSizes(SERVING_SIZE_FILE );
		view.setupMenus();
		view.setupNutriTrackerGrid();
		view.root.setCenter(view.setupWelcomeScene());
		Background b = new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));
		view.root.setBackground(b);
		Scene scene = new Scene (view.root, NUTRIBYTE_SCREEN_WIDTH, NUTRIBYTE_SCREEN_HEIGHT);
		view.root.requestFocus();  //this keeps focus on entire window and allows the textfield-prompt to be visible
		setupBindings();
		stage.setTitle("NutriByte 2.0");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	void setupBindings() {
		//bindings for menu items
		//bindings for diet
		view.newNutriProfileMenuItem.setOnAction(controller.new NewMenuItemHandler());
		view.openNutriProfileMenuItem.setOnAction(controller.new OpenMenuItemHandler());
		view.saveNutriProfileMenuItem.setOnAction(controller.new SaveMenuItemHandler());
		view.searchButton.setOnAction(controller.new SearchButtonHandler());
		view.clearButton.setOnAction(controller.new ClearButtonHandler());
		view.addDietButton.setOnAction(controller.new AddDietButtonHandler());
		view.removeDietButton.setOnAction(controller.new RemoveDietButtonHandler());
		view.exitNutriProfileMenuItem.setOnAction(event -> Platform.exit());
		view.aboutMenuItem.setOnAction(controller.new AboutMenuItemHandler());
		view.closeNutriProfileMenuItem.setOnAction(controller.new CloseButtonHandler());
		
		//bindings for recommendednutrient tableview
		view.recommendedNutrientNameColumn.setCellValueFactory(recommendedNutrientNameCallback);
		view.recommendedNutrientQuantityColumn.setCellValueFactory(recommendedNutrientQuantityCallback);
		view.recommendedNutrientUomColumn.setCellValueFactory(recommendedNutrientUomCallback);
		
		view.productNutrientNameColumn.setCellValueFactory(productNutrientNameCallback);
		view.productNutrientQuantityColumn.setCellValueFactory(productNutrientQuantityCallback);
		view.productNutrientUomColumn.setCellValueFactory(productNutrientUomCallback);

		
		view.createProfileButton.setOnAction(controller.new RecommendNutrientsButtonHandler());
		
		view.productsComboBox.setCellFactory(listView -> new ProductListCell());
		view.productsComboBox.setButtonCell(new ProductListCell());
		
		//diet tableview bindings
		view.dietProductNameColumn.setCellValueFactory(productNameCallback);
		view.dietServingSizeColumn.setCellValueFactory(productServingSizeCallback);
		view.dietHouseholdUomColumn.setCellValueFactory(productHouseholdUOMCallback);
		view.dietServingUomColumn.setCellValueFactory(productUOMCallback);
		view.dietHouseholdSizeColumn.setCellValueFactory(productHouseholdSizeCallback);

	}
	
	//Callbacks for each column of the tableview
	Callback<CellDataFeatures<RecommendedNutrient, String>, ObservableValue<String>> recommendedNutrientNameCallback = new Callback<CellDataFeatures<RecommendedNutrient, String>, ObservableValue<String>>() {
		@Override
		public ObservableValue<String> call(CellDataFeatures<RecommendedNutrient, String> arg0) {
			Nutrient nutrient = Model.nutrientsMap.get(arg0.getValue().getNutrientCode());

			return nutrient.nutrientNameProperty();
		}
	};
	
	Callback<CellDataFeatures<RecommendedNutrient, String>, ObservableValue<String>> recommendedNutrientQuantityCallback = new Callback<CellDataFeatures<RecommendedNutrient, String>, ObservableValue<String>>() {
		@Override
		public ObservableValue<String> call(CellDataFeatures<RecommendedNutrient, String> arg0) {
			ObservableValue<String> q = Bindings.format("%.2f", arg0.getValue().nutrientQuantityProperty());
			
			return q;
		}
	};
	
	Callback<CellDataFeatures<RecommendedNutrient, String>, ObservableValue<String>> recommendedNutrientUomCallback = new Callback<CellDataFeatures<RecommendedNutrient, String>, ObservableValue<String>>() {
		@Override
		public ObservableValue<String> call(CellDataFeatures<RecommendedNutrient, String> arg0) {
			Nutrient nutrient = Model.nutrientsMap.get(arg0.getValue().getNutrientCode());
			return nutrient.nutrientUomProperty();
		}
	};
	
	Callback<CellDataFeatures<ProductNutrient, String>, ObservableValue<String>> productNutrientNameCallback = new Callback<CellDataFeatures<ProductNutrient, String>, ObservableValue<String>>() {
		@Override
		public ObservableValue<String> call(CellDataFeatures<ProductNutrient, String> arg0) {
			Nutrient nutrient = Model.nutrientsMap.get(arg0.getValue().getNutrientCode());

			return nutrient.nutrientNameProperty();
		}
	};
	
	Callback<CellDataFeatures<ProductNutrient, String>, ObservableValue<String>> productNutrientQuantityCallback = new Callback<CellDataFeatures<ProductNutrient, String>, ObservableValue<String>>() {
		@Override
		public ObservableValue<String> call(CellDataFeatures<ProductNutrient, String> arg0) {
			ObservableValue<String> q = Bindings.format("%.2f", arg0.getValue().quantityProperty());

			return q;
		}
	};
	
	Callback<CellDataFeatures<ProductNutrient, String>, ObservableValue<String>> productNutrientUomCallback = new Callback<CellDataFeatures<ProductNutrient, String>, ObservableValue<String>>() {
		@Override
		public ObservableValue<String> call(CellDataFeatures<ProductNutrient, String> arg0) {
			Nutrient nutrient = Model.nutrientsMap.get(arg0.getValue().getNutrientCode());


			return nutrient.nutrientUomProperty();
		}
	};
	Callback<CellDataFeatures<Product, String>, ObservableValue<String>> productNameCallback = new Callback<CellDataFeatures<Product, String>, ObservableValue<String>>() {
		@Override
		public ObservableValue<String> call(CellDataFeatures<Product, String> arg0) {
			ObservableValue<String> product = arg0.getValue().productNameProperty();


			return product;
		}
	};
	Callback<CellDataFeatures<Product, Float>, ObservableValue<Float>> productServingSizeCallback = new Callback<CellDataFeatures<Product, Float>, ObservableValue<Float>>() {
		@Override
		public ObservableValue<Float> call(CellDataFeatures<Product, Float> arg0) {
			ObservableValue<Float> q = arg0.getValue().servingSizeProperty().asObject();

			return q;
		}
	};
	Callback<CellDataFeatures<Product, String>, ObservableValue<String>> productUOMCallback = new Callback<CellDataFeatures<Product, String>, ObservableValue<String>>() {
		@Override
		public ObservableValue<String> call(CellDataFeatures<Product, String> arg0) {
			ObservableValue<String> product = arg0.getValue().servingUomProperty();


			return product;
		}
	};
	Callback<CellDataFeatures<Product, Float>, ObservableValue<Float>> productHouseholdSizeCallback = new Callback<CellDataFeatures<Product, Float>, ObservableValue<Float>>() {
		@Override
		public ObservableValue<Float> call(CellDataFeatures<Product, Float> arg0) {
			ObservableValue<Float> q = arg0.getValue().householdSizeProperty().asObject();


			return q;
		}
	};
	Callback<CellDataFeatures<Product, String>, ObservableValue<String>> productHouseholdUOMCallback = new Callback<CellDataFeatures<Product, String>, ObservableValue<String>>() {
		@Override
		public ObservableValue<String> call(CellDataFeatures<Product, String> arg0) {
			ObservableValue<String> product = arg0.getValue().householdUomProperty();

			return product;
		}
	};
	
	//print out the name of product in the combobox
	private static class ProductListCell extends ListCell<Product> {

	    @Override
	    public void updateItem(Product item, boolean empty) {
	        super.updateItem(item, empty);
	        if (item != null) {

	            setText(item.getProductName() + " by " + item.getManufacturer());

	        }
	        else {
	            setText(null);
	        }
	    }

	}
	

}