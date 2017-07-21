package oxim.digital.reedly.ui.article.content;

import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public final class ArticleWebClient extends WebViewClient {

    private final String articleUrl;

    public ArticleWebClient(final String articleUrl) {
        this.articleUrl = articleUrl;
    }

    @Override
    public boolean shouldOverrideUrlLoading(final WebView view, final WebResourceRequest request) {
        if (request.getUrl().toString().equals(articleUrl)) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }
        return super.shouldOverrideUrlLoading(view, request);
    }
}
