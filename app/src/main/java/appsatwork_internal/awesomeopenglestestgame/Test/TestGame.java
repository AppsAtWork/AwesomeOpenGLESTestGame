package appsatwork_internal.awesomeopenglestestgame.Test;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.appsatwork.ezgles.Gaming.Game;
import com.appsatwork.ezgles.Objects.Drawables.BorderedShapeDrawable;
import com.appsatwork.ezgles.Objects.Drawables.CompositeDrawable;
import com.appsatwork.ezgles.Objects.Drawables.IDrawable;
import com.appsatwork.ezgles.Objects.Drawables.ShapeDrawable;
import com.appsatwork.ezgles.Objects.Drawables.SpriteDrawable;
import com.appsatwork.ezgles.Objects.TextureObjects.UVCoordProviders.VariableTextureAtlas;
import com.appsatwork.ezgles.Objects.Transformables.Geometries.Border;
import com.appsatwork.ezgles.Objects.Transformables.Geometries.Line;
import com.appsatwork.ezgles.Objects.Transformables.Geometries.Rectangle;
import com.appsatwork.ezgles.Objects.Transformables.Geometries.RegularPolygon;
import com.appsatwork.ezgles.Util.Color;

import java.util.ArrayList;

import appsatwork_internal.awesomeopenglestestgame.R;

/**
 * Created by Casper on 27-3-2015.
 */
public class TestGame extends Game
{
    private BorderedShapeDrawable polygon;
    private ShapeDrawable line;
    private boolean fingerDown;
    private PointF location;
    private ShapeDrawable border;
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
        border = new ShapeDrawable(new Border(new Rectangle(new PointF(0.0f, 0.0f), 0.05f, 0.05f), 1), new Color(0,1,1,1));
      //  rectangle = this.Canvas.DrawRectangle(new PointF(-0.1f, -0.1f), 0.05f, 0.03f, new Color(1.0f, 0.1f, 0.1f, 1.0f));
        ArrayList<IDrawable> drawables = new ArrayList<>();
        drawables.add(new ShapeDrawable(new RegularPolygon(new PointF(0.1f, 0.1f), 0.2f, 50), new Color(1,0,1,1)));
        drawables.add(border);
        VariableTextureAtlas atlas = this.Canvas.LoadVariableTextureAtlas(R.drawable.sheet, R.raw.sheet_atlas);
        SpriteDrawable spriteDrawable = new SpriteDrawable(new Rectangle(new PointF(0.2f, 0.2f), 0.2f, 0.2f), atlas, 1);
        drawables.add(spriteDrawable);
        CompositeDrawable comp = new CompositeDrawable(drawables);
        ArrayList<IDrawable> drawables2 = new ArrayList<>();
        drawables2.add(comp);
        drawables2.add(new ShapeDrawable(new Line(new PointF(0.0f, 0.0f), new PointF(0.5f, 0.5f), 2), new Color(1,0.5f, 0.5f,1.0f)));
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
