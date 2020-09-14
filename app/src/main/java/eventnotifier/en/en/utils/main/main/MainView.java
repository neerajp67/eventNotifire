package eventnotifier.en.en.utils.main.main;

import android.view.View;

import eventnotifier.en.en.utils.main.base.BaseView;
import eventnotifier.en.en.utils.model.Post;

public interface MainView extends BaseView {
    void openCreatePostActivity();
    void hideCounterView();
    void openPostDetailsActivity(Post post, View v);
    void showFloatButtonRelatedSnackBar(int messageId);
    void openProfileActivity(String userId, View view);
    void refreshPostList();
    void removePost();
    void updatePost();
    void showCounterView(int count);
}
