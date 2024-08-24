package edu.msu.moranti1.project1;



import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


public class Board {

    GameActivity gameActivity = new GameActivity();

    /**
     * The current parameters
     */
    private Parameters params = new Parameters();

    private static class Parameters implements Serializable {

        public ArrayList<BoardPiece> getPieces() {
            return pieces;
        }

        public void setPieces(ArrayList<BoardPiece> pieces) {
            this.pieces = pieces;
        }

        public ArrayList<BoardPiece> pieces;
    }

     private int turn ;
    /**
     * The snap distance for a piece
     */
    final static float SNAP_DISTANCE = 0.05f;
    final static float SNAP_DISTANCE_Y = 0.8f;
    /**
     * Completed board Bitmap
     */

    private Bitmap boardComplete;

    /**
     * Paint for filling the area the puzzle is in
     */
    private final Paint fillPaint;

    /**
     * Left margin for pixels
     */
    private int marginX;

    /**
     * Top margin for pixels
     */

    private int marginY;

    /**
     * Board size in pixels
     */

    private int boardSize;


    /**
     * Percentage of the display width or height that
     * is occupied by the puzzle.
     */
    final static float SCALE_IN_VIEW = 0.9f;

    /**
     * How much we scale the puzzle pieces
     */
    private float scaleFactor;

    /**
     * row/column pair for last piece added
     */
    private int lastRow = 0;
    private int lastCol = 0;
    boolean validPlacement=false;
    private ArrayList<ArrayList<BoardPiece>> boardPieces = new ArrayList<>();
//    public ArrayList<GamePiece> gamePieces = new ArrayList<GamePiece>();
    private ArrayList<ArrayList<Final_XY>> positions = new ArrayList<>();
    private GamePiece dragging = null;
    private GamePiece piece = null;

    public ArrayList<BoardPiece> getPieces() {
        return params.pieces;
    }

    public void setPieces(ArrayList<BoardPiece> pieces) {
        params.pieces = pieces;
    }
    /**
     * Most recent relative X touch when dragging
     */
    private float lastRelX;

    /**
     * Most recent relative Y touch when dragging
     */
    private float lastRelY;

    private static String LOCATIONSX = "Board.locationsx";
    private static String LOCATIONSY = "Board.locationsy";
    private static String IDS = "Board.ids";
    private static String TURN = "turn";
    private static String FILLED = "filled";
    private static String PLAYERS = "players";
    private static String CURRENT = "currentPlayer";
    GameView view1;
    public void setView(GameView context) {
        this.view1 = context;
    }

    private boolean won = false;

    private Context currContext;


    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void setBoardPiece(boolean boardPiece) {
        this.boardPiece = boardPiece;
    }
    
    private boolean boardPiece = false;

    private boolean lastPlacementValid = false;

    ArrayList<GamePiece> gamePieces;

    /**
     * Indicates whether or not the disc has already been set
     */
    private boolean discSet = false;
    Context context;

    public Board(Context context) {
        this.context =context;

        gamePieces = gameActivity.getGamePieces();
        turn = gameActivity.put_turn(context);

        // Create paint for filling the area the puzzle will
        // be solved in.
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint.setColor(0xffcccccc);

        for (int i = 0; i < 6; i++){
            ArrayList<BoardPiece> temp = new ArrayList<>();
            for (int j = 0; j < 7; j++ ){
               temp.add(new BoardPiece(context, R.drawable.board_piece));
            }

            boardPieces.add(temp);
        }
        if (piece == null) {
            if (turn == 1) {
                piece = (new GamePiece(context, R.drawable.sparty_white, 0.259f, 0.238f));
            } else {
                piece = (new GamePiece(context, R.drawable.sparty_green, 0.259f, 0.238f));
            }
        }


    }

    public void draw(Canvas canvas) {

        int wid = canvas.getWidth();
        int hit = canvas.getHeight();

        // Determine the minimum of the two dimensions
        int minDim = wid < hit ? wid : hit;

        boardSize = (int) (minDim * SCALE_IN_VIEW);

        // Compute the margins so we center the board
        marginX = (wid - boardSize) / 2;
        marginY = (hit - boardSize) / 2;

        scaleFactor = (float) boardSize / (float) (boardPieces.get(0).get(0).getWidth()*7);


        int shiftX = 0;
        int shiftY = 0;
        for (int row = 0; row < boardPieces.size(); row++) {
            ArrayList<Final_XY> temp_positions_col = new ArrayList<>();
            for (BoardPiece board: boardPieces.get(row)){
                //canvas.drawBitmap(board, shiftX, shiftY, null);
                board.draw(canvas, marginX, marginY, shiftX, shiftY, boardSize, scaleFactor);
                Final_XY final_xy = new Final_XY();
                final_xy.setX(((board.getWidth()/2)+shiftX)*scaleFactor);
                final_xy.setY(((board.getHeight()/2)+shiftY)*scaleFactor);
                shiftX += board.getWidth();
                temp_positions_col.add(final_xy);
                //Log.e("board", "draw: ",board);
            }
            shiftY += boardPieces.get(row).get(0).getHeight();
            shiftX = 0;
            if (positions.size() < 6){
                positions.add(temp_positions_col);
            }
        }

        for(GamePiece piece : gamePieces) {
            piece.draw(canvas, marginX, marginY, boardSize, scaleFactor);
        }

    }
    private boolean onTouched(float x, float y, View view) {
        //Sets the location of the piece to where we clicked and draws it
        if(!validPlacement){
            if(gamePieces.size()>0){
                onUndo();
            }

        }
        if (dragging == null) {
            piece.setX(x);
            piece.setY(y);
            piece.setId(turn);
            gamePieces.add(piece);
            view.invalidate();
            dragging = piece;
            lastRelX = x;
            lastRelY = y;
            return true;
        }



        return false;
    }
    public boolean onTouchEvent(View view, MotionEvent event) {

        float relX = (event.getX() - marginX) / boardSize;
        float relY = (event.getY() - marginY) / boardSize;

        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:
                return onTouched(relX, relY, view);
            case MotionEvent.ACTION_MOVE:
                // If we are dragging, move the piece and force a redraw
                if(dragging != null) {
                    dragging.move(relX - lastRelX, relY - lastRelY);
                    lastRelX = relX;
                    lastRelY = relY;
                    view.invalidate();
                    return true;
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                return onReleased(view, relX, relY);

        }
        return false;
    }
    private boolean onReleased(View view, float x, float y) {
        if(dragging != null) {
             validPlacement = false;
            for (int i = 0; i < positions.size(); i++) {
                for (Final_XY pos: positions.get(i)) {
                    float testx = Math.abs(x - pos.getX()/boardSize);
                    float testy =  Math.abs(y - pos.getY()/boardSize);
                    if(testx < SNAP_DISTANCE &&
                            testy < SNAP_DISTANCE) {
                        Log.e("Snapped", "Snapped");
                        dragging.setX(pos.getX()/boardSize);
                        dragging.setY(pos.getY()/boardSize);
                        moveToLowest();
                        view.invalidate();
                        validPlacement = true;
                        break;
                    }
                }
            } if (!validPlacement) lastPlacementValid = false;
            else lastPlacementValid = true;
        }
        //dragging = null;
        return false;
    }

    /**
     * Moves the piece to the lowest available spot
     */
    public void moveToLowest() {
        boolean end = false;
        for (int row = boardPieces.size()-1; row >= 0; row--) {
            for (int col = 0; col < boardPieces.get(row).size(); col++) {
                if (!boardPieces.get(row).get(col).isFilled() && positions.get(row).get(col).getX()/boardSize == dragging.getX()) {
                    float newY = positions.get(row).get(col).getY()/boardSize;
                    dragging.setY(newY);
                    piece.setX(dragging.getX());
                    piece.setY(newY);
//                    boardPieces.get(row).get(col).setFilled(true);
                    end = true;
                    break;
                }
            }
            if (end) {
                break;
            }
        }
    }
    public void loadInstanceState(@NonNull Bundle bundle) {
        turn = bundle.getInt(TURN);
        gameActivity.checkForTurn(context,turn);

        float [] locationsx = bundle.getFloatArray(LOCATIONSX);
        float [] locationsy = bundle.getFloatArray(LOCATIONSY);
        int [] ids = bundle.getIntArray(IDS);
        boolean[] filled = bundle.getBooleanArray(FILLED);
        int[] players = bundle.getIntArray(PLAYERS);
        int curr_player = bundle.getInt(CURRENT);

        for(int i=0; i<locationsx.length; i++) {
            int id_value;
            if(ids[i]==1){
                id_value =  R.drawable.sparty_white;
            }
            else{
                id_value =  R.drawable.sparty_green;
            }
            GamePiece t = new GamePiece(context,id_value);
            t.setX(locationsx[i]);
            t.setY(locationsy[i]);
            t.setId(ids[i]);
            gamePieces.add(t);
        }
        if (gamePieces.size()> 0 && gamePieces.get(gamePieces.size()-1).getId() == curr_player) {
            piece = gamePieces.get(gamePieces.size()-1);
        }
        int row = -1;
        for (int i = 0; i < filled.length; i++) {
            if (i%7 == 0) {
                ++row;
            }
            boardPieces.get(row).get(i%7).setFilled(filled[i]);
            boardPieces.get(row).get(i%7).setPlayer(players[i]);
        }


    }
    public void saveInstanceState(Bundle bundle) {
        float[] locationsx = new float[gamePieces.size()];
        float[] locationsy = new float[gamePieces.size()];
        int[] ids = new int[gamePieces.size()];
        boolean[] filled = new boolean[boardPieces.size()*boardPieces.get(0).size()];
        int[] players = new int[boardPieces.size()*boardPieces.get(0).size()];
        int curr_player = piece.getId();


        for (int i = 0; i < gamePieces.size(); i++) {
            GamePiece piece = gamePieces.get(i);
            locationsx[i] = piece.getX();
            locationsy[i] = piece.getY();
            ids[i] = piece.getId();
        }
        int row = -1;
        for (int i = 0; i < boardPieces.size()*boardPieces.get(0).size(); i++) {
            if (i%7 == 0) {
                ++row;
            }
            filled[i] = boardPieces.get(row).get(i%7).isFilled();
            players[i] = boardPieces.get(row).get(i%7).getPlayer();
        }
        bundle.putFloatArray(LOCATIONSX, locationsx);
        bundle.putFloatArray(LOCATIONSY, locationsy);
        bundle.putIntArray(IDS, ids);
        bundle.putInt(TURN, turn);
        bundle.putBooleanArray(FILLED, filled);
        bundle.putIntArray(PLAYERS, players);
        bundle.putInt(CURRENT,curr_player);

    }

    /**
     * Switches between player one and player 2
     */
    public void switchPlayer(Context context){
        if (piece == null) {
            //Switches to player 2 and sets piece to player 2
            if (turn == 1) {
                gameActivity.checkForTurn(context,2);
                turn = 2;
                gameActivity.saveturn(context,turn);
//                gameActivity.setTurn(2);
                piece = (new GamePiece(context, R.drawable.sparty_green, 0.259f, 0.238f));
            } else {
                //Switches to player 1 and sets piece to player 1
                gameActivity.checkForTurn(context, 1);
                turn = 1;
                gameActivity.saveturn(context,turn);
//                gameActivity.setTurn(1);
                piece = (new GamePiece(context, R.drawable.sparty_white, 0.259f, 0.238f));
            }
        }
    }

    /**
     * Handles when Done button is pressed
     * @param context
     */
    public int onDone(Context context) {
        //Fills the right spot on the board
        if (!lastPlacementValid || dragging == null) {
            return 0;
        }
        for (int row = boardPieces.size()-1; row >= 0; row--) {
            boolean end = false;
            for (int col = 0; col < boardPieces.get(0).size(); col++) {
                if (!boardPieces.get(row).get(col).isFilled() &&
                        positions.get(row).get(col).getX()/boardSize == piece.getX() &&
                        positions.get(row).get(col).getY()/boardSize == piece.getY()){
                    boardPieces.get(row).get(col).setFilled(true);
                    boardPieces.get(row).get(col).setPlayer(turn);

                    // used to determine starting position for checkWin
                    lastRow = row;
                    lastCol = col;


                    end = true;
                    break;
                }
            }
            if (end) {
                break;
            }
        }

        if (checkWin()) {
            Log.i("win", "U win! Player: "+ turn);
            return turn;
        }
        piece = null;
        dragging = null;
        //switches players
        switchPlayer(context);
        return 0;
    }

    /**
     * Handles when undo button is clicked
     */
    public void onUndo() {
        if (gamePieces.size() > 0) {
            if (piece == gamePieces.get(gamePieces.size() - 1)) {
                gamePieces.remove(gamePieces.size() - 1);
                dragging = null;
            }
        }
    }

    /**
     * Using the last played piece as a basepoint, checks if a winner has been decided.
     * @return true if someone won, false if no winner yet
     */
    public boolean checkWin() {
        boolean win = false;
        BoardPiece base = boardPieces.get(lastRow).get(lastCol);
        //Log.i("checking win", "row: "+lastRow+" col: "+lastCol);
        int team = base.getPlayer();
        // four checks: L-R, T-B, D^R, D^L
        for (int check = 0; check < 4; check++) {
            int connected = 1;

            // LEFT TO RIGHT
            if (check == 0) {
                for (int i = 1; i < 4; i++) {
                    if (lastCol - i < 0) break;
                    if (boardPieces.get(lastRow).get(lastCol - i).getPlayer() == team) connected+=1;
                    else break;
                }
                for (int i = 1; i < 4; i++) {
                    if (lastCol + i > 6) break;
                    if (boardPieces.get(lastRow).get(lastCol + i).getPlayer() == team) connected+=1;
                    else break;
                }
                if (connected >= 4) {
                    Log.i("checkWin True", connected + " check: "+ check);
                    win = true;
                }
            }

            // TOP TO BOTTOM
            else if (check == 1) {
                for (int i = 1; i < 4; i++) {
                    if (lastRow - i < 0) break;
                    if (boardPieces.get(lastRow - i).get(lastCol).getPlayer() == team) connected+=1;
                    else break;
                }
                for (int i = 1; i < 4; i++) {
                    if (lastRow + i > 5) break;
                    if (boardPieces.get(lastRow + i).get(lastCol).getPlayer() == team) connected+=1;
                    else break;
                }
                if (connected >= 4) {
                    Log.i("checkWin True", connected + " check: " + check);
                    win = true;
                }
            }

            // DIAGONAL LOWER-LEFT TO UPPER-RIGHT
            else if (check == 2) {
                for (int i = 1; i < 4; i++) {
                    if (lastRow + i > 5) break;
                    if (lastCol - i < 0) break;
                    if (boardPieces.get(lastRow + i).get(lastCol - i).getPlayer() == team) connected+=1;
                    else break;
                }
                for (int i = 1; i < 4; i++) {
                    if (lastRow - i < 0) break;
                    if (lastCol + i > 6) break;
                    if (boardPieces.get(lastRow - i).get(lastCol + i).getPlayer() == team) connected+=1;
                    else break;
                }
                if (connected >= 4) {
                    Log.i("checkWin True", connected + " check: "+ check);
                    win = true;
                }
            }

            // DIAGONAL UPPER-LEFT TO LOWER-RIGHT
            else if (check == 3) {
                for (int i = 1; i < 4; i++) {
                    if (lastRow - i < 0) break;
                    if (lastCol - i < 0) break;
                    if (boardPieces.get(lastRow - i).get(lastCol - i).getPlayer() == team) connected+=1;
                    else break;
                }
                for (int i = 1; i < 4; i++) {
                    if (lastRow + i > 5) break;
                    if (lastCol + i > 6) break;
                    if (boardPieces.get(lastRow + i).get(lastCol + i).getPlayer() == team) connected+=1;
                    else break;
                }
                if (connected >= 4) {
                    Log.i("checkWin True", connected + " check: "+ check);
                    win = true;
                }
            }
        }
        return win;
    }


    public void resetGame() {
        for (int row = boardPieces.size()-1; row >= 0; row--) {
            for (int col = 0; col < boardPieces.get(row).size(); col++) {
                if (boardPieces.get(row).get(col).isFilled()) {
                    boardPieces.get(row).get(col).setFilled(false);
                }
            }
        }
        gamePieces.clear();
        positions.clear();
        dragging = null;
        //turn = 1;
    }
}
