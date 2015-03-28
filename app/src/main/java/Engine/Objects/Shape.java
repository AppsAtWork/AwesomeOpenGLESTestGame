package Engine.Objects;

import android.graphics.PointF;
import android.opengl.GLES20;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import Engine.Objects.GeometryObjects.Geometry;
import Engine.Util.Color;
import Engine.Util.Vector2;

/**
 * Created by Casper on 27-3-2015.
 */
public class Shape implements IDrawable
{
    private float BorderThickness = 0;
    private boolean Border = false;
    private boolean Filled = true;

    private Color Color;
    private Color BorderColor;

    private Geometry Geometry;

    private ShortBuffer DrawingListBuffer;
    private FloatBuffer VertexBuffer;

    private short[] BorderDrawingList;
    private ShortBuffer BorderDrawingListBuffer;
    private FloatBuffer BorderVertexBuffer;

    //A filled shape without a border
    public Shape(Geometry geometry, Color color)
    {
        //Set color and geometry
        this.Filled = true;
        this.Color = color;
        this.Geometry = geometry;

        //Set border
        this.Border = false;
        this.BorderColor = null;
        this.BorderThickness = 0;

        //Load drawing list buffer
        this. DrawingListBuffer = BufferBuilder.BuildShortBuffer(geometry.GetDrawingList());
    }

    //An open shape with a border
    public Shape(Geometry geometry, Color borderColor, float borderThickness)
    {
        //Set color and geometry
        this.Filled = false;
        this.Color = null;
        this.Geometry = geometry;

        //Set border
        this.Border = true;
        this.BorderColor = borderColor;
        this.BorderThickness = borderThickness;

        //Load drawing list buffer
        this. DrawingListBuffer = BufferBuilder.BuildShortBuffer(geometry.GetDrawingList());
    }

    //A filled shape with a border
    public Shape(Geometry geometry, Color color, float borderThickness, Color borderColor)
    {
        //Set color and geometry
        this.Filled = true;
        this.Color = color;
        this.Geometry = geometry;

        //Set border
        this.Border = true;
        this.BorderColor = borderColor;
        this.BorderThickness = borderThickness;

        //Load drawing list buffer
        this. DrawingListBuffer = BufferBuilder.BuildShortBuffer(geometry.GetDrawingList());
    }

    //Border thickness getter and setter
    public void SetBorderThickness(float thickness)
    {
        this.BorderThickness = thickness;
    }
    public float GetBorderThickness()
    {
        return this.BorderThickness;
    }

    //Border getter and setter
    public void SetBorder(boolean borderEnabled)
    {
        this.Border = borderEnabled;
    }
    public boolean GetBorder()
    {
        return this.Border;
    }

    //Color getter and setter
    public void SetColor(Color color)
    {
        this.Color = color;
    }
    public Color GetColor(Color color)
    {
        return this.Color;
    }

    //Filled getter and setter
    public void SetFilled(boolean filledEnabled) {this.Filled = filledEnabled;}
    public boolean GetFilled() {return this.Filled;}

    //Geometry getter (no setter)
    public Geometry GetGeometry()
    {
        return this.Geometry;
    }

    public void Draw(float[] projectionViewMatrix, int programHandle)
    {
        //For now, just rebuild the vertex buffer at every draw call. Change this in the future to
        //To use observer patterns and such, or move the vertex buffer building entirely to geometry.
        this.VertexBuffer = BufferBuilder.BuildFloatBuffer(Geometry.GetVertices());

        if(Filled)
            DrawFill(projectionViewMatrix, programHandle);
        if(Border)
            DrawBorder(projectionViewMatrix, programHandle);
    }

    private void DrawFill(float[] projectionViewMatrix, int programHandle)
    {
        //Get a handle to the vPosition member of the shader
        //If I understand correctly, this is pretty much the point that is being rasterized.
        int positionHandle = GLES20.glGetAttribLocation(programHandle, "vPosition");

        //Enable the vertex array that will contain the positions that will be drawn
        GLES20.glEnableVertexAttribArray(positionHandle);

        //Send the vertex data to the OpenGLES pipeline.
        GLES20.glVertexAttribPointer(
                positionHandle, //Which variable will hold the vertices
                3, //The amount of floats used to represents a vertex.
                GLES20.GL_FLOAT, //Tell OpenGLES that we are using floats
                false,
                0, //We can pass 0 here, because we use the drawingOrder in the drawListBuffer
                VertexBuffer);

        //We need to send our projectionview matrix to OpenGLES too.
        GLES20.glUniformMatrix4fv(GLES20.glGetUniformLocation(programHandle, "uMVPMatrix"),1,false,projectionViewMatrix,0);

        //Send over the drawing type to the shader
        GLES20.glUniform1i(GLES20.glGetUniformLocation(programHandle, "drawingType"), 0);

        //Send over the color of the square
        GLES20.glUniform4fv(GLES20.glGetUniformLocation(programHandle, "vColor"),1, Color.GetFloatArray(), 0);

        //OpenGLES now knows everything necessary to draw the square (except the color, later)
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, Geometry.GetDrawingList().length, GLES20.GL_UNSIGNED_SHORT, DrawingListBuffer);

        //Disable the vertex array again :)
        GLES20.glDisableVertexAttribArray(positionHandle);
    }

    private void DrawBorder(float[] projectionViewMatrix, int programHandle)
    {
        ConstructBorder();
        //Get a handle to the vPosition member of the shader
        //If I understand correctly, this is pretty much the point that is being rasterized.
        int positionHandle = GLES20.glGetAttribLocation(programHandle, "vPosition");

        //Enable the vertex array that will contain the positions that will be drawn
        GLES20.glEnableVertexAttribArray(positionHandle);

        //Send the vertex data to the OpenGLES pipeline.
        GLES20.glVertexAttribPointer(
                positionHandle, //Which variable will hold the vertices
                3, //The amount of floats used to represents a vertex.
                GLES20.GL_FLOAT, //Tell OpenGLES that we are using floats
                false,
                0, //We can pass 0 here, because we use the drawingOrder in the drawListBuffer
                BorderVertexBuffer);

        //We need to send our projectionview matrix to OpenGLES too.
        GLES20.glUniformMatrix4fv(GLES20.glGetUniformLocation(programHandle, "uMVPMatrix"),1,false,projectionViewMatrix,0);

        //Send over the drawing type to the shader
        GLES20.glUniform1i(GLES20.glGetUniformLocation(programHandle, "drawingType"), 0);

        //Send over the color of the square
        GLES20.glUniform4fv(GLES20.glGetUniformLocation(programHandle, "vColor"),1, BorderColor.GetFloatArray(), 0);

        //OpenGLES now knows everything necessary to draw the square (except the color, later)
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, BorderDrawingList.length, GLES20.GL_UNSIGNED_SHORT, BorderDrawingListBuffer);

        //Disable the vertex array again :)
        GLES20.glDisableVertexAttribArray(positionHandle);
    }

    private void ConstructBorder()
    {
        float[] verts = Geometry.GetVertices();

        float[] borderVerts = new float[verts.length * 4];
        short[] borderVertsDrawingList = new short[verts.length*2];
        int borderVertPosition = 0;

        for(int i = 0; i < verts.length-3; i = i+3)
        {
            float[] line = ThickLineBetweenTwoPoints(new PointF(verts[i], verts[i+1]), new PointF(verts[i+3], verts[i+4]), BorderThickness);
            for(int j = 0; j < line.length; j++)
            {
                borderVerts[borderVertPosition++] = line[j];
            }
        }

        float[] lastLine = ThickLineBetweenTwoPoints(new PointF(verts[verts.length-3], verts[verts.length-2]), new PointF(verts[0], verts[1]), BorderThickness);
        for(int j = 0; j < lastLine.length; j++)
            borderVerts[borderVertPosition++] = lastLine[j];

        int j = 0;
        for(int i = 0; i < borderVertsDrawingList.length; i += 6)
        {
            borderVertsDrawingList[i] = (short)j;
            borderVertsDrawingList[i+1] = (short) (j+1);
            borderVertsDrawingList[i+2] = (short) (j+2);
            borderVertsDrawingList[i+3] = (short) j;
            borderVertsDrawingList[i+4] = (short) (j+2);
            borderVertsDrawingList[i+5] = (short) (j+3);
            j += 4;
        }

        BorderDrawingList = borderVertsDrawingList;
        BorderDrawingListBuffer = BufferBuilder.BuildShortBuffer(BorderDrawingList);
        BorderVertexBuffer = BufferBuilder.BuildFloatBuffer(borderVerts);
    }

    private float[] ThickLineBetweenTwoPoints(PointF p1, PointF p2, float thickness)
    {
        System.out.println("P1: " + p1.x + ", " + p1.y);
        System.out.println("P2: " + p2.x + ", " + p2.y);

        Vector2 direction = new Vector2(p2.x - p1.x, p2.y - p1.y);
        System.out.println("Direction: " + direction.X + ", " + direction.Y);

        direction.Normalize(thickness/2.0f);
        System.out.println("Normalized direction: " + direction.X + ", " + direction.Y);

        Vector2 perpendicular = new Vector2(-direction.Y, direction.X);
        System.out.println("Perpendicular vector: " + perpendicular.X + ", " + perpendicular.Y);

        float xThickness = perpendicular.X;
        float yThickness = perpendicular.Y;

        return new float[] {
                p1.x + xThickness/2.0f, p1.y + yThickness/2.0f, 0.0f,
                p2.x + xThickness/2.0f, p2.y + yThickness/2.0f, 0.0f,
                p2.x - xThickness/2.0f, p2.y - yThickness/2.0f, 0.0f,
                p1.x - xThickness/2.0f, p1.y - yThickness/2.0f, 0.0f
        };
    }
}
