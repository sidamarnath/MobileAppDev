package edu.msu.moranti1.project1;



import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

public class GameView extends View {

    private Board board;

    private Parameters params = new Parameters();

    private float mScaleFactor = 1.F;

    private static class Parameters implements Serializable{
        public String player1Name;

        public String player2Name;

        public String currPlayerName;


    }

    public GameView(Context context) {
        super(context);
        init(null, 0);
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(attrs, 0);
    }

    public GameView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {
        board = new Board(getContext());
        board.setView(this);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return board.onTouchEvent(this, event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        board.draw(canvas);
    }
    public void saveInstanceState(Bundle bundle) {
        board.saveInstanceState(bundle);
    }
    public void loadInstanceState(Bundle bundles) {
        board.loadInstanceState(bundles);
    }

    public Board getBoard() {
        return board;
    }


}
