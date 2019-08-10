package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import view.MainTest;

public class DataRegistry {
	
	private MainTest simulation;

	public DataRegistry(MainTest simulation) {
		this.simulation = simulation;
	}
	
	public void loadImport(File file) {
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    if ((line = br.readLine()).equals("format 1")) {
			    while ((line = br.readLine()) != null) {
			    	String[] lineArr = line.split(" ");
			    	switch(lineArr[0]) {
			    	case "width":
			    		System.out.println("Width = " + lineArr[1]);
			    		break;
			    	case "height":
			    		System.out.println("Height = " + lineArr[1]);
			    		break;
			    	case "capacity":
			    		System.out.println("Charge Capacity = " + lineArr[1]);
			    		break;
			    	case "chargeSpeed":
			    		System.out.println("Charge Speed = " + lineArr[1]);
			    		break;
			    	case "podRobot":
			    		System.out.println("Charging Pod ID = " + lineArr[1] + " Robot ID = " + lineArr[2] + " X-Coordinate " + lineArr[3] + " Y-Coordinate " + lineArr[4]);
			    		break;
			    	case "shelf":
			    		System.out.println("Storage Shelf ID = " + lineArr[1] + " X-Coordinate " + lineArr[2] + " Y-Coordinate " + lineArr[3]);
			    		break;
			    	case "station":
			    		System.out.println("Packing Station ID = " + lineArr[1] + " X-Coordinate " + lineArr[2] + " Y-Coordinate " + lineArr[3]);
			    		break;
			    	case "order":
			    		System.out.println("Packing Ticks = " + lineArr[1]);
			    		break;
			    	default:
			    		System.out.println("Invalid format: " + lineArr[0]);
			    		break;
			    	}
			    }
		    } else {
		    	System.out.println("Invalid simulation format");
		    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
