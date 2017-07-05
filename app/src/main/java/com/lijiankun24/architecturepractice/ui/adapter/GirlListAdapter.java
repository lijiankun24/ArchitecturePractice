package com.lijiankun24.architecturepractice.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lijiankun24.architecturepractice.R;
import com.lijiankun24.architecturepractice.db.entity.Girl;
import com.lijiankun24.architecturepractice.ui.listener.OnGirlClickListener;

import java.util.List;

public class GirlListAdapter extends RecyclerView.Adapter<GirlListAdapter.ViewHolder> {

    private List<Girl> mGirlList = null;

    private OnGirlClickListener mGirlClickListener = null;

    public GirlListAdapter(OnGirlClickListener listener) {
        mGirlClickListener = listener;
    }

    public void setGirlList(List<Girl> girlList) {
        mGirlList = girlList;
        notifyDataSetChanged();
//        if (mGirlList == null) {
//            mGirlList = girlList;
//            notifyDataSetChanged();
//        } else {
//
//        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_girl_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Girl girl = mGirlList.get(position);
        holder.mTVGirlName.setText(girl.getName());
        holder.mTVGirlAge.setText(girl.getAge());
        Glide.with(holder.mIVGirlAvatar.getContext())
                .load(girl.getAvatar())
                .error(R.drawable.ic_launcher)
                .centerCrop()
                .into(holder.mIVGirlAvatar);
        holder.mRoot.setOnClickListener(new View.OnClickListener() {
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

    class ViewHolder extends RecyclerView.ViewHolder {

        private final View mRoot;

        private final TextView mTVGirlName;

        private final TextView mTVGirlAge;

        private final ImageView mIVGirlAvatar;

        ViewHolder(View view) {
            super(view);

            mRoot = view.findViewById(R.id.rl_girl_item_root);

            mTVGirlName = view.findViewById(R.id.tv_girl_name);

            mTVGirlAge = view.findViewById(R.id.tv_girl_age);

            mIVGirlAvatar = view.findViewById(R.id.iv_girl_avatar);
        }
    }
}
