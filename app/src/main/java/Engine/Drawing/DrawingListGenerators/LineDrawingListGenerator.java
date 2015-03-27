package Engine.Drawing.DrawingListGenerators;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

/**
 * Created by Casper on 3/27/2015.
 */
public class LineDrawingListGenerator implements IDrawingListGenerator
{
    private short[] filledDrawingOrder = new short[] { 0,1 };
    private ShortBuffer filledDrawingList;

    public LineDrawingListGenerator()
    {
        //Each short takes up 2 bytes.
        ByteBuffer buffer = ByteBuffer.allocateDirect(filledDrawingOrder.length * 2);
        buffer.order(ByteOrder.nativeOrder());
        filledDrawingList = buffer.asShortBuffer();
        filledDrawingList.put(filledDrawingOrder);
        filledDrawingList.position(0);
    }

    @Override
    public ShortBuffer GetFilledDrawingList() {
        return filledDrawingList;
    }

    @Override
    public ShortBuffer GetOpenedDrawingList() {
        return filledDrawingList;
    }
}
