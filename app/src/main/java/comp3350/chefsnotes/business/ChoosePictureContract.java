package comp3350.chefsnotes.business;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ChoosePictureContract extends ActivityResultContract {

    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, Object input) {
        return null;
    }

    @Override
    public Object parseResult(int resultCode, @Nullable Intent intent) {
        return null;
    }
}
