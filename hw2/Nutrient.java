//Michell Li
//MLi5

package hw2;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Nutrient {
	private StringProperty nutrientCode  = new SimpleStringProperty();
	private StringProperty nutrientName  = new SimpleStringProperty();
	private StringProperty nutrientUom  = new SimpleStringProperty();
	
	//constructor
	public Nutrient(){
		this.nutrientCode.set("");
		this.nutrientName.set("");
		this.nutrientUom.set("");
	}
	public Nutrient(String nutrientCode, String nutrientName, String nutrientUom) {
		super();
		this.nutrientCode.set(nutrientCode); 
		this.nutrientName.set(nutrientName);
		this.nutrientUom.set(nutrientUom);
	}

	//Getters, setters
	public final StringProperty nutrientCodeProperty(){return nutrientCode;}
	public final StringProperty nutrientNameProperty(){return nutrientName;}
	public final StringProperty nutrientUomProperty(){return nutrientUom;}

    public final void setNutrientCode(String value){nutrientCode.set(value);}
    public final void setNutrientName(String value){nutrientName.set(value);}
    public final void setNutrientUom(String value){nutrientUom.set(value);}

	public String getNutrientCode(){return nutrientCode.get();}
	public String getNutrientName(){return nutrientName.get();}
	public String getNutrientUom(){return nutrientUom.get();}

	

}
