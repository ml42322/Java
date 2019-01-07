//Michell Li
//MLi5

package hw3;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public class Product {

	private StringProperty ndbNumber = new SimpleStringProperty();
	private StringProperty productName = new SimpleStringProperty();
	private StringProperty manufacturer = new SimpleStringProperty();
	private StringProperty ingredients = new SimpleStringProperty();
	private FloatProperty servingSize = new SimpleFloatProperty();
	private StringProperty servingUom = new SimpleStringProperty();
	private FloatProperty householdSize = new SimpleFloatProperty();
	private StringProperty householdUom = new SimpleStringProperty();
	private ObservableMap<String, ProductNutrient> productNutrients = FXCollections.observableHashMap();
	//constructor
	public Product(){
		ndbNumber.set("");
		productName.set("");
		manufacturer.set("");
		ingredients.set("");
		servingUom.set("");
		householdUom.set("");
		
	}
	public Product(String ndbNumber, String productName, String manufacturer, String ingredients) {
		super();
		this.ndbNumber.set(ndbNumber); 
		this.productName.set(productName);
		this.manufacturer.set(manufacturer);
		this.ingredients.set(ingredients);
	}
	
	public class ProductNutrient{	
		private StringProperty nutrientCode= new SimpleStringProperty();
		private FloatProperty nutrientQuantity= new SimpleFloatProperty();
		
		//constructor....omit super call???
		public ProductNutrient(){
			this.nutrientCode.set("");;
		}
		public ProductNutrient(String nutrientCode, Float quantity) {
			super();
			this.nutrientCode.set(nutrientCode);
			this.nutrientQuantity.set(quantity);
			
		}
		public final StringProperty nutrientCodeProperty(){return nutrientCode;}
		public final FloatProperty quantityProperty(){return nutrientQuantity;}
		
		public final void setNutrientCode(String value){nutrientCode.set(value);}
		public final void setQuantity(float value){nutrientQuantity.set(value);}
		
		public String getNutrientCode(){return nutrientCode.get();}
		public float getQuantity(){return nutrientQuantity.get();}
		    
		
	}
	//Getters and setters
	public final StringProperty productNameProperty(){return productName;}
	public final FloatProperty servingSizeProperty(){return servingSize;}
	public final FloatProperty householdSizeProperty(){return householdSize;}
	public final StringProperty servingUomProperty(){return servingUom;}
	public final StringProperty householdUomProperty(){return householdUom;}
	public final StringProperty ndbNumberProperty(){return ndbNumber;}
	public final StringProperty manufacturerProperty(){return manufacturer;}
	public final StringProperty ingredientsProperty(){return ingredients;}


    public final void setProductName(String value){productName.set(value);}
    public final void setServingSize(float value){servingSize.set(value);}
    public final void setHouseholdSize(float value){householdSize.set(value);}
    public final void setServingUoM(String value){servingUom.set(value);}
    public final void setHouseholdUom(String value){householdUom.set(value);}
    public final void setIngredients(String value){ingredients.set(value);}
    public final void setNdbNumber(String value){ndbNumber.set(value);}
    public final void setManufacturer(String value){manufacturer.set(value);}

    
	public String getProductName(){return productName.get();}
	public String getNdbNumber(){return ndbNumber.get();}
	public String getManufacturer(){return manufacturer.get();}
	public String getIngredients(){return ingredients.get();}
	public float getServingSize(){return servingSize.get();}
	public String getServingUom(){return servingUom.get();}
	public float getHouseholdSize(){return householdSize.get();}
	public String getHouseholdUom(){return householdUom.get();}
	
	public ObservableMap<String, ProductNutrient> getProductNutrients() {
		return productNutrients;
	}
	public void setProductNutrients(ObservableMap<String, ProductNutrient> productNutrients) {
		this.productNutrients = productNutrients;
	}
	


}

