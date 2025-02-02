package com.example.myapplication.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.myapplication.BatDauNguPhapActivity;
import com.example.myapplication.BatDauTuVungActivity;
import com.example.myapplication.QuanLyActivity;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    String phanLoai;
    CardView cvQuanLy,cvTuVung,cvNguPhap;
    ImageView ivAvatar;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        cvQuanLy = view.findViewById(R.id.cvQuanLy);
        cvNguPhap = view.findViewById(R.id.cvNguPhap);
        cvTuVung = view.findViewById(R.id.cvTuVung);
        ivAvatar = view.findViewById(R.id.ivAvatar);
        TextView tvTongdiem = view.findViewById(R.id.tvTongDiem);
        SharedPreferences preferences = getContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        String tenDangNhap = preferences.getString("tendangnhap","");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("DanhSachTaiKhoan");
        Query query = reference.orderByChild("tenDangNhap").equalTo(tenDangNhap);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int tongDiemFireBase = snapshot.child(tenDangNhap).child("tongDiem").getValue(Integer.class);
                String linkAnhFireBase = snapshot.child(tenDangNhap).child("linkAnh").getValue(String.class);
                tvTongdiem.setText(""+tongDiemFireBase);
                Glide.with(getActivity()).load(linkAnhFireBase).into(ivAvatar);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        phanLoai = preferences.getString("phanloai","");
        QuanLy();
        cvQuanLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), QuanLyActivity.class);
                startActivity(intent);
            }
        });
        cvNguPhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), BatDauNguPhapActivity.class);
                startActivity(intent);
            }
        });
        cvTuVung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), BatDauTuVungActivity.class));
            }
        });
        return view;
    }
    private void QuanLy(){
        if(phanLoai.equalsIgnoreCase("khách hàng")){
            cvQuanLy.setVisibility(View.GONE);
        }else {
            cvQuanLy.setVisibility(View.VISIBLE);
        }
    }
}