package app.my.com.diyizhou;


import app.my.com.diyizhou.bean.Bean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by lenovo on 2017/11/29.
 */

public interface GitHubService {
    //product/getCatagory
    @GET("{user}/getCatagory")       //把参数user  替换到 {user}
    Call<Bean> ListRepos(@Path("user") String user);
}
