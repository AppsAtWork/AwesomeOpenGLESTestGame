package Engine.Objects;

import  android.graphics.PointF;
import Engine.Drawing.Drawers.Drawer;
import Engine.Drawing.DrawingListGenerators.DrawingList;

/**
 * Created by Casper on 11-2-2015.
 */
public abstract class OpenGLObject
{
    public DrawingList DrawingList;
    protected Drawer drawer;

    protected float[] vertices;
    protected float[] baseVertices;

    protected float scale = 1;
    protected float degrees = 0;
    protected float[] translation = new float[] {0,0};

    public abstract PointF Center();

    public void Draw(float[] projectionViewMatrix, int program)
    {
        drawer.Draw(projectionViewMatrix, program);
    }

    public void ApplyTransformations()
    {
        for(int i = 0; i < baseVertices.length; i+=3)
        {
            //Scale
            vertices[i] = baseVertices[i] * scale;
            vertices[i+1] = baseVertices[i+1] * scale;

            //Rotate
            float sin = (float)Math.sin(Math.toRadians(degrees));
            float cos = (float)Math.cos(Math.toRadians(degrees));

            float vert_i = vertices[i];
            vertices[i] = cos * vertices[i] - sin * vertices[i+1];
            vertices[i+1] = sin * vert_i + cos * vertices[i+1];

            //Translate
            vertices[i] += translation[0];
            vertices[i+1] += translation[1];

            //Set the third element to 0, we're not working in 3D.
            vertices[i+2] = 0.0f;
        }
        drawer.SetDirty();
    }

    public void SetScale(float factor) { scale = factor; }

    public void SetRotation(float degree) { degrees = degree; }

    public void SetCenter(PointF newCenter) { translation[0] = newCenter.x; translation[1] = newCenter.y; }

    public void TranslateBy(float deltaX, float deltaY) {translation[0] += deltaX; translation[1] += deltaY;}

    public void RotateBy(float degrees) { this.degrees += degrees; }

    public float[] GetVertices(){ return vertices; }

    public abstract float Intersects(PointF point);
}
