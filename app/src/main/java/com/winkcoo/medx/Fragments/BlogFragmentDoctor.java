package com.winkcoo.medx.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.winkcoo.medx.R;
import com.winkcoo.medx.adapter.BlogAdapterPatient;
import com.winkcoo.medx.adapter.BlogCategoryHorizontalAdapter;
import com.winkcoo.medx.api.Api;
import com.winkcoo.medx.api.ApiListener;
import com.winkcoo.medx.model.BlogCategoryNameID;
import com.winkcoo.medx.model.BlogCategorySelectedBoolean;
import com.winkcoo.medx.model.BlogModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.winkcoo.medx.Data.DataStore.TOKEN;


public class BlogFragmentDoctor extends Fragment implements ApiListener.BlogCategoryDownloadListener {
    View v;
    Context context;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.recycler_viewCategory)
    RecyclerView recycler_viewCategory;

    public static List<BlogCategorySelectedBoolean> list_ = new ArrayList<>();

    public static BlogFragmentDoctor newInstance() {
        BlogFragmentDoctor fragment = new BlogFragmentDoctor();
        return fragment;
    }

    public BlogFragmentDoctor() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.blog_fragment_doc, container, false);
        context = v.getContext();

        ButterKnife.bind(this, v);
        //List<BlogModel>
        Api.getInstance().BlogCategoryNameID(TOKEN, this);


        return v;
    }


    @Override
    public void onBlogCategoryDownloadSuccess(List<BlogCategoryNameID> list) {
        list_.clear();


        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                list_.add(new BlogCategorySelectedBoolean(false, list.get(i)));
            }
            list_.get(0).setSelected(true);
            BlogCategoryHorizontalAdapter mAdapter = new BlogCategoryHorizontalAdapter();
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            recycler_viewCategory.setLayoutManager(layoutManager);
            recycler_viewCategory.setItemAnimator(new DefaultItemAnimator());
            recycler_viewCategory.setAdapter(mAdapter);

            ApiListener.BlogDownloadListener listener = new ApiListener.BlogDownloadListener() {
                @Override
                public void onBlogDownloaSuccess(List<BlogModel> list) {
                    BlogAdapterPatient mAdapter = new BlogAdapterPatient(list);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                    recycler_view.setLayoutManager(mLayoutManager);
                    recycler_view.setItemAnimator(new DefaultItemAnimator());
                    recycler_view.setAdapter(mAdapter);
                }

                @Override
                public void onBlogDownloaSuccessFailed(String msg) {
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

                }
            };
            //  Api.getInstance().blogsDownload(TOKEN, "" + list_.get(0).getBlogCategoryNameID().getId(), listener);

            mAdapter.setBlogCategorySelectListener(new BlogCategoryHorizontalAdapter.BlogCategorySelectListener() {
                @Override
                public void onSelected(int i) {
                    Api.getInstance().blogsDownload(TOKEN, "" + i, listener);

                }
            });


        }


    }

    @Override
    public void onBlogCategoryDownloadFailed(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }
}
