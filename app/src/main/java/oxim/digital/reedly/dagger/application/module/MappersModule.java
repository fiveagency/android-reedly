package oxim.digital.reedly.dagger.application.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import oxim.digital.reedly.ui.mapper.FeedViewModeMapper;
import oxim.digital.reedly.ui.mapper.FeedViewModelMapperImpl;
import oxim.digital.reedly.util.DateUtils;

@Module
public final class MappersModule {

    @Provides
    @Singleton
    FeedViewModeMapper provideFeedViewModeMapper(final DateUtils dateUtils) {
        return new FeedViewModelMapperImpl(dateUtils);
    }

    public interface Exposes {

        FeedViewModeMapper feedViewModeMapper();
    }
}
