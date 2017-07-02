package com.tony_tang.android.demo.common.base;

import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.jordifierro.androidbase.domain.exception.RestApiErrorException;
import com.jordifierro.androidbase.presentation.view.base.CleanView;
import com.tony_tang.android.demo.R;

public abstract class CleanActivity extends BaseActivity implements CleanView {


    public void handleError(Throwable throwable) {
        if (throwable instanceof RestApiErrorException) {
            switch (((RestApiErrorException) throwable).getStatusCode()) {
                case RestApiErrorException.UNAUTHORIZED:
                case RestApiErrorException.UPGRADE_REQUIRED:
                    createUpgradeDialog();
                    break;
                default:
                    showMessage(throwable.getMessage());
            }
        } else Toast.makeText(context(), getResources().getString(R.string.message_error),
                Toast.LENGTH_LONG).show();
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
//                }).create().show();
    }

    private void navigateToUpgrater() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=com.jordifierro.androidbase"));
        startActivity(intent);
    }


    public void showMessage(String message) {
        Toast.makeText(context(), message, Toast.LENGTH_LONG).show();
    }

    public void initUI() {

    }


    public void close() {
        this.finish();
    }

}
