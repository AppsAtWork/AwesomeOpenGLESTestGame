package Engine.Drawing.DrawingListGenerators;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

/**
 * Created by Casper on 3/27/2015.
 */
public class TriangleDrawingList extends DrawingList
{
    @Override
    protected short[] FilledDrawingList() {
        return new short[] {0,1,2};
    }

    @Override
    protected short[] OpenedDrawingList() {
        return new short[] {0,1,1,2,2,0};
    }
}
