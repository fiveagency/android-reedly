package oxim.digital.reedly.ui.view;

import android.text.Editable;
import android.text.TextWatcher;

public abstract class TextWatcherAdapter implements TextWatcher {

    @Override
    public void beforeTextChanged(final CharSequence charSequence, final int start, final int count, final int after) {
        // Template
    }

    @Override
    public void onTextChanged(final CharSequence charSequence, final int start, final int before, final int count) {
        // Template
    }

    @Override
    public void afterTextChanged(final Editable editable) {
        // Template
    }
}
