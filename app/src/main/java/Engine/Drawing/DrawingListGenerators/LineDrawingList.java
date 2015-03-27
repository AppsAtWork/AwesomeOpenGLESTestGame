package Engine.Drawing.DrawingListGenerators;

/**
 * Created by Casper on 3/27/2015.
 */
public class LineDrawingList extends DrawingList
{
    @Override
    protected short[] FilledDrawingList() { return new short[] {0,1}; }

    @Override
    protected short[] OpenedDrawingList()
    {
        return new short[] {0,1};
    }
}
