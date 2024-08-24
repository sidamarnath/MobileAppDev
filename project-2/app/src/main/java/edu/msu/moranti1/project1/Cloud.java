package edu.msu.moranti1.project1;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;

import java.io.IOException;
import java.util.ArrayList;

import edu.msu.moranti1.project1.Model.Catalog;
import edu.msu.moranti1.project1.Model.Item;
import edu.msu.moranti1.project1.Model.LoginResult;
import edu.msu.moranti1.project1.Model.SaveResult;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class Cloud {
    private static final String USER = "";
    private static final String PASSWORD = "";
    private static final String MAGIC = "NechAtHa6RuzeR8x";
//    private static final String BASE_URL = "https://webdev.cse.msu.edu/~mathura5/cse476/project2web/";
    private static final String BASE_URL = "https://webdev.cse.msu.edu/~moranti1/cse476/project2Web/";
    public static final String LOGIN_PATH = "game-login.php";
    public static final String CREATE_USER_PATH = "game-create-user.php";
    public static final String CAT_PATH = "game-cat.php";
    public static final String FCM_REGISTER_PATH = "game-fcm-register.php";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build();
    public String getuserlogin(String user, String password, View v) throws IOException, RuntimeException {
        String msg="";
        Connect4Service service = retrofit.create(Connect4Service.class);

        Response<LoginResult> response = null;
        try {
            response = service.getlogin(user, password).execute();
        } catch (Exception e) {
            Log.e("cloud", "getuserlogin: "+ e);
        }
        LoginResult catalog= response.body();
        String status = catalog.getStatus();
        if (status.equals("no")) {
             msg = catalog.getMsg();
        } else if (status.equals("yes")){
            msg = "Login Successful!!";
            String name = catalog.getUser();
            int id = catalog.getId();
            Intent intent = new Intent(v.getContext(), JoinGameActivity.class);
            v.getContext().startActivity(intent);
        }
        return msg;
    }

    public String getcreateuser(String user, String password, View v) throws IOException, RuntimeException {
        String msg="";
        Connect4Service service = retrofit.create(Connect4Service.class);

        Response<LoginResult> response = null;
        try {
            response = service.getcreateuser(user, password).execute();
        } catch (Exception e) {
            Log.e("cloud", "getuserlogin: "+ e);
        }
        LoginResult catalog= response.body();
        String status = catalog.getStatus();
        if (status.equals("no")) {
            msg = "Unable to Create new User!!";
        } else if (status.equals("yes")){
            msg = "User Created Successfully!!";
            String name = catalog.getUser();
            int id = catalog.getId();
            Log.e("id", String.valueOf(id));
            Intent intent = new Intent(v.getContext(), JoinGameActivity.class);
            v.getContext().startActivity(intent);
        }
        return msg;
    }


    public static class CatalogAdapter extends BaseAdapter {
        /**
         * The items we display in the list box. Initially this is
         * null until we get items from the server.
         */
        private Catalog catalog = new Catalog("", new ArrayList<Item>());


        @Override
        public int getCount() {
            return catalog.getItems().size();
//            return 5;
        }

        @Override
        public Item getItem(int position) {
            return catalog.getItems().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
        //todo
        public String getId(int position) {
            return catalog.getItems().get(position).getId();
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if(view == null) {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
            }

            AppCompatButton btn = view.findViewById(R.id.btn);
            btn.setText(catalog.getItems().get(position).getName());

            return view;
        }


//    public CatalogAdapter() {
//        Item a = new Item();
//        a.setName("first item");
//        a.setId("id");
//        catalog.getItems().add(a);
//
//        Item b = new Item();
//        b.setName("second item");
//        b.setId("id");
//        catalog.getItems().add(b);
//
//    }
        /**
         * Constructor
         */
        public CatalogAdapter(View view, String user, String pass) {
            // Create a thread to load the catalog
            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        catalog = getCatalog(user,pass);

//                        if (catalog.getStatus().equals("no")) {
//                            String msg = "Loading catalog returned status 'no'! Message is = '" + catalog.getStatus() + "'";
//                            throw new Exception(msg);
//                        }
//                        if (catalog.getItems().isEmpty()) {
//                            String msg = "Catalog does not contain any hattings.";
//                            throw new Exception(msg);
//                        }

                        view.post(new Runnable() {

                            @Override
                            public void run() {
                                // Tell the adapter the data set has been changed
                                notifyDataSetChanged();
                            }

                        });
                    } catch (Exception e) {
                        // Error condition! Something went wrong
                        Log.e("CatalogAdapter", "Something went wrong when loading the catalog", e);
                        view.post(new Runnable() {
                            @Override
                            public void run() {
                                String string;
                                // make sure that there is a message in the catalog
                                // if there isn't use the message from the exception
                                if (catalog.getStatus() == null) {
                                    string = e.getMessage();
                                } else {
                                    string = catalog.getStatus();
                                }
                                Toast.makeText(view.getContext(), string, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }).start();
        }
//                public Catalog getCatalog() {
//            Catalog newCatalog = new Catalog("", new ArrayList(), "");
//
//            Item a = new Item();
//            a.setName("first item");
//            a.setId("id");
//            newCatalog.getItems().add(a);
//
//            Item b = new Item();
//            b.setName("second item");
//            b.setId("id");
//            newCatalog.getItems().add(b);
//
//            return newCatalog;
//        }
        // Create a GET query
        public Catalog getCatalog(String user, String pass) throws IOException, RuntimeException {
            Connect4Service service = retrofit.create(Connect4Service.class);
            Response<Catalog> response = null;
            try{
                response = service.getcatalog(user,MAGIC, pass).execute();
            } catch (Exception e) {
                Log.e("catalog", "Something went wrong "+e.getMessage());
            }

            // check if request failed
            if (!response.isSuccessful()) {
                Log.e("getCatalog", "Failed to get catalog, response code is = " + response.code());
                return new Catalog("no", new ArrayList<Item>());
            }
            Catalog catalog = response.body();
            if (catalog.getStatus().equals("no")) {
                String string = "Failed to get catalog, msg is = " + catalog.getStatus();
                Log.e("getCatalog", string);
                return new Catalog("no", new ArrayList<Item>());
            };
            if (catalog.getItems() == null) {
                catalog.setItems(new ArrayList<Item>());
            }
            return catalog;
        }

    }

    public boolean registerFCMId(String user, String fcmid) throws IOException {
        user = user.trim();
        if(user.length() == 0) {
            return false;
        }
        Connect4Service service = retrofit.create(Connect4Service.class);
        try {
            Response<SaveResult> response = service.registerFCM(user, fcmid).execute();
            if(response.isSuccessful()) {
                SaveResult result = response.body();
                if(result.getStatus()!= null && result.getStatus().equals("yes")) {
                    Log.e("SaveToCloud", "Able to save, message = '" + result.getMsg() + "'");
                    return true;
                }
                Log.e("SaveFCM", "Failed to save, message = '" + result.getMsg() + "'");
                return false;
            }
            Log.e("SaveFCM", "Failed to save, message = '" + response.code() + "'");
            return false;
        } catch (IOException e) {
            Log.e("SaveFCM", "Exception occurred while trying to save fcmid!", e);
            return false;
        } catch (RuntimeException e) {
            Log.e("SaveFCM", "Runtime Exception: " + e.getMessage());
            return false;
        }
    }
}
