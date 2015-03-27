package Engine.Drawing.DrawingListGenerators;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

/**
 * Created by Casper on 3/27/2015.
 */
public abstract class DrawingList
{
    protected short[] filledDrawingOrder;
    protected ShortBuffer filledDrawingList;
    protected short[] openedDrawingOrder;
    protected ShortBuffer openedDrawingList;

    public DrawingList()
    {
        FillBuffers();
    }

    protected void FillBuffers(){
        filledDrawingOrder = FilledDrawingList();
        openedDrawingOrder = OpenedDrawingList();
        filledDrawingList = ToShortBuffer(filledDrawingOrder);
        openedDrawingList = ToShortBuffer(openedDrawingOrder);
    }

    protected abstract short[] FilledDrawingList();
    protected abstract short[] OpenedDrawingList();

    public int GetFilledLength()
    {
        return filledDrawingOrder.length;
    }

    public int GetOpenedLength()
    {
        return openedDrawingOrder.length;
    }

    public ShortBuffer GetFilledDrawingList()
    {
        return filledDrawingList;
    }

    public ShortBuffer GetOpenenedDrawingList()
    {
        return openedDrawingList;
    }

    protected ShortBuffer ToShortBuffer(short[] shorts)
    {
        ByteBuffer buffer = ByteBuffer.allocateDirect(shorts.length * 2);
        buffer.order(ByteOrder.nativeOrder());
        ShortBuffer shortBuffer = buffer.asShortBuffer();
        shortBuffer.put(shorts);
        shortBuffer.position(0);
        return shortBuffer;
    }
}
