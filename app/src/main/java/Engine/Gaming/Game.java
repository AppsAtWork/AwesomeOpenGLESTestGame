package Engine.Gaming;

import android.content.Context;
import android.graphics.PointF;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import Engine.Drawing.OpenGLCanvas;
import Engine.Drawing.OpenGLRenderer;

/**
 * Created by Casper on 16-2-2015.
 */
public abstract class Game extends GLSurfaceView
{
    public long TimeStepMillis;
    public OpenGLCanvas Canvas;
    public OpenGLRenderer renderer;

    public Game(Context context, int FPS)
    {
        super(context);
        TimeStepMillis = (long)(1000.0f/(float)FPS);
        Canvas = new OpenGLCanvas(context);
        this.setEGLContextClientVersion(2);
        renderer = new OpenGLRenderer(context, this, Canvas.DrawableList);
        this.setRenderer(renderer);

        //Don't wait till dirty
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

        Init();
    }

    public Game(Context context, AttributeSet atrs, int FPS)
    {
        super(context, atrs);
        TimeStepMillis = (long)(1000.0f/(float)FPS);
        Canvas = new OpenGLCanvas(context);
        this.setEGLContextClientVersion(2);
        renderer = new OpenGLRenderer(context, this, Canvas.DrawableList);
        this.setRenderer(renderer);

        //Don't wait till dirty
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

        Init();
    }

    protected void Init()
    {
        Initialize();
        LoadContent();
    }

    public abstract void Initialize();

    public abstract void LoadContent();

    public abstract void Update();

    public PointF ScreenSpaceToWorldSpace(PointF point)
    {
        float x = point.x / (float)getWidth() * 2.0f - 1.0f;
        float y = point.y / (float)getHeight() * -2.0f + 1.0f;
        return renderer.ToWorldCoords(new PointF(x,y));
    }

    public void SetBackgroundColor(float r, float g, float b)
    {
        renderer.SetClearColor(r,g,b);
    }
}
