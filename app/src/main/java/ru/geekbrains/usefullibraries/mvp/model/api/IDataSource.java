package ru.geekbrains.usefullibraries.mvp.model.api;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;
import ru.geekbrains.usefullibraries.mvp.model.entity.Repo;
import ru.geekbrains.usefullibraries.mvp.model.entity.User;

public interface IDataSource {

    @GET("/users/{user}")
    Observable<User> getUser(@Path("user") String username);

    @GET
    Observable<List<Repo>> getUserRepos(@Url String url);
}
