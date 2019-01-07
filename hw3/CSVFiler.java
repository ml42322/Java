//Michell Li
//MLi5

package hw3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class CSVFiler extends DataFiler{

	@Override
	public void writeFile(String filename) throws IOException {
		// TODO Auto-generated method stub
		BufferedWriter writer = null;
		Person p = NutriByte.person;
		String gender = "Female";
		StringBuilder record = new StringBuilder();
		String[] ingredients = new String[0];
		try {
			writer = new BufferedWriter(new FileWriter(filename));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(p instanceof Male){
			gender = "Male";
		}
		ingredients = p.ingredientsToWatch.split(" ");
		record.append(gender+", "+p.age+", "+p.weight+", "+p.height+", "+p.physicalActivityLevel);
			for(int i=0; i<ingredients.length; i++){
				record.append(", "+ingredients[i]);
			}
		
			//write to csv file
		writer.write(record.toString());
		writer.write("\n");
		for(Product dp : NutriByte.person.dietProductsList){
			writer.write(dp.getNdbNumber()+ ", "+ String.valueOf(dp.getServingSize())+ ", "+  String.valueOf(dp.getHouseholdSize())+"\n");
		}
		writer.flush();		
	}

	//takes in a person's profile
	//parse csv, creates person based on male or female gender
	@Override
	public boolean readFile(String filename) {
		// TODO Auto-generated method stub
		Scanner fileInput = null;
		try {
			fileInput = new Scanner (new File(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String nextline = fileInput.nextLine();
		validatePersonData(nextline);
		if(NutriByte.person == null){
			try{
				throw(new InvalidProfileException("Could not read profile data"));
			}catch(InvalidProfileException e){
				return false;	
			}
		}
		while(fileInput.hasNextLine()){
			Product p = validateProductData(fileInput.nextLine());
			if(p != null && NutriByte.person != null){
				NutriByte.person.dietProductsList.add(p);
				NutriByte.person.populateDietNutrientsMap();
			}
		}
		return true;
	}
	
	//ensure open csv file person data is correct (first line)
	public Person validatePersonData(String data){
		String[] profile = data.split(",");

		StringBuilder ingredients = new StringBuilder();
		float age =0;
		float weight = 0;
		float phys=0;
		float height = 0;
		try{
			if(!profile[0].toLowerCase().equals("female") && !profile[0].toLowerCase().equals("male")){
				throw(new InvalidProfileException("The profile must have gender: Female or Male as first word"));
			}}catch(InvalidProfileException e){
				return null;

			}catch(Exception e){

			}

		try{
			age = Float.valueOf(profile[1].trim());
			if(age<0){
				throw (new InvalidProfileException("Invalid data for age: " + profile[1] + "\n" + "Age must be a positive number"));
			}
		}catch(InvalidProfileException e){
		}catch(Exception e){
			try {
				throw new InvalidProfileException("Invalid data for age: " + profile[1] + "\n" + "Age must be a number");


			} catch (InvalidProfileException f) {
				return null;
			}
		}


		try{
			weight = Float.valueOf(profile[2].trim());
			if(weight<0){
				throw (new InvalidProfileException("Invalid data for weight: " + profile[2].trim() + "\n" + "Weight must be a positive number"));
			}
		}catch(InvalidProfileException e){
			return null;

		}catch(Exception e){
			try {
				throw new InvalidProfileException("Invalid data for weight: " + profile[2].trim() + "\n" + "Weight must be a number");		
			} catch (InvalidProfileException f) {
				return null;
			}
		}

		try{
			height = Float.valueOf(profile[3].trim());
			if(height<0){
				throw (new InvalidProfileException("Invalid data for height: " + profile[3].trim() + "\n" + "Height must be a positive number"));
			}
		}catch(InvalidProfileException e){
			return null;
		}catch(Exception e){
			try {
				throw new InvalidProfileException("Invalid data for height: " + profile[3].trim() + "\n" + "Height must be a number");		
			} catch (InvalidProfileException f) {
				return null;
			}
		}


		try{
			phys = Float.parseFloat(profile[4].trim());

			if(phys!= 1.0f && phys!=1.1f && phys != 1.25f && phys!=1.48f){
				throw (new InvalidProfileException("Invalid data for physical activity level: " + profile[4].trim() + "\n" + "Must be: 1.0, 1.1, 1.25, 1.48"));
			}
		}catch(InvalidProfileException e){
			return null;

		}catch(Exception e){
			try {
				throw new InvalidProfileException("Invalid data for physical activity level: " + profile[4].trim() + "\n" + "Must be: 1.0, 1.1, 1.25, 1.48");		
			} catch (InvalidProfileException f) {
				return null;
			}
		}


		for(int i=5; i<profile.length; i++){
			try{

				ingredients.append(profile[i] + " ");
			}catch(InvalidProfileException e){
				throw (new InvalidProfileException("Invalid ingredients"));

			}
		}
		String ing = ingredients.toString();

		//validate gender and then create person
		if(profile[0].toLowerCase().equals("female")){
			NutriByte.person = new Female(age,weight,height,phys,ing);
		}else if(profile[0].toLowerCase().equals("male")){
			NutriByte.person = new Male(age,weight,height,phys,ing);

		}
		return NutriByte.person;
	}

	//validate all products in the csv file, skips if not valid
	public Product validateProductData(String data){
		String[] validProd = data.split(", ");
		Product p = null;
		try{
			if(Model.productsMap.containsKey(validProd[0])){
				p = Model.productsMap.get(validProd[0]);
				if(p instanceof Product){
					try{
						float dietserv = Float.valueOf(validProd[1]);
						p.setServingSize(dietserv);
						float householdsize = Float.valueOf(validProd[2]);
						p.setHouseholdSize(householdsize);
					}catch(Exception e){
						p = null;
						throw(new InvalidProfileException("Cannot read: " + data + 
								"\n" + "The data must be - String, number, number "));
					}

				}
			}else{
				throw(new InvalidProfileException("No product found with this code: " + validProd[0]));
			}
		}catch(InvalidProfileException e){

		}


		return p;
	}

}
