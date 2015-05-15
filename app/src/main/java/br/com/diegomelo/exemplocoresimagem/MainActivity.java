package br.com.diegomelo.exemplocoresimagem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.graphics.Palette;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity {
    private static final int REQUEST_CODE_OBTER_IMAGEM = 1234;
    @ViewById RelativeLayout rlPrincipal;
    @ViewById TextView tvVibrant;
    @ViewById TextView tvVibrantLight;
    @ViewById TextView tvVibrantDark;
    @ViewById TextView tvMuted;
    @ViewById TextView tvMutedLight;
    @ViewById TextView tvMutedDark;
    @ViewById ImageView ivImage;
    @ViewById Button btVai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Click(R.id.btVai)
    public void clickBtVai() {
        Bitmap imgBitmap = getBitmapFromImageView();
        setColorsFromImage(imgBitmap);
    }

    private Bitmap getBitmapFromImageView() {
        Bitmap imgBitmap;
        if (ivImage.getDrawable() instanceof BitmapDrawable) {
            imgBitmap = ((BitmapDrawable) ivImage.getDrawable()).getBitmap();
        } else {
            Drawable d = ivImage.getDrawable();
            imgBitmap = Bitmap.createBitmap(d.getIntrinsicWidth(), d.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }
        return imgBitmap;
    }

    @Click(R.id.ivImage)
    public void clickIvImage() {
        selectImageFromGallery();
    }

    private void selectImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), REQUEST_CODE_OBTER_IMAGEM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {

            if(requestCode == REQUEST_CODE_OBTER_IMAGEM) {
                Uri uri = data.getData();
                Picasso.with(getApplicationContext()).load(uri.toString()).into(ivImage);
            }
        }
    }

    private void setColorsFromImage(Bitmap img) {
        Palette palette = Palette.generate(img);

        // Getting the different types of colors from the Image
        Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
        float[] vibrant = vibrantSwatch !=null ? vibrantSwatch.getHsl() : new float[]{0.0f, 0.0f, 0.0f};

        Palette.Swatch vibrantLightSwatch = palette.getLightVibrantSwatch();
        float[] vibrantLight = vibrantLightSwatch !=null ? vibrantLightSwatch.getHsl() : new float[]{0.0f, 0.0f, 0.0f};

        Palette.Swatch vibrantDarkSwatch = palette.getDarkVibrantSwatch();
        float[] vibrantDark = vibrantDarkSwatch != null ? vibrantDarkSwatch.getHsl() : new float[]{0.0f, 0.0f, 0.0f};

        Palette.Swatch mutedSwatch = palette.getMutedSwatch();
        float[] muted = mutedSwatch !=null ? mutedSwatch.getHsl() : new float[]{0.0f, 0.0f, 0.0f};

        Palette.Swatch mutedLightSwatch = palette.getLightMutedSwatch();
        float[] mutedLight = mutedLightSwatch !=null ? mutedLightSwatch.getHsl() : new float[]{0.0f, 0.0f, 0.0f};

        Palette.Swatch mutedDarkSwatch = palette.getDarkMutedSwatch();
        float[] mutedDark = mutedDarkSwatch !=null ? mutedDarkSwatch.getHsl() : new float[]{0.0f, 0.0f, 0.0f};

        tvVibrant.setBackgroundColor(Color.HSVToColor(vibrant));
        tvVibrantLight.setBackgroundColor(Color.HSVToColor(vibrantLight));
        tvVibrantDark.setBackgroundColor(Color.HSVToColor(vibrantDark));
        tvMuted.setBackgroundColor(Color.HSVToColor(muted));
        tvMutedDark.setBackgroundColor(Color.HSVToColor(mutedDark));
        tvMutedLight.setBackgroundColor(Color.HSVToColor(mutedLight));

        rlPrincipal.setBackgroundColor(Color.HSVToColor(vibrant));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
