package com.example.dipon.tabviewdemo.main.UI_utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Customized view to show rounded image
 */
public class RoundedImageView extends AppCompatImageView {
    public RoundedImageView(Context context) {
        super(context);
    }

    public RoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    Drawable drawable;
    int w, h;
    Bitmap bitmap;

    @Override
    protected void onDraw(Canvas canvas) {
        drawable = getDrawable();
        w = getWidth();
        h = getHeight();
        if (drawable == null) {
            return;
        }
        if (w == 0 || h == 0) {
            return;
        }
        bitmap = drawableToBitmap(drawable).copy(Bitmap.Config.ARGB_8888, true);
        if (w > h) {
            w = h;
        }
        w = w - 1;
        canvas.drawBitmap(getCroppedBitmap(bitmap, w), 0, 0, null);

    }

    Bitmap tempBitmap = null;
    BitmapDrawable bitmapDrawable;
    Canvas tempCanvas;

    private Bitmap drawableToBitmap(Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            tempBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            tempBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        tempCanvas = new Canvas(tempBitmap);
        drawable.setBounds(0, 0, tempCanvas.getWidth(), tempCanvas.getHeight());
        drawable.draw(tempCanvas);
        return tempBitmap;
    }


    Bitmap output;
    Bitmap sbmp;
    private Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
        if (bmp.getWidth() != radius || bmp.getHeight() != radius) {
            sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
        } else {
            sbmp = bmp;
        }
        output = Bitmap.createBitmap(sbmp.getWidth(),
                sbmp.getHeight(), Bitmap.Config.ARGB_8888);
        tempCanvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        tempCanvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));
        tempCanvas.drawCircle(sbmp.getWidth() / 2 + 0.7f, sbmp.getHeight() / 2 + 0.7f, sbmp.getWidth() / 2 + 0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        tempCanvas.drawBitmap(sbmp, rect, rect, paint);

        return output;
    }
}
