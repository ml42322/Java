package hw3;

import java.io.IOException;

public abstract class DataFiler {
	
	public abstract void writeFile(String filename) throws IOException;
	
	public abstract boolean readFile(String filename);

}
