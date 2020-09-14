package eventnotifier.en.en.utils.main.followPosts;

import android.content.Context;
import android.view.View;

import eventnotifier.en.en.R;
import eventnotifier.en.en.utils.main.base.BasePresenter;
import eventnotifier.en.en.utils.managers.PostManager;
import eventnotifier.en.en.utils.managers.listeners.OnPostChangedListener;
import eventnotifier.en.en.utils.model.Post;

class FollowingPostsPresenter extends BasePresenter<FollowPostsView> {

    private PostManager postManager;

    FollowingPostsPresenter(Context context) {
        super(context);
        postManager = PostManager.getInstance(context);
    }

    void onPostClicked(final String postId, final View postView) {
        postManager.isPostExistSingleValue(postId, exist -> ifViewAttached(view -> {
            if (exist) {
                view.openPostDetailsActivity(postId, postView);
            } else {
                view.showSnackBar(R.string.error_post_was_removed);
            }
        }));
    }

    public void loadFollowingPosts() {
        if (checkInternetConnection()) {
            if (getCurrentUserId() != null) {
                ifViewAttached(FollowPostsView::showLocalProgress);
                postManager.getFollowingPosts(getCurrentUserId(), list -> ifViewAttached(view -> {
                    view.hideLocalProgress();
                    view.onFollowingPostsLoaded(list);
                    view.showEmptyListMessage(list.isEmpty());
                }));
            } else {
                ifViewAttached(view -> {
                    view.showEmptyListMessage(true);
                    view.hideLocalProgress();
                });
            }
        } else {
            ifViewAttached(FollowPostsView::hideLocalProgress);
        }
    }

    public void onRefresh() {
        loadFollowingPosts();
    }

    public void onAuthorClick(String postId, View authorView) {
        postManager.getSinglePostValue(postId, new OnPostChangedListener() {
            @Override
            public void onObjectChanged(Post obj) {
                ifViewAttached(view -> view.openProfileActivity(obj.getAuthorId(), authorView));
            }

            @Override
            public void onError(String errorText) {
                ifViewAttached(view -> view.showSnackBar(errorText));
            }
        });
    }
}
