package edu.roosevelt.vsshooter;

import android.graphics.Paint;

public abstract class HoldableButton extends Button {
    
    protected Paint tapForegroundPaint;
    volatile Paint activePaint;
    
    public HoldableButton(Paint paint, int x, int y, int width, int height) {
        super(paint, x, y, width, height);
        tapForegroundPaint = new Paint(foregroundPaint);
        activePaint = foregroundPaint;
    }
    
    public void onDraw() {
        super.onDraw();
        if (!active) {
            Stage.getCurrentStage().draw(getTrueX() + 2, getTrueY() + 2, getTrueX() + width - 2,
                    getTrueY() + height - 2, foregroundPaint, z - 3);
                    
        } else {
            Stage.getCurrentStage().draw(getTrueX() + 2, getTrueY() + 2, getTrueX() + width - 2,
                    getTrueY() + height - 2, tapForegroundPaint, z - 3);
        }
        for (int i = 0; i < toPrint.size(); i++) {
            textFont.getTextBounds(toPrint.get(i), 0, toPrint.get(i).length(), bounds);
            int textHeight = bounds.height();
            textHeight /= Stage.getCurrentStage().getScaleParameter();// scale to units used elsewhere
            int textWidth = bounds.width();
            textWidth /= Stage.getCurrentStage().getScaleParameter();// scale to units used elsewhere
            int textY = getTrueY() + (height - textHeight*(toPrint.size()-1)/2) / 2 + textHeight*i;
            int textX = getTrueX() + (width - textWidth) / 2;
            Stage.getCurrentStage().drawText(toPrint.get(i), textX, textY, textFont, z - 4);
        }
    }
    
    @Override
    protected void startTap() {
        activePaint = tapForegroundPaint;
    }
    
    @Override
    protected void cancelTap() {
        activePaint = foregroundPaint;
    }
    
    @Override
    protected void resumeTap() {
        activePaint = tapForegroundPaint;
    }
    
    @Override
    protected void endTap() {
        activePaint = foregroundPaint;
    }
    
}
