package edu.roosevelt.vsshooter.gun;

import edu.roosevelt.vsshooter.Actor;

public abstract class Gun {
    
    public double bps;
    protected long shotProgress;
    protected Actor source;
    protected boolean fired;
    protected boolean singleShot = false;//Debugging constant. One shot only, for debugging bullets.
    public int velocity;
    
    public void update(long milliSeconds) {
        if ((!fired||!singleShot) && bps != Double.NaN) {
            long milliPerBullet = (int) (1000 / bps);
            shotProgress += milliSeconds;
            while (shotProgress > milliPerBullet) {
                shotProgress -= milliPerBullet;
                generateBullet(shotProgress);
                fired = true;
            }
        }
    }
    
    public abstract void generateBullet(long shotProgress);
    
    public Gun(Actor source) {
        this.source = source;
        shotProgress = 0;
        this.fired = false;
    }
    
}
