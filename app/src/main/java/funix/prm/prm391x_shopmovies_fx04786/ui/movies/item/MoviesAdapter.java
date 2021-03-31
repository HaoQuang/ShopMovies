package funix.prm.prm391x_shopmovies_fx04786.ui.movies.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import funix.prm.prm391x_shopmovies_fx04786.R;

/*Custom adapter cho listview*/
public class MoviesAdapter extends ArrayAdapter<MoviesData> {

    Context context;
    ArrayList<MoviesData> arrayList;
    int layoutResource;

    public MoviesAdapter(@NonNull Context context, int resource, @NonNull ArrayList<MoviesData> objects) {
        super(context, resource, objects);
        this.context = context;
        this.arrayList = objects;
        this.layoutResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(layoutResource,null);
        TextView textViewName = convertView.findViewById(R.id.tv_name_movie);
        TextView textViewPrice = convertView.findViewById(R.id.tv_price);
        ImageView imageView = convertView.findViewById(R.id.img_movie);
        textViewName.setText(arrayList.get(position).getTitle());               //Ten phim
        textViewPrice.setText(arrayList.get(position).getPrice());              //Tieu de
        Picasso.get().load(arrayList.get(position).getImage()).into(imageView); //Hinh anh
        return convertView;
    }
}

