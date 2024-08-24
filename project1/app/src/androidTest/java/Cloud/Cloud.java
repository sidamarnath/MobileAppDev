package Cloud;

import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;


@SuppressWarnings("deprecation")
public class Cloud {
    private static final String MAGIC = "NechAtHa6RuzeR8x";
    private static final String USER = "imtiazay";
    private static final String PASSWORD = "justforfun";

    private static final String BASE_URL = "https://facweb.cse.msu.edu/cse476/cse476x/";
    public static final String CATALOG_PATH = "hatter-cat.php";
    public static final String SAVE_PATH = "hatter-save.php";
    public static final String DELETE_PATH = "hatter-delete.php";
    public static final String LOAD_PATH = "hatter-load.php";
    private static final String UTF8 = "UTF-8";

}