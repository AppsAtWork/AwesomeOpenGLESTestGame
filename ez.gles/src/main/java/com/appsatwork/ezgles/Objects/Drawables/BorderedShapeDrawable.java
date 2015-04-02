package com.appsatwork.ezgles.Objects.Drawables;

import java.nio.ShortBuffer;

import com.appsatwork.ezgles.Drawing.FlatDrawer;
import com.appsatwork.ezgles.Util.BufferBuilder;
import com.appsatwork.ezgles.Objects.Transformables.Geometries.Border;
import com.appsatwork.ezgles.Objects.Transformables.Geometries.Geometry;
import com.appsatwork.ezgles.Util.Color;

/**
 * Created by Casper on 30-3-2015.
 */
public class BorderedShapeDrawable implements IDrawable
{
    private Geometry geometry;
    private Color color;
    private ShortBuffer geometryDrawingListBuffer;

    private Border border;
    private Color borderColor;
    private ShortBuffer borderDrawingListBuffer;

    private FlatDrawer drawer;

    public BorderedShapeDrawable(Border border, Geometry fill, Color fillColor, Color borderColor)
    {
        this.geometry = fill;
        this.border = border;

        this.color = fillColor;
        this.borderColor = borderColor;

        this.geometryDrawingListBuffer =  BufferBuilder.BuildShortBuffer(geometry.GetDrawingList());
        this.borderDrawingListBuffer = BufferBuilder.BuildShortBuffer(border.GetDrawingList());
    }

    public Geometry GetTransformable()
    {
        return this.border;
    }

    @Override
    public void Draw(float[] projectionViewMatrix)
    {
        if(drawer == null)
            this.drawer = new FlatDrawer();

        drawer.Draw(projectionViewMatrix, border.GetVertices(), borderDrawingListBuffer, borderColor);
        drawer.Draw(projectionViewMatrix, geometry.GetVertices(), geometryDrawingListBuffer, color);
    }
}
