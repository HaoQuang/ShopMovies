package funix.prm.prm391x_shopmovies_fx04786.ui.movies.item;

/*Khoi tao doi tuong */
public class MoviesData {

    private String title;
    private String image;
    private String price;

    public MoviesData(String title, String image, String price) {
        this.title = title;
        this.image = image;
        this.price = price;
    }

    public MoviesData() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}