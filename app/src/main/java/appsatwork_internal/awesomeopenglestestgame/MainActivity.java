package appsatwork_internal.awesomeopenglestestgame;

import android.graphics.PointF;
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
import Engine.OpenGLObjects.OpenGLColor;
import Engine.OpenGLObjects.OpenGLObject;
import Engine.OpenGLObjects.Sprites.FittingType;
import Engine.OpenGLObjects.Sprites.SpriteObjects.TextureSprite;
import Engine.OpenGLObjects.Sprites.TextureManagement;
import Engine.OpenGLObjects.Sprites.UVCoordProviders.Texture;
import Engine.OpenGLObjects.Sprites.UVCoordProviders.TextureAtlas;


public class MainActivity extends ActionBarActivity {

    OpenGLCanvas gameCanvas;
    int atlasIndex = 0;
    TextureAtlas atlas;

    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        gameCanvas = new OpenGLCanvas(this);
        this.setContentView(gameCanvas);
        gameCanvas.setOnTouchListener(touchListener);

        gameCanvas.DrawSprite(R.drawable.dj, new PointF(0,0), 0.5f, 0.5f, FittingType.Stretch);
        atlas = gameCanvas.LoadTextureAtlas(R.drawable.just_another_puzzle, 128);
    }

    private View.OnTouchListener touchListener = new View.OnTouchListener()
    {
        @Override
        public boolean onTouch(View v, MotionEvent event)
        {
            OpenGLObject openGLObject = OpenGLObjectManager.FirstIntersection(gameCanvas.ScreenSpaceToWorldSpace(new PointF(event.getX(), event.getY())));
            if(openGLObject != null)
            {
                openGLObject.SetCenter(gameCanvas.ScreenSpaceToWorldSpace(new PointF(event.getX(), event.getY())));
                OpenGLObjectManager.MoveToFront(openGLObject);
                openGLObject.ApplyTransformations();
            }
            else
            {
                gameCanvas.DrawSprite(R.drawable.npot, gameCanvas.ScreenSpaceToWorldSpace(new PointF(event.getX(), event.getY())), 0.3f, 0.4f, FittingType.Stretch );
                gameCanvas.DrawSprite(atlas, (atlasIndex++ % 16), gameCanvas.ScreenSpaceToWorldSpace(new PointF(event.getX(), event.getY())), 0.3f, 0.3f, FittingType.Stretch);

                return false;
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
