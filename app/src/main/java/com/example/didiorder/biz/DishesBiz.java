package com.example.didiorder.biz;

import android.content.Context;

import com.example.didiorder.bean.Dishes;
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
 * Created by qqq34 on 2016/1/19.
 */
public class DishesBiz implements IDishesBiz {
    @Override
    public Observable<Dishes> updata(Context context, String name, Integer price, File file) {
        Observable<Dishes> observable = Observable.create(subscriber -> {
            if (file!=null){
                BmobFile bmobFile = new BmobFile(file);
                bmobFile.uploadblock(context, new UploadFileListener() {
                    @Override
                    public void onSuccess() {

                        Dishes dishes = new Dishes();
                        dishes.setName(name);
                        dishes.setPrice(price);
                        dishes.setImageUrl(bmobFile.getFileUrl(context));
                        dishes.save(context, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                subscriber.onNext(dishes);
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
                Dishes dishes = new Dishes();
                dishes.setPrice(price);
                dishes.setName(name);
              dishes.save(context, new SaveListener() {
                  @Override
                  public void onSuccess() {
                      subscriber.onNext(dishes);
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
