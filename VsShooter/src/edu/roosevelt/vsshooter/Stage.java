package edu.roosevelt.vsshooter;

import java.util.ArrayList;
import java.util.PriorityQueue;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.Display;
import android.view.MotionEvent;

public class Stage extends GameObject {// Stages hold everything. 
    
    private static Stage currentStage;
    
    private long time;
    private long newTime;
    
    private GameField field;
    
    private ArrayList<Actor> collisionListeners;
    private ArrayList<TouchListener> touchListeners;
    private PriorityQueue<DrawRequest> toDraw;
    
    private int frameLock = 0; // Debugging code. Fixes the time passed between frames at set value if >0.

    private ArrayList<TouchListener> waitingTouchListeners;

    private ArrayList<TouchListener> waitingRemoveTouchListeners;
    static boolean shouldPause = false;
    
    public Stage(GameField field) {
        super((Paint) null, 0, 0, 0, 0);
        field.setStage(this);
        time = System.currentTimeMillis();
        newTime = System.currentTimeMillis();
        collisionListeners = new ArrayList<Actor>();
        touchListeners = new ArrayList<TouchListener>();
        waitingTouchListeners = new ArrayList<TouchListener>();
        waitingRemoveTouchListeners = new ArrayList<TouchListener>();
        toDraw = new PriorityQueue<DrawRequest>();
        stage = this;
    }
    
    public static Stage getCurrentStage() {
        return currentStage;
    }
    
    public static void setCurrentStage(Stage newStage) {
        
        Stage.currentStage = newStage;
        newStage.newTime = System.currentTimeMillis();
    }
    
    public void realAddChild(GameObject o)
    {
        super.realAddChild(o);
    }
    
    public int getHeight() {
        Display display = VsShooter.getMain().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return 300 * size.y / size.x;
    }
    
    public int getWidth() {
        return 300;// using fixed 300px width, doing math to adjust.
    }
    
    public void onDraw(Canvas c) {
        time = newTime;
        newTime = System.currentTimeMillis();
        super.onDraw();
        while (!toDraw.isEmpty()) {
            toDraw.poll().draw(c);
        }
        for (Actor a : Actor.destroyedList) {
            a.trueDestroy();
        }
        Actor.destroyedList.clear();
        checkCollisions();
    }
    
    public double getFps() {
        return 1000.0 / (newTime - time);
    }
    
    public long getInterval() {
        if (frameLock > 0) {
            return frameLock;
        }
        return newTime - time;
    }
    
    public int getTrueWidth() {
        Display display = VsShooter.getMain().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }
    
    public int getTrueHeight() {
        Display display = VsShooter.getMain().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }
    
    public float getScaleParameter() { 
        return ((float)getTrueWidth()) / getWidth();
    }
    
    public GameField getField() {
        return field;
    }
    
    public void registerCollidingActor(Actor a) {
        collisionListeners.add(a);
    }
    
    public void unregisterCollidingActor(Actor a) {
        collisionListeners.remove(a);
    }
    
    public void realRegisterTouchListener(TouchListener a) {
        touchListeners.add(a);
    }
    
    public void realUnregisterTouchListener(TouchListener a) {
        touchListeners.remove(a);
    }
    
    public void registerTouchListener(TouchListener a) {
        waitingTouchListeners.add(a);
    }
    
    public void unregisterTouchListener(TouchListener a) {
        waitingRemoveTouchListeners.add(a);
    }
    
    public void checkCollisions() {
        for (Actor a : collisionListeners) {
            for (Actor a2 : collisionListeners) {
                
                if (a != a2 && RectF.intersects(a.hitboxTrue, a2.hitboxTrue)) {
                    a.handleCollision(a2);
                }
            }
        }
    }
    
    public void handleTouch(MotionEvent event) {
        Display display = VsShooter.getMain().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        for(TouchListener t : waitingRemoveTouchListeners)
        {
            touchListeners.remove(t);
        }
        for(TouchListener t : waitingTouchListeners)
        {
            touchListeners.add(t);
        }
        waitingTouchListeners.clear();
        waitingTouchListeners.clear();
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {// If it was a tap
            double touchX = event.getX() * 300.0 / size.x;
            double touchY = event.getY() * 300.0 / size.x;
            for (TouchListener t : touchListeners) {
                t.handleTouchStart(touchX, touchY);
            }
        } else if (event.getActionMasked() == MotionEvent.ACTION_UP) {
            for (TouchListener t : touchListeners) {
                t.handleTouchEnd();
            }
        } else if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
            double touchX = event.getX() * 300.0 / size.x;
            double touchY = event.getY() * 300.0 / size.x;
            for (TouchListener t : touchListeners) {
                t.handleTouchMove(touchX, touchY);
            }
        }
    }
    
    public void draw(Bitmap sprite, Matrix location, Paint p, int z) {
        toDraw.add(new BitmapMatrixDrawRequest(sprite, location, p, z));
    }
    
    public void draw(Bitmap sprite, Rect source, Rect dest, Paint p, int z) {
        toDraw.add(new BitmapDrawRequest(sprite, source, dest, p, z));
    }
    
    public void draw(float left, float top, float right, float bottom, Paint paint, int z) {
        toDraw.add(new RectangleDrawRequest(left, top, right, bottom, paint, z));
    }
    
    public void draw(RectF location, Paint paint, int z) {
        toDraw.add(new RectangleDrawRequest(location, paint, z));
    }
    
    public void drawText(String s, float x, float y, Paint p, int z) {
        toDraw.add(new TextDrawRequest(s, x, y, p, z));
    }
    
    public static abstract class DrawRequest implements Comparable<DrawRequest> {
        DrawRequest(int z) {
            this.z = z;
        }
        
        int z;
        
        public int compareTo(DrawRequest another) {
            return another.z - this.z;
        }
        
        public abstract void draw(Canvas c);
    }
    
    public class TextDrawRequest extends DrawRequest {
        
        String s;
        float x;
        float y;
        Paint p;
        
        public TextDrawRequest(String s, float x, float y, Paint p, int z) {
            super(z);
            this.s = s;
            this.x = x;
            this.y = y;
            this.p = p;
        }
        
        @Override
        public void draw(Canvas c) {
            float scaleFactor = Stage.getCurrentStage().getScaleParameter();
            c.drawText(s, scaleFactor * x, scaleFactor * y, p);
        }
        
    }
    
    public static class BitmapMatrixDrawRequest extends DrawRequest {
        Bitmap sprite;
        Matrix location;
        Paint p;
        
        public BitmapMatrixDrawRequest(Bitmap sprite, Matrix location, Paint p, int z) {
            super(z);
            this.sprite = sprite;
            this.location = location;
            this.p = p;
        }
        
        public void draw(Canvas c) {
            c.drawBitmap(sprite, location, p);
        }
        
    }
    
    public static class BitmapDrawRequest extends DrawRequest {
        private Bitmap sprite;
        private Rect source;
        private Rect dest;
        private Paint p;
        
        public BitmapDrawRequest(Bitmap sprite, Rect source, Rect dest, Paint p, int z) {
            super(z);
            this.sprite = sprite;
            this.source = source;
            this.dest = dest;
            this.p = p;
        }
        
        public void draw(Canvas c) {
            c.drawBitmap(sprite, source, dest, p);
        }
    }
    
    public static class RectangleDrawRequest extends DrawRequest {
        
        private float left;
        private float top;
        private float right;
        private float bottom;
        private Paint paint;
        
        public RectangleDrawRequest(float left, float top, float right, float bottom, Paint paint, int z) {
            super(z);
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
            this.paint = paint;
        }
        
        public RectangleDrawRequest(RectF location, Paint paint, int z) {
            super(z);
            this.left = location.left;
            this.top = location.top;
            this.right = location.right;
            this.bottom = location.bottom;
            this.paint = paint;
        }
        
        public void draw(Canvas c) {
            float scaleFactor = Stage.getCurrentStage().getScaleParameter();
            c.drawRect(left * scaleFactor, top * scaleFactor, right * scaleFactor, bottom * scaleFactor, paint);
        }
        
    }
    
}
