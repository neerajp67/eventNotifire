package eventnotifier.en.en.utils.adapters.holders;

import android.view.View;

import eventnotifier.en.en.utils.main.base.BaseActivity;
import eventnotifier.en.en.utils.model.FollowingPost;
import eventnotifier.en.en.utils.model.Post;
import eventnotifier.en.en.utils.utils.LogUtil;
import eventnotifier.en.en.utils.managers.listeners.OnPostChangedListener;

public class FollowPostViewHolder extends PostViewHolder {


    public FollowPostViewHolder(View view, OnClickListener onClickListener, BaseActivity activity) {
        super(view, onClickListener, activity);
    }

    public FollowPostViewHolder(View view, OnClickListener onClickListener, BaseActivity activity, boolean isAuthorNeeded) {
        super(view, onClickListener, activity, isAuthorNeeded);
    }

    public void bindData(FollowingPost followingPost) {
        postManager.getSinglePostValue(followingPost.getPostId(), new OnPostChangedListener() {
            @Override
            public void onObjectChanged(Post obj) {
                bindData(obj);
            }

            @Override
            public void onError(String errorText) {
                LogUtil.logError(TAG, "bindData", new RuntimeException(errorText));
            }
        });
    }

}
