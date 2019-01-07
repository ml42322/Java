//Michell Li
//MLi5

package hw3;

import java.util.Map.Entry;

import hw3.Product.ProductNutrient;
import javafx.beans.Observable;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public abstract class Person {
	ObservableList<RecommendedNutrient> recommendedNutrientsList = FXCollections.observableArrayList();  
	ObservableList<Product> dietProductsList = FXCollections.observableArrayList();
	ObservableMap<String, RecommendedNutrient> dietNutrientsMap = FXCollections.observableHashMap();

	float age, weight, height, physicalActivityLevel; //age in years, weight in kg, height in cm
	String ingredientsToWatch;
	float[][] nutriConstantsTable = new float[NutriProfiler.RECOMMENDED_NUTRI_COUNT][NutriProfiler.AGE_GROUP_COUNT];

	NutriProfiler.AgeGroupEnum ageGroup;

	abstract void initializeNutriConstantsTable();
	abstract float calculateEnergyRequirement();

	//remove this default constructor once you have defined the child's constructor
	Person(float age, float weight, float height, float physicalActivityLevel, String ingredientsToWatch) {
		//write your code here
		this.age = age;
		this.weight = weight;
		this.height = height;
		this.physicalActivityLevel = physicalActivityLevel;
		this.ingredientsToWatch = ingredientsToWatch;

	}

	//returns an array of nutrient values of size NutriProfiler.RECOMMENDED_NUTRI_COUNT. 
	//Each value is calculated as follows:
	//For Protein, it multiples the constant with the person's weight.
	//For Carb and Fiber, it simply takes the constant from the 
	//nutriConstantsTable based on NutriEnums' nutriIndex and the person's ageGroup
	//For others, it multiples the constant with the person's weight and divides by 1000.
	//Try not to use any literals or hard-coded values for age group, nutrient name, array-index, etc. 

	//calculates the nutrient requirements 
	float[] calculateNutriRequirement() {
		//write your code here
		int ageIndex = 0;
		float[] nutVals = new float[NutriProfiler.RECOMMENDED_NUTRI_COUNT];
		for(NutriProfiler.AgeGroupEnum s: ageGroup.values()){
			if(this.age<=s.getAge()){
				ageIndex = s.getAgeGroupIndex();

				break;

			}

		}

		//runs through all the nutrients and multiplies by relevant weight factor

		for(int i=0; i<nutriConstantsTable.length; i++){
			if(i==0){
				nutVals[i] = nutriConstantsTable[i][ageIndex] * this.weight;
			}
			else if(i==1 || i == 2){
				nutVals[i] = nutriConstantsTable[i][ageIndex];
			}else{
				nutVals[i] = nutriConstantsTable[i][ageIndex] * this.weight/1000;		
			}
			//System.out.println(nutVals[i]);
		}
		return nutVals;
	}

	public void populateDietNutrientsMap(){
		//take data from dietProductList populated in CSVFiler
		//or AddDietButtonHandler and populates dietNutrientsMap
		NutriByte.person.dietNutrientsMap.clear();
		if(NutriByte.person.dietNutrientsMap.size()==0){
			NutriByte.person.dietNutrientsMap.put(NutriProfiler.ENERGY_NUTRIENT_CODE, new RecommendedNutrient(NutriProfiler.ENERGY_NUTRIENT_CODE, 0f));				
			NutriByte.person.dietNutrientsMap.put(NutriProfiler.NutriEnum.PROTEIN.getNutrientCode(), new RecommendedNutrient(NutriProfiler.NutriEnum.PROTEIN.getNutrientCode(), 0f));				
			NutriByte.person.dietNutrientsMap.put(NutriProfiler.NutriEnum.CARBOHYDRATE.getNutrientCode(), new RecommendedNutrient(NutriProfiler.NutriEnum.CARBOHYDRATE.getNutrientCode(), 0f));				
			NutriByte.person.dietNutrientsMap.put(NutriProfiler.NutriEnum.FIBER.getNutrientCode(), new RecommendedNutrient(NutriProfiler.NutriEnum.FIBER.getNutrientCode(), 0f));				
		}

		//populate nutrient codes and calculated quantities for the nutrichart
		for(Product p : NutriByte.person.dietProductsList){
			for(Entry<String, ProductNutrient> pn : p.getProductNutrients().entrySet()){
				if(NutriByte.person.dietNutrientsMap.containsKey(pn.getKey())){
					float calculation = pn.getValue().getQuantity() * p.getServingSize()/100;
					float total = NutriByte.person.dietNutrientsMap.get(pn.getKey()).getNutrientQuantity() + calculation;
					FloatProperty newtotal = new SimpleFloatProperty(total);
					NutriByte.person.dietNutrientsMap.get(pn.getKey()).setNutrientQuantity(newtotal);

				}
			}
		}
		NutriByte.view.nutriChart.updateChart();

	}
	

}
