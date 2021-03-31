package funix.prm.prm391x_shopmovies_fx04786.ui.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import funix.prm.prm391x_shopmovies_fx04786.MainActivity;
import funix.prm.prm391x_shopmovies_fx04786.R;

public class ProfileFragment extends Fragment {

    Button buttonLogout;
    ImageView imageView;
    TextView textViewName;
    ArrayList arrayList;
    ListView listView;
    ArrayAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.profile_fragment, container, false);
        //lay thong tin nguoi dung
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        imageView = root.findViewById(R.id.image_user);
        textViewName = root.findViewById(R.id.tv_name_user);
        buttonLogout = root.findViewById(R.id.btn_logout);
        listView = root.findViewById(R.id.lv_info_user);
        arrayList = new ArrayList();
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,arrayList);

        //Dang xuat tai khoan
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance()
                        .signOut(getActivity())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                // ...
                                startActivity(new Intent(getActivity(), MainActivity.class));
                            }
                        });
            }
        });

        //Kien tra nguoi dung da dang nhap hay chua
        if (user != null) {
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
            if (isLoggedIn) {
                //Facebook lay hinh anh dai dien tu facebook
                GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            String pic = object.getJSONObject("picture").getJSONObject("data").getString("url");
                            Picasso.get().load(pic).into(imageView);

                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Bundle bundle = new Bundle();
                bundle.putString("fields", "gender, name, id, first_name, last_name,picture");
                graphRequest.setParameters(bundle);
                graphRequest.executeAsync();
            }

            // Nhan thong tin name, email, image
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            String phoneNumber = user.getPhoneNumber();

            // Kiem tra email (khong dung)
            boolean emailVerified = user.isEmailVerified();

            //Lay hinh anh ten
            String uid = user.getUid();
            //Log.d("URL", photoUrl.toString());
            textViewName.setText(name);
            Picasso.get().load(photoUrl).into(imageView);
            arrayList.add("Email: "+email);
            arrayList.add("Adress: ");
            arrayList.add("Phone: "+phoneNumber);
        } else {
            textViewName.setText("");
            buttonLogout.setText("Đăng nhập");
        }
        listView.setAdapter(adapter);
        return root;
    }

    //xoa tai khoan (khong dung)
    public void delete() {
        // [START auth_fui_delete]
        AuthUI.getInstance()
                .delete(getActivity())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }
}
