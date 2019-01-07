//Michell Li
//MLi5


package hw3;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RecommendedNutrient {
	private StringProperty nutrientCode = new SimpleStringProperty();
	private FloatProperty nutrientQuantity = new SimpleFloatProperty();
	
	//Constructors
	RecommendedNutrient(){
		nutrientCode.setValue("");
	}
	RecommendedNutrient(String nutrientCode, Float nutrientQuantity){
		this.nutrientCode.setValue(nutrientCode);
		this.nutrientQuantity.set(nutrientQuantity);
	}
	
	//Getters and Setters
	public String getNutrientCode(){return this.nutrientCode.get();}
	public void setNutrientCode(StringProperty nutrientCode){this.nutrientCode = nutrientCode;}
	
	public float getNutrientQuantity(){return this.nutrientQuantity.get();}
	public void setNutrientQuantity(FloatProperty nutrientQuantity){this.nutrientQuantity = nutrientQuantity;}

	public final StringProperty nutrientCodeProperty(){return nutrientCode;}
	public final FloatProperty nutrientQuantityProperty(){return nutrientQuantity;}


}
