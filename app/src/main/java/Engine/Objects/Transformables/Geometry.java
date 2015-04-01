package Engine.Objects.Transformables;

import android.graphics.PointF;

/**
 * Created by Casper on 11-2-2015.
 */
public abstract class Geometry implements ITransform
{
    protected float[] vertices;
    protected float[] baseVertices;

    protected float scale = 1;
    protected float degrees = 0;
    protected float[] translation = new float[] {0,0};

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
    }

    public float GetScale() { return scale; }
    public void SetScale(float factor) { scale = factor; }
    public void ScaleBy(float factor) { scale *= factor; }

    public float GetRotation(){return degrees;}
    public void SetRotation(float degree) { degrees = degree; }
    public void RotateBy(float degrees) { this.degrees += degrees; }

    public void SetTranslation(PointF newCenter) { translation[0] = newCenter.x; translation[1] = newCenter.y; }
    public PointF GetTranslation() { return new PointF(translation[0], translation[1]); }
    public void TranslateBy(float deltaX, float deltaY) {translation[0] += deltaX; translation[1] += deltaY; }

    public PointF GetCenter()
    {
        float xCenter = 0;
        float yCenter = 0;
        for(int i = 0; i < vertices.length; i = i + 3)
        {
            xCenter += vertices[i];
            yCenter += vertices[i+1];
        }
        return new PointF((float)Math.round(xCenter/(vertices.length/3.0f) * 10)/10, (float)Math.round(yCenter/(vertices.length/3.0f) * 10)/10);
    }

    public float[] GetVertices() { return vertices; }

    public void SetVertices(float[] verts) { this.vertices = verts; }

    public abstract short[] GetDrawingList();
}
