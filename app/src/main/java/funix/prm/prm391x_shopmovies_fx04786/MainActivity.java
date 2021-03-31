package funix.prm.prm391x_shopmovies_fx04786;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.util.Arrays;
import java.util.List;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 0; //Check login
    private FirebaseAuth auth;//Dung API fireBase
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //Tao giao dien dang nhap

        auth = FirebaseAuth.getInstance();
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build());

        //Kiem tra xem nguoi dung da dang nhap hay chua.
        if (auth.getCurrentUser() != null) {
            //mo ung dung
        } else {
            // Khoi tao qua trinh dang nhap (giao dien)
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .setLogo(R.drawable.logo)      // Tao logo
                            .setTheme(R.style.Theme_MaterialComponents_Light_Bridge)      // tao giao dien
                            .build(),
                    RC_SIGN_IN);
        }//end

        //botttom navbar
        BottomNavigationView navView = findViewById(R.id.nav_view);
        //Tao navbar
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_movies, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


    }
    // nhan ket qua dang nhap
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
               //Neu dang nhap thanh cong
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Log.d("AUTH", auth.getCurrentUser().getEmail());
                Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
                // ...
            } else {
                //Xu ly neu dang nhap that bai
                Toast.makeText(this, "Login fail", Toast.LENGTH_SHORT).show();
            }
        }
    }
}