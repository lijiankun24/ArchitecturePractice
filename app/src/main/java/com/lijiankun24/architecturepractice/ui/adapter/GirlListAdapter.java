package com.lijiankun24.architecturepractice.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lijiankun24.architecturepractice.R;
import com.lijiankun24.architecturepractice.data.local.db.entity.Girl;
import com.lijiankun24.architecturepractice.ui.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class GirlListAdapter extends RecyclerView.Adapter<GirlListAdapter.ViewHolder> {

    private OnItemClickListener<Girl> mGirlClickListener = null;

    private List<Girl> mGirlList = null;

    public GirlListAdapter(OnItemClickListener<Girl> listener) {
        mGirlClickListener = listener;
        mGirlList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_girl_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Girl girl = mGirlList.get(position);
        holder.getTVGirlName().setText(girl.getWho());
        holder.getTVGirlAge().setText(girl.getPublishedAt());
        Glide.with(holder.getIVGirlAvatar().getContext())
                .load(girl.getUrl())
                .error(R.drawable.ic_launcher)
                .centerCrop()
                .into(holder.getIVGirlAvatar());
        holder.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mGirlClickListener != null) {
                    mGirlClickListener.onClick(girl);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGirlList == null ? 0 : mGirlList.size();
    }

    public void setGirlList(List<Girl> girlList) {
        mGirlList.addAll(girlList);
        notifyDataSetChanged();
    }

    public void clearGirlList() {
        mGirlList.clear();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private View mRoot;

        private TextView mTVGirlName;

        private TextView mTVGirlAge;

        private ImageView mIVGirlAvatar;

        ViewHolder(View view) {
            super(view);
            mRoot = view.findViewById(R.id.rl_girl_item_root);
            mTVGirlName = view.findViewById(R.id.tv_girl_name);
            mTVGirlAge = view.findViewById(R.id.tv_girl_age);
            mIVGirlAvatar = view.findViewById(R.id.iv_girl_avatar);
        }

        private View getRoot() {
            return mRoot;
        }

        private TextView getTVGirlName() {
            return mTVGirlName;
        }

        private TextView getTVGirlAge() {
            return mTVGirlAge;
        }

        private ImageView getIVGirlAvatar() {
            return mIVGirlAvatar;
        }
    }
}
