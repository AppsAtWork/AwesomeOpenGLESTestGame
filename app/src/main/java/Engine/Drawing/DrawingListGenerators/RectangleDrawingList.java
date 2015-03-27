package Engine.Drawing.DrawingListGenerators;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

/**
 * Created by Casper on 3/27/2015.
 */
public class RectangleDrawingList extends DrawingList
{
    @Override
    protected short[] FilledDrawingList() {
        return new short[] {0,1,2,0,2,3};
    }

    @Override
    protected short[] OpenedDrawingList() {
        return new short[] {0,1,1,2,2,3,3,0};
    }
}
