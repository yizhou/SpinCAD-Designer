/* SpinCAD Designer - DSP Development Tool for the Spin FV-1 
 * tremolizerCADBlock.java
 * Copyright (C) 2015 - Gary Worsham 
 * Based on ElmGen by Andrew Kilpatrick 
 * 
 *   This program is free software: you can redistribute it and/or modify 
 *   it under the terms of the GNU General Public License as published by 
 *   the Free Software Foundation, either version 3 of the License, or 
 *   (at your option) any later version. 
 * 
 *   This program is distributed in the hope that it will be useful, 
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of 
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
 *   GNU General Public License for more details. 
 * 
 *   You should have received a copy of the GNU General Public License 
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>. 
 *     
 */ 
		package com.holycityaudio.SpinCAD.CADBlocks;
	
		import com.holycityaudio.SpinCAD.SpinCADBlock;
		import com.holycityaudio.SpinCAD.SpinCADPin;
		import com.holycityaudio.SpinCAD.SpinFXBlock;
 		import com.holycityaudio.SpinCAD.ControlPanel.tremolizerControlPanel;
		
		public class tremolizerCADBlock extends SpinCADBlock {

			private static final long serialVersionUID = 1L;
			private tremolizerControlPanel cp = null;
			
			private double depth = 0.75;
			private int output;

			public tremolizerCADBlock(int x, int y) {
				super(x, y);
				setName("Tremolizer");	
				// Iterate through pin definitions and allocate or assign as needed
				addControlInputPin(this, "LFO_Input");
				addControlInputPin(this, "LFO_Width");
				addControlOutputPin(this, "Control_Output");
			// if any control panel elements declared, set hasControlPanel to true
						hasControlPanel = true;
						}
		
			// In the event there are parameters editable by control panel
			public void editBlock(){ 
				if(cp == null) {
					if(hasControlPanel == true) {
						cp = new tremolizerControlPanel(this);
					}
				}
			}
			
			public void clearCP() {
				cp = null;
			}	
				
			public void generateCode(SpinFXBlock sfxb) {
	
			// Iterate through mem and equ statements, allocate accordingly

			
			sfxb.comment(getName());
			
			SpinCADPin sp = null;
					
			// Iterate through pin definitions and connect or assign as needed
			sp = this.getPin("LFO_Input").getPinConnection();
			int input = -1;
			if(sp != null) {
				input = sp.getRegister();
			}
			
			sp = this.getPin("LFO_Width").getPinConnection();
			int width = -1;
			if(sp != null) {
				width = sp.getRegister();
			}
			
			
			// finally, generate the instructions
			output = sfxb.allocateReg();
			if(this.getPin("Input").isConnected() == true) {
			sfxb.readRegister(input, depth);
			if(this.getPin("LFO_Width").isConnected() == true) {
			sfxb.mulx(width);
			}
			
			sfxb.scaleOffset(-0.999, 0.999);
			sfxb.writeRegister(output, 0.0);
			this.getPin("Control_Output").setRegister(output);
			}
			

			}
			
			// create setters and getter for control panel variables
			public void setdepth(double __param) {
				depth = __param;	
			}
			
			public double getdepth() {
				return depth;
			}
		}	
