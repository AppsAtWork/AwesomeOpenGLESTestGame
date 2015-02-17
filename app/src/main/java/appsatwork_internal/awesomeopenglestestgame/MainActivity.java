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
import Engine.OpenGLObjects.Sprites.AtlasSprite;
import Engine.OpenGLObjects.Sprites.Texture;
import Engine.OpenGLObjects.Sprites.TextureAtlas;
import Engine.OpenGLObjects.Sprites.TextureManagement;
import Engine.OpenGLObjects.Sprites.TextureSprite;
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

        TextureAtlas texAtlas = TextureManagement.LoadAsTextureAtlas(getResources(), R.drawable.awesome_atlas, 256);

        AtlasSprite sprite = new AtlasSprite(texAtlas, 0, 0.0f, 0.0f, 0.3f, 0.3f);
        sprite.StartDrawing();
        AtlasSprite sprite2 = new AtlasSprite(texAtlas, 1, 0.0f, 0.0f, 0.3f, 0.3f);
        sprite2.StartDrawing();
        AtlasSprite sprite3 = new AtlasSprite(texAtlas, 2, 0.0f, 0.0f, 0.3f, 0.3f);
        sprite3.StartDrawing();
        AtlasSprite sprite4 = new AtlasSprite(texAtlas, 3, 0.0f, 0.0f, 0.3f, 0.3f);
        sprite4.StartDrawing();

        TextureAtlas atlas2 = TextureManagement.LoadAsTextureAtlas(getResources(), R.drawable.just_another_puzzle, 128);
        AtlasSprite spriteA = new AtlasSprite(atlas2, 0, -0.6f, 0.1f, 0.2f, 0.2f);
        spriteA.StartDrawing();
        AtlasSprite spriteB = new AtlasSprite(atlas2, 1, -0.1f, 0.3f, 0.2f, 0.2f);
        spriteB.StartDrawing();
        AtlasSprite spriteC = new AtlasSprite(atlas2, 2, 0.2f, 0.5f, 0.2f, 0.2f);
        spriteC.StartDrawing();
        AtlasSprite spriteD = new AtlasSprite(atlas2, 3, 0.1f, -0.2f, 0.2f, 0.2f);
        spriteD.StartDrawing();
        AtlasSprite spriteE = new AtlasSprite(atlas2, 4, 0.3f, 0.3f, 0.2f, 0.2f);
        spriteE.StartDrawing();
        AtlasSprite spriteF = new AtlasSprite(atlas2, 5, -0.4f, 0.1f, 0.2f, 0.2f);
        spriteF.StartDrawing();
        AtlasSprite spriteG = new AtlasSprite(atlas2, 6, 0.5f, -0.1f, 0.2f, 0.2f);
        spriteG.StartDrawing();
        AtlasSprite spriteH = new AtlasSprite(atlas2, 7, 0.5f, -0.4f, 0.2f, 0.2f);
        spriteH.StartDrawing();
        new AtlasSprite(atlas2, 8, -0.5f, -0.4f, 0.2f, 0.2f).StartDrawing();
        new AtlasSprite(atlas2, 9, 0.3f, -0.2f, 0.2f, 0.2f).StartDrawing();
        new AtlasSprite(atlas2, 10, 0.1f, 0.4f, 0.2f, 0.2f).StartDrawing();
        new AtlasSprite(atlas2, 11, 0.6f, -0.2f, 0.2f, 0.2f).StartDrawing();
        new AtlasSprite(atlas2, 12, 0.4f, -0.1f, 0.2f, 0.2f).StartDrawing();
        new AtlasSprite(atlas2, 13, 0.5f, 0.6f, 0.2f, 0.2f).StartDrawing();
        new AtlasSprite(atlas2, 14, 0.2f, -0.3f, 0.2f, 0.2f).StartDrawing();
        new AtlasSprite(atlas2, 15, 0.0f, 0.2f, 0.2f, 0.2f).StartDrawing();
        gameCanvas.DrawRegularPolygon(new  PointF(0.0f, 0.0f), 0.2f, 5, new OpenGLColor(0.7f, 0.2f, 0.2f, 0.7f));
        gameCanvas.DrawRegularPolygon(new  PointF(0.0f, 0.0f), 0.2f, 6, new OpenGLColor(0.4f, 0.8f, 0.4f, 0.6f));
        gameCanvas.DrawRegularPolygon(new  PointF(0.0f, 0.0f), 0.2f, 9, new OpenGLColor(0.6f, 0.3f, 0.1f, 0.8f));
        Texture dj = TextureManagement.LoadAsTexture(getResources(), R.drawable.dj);
        new TextureSprite(dj, 0.0f, 0.0f, 0.3f, 0.3f).StartDrawing();
        Texture hoofd = TextureManagement.LoadAsTexture(getResources(), R.drawable.npot);
        new TextureSprite(hoofd, 0.0f, 0.0f, 0.37f, 0.5f).StartDrawing();
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
            if(openGLObject != null)
            {            openGLObject.SetCenter(gameCanvas.ScreenSpaceToWorldSpace(new PointF(event.getX(), event.getY())));
                OpenGLObjectManager.MoveToFront(openGLObject);
                if(openGLObject.getClass() == TextureSprite.class)
                {
                    openGLObject.SetRotation(gameCanvas.ScreenSpaceToWorldSpace(new PointF(event.getX(), event.getY())).y * 180.0f);
                }
            openGLObject.ApplyTransformations(); }
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
