package com.example.citycyclerentals;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class ProfileFragment extends Fragment {

    private EditText  editNic, editName, editPhone, editPass, editComPass;
    private Button editBtn;
    private DatabaseHelper dbHelper;
    private String userNIC;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment profileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get NIC from SharedPreferences (assumes NIC was stored at login)
        SharedPreferences prefs = getActivity().getSharedPreferences("user_session", Context.MODE_PRIVATE);
        userNIC = prefs.getString("nic", null);

        if (userNIC == null) {
            Toast.makeText(getActivity(), "User NIC not found", Toast.LENGTH_SHORT).show();
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        editNic = view.findViewById(R.id.nic);
        editName = view.findViewById(R.id.name);
        editPhone = view.findViewById(R.id.phone);
        editPass = view.findViewById(R.id.password);
        editComPass = view.findViewById(R.id.comPassword);
        editBtn = view.findViewById(R.id.buttonEdit);

        dbHelper = new DatabaseHelper(getActivity());

        if (userNIC != null) {
            loadUserData();
        }

        // Edit button click
        editBtn.setOnClickListener(v -> updateUser());

        return view;
    }

    private void loadUserData() {
        Cursor cursor = dbHelper.getUserByNIC(userNIC);
        if (cursor != null && cursor.moveToFirst()) {
            editNic.setText(cursor.getString(cursor.getColumnIndexOrThrow("nic")));
            editName.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")));
            editPhone.setText(cursor.getString(cursor.getColumnIndexOrThrow("phone")));
            editPass.setText(cursor.getString(cursor.getColumnIndexOrThrow("password")));
            editComPass.setText(cursor.getString(cursor.getColumnIndexOrThrow("password")));
            cursor.close();
        } else {
            Toast.makeText(getActivity(), "User not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUser() {
        String nic = editNic.getText().toString();
        String name = editName.getText().toString();
        String phone = editPhone.getText().toString();
        String pass = editPass.getText().toString();
        String comPass = editComPass.getText().toString();

        if (!pass.equals(comPass)) {
            Toast.makeText(getActivity(), "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean result = dbHelper.updateUser(nic, name, phone, pass);
        if (result) {
            Toast.makeText(getActivity(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Update failed", Toast.LENGTH_SHORT).show();
        }
    }
}
