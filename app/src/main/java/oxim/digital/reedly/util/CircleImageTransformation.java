package oxim.digital.reedly.util;

import java.security.MessageDigest;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;

public class CircleImageTransformation extends BitmapTransformation {

    public CircleImageTransformation(final Context context) {
        super(context);
    }

    @Override
    protected Bitmap transform(final BitmapPool pool, final Bitmap toTransform, final int outWidth, final int outHeight) {
        return cropBitmap(pool, toTransform);
    }

    private Bitmap cropBitmap(final BitmapPool pool, final Bitmap source) {
        if (source == null) {
            return null;
        }

        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        final Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }

        final Canvas canvas = new Canvas(result);
        final Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float radius = size / 2f;
        canvas.drawCircle(radius, radius, radius, paint);
        return result;
    }



    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }
}
