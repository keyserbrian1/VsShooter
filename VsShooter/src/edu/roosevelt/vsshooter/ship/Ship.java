package edu.roosevelt.vsshooter.ship;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import edu.roosevelt.vsshooter.Actor;
import edu.roosevelt.vsshooter.R;
import edu.roosevelt.vsshooter.Stage;
import edu.roosevelt.vsshooter.VsShooter;
import edu.roosevelt.vsshooter.bullet.Bullet;

public abstract class Ship extends Actor{
    
    boolean moved;
    protected double health = 255;
    
    public Ship(int x, int y, int width, int height) {
        super(BitmapFactory.decodeResource(VsShooter.getMain().getResources(), R.drawable.ship), x, y, width, height, 3,
                1);
        // TODO Auto-generated constructor stub
        moved = false;
        //hitbox = new RectF(43, 61, 51, 71);

        playAnimation(0, 2, true, 10);
        
    }
    
    public void onAttach(Stage s)
    {
        super.onAttach(s);
        s.registerCollidingActor(this);
        
        hitbox = new RectF(12, 16.5f, 13.5f, 18);
        
        Matrix ma = new Matrix();
        //ma.setScale(width/30, height/30, hitbox.centerX(), hitbox.centerY());
        ma.setScale(stage.getScaleParameter(), stage.getScaleParameter());
        ma.mapRect(hitbox);
    }
    
    public void onDraw() {
        super.onDraw();
        drawHealthBar();
    }
    
    public void handleCollision(Actor a2) {
        if (a2 instanceof Bullet) {
            takeDamage(((Bullet) a2).getDamage());
            setOpacity((int) (health / 2 + 128));
        }
        if (health < .01) {// account for floating point inaccuracy, 0<hp<.01 is rare enough that we don't need to
                           // consider it, they're going to die anyway.
            destroy();
        }
    }
    

    public void takeDamage(double damage) {
        this.health -= damage;
        health = Math.min(health, 255);//no overhealing
    }
    
    public abstract void drawHealthBar();
}
