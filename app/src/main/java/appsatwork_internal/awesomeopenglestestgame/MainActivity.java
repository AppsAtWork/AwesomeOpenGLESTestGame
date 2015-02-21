package appsatwork_internal.awesomeopenglestestgame;

import android.app.Activity;
import android.os.Bundle;


public class MainActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(new TestGame(this));
    }
}
