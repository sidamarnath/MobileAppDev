package edu.msu.moranti1.project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.Random;

public class GamePiece {
    private Bitmap piece;

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


    /**
     * x location.
     * We use relative x locations in the range 0-1 for the center
     * of the puzzle piece.
     */
    private float x = 0.5f;

    /**
     * y location
     */
    private float y = 0.25f;

    /**
     * x location when the puzzle is solved
     */
    private float finalX;

    /**
     * y location when the puzzle is solved
     */
    private float finalY;
    /**
     * We consider a piece to be in the right location if within
     * this distance.
     */

    final static float SNAP_DISTANCE = 0.05f;
    /**
     * The puzzle piece ID
     */
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    //    /**
//     * Replaced ID with team.
//     * A value of 1 indicates team 1.
//     * A value of 2 indicates team 2.
//     */
//    private int team;
//
//    public int getTeam() {
//        return team;
//    }
//
//    public GamePiece(Context context, int team) {
//        this.team = team;
//
//        if (team == 1) img = BitmapFactory.decodeResource(context.getResources(), R.drawable.sparty_white);
//        else if (team == 2) img = BitmapFactory.decodeResource(context.getResources(), R.drawable.sparty_green);
//    }
    public GamePiece(Context context, int id, float finalX, float finalY) {
        this.finalX = finalX;
        this.finalY = finalY;
        this.id = id;
//        if (player == 1) piece = BitmapFactory.decodeResource(context.getResources(), R.drawable.sparty_white);
//        else if (player == 2) piece = BitmapFactory.decodeResource(context.getResources(), R.drawable.sparty_green);
        piece = BitmapFactory.decodeResource(context.getResources(), id);
    }
    public GamePiece(Context context, int id) {
        piece = BitmapFactory.decodeResource(context.getResources(), id);
    }

    /**
     * Draw the puzzle piece
     * @param canvas Canvas we are drawing on
     * @param marginX Margin x value in pixels
     * @param marginY Margin y value in pixels
     * @param puzzleSize Size we draw the puzzle in pixels
     * @param scaleFactor Amount we scale the puzzle pieces when we draw them
     */
    public void draw(Canvas canvas, int marginX, int marginY, int puzzleSize, float scaleFactor) {
        canvas.save();

        // Convert x,y to pixels and add the margin, then draw
        canvas.translate(marginX + x * puzzleSize, marginY + y * puzzleSize);

        // Scale it to the right size
        canvas.scale(scaleFactor, scaleFactor);

        // This magic code makes the center of the piece at 0, 0
        canvas.translate(-piece.getWidth() / 2f, -piece.getHeight() / 2f);

        // Draw the bitmap
        canvas.drawBitmap(piece, 0, 0, null);
        canvas.restore();
    }
    /**
     * Test to see if we have touched a puzzle piece
     * @param testX X location as a normalized coordinate (0 to 1)
     * @param testY Y location as a normalized coordinate (0 to 1)
     * @param puzzleSize the size of the puzzle in pixels
     * @param scaleFactor the amount to scale a piece by
     * @return true if we hit the piece
     */
    public boolean hit(float testX, float testY,
                       int puzzleSize, float scaleFactor) {

        // Make relative to the location and size to the piece size
        int pX = (int)((testX - x) * puzzleSize / scaleFactor) +
                piece.getWidth() / 2;
        int pY = (int)((testY - y) * puzzleSize / scaleFactor) +
                piece.getHeight() / 2;

        if(pX < 0 || pX >= piece.getWidth() ||
                pY < 0 || pY >= piece.getHeight()) {
            return false;
        }

        // We are within the rectangle of the piece.
        // Are we touching actual picture?
        return (piece.getPixel(pX, pY) & 0xff000000) != 0;
    }
    /**
     * Move the puzzle piece by dx, dy
     * @param dx x amount to move
     * @param dy y amount to move
     */
    public void move(float dx, float dy) {
        x += dx;
        y += dy;
    }
    /**
     * If we are within SNAP_DISTANCE of the correct
     * answer, snap to the correct answer exactly.
     * @return
     */
    public boolean maybeSnap() {
        if(Math.abs(x - finalX) < SNAP_DISTANCE &&
                Math.abs(y - finalY) < SNAP_DISTANCE) {

            x = finalX;
            y = finalY;
            return true;
        }

        return false;
    }
    /**
     * Determine if this piece is snapped in place
     * @return true if snapped into place
     */
    public boolean isSnapped() {

        return maybeSnap();

    }
    /**
     * Shuffle the location of this piece
     * @param rand A random number generator
     */
    public void shuffle(Random rand) {
        x = rand.nextFloat();
        y = rand.nextFloat();
    }
}
