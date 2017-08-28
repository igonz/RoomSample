package com.gkproggy.room_implementation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by greatkiller on 8/27/2017.
 */

public class UserAdpater extends RecyclerView.Adapter<UserAdpater.UserHolder> {

    private List<User> users = new ArrayList<>();

    @Override
    public UserAdpater.UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false));
    }

    public void addAllUser(List<User> users){
        this.users.addAll(users);
        notifyDataSetChanged();
    }

    public void clear(){
        users.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(UserAdpater.UserHolder holder, int position) {
        User user = users.get(position);
        holder.nameView.setText(user.getFirstName() + " " + user.getLastName());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class UserHolder extends RecyclerView.ViewHolder {
        TextView nameView;
        public UserHolder(View itemView) {
            super(itemView);
            nameView = (TextView) itemView.findViewById(R.id.name_view);
        }
    }
}
