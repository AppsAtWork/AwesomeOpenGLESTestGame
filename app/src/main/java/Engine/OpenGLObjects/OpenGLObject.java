package Engine.OpenGLObjects;

import android.graphics.PointF;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Casper on 11-2-2015.
 */
public abstract class OpenGLObject
{
    protected float[] vertices;
    protected short[] drawingOrder = new short[] {0,1,2,0,2,3};

    protected FloatBuffer vertexBuffer;
    protected ShortBuffer drawListBuffer;

    protected float scale = 1;
    protected float degrees = 0;
    protected float[] translation = new float[] {0,0};

    public abstract void Draw(float[] projectionMatrix, int program);

    public abstract PointF GetCenter();

    public abstract void ApplyTransformations();

    public void SetScale(float factor) { scale = factor; }

    public void SetRotation(float degree) { degrees = degree; }

    public void SetCenter(PointF newCenter) { translation[0] = newCenter.x; translation[1] = newCenter.y; }

    protected abstract void UpdateVertexBuffer();

    protected abstract void UpdateDrawListBuffer();
}