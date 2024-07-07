/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.niche.generictableimporter.controller;

import com.niche.generictableimporter.libreutil.*;
import com.niche.generictableimporter.model.GenericDbHolder;
import com.niche.generictableimporter.util.FileUtil;
import com.opencsv.CSVReaderHeaderOrderAware;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import org.apache.commons.io.FilenameUtils;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.sheet.XSpreadsheet;
import com.sun.star.sheet.XSpreadsheetDocument;
import com.sun.star.table.XCell;
import com.sun.star.table.XCellRange;
import javax.swing.JTextArea;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Nico
 */
public class DbImporter {
	private GenericDbHolder database;
	
	private static final String LIBRE_INSTALL = "C:/Program Files/LibreOffice/program";
	
	public GenericDbHolder getDatabase() {
		return database;
	}
	
	// empties out database
	public void emptyDatabase() {
		// make new, empty database so as to not mess with
		//		anywhere the database was passed to
		database = new GenericDbHolder();
	}
	
	
	
	public DbImporter() {
		database = new GenericDbHolder();
	}
	
	
	public void importDb(int operation) {
		switch (operation) {
			case 0 -> this.testDefaultImport("resources/table_default.ods");
			case 1 -> this.genericImport();										// get filename from command line, redirect to appropriate importer
			// case 2 -> this.genericImportChooser();							// get filename from file chooser, redirect to appropriate importer
			case 3 -> this.testCsvImport();
			case 4 -> this.testHtmlImport();
			case 5 -> this.testXlsImport();
			case 6 -> this.testXlsxImport();
			case 7 -> this.testLibreImport();
			default -> System.out.println("------ Option not available ------");
		}
	}
	
	public void importDb(String filename) {
		this.importGenericFile(filename);
//		printDatabase();
	}
	
	// <editor-fold defaultstate="collapsed" desc="TEST ONLY - default file names">
	// TEST ONLY																TEST ONLY
	private void testDefaultImport(String filename) {
		importGenericFile(filename);
		printDatabase();
		// -------- empty database between tests --------
		emptyDatabase();
	}
	
	private void testCsvImport() {
		// testing CSV import
		String csvFilename = "resources/table_test.csv";
		this.importCsv(csvFilename);
		
		System.out.println("CSV:");
		printDatabase();
		
		// -------- empty database between tests --------
		emptyDatabase();
	}
	
	private void testHtmlImport() {
		// testing HTML import
		String htmlFilename = "resources/html_test1.html";
		this.importHtmlTable(htmlFilename);
		
		System.out.println("HTML:");
		printDatabase();
		
		// -------- empty database between tests --------
		emptyDatabase();
	}
	
	private void testXlsImport() {
		// testing excel (pre-2007) import
		String xlsFilename = "resources/table_test_excel2.xls";
		this.importExcelTable(xlsFilename);
		
		System.out.println("Xls:");
		printDatabase();
		
		// -------- empty database between tests --------
		emptyDatabase();
	}
	
	private void testXlsxImport() {
		// testing excel (2007+) import
		String xlsxFilename = "resources/table_test_excel2.xlsx";
		this.importXlsxTable(xlsxFilename);
		
		System.out.println("Xlsx:");
		printDatabase();
		
		// -------- empty database between tests --------
		emptyDatabase();
	}
	
	private void testLibreImport() {
		// testing LibreOffice import
		String libreFilename = "resources/table_test_libre.ods";
		this.importLibreOfficeTable(libreFilename);
		
		System.out.println("ODS:");
		printDatabase();
		
		// -------- empty database between tests --------
		emptyDatabase();
	}
	// </editor-fold>																TEST ONLY
	
	// will detect the file extension of the desired import file
	private void genericImport() {
		String filename = FileUtil.CmdPath();
		// String filename = "resources/table_test_libre.ods";
		
		importGenericFile(filename);
//		printDatabase();
	}
	
	
	private void importGenericFile(String filename) {
		// get extension
		String ext = FilenameUtils.getExtension(filename);
		
		// do import, while getting file type for output
		String fileType = switch (ext) {
			case "ods" -> {
				// LibreOffice import
				this.importLibreOfficeTable(filename);
				yield "ODS:";
			} case "csv" -> {
				// Csv import
				this.importCsv(filename);
				yield "CSV:";
			} case "xlsx" -> {
				// Excel (2007+) import
				this.importXlsxTable(filename);
				yield "XLSX:";
			} case "xls" -> {
				// Excel (pre-2007) import
				this.importExcelTable(filename);
				yield "XLS:";
			} case "html" -> {
				// HTML import
				this.importHtmlTable(filename);
				yield "HTML:";
			}
			default -> "------ Extension not supported ------";
		};
		
		// output filetype (or error message)
		System.out.println(fileType);
	}
	
	public void printDatabase() {
		// print database
		if (database == null || database.getImportedDb().isEmpty()) {
			System.out.println("------ Database is empty ------");
		} else {
			database.output();
		}
	}
	
	public void printDatabase(JTextArea textArea) {
		// print database to TextArea
		if (database == null || database.getImportedDb().isEmpty()) {
			System.out.println("------ Database is empty ------");
		} else {
			database.output(textArea);
		}
	}
	
	
	// -----------------------------------------
	// --------------- IMPORTERS ---------------
	// -----------------------------------------
	
	// <editor-fold defaultstate="collapsed" desc="IMPORTERS">
	// IMPORTERS																IMPORTERS
	// ----------------------------------------
	// ------------- CSV importer -------------
	// ----------------------------------------
	private void importCsv(String filename) {
		
		// TODO: remove whitespace from beginning, to get accurate headers
		
		try {
			CSVReaderHeaderOrderAware readerOrder = new CSVReaderHeaderOrderAware(new FileReader(filename));
			LinkedHashMap<String, String> values;
			
			// while (values != null) {			// this loops one more time than I want....
			// ... this version works better
			while ((values = readerOrder.readMap()) != null) {
				// putting this here is kind of messy, but guarantees headers in the proper order
				if (database.getHeaders().isEmpty()) {
					ArrayList<String> keys = new ArrayList<>(values.keySet());
					database.setHeaders(keys);
				}
				database.addRow(values);
				// System.out.println("check");
			}
			
		} catch (CsvValidationException | IOException e) {
			e.printStackTrace();
		}
	}
	
	// ----------------------------------------
	// --------- LibreOffice importer ---------
	// ----------------------------------------
	private void importLibreOfficeTable(String filename) {
		// System.out.println("Please convert " + filename + " to XLS or XLSX.");	// navigating the LibreOffice API is currently too much trouble
		ArrayList <String> data = new ArrayList<>();
		
		// make sure Lo knows where the LibreOffice install is
		if (!Lo.getLibExeFolder().equals(LIBRE_INSTALL)) {
			Lo.setLibExeFolder(LIBRE_INSTALL);
		}
		
		XComponentLoader loader = Lo.loadOffice();								// TODO: should gracefully fail if LibreOffice SDK etc. not installed
		
		// load the document
		XSpreadsheetDocument doc = Calc.openDoc(filename, loader);
		if (doc == null) {
			System.out.println("Could not open " + filename);
			Lo.closeOffice();
			return;
		}
		XSpreadsheet firstSheet = Calc.getSheet(doc, 0);
		
		int cols; // No of cols
		int rows; // No of rows
		
		try {
			XCellRange activeSheet = Calc.findUsedRange(firstSheet);
			
			rows = MyCalc.getNumRows(activeSheet);
			cols = MyCalc.getNumCols(activeSheet);
			
			for (int r = 0; r < rows; r++) {
				
				for (int c = 0; c < cols; c++) {
					// the formula of a numeric string is the string w/' at the beginning!
					data.add(Calc.getCell(firstSheet, c, r).getFormula());		// TODO: remove ' from beginning of numbers; maybe check for actual formulae?
				}
				
				// if headers haven't been set, do that instead of creating new row
				//		(this assumes the first row is table headers)			// TODO: naive assumption about first row
				if (database.getHeaders().isEmpty()) {
					// make new ArrayList so that keys are not tied to ArrayList we want to clear
					ArrayList<String> keys = new ArrayList<>(data);
					database.setHeaders(keys);
				} else {
					database.addRow(data);
				}
				data.clear();
			}
			
		} catch (java.lang.ArrayIndexOutOfBoundsException ai) {
			System.out.println("Out of bounds exception in array");
		}
		
		// cleanup
		Lo.closeDoc(doc);
		Lo.closeOffice();
	}
	
	// ----------------------------------------
	// ------------- XLS importer -------------
	// ----------------------------------------
	private void importExcelTable(String filename) {
		try {
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filename));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			// only reads from the first sheet
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow row;
			HSSFCell cell;
			ArrayList <String> data = new ArrayList<>();
			DataFormatter fmt = new DataFormatter();
			
			int rows; // No of rows
			rows = sheet.getPhysicalNumberOfRows();
			
			int cols = 0; // No of columns
			int tmp = 0;
			
			// This trick ensures that we get the data properly even if it doesn't start from first few rows
			for(int i = 0; i < 10 || i < rows; i++) {
				row = sheet.getRow(i);
				if(row != null) {
					tmp = sheet.getRow(i).getPhysicalNumberOfCells();
					if(tmp > cols) cols = tmp;
				}
			}
			
			for(int r = 0; r < rows; r++) {
				row = sheet.getRow(r);
				if(row != null) {
					for(int c = 0; c < cols; c++) {
						cell = row.getCell((short)c);
						
						// put through data formatter to get the value as seen in the spreadsheet
						// (mostly useful for number formatting -- also makes null cell into empty String)
						String valueAsSeenInExcel = fmt.formatCellValue(cell);
						data.add(valueAsSeenInExcel);
					}
					
					// if headers haven't been set, do that instead of creating new row
					//		(this assumes the first row is table headers)		// TODO: naive assumption about first row
					if (database.getHeaders().isEmpty()) {
						// make new ArrayList so that keys are not tied to ArrayList we want to clear
						ArrayList<String> keys = new ArrayList<>(data);
						database.setHeaders(keys);
					} else {
						database.addRow(data);
					}
					data.clear();
				}
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	// ---------------------------------------
	// ------------ XLSX importer ------------
	// ---------------------------------------
	private void importXlsxTable(String filename) {
		try {
			// POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filename));
			XSSFWorkbook wb = new XSSFWorkbook(filename);
			// only reads from the first sheet
			XSSFSheet sheet = wb.getSheetAt(0);
			XSSFRow row;
			XSSFCell cell;
			ArrayList <String> data = new ArrayList<>();
			DataFormatter fmt = new DataFormatter();
			
			int rows; // No of rows
			rows = sheet.getPhysicalNumberOfRows();
			
			int cols = 0; // No of columns
			int tmp = 0;
			
			// This trick ensures that we get the data properly even if it doesn't start from first few rows
			for(int i = 0; i < 10 || i < rows; i++) {
				row = sheet.getRow(i);
				if(row != null) {
					tmp = sheet.getRow(i).getPhysicalNumberOfCells();
					if(tmp > cols) cols = tmp;
				}
			}
			
			for(int r = 0; r < rows; r++) {
				row = sheet.getRow(r);
				if(row != null) {
					for(int c = 0; c < cols; c++) {
						cell = row.getCell((short)c);
						
						// put through data formatter to get the value as seen in the spreadsheet
						// (mostly useful for number formatting -- also makes null cell into empty String)
						String valueAsSeenInExcel = fmt.formatCellValue(cell);
						data.add(valueAsSeenInExcel);
					}
					
					// if headers haven't been set, do that instead of creating new row
					//		(this assumes the first row is table headers)		// TODO: naive assumption about first row
					if (database.getHeaders().isEmpty()) {
						// make new ArrayList so that keys are not tied to ArrayList we want to clear
						ArrayList<String> keys = new ArrayList<>(data);
						database.setHeaders(keys);
					} else {
						database.addRow(data);
					}
					data.clear();
				}
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	// -----------------------------------------
	// ---------- html table importer ----------
	// -----------------------------------------
	private void importHtmlTable(String filename) {
		
		try {
			File input = new File(filename);
			Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
			
			ArrayList <String> data = new ArrayList<>();
																				// TODO: this is currently naive (assumes one table, nothing more)
			// get table
			Elements tables = doc.getElementsByTag("table");
			
			for (Element table : tables) {
				Elements rows = table.getElementsByTag("tr");
				
				for (Element row : rows) {
					Elements cells = row.getElementsByTag("td");
					for (Element cell : cells) {
						data.add(cell.text());
					}
					// if headers haven't been set, do that instead of creating new row
					//		(this assumes the first row is table headers)		// TODO: naive assumption about first row
					if (database.getHeaders().isEmpty()) {
						ArrayList<String> keys = new ArrayList<>(data);
						database.setHeaders(keys);
					} else {
						database.addRow(data);
					}
					data.clear();
				}
			}
			
			// System.out.println("check");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// </editor-fold>															IMPORTERS
}
