//Michell Li
//MLi5

package hw2;


public abstract class Person {

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
	
}
