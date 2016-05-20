package edu.roosevelt.vsshooter;

import java.util.ArrayList;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public abstract class Button extends GameObject implements TouchListener {
    
    protected boolean active;
    protected boolean tapped;
    protected Paint textFont;
    protected int fontSize;
    protected ArrayList<String> toPrint;
    protected Paint foregroundPaint;
    Rect bounds;
    
    public Button(Paint paint, int x, int y, int width, int height) {
        super(paint, x, y, width, height);
        active = false;
        tapped = false;
        fontSize = 16;
        bounds = new Rect();
        toPrint = new ArrayList<String>();
        textFont = new Paint();
        this.foregroundPaint = new Paint();
    }
    
    public void onAttach(Stage s) {
        super.onAttach(s);
        setText("Button", 12, Color.BLACK);
        s.registerTouchListener(this);
    }
    
    public void onDraw() {
        super.onDraw();
        Stage.getCurrentStage().draw(getTrueX() + 2, getTrueY() + 2, getTrueX() + width - 2, getTrueY() + height - 2,
                foregroundPaint, z - 1);
        for (int i = 0; i < toPrint.size(); i++) {
            textFont.getTextBounds(toPrint.get(i), 0, toPrint.get(i).length(), bounds);
            int textHeight = bounds.height();
            textHeight /= Stage.getCurrentStage().getScaleParameter();// scale to units used elsewhere
            int textWidth = bounds.width();
            textWidth /= Stage.getCurrentStage().getScaleParameter();// scale to units used elsewhere

            int textY = getTrueY() + (height - textHeight*(toPrint.size()-1)/2) / 2 + textHeight*i;
            int textX = getTrueX() + (width - textWidth) / 2;
            Stage.getCurrentStage().drawText(toPrint.get(i), textX, textY, textFont, z - 2);
        }
    }
    
    public void setBackgroundPaint(Paint paint) {
        this.paint = paint;
    }
    
    public Paint getForegroundPaint() {
        return foregroundPaint;
    }
    
    public void setForegroundPaint(Paint foregroundPaint) {
        this.foregroundPaint = foregroundPaint;
    }
    
    public void setText(String s) {
        setText(s, fontSize, textFont.getColor());
    }
    
    public void setText(String s, int fontSize, int color) {
        toPrint.clear();
        while (!s.isEmpty()) {
            int breakpoint = s.indexOf("\n");
            if (breakpoint == -1) {
                breakpoint = s.length();
            }
            int breakOn = textFont.breakText(s.toCharArray(), 0, breakpoint, width * stage.getScaleParameter(), null);
            if (breakOn == breakpoint) {
                toPrint.add(s.substring(0, breakOn));
                if (breakOn != s.length()) {
                    s = s.substring(breakOn + 1);
                } else {
                    s = s.substring(breakOn);
                }
            } else {
                breakpoint = s.lastIndexOf(" ", breakOn);
                if (breakpoint != -1) {
                    toPrint.add(s.substring(0, breakpoint));
                    s = s.substring(breakpoint + 1);
                } else {
                    breakpoint = s.indexOf(" ", breakOn);
                    if (breakpoint != -1) {
                        toPrint.add(s.substring(0, breakpoint));
                        s = s.substring(breakpoint + 1);
                    } else {
                        toPrint.add(s);
                        s = "";
                    }
                }
            }
        }
        float scale = VsShooter.getContext().getResources().getDisplayMetrics().density;
        float trueFontSize = (fontSize * scale + 0.5f);
        textFont.setTextSize(trueFontSize);
        this.fontSize = fontSize;
        
        textFont.setColor(color);
    }
    
    @Override
    public void handleTouchStart(double x, double y) {
        if (getTrueX() < x && x < getTrueX() + width && getTrueY() < y && y < getTrueY() + height) {
            startTap();
            active = true;
            tapped = true;
        }
    }
    
    @Override
    public void handleTouchMove(double x, double y) {
        if (tapped) {
            boolean onButton = getTrueX() < x && x < getTrueX() + width && getTrueY() < y && y < getTrueY() + height;
            if (!active && onButton) {
                resumeTap();
                active = true;
            } else if (active && !onButton) {
                cancelTap();
                active = false;
            }
        }
        
    }
    
    @Override
    public void handleTouchEnd() {
        // TODO Auto-generated method stub
        if (active) {
            endTap();
        }
        active = false;
        tapped = false;
    }
    
    protected abstract void startTap();
    
    protected abstract void cancelTap();
    
    protected abstract void resumeTap();
    
    protected abstract void endTap();
}
