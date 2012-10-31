package components;

import processing.core.PApplet;
import themidibus.MidiBus;
import controlP5.CheckBox;
import controlP5.ControlP5;
import controlP5.Textlabel;
import controlP5.Toggle;

public class MidiOutputList {
	PApplet p;
	ControlP5 cp5;
	Textlabel heading;
	CheckBox checkboxes;

	public MidiOutputList(PApplet p) {
		this.p = p;
	}

	public void setup() {
		cp5 = new ControlP5(this.p);

		cp5.addButton("b1").setPosition(100, 120).setSize(200, 19);

		heading = cp5.addTextlabel("label").setText("Available Outputs:")
				.setPosition(20, 20).setFont(p.createFont("Verdana", 14));

		checkboxes = cp5.addCheckBox("availOutCheckBoxHandler")
				.setPosition(20, 40).setColorForeground(p.color(120))
				.setColorActive(p.color(255)).setColorLabel(p.color(255))
				.setSize(10, 10);

		// String[] availOutputs = MidiBus.availableOutputs();
		// String[] attachedOutpus = myBus.attachedOutputs();
		/*
		 * for (int i = 0; i < availOutputs.length; i++) { String output =
		 * availOutputs[i];
		 * 
		 * availOutputsCheckbox.addItem(output, i);
		 * 
		 * for(int j = 0; j < attachedOutpus.length; j++){ if(attachedOutpus[j]
		 * == output){ availOutputsCheckbox.activate(output); } } }
		 */

	}

	public void availOutCheckBoxHandler(float[] a) {

		for (int i = 0; i < a.length; i++) {
			Toggle item = checkboxes.getItem(i);
			if (a[i] == 1.0f) {
				if (!isAttachedOutput(item.getName())) {
					if (!myBus.addOutput(i)) {
						p.println("Adding Device FAILD!");
						checkboxes.deactivate(i);
					} else {
						p.println("Device Successfully Added!");
					}
				}
			} else {
				if (isAttachedOutput(item.getName())) {
					if (myBus.removeOutput(i)) {
						p.println("Device Successfully Removed!");
					} else {
						println("Removing Device FAILD!");
						p.checkboxes.activate(i);
					}

				}
			}
		}
	}
	
	
	private Boolean isAttachedOutput (String name){
	//	println("name"+name);
		String[] attachedOutputs = myBus.attachedOutputs();
		for(int i = 0; i < attachedOutputs.length; i++){
		//	println("name2"+attachedOutputs[i]);
			if(name == attachedOutputs[i]){
				println("isAttached " + name);
				return true;
			}
		}
		return false;
	}

}
