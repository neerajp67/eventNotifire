package eventnotifier.en.en.utils.managers.listeners;

import eventnotifier.en.en.utils.model.Post;

public interface OnPostChangedListener {
    public void onObjectChanged(Post obj);

    public void onError(String errorText);
}
