package rs.rbt.backendTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FileProcess {

	public InputStream readFile(String path, OutputStream os) throws FileNotFoundException {
		if (path != null && !path.isEmpty()) {
			File file = new File(path);
			return new FileInputStream(file);
		} else {
			try {
				os.write(new String("Input file path must be specified.").getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	public List<Point> readXMLMap(InputStream is, OutputStream os)
			throws IllegalArgumentException, ParserConfigurationException, IOException {
		List<Point> availableCells = new ArrayList<>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document;
		try {
			document = db.parse(is);
			setSearchPoint("start-point", document, availableCells);
			setAvailableCells(document, availableCells);
			setSearchPoint("end-point", document, availableCells);
		} catch (SAXException e) {
			os.write(new String("Not a valid XML file. Check the file and try again.").getBytes());
		}

		return availableCells;
	}

	private void setSearchPoint(String pointName, Document document, List<Point> availableCells) {
		NodeList nodeList = document.getElementsByTagName(pointName);
		if (nodeList.getLength() != 1) {
			throw new IllegalArgumentException(pointName + " argument missing");
		}
		availableCells.add(getCellCoordinate(nodeList.item(0)));
	}

	private void setAvailableCells(Document document, List<Point> availableCells) {
		NodeList nodeList = document.getElementsByTagName("cell");
		for (int i = 0; i < nodeList.getLength(); i++) {
			availableCells.add(getCellCoordinate(nodeList.item(i)));
		}
	}

	private Point getCellCoordinate(Node node) {
		int i = Integer.parseInt(node.getAttributes().getNamedItem("row").getNodeValue());
		String j = node.getAttributes().getNamedItem("col").getNodeValue();
		return new Point(i, j);
	}

	public OutputStream writeFile(String path, OutputStream os) throws IOException {
		if (path != null && !path.isEmpty()) {
			try {
				File file = new File(path);
				os.write(new String("Result is store in: " + file.getAbsolutePath()).getBytes());
				return new FileOutputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			File file = new File("map.json");
			os.write(new String("Result is store in: " + file.getAbsolutePath()).getBytes());
			return new FileOutputStream(file);
		}
	}

	public void saveJSONFile(MinimalPaths minimalPaths, OutputStream os) throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(minimalPaths);

		os.write(json.getBytes());
	}

}
