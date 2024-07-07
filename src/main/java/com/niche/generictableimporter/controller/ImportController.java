/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.niche.generictableimporter.controller;

import com.niche.generictableimporter.view.ImportChooser;
import javax.swing.JTextArea;

/**
 *
 * @author Nico
 */
public class ImportController {
	private final DbImporter imp = new DbImporter();
	
	public void control() {
		// instead, create the chooser not through DbImporter, listen for filename
		ImportListener impList = new ImportListener();
		impList.setParentController(this);
		
		ImportChooser impChoose = new ImportChooser("Table Importer", impList);
		
		impChoose.pack();
		impChoose.setVisible(true);
		
		System.out.println("------ Chooser opened ------");
	}
	
	public void importFile(String filename) {
		imp.importDb(filename);
	}
	
	
	// ----------------------------------------
	// --------- pass-through methods ---------
	// ----------------------------------------
	
	public void outputDb() {
		imp.printDatabase();
	}
	
	public void outputDb(JTextArea textArea) {
		imp.printDatabase(textArea);
	}
	
	public void outputDbAll(JTextArea textArea) {
		outputDb();
		outputDb(textArea);
	}
	
	public void emptyDb() {
		imp.emptyDatabase();
	}
}
