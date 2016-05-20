package edu.roosevelt.vsshooter.gun;

import edu.roosevelt.vsshooter.Stage;
import edu.roosevelt.vsshooter.bullet.BasicBullet;
import edu.roosevelt.vsshooter.bullet.Bullet;
import edu.roosevelt.vsshooter.ship.PlayerShip;

public class BasicTargetedGun extends TargetedGun {
    
    public BasicTargetedGun(PlayerShip source) {
        super(source);
        cost = 30;
    }
    
    @Override
    public void generateBullet(double tapX, double tapY) {
        double direction = Math.toDegrees(Math.atan2(tapY-source.getTrueY(), tapX-source.getTrueX()));
        Bullet b = new BasicBullet(source.getWidth() / 2+source.getTrueX(), source.getTrueY(), 14, 4, (int)direction, 300);
        Stage.getCurrentStage().addChild(b);
        b.update(0);//force an update to rotate the bullet to avoid one frame of no rotation.
    }
    
}
