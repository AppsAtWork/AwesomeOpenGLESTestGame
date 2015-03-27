package Engine.Drawing.DrawingListGenerators;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

/**
 * Created by Casper on 3/27/2015.
 */
public class RectangleDrawingListGenerator implements IDrawingListGenerator
{
    private short[] filledDrawingOrder = new short[] {0,1,2,0,2,3};
    private short[] openedDrawingOrder = new short[] {0,1,1,2,2,3,3,0};
    private ShortBuffer filledDrawingList;
    private ShortBuffer openedDrawingList;

    public RectangleDrawingListGenerator()
    {
        //Each short takes up 2 bytes.
        ByteBuffer buffer = ByteBuffer.allocateDirect(filledDrawingOrder.length * 2);
        buffer.order(ByteOrder.nativeOrder());
        filledDrawingList = buffer.asShortBuffer();
        filledDrawingList.put(filledDrawingOrder);
        filledDrawingList.position(0);

        //Each short takes up 2 bytes.
        ByteBuffer buffer2 = ByteBuffer.allocateDirect(openedDrawingOrder.length * 2);
        buffer2.order(ByteOrder.nativeOrder());
        openedDrawingList = buffer.asShortBuffer();
        openedDrawingList.put(openedDrawingOrder);
        openedDrawingList.position(0);
    }

    @Override
    public ShortBuffer GetFilledDrawingList()
    {
        return filledDrawingList;
    }

    @Override
    public ShortBuffer GetOpenedDrawingList()
    {
        return openedDrawingList;
    }
}
