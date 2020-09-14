package eventnotifier.en.en.utils.main.search.users;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import eventnotifier.en.en.utils.main.base.BasePresenter;
import eventnotifier.en.en.utils.main.base.BaseView;
import eventnotifier.en.en.utils.managers.FollowManager;
import eventnotifier.en.en.utils.managers.ProfileManager;
import eventnotifier.en.en.utils.utils.LogUtil;
import eventnotifier.en.en.utils.views.FollowButton;

public class SearchUsersPresenter extends BasePresenter<SearchUsersView> {
    private final FollowManager followManager;
    private String currentUserId;
    private Activity activity;
    private ProfileManager profileManager;

    public SearchUsersPresenter(Activity activity) {
        super(activity);
        this.activity = activity;

        followManager = FollowManager.getInstance(context);
        currentUserId = FirebaseAuth.getInstance().getUid();
        profileManager = ProfileManager.getInstance(context.getApplicationContext());
    }

    public void onFollowButtonClick(int state, String targetUserId) {
        if (checkInternetConnection() && checkAuthorization()) {
            if (state == FollowButton.FOLLOW_STATE || state == FollowButton.FOLLOW_BACK_STATE) {
                followUser(targetUserId);
            } else if (state == FollowButton.FOLLOWING_STATE) {
                unfollowUser(targetUserId);
            }
        }
    }

    private void followUser(String targetUserId) {
        ifViewAttached(BaseView::showProgress);
        followManager.followUser(activity, currentUserId, targetUserId, success -> {
            ifViewAttached(view -> {
                view.hideProgress();
                if (success) {
                    view.updateSelectedItem();
                } else {
                    LogUtil.logDebug(TAG, "followUser, success: " + false);
                }
            });
        });
    }

    public void unfollowUser(String targetUserId) {
        ifViewAttached(BaseView::showProgress);
        followManager.unfollowUser(activity, currentUserId, targetUserId, success ->
                ifViewAttached(view -> {
                    view.hideProgress();
                    if (success) {
                        view.updateSelectedItem();
                    } else {
                        LogUtil.logDebug(TAG, "unfollowUser, success: " + false);
                    }
                }));
    }

    public void loadUsersWithEmptySearch() {
        search("");
    }

    public void search(String searchText) {
        if (checkInternetConnection()) {
            ifViewAttached(SearchUsersView::showLocalProgress);
            profileManager.search(searchText, list -> {
                ifViewAttached(view -> {
                    view.hideLocalProgress();
                    view.onSearchResultsReady(list);

                    if (list.isEmpty()) {
                        view.showEmptyListLayout();
                    }
                });

                LogUtil.logDebug(TAG, "search text: " + searchText);
                LogUtil.logDebug(TAG, "found items count: " + list.size());
            });
        } else {
            ifViewAttached(SearchUsersView::hideLocalProgress);
        }
    }

}
