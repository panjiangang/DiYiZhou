package app.my.com.diyizhou.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.anye.greendao.gen.DaoMaster;
import com.anye.greendao.gen.DaoSession;
import com.anye.greendao.gen.UserDao;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import app.my.com.diyizhou.EventBusActivity;

import app.my.com.diyizhou.GitHubService;
import app.my.com.diyizhou.MessageEvent;

import app.my.com.diyizhou.R;
import app.my.com.diyizhou.bean.Bean;

import app.my.com.diyizhou.bean.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by lenovo on 2017/12/02.
 */

public class HomePage extends Fragment {

    private RecyclerView rv;
    private UserDao userDao;
    private List<Bean.DataBean> list = new ArrayList<>();
    private LinearLayoutManager manager;
    private List<User> users = new ArrayList<>();
    boolean s;
    boolean a=true;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_homepage, null);

        rv = view.findViewById(R.id.rv);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);//EventBus初始化
        s = isNetworkConnected(getActivity());
        Toast.makeText(getActivity(), "s为:"+s, Toast.LENGTH_SHORT).show();

        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getActivity(), "db", null);
        SQLiteDatabase sqLiteDatabase = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(sqLiteDatabase);
        DaoSession daoSession = daoMaster.newSession();
        userDao = daoSession.getUserDao();

        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(manager);
        if (s) {
            Toast.makeText(getActivity(), "网络yi连接", Toast.LENGTH_SHORT).show();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.zhaoapi.cn/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            GitHubService gitHubService = retrofit.create(GitHubService.class);

            Call<Bean> call = gitHubService.ListRepos("product");

            call.enqueue(new Callback<Bean>() {
                @Override
                public void onResponse(Call<Bean> call, Response<Bean> response) {
                    Bean bean = response.body();
                    list = bean.getData();

                    for (int i = 0; i < list.size(); i++) {

                        //将网络上请求到的 数据 添加到数据库
                        userDao.insert(new User(null, list.get(i).getName()));
                    }

                    MyAdapter adapter = new MyAdapter();
                    rv.setAdapter(adapter);

                }

                @Override
                public void onFailure(Call<Bean> call, Throwable t) {
                    System.out.println("失败");
                }
            });
        } else {
            Toast.makeText(getActivity(), "网络未连接", Toast.LENGTH_SHORT).show();
            System.out.println("sjk");
            users = userDao.loadAll();
            MMyAdapter myAdapter = new MMyAdapter();
            rv.setAdapter(myAdapter);
        }

    }


    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item, parent, false));

            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {

            holder.te1.setText(list.get(position).getName());
//            holder.te2.setText(list.get(position).getCreatetime());
//
//            Uri uri = Uri.parse(list.get(position).getIcon());
//            RoundingParams roundingParams = RoundingParams.fromCornersRadius(5f);
//            roundingParams.setRoundAsCircle(true);
//            holder.img.getHierarchy().setRoundingParams(roundingParams);
//            holder.img.setImageURI(uri);


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), EventBusActivity.class);
                    intent.putExtra("url", list.get(position).getIcon());
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return list == null ? 0 : list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.sdv)
            SimpleDraweeView img;
            @BindView(R.id.te1)
            TextView te1;
            @BindView(R.id.te2)
            TextView te2;

            public MyViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

    }

    @Subscribe(sticky = true)
    public void onMessageEvent(MessageEvent event) {
        //打印EventBus  的
        System.out.println("event NetActivity = " + event.tag);
        //吐司
        Toast.makeText(getActivity(), "EventBus:" + event.tag, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    class MMyAdapter extends RecyclerView.Adapter<MMyAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.item, parent, false));

            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {

            holder.te1.setText(users.get(position).getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), EventBusActivity.class);
                    intent.putExtra("url", users.get(position).getId());
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return users == null ? 0 : users.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.sdv)
            SimpleDraweeView img;
            @BindView(R.id.te1)
            TextView te1;
            @BindView(R.id.te2)
            TextView te2;

            public MyViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

    }

}
