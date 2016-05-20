package edu.roosevelt.vsshooter.bullet;

import android.graphics.Bitmap;
import edu.roosevelt.vsshooter.Actor;
import edu.roosevelt.vsshooter.Stage;
import edu.roosevelt.vsshooter.VectorActor;
import edu.roosevelt.vsshooter.ship.Ship;

public abstract class Bullet extends VectorActor {
    
    private int damage = 51;
    
    public Bullet(Bitmap spriteSheet, int x, int y, int width, int height, int frameColumns, int frameRows, boolean rotate) {
        super(spriteSheet, x, y, width, height, frameColumns, frameRows, rotate);
        // TODO Auto-generated constructor stub
        setZone(0, 400, 0, 300);
    }
    
    public void onAttach(Stage s)
    {
        super.onAttach(s);
        Stage.getCurrentStage().registerCollidingActor(this);
    }
    
    public void handleCollision(Actor a2)
    {
        if (a2 instanceof Ship)
        {
            destroy();
        }
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
