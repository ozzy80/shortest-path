package rs.rbt.backendTask;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.xml.sax.SAXException;

class FileProcessTest {
	private FileProcess fileProcess;
	
	@BeforeEach
	void setup() {
		fileProcess = new FileProcess();
	}

	@Test
	void readFileTest_checkWrongPathError() throws IOException {
		String path = "unknown/unknown.xml";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		assertThrows(FileNotFoundException.class,
				() -> fileProcess.readFile(path, baos));
	}
	
	@Test
	void readFileTest_checkMissingParameter() throws IOException {
		String path = "";
		String expectedOutput = "Input file path must be specified.";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		fileProcess.readFile(path, baos);
		
		assertEquals(expectedOutput, baos.toString());
	}
		
	@Test
	void readXMLMapTest_checkQubeMap() throws ParserConfigurationException, SAXException, IOException, ParseException {
		List<Point> expectedPoints = AStarTest.createAvailableCells(3, 3, new Point(1, "C"), new Point(4, "C"));
		byte[] file = new String("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
						+ "<map>  "
						+ "	<cells>"
						+ "		<cell row=\"1\" col= \"A\" />\n"
						+ "		<cell row=\"1\" col= \"B\" />\r\n"
						+ "		<cell row=\"1\" col= \"C\" />\r\n"
						+ "		<cell row=\"2\" col= \"A\" />\r\n"
						+ "		<cell row=\"2\" col= \"B\" />\n"
						+ "		<cell row=\"2\" col= \"C\" />\r\n"
						+ "		<cell row=\"3\" col= \"A\" />\n"
						+ "		<cell row=\"3\" col= \"B\" />\r\n"
						+ "		<cell row=\"3\" col= \"C\" />\r\n"
						+ "	</cells>\r\n" 
						+ "	<start-point row=\"1\" col= \"C\" />" 
						+ "	<end-point row=\"4\" col= \"C\" />" 
						+ "</map>").getBytes();
		InputStream is = new ByteArrayInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		List<Point> actualPoints = fileProcess.readXMLMap(is, baos);
		
		assertEquals(actualPoints, expectedPoints);
		assertEquals(baos.size(), 0);
	}
	
	@Test
	void readXMLMapTest_checkHorizontalElongatedShapeMap() throws ParserConfigurationException, SAXException, IOException, ParseException {
		List<Point> expectedPoints = AStarTest.createAvailableCells(2, 4, new Point(0, "C"), new Point(3, "C"));
		byte[] file = new String("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
						+ "<map>  "
						+ "	<cells>"
						+ "		<cell row=\"1\" col= \"A\" />\n"
						+ "		<cell row=\"1\" col= \"B\" />\r\n"
						+ "		<cell row=\"1\" col= \"C\" />\r\n"
						+ "		<cell row=\"1\" col= \"D\" />\r\n"
						+ "		<cell row=\"2\" col= \"A\" />\n"
						+ "		<cell row=\"2\" col= \"B\" />\r\n"
						+ "		<cell row=\"2\" col= \"C\" />\n"
						+ "		<cell row=\"2\" col= \"D\" />\r\n"
						+ "	</cells>\r\n" 
						+ "	<start-point row=\"0\" col= \"C\" />" 
						+ "	<end-point row=\"3\" col= \"C\" />" 
						+ "</map>").getBytes();
		InputStream is = new ByteArrayInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		List<Point> actualPoints = fileProcess.readXMLMap(is, baos);
		
		assertEquals(actualPoints, expectedPoints);
		assertEquals(baos.size(), 0);
	}
	
	@Test
	void readXMLMapTest_checkVerticalElongatedShapeMap() throws ParserConfigurationException, SAXException, IOException, ParseException {
		List<Point> expectedPoints = AStarTest.createAvailableCells(4, 2, new Point(1, "C"), new Point(4, "C"));
		expectedPoints.remove(new Point(2, "C"));
		byte[] file = new String("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<map>  "
				+ "	<cells>"
				+ "		<cell row=\"1\" col= \"A\" />\n"
				+ "		<cell row=\"1\" col= \"B\" />\r\n"
				+ "		<cell row=\"2\" col= \"A\" />\r\n"
				+ "		<cell row=\"2\" col= \"B\" />\r\n"
				+ "		<cell row=\"3\" col= \"A\" />\n"
				+ "		<cell row=\"3\" col= \"B\" />\r\n"
				+ "		<cell row=\"4\" col= \"A\" />\n"
				+ "		<cell row=\"4\" col= \"B\" />\r\n"
				+ "	</cells>\r\n" 
				+ "	<start-point row=\"1\" col= \"C\" />" 
				+ "	<end-point row=\"4\" col= \"C\" />" 
				+ "</map>").getBytes();
		InputStream is = new ByteArrayInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		List<Point> actualPoints = fileProcess.readXMLMap(is, baos);
		
		assertEquals(actualPoints, expectedPoints);
		assertEquals(baos.size(), 0);
	}
	
	@Test
	void readXMLMapTest_checkMissingStartPointInXmlMap() throws ParserConfigurationException, SAXException, IOException {
		List<Point> expectedPoints = AStarTest.createAvailableCells(4, 2, new Point(1, "C"), new Point(4, "C"));
		expectedPoints.remove(new Point(2, "C"));
		byte[] file = new String("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<map>  "
				+ "	<cells>"
				+ "		<cell row=\"1\" col= \"A\" />\n"
				+ "		<cell row=\"1\" col= \"B\" />\r\n"
				+ "		<cell row=\"1\" col= \"C\" />\r\n"
				+ "		<cell row=\"2\" col= \"A\" />\r\n"
				+ "		<cell row=\"2\" col= \"B\" />\n"
				+ "		<cell row=\"2\" col= \"C\" />\r\n"
				+ "		<cell row=\"3\" col= \"A\" />\n"
				+ "		<cell row=\"3\" col= \"B\" />\r\n"
				+ "		<cell row=\"3\" col= \"C\" />\r\n"
				+ "	</cells>\r\n" 
				+ "	<end-point row=\"4\" col= \"C\" />" 
				+ "</map>").getBytes();
		InputStream is = new ByteArrayInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		assertThrows(IllegalArgumentException.class,
				() -> fileProcess.readXMLMap(is, baos));
	}
	
	@Test
	void readXMLMapTest_checkMissingEndPointInXmlMap() throws ParserConfigurationException, SAXException, IOException {
		List<Point> expectedPoints = AStarTest.createAvailableCells(4, 2, new Point(1, "C"), new Point(4, "C"));
		expectedPoints.remove(new Point(2, "C"));
		byte[] file = new String("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<map>  "
				+ "	<cells>"
				+ "		<cell row=\"1\" col= \"A\" />\n"
				+ "		<cell row=\"1\" col= \"B\" />\r\n"
				+ "		<cell row=\"1\" col= \"C\" />\r\n"
				+ "		<cell row=\"2\" col= \"A\" />\r\n"
				+ "		<cell row=\"2\" col= \"B\" />\n"
				+ "		<cell row=\"2\" col= \"C\" />\r\n"
				+ "		<cell row=\"3\" col= \"A\" />\n"
				+ "		<cell row=\"3\" col= \"B\" />\r\n"
				+ "		<cell row=\"3\" col= \"C\" />\r\n"
				+ "	</cells>\r\n" 
				+ "	<start-point row=\"1\" col= \"C\" />" 
				+ "</map>").getBytes();
		InputStream is = new ByteArrayInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		assertThrows(IllegalArgumentException.class,
				() -> fileProcess.readXMLMap(is, baos));
	}

	@ParameterizedTest
	@ValueSource(strings = { "", "newmap.json" })
	void writeFileTest_checkDefaultBehaviour(String path) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		File file = new File(path.isEmpty() ? "map.json" : path);
		String expectedOutput = "Result is store in: " + file.getAbsolutePath();
		
		fileProcess.writeFile(path, baos);
		
		assertEquals(expectedOutput, baos.toString());
	}
	
	@Test
	void saveJSONFileTest_checkJSON() throws ParserConfigurationException, SAXException, IOException {
		String expectedOutput = "{\n" + 
				"  \"execution_time_in_ms\": 10,\n" + 
				"  \"paths\": [\n" + 
				"    {\n" + 
				"      \"points\": [\n" + 
				"        {\n" + 
				"          \"row\": 1,\n" + 
				"          \"col\": \"A\"\n" + 
				"        },\n" + 
				"        {\n" + 
				"          \"row\": 2,\n" + 
				"          \"col\": \"B\"\n" + 
				"        }\n" + 
				"      ]\n" + 
				"    }\n" + 
				"  ]\n" + 
				"}";
		MinimalPaths minimalPaths = new MinimalPaths();
		Path path = new Path();
		path.addPoints(new Point(1, "A"));
		path.addPoints(new Point(2, "B"));
		minimalPaths.addPaths(path);
		minimalPaths.setExecution_time_in_ms(10);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		fileProcess.saveJSONFile(minimalPaths, baos);
		
		assertEquals(expectedOutput, baos.toString());
	}
}
