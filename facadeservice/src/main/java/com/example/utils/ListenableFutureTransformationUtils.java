package com.example.utils;

import com.google.common.base.Functions;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.SettableListenableFuture;

import java.util.concurrent.Executor;

public class ListenableFutureTransformationUtils {

    public static <T> ListenableFuture<T> toGuava(
            org.springframework.util.concurrent.ListenableFuture<T> listenableFuture,
            Executor executor) {

        final SettableFuture<T> settableFuture = SettableFuture.create();

        listenableFuture.addCallback(new ListenableFutureCallback<T>() {

            @Override
            public void onSuccess(T result) {
                settableFuture.set(result);
            }

            @Override
            public void onFailure(Throwable ex) {
                settableFuture.setException(ex);
            }

        });

        return Futures.transform(settableFuture, Functions.identity(), executor);

    }

    public static <T> org.springframework.util.concurrent.ListenableFuture<T> toSpring(ListenableFuture<T> listenableFuture) {

        final SettableListenableFuture<T> settableListenableFuture = new SettableListenableFuture<>();
        Futures.addCallback(listenableFuture, new FutureCallback<T>() {
            @Override
            public void onSuccess(T result) {
                settableListenableFuture.set(result);
            }

            @Override
            public void onFailure(Throwable t) {
                settableListenableFuture.setException(t);
            }
        });

        return settableListenableFuture;

    }

}
