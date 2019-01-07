//Michell Li
//MLi5

package hw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class CSVFiler extends DataFiler{

	@Override
	public void writeFile(String filename) {
		// TODO Auto-generated method stub
		
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

		StringBuilder ingredients = new StringBuilder();
		String[] profile = fileInput.nextLine().split(",");
		for(int i=5; i<profile.length; i++){
			ingredients.append(profile[i] + ",");
		}
		//System.out.println(profile[0]);

		String ing = ingredients.toString();
		if(profile[0].toLowerCase().contentEquals("female")){
			try{
			NutriByte.person = new Female(Float.valueOf(profile[1]),Float.valueOf(profile[2]),Float.valueOf(profile[3]),Float.valueOf(profile[4]),ing);
			return true;
			}catch(Exception e){
				return false;
			}
		}else{
			try{
			NutriByte.person = new Male(Float.valueOf(profile[1]),Float.valueOf(profile[2]),Float.valueOf(profile[3]),Float.valueOf(profile[4]),ing);

			return true;
			}catch(Exception e){
				return false;
			}
		}
	}

}
