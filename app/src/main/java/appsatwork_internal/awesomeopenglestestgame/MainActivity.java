package appsatwork_internal.awesomeopenglestestgame;

import android.graphics.PointF;
import android.opengl.GLSurfaceView;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import Engine.OpenGLCanvas;
import Engine.OpenGLObjects.Geometry.Circle;
import Engine.Util;


public class MainActivity extends ActionBarActivity {

    OpenGLCanvas gameCanvas;
    Circle circle;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        gameCanvas = new OpenGLCanvas(this);
        this.setContentView(gameCanvas);
        gameCanvas.setOnTouchListener(touchListener);
        circle = gameCanvas.DrawCircle(new PointF(0,0), 0.5f, 0.3f, 0.3f, 0.3f, 1.0f);
    }

    private View.OnTouchListener touchListener = new View.OnTouchListener()
    {
        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            PointF dist = gameCanvas.ScreenSpaceToWorldSpace(new PointF(event.getX(), event.getY()));

            circle.SetColor(dist.x, 0.8f, dist.y, 1.0f);
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.asdf, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
