//Michell Li
//MLi5

package hw3;

public class Male extends Person{
	float[][] nutriConstantsTableMale = new float[][]{
		//AgeGroups: 3M, 6M, 1Y, 3Y, 8Y, 13Y, 18Y, 30Y, 50Y, ABOVE 
		{1.52f, 1.52f, 1.2f, 1.05f, 0.95f, 0.95f, 0.73f, 0.8f, 0.8f, 0.8f}, //Protein
		{60, 60, 95, 130, 130, 130, 130, 130, 130, 130}, //Carbohydrate
		{19, 19, 19, 19, 25, 31, 38, 38, 38, 30},       //Fiber 
		{36, 36, 32, 21, 16, 17, 15, 14, 14, 14	},  //Histidine
		{88, 88, 43, 28, 22, 22, 21, 19, 19, 19	}, 	//isoleucine
		{156, 156, 93, 63, 49, 49, 47, 42, 42, 42},//leucine
		{107, 107, 89, 58, 46, 46, 43, 38, 38, 38 },//lysine
		{59, 59, 43, 28, 22, 22, 21, 19, 19, 19	}, 	//methionine 
		{59, 59, 43, 28, 22, 22, 21, 19, 19, 19	}, 	//cysteine
		{135, 135, 84, 54, 41, 41, 38, 33, 33, 33 },//phenylalanine 
		{135, 135, 84, 54, 41, 41, 38, 33, 33, 33 },//tyrosine
		{73, 73, 49, 32, 24, 24, 22, 20, 20, 20}, 	//threonine
		{28, 28, 13, 8, 6, 6, 6, 5, 5, 5}, 			//tryptophan
		{87, 87, 58, 37, 28, 28, 27, 24, 24, 24}  	//valine
	};

	Male(float age, float weight, float height, float physicalActivityLevel, String ingredientsToAvoid) {
		super(age, weight, height, physicalActivityLevel, ingredientsToAvoid);
		//sets the person's age group based on their age
		for(NutriProfiler.AgeGroupEnum s: ageGroup.values()){
			if(this.age<=s.getAge()){
				ageGroup = s;				
				break;
			}
		}
		//method to populate the person's nutriconstants table to male version
		initializeNutriConstantsTable();
	}

	//calculates based on age, height, weight, and physical activity
	@Override
	float calculateEnergyRequirement() {
		//write your code here
		float val = 0;
		int ageIndex = 0;
		int X =0;
		NutriProfiler.PhysicalActivityEnum physActivity = null;

		for(NutriProfiler.AgeGroupEnum s: ageGroup.values()){
			if(this.age<=s.getAge()){
				ageIndex = s.getAgeGroupIndex();
				break;
			}else if(this.age>=150){
				System.out.println("Here");
				ageIndex = s.getAgeGroupIndex();

				this.ageGroup = s;
				break;
			}
		}
		for(NutriProfiler.PhysicalActivityEnum p : physActivity.values()){
			if(p.getPhysicalActivityLevel() == this.physicalActivityLevel){
				physActivity = p;
			}
		}
		switch(ageIndex){
		case 0: X=-75; val=(89 * this.weight) - X; break;
		case 1: X = 44; val=(89 * this.weight) - X; break;
		case 2: X = 78; val=(89 * this.weight) - X; break;
		case 3: X = 80; val=(89 * this.weight) - X; break;
		case 4: 
		case 5:
		case 6: {
			float A = 88.5f;
			float B = 61.9f;
			float C = 26.7f;
			float D = 903f;
			val = A - (B*this.age)+physActivity.getPhysicalActivityLevel() * (C*this.weight + (D*height/100)) + 20;
			break;
		} 
		case 7:
		case 8:
		case 9:{
			float A = 662f;
			float B = 9.53f;
			float C = 15.91f;
			float D = 539.6f;
			val = A - (B*this.age)+physActivity.getPhysicalActivityLevel() * (C*this.weight + (D*height/100));
			break;

		}
		default: return 0;
		}
		return val;
	}

	@Override
	void initializeNutriConstantsTable() {
		//write your code here
		this.nutriConstantsTable = nutriConstantsTableMale;
	}
}
