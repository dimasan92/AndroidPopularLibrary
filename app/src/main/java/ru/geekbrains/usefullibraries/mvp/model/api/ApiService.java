package ru.geekbrains.usefullibraries.mvp.model.api;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;
import ru.geekbrains.usefullibraries.mvp.model.entity.Repository;
import ru.geekbrains.usefullibraries.mvp.model.entity.User;

public interface ApiService {

    @GET("users/{user}")
    Observable<User> getUser(@Path("user") String userName);

    @GET
    Observable<List<Repository>> getUserRepos(@Url String url);
}
