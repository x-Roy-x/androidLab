package com.example.lab1;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WelcomeFragment extends Fragment  {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private LinearLayout linearLayout;
    private HospitalAdapter hospitalAdapter;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_welcome, container, false);

        initViews();
        registerNetworkMonitoring();
        showHospitalList();

        return view;
    }

    private void registerNetworkMonitoring() {
        final IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        final NetworkChangeReceiver receiver = new NetworkChangeReceiver(linearLayout);
        Objects.requireNonNull(getActivity()).registerReceiver(receiver, filter);
    }

    private void initViews(){
        recyclerView = view.findViewById(R.id.data_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        linearLayout = view.findViewById(R.id.main);
        swipeRefreshLayout = view.findViewById(R.id.data_list_swipe_refresh);
        updateHospitalData();
    }

    private  void showHospitalList(){
        final ApiService apiService = getApplication().getApiService ();
        final Call<List<Hospital>> call = apiService.getHospitalData();

        call.enqueue(new Callback<List<Hospital>>() {
            @Override
            public void onResponse(final Call<List<Hospital>> call, final Response<List<Hospital>> response) {
                hospitalAdapter = new HospitalAdapter(getActivity(), response.body());
                recyclerView.setAdapter(hospitalAdapter);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Hospital>> call, Throwable t) {
                Snackbar.make(linearLayout, getString(R.string.error_update), Snackbar.LENGTH_LONG).show();
            }
        });
    }
    private void updateHospitalData(){
        swipeRefreshLayout.setOnRefreshListener(() -> {
                    showHospitalList();
                    swipeRefreshLayout.setRefreshing(true);
                }
        );
    }
    private ApplicationEx getApplication() {
        return ((ApplicationEx) Objects.requireNonNull(getActivity()).getApplication());
    }
}