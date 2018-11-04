package com.tony.tang.note.app;

import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.akaita.java.rxjava2debug.RxJava2Debug;
import com.tony.tang.note.domain.exception.RestApiErrorException;
import com.tony.tang.note.presenter.CleanView;

public abstract class CleanActivity extends BaseActivity implements CleanView {


    @Override
    public void handleError(Throwable throwable) {
        if (throwable instanceof RestApiErrorException) {
            switch (((RestApiErrorException) throwable).getStatusCode()) {
                case RestApiErrorException.UNAUTHORIZED:
                case RestApiErrorException.UPGRADE_REQUIRED:
                    createUpgradeDialog();
                    break;
                default:
                    onUnknownError(throwable);
            }
        } else {
            onUnknownError(throwable);
        }
    }

    private void onUnknownError(Throwable throwable) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
        RxJava2Debug.getEnhancedStackTrace(throwable);
    }


    private void createUpgradeDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage(getResources().getString(R.string.message_expired) + ".\n" +
//                getResources().getString(R.string.message_update) + ".")
//                .setPositiveButton(R.string.message_ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        navigateToUpgrater();
//                    }
//                }).build().show();
    }

    private void navigateToUpgrater() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=com.tony.tang.note"));
        startActivity(intent);
    }

    @Override
    public void initUI() {

    }
}
