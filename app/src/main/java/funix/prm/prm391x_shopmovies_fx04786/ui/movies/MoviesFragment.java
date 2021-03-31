package funix.prm.prm391x_shopmovies_fx04786.ui.movies;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import funix.prm.prm391x_shopmovies_fx04786.R;
import funix.prm.prm391x_shopmovies_fx04786.ui.movies.item.MoviesAdapter;
import funix.prm.prm391x_shopmovies_fx04786.ui.movies.item.MoviesData;

public class MoviesFragment extends Fragment {
    JSONArray jsonArray;    //mang JSON
    ArrayList<MoviesData> arrayList;    //arraylist movies
    private ProgressBar progressBar;    //Thanh tien do
    private MoviesAdapter moviesAdapter;    //adapter
    ListView listView;
    ShareDialog shareDialog;
    ShareLinkContent shareLinkContent;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.movies_fragment, container, false);
        progressBar = root.findViewById(R.id.idPBLoading);
        listView = root.findViewById(R.id.lv_movie);

        //Tao doi tuong de lay du  lieu tu web
        readJson  readJson = new readJson();
        readJson.execute("https://api.androidhive.info/json/movies_2017.json");

        //hop thoai chia se tren facebook
        shareDialog = new ShareDialog(getActivity());
        //kiem tra nguoi dung da click vao item nao tren listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    //Lay thong tin URL hinh anh phim
                    JSONObject object = jsonArray.getJSONObject(position);
                    String urlShare = object.getString("image");
                    Log.d("ImageLink", urlShare);

                    //goi den API chia se cua facebook
                    if (shareDialog.canShow(ShareLinkContent.class)) {
                        shareLinkContent = new ShareLinkContent.Builder()
                                .setContentUrl(Uri.parse(urlShare)).build();
                        Toast.makeText(getActivity(), (object.getString("title")), Toast.LENGTH_SHORT).show();
                    }
                    shareDialog.show(shareLinkContent);//hien thi

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return root;
    }

    //xu ly duoi nen chua noi dung trang web tra ve String
    class readJson extends AsyncTask<String, Integer, String> {
        //Thuc hien tac vu chay duoi nen, load du lieu JSON ve dien thoai
        @Override
        protected String doInBackground(String... strings) {
            return getAllData(strings[0]);
        }

        //doc du lieu
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Data", s);
            try {
                arrayList = new ArrayList<>();//tao danh sach de luu du lieu
                jsonArray = new JSONArray(s);//mang chua du lieu dc tai ve
                for (int i=0;i<jsonArray.length();i++) {
                    JSONObject object = jsonArray.getJSONObject(i);//goi doi tuong JSON de lay danh sach mang
                    MoviesData moviesData = new MoviesData();//Tao doi  tuong movies va set du lieu vao lan luot
                    moviesData.setTitle(object.getString("title"));
                    moviesData.setImage(object.getString("image"));
                    moviesData.setPrice(object.getString("price"));
                    arrayList.add(moviesData);//them du lieu moi vao mang
                    moviesAdapter = new MoviesAdapter(getActivity(),R.layout.movie_item,arrayList);
                    listView.setAdapter(moviesAdapter);//do du  lieu vao listview

                    //Log.d("Movies",String.valueOf(arrayList.size()));
                }
                progressBar.setVisibility(View.GONE);//an thanh progress

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //Doc du lieu tu URL
    private static String getAllData(String Url) {
        StringBuilder content = new StringBuilder();

        try {
            URL url = new URL(Url);
            URLConnection urlConnection = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line +"\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

}