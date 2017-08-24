package oxim.digital.reedly.dagger.application.module;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import oxim.digital.reedly.data.feed.FeedRepositoryImpl;
import oxim.digital.reedly.data.feed.converter.FeedModelConverter;
import oxim.digital.reedly.data.feed.converter.FeedModelConverterImpl;
import oxim.digital.reedly.data.feed.db.FeedDao;
import oxim.digital.reedly.data.feed.db.FeedDaoImpl;
import oxim.digital.reedly.data.feed.service.FeedService;
import oxim.digital.reedly.data.feed.service.parser.ExternalParserWrapper;
import oxim.digital.reedly.data.feed.service.parser.EarlParserWrapper;
import oxim.digital.reedly.data.feed.service.parser.FeedParser;
import oxim.digital.reedly.data.feed.service.parser.FeedParserImpl;
import oxim.digital.reedly.data.util.CurrentTimeProvider;
import oxim.digital.reedly.data.util.PreferenceUtils;
import oxim.digital.reedly.domain.repository.FeedRepository;
import rx.Scheduler;

@Module
public final class DataModule {

    @Provides
    @Singleton
    FeedRepository provideFeedRepository(final FeedService feedService, final FeedDao feedDao, final PreferenceUtils preferenceUtils,
                                         final @Named(ThreadingModule.BACKGROUND_SCHEDULER) Scheduler backgroundScheduler) {
        return new FeedRepositoryImpl(feedService, feedDao, preferenceUtils, backgroundScheduler);
    }

    @Provides
    @Singleton
    FeedModelConverter provideFeedModelConverter() {
        return new FeedModelConverterImpl();
    }

    @Provides
    @Singleton
    FeedParser provideFeedParser(final CurrentTimeProvider currentTimeProvider, final ExternalParserWrapper externalParserWrapper) {
        return new FeedParserImpl(currentTimeProvider, externalParserWrapper);
    }

    @Provides
    @Singleton
    ExternalParserWrapper provideExternalParserWrapper(){
        return new EarlParserWrapper();
    }

    @Provides
    @Singleton
    FeedDao provideFeedDao(final FeedModelConverter feedModelConverter) {
        return new FeedDaoImpl(feedModelConverter);
    }

    public interface Exposes {

        FeedRepository feedRepository();

        FeedModelConverter feedModelConverter();

        FeedParser feedParser();
    }
}
