package views.tabs;

import java.util.ArrayList;

import components.Button;

import controlP5.Controller;

public class Tab {
	
	private String name;
	private ArrayList<Controller> controller;
	
	public Tab(String name){
		this.name = name;
		controller = new ArrayList<Controller>();
	}
	
	public String getName(){
		return name;
	}
	
	public void addController(Controller c){
		
		controller.add(c);
		
	}
	
	public ArrayList<Controller> getControllers(){
		return controller;
	}

}
