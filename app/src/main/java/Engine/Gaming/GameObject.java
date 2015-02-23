package Engine.Gaming;


import Engine.OpenGLCanvas;
import Engine.OpenGLObjectManager;
import Engine.OpenGLObjects.OpenGLObject;
import Engine.OpenGLObjects.Sprites.TextureManagement;

/**
 * Created by Casper on 22-2-2015.
 */
public abstract class GameObject
{
    protected OpenGLCanvas Canvas;
    protected OpenGLObject OGLObject;

    public GameObject(OpenGLCanvas canvas)
    {
        Canvas = canvas;
    }

    public abstract void Load();

    public abstract void Update();

    public void MoveToFront()
    {
        OpenGLObjectManager.MoveToFront(OGLObject);
    }

    public void MoveForward() {OpenGLObjectManager.MoveForward(OGLObject);}

    public void MoveBackward() {OpenGLObjectManager.MoveBackward(OGLObject);}
}
