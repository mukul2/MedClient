package com.winkcoo.medx.Activity;//package com.winkcoo.medx.Activity;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Build;
//import android.os.Bundle;
//
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.DefaultItemAnimator;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//
//import com.winkcoo.medx.R;
//import com.winkcoo.medx.adapter.DoctorsSearchAdapter;
//import com.winkcoo.medx.adapter.LastMessageAdapter;
//import com.winkcoo.medx.model.LastMsgModel;
//import com.winkcoo.medx.widgets.DividerItemDecoration;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.StringTokenizer;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
//import static com.winkcoo.medx.Data.DataStore.USER_ID;
//
//public class DrChatActivity extends AppCompatActivity {
//    DatabaseReference databaseReference;
//    List<String> datas = new ArrayList<>();
//    @BindView(R.id.edSearch)
//    EditText edSearch;
//    @BindView(R.id.recycler_view)
//    RecyclerView recycler_view;
//    List<LastMsgModel> list = new ArrayList<>();
//    Context context = this;
//    LastMessageAdapter mAdapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_dr_chat);
//        ButterKnife.bind(this);
//        setUpStatusbar();
//        initRecycler();
//        databaseReference = FirebaseDatabase.getInstance().getReference();
//
//        databaseReference.child("chat").child("lastMsg").child(USER_ID).addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                list.add(dataSnapshot.getValue(LastMsgModel.class));
//                mAdapter.notifyItemInserted(list.size() - 1);
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//
//    }
//
//    private void initRecycler() {
//        mAdapter = new LastMessageAdapter(list);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
//        recycler_view.setLayoutManager(mLayoutManager);
//        recycler_view.setItemAnimator(new DefaultItemAnimator());
//        recycler_view.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL, false));
//        recycler_view.setAdapter(mAdapter);
//    }
//
//    public void setUpStatusbar() {
//        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
//            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
//        }
//        if (Build.VERSION.SDK_INT >= 19) {
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        }
//        //make fully Android Transparent Status bar
//        if (Build.VERSION.SDK_INT >= 21) {
//            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
//    }
//
//    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
//        Window win = activity.getWindow();
//        WindowManager.LayoutParams winParams = win.getAttributes();
//        if (on) {
//            winParams.flags |= bits;
//        } else {
//            winParams.flags &= ~bits;
//        }
//        win.setAttributes(winParams);
//    }
//
//    public void back(View view) {
//        onBackPressed();
//    }
//
//    public void openChat(View view) {
//        startActivity(new Intent(this, ChatActivityDr.class));
//    }
//}
