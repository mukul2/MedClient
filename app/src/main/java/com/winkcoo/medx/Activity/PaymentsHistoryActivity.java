package com.winkcoo.medx.Activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.winkcoo.medx.Fragments.AllCollectionFragment;
import com.winkcoo.medx.Fragments.AllWidthdrawFragment;
import com.winkcoo.medx.R;
import com.winkcoo.medx.api.Api;
import com.winkcoo.medx.api.ApiListener;
import com.winkcoo.medx.model.AllCollectionWithdraModel;
import com.google.android.material.tabs.TabLayout;
import com.sinch.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.winkcoo.medx.Data.Data.CURRENCY_USD_SIGN;
import static com.winkcoo.medx.Data.DataStore.TOKEN;
import static com.winkcoo.medx.Data.DataStore.USER_ID;
import static com.winkcoo.medx.Data.DataStore.USER_TYPE;

public class PaymentsHistoryActivity extends BaseActivity {

    Context context = this;
    @BindView(R.id.tv_total_collection)
    TextView tv_total_collection;
    @BindView(R.id.tv_total_width)
    TextView tv_total_width;
    @BindView(R.id.tv_remaining)
    TextView tv_remaining;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tabs)
    TabLayout tabs;
    public  static  AllCollectionWithdraModel ALL_COLLECTION_WIDTHDRAWL ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments_history);
        ButterKnife.bind(this);

        String USER_TYPE_;
        if (USER_TYPE.equals("d")) {
            USER_TYPE_ = "doctor";
        } else {
            USER_TYPE_ = "patient";
        }
        Api.getInstance().get_payment_list(TOKEN, USER_ID, USER_TYPE_, new ApiListener.PaymentListDownloadListener() {
            @Override
            public void onPaymentListDownloadSuccess(AllCollectionWithdraModel response) {
                Gson gson =  new Gson() ;
                Log.i("mkl",gson.toJson(response));
                tv_total_collection.setText(""+Math.floor( response.getTotal_bill())+ CURRENCY_USD_SIGN);
                tv_total_collection.setText(""+ String.format("%.2f", response.getTotal_bill())+ CURRENCY_USD_SIGN);
                tv_total_width.setText(""+Math.floor(response.getAll_widthdraw())+ CURRENCY_USD_SIGN);
                //tv_total_width.setText(""+String.format("%.2f", response.getAll_widthdraw())+ CURRENCY_USD_SIGN);
                tv_remaining.setText(""+Math.floor(response.getTotal_bill()-response.getAll_widthdraw())+ CURRENCY_USD_SIGN);
                tv_remaining.setText(""+String.format("%.2f", response.getTotal_bill()-response.getAll_widthdraw())+ CURRENCY_USD_SIGN);
                ALL_COLLECTION_WIDTHDRAWL = response ;
                setupViewPager(viewpager);
                int selectedColor=context.getResources().getColor(R.color.black);
                int normal=context.getResources().getColor(R.color.textText);
                tabs.setTabTextColors(normal,selectedColor);
                tabs.setupWithViewPager(viewpager);
                //Toast.makeText(context, ""+response.getTotal_bill(), Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onPaymentListDownloadFailed(String msg) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();


            }
        });

    }
    private void setupViewPager(ViewPager viewPager) {
      ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AllCollectionFragment(), "Collections");
        adapter.addFragment(new AllWidthdrawFragment(), "Withdrawal");
        viewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }



}
