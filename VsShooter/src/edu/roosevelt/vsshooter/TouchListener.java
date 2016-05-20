package edu.roosevelt.vsshooter;

public interface TouchListener {

    public void handleTouchStart(double x, double y);
    
    public void handleTouchMove(double x, double y);
    
    public void handleTouchEnd();
}
