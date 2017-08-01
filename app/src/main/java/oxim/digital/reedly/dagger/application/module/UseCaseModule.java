package oxim.digital.reedly.dagger.application.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import oxim.digital.reedly.domain.interactor.feed.AddNewFeedUseCase;
import oxim.digital.reedly.domain.interactor.feed.DeleteFeedUseCase;
import oxim.digital.reedly.domain.interactor.feed.update.DisableBackgroundFeedUpdatesUseCase;
import oxim.digital.reedly.domain.interactor.feed.update.EnableBackgroundFeedUpdatesUseCase;
import oxim.digital.reedly.domain.interactor.article.favourite.FavouriteArticleUseCase;
import oxim.digital.reedly.domain.interactor.article.favourite.GetFavouriteArticlesUseCase;
import oxim.digital.reedly.domain.interactor.article.GetArticlesUseCase;
import oxim.digital.reedly.domain.interactor.article.GetUnreadArticlesCountUseCase;
import oxim.digital.reedly.domain.interactor.feed.GetUserFeedsUseCase;
import oxim.digital.reedly.domain.interactor.feed.IsUserSubscribedToFeedUseCase;
import oxim.digital.reedly.domain.interactor.article.MarkArticleAsReadUseCase;
import oxim.digital.reedly.domain.interactor.feed.update.SetShouldUpdateFeedsInBackgroundUseCaseImpl;
import oxim.digital.reedly.domain.interactor.feed.update.ShouldUpdateFeedsInBackgroundUseCase;
import oxim.digital.reedly.domain.interactor.article.favourite.UnFavouriteArticleUseCase;
import oxim.digital.reedly.domain.interactor.feed.update.UpdateFeedUseCase;
import oxim.digital.reedly.domain.repository.FeedRepository;
import oxim.digital.reedly.domain.update.FeedsUpdateScheduler;

@Module
public final class UseCaseModule {

    @Provides
    @Singleton
    GetUserFeedsUseCase provideGetUserSubscribedFeedsUseCase(final FeedRepository feedRepository) {
        return new GetUserFeedsUseCase(feedRepository);
    }

    @Provides
    @Singleton
    AddNewFeedUseCase provideAddNewFeedUseCase(final FeedRepository feedRepository) {
        return new AddNewFeedUseCase(feedRepository);
    }

    @Provides
    @Singleton
    GetArticlesUseCase provideGetArticlesUseCase(final FeedRepository feedRepository) {
        return new GetArticlesUseCase(feedRepository);
    }

    @Provides
    @Singleton
    DeleteFeedUseCase provideDeleteFeedUseCase(final FeedRepository feedRepository) {
        return new DeleteFeedUseCase(feedRepository);
    }

    @Provides
    @Singleton
    IsUserSubscribedToFeedUseCase provideIsUserSubscribedToFeedUseCase(final FeedRepository feedRepository) {
        return new IsUserSubscribedToFeedUseCase(feedRepository);
    }

    @Provides
    @Singleton
    UpdateFeedUseCase provideUpdateFeedUseCase(final FeedRepository feedRepository) {
        return new UpdateFeedUseCase(feedRepository);
    }

    @Provides
    @Singleton
    MarkArticleAsReadUseCase provideMarkArticleAsReadUseCase(final FeedRepository feedRepository) {
        return new MarkArticleAsReadUseCase(feedRepository);
    }

    @Provides
    @Singleton
    FavouriteArticleUseCase provideFavouriteArticleUseCase(final FeedRepository feedRepository) {
        return new FavouriteArticleUseCase(feedRepository);
    }

    @Provides
    @Singleton
    UnFavouriteArticleUseCase provideUnFavouriteArticleUseCase(final FeedRepository feedRepository) {
        return new UnFavouriteArticleUseCase(feedRepository);
    }

    @Provides
    @Singleton
    GetUnreadArticlesCountUseCase provideGetUnreadArticlesCountUseCase(final FeedRepository feedRepository) {
        return new GetUnreadArticlesCountUseCase(feedRepository);
    }

    @Provides
    @Singleton
    GetFavouriteArticlesUseCase provideGetFavouriteArticlesUseCase(final FeedRepository feedRepository) {
        return new GetFavouriteArticlesUseCase(feedRepository);
    }

    @Provides
    @Singleton
    ShouldUpdateFeedsInBackgroundUseCase provideShouldUpdateFeedsInBackgroundUseCase(final FeedRepository feedRepository) {
        return new ShouldUpdateFeedsInBackgroundUseCase(feedRepository);
    }

    @Provides
    @Singleton
    SetShouldUpdateFeedsInBackgroundUseCaseImpl provideSetShouldUpdateFeedsInBackgroundUseCase(final FeedRepository feedRepository) {
        return new SetShouldUpdateFeedsInBackgroundUseCaseImpl(feedRepository);
    }

    @Provides
    @Singleton
    EnableBackgroundFeedUpdatesUseCase provideEnableBackgroundFeedUpdatesUseCase(final SetShouldUpdateFeedsInBackgroundUseCaseImpl setShouldUpdateFeedsInBackgroundUseCase,
                                                                                 final FeedsUpdateScheduler feedsUpdateScheduler) {
        return new EnableBackgroundFeedUpdatesUseCase(setShouldUpdateFeedsInBackgroundUseCase, feedsUpdateScheduler);
    }

    @Provides
    @Singleton
    DisableBackgroundFeedUpdatesUseCase provideDisableBackgroundFeedUpdatesUseCase(final SetShouldUpdateFeedsInBackgroundUseCaseImpl setShouldUpdateFeedsInBackgroundUseCase,
                                                                                   final FeedsUpdateScheduler feedsUpdateScheduler) {
        return new DisableBackgroundFeedUpdatesUseCase(setShouldUpdateFeedsInBackgroundUseCase, feedsUpdateScheduler);
    }

    public interface Exposes {

        GetUserFeedsUseCase getUserSubscribedFeedsUseCase();

        AddNewFeedUseCase addNewFeedUseCase();

        GetArticlesUseCase getArticlesUseCase();

        DeleteFeedUseCase deleteFeedUseCase();

        IsUserSubscribedToFeedUseCase isUserSubscribedToFeedUseCase();

        UpdateFeedUseCase updateFeedUseCase();

        MarkArticleAsReadUseCase markArticleAsReadUseCase();

        FavouriteArticleUseCase favouriteArticleUseCase();

        UnFavouriteArticleUseCase unFavouriteArticleUseCase();

        GetUnreadArticlesCountUseCase getUnreadArticlesCountUseCase();

        GetFavouriteArticlesUseCase getFavouriteArticlesUseCase();

        ShouldUpdateFeedsInBackgroundUseCase shouldUpdateFeedsInBackgroundUseCase();

        SetShouldUpdateFeedsInBackgroundUseCaseImpl setShouldUpdateFeedsInBackgroundUseCase();

        EnableBackgroundFeedUpdatesUseCase enableBackgroundFeedUpdatesUseCase();

        DisableBackgroundFeedUpdatesUseCase disableBackgroundFeedUpdatesUseCase();
    }
}
