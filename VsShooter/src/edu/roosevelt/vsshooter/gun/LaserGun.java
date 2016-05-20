package edu.roosevelt.vsshooter.gun;

import edu.roosevelt.vsshooter.bullet.LaserBullet;
import edu.roosevelt.vsshooter.ship.PlayerShip;

public class LaserGun extends TargetedGun {
    
    public LaserGun(PlayerShip source) {
        super(source);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void generateBullet(double tapX, double tapY) {
        // TODO Auto-generated method stub
        int width = 120;
        LaserBullet b = new LaserBullet((source.getWidth()-(width/6))/2, 0, width, 300, source);
        source.addChild(b);
        b.update(0);
    }
    
}
