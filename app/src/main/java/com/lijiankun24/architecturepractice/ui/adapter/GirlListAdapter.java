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
        holder.getTVGirlName().setText(girl.getName());
        holder.getTVGirlAge().setText(String.valueOf(girl.getAge()));
        Glide.with(holder.getIVGirlAvatar().getContext())
                .load(girl.getAvatar())
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

    class ViewHolder extends RecyclerView.ViewHolder {

        private View mRoot;

        private TextView mTVGirlName;

        private TextView mTVGirlAge;

        private ImageView mIVGirlAvatar;

        ViewHolder(View view) {
            super(view);

            mRoot = view.findViewById(R.id.rl_girl_item_root);

            mTVGirlName = (TextView) view.findViewById(R.id.tv_girl_name);

            mTVGirlAge = (TextView) view.findViewById(R.id.tv_girl_age);

            mIVGirlAvatar = (ImageView) view.findViewById(R.id.iv_girl_avatar);
        }

        public View getRoot() {
            return mRoot;
        }

        public TextView getTVGirlName() {
            return mTVGirlName;
        }

        public TextView getTVGirlAge() {
            return mTVGirlAge;
        }

        public ImageView getIVGirlAvatar() {
            return mIVGirlAvatar;
        }
    }
}
