package com.example.didiorder.biz;

import android.content.Context;
import android.util.Log;

import com.example.didiorder.bean.User;
import com.example.didiorder.tools.ErrorList;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import rx.Observable;

/**
 * Created by qqq34 on 2016/1/11.
 */
public class UserBiz implements IUserBiz {  //使用接口能够让 高层逻辑解耦，使耦合发生在抽象层
    private final String TAG = "UserBiz";
    @Override
    public Observable<User> login(Context context, String username, String password) { //实现 接口的方法
        Observable<User> observable = Observable.create(subscriber -> {   //Rxjava 响应式编程，实现逻辑和界面的解耦，数据链式处理。
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.login(context, new SaveListener() {
                @Override
                public void onSuccess() {
                    User userInfo = BmobUser.getCurrentUser(context, User.class);
                    if (userInfo != null) {
                        subscriber.onNext(userInfo);
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new Throwable("登录失败"));
                    }
                }

                @Override
                public void onFailure(int i, String s) {
                    subscriber.onError(new Throwable(new ErrorList().getErrorMsg(i)));
                    Log.d(TAG,""+i);
                }
            });
        });
        return observable;

    }

    @Override
    public Observable<User> updata(Context context, String name, String job, File file) { //更新个人资料
        Observable<User> observable = Observable.create(subscriber -> {
            if (file!=null){
                BmobFile bmobFile = new BmobFile(file);
                bmobFile.uploadblock(context, new UploadFileListener() {
                    @Override
                    public void onSuccess() {

                        User newUser = new User();
                        newUser.setJob(job);
                        newUser.setUser_image_url(bmobFile.getFileUrl(context));
                        newUser.setName(name);
                        User user = BmobUser.getCurrentUser(context, User.class);
                        newUser.update(context,user.getObjectId(),new UpdateListener() {
                            @Override
                            public void onSuccess() {
                                subscriber.onNext(user);
                                subscriber.onCompleted();
                            }
                            @Override
                            public void onFailure(int code, String msg) {
                                subscriber.onError(new Throwable(new ErrorList().getErrorMsg(code)));
                            }
                        });

                    }

                    @Override
                    public void onFailure(int i, String s) {
                        subscriber.onError(new Throwable(new ErrorList().getErrorMsg(i)));
                    }
                });
            }else {
                User newUser = new User();
                newUser.setJob(job);
                newUser.setName(name);
                User user = BmobUser.getCurrentUser(context, User.class);
                newUser.update(context,user.getObjectId(),new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        subscriber.onNext(user);
                        subscriber.onCompleted();
                    }
                    @Override
                    public void onFailure(int code, String msg) {
                        subscriber.onError(new Throwable(new ErrorList().getErrorMsg(code)));
                    }
                });

            }
        });
        return observable;
    }
}
