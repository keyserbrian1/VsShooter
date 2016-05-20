package edu.roosevelt.vsshooter.gun;

import edu.roosevelt.vsshooter.bullet.LaserBullet;
import edu.roosevelt.vsshooter.ship.EnemyShip;

public class EnemyLaserGun extends Gun {
    
    public int width = 60;
    public int duration = 500;
    public long delay = 3000;
    
    
    public EnemyLaserGun(EnemyShip source) {
        super(source);
        // TODO Auto-generated constructor stub
        bps = Double.NaN;
    }
    
    @Override
    public void generateBullet(long shotProgress) {
        // TODO Auto-generated method stub
        LaserBullet b = new LaserBullet((source.getWidth()+(width/6))/2, source.getHeight(), width, duration, (EnemyShip)source);
        source.addChild(b);
        b.update(0);
    }
    
}
