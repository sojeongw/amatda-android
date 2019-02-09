/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.amatda.tasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amatda.R;
import com.amatda.addedittask.AddEditTaskActivity;
import com.amatda.data.MockPreparationData;
import com.amatda.tasks.domain.model.Task;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Display a grid of {@link Task}s. User can choose to view all, active or completed tasks.
 */
public class CarrierMainFragment extends Fragment implements TasksContract.View {

    private TasksContract.Presenter mPresenter;
    private PreparationBeforeListAdapter mBeforeListAdapter;
    private ArrayList<MockPreparationData> mDatas;

    FloatingActionButton fabCarrierAddPreparation;
    RecyclerView recyclerCarrierMainBeforeList;

    public CarrierMainFragment() {
        // Requires empty public constructor
    }

    public static CarrierMainFragment newInstance() {
        return new CarrierMainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatas = new ArrayList<>();
        mPresenter.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        RealmResults<MockPreparationData> realmResults = Realm.getDefaultInstance().where(MockPreparationData.class).findAll();
        for (MockPreparationData data : realmResults) {
            mDatas.add(data);
        }
    }

    @Override
    public void setPresenter(@NonNull TasksContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carrier_main, container, false);

        // Set up floating action button
        fabCarrierAddPreparation = getActivity().findViewById(R.id.fabCarrierAddPreparation);

        recyclerCarrierMainBeforeList = view.findViewById(R.id.recyclerCarrierMainBeforeList);

        fabCarrierAddPreparation.setImageResource(R.drawable.ic_add);
        fabCarrierAddPreparation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.addNewTask();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        mBeforeListAdapter = new PreparationBeforeListAdapter();
        recyclerCarrierMainBeforeList.setAdapter(mBeforeListAdapter);
        recyclerCarrierMainBeforeList.setLayoutManager(layoutManager);
        mBeforeListAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void showTasks(List<Task> tasks) {
        //
    }

    @Override
    public void showAddTask() {
        Intent intent = new Intent(getContext(), AddEditTaskActivity.class);
        startActivityForResult(intent, AddEditTaskActivity.REQUEST_ADD_TASK);
    }

    private class PreparationBeforeListAdapter extends RecyclerView.Adapter<PreparationViewHolder> {

        @Override
        public PreparationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_preparation, parent, false);
            PreparationViewHolder viewHolder = new PreparationViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(PreparationViewHolder holder, int position) {
            holder.setData(mDatas.get(position));
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }
    }
}
