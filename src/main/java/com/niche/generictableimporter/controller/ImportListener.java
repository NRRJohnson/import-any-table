/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.niche.generictableimporter.controller;

//import com.niche.generictableimporter.view;

import com.niche.generictableimporter.view.ImportChooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Nico
 */
public class ImportListener implements ActionListener {
	ImportChooser frame;
	private String filename;
	
	ImportController parentController;
	
	// --------------- SETTERS ---------------
	public void setFrame(ImportChooser frame) {
		this.frame = frame;
	}
	
	public void setParentController(ImportController parentController) {
		this.parentController = parentController;
	}
	
	
	// --------------- CONSTRUCTORS ---------------
	public ImportListener() {
	}
	
	public ImportListener(ImportChooser chooser) {
		frame = chooser;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//Handle open button action.
		if (e.getSource() == frame.getOpenButton()) {
			int returnVal = frame.getFc().showOpenDialog(frame);
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = frame.getFc().getSelectedFile();
				// Open the file
				filename = file.getPath();
				frame.logAppend("Opening: " + filename + ".");
				parentController.importFile(filename);
				parentController.outputDbAll(frame.getLog());
			} else {
				frame.logAppend("Open command cancelled by user.");
			}
			frame.logEndCaret();
			
		// Handle empty button action
		} else if (e.getSource() == frame.getEmptyButton()) {
			int input = JOptionPane.showConfirmDialog(frame, "Are you sure you want to empty the database?", "Are you sure?", JOptionPane.YES_NO_OPTION);
			if (input == JOptionPane.YES_OPTION) {
				parentController.emptyDb();
			}
		} else if (e.getSource() == frame.getCloseButton()) {
			frame.cleanClose(frame);
		}
	}
}
