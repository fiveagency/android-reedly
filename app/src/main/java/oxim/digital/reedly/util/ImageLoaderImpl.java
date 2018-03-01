package oxim.digital.reedly.util;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

public final class ImageLoaderImpl implements ImageLoader {

    private final Context context;

    public ImageLoaderImpl(final Context context) {
        this.context = context;
    }

    @Override
    public void loadImage(final String url, final ImageView target, @DrawableRes final int placeholderDrawable, @DrawableRes final int errorDrawable) {
        RequestOptions requestOptions = new RequestOptions()
            .placeholder(placeholderDrawable)
            .error(errorDrawable)
            .transform(new CircleImageTransformation(context));

        Glide.with(context)
             .setDefaultRequestOptions(requestOptions)
             .load(url)
             .into(target);
    }
}
