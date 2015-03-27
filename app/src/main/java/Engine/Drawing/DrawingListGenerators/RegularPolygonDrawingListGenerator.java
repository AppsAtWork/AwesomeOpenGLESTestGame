package Engine.Drawing.DrawingListGenerators;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

/**
 * Created by Casper on 3/27/2015.
 */
public class RegularPolygonDrawingListGenerator implements IDrawingListGenerator
{
    private short[] filledDrawingOrder;
    private ShortBuffer filledDrawingList;

    private short[] openedDrawingOrder;
    private ShortBuffer openedDrawingList;

    public RegularPolygonDrawingListGenerator(float cornerCount)
    {
        //Generate drawing list
        filledDrawingOrder = new short[((int)cornerCount + 1)*3 + 6 - 3];
        filledDrawingOrder[0] = 0;
        int vertexNumber = 1;
        for(int i = 1; i < filledDrawingOrder.length-3; i+=3)
        {
            filledDrawingOrder[i] = (short)(vertexNumber);
            filledDrawingOrder[i+1] = (short)(++vertexNumber);
            filledDrawingOrder[i+2] = 0;
        }

        //And convert to buffer
        //Each short takes up 2 bytes.
        ByteBuffer buffer = ByteBuffer.allocateDirect(filledDrawingOrder.length * 2);
        buffer.order(ByteOrder.nativeOrder());
        filledDrawingList = buffer.asShortBuffer();
        filledDrawingList.put(filledDrawingOrder);
        filledDrawingList.position(0);
    }
    @Override
    public ShortBuffer GetFilledDrawingList() {
        return null;
    }

    @Override
    public ShortBuffer GetOpenedDrawingList() {
        return filledDrawingList;
    }
}
