package eventnotifier.en.en.utils.main.main;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import eventnotifier.en.en.R;
import eventnotifier.en.en.utils.adapters.PostsAdapter;
import eventnotifier.en.en.utils.main.base.BaseActivity;
import eventnotifier.en.en.utils.main.login.LoginActivity;
import eventnotifier.en.en.utils.main.post.createPost.CreatePostActivity;
import eventnotifier.en.en.utils.main.postDetails.PostDetailsActivity;
import eventnotifier.en.en.utils.main.profile.ProfileActivity;
import eventnotifier.en.en.utils.main.search.SearchActivity;
import eventnotifier.en.en.utils.model.Post;
import eventnotifier.en.en.utils.utils.AnimationUtils;

public class MainActivity extends BaseActivity<MainView, MainPresenter> implements MainView {

    private PostsAdapter postsAdapter;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    private TextView newPostsCounterTextView;
    private boolean counterAnimationInProgress = false;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initContentView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.updateNewPostCounter();
    }

    @NonNull
    @Override
    public MainPresenter createPresenter() {
        if (presenter == null) {
            return new MainPresenter(this);
        }
        return presenter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ProfileActivity.CREATE_POST_FROM_PROFILE_REQUEST:
                    refreshPostList();
                    break;
                case CreatePostActivity.CREATE_NEW_POST_REQUEST:
                    presenter.onPostCreated();
                    break;

                case PostDetailsActivity.UPDATE_POST_REQUEST:
                    presenter.onPostUpdated(data);
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        attemptToExitIfRoot(floatingActionButton);
    }

    public void refreshPostList() {
        postsAdapter.loadFirstPage();
        if (postsAdapter.getItemCount() > 0) {
            recyclerView.scrollToPosition(0);
        }
    }

    @Override
    public void removePost() {
        postsAdapter.removeSelectedPost();
    }

    @Override
    public void updatePost() {
        postsAdapter.updateSelectedPost();
    }

    @Override
    public void showCounterView(int count) {
        AnimationUtils.showViewByScaleAndVisibility(newPostsCounterTextView);
        String counterFormat = getResources().getQuantityString(R.plurals.new_posts_counter_format, count, count);
        newPostsCounterTextView.setText(String.format(counterFormat, count));
    }

    private void initContentView() {
        if (recyclerView == null) {
            progressBar = findViewById(R.id.progressBar);
            swipeContainer = findViewById(R.id.swipeContainer);

            initFloatingActionButton();
            initPostListRecyclerView();
            initPostCounter();
        }
    }

    private void initFloatingActionButton() {
        floatingActionButton = findViewById(R.id.addNewPostFab);
        if (floatingActionButton != null) {
            floatingActionButton.setOnClickListener(v -> presenter.onCreatePostClickAction(floatingActionButton));
        }
    }

    private void initPostListRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        postsAdapter = new PostsAdapter(this, swipeContainer);
        postsAdapter.setCallback(new PostsAdapter.Callback() {
            @Override
            public void onItemClick(final Post post, final View view) {
                presenter.onPostClicked(post, view);
            }

            @Override
            public void onListLoadingFinished() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onAuthorClick(String authorId, View view) {
                //openProfileActivity(authorId, view);
            }

            @Override
            public void onCanceled(String message) {
                progressBar.setVisibility(View.GONE);
                showToast(message);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setAdapter(postsAdapter);
        postsAdapter.loadFirstPage();
    }

    private void initPostCounter() {
        newPostsCounterTextView = findViewById(R.id.newPostsCounterTextView);
        newPostsCounterTextView.setOnClickListener(v -> refreshPostList());

        presenter.initPostCounter();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                hideCounterView();
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    public void hideCounterView() {
        if (!counterAnimationInProgress && newPostsCounterTextView.getVisibility() == View.VISIBLE) {
            counterAnimationInProgress = true;
            AlphaAnimation alphaAnimation = AnimationUtils.hideViewByAlpha(newPostsCounterTextView);
            alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    counterAnimationInProgress = false;
                    newPostsCounterTextView.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            alphaAnimation.start();
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void openPostDetailsActivity(Post post, View v) {
        Intent intent = new Intent(MainActivity.this, PostDetailsActivity.class);
        intent.putExtra(PostDetailsActivity.POST_ID_EXTRA_KEY, post.getId());

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            View imageView = v.findViewById(R.id.postImageView);
            View authorImageView = v.findViewById(R.id.authorImageView);

            ActivityOptions options = ActivityOptions.
                    makeSceneTransitionAnimation(MainActivity.this,
                            new android.util.Pair<>(imageView, getString(R.string.post_image_transition_name)),
                            new android.util.Pair<>(authorImageView, getString(R.string.post_author_image_transition_name))
                    );
            startActivityForResult(intent, PostDetailsActivity.UPDATE_POST_REQUEST, options.toBundle());
        } else {
            startActivityForResult(intent, PostDetailsActivity.UPDATE_POST_REQUEST);
        }
    }

    public void showFloatButtonRelatedSnackBar(int messageId) {
        showSnackBar(floatingActionButton, messageId);
    }

    @Override
    public void openCreatePostActivity() {
        Intent intent = new Intent(this, CreatePostActivity.class);
        startActivityForResult(intent, CreatePostActivity.CREATE_NEW_POST_REQUEST);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void openProfileActivity(String userId, View view) {
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        intent.putExtra(ProfileActivity.USER_ID_EXTRA_KEY, userId);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && view != null) {

            View authorImageView = view.findViewById(R.id.authorImageView);

            ActivityOptions options = ActivityOptions.
                    makeSceneTransitionAnimation(MainActivity.this,
                            new android.util.Pair<>(authorImageView, getString(R.string.post_author_image_transition_name)));
            startActivityForResult(intent, ProfileActivity.CREATE_POST_FROM_PROFILE_REQUEST, options.toBundle());
        } else {
            startActivityForResult(intent, ProfileActivity.CREATE_POST_FROM_PROFILE_REQUEST);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
                //presenter.onProfileMenuActionClicked();
                userLogout();
                return true;

           // case R.id.followingPosts:
                //Intent followingPosts = new Intent(this, FollowingPostsActivity.class);
               // startActivity(followingPosts);
                //Toast.makeText(MainActivity.this,"Cannot Launch Profile Activity",Toast.LENGTH_SHORT).show();
               // return true;

            case R.id.search:
                Intent searchIntent = new Intent(this, SearchActivity.class);
                startActivity(searchIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void userLogout() {

        FirebaseAuth.getInstance().signOut();
        Intent loginIntent=new Intent(MainActivity.this,LoginActivity.class);
        startActivity(loginIntent);

    }
}