package ru.geekbrains.usefullibraries.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.geekbrains.usefullibraries.R;
import ru.geekbrains.usefullibraries.mvp.presenter.IReposListPresenter;
import ru.geekbrains.usefullibraries.mvp.view.ReposListView;

public final class ReposListAdapter extends RecyclerView.Adapter<ReposListAdapter.RepoHolder>
        implements ReposListView {

    private final IReposListPresenter presenter;

    ReposListAdapter(IReposListPresenter presenter) {
        this.presenter = presenter;
        presenter.attachView(this);
    }

    @NonNull
    @Override
    public RepoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RepoHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_repo,
                viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RepoHolder repoHolder, int position) {
        presenter.bindViewAt(position, repoHolder);
    }

    @Override
    public int getItemCount() {
        return presenter.getReposCount();
    }

    @Override
    public void refreshReposList() {
        notifyDataSetChanged();
    }

    final class RepoHolder extends RecyclerView.ViewHolder implements ReposListView.RepoRowView {

        @BindView(R.id.tv_repo_name)
        TextView repoNameTextView;

        RepoHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setRepoName(final String name) {
            repoNameTextView.setText(name);
        }
    }
}
