package Engine.Gaming;


import Engine.OpenGLCanvas;
import Engine.Objects.OpenGLObject;;

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
}
