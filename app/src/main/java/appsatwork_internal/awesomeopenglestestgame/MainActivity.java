package appsatwork_internal.awesomeopenglestestgame;

import android.graphics.PointF;
import android.opengl.GLSurfaceView;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import Engine.OpenGLCanvas;
import Engine.OpenGLObjects.Geometry.Circle;
import Engine.OpenGLObjects.Geometry.OpenGLGeometry;
import Engine.OpenGLObjects.Geometry.RegularPolygon;
import Engine.OpenGLObjects.OpenGLColor;
import Engine.Util;


public class MainActivity extends ActionBarActivity {

    OpenGLCanvas gameCanvas;
    Circle circle;
    OpenGLColor color = new OpenGLColor(0.3f, 0.3f, 0.3f, 1.0f);

    List<OpenGLGeometry> geometryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        gameCanvas = new OpenGLCanvas(this);
        this.setContentView(gameCanvas);
        gameCanvas.setOnTouchListener(touchListener);

        for(int i = 10; i > 0; i--)
        {
            RegularPolygon polygon = gameCanvas.DrawRegularPolygon(new PointF(0,0), 0.5f, (2*i + 1), new OpenGLColor(1.0f, 1-((i-1) * 0.1f) - 0.1f,1.0f, 1.0f));
            geometryList.add(polygon);
        }
    }

    private View.OnTouchListener touchListener = new View.OnTouchListener()
    {
        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            PointF dist = gameCanvas.ScreenSpaceToWorldSpace(new PointF(event.getX(), event.getY()));
            for(OpenGLGeometry geometry : geometryList)
            {
                float mid = geometry.GetColor().G;
                geometry.SetColor(new OpenGLColor(dist.x, mid, dist.y, 1.0f));
            }
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
