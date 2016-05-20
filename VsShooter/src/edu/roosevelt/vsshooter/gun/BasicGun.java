package edu.roosevelt.vsshooter.gun;

import edu.roosevelt.vsshooter.Actor;
import edu.roosevelt.vsshooter.Stage;
import edu.roosevelt.vsshooter.bullet.BasicBullet;
import edu.roosevelt.vsshooter.bullet.Bullet;

public class BasicGun extends Gun {
    
    boolean fired;
    int direction;
    protected int yDisplacement;
    public BasicGun(Actor source, boolean enemy) {
        super(source);
        bps = 3;
        velocity = 300;
        fired = false;
        if (enemy)
        {
            direction = 90;
            yDisplacement = source.getHeight();
        } else {
            direction = 270;
            yDisplacement = 0;
        }
    }
    
    @Override
    public void generateBullet(long shotProgress) {

            Bullet b = new BasicBullet(source.getWidth() / 2+source.getTrueX(), source.getTrueY()+yDisplacement, 14, 4, direction, velocity);
            Stage.getCurrentStage().addChild(b);
            b.update(shotProgress);
            fired = true;
    }
    
}
