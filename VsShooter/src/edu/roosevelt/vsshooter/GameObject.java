package edu.roosevelt.vsshooter;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;

public abstract class GameObject {
    
    protected Paint paint;
    
    public Stage stage;
    
    public GameObject(Bitmap sprite, int x, int y, int width, int height) {
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.parent = null;
        this.children = new ArrayList<GameObject>();
        this.waitingChildren = new ArrayList<GameObject>();
        this.z = 0;
        this.paint = null;
        
    }
    
    public GameObject(Paint paint, int x, int y, int width, int height) {
        this((Bitmap) null, x, y, width, height);
        this.paint = paint;
    }
    
    protected Bitmap sprite;
    protected double x; // offset from parent
    protected double y; // offset from parent
    protected int width;
    protected int height;
    protected ArrayList<GameObject> children;
    protected ArrayList<GameObject> waitingChildren;
    protected GameObject parent; // should have a parent if not Stage. Stage receives draw events from GameField, and
                                 // all others receive them from the parent.
    protected int z; // draw depth
    
    public void onDraw() {
        for (GameObject child : children) {
            child.onDraw();
        }
        if (sprite != null) {
            drawSprite();
        } else {
            if (paint != null) {
                Stage.getCurrentStage().draw(getTrueX(), getTrueY(), getTrueX() + width, getTrueY() + height, paint, z);
            }
            if (this instanceof Actor) {
                ((Actor) this).update(Stage.getCurrentStage().getInterval());
            }
        }
        addWaitingChildren();
    }
    
    private void addWaitingChildren() {
        for (GameObject child : waitingChildren) {
            this.realAddChild(child);
        }
        waitingChildren.clear();
    }
    
    public void drawSprite() {
        Rect source = new Rect(0, 0, sprite.getWidth(), sprite.getHeight()); // grab the whole image
        int targetX = (getTrueX() * Stage.getCurrentStage().width) / 300;
        int targetY = (getTrueY() * Stage.getCurrentStage().width) / 300;
        int targetXEnd = ((getTrueX() + width) * Stage.getCurrentStage().width) / 300;
        int targetYEnd = ((getTrueY() + height) * Stage.getCurrentStage().width) / 300;
        Rect dest = new Rect(targetX, targetY, targetXEnd, targetYEnd); // specify the target
        Stage.getCurrentStage().draw(sprite, source, dest, null, z);
    }
    
    public Bitmap getSprite() {
        return sprite;
    }
    
    public void setSprite(Bitmap sprite) {
        this.sprite = sprite;
    }
    
    public int getX() { // x offset from parent
        return (int) x;
    }
    
    public int getTrueX() // actual x position on screen
    {
        if (parent == null) {
            return (int) x;
        } else {
            return parent.getTrueX() + (int) x;
        }
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public int getY() { // y offset from parent
        return (int) y;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public int getTrueY() // actual y position on screen
    {
        if (parent == null) {
            return (int) y;
        } else {
            return parent.getTrueY() + (int) y;
        }
    }
    
    public int getWidth() {
        return width;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public ArrayList<GameObject> getChildren() {
        return children;
    }
    
    public void realAddChild(GameObject o) {
        if (o.parent != null) {
            o.parent.removeChild(o);
        }
        o.parent = this;
        children.add(o);
        o.onAttach(stage);
    }
    
    public void addChild(GameObject o) {
        waitingChildren.add(o);
    }
    
    public boolean removeChild(GameObject o) {
        return children.remove(o);
    }
    
    public GameObject getParent() {
        return parent;
    }
    
    public void onAttach(Stage s)
    {
        this.stage = s;
    }
    
}
