package appsatwork_internal.awesomeopenglestestgame.Test;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import Engine.Gaming.Game;
import Engine.Objects.Drawables.BorderedShape;
import Engine.Objects.Drawables.CompositeDrawable;
import Engine.Objects.Drawables.IDrawable;
import Engine.Objects.TextureObjects.UVCoordProviders.VariableTextureAtlas;
import Engine.Objects.Transformables.Geometries.Border;
import Engine.Objects.Transformables.Geometries.Line;
import Engine.Objects.Transformables.Geometries.Rectangle;
import Engine.Objects.Transformables.Geometries.RegularPolygon;
import Engine.Objects.Drawables.Shape;
import Engine.Objects.Drawables.Sprite;
import Engine.Util.Color;
import appsatwork_internal.awesomeopenglestestgame.R;

/**
 * Created by Casper on 27-3-2015.
 */
public class TestGame extends Game
{
    private BorderedShape polygon;
    private Shape line;
    private boolean fingerDown;
    private PointF location;
    private Shape border;
    private CompositeDrawable rectangle;

    public TestGame(Context context, AttributeSet attrs) {

        super(context, attrs, 60);
        this.setOnTouchListener(new OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    fingerDown = true;
                else if(event.getAction() == MotionEvent.ACTION_UP)
                    fingerDown = false;

                location = new PointF(event.getX(), event.getY());

                return true;
            }
        });
    }

    @Override
    public void Initialize()
    {

    }

    @Override
    public void LoadContent()
    {
        polygon = this.Canvas.DrawWithBorder(new RegularPolygon(new PointF(0, 0), 0.1f, 5), 1, new Color(1.0f, 1.0f, 0.0f, 1.0f), new Color(0.8f, 0.3f, 0.4f, 1.0f));
        //line = new Shape(new Line(new PointF(0.0f, 0.0f), new PointF(0.5f, 0.5f), 3), new Color(1.0f, 1.0f, 1.0f, 1.0f));
        border = new Shape(new Border(new Rectangle(new PointF(0.0f, 0.0f), 0.05f, 0.05f), 1), new Color(0,1,1,1));
      //  rectangle = this.Canvas.DrawRectangle(new PointF(-0.1f, -0.1f), 0.05f, 0.03f, new Color(1.0f, 0.1f, 0.1f, 1.0f));
        ArrayList<IDrawable> drawables = new ArrayList<>();
        drawables.add(new Shape(new RegularPolygon(new PointF(0.1f, 0.1f), 0.2f, 50), new Color(1,0,1,1)));
        drawables.add(border);
        VariableTextureAtlas atlas = this.Canvas.LoadVariableTextureAtlas(R.drawable.sheet, R.raw.sheet_atlas);
        Sprite sprite = new Sprite(new Rectangle(new PointF(0.2f, 0.2f), 0.2f, 0.2f), atlas, 1);
        drawables.add(sprite);
        CompositeDrawable comp = new CompositeDrawable(drawables);
        ArrayList<IDrawable> drawables2 = new ArrayList<>();
        drawables2.add(comp);
        drawables2.add(new Shape(new Line(new PointF(0.0f, 0.0f), new PointF(0.5f, 0.5f), 2), new Color(1,0.5f, 0.5f,1.0f)));
        rectangle = new CompositeDrawable(drawables2);
        this.Canvas.DrawableList.Add(rectangle);
       // this.Canvas.DrawableList.Add(comp);
    }

    @Override
    public void Update()
    {
        if(fingerDown)
        {
            rectangle.GetTransformable().RotateBy(0.5f);
            rectangle.GetTransformable().SetScale(Math.abs(ScreenSpaceToWorldSpace(location).y * 2) + 1);
            rectangle.GetTransformable().SetTranslation(ScreenSpaceToWorldSpace(location));
            rectangle.GetTransformable().ApplyTransformations();
        }
    }
}
