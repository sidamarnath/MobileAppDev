package edu.msu.amarnat4.puzzle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class PuzzleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_puzzle);

        if(bundle != null) {
            // We have saved state
            //PuzzleView view = (PuzzleView)this.findViewById(R.id.puzzleView);
            //view.loadInstanceState(bundle);
            getPuzzleView().loadInstanceState(bundle);
        }
    }

    /**
     * Save the instance state into a bundle
     * @param bundle the bundle to save into
     */
    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);

        //PuzzleView view = (PuzzleView)this.findViewById(R.id.puzzleView);
        //view.saveInstanceState(bundle);
        getPuzzleView().saveInstanceState(bundle);
    }

    /**
     * Get the puzzle view
     * @return PuzzleView reference
     */
    private PuzzleView getPuzzleView() {
        return (PuzzleView)this.findViewById(R.id.puzzleView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_puzzle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_shuffle:
                getPuzzleView().getPuzzle().shuffle();
                getPuzzleView().invalidate();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}