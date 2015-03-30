package Engine.Objects.GeometryObjects;

import android.graphics.PointF;

import Engine.Util.Vector2;

/**
 * Created by Casper on 29-3-2015.
 */
public class Border extends Geometry
{
    public float Thickness;
    private Geometry geometry;
    private short[] drawingList;

    public Border(Rectangle around, float thickness)
    {
        this.geometry = around;
        this.Thickness = thickness;
        ConstructBorderRectangle();
    }

    public Border(RegularPolygon around, float thickness)
    {
        this.geometry = around;
        this.Thickness = thickness;
        ConstructBorderRegularPolygon();
    }

    private void ConstructBorderRectangle()
    {
        PointF center = geometry.Center();
        float[] verts = geometry.GetVertices();

        float[] borderVerts = new float[verts.length * 4];
        short[] borderVertsDrawingList = new short[verts.length*2];
        int borderVertPosition = 0;

        for(int i = 0; i < verts.length-3; i = i+3)
        {
            //Calculate the vector to two adjacent vertices
            Vector2 vertex1Vector = new Vector2(verts[i] - center.x, verts[i+1] - center.y );
            float xExtra = vertex1Vector.X < 0 ? -Thickness/60.0f : Thickness/60.0f;
            float yExtra = vertex1Vector.Y < 0 ? -Thickness/60.0f : Thickness/60.0f;

            Vector2 vertex2Vector = new Vector2(verts[i+3] - center.x , verts[i+4] - center.y);
            float xExtra2 = vertex2Vector.X < 0 ? -Thickness/60.0f : Thickness/60.0f;
            float yExtra2 = vertex2Vector.Y < 0 ? -Thickness/60.0f : Thickness/60.0f;

            float[] line = new float[]
                    {
                            center.x + vertex1Vector.X + xExtra, center.y + vertex1Vector.Y + yExtra, 0.0f,
                            center.x + vertex2Vector.X + xExtra2, center.y + vertex2Vector.Y + yExtra2, 0.0f,
                            verts[i+3], verts[i+4], 0.0f,
                            verts[i], verts[i+1], 0.0f
                    };

            for(int j = 0; j < line.length; j++)
            {
                borderVerts[borderVertPosition++] = line[j];
            }
        }
        //Calculate the vector to two adjacent vertices
        Vector2 vertex1Vector = new Vector2(verts[verts.length-3] - center.x, verts[verts.length-2]- center.y);
        float xExtra = vertex1Vector.X < 0 ? -Thickness/60.0f : Thickness/60.0f;
        float yExtra = vertex1Vector.Y < 0 ? -Thickness/60.0f : Thickness/60.0f;
        Vector2 vertex2Vector = new Vector2(verts[0] - center.x, verts[1] - center.y);
        float xExtra2 = vertex2Vector.X < 0 ? -Thickness/60.0f : Thickness/60.0f;
        float yExtra2 = vertex2Vector.Y < 0 ? -Thickness/60.0f : Thickness/60.0f;

        float[] lastLine = new float[]
                {
                        center.x + vertex1Vector.X + xExtra, center.y + vertex1Vector.Y + yExtra, 0.0f,
                        center.x + vertex2Vector.X + xExtra2, center.y + vertex2Vector.Y + yExtra2, 0.0f,
                        verts[0], verts[1], 0.0f,
                        verts[verts.length-3], verts[verts.length-2], 0.0f
                };
        for(int j = 0; j < lastLine.length; j++)
            borderVerts[borderVertPosition++] = lastLine[j];

        int j = 0;
        for(int i = 0; i < borderVertsDrawingList.length; i += 6)
        {
            borderVertsDrawingList[i]   = (short) j;
            borderVertsDrawingList[i+1] = (short) (j+1);
            borderVertsDrawingList[i+2] = (short) (j+2);
            borderVertsDrawingList[i+3] = (short) j;
            borderVertsDrawingList[i+4] = (short) (j+2);
            borderVertsDrawingList[i+5] = (short) (j+3);
            j += 4;
        }

        drawingList = borderVertsDrawingList;
        vertices = borderVerts;
        baseVertices = new float[vertices.length];
        for(int i = 0; i < vertices.length; i+=3)
        {
            baseVertices[i] = vertices[i] - center.x;
            baseVertices[i+1] = vertices[i+1] - center.y;
            baseVertices[i+2] = 0.0f;
        }
        translation = new float[] { center.x, center.y };
    }

    private void ConstructBorderRegularPolygon()
    {
        PointF center = geometry.Center();
        float[] verts = geometry.GetVertices();

        float[] borderVerts = new float[verts.length * 4];
        short[] borderVertsDrawingList = new short[verts.length*2];
        int borderVertPosition = 0;

        for(int i = 0; i < verts.length-3; i = i+3)
        {
            //Calculate the vector to two adjacent vertices
            Vector2 vertex1Vector = new Vector2(verts[i] - center.x, verts[i+1] - center.y );
            vertex1Vector.Normalize(vertex1Vector.Length() + Thickness/60.0f);

            Vector2 vertex2Vector = new Vector2(verts[i+3] - center.x , verts[i+4] - center.y);
            vertex2Vector.Normalize(vertex2Vector.Length() + Thickness/60.0f);
            float[] line = new float[]
                    {
                            center.x + vertex1Vector.X, center.y + vertex1Vector.Y, 0.0f,
                            center.x + vertex2Vector.X, center.y + vertex2Vector.Y, 0.0f,
                            verts[i+3], verts[i+4], 0.0f,
                            verts[i], verts[i+1], 0.0f
                    };

            for(int j = 0; j < line.length; j++)
            {
                borderVerts[borderVertPosition++] = line[j];
            }
        }
        //Calculate the vector to two adjacent vertices
        Vector2 vertex1Vector = new Vector2(verts[verts.length-3] - center.x, verts[verts.length-2]- center.y);
        vertex1Vector.Normalize(vertex1Vector.Length() + Thickness/60.0f);
        Vector2 vertex2Vector = new Vector2(verts[0] - center.x, verts[1] - center.y);
        vertex2Vector.Normalize(vertex2Vector.Length() + Thickness/60.0f);
        float[] lastLine = new float[]
                {
                        center.x + vertex1Vector.X, center.y + vertex1Vector.Y, 0.0f,
                        center.x + vertex2Vector.X, center.y + vertex2Vector.Y, 0.0f,
                        verts[0], verts[1], 0.0f,
                        verts[verts.length-3], verts[verts.length-2], 0.0f
                };
        for(int j = 0; j < lastLine.length; j++)
            borderVerts[borderVertPosition++] = lastLine[j];

        int j = 0;
        for(int i = 0; i < borderVertsDrawingList.length; i += 6)
        {
            borderVertsDrawingList[i]   = (short) j;
            borderVertsDrawingList[i+1] = (short) (j+1);
            borderVertsDrawingList[i+2] = (short) (j+2);
            borderVertsDrawingList[i+3] = (short) j;
            borderVertsDrawingList[i+4] = (short) (j+2);
            borderVertsDrawingList[i+5] = (short) (j+3);
            j += 4;
        }

        drawingList = borderVertsDrawingList;
        vertices = borderVerts;
        baseVertices = new float[vertices.length];
        for(int i = 0; i < vertices.length; i+=3)
        {
            baseVertices[i] = vertices[i] - center.x;
            baseVertices[i+1] = vertices[i+1] - center.y;
            baseVertices[i+2] = 0.0f;
        }
        translation = new float[] { center.x, center.y };
    }

    @Override
    public void ApplyTransformations()
    {
        //Copy over all transformations of the border to the geometry
        geometry.SetScale(scale);
        geometry.SetCenter(new PointF(translation[0], translation[1]));
        geometry.SetRotation(degrees);
        geometry.ApplyTransformations();

        super.ApplyTransformations();
    }

    public Geometry GetGeometry()
    {
        return this.geometry;
    }

    @Override
    public short[] GetDrawingList() {
        return drawingList;
    }
}
