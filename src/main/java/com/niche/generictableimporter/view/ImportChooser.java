/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.niche.generictableimporter.view;

import com.niche.generictableimporter.controller.ImportListener;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Nico
 */
public class ImportChooser extends JFrame {
	static private final String NEWLINE = "\n";
	JButton openButton;
	JButton closeButton;
	JButton emptyButton;
	JTextArea log;
	JFileChooser fc;
	
	private CountDownLatch doneSignal = new CountDownLatch(1);					// make sure to count down latch when closing!
	private ImportListener impList;
	
	// --------------- GETTERS ---------------
	public JButton getOpenButton() {
		return openButton;
	}
	
	public JButton getCloseButton() {
		return closeButton;
	}
	
	public JButton getEmptyButton() {
		return emptyButton;
	}
	
	public JTextArea getLog() {
		return log;
	}
	
	public JFileChooser getFc() {
		return fc;
	}
	
	// --------------- SETTERS ---------------
	public void setDoneSignal(CountDownLatch doneSignal) {
		this.doneSignal = doneSignal;
	}
	
	public void setImpList(ImportListener impList) {
		this.impList = impList;
	}
	
	
	// --------------- CONSTRUCTORS ---------------
	public ImportChooser() {
		this.impList = new ImportListener();
		frameSetup();
	}
	
	public ImportChooser(CountDownLatch doneSignal) {
		this.doneSignal = doneSignal;
		this.impList = new ImportListener();
		frameSetup();
	}
	
	public ImportChooser(ImportListener impList) {
		this.impList = impList;
		frameSetup();
	}
	
	public ImportChooser(CountDownLatch doneSignal, ImportListener impList) {
		this.doneSignal = doneSignal;
		this.impList = impList;
		frameSetup();
	}
	
	public ImportChooser(String title) {
		super(title);
		this.impList = new ImportListener();
		frameSetup();
	}
	
	public ImportChooser(String title, ImportListener impList) {
		super(title);
		this.impList = impList;
		frameSetup();
	}
	
	public ImportChooser(String title, ImportListener impList, CountDownLatch doneSignal) {
		super(title);
		this.doneSignal = doneSignal;
		this.impList = impList;
		frameSetup();
	}
	
	
	
	
	
	private void frameSetup() {
		// give listener access to this frame
		impList.setFrame(this);
		
		//Create the log first, because the action listeners need to refer to it.
		log = new JTextArea(20,50);
		log.setMargin(new Insets(5,5,5,5));
		log.setEditable(false);
		JScrollPane logScrollPane = new JScrollPane(log);
		
		//Create a file chooser
		fc = new JFileChooser();
		
		//Create the open button.  We use the image from the JLF Graphics Repository (but we extracted it from the jar).
		openButton = new JButton("Open a File...", new ImageIcon("resources/images/Open16.gif"));
		openButton.addActionListener(impList);
		
		// Create the empty button -- used to empty the database
		emptyButton = new JButton("Empty Database");
		emptyButton.addActionListener(impList);
		
		// Create the close button
		closeButton = new JButton("Close");
		closeButton.addActionListener(impList);
		
		//For layout purposes, put the buttons in a separate panel
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); //use FlowLayout
		buttonPanel.add(openButton);
		buttonPanel.add(emptyButton);
		buttonPanel.add(closeButton);
		
		//Add the buttons and the log to this panel.
		add(buttonPanel, BorderLayout.PAGE_START);
		add(logScrollPane, BorderLayout.CENTER);
		
		// add close listener
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing (WindowEvent e) {
				cleanClose(e.getWindow());
			}
		});
	}
	
	
	// -----------------------------------------
	// -------------- Log methods --------------
	// -----------------------------------------
	
	public void logAppend(String content) {
		log.append(content + NEWLINE);
	}
	
	public void logEndCaret() {
		log.setCaretPosition(log.getDocument().getLength());
	}
	
	public void logUpdate() {
		log.update(log.getGraphics());
	}
	
	
	
	
	// close this frame cleanly, including decrementing the doneSignal if applicable
	public void cleanClose(Window win) {
		System.out.println("------ closing window --------");
		log.append("--- closing window ---" + NEWLINE);
		log.setCaretPosition(log.getDocument().getLength());
		log.update(log.getGraphics());
		
//		try {
//			TimeUnit.SECONDS.sleep(2);
//		} catch (InterruptedException ie) {
//			System.out.println("sleep interrupted...");
//		}
		
		// if using CountDownLatch, make sure to decrement it
		if (doneSignal.getCount() > 0) {
			doneSignal.countDown();
		}
		
		win.dispose();
	}
}
