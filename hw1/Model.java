package hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;

public class Model {
	Product[] products;
	Nutrient[] nutrients;
	ProductNutrient[] productNutrients;

	public void readProducts(String productFile) {
		//load products into product array
		Scanner fileInput = null;
		StringBuilder fileContent = new StringBuilder();
		try {
			fileInput = new Scanner (new File(productFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		//read file
		fileInput.nextLine();
		while(fileInput.hasNextLine()){
			fileContent.append(fileInput.nextLine() + "\n");
		}
		String[] words = fileContent.toString().split("\n"); 
		products = new Product[words.length];

		//instantiate new products and append to array
		for(int i=0; i<words.length; i++){
			String[] a = words[i].split("\",\"");
			//System.out.println(a.length);
			products[i] = new Product(a[0].replaceAll("\"", ""),a[1].replaceAll("\"", ""), a[4].replaceAll("\"", ""), a[a.length-1].replaceAll("\"", ""));
			//System.out.println(products[i].productName);
		}

	}

	public void readNutrients(String nutrientFile) {
		//load objects in the nutrients and productNutrients array 
		Scanner fileInput = null;
		StringBuilder fileContent = new StringBuilder();
		try {
			fileInput = new Scanner (new File(nutrientFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		fileInput.nextLine();
		while(fileInput.hasNextLine()){
			fileContent.append(fileInput.nextLine() + "\n");
		}

		//productNutrient array initialization
		String[] nutrientWords = fileContent.toString().split("\n"); 
		productNutrients = new ProductNutrient[nutrientWords.length];
		String[] allNutCodes = new String[nutrientWords.length];
		for(int z=0; z<nutrientWords.length; z++){
			String[] b = nutrientWords[z].split("\",\"");
			productNutrients[z] = new ProductNutrient(b[0].replaceAll("\"", ""),b[1].replaceAll("\"", ""),b[2].replaceAll("\"", ""),Float.valueOf(b[4]),b[5].replaceAll("\"", ""));
			//System.out.println(productNutrients[z].ndbNumber);

			allNutCodes[z] = b[1];

		}
		//unique code values
		int temp=1;
		int codeCounter =allNutCodes.length;
		String[] uniqueCodes = new String[allNutCodes.length];
		Arrays.sort(allNutCodes);
		uniqueCodes[0] = allNutCodes[0];
		for(int c=0; c<allNutCodes.length-1; c++){
			if(allNutCodes[c].equals(allNutCodes[c+1])){
				codeCounter --;
			}else{
				uniqueCodes[temp] = allNutCodes[c+1];
				//System.out.println(uniqueCodes[temp]);
				temp++;
			}

		}
		//System.out.println(codeCounter);

		//initialize array and populate 
		nutrients = new Nutrient[codeCounter];
		for(int d=0; d<codeCounter; d++){
			for(int e=0; e<productNutrients.length; e++){
				if(uniqueCodes[d].equals(productNutrients[e].nutrientCode)){
					String[] nutrientLine = nutrientWords[e].split("\",\"");
					nutrients[d] = new Nutrient(nutrientLine[0].replaceAll("\"", ""),nutrientLine[2].replaceAll("\"", ""),nutrientLine[5].replaceAll("\"", ""));
					//System.out.println(nutrients[d].nutrientName);
					break;
				}
			}
		}

	}

	public void readServingSizes(String servingSizeFile) {
		//parse the file
		Scanner input = null;
		try{
			input = new Scanner(new File(servingSizeFile));
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		StringBuilder fileContent = new StringBuilder();

		while(input.hasNextLine()){
			fileContent.append(input.nextLine() + "\n");
		}
		String[] servingSizes = fileContent.toString().split("\n");
		//loop through product object and populate four fields
		for(int i=0; i<servingSizes.length; i++ ){
			String[] eachServingSize = servingSizes[i].split("\",\"");
			eachServingSize[0] = eachServingSize[0].replaceAll("\"", "");
			for(int j=0; j<products.length; j++){
				//if the ndbservingsize number matches the product ndb number,
				//populate the rest of the product properties 
				if(eachServingSize[0].equals(products[j].ndbNumber)){
					try{
						
						products[j].servingSize = Float.valueOf(eachServingSize[1]);
					}catch(NumberFormatException e){
						products[j].servingSize = 0;
						
					}
					products[j].servingUom = eachServingSize[2];
					try{
						products[j].householdSize = Float.valueOf(eachServingSize[3]);
					}catch(NumberFormatException e){
						products[j].householdSize = 0;

					}
					products[j].householdUom = eachServingSize[4];
					//System.out.println(products[j].productName + " " + products[j].householdUom);
					break;
				}
			}

		}

	}


}
