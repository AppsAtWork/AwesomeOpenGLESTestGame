package com.appsatwork.ezgles.Objects.Transformables;

import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Casper on 1-4-2015.
 */
public class CompositeTransformable implements ITransform
{
    public List<ITransform> GeometryList;
    private List<float[]> baseVertexSets;
    private float[] flatVertices;

    private float scale = 1;
    private float degrees = 0;
    private float[] translation = new float[] {0,0};

    public CompositeTransformable(List<ITransform> geometryList)
    {
        this.GeometryList = geometryList;

        baseVertexSets = new ArrayList<>();
        int flatCount = 0;
        for(ITransform geometry : GeometryList)
            flatCount += geometry.GetVertices().length;
        flatVertices = new float[flatCount];

        int flatHead = 0;
        for(ITransform geometry : GeometryList)
        {
            //Each piece of geometry contains a vertex list
            float[] geometryVertices = geometry.GetVertices();
            for(int j = 0; j < geometryVertices.length; j+=3)
            {
                flatVertices[flatHead++] = geometryVertices[j];
                flatVertices[flatHead++] = geometryVertices[j+1];
                flatVertices[flatHead++] = geometryVertices[j+2];
            }
        }

        float xCenter = 0;
        float yCenter = 0;
        for(int i = 0; i < flatVertices.length; i = i + 3)
        {
            xCenter += flatVertices[i];
            yCenter += flatVertices[i+1];
        }
        xCenter = xCenter / (flatVertices.length/3.0f);
        yCenter = yCenter / (flatVertices.length/3.0f);

        for(ITransform geometry : GeometryList)
        {
            //Each piece of geometry contains a vertex list
            float[] geometryVertices = geometry.GetVertices();
            float[] baseGeometryVertices = new float[geometryVertices.length];
            for(int j = 0; j < geometryVertices.length; j+=3)
            {
                baseGeometryVertices[j] = geometryVertices[j] - xCenter;
                baseGeometryVertices[j+1] = geometryVertices[j+1] - yCenter;
                baseGeometryVertices[j+2] = geometryVertices[j+2];
            }
            baseVertexSets.add(baseGeometryVertices);
        }
        translation = new float[] {xCenter, yCenter};
    }

    @Override
    public void ApplyTransformations()
    {
        int flatHead = 0;
        for(int i = 0; i < baseVertexSets.size(); i++)
        {
            float[] baseVertexSet = baseVertexSets.get(i);
            float[] vertexSet = new float[baseVertexSet.length];
            for(int j = 0; j < baseVertexSet.length; j+=3)
            {
                float x = baseVertexSet[j];
                float y = baseVertexSet[j+1];
                float z = 0;

                x *= scale;
                y *= scale;

                //Rotate
                float sin = (float)Math.sin(Math.toRadians(degrees));
                float cos = (float)Math.cos(Math.toRadians(degrees));

                float x_temp = x;
                x = cos * x - sin * y;
                y = sin * x_temp + cos * y;

                x+= translation[0];
                y+= translation[1];
                vertexSet[j] = x;
                vertexSet[j+1] = y;
                vertexSet[j+2] = z;
                flatVertices[flatHead++] = x;
                flatVertices[flatHead++] = y;
                flatVertices[flatHead++] = z;
            }
            this.GeometryList.get(i).SetVertices(vertexSet);
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

    @Override
    public float[] GetVertices() {
        return flatVertices;
    }

    @Override
    public void SetVertices(float[] verts) {
        flatVertices = verts;
        int flatHead = 0;
        //Now distribute like a bitch.
        for(int i = 0; i < GeometryList.size(); i++)
        {
            ITransform geometry = GeometryList.get(i);
            int length = geometry.GetVertices().length;
            float[] geometryVerts = new float[length];
            for(int j = 0; j < length; j++)
            {
                geometryVerts[j] = verts[flatHead++];
            }
            geometry.SetVertices(geometryVerts);
        }
    }
}
