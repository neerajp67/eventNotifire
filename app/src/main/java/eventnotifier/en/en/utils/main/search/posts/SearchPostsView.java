package eventnotifier.en.en.utils.main.search.posts;

import java.util.List;

import eventnotifier.en.en.utils.main.base.BaseFragmentView;
import eventnotifier.en.en.utils.model.Post;

public interface SearchPostsView extends BaseFragmentView {
    void onSearchResultsReady(List<Post> posts);
    void showLocalProgress();
    void hideLocalProgress();
    void showEmptyListLayout();
}
