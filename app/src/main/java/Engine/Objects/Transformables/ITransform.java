package Engine.Objects.Transformables;

import android.graphics.PointF;

/**
 * Created by Casper on 1-4-2015.
 */
public interface ITransform
{
    public void ApplyTransformations();

    public float GetScale();
    public void SetScale(float factor);
    public void ScaleBy(float factor);

    public float GetRotation();
    public void SetRotation(float degree);
    public void RotateBy(float degrees);

    public PointF GetTranslation();
    public void SetTranslation(PointF newCenter);
    public void TranslateBy(float deltaX, float deltaY);

    public float[] GetVertices();
    public void SetVertices(float[] verts);
}
