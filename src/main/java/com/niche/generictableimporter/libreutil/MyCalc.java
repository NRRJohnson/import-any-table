/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.niche.generictableimporter.libreutil;

import com.sun.star.table.XCellRange;
import com.sun.star.lang.IndexOutOfBoundsException;

/**
 *
 * @author Nico
 * 
 * These are additional Calc util methods to fill gaps I found in Calc.java
 */
public class MyCalc {
	
	public static int getNumRows(XCellRange range) {
		int rows = 0;
		int r = -1;
		
		try {
			while (true) {
				r++;
				range.getCellByPosition(0, r);
				rows++;
			}
		} catch (IndexOutOfBoundsException ioobe) {
			// this is, unfortunately, intended
		}
		
		return rows;
	}
	
	public static int getNumCols(XCellRange range) {
		int cols = 0;
		int c = -1;
		
		try {
			while (true) {
				c++;
				range.getCellByPosition(c, 0);
				cols++;
			}
		} catch (IndexOutOfBoundsException ioobe) {
			// this is, unfortunately, intended
		}
		
		return cols;
	}
}
