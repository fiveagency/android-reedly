package oxim.digital.reedly.ui.router;

public interface Router {

    void closeScreen();

    void goBack();

    void showUserSubscriptionsScreen();

    void showFavouriteArticlesScreen();

    void showArticlesScreen(int feedId, String feedTitle);

    void showArticleContentScreen(String contentUrl);

    void showAddNewFeedScreen();

    void hideAddNewFeedScreen();

}
