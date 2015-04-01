package Engine.Objects;

import android.opengl.GLES20;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import Engine.Drawing.FlatDrawer;
import Engine.Objects.GeometryObjects.Border;
import Engine.Objects.GeometryObjects.Geometry;
import Engine.Util.Color;

/**
 * Created by Casper on 30-3-2015.
 */
public class BorderedShape implements IDrawable
{
    private Geometry geometry;
    private Color color;
    private ShortBuffer geometryDrawingListBuffer;

    private Border border;
    private Color borderColor;
    private ShortBuffer borderDrawingListBuffer;

    private FlatDrawer drawer;

    public BorderedShape(Border border, Geometry fill, Color fillColor, Color borderColor)
    {
        this.geometry = fill;
        this.border = border;

        this.color = fillColor;
        this.borderColor = borderColor;

        this.geometryDrawingListBuffer =  BufferBuilder.BuildShortBuffer(geometry.GetDrawingList());
        this.borderDrawingListBuffer = BufferBuilder.BuildShortBuffer(border.GetDrawingList());
    }

    public Geometry GetGeometry()
    {
        return this.border;
    }

    @Override
    public void Draw(float[] projectionViewMatrix, int programHandle)
    {
        if(drawer == null)
            this.drawer = new FlatDrawer(programHandle);

        drawer.Draw(projectionViewMatrix, border.GetVertices(), borderDrawingListBuffer, borderColor);
        drawer.Draw(projectionViewMatrix, geometry.GetVertices(), geometryDrawingListBuffer, color);
    }
}
