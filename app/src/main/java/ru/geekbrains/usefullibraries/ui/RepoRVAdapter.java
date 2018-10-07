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
import ru.geekbrains.usefullibraries.mvp.presenter.RepoListPresenter;
import ru.geekbrains.usefullibraries.mvp.view.RepoRowView;

final class RepoRVAdapter extends RecyclerView.Adapter<RepoRVAdapter.ViewHolder> {

    private final RepoListPresenter presenter;

    RepoRVAdapter(RepoListPresenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(
                viewGroup.getContext()).inflate(R.layout.item_repo, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        presenter.bindRepoListRow(position, viewHolder);
    }

    @Override
    public int getItemCount() {
        return presenter.getRepoCount();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements RepoRowView {

        @BindView(R.id.tv_title)
        TextView titleTextView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setTitle(String title) {
            titleTextView.setText(title);
        }
    }
}
