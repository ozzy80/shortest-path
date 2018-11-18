package rs.rbt.backendTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

public class Test {

	public static void main(String[] args) throws ParseException {
		Scanner scan = new Scanner(System.in);
		System.out.println("Input XML path:");
		String inputPath = scan.nextLine();
		System.out.println("Output JSON path or ENTER if you want default path:");
		String outputPath = scan.nextLine();
		scan.close();

		try {
			FileProcess fileProcess = new FileProcess();
			InputStream is = fileProcess.readFile(inputPath, System.out);
			if (is != null) {
				List<Point> availableField = fileProcess.readXMLMap(is, System.out);
				is.close();

				MapSearch mapSearch = new MapSearch(availableField);
				MinimalPaths minimalPaths = mapSearch.findAllMinimalPaths();

				OutputStream os = fileProcess.writeFile(outputPath, System.out);
				fileProcess.saveJSONFile(minimalPaths, os);
				os.close();
			}
		} catch (FileNotFoundException e) {
			System.out.println("File " + inputPath + " not found. Check the file name and try again.");
		} catch (IllegalArgumentException e) {
			System.out.println("Not a valid XML file. " + e.getMessage());
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
