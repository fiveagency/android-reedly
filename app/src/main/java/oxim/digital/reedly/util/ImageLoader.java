package oxim.digital.reedly.util;

import android.support.annotation.DrawableRes;
import android.widget.ImageView;

public interface ImageLoader {

    void loadImage(String url, ImageView target, @DrawableRes int placeholderDrawable, @DrawableRes int errorDrawable);
}
