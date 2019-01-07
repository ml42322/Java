//Michell Li
//MLi5
package hw1;

import java.util.Scanner;
public class NutriByte {
	Model model = new Model();  				//will handle all data read from the data files
	Scanner input = new Scanner(System.in);  	//to be used for all console i/o in this class

	static final String PRODUCT_FILE = "data/Products.csv";
	static final String NUTRIENT_FILE = "data/Nutrients.csv";
	static final String SERVING_SIZE_FILE = "data/ServingSize.csv";

	//do not change this method
	public static void main(String[] args) {
		NutriByte nutriByte = new NutriByte();
		nutriByte.model.readProducts(PRODUCT_FILE);
		nutriByte.model.readNutrients(NUTRIENT_FILE);
		nutriByte.model.readServingSizes(SERVING_SIZE_FILE );
		switch (nutriByte.getMenuChoice()) {
		case 1: {
			nutriByte.printSearchResults(nutriByte.searchProductsWithSelectedIngredients(nutriByte.getIngredientChoice()));
			break;
		}
		case 2: {
			int nutrientChoice = nutriByte.getNutrientChoice();
			nutriByte.printSearchResults(nutriByte.searchProductsWithSelectedNutrient(nutrientChoice), nutrientChoice);
			break;
		}
		case 3:  
		default: System.out.println("Good Bye!"); break;
		}
	}

	//do not change this method
	int getMenuChoice() {
		System.out.println("*** Welcome to NutriByte ***");
		System.out.println("--------------------------------------------------");
		System.out.println("1. Find ingredient(s)");
		System.out.println("2. Find a nutrient");
		System.out.println("3. Exit");
		input = new Scanner(System.in);
		return input.nextInt();
	}

	//do not change this method
	String getIngredientChoice() {
		input.nextLine();
		System.out.println("Type the keywords to search for the ingredients");
		System.out.println("--------------------------------------------------");
		return input.nextLine();
	}

	int getNutrientChoice() {
		//print nutrients in columns
		System.out.println("Select the nutrient you are looking for");
		System.out.println("--------------------------------------------------");
		for(int i=0; i<model.nutrients.length; i++){
			if(i%3==0 && i!=0){
				System.out.print("\n");
			}
			System.out.printf("%-40s", (i+1) + ". " + model.nutrients[i].nutrientName);
			//System.out.println((i+1) + ". " + model.nutrients[i].nutrientName + "\t" + (i+2) + ". " + model.nutrients[i+2].nutrientName + "\t" + (i+3) + ". " + model.nutrients[i+3].nutrientName);

		}
		System.out.println();
		System.out.println("--------------------------------------------------");

		//look for selected nutrient
		input.nextLine();
		String lookforNutrient = input.nextLine();
		int x = Integer.valueOf(lookforNutrient);
		return x;
	}


	Product[] searchProductsWithSelectedIngredients(String searchString) {
		//include null cases and exceptions
		Product[] foundProds = null;
		StringBuilder foundProducts = new StringBuilder();


		if(searchString == ""){
			Product[] empty = new Product[0];
			//System.out.println("Here");
			return empty;
		}else{

			String[] userInput = searchString.toLowerCase().split(" ");
			int counter = 0;
			//append all products including the ingredient
			for(int i=0; i<userInput.length; i++){
				for(int j=0; j<model.products.length; j++){
					if(model.products[j].ingredients.toLowerCase().contains(userInput[i])){
						counter ++;
						foundProducts.append(model.products[j].ndbNumber + "="  + model.products[j].productName +"\n");
						//System.out.println(model.products[j].productName + " from " + model.products[j].manufacturer);
					}
				}

			}
			
			//append all initial findings to a multidimensional array
			//parse through and only append unique products to a new stringbuilder
			if(foundProducts.length()==0){
				Product[] empty = new Product[0];

				return empty;
			}
			String[][] dupProducts = new String[counter][]; 
			String[] temp = foundProducts.toString().split("\n");
			//System.out.println(temp.length);
			StringBuilder uniqueProds = new StringBuilder();
			for(int k=0; k<temp.length; k++){
				dupProducts[k] = temp[k].split("=");
			}
			
			
			boolean exists = false;
			for(int p=0; p<dupProducts.length; p++){
				for(int j=0; j<p; j++){
					if(dupProducts[j][1].equals(dupProducts[p][1])){
						//System.out.println(dupProducts[j][1] + " " + dupProducts[p][1]);
						exists = true;
						break;
					}
				}
				if(exists == false){
					uniqueProds.append(dupProducts[p][0] + "\n");
					//System.out.println(dupProducts[p][0] +  " " + dupProducts[p][1]);
				}else{
					exists = false;
				}
			}
			
			
			String[] uniqueNums = uniqueProds.toString().split("\n");
			foundProds = new Product[uniqueNums.length];
			for(int q=0; q<foundProds.length; q++){
				for(int y=0; y<model.products.length; y++){
					if(model.products[y].ndbNumber.equals(uniqueNums[q])){
						foundProds[q] = model.products[y];
						break;
					}
				}
			}
		
					

		}
		//get rid of case sensitivity
		return foundProds;
	}


	Product[] searchProductsWithSelectedNutrient(int menuChoice) {
		//selected nutrient
		String nutrientSelected = model.nutrients[menuChoice-1].nutrientName.toLowerCase();
		StringBuilder foundProducts = new StringBuilder();
		for(int i=0; i< model.productNutrients.length; i++){;
			if(model.productNutrients[i].nutrientName.toLowerCase().equals(nutrientSelected)){
				if(model.productNutrients[i].quantity != 0)
					foundProducts.append(model.productNutrients[i].ndbNumber + "\n");
				//System.out.println(model.productNutrients[i].ndbNumber +  " " +model.productNutrients[i].quantity);
			}
		}


		//Find the associated product by ndbNumber
		String[] ndbNumbersFound = foundProducts.toString().split("\n");
		Product[] arrayFound = new Product[ndbNumbersFound.length];

		for(int j=0; j<ndbNumbersFound.length; j++){
			for(int m=0; m<model.products.length; m++){
				if(model.products[m].ndbNumber.equals(ndbNumbersFound[j])){
					arrayFound[j] = model.products[m];
					//System.out.println(model.products[m].ndbNumber + " " + model.products[m].ingredients);

				}
			}
		}
		return arrayFound;
	}

	void printSearchResults(Product[] searchResults) {
		//method for ingredient search
		System.out.println("*** " + searchResults.length + " products found ***");
		for(int i=0; i<searchResults.length; i++){
			System.out.println((i+1) + ". " + searchResults[i].productName + " from " + searchResults[i].manufacturer);
		}
	}

	void printSearchResults(Product[] searchResults, int nutrientChoice) {

		//method for nutrient search
		Nutrient nutrient = model.nutrients[nutrientChoice-1];
		System.out.println("*** " + searchResults.length + " products found ***");
		float temp =0;
		float[] nutrientServing = new float[searchResults.length];
		//serving measurements
		for(int q=0; q<searchResults.length; q++){
			for(int a =0; a<model.productNutrients.length; a++){
				if(searchResults[q].ndbNumber.equals(model.productNutrients[a].ndbNumber)
						&& nutrient.nutrientName.toLowerCase().equals(model.productNutrients[a].nutrientName.toLowerCase())){
					nutrientServing[q] = model.productNutrients[a].quantity;
					//System.out.println(model.productNutrients[a].quantity);
				}

			}
		}

		for(int i=0; i<searchResults.length; i++){
			temp = (searchResults[i].servingSize * nutrientServing[i]) /100;
			//prints household size and uom i.e. ".5 cups"
			System.out.print((i+1)+ ". " + searchResults[i].householdSize + " "+ searchResults[i].householdUom);

			//prints out product ndb number and name
			System.out.print(" of "+ searchResults[i].ndbNumber + ": " + searchResults[i].productName);

			System.out.println(" has " + temp + " " + searchResults[i].servingUom + " of " + nutrient.nutrientName);
			temp = 0;
		}

	}
}
