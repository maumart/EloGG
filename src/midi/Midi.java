package midi;

import processing.core.PApplet;
import themidibus.MidiBus;

public class Midi {
	
	public MidiBus bus;
	private int channel;
	private int velocity = 80;
	private PApplet p;
	
	public Midi(PApplet p){
		channel = 0;
		this.p = p;
		MidiBus.list();
		bus = new MidiBus(p, -1, 1);
	}
	
	
	public void noteOn(int channel, int pitch, int velocity){
		int vel = this.velocity;
		if(velocity != -1) vel = velocity;
		bus.sendNoteOn(channel, pitch, vel);
	}
	
	public void noteOff(int channel, int pitch, int velocity){
		int vel = this.velocity;
		if(velocity != -1) vel = velocity;
		bus.sendNoteOff(channel, pitch, vel);
	}

}
