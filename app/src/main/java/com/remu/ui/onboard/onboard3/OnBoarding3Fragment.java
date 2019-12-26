package com.remu.ui.onboard.onboard3;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.remu.LoginActivity;
import com.remu.MainActivity;
import com.remu.OnboardingActivity;
import com.remu.POJO.FragmentChangeListener;
import com.remu.PermissionActivity;
import com.remu.R;

import org.jetbrains.annotations.NotNull;

public class OnBoarding3Fragment extends Fragment {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private FirebaseAuth mAuth;
    private OnBoarding3ViewModel onBoarding3ViewModel;
    private Button permissionButton;

    private FragmentActivity mActivity;

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);

        if (context instanceof Activity){
            mActivity = (FragmentActivity) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        onBoarding3ViewModel = ViewModelProviders.of(this).get(OnBoarding3ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_onboard3, container, false);

        initializeUI(root);

        permissionButton.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(mActivity, PermissionActivity.class);
                startActivity(intent);
                mActivity.finish();
            } else {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();

                if (user != null) {
                    Intent intent = new Intent(mActivity, MainActivity.class);
                    startActivity(intent);
                    mActivity.finish();
                } else {
                    Intent intent = new Intent(mActivity, LoginActivity.class);
                    startActivity(intent);
                    mActivity.finish();
                }
            }
        });

        return root;
    }

    private void initializeUI(View root) {
        permissionButton = root.findViewById(R.id.button_permission);
    }

}