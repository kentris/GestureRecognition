package com.korpus.kentris.gesturerecognition;

/**
 * Created by thema on 12/22/2015.
 */
public class EventData {
    private float accelX, accelY, accelZ;
    private float magnetX, magnetY, magnetZ;
    private float azimuth, pitch, roll;
    private float velX, velY, velZ, velMagnitude;

    EventData(){
        velX = 0;
        velY = 0;
        velZ = 0;
    }

    public void setAccel(float x, float y, float z){
        accelX = x;
        accelY = y;
        accelZ = z;
    }

    public float[] getAccel(){
        return new float[] {accelX, accelY, accelZ};
    }

    public void setMagnet(float x, float y, float z){
        magnetX = x;
        magnetY = y;
        magnetZ = z;
    }

    public float[] getMagnet(){
        return new float[] {magnetX, magnetY, magnetZ};
    }

    public void setOrientation(float a, float p, float r){
        azimuth = a;
        pitch = p;
        roll = r;
    }

    public float[] getOrientation(){
        return new float[] {azimuth, pitch, roll};
    }

    public void setVelocity(float x, float y, float z){
        velX = x;
        velY = y;
        velZ = z;
        velMagnitude = (float) Math.sqrt( Math.pow(x,2) + Math.pow(y,2) + Math.pow(z,2) );
    }

    public float[] getVelocity(){
        return new float[] {velX, velY, velZ};
    }

    public String getAccelPrintOut(){
        return Float.toString(accelX) + ", " +
            Float.toString(accelY) + ", " +
            Float.toString(accelZ);
    }

    public String getMagnetPrintOut(){
        return Float.toString(magnetX) + ", " +
                Float.toString(magnetY) + ", " +
                Float.toString(magnetZ);
    }

    public String getOrientationPrintOut(){
        return Float.toString(azimuth) + ", " +
                Float.toString(pitch) + ", " +
                Float.toString(roll);
    }

    public String getDetailedPrintOut(){
        return "Acceleration: (" + getAccelPrintOut() + "), Geomagnet: (" + getMagnet() + "), Orientation: (" + getOrientationPrintOut() + "), Velocity: " + Float.toString(velX);
    }

    public String getPrintOut(){
        return getAccelPrintOut() + ", " + getMagnet() + ", " + getOrientationPrintOut() + ", " + Float.toString(velX);
    }


}
