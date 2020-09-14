package eventnotifier.en.en.utils.main.search;

import android.app.Activity;

import eventnotifier.en.en.utils.main.base.BasePresenter;

class SearchPresenter extends BasePresenter<SearchView> {

    private String currentUserId;
    private Activity activity;

    SearchPresenter(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    public void search(String query) {

    }
}
