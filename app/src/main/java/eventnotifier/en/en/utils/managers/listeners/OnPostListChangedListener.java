package eventnotifier.en.en.utils.managers.listeners;

import eventnotifier.en.en.utils.model.PostListResult;

public interface OnPostListChangedListener<Post> {

    public void onListChanged(PostListResult result);

    void onCanceled(String message);
}
