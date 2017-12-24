package com.usermindarchive.h.friendschat.Model.Dagger;

import android.content.Context;

import com.usermindarchive.h.friendschat.Model.MainClass.Firebase.Firebase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class FirebaseDependency {
Context context;

public FirebaseDependency(Context context){
    this.context=context;
}

@Provides
@Singleton
Context provideContext(){
    return context;
}

@Provides
@Singleton
public Firebase getFirebase(Context context){
    return new Firebase(context);
}
}
