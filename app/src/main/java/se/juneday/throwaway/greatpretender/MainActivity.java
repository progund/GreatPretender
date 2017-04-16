package se.juneday.throwaway.greatpretender;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

  private static final String LOG_TAG = MainActivity.class.getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Log.d(LOG_TAG, "onCreate");
  }

  public void startClicked(View v) {
    Log.d(LOG_TAG, "startClicked");
    ((Button)findViewById(R.id.startButton)).setText(R.string.working_text);
    int period; // don't default to anything
    EditText et = ((EditText)(findViewById(R.id.work_peroid_input)));
    try {
      period = Integer.parseInt(et.getText().toString());
    } catch (NumberFormatException e) {
      Log.d(LOG_TAG, "Bad input, defaulting to 0");
      period = 0;
    }
    new PretendToWork().execute(period);
  }

  private class PretendToWork extends AsyncTask<Integer, Integer , Integer> {

    @Override
    protected Integer doInBackground(Integer... integers) {
      int period=integers[0];
      Log.d(LOG_TAG, "doInBackground: " + period);
      while (period-->0) {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        Log.d(LOG_TAG, "doInBackground: " + period + " seconds left");
        publishProgress(period);
      }
      Log.d(LOG_TAG, "doInBackground: leaving");
      return integers[0];
    }

    @Override
    protected void onPostExecute(Integer period) {
      super.onPostExecute(period);
      Log.d(LOG_TAG, "onPostExecute: " + period);
      ((Button)findViewById(R.id.startButton)).setText(R.string.start_text);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
      super.onProgressUpdate(values);
      int timeLeft = values[0];
      Log.d(LOG_TAG, "onPostExecute: " + timeLeft);
      ((Button)findViewById(R.id.startButton)).setText(getString(R.string.working_text) + " (" + timeLeft + " seconds left)" );
    }
  }

}
