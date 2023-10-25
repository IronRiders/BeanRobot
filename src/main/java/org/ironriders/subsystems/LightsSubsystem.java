package org.ironriders.subsystems;


import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LightsSubsystem extends SubsystemBase {
public enum LightMode{
    OFF,
    GREEN,
    GOLD,
    RAINBOW,
    BROWN
}
    // With eager singleton initialization, any static variables/fields used in the 
    // constructor must appear before the "INSTANCE" variable so that they are initialized 
    // before the constructor is called when the "INSTANCE" variable initializes.



private LightMode lightMode;
private AddressableLED addressableLED= new AddressableLED(0);
private AddressableLEDBuffer ledBuffer=new AddressableLEDBuffer(50);
private int rainbowFirstHue=0;
    public LightsSubsystem(LightMode defaultMode) {
    this.lightMode=defaultMode;

    addressableLED.setLength(ledBuffer.getLength());
    }
    public void setColorRGB(int r,int g,int b){
for(int i=0; i< ledBuffer.getLength();i++){
    ledBuffer.setRGB(i,r,g,b);
}
addressableLED.setData(ledBuffer);
    }
    public LightMode getLightMode(){
        return lightMode;
    }
    public void setLightMode(LightMode lightMode){
        this.lightMode=lightMode;
    }

    @Override
    public void periodic() {
        super.periodic();
        switch (lightMode){
            case OFF ->setColorRGB(0,0,0);
            case GREEN -> setColorRGB(0,255,0);
            case GOLD -> setColorRGB(255,255,0);
            case RAINBOW -> rainbow();
            case BROWN -> setColorRGB(120, 90, 7);
        }
    }
    private void rainbow(){
        for(int i=0;i< ledBuffer.getLength();i++){
            int hue=(rainbowFirstHue+(i*180/ ledBuffer.getLength()))%100;
            ledBuffer.setHSV(i,hue,255,128);
        }
        rainbowFirstHue +=1;
        rainbowFirstHue %= 180;
        addressableLED.setData(ledBuffer);
    }
}

