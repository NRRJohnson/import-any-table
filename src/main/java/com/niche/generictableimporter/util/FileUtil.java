/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.niche.generictableimporter.util;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author Nico
 */
public class FileUtil {
	
	public static String CmdPath() {
		String input = getInput("Enter target filepath:");
		
		// error check input
		File file = new File(input);
		
		if (file.exists()) {
			if (file.isDirectory()) {
				System.out.println("------ that's a directory! ------");
				return "";
			} else if (file.isFile()) {
				System.out.println("------ that's a file! ------");
				return input;
			} else {
				System.out.println("------ that's something else! ------");
				return "";
			}
		} else {
			System.out.println("------ File does not exist ------");
			return "";
		}
		
	}
	
	
	// get file path with java gui interface									// TODO
	public static String GuiPath() {
		String path = "";
		
		return path;
	}
	
	
	// get any arbitrary string input from the console
	public static String getInput(String prompt) {
		Scanner myObj = new Scanner(System.in);  // Create a Scanner object
		System.out.println(prompt);
		
		String input = myObj.nextLine();  // Read user input
		System.out.println("Input is: " + input);  // Output user input
		
		return input;
	}
}
