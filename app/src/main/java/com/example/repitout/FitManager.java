/*
package com.example.repitout;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;

public class FitManager {
    private void buildClient() {
        GoogleApiClient fitApiClient = new GoogleApiClient.Builder(application)
                .addApi(Fitness.HISTORY_API)
                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        connectionResultPublishSubject.onNext(ConnectionUpdate.onConnected());
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        connectionResultPublishSubject.onNext(ConnectionUpdate.onSuspended(i));
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult result) {
                        connectionResultPublishSubject.onNext(ConnectionUpdate.onFailed(result));
                    }
                })
                .build();
    }
}
*/
