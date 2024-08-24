package edu.msu.moranti1.project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class BoardPiece {

    private Bitmap piece;

    private float x = 0.07f;
    private float y = 0.07f;
    private int width;
    private int height;

    private float finalX;
    private float finalY;

    public float getFinalY() {
        return finalY;
    }

    public float getFinalX() {
        return finalX;
    }

    private boolean filled = false;

    public boolean isFilled() {return filled;}
    public void setFilled(boolean fill) {filled = fill;}

    public int getWidth() {return width;}
    public int getHeight() {return height;}

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    private int id;

    private int player;

    public BoardPiece(Context context, int id) {
        this.id = id;
        piece = BitmapFactory.decodeResource(context.getResources(), id);
        this.width = piece.getWidth();
        this.height = piece.getHeight();
    }

    public void draw(Canvas canvas, int marginX, int marginY, float posX, float posY, int boardSize, float scaleFactor) {
        canvas.save();

        // Convert x,y to pixels and add the margin, then draw
        canvas.translate(marginX + x * boardSize, marginY + y * boardSize);

        // Scale it to the right size
        canvas.scale(scaleFactor, scaleFactor);

        // This magic code makes the center of the piece at 0, 0
        canvas.translate(-piece.getWidth() / 2f, -piece.getHeight() / 2f);

        // Draw the bitmap
        canvas.drawBitmap(piece, posX, posY, null);
        canvas.restore();

        //TODO Get the relative position of each piece
        finalX = getWidth()/2 + marginX + posX;
        finalY = getHeight()/2 + marginY + posY;

    }

     public int getPlayer(){
        return player;
    }

    public void setPlayer(int player){
        this.player = player;
    }

}
