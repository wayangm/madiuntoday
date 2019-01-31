package net.winnerawan.madiun.ui.dbhcht;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.facebook.shimmer.ShimmerFrameLayout;
import net.winnerawan.madiun.R;
import net.winnerawan.madiun.data.network.model.Category;
import net.winnerawan.madiun.data.network.model.Post;
import net.winnerawan.madiun.di.component.ActivityComponent;
import net.winnerawan.madiun.ui.adapter.PostAdapter;
import net.winnerawan.madiun.ui.base.BaseFragment;
import net.winnerawan.madiun.ui.content_news.ContentNewsFragment;
import net.winnerawan.madiun.utils.AppConstants;

import javax.inject.Inject;
import java.util.List;

public class DBHCHTFragment extends BaseFragment implements DBHCHTView, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    DBHCHTMvpPresenter<DBHCHTView> presenter;

    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    PostAdapter adapter;
    @BindView(R.id.content_news_srv)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recycler_news)
    RecyclerView mRecyclerNews;
    @BindView(R.id.shimmer)
    ShimmerFrameLayout mShimmer;
    private Category category;

    public static DBHCHTFragment newInstance() {

        Bundle args = new Bundle();

        DBHCHTFragment fragment = new DBHCHTFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content_news, container, false);
        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            presenter.onAttach(this);
            refreshLayout.setOnRefreshListener(this);
            refreshLayout.setColorSchemeResources(R.color.colorAccent);
        }
        return view;
    }

    @Override
    protected void setUp(View view) {
        category = new Category();
        category.setId(105);
        category.setName("DBH CHT");
        presenter.getDbhCht(category);
    }

    @Override
    public void setDisableRefreshLayout() {
        if (refreshLayout.isRefreshing()) refreshLayout.setRefreshing(false);
    }

    @Override
    public void setEnableRefreshLayout() {
        if (!refreshLayout.isRefreshing()) refreshLayout.setRefreshing(true);
    }

    @Override
    public void onRefresh() {
        if (category != null) presenter.getDbhCht(category);
    }

    @Override
    public void showContents(List<Post> posts) {
        mRecyclerNews.setLayoutManager(mLayoutManager);
        adapter.addItems(posts);
        mRecyclerNews.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        mShimmer.startShimmer();
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmer.startShimmer();
    }

    @Override
    public void stopShimmer() {
        mShimmer.stopShimmer();
        mShimmer.setVisibility(View.GONE);
    }
}