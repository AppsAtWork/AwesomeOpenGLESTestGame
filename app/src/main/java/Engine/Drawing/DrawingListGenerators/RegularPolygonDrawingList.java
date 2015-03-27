package Engine.Drawing.DrawingListGenerators;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

/**
 * Created by Casper on 3/27/2015.
 */
public class RegularPolygonDrawingList extends DrawingList
{
    private float cornerCount;
    public RegularPolygonDrawingList(float cornerCount)
    {
        this.cornerCount = cornerCount;
        FillBuffers();
    }

    @Override
    protected short[] FilledDrawingList()
    {
        short[] drawingOrder = new short[((int)cornerCount + 1)*3 + 6 - 3];
        drawingOrder [0] = 0;
        int vertexNumber = 1;
        for(int i = 1; i < drawingOrder .length-3; i+=3)
        {
            drawingOrder [i] = (short)(vertexNumber);
            drawingOrder [i+1] = (short)(++vertexNumber);
            drawingOrder [i+2] = 0;
        }
        return drawingOrder;
    }

    @Override
    protected short[] OpenedDrawingList() {
        return new short[0];
    }
}
