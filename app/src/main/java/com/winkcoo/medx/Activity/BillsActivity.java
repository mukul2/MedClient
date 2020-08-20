package com.winkcoo.medx.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.winkcoo.medx.R;
import com.winkcoo.medx.adapter.BillsLitsAdapterAdmin;
import com.winkcoo.medx.api.Api;
import com.winkcoo.medx.api.ApiListener;
import com.winkcoo.medx.model.BillItem;
import com.winkcoo.medx.model.BillSummery;
import com.winkcoo.medx.widgets.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.winkcoo.medx.Data.Data.CURRENCY_USD_SIGN;
import static com.winkcoo.medx.Data.Data.SESSION_MANAGER;
import static com.winkcoo.medx.Data.DataStore.TOKEN;

public class BillsActivity extends BaseActivity implements ApiListener.UserBillSummeryDownloadListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_due)
    TextView tv_due;
    @BindView(R.id.tv_paid)
    TextView tv_paid;
    @BindView(R.id.tv_total)
    TextView tv_total;
    @BindView(R.id.tv_show_bills)
    TextView tv_show_bills;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills);
        ButterKnife.bind(this);
        Api.getInstance().yearlySingleUserBillSummery(TOKEN, "" + SESSION_MANAGER.getUserId(), "2019", this);
        tv_show_bills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Api.getInstance().yearlySingleUserBillList(TOKEN, "" + SESSION_MANAGER.getUserId(), "2019", new ApiListener.UserBillDownloadListener() {
                    @Override
                    public void onUserBillDownloadSuccess(List<BillItem> list) {
                        if (list.size()>0){
                            BillsLitsAdapterAdmin mAdapter = new BillsLitsAdapterAdmin(list);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                            recyclerView.setLayoutManager(mLayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(mAdapter);
                            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                                    recyclerView.VERTICAL,false);
                            recyclerView.addItemDecoration(dividerItemDecoration);
                            tv_show_bills.setVisibility(View.GONE);

                        }else {
                            tv_show_bills.setText("No Bill for You");
                        }
                    }

                    @Override
                    public void onUserBillDownloadFailed(String msg) {

                    }
                });

            }
        });

    }

    @Override
    public void onUserBillSummeryDownloadSuccess(BillSummery list) {
        tv_total.setText("" + list.getTotal() + CURRENCY_USD_SIGN);
        tv_due.setText("" + list.getDue() +CURRENCY_USD_SIGN);
        tv_paid.setText("" + list.getPaid() + CURRENCY_USD_SIGN);

    }

    @Override
    public void onUserBillSummeryDownloadFailed(String msg) {

    }

    public void back(View view) {

        onBackPressed();
    }
}
