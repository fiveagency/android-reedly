package oxim.digital.reedly.util;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public final class ImageLoaderImpl implements ImageLoader {

    private final Context context;

    public ImageLoaderImpl(final Context context) {
        this.context = context;
    }

    @Override
    public void loadImage(final String url, final ImageView target, @DrawableRes final int placeholderDrawable, @DrawableRes final int errorDrawable) {
        Glide.with(context)
             .load(url)
             .placeholder(placeholderDrawable)
             .error(errorDrawable)
             .crossFade()
             .transform(new CircleImageTransformation(context))
             .into(target);
    }
}
