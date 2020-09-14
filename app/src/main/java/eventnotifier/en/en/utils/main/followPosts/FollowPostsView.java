package eventnotifier.en.en.utils.main.followPosts;

import android.view.View;

import java.util.List;

import eventnotifier.en.en.utils.main.base.BaseView;
import eventnotifier.en.en.utils.model.FollowingPost;

public interface FollowPostsView extends BaseView {
    void openPostDetailsActivity(String postId, View v);

    void openProfileActivity(String userId, View view);

    void onFollowingPostsLoaded(List<FollowingPost> list);

    void showLocalProgress();

    void hideLocalProgress();

    void showEmptyListMessage(boolean show);
}
