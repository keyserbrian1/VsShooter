package edu.roosevelt.vsshooter;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

public abstract class Actor extends GameObject {

    
    protected int frameHeight;
    protected int frameWidth;
    protected long frameProgress;
    protected int animFPS;
    protected Bitmap spriteFrame;
    
    protected double xVel;
    protected double yVel;
    
    protected int currentFrame;
    protected int startFrame;
    protected int endFrame;
    protected boolean loop;
    
    protected RectF hitbox;
    protected RectF hitboxTrue;
    
    public static final int TOP_EDGE = 1;// Bitmasks for the edge collisions.
    public static final int BOTTOM_EDGE = 2;
    public static final int LEFT_EDGE = 4;
    public static final int RIGHT_EDGE = 8;
    
    protected int zoneTop;
    protected int zoneBottom;
    protected int zoneLeft;
    protected int zoneRight;
    protected boolean relativeZone;
    protected boolean permitPartialCrossing;
    
    protected Matrix rotation;
    protected Matrix scale;
    protected int[] frames;
    
    protected Paint hitboxPaint;
    
    protected boolean destroyed;
    protected boolean trueDestroyed;
    static ArrayList<Actor> destroyedList = new ArrayList<Actor>();
    
    protected double xDisplacement;
    protected double yDisplacement;
    protected int opacity;
    private boolean finished;
    
    public Actor(Bitmap spriteSheet, int x, int y, int width, int height, int frameColumns, int frameRows) {
        super(spriteSheet, x, y, width, height);
        if (spriteSheet != null) {
            frameHeight = spriteSheet.getHeight() / frameRows;
            frameWidth = spriteSheet.getWidth() / frameColumns;
        } else {
            frameHeight = 1;
            frameWidth = 1;
        }
        xVel = 0;
        yVel = 0;
        permitPartialCrossing = false;
        relativeZone = false;
        hitbox = new RectF(0, 0, frameWidth, frameHeight);
        hitboxTrue = new RectF();
        hitboxPaint = new Paint();
        hitboxPaint.setColor(Color.WHITE);
        hitboxPaint.setAlpha(0);
        rotation = new Matrix();
        playAnimation(0, 0, false, 1);
        destroyed = false;
        trueDestroyed = false;
        opacity = 255;
    }
    
    public void onAttach(Stage s)
    {
        super.onAttach(s);
        zoneTop = 0;
        zoneBottom = s.getHeight();
        zoneLeft = 0;
        zoneRight = s.getWidth();

        scale = new Matrix();
        float scaleParameterX = Stage.getCurrentStage().getScaleParameter()
                * ((float) width) / frameWidth;
        float scaleParameterY = Stage.getCurrentStage().getScaleParameter()
                * ((float) height) / frameHeight;
        scale.setScale(scaleParameterX, scaleParameterY);
    }
    
    public void setZone(int top, int bottom, int left, int right, boolean relative, boolean permitPartialCrossing) {
        zoneTop = top;
        zoneBottom = bottom;
        zoneLeft = left;
        zoneRight = right;
        this.relativeZone = relative;
        this.permitPartialCrossing = permitPartialCrossing;
    }
    
    public void setZone(int top, int bottom, int left, int right) {
        setZone(top, bottom, left, right, false, false);
    }
    
    public void setZone(int top, int bottom, int left, int right, boolean relative) {
        setZone(top, bottom, left, right, relative, false);
    }
    
    public int[] getSpritePosition(int frame) {
        int columns = sprite.getWidth() / frameWidth;
        int[] pos = new int[2];
        pos[0] = (frame % columns) * frameWidth; // get start x position by calculating column, then multiplying by
                                                 // width.
        pos[1] = (frame / columns) * frameHeight; // get start y position by calculating row, then multiplying by
                                                  // height.
        return pos;
    }
    
    public void drawSprite() {
        int[] target = getSpritePosition(frames[currentFrame]); // identify the frame we want
        spriteFrame = Bitmap.createBitmap(sprite, target[0], target[1], frameWidth, frameHeight);
        int targetX = (int)(getTargetX() * Stage.getCurrentStage().getScaleParameter());
        int targetY = (int)(getTargetY() * Stage.getCurrentStage().getScaleParameter());
        
        Matrix transform = new Matrix(rotation);
        transform.postConcat(scale);
        transform.postTranslate(targetX, targetY);
        Matrix hit = new Matrix();
        float scaleFactorX = ((float)width)/frameWidth;
        float scaleFactorY = ((float)height)/frameHeight;
        hit.set(rotation);
        hit.postScale(scaleFactorX, scaleFactorY);
        hit.postTranslate(getTargetX(), getTargetY());
        
        // hit.mapRect(hitboxTrue, hitbox);
        hit.mapRect(hitboxTrue, hitbox);
        Paint p = new Paint();
        p.setAlpha(opacity);
        
        Stage.getCurrentStage().draw(spriteFrame, transform, p, z);
        Stage.getCurrentStage().draw(hitboxTrue, hitboxPaint, z - 1);
        // p.setColor(Color.WHITE);
        // RectF r = new RectF(0, 0, width, height);
        // hit.mapRect(r);
        // Stage.getCurrentStage().draw(r, p, z-1);
        updateFrame();
        update(Stage.getCurrentStage().getInterval());
    }
    
    public void setHitboxColor(int color) {
        hitboxPaint.setColor(color);
    }
    
    public void setHitboxOpacity(int alpha) {
        hitboxPaint.setAlpha(alpha);
    }
    
    public void update(long milliSeconds) {
        if (trueDestroyed) {
            Log.wtf("Actor", "Destroyed Object Updated");
        }
        updateVelocity(milliSeconds);
        x += xVel * milliSeconds / 1000;
        y += yVel * milliSeconds / 1000;
        int zoneMask = 0;
        int relevantX = (relativeZone ? getX() : getTargetX());
        int relevantY = (relativeZone ? getY() : getTargetY());
        if (relevantX < zoneLeft - (permitPartialCrossing ? width : 0)) {
            zoneMask += LEFT_EDGE;
            
        }
        if (relevantX > zoneRight - (permitPartialCrossing ? 0 : width)) {
            zoneMask += RIGHT_EDGE;
            
        }
        if (relevantY < zoneTop - (permitPartialCrossing ? height : 0)) {
            zoneMask += TOP_EDGE;
            
        }
        if (relevantY > zoneBottom - (permitPartialCrossing ? 0 : height)) {
            zoneMask += BOTTOM_EDGE;
            
        }
        if (zoneMask != 0) {
            handleZoneExit(zoneMask);
        }
    }
    
    public void handleZoneExit(int zoneMask) {
        this.destroy(); // Destroy on leaving zone, probably should override in subclasses.
    }
    
    public void updateVelocity(long milliSeconds) {
        // Do nothing, future subclasses should override
    }
    
    public void updateFrame() {
        long progress = Stage.getCurrentStage().getInterval();
        frameProgress += progress;
        while (frameProgress > 1000 / animFPS) {
            frameProgress -= 1000 / animFPS;
            advanceFrame();
        }
    }
    
    public void advanceFrame() {
        
        if (currentFrame == frames.length - 1) // If the animation is finished
        {
            if (loop) // if it's looping, then restart. Otherwise, stay on the final frame.
            {
                currentFrame = startFrame;
            } else if (!finished)
                {
                    animFinish();
                    finished = true;
                }
        } else { // not finished, so continue.
            currentFrame++;
        }
    }
    
    protected void animFinish() {
        // FOR SUBclasses
        
    }

    public void playAnimation(int startFrame, int endFrame, boolean loop, int fps) {
        frames = new int[endFrame - startFrame + 1];
        for (int i = 0; i <= endFrame - startFrame; i++) {
            frames[i] = i;
        }
        playAnimation(frames, loop, fps);
    }
    
    public void playAnimation(int[] frames, boolean loop, int fps) {
        this.currentFrame = 0;
        this.loop = loop;
        this.frameProgress = 0;
        this.animFPS = fps;
        this.frames = frames;
        finished = false;
    }
    
    public void trueDestroy() // subclasses that care about being destroyed should override this, and this should be
                              // called
    // whenever the object is destroyed, except by destruction of stage.
    {
        if (parent != null) {
            parent.removeChild(this);
        }
        Stage.getCurrentStage().unregisterCollidingActor(this);
        if (this instanceof TouchListener) {
            Stage.getCurrentStage().unregisterTouchListener((TouchListener) this);
        }
        trueDestroyed = true;
    }
    
    public void destroy() {
        destroyed = true;
        destroyedList.add(this);
    }
    
    public void setHeight(int height) {
        this.height = height;
        float scaleParameterX = Stage.getCurrentStage().getScaleParameter()
                * ((float) width) / frameWidth;
        float scaleParameterY = Stage.getCurrentStage().getScaleParameter()
                * ((float) height) / frameHeight;
        scale.setScale(scaleParameterX, scaleParameterY);
    }
    
    public void setWidth(int width) {
        this.width = width;
        float scaleParameterX = Stage.getCurrentStage().getScaleParameter()
                * ((float) width) / frameWidth;
        float scaleParameterY = Stage.getCurrentStage().getScaleParameter()
                * ((float) height) / frameHeight;
        scale.setScale(scaleParameterX, scaleParameterY);
    }
    
    protected int getTargetX() {
        return getTrueX() + (int) xDisplacement;
    }
    
    protected int getTargetY() {
        return getTrueY() + (int) yDisplacement;
    }
    
    public void handleCollision(Actor a2) { // subclasses should implement if they care about collisions.
    
    }
    
    public int getOpacity() {
        return opacity;
    }
    
    public void setOpacity(int opacity) {
        this.opacity = opacity;
    }
    
}
