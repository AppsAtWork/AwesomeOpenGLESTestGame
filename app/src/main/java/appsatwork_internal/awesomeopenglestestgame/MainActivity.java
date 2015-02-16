package appsatwork_internal.awesomeopenglestestgame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import Engine.OpenGLObjectManager;
import Engine.OpenGLObjects.Geometry.Circle;
import Engine.OpenGLObjects.Geometry.OpenGLGeometry;
import Engine.OpenGLObjects.Geometry.RegularPolygon;
import Engine.OpenGLObjects.OpenGLColor;
import Engine.OpenGLObjects.OpenGLObject;
import Engine.OpenGLObjects.Sprites.OpenGLSprite;
import Engine.OpenGLObjects.Sprites.TextureAtlas;
import Engine.OpenGLObjects.Sprites.TextureManagement;
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

        gameCanvas.DrawCircle(new PointF(0.0f, 0.0f), 0.2f, 0.5f, 0.5f, 0.5f, 0.5f);
/*
        for(int i = 10; i > 0; i--)
        {
            RegularPolygon polygon = gameCanvas.DrawRegularPolygon(new PointF(0,0), 0.5f, (2*i + 1), new OpenGLColor(1.0f, 1-((i-1) * 0.1f) - 0.1f,1.0f, 1.0f));
            geometryList.add(polygon);
        } */

        Bitmap atlasbmp = BitmapFactory.decodeResource(getResources(), R.drawable.awesome_atlas);
        TextureAtlas atlas = new TextureAtlas(256, atlasbmp);
        TextureManagement.AddTextureAtlas(atlas);

        OpenGLSprite sprite = new OpenGLSprite(atlas, 0, 0.0f, 0.0f, 0.5f, 0.5f);
        sprite.StartDrawing();
        OpenGLSprite sprite2 = new OpenGLSprite(atlas, 1, 0.0f, 0.0f, 0.5f, 0.5f);
        sprite2.StartDrawing();

        OpenGLSprite sprite3 = new OpenGLSprite(atlas, 2, 0.0f, 0.0f, 0.5f, 0.5f);
        sprite3.StartDrawing();
        OpenGLSprite sprite4 = new OpenGLSprite(atlas, 3, 0.0f, 0.0f, 0.5f, 0.5f);
        sprite4.StartDrawing();
    }

    private View.OnTouchListener touchListener = new View.OnTouchListener()
    {
        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
           /* PointF dist = gameCanvas.ScreenSpaceToWorldSpace(new PointF(event.getX(), event.getY()));
            for(OpenGLGeometry geometry : geometryList)
            {
                float mid = geometry.GetColor().G;
                geometry.SetColor(new OpenGLColor(dist.x, mid, dist.y, 1.0f));
            }*/
            OpenGLObject openGLObject = OpenGLObjectManager.FirstIntersection(gameCanvas.ScreenSpaceToWorldSpace(new PointF(event.getX(), event.getY())));
            openGLObject.SetCenter(gameCanvas.ScreenSpaceToWorldSpace(new PointF(event.getX(), event.getY())));
            openGLObject.ApplyTransformations();
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
