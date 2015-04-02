package com.appsatwork.ezgles.Objects.Drawables;

import java.nio.ShortBuffer;

import com.appsatwork.ezgles.Drawing.FlatDrawer;
import com.appsatwork.ezgles.Util.BufferBuilder;
import com.appsatwork.ezgles.Objects.Transformables.Geometries.Geometry;
import com.appsatwork.ezgles.Util.Color;

/**
 * Created by Casper on 27-3-2015.
 */
public class Shape implements IDrawable
{
    private Geometry geometry;
    private Color color;
    private ShortBuffer geometryDrawingListBuffer;
    private FlatDrawer drawer;

    //A filled shape
    public Shape(Geometry geometry, Color color)
    {
        this.geometry = geometry;
        this.color = color;
        this.geometryDrawingListBuffer =  BufferBuilder.BuildShortBuffer(geometry.GetDrawingList());
    }


    //Color getter and setter
    public void SetColor(Color color)
    {
        this.color = color;
    }
    public Color GetColor()
    {
        return this.color;
    }

    //Geometry getter (no setter)
    public Geometry GetTransformable()
    {
        return geometry;
    }

    public void Draw(float[] projectionViewMatrix, int programHandle)
    {
        if(drawer == null)
            drawer = new FlatDrawer(programHandle);

        drawer.Draw(projectionViewMatrix, geometry.GetVertices(), geometryDrawingListBuffer, color);
    }
}
