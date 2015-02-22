package Engine.Util;

/**
 * Created by Casper on 22-2-2015.
 */
public class Velocity
{
    public Vector2 SpeedVector;
    public float Velocity;

    public Velocity(Vector2 direction, float velocity)
    {
        SpeedVector = direction;
        Velocity = velocity;
        SpeedVector.Normalize(velocity);
    }

    public void InvertX()
    {
        SpeedVector.X = -SpeedVector.X;
    }

    public void InvertY()
    {
        SpeedVector.Y = -SpeedVector.Y;
    }

    public void Invert()
    {
        InvertX();
        InvertY();
    }

    public void SpeedUp(float extra)
    {
        this.Velocity += extra;
    }

    public void SlowDown(float extra)
    {
        this.Velocity -= extra;
    }

    public void SetSpeed(float speed)
    {
        Velocity = speed;
    }
}
