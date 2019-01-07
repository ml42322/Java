//Michell Li
//MLi5


package hw2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import javafx.collections.ObservableMap;
import javafx.collections.FXCollections;


public class Model {
	static ObservableMap<String,Nutrient> nutrientsMap=FXCollections.observableHashMap();
	static ObservableMap<String, Product> productsMap=FXCollections.observableHashMap();


	public void readProducts(String productFile) {
		//load products into product array
		CSVFormat csvFormat =CSVFormat.DEFAULT.withFirstRecordAsHeader();
		CSVParser csvParser = null;
		try {
			csvParser = CSVParser.parse(new FileReader(productFile), csvFormat);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e1){ e1.printStackTrace();}

		//instantiate new products and append to array
		for(CSVRecord csvRecord : csvParser){
			Product p = new Product(csvRecord.get(0), csvRecord.get(1), csvRecord.get(4), csvRecord.get(7));
			productsMap.put(csvRecord.get(0), p);

		}


	}

	public void readNutrients(String nutrientFile) {
		//load objects in the nutrients and productNutrients array 
		CSVFormat csvFormat =CSVFormat.DEFAULT.withFirstRecordAsHeader();
		CSVParser csvParser = null;
		try {
			csvParser = CSVParser.parse(new FileReader(nutrientFile), csvFormat);
			//productNutrient array initialization
			for(CSVRecord csvRecord  : csvParser){
				if(nutrientsMap.get(csvRecord.get(1))==null){
					nutrientsMap.put(csvRecord.get(1), new Nutrient(csvRecord.get(1),csvRecord.get(2),csvRecord.get(5)));
				}
				Product c = productsMap.get(csvRecord.get(0));
				if(Float.valueOf(csvRecord.get(4))>0){

					c.getProductNutrients().put(csvRecord.get(1), c.new ProductNutrient(csvRecord.get(1), Float.valueOf(csvRecord.get(4))));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e1){ e1.printStackTrace();}
	}

	public void readServingSizes(String servingSizeFile) {
		//parse the file
		CSVFormat csvFormat =CSVFormat.DEFAULT.withFirstRecordAsHeader();
		CSVParser csvParser = null;
		try {
			csvParser = CSVParser.parse(new FileReader(servingSizeFile), csvFormat);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e1){ e1.printStackTrace();}

		for(CSVRecord csvRecord : csvParser ){
//			System.out.println(++i+": "+csvRecord.toString());
			Product p = productsMap.get(csvRecord.get(0));
			//if the ndbservingsize number matches the product ndb number,
			//populate the rest of the product properties 

			try{

				p.setServingSize(Float.valueOf(csvRecord.get(1)));
			}catch(NumberFormatException e){
				p.setServingSize(0);
			}
			p.setServingUoM(csvRecord.get(2));
			try{
				p.setHouseholdSize(Float.valueOf(csvRecord.get(3)));
		
			}catch(NumberFormatException e){
				p.setHouseholdSize(0);
			}
			p.setHouseholdUom(csvRecord.get(4));
		}


	}

	public boolean readProfiles(String filename){
		DataFiler datafiler = null;
		if(filename.endsWith(".csv")){
			datafiler = new CSVFiler();

		}else{
			datafiler = new XMLFiler();
		}
		return datafiler.readFile(filename);

	}


}
