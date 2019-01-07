//Michell Li
//MLi5
package hw3;

public class Female extends Person {

	float[][] nutriConstantsTableFemale = new float[][]{
		//AgeGroups: 3M, 6M, 1Y, 3Y, 8Y, 13Y, 18Y, 30Y, 50Y, ABOVE 
		{1.52f, 1.52f, 1.2f, 1.05f, 0.95f, 0.95f, 0.71f, 0.8f, 0.8f, 0.8f}, //0: Protein constants
		{60, 60, 95, 130, 130, 130, 130, 130, 130, 130}, //1: Carbohydrate
		{19, 19, 19, 19, 25, 26, 26, 25, 25, 21},  //2: Fiber constants
		{36, 36, 32, 21, 16, 15, 14, 14, 14, 14}, 	//3: Histidine
		{88, 88, 43, 28, 22, 21, 19, 19, 19, 19}, 	//4: isoleucine
		{156, 156, 93, 63, 49, 47, 44 , 42, 42, 42},//5: leucine
		{107, 107, 89, 58, 46, 43, 40, 38, 38, 38}, //6: lysine
		{59, 59, 43, 28, 22, 21, 19, 19, 19, 19}, 	//7: methionine
		{59, 59, 43, 28, 22, 21, 19, 19, 19, 19}, 	//8: cysteine
		{135, 135, 84, 54, 41, 38, 35, 33, 33, 33}, //9: phenylalanine
		{135, 135, 84, 54, 41, 38, 35, 33, 33, 33}, //10: phenylalanine
		{73, 73, 49, 32, 24, 22, 21, 20, 20, 20}, 	//11: threonine
		{28, 28, 13, 8, 6, 6, 5, 5, 5, 5}, 			//12: tryptophan
		{87, 87, 58, 37, 28, 27, 24, 24, 24, 24	}  	//13: valine
	};

	Female(float age, float weight, float height, float physicalActivityLevel, String ingredientsToAvoid) {
		//write your code here
		super(age, weight, height, physicalActivityLevel, ingredientsToAvoid);
		//set correct agegroup for female
		for(NutriProfiler.AgeGroupEnum s: ageGroup.values()){
			if(this.age<=s.getAge()){
				ageGroup = s;				
				break;
			}
		}
		//method to set the person's table to female constants table
		initializeNutriConstantsTable();
	}

	@Override
	//calculates based on person's age group, weight, height, and physical activity level
	float calculateEnergyRequirement() {
		//write your code here
		float val = 0;
		int ageIndex = 0;
		int X =0;
		NutriProfiler.PhysicalActivityEnum physActivity = null;
		
		for(NutriProfiler.AgeGroupEnum s: this.ageGroup.values()){
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
			float A = 135.3f;
			float B = 30.8f;
			float C = 10f;
			float D = 934f;
			val = A - (B*this.age)+physActivity.getPhysicalActivityLevel() * (C*this.weight + (D*height/100)) + 20;
			break;
		} 
		case 7:
		case 8:
		case 9:{
			float A = 354f;
			float B = 6.91f;
			float C = 9.36f;
			float D = 726f;
			val = A - (B*this.age)+physActivity.getPhysicalActivityLevel() * (C*this.weight + (D*height/100));
			break;

		}
		default: return 0;
		}
		//returns the person's energy level
		return val;

		

	}

	@Override
	void initializeNutriConstantsTable() {
		nutriConstantsTable = nutriConstantsTableFemale;
	}
}
