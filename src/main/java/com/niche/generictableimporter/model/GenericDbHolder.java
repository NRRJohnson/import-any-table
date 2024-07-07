/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.niche.generictableimporter.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JTextArea;

/**
 *
 * @author Nico
 */
public class GenericDbHolder {
	// LinkedHashMap used in order to retain order of key/value pairs
	private ArrayList<LinkedHashMap<String, String>> importedDb;
	private ArrayList<String> headers;
	
//	public GenericDbHolder(ArrayList<String> importedDb) {
//		this.importedDb = importedDb;
//	}
	
	/*	// if constructing with full ArrayList of LinkedHashMaps, use setter instead
	public GenericDbHolder(ArrayList<LinkedHashMap<String, String>> importedDb) {
		this.importedDb = importedDb;
	}
	*/
	
	// --------------- CONSTRUCTORS ---------------
	public GenericDbHolder() {
		importedDb = new ArrayList<>();
		headers = new ArrayList<>();
	}
	
	public GenericDbHolder(ArrayList<String> headers) {
		this.headers = headers;
	}
	
	
	
	// add a "row", passing in only row contents, not headers
	public void addRow(ArrayList<String> rowData) {
		if (rowData.size() == headers.size()) {
			LinkedHashMap<String, String> row = new LinkedHashMap<>();
			for (int i = 0; i < headers.size(); i++) {
				row.put(headers.get(i), rowData.get(i));
			}
			importedDb.add(row);
		} else {
			//																	// throw an exception?
		}
	}
	
	// add a "row" from a passed Map
	public void addRow(Map<String, String> rowData) {
		LinkedHashMap<String, String> row = new LinkedHashMap<>();
		for (int i = 0; i < headers.size(); i++) {
			row.put(headers.get(i), rowData.get(headers.get(i)));
		}
		importedDb.add(row);
	}
	
	// add a "row" from a passed Map, adding headers if necessary				TODO ?
	
	
	
	// insert a "row" at a given index, passing in only row contents, not headers
	public void insertRow(int index, ArrayList<String> rowData) {
		if (rowData.size() == headers.size()) {
			LinkedHashMap<String, String> row = new LinkedHashMap<>();
			for (int i = 0; i < headers.size(); i++) {
				row.put(headers.get(i), rowData.get(i));
			}
			importedDb.add(index, row);
		} else {
			//																	// throw an exception?
		}
	}
	
	// insert a "row" at a given index, from a passed-in Map
	public void insertRow(int index, Map<String, String> rowData) {
		LinkedHashMap<String, String> row = new LinkedHashMap<>();
		for (int i = 0; i < headers.size(); i++) {
			row.put(headers.get(i), rowData.get(headers.get(i)));
		}
		importedDb.add(index, row);
	}
	
	
	
	// find index of first row with given key/value pair
	// returns -1 if row not found
	public int getRowNum(String key, String value) {
		int row = -1;
		
		for (int i = 0; i < importedDb.size(); i++) {
			if (importedDb.get(i).containsKey(key)) {
				if (importedDb.get(i).get(key).equals(value)) {
					row = i;
					break;
				}
			}
		}
		return row;
	}
	
	// find all indices of first row with given key/value pair
	// returns empty ArrayList if row not found
	public ArrayList<Integer> getRowNums(String key, String value) {
		ArrayList<Integer> rows = new ArrayList<>();
		
		for (int i = 0; i < importedDb.size(); i++) {
			if (importedDb.get(i).containsKey(key)) {
				if (importedDb.get(i).get(key).equals(value)) {
					rows.add(i);
				}
			}
		}
		return rows;
	}
	
	
	
	// simple test output
	public void output() {
		String line = "";
		
		for(LinkedHashMap<String, String> i : this.importedDb) {
			line = (i.toString());
			System.out.println(line);
		}
		
		System.out.println("--- END ---");
		
	}
	
	public void output(JTextArea textArea) {
		String NEWLINE = "\n";
		String line = "";
		
		for(LinkedHashMap<String, String> i : this.importedDb) {
			line = (i.toString());
			textArea.append(line + NEWLINE);
			textArea.setCaretPosition(textArea.getDocument().getLength());
		}
		
		textArea.append("--- END ---" + NEWLINE);
		textArea.setCaretPosition(textArea.getDocument().getLength());
		textArea.update(textArea.getGraphics());
		
	}
	
	
	// --------------- GETTERS ---------------
	public ArrayList<LinkedHashMap<String, String>> getImportedDb() {
		return importedDb;
	}
	
	public ArrayList<String> getHeaders() {
		return headers;
	}
	
	
	// --------------- COMPLICATED GETTERS ---------------
	
	// get full row by array index
	public LinkedHashMap<String, String> getRow(int index) {
		return importedDb.get(index);
	}
	
	// get first row found with given key/value pair -- returns an empty LinkedHashMap if nothing found
	public LinkedHashMap<String, String> getValueRowNaive(String key, String value) {
		LinkedHashMap<String, String> row = new LinkedHashMap<>();
		
		for (LinkedHashMap i : this.importedDb) {
			// check if key exists, so as to not try to pass NULL to .get()
			if (i.containsKey(key) && i.get(key) == value) {
			// if (i.containsKey(key) && i.get(key) == value) {
				row = i;
				break;
			}
		}
		return row;
	}
	
	// get all rows with given key/value pair -- returns an empty LinkedHashMap if nothing found
	public ArrayList<LinkedHashMap<String, String>> getValueRows(String key, String value) {
		ArrayList<LinkedHashMap<String, String>> rows = new ArrayList<>();
		
		for (LinkedHashMap i : this.importedDb) {
			// check if key exists, so as to not try to pass NULL to .get()
			if (i.containsKey(key) && i.get(key) == value) {
				rows.add(i);
			}
		}
		return rows;
	}
	
	
	// --------------- SETTERS ---------------
	public void setImportedDb(ArrayList<LinkedHashMap<String, String>> importedDb) {
		this.importedDb = importedDb;
	}
	
	public void setHeaders(ArrayList<String> headers) {
		this.headers = headers;
	}
	
	
	// --------------- COMPLICATED SETTERS ---------------
	
	// add a "row" to importedDb
	public void setAddRow(LinkedHashMap<String, String> addedRow) {
		this.importedDb.add(addedRow);
	}
}
