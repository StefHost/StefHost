package nl.stefhost.stefhost;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class WallOfFame extends Fragment {

    static ProgressDialog progressDialog;
    public String resultaat = "";
    public TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View View = inflater.inflate(R.layout.fragment_walloffame, container, false);

        textView = (TextView) View.findViewById(R.id.textView);
        progressDialog = android.app.ProgressDialog.show(this.getContext(), "Informatie laden", "Even geduld aub..", true, false);
        new info_laden().execute();

        return View;
    }

    private class info_laden extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params)  {

            URL url = null;
            URLConnection urlConnection = null;
            InputStream inputStream = null;

            try {
                url = new URL("http://www.stefhost.nl/creacafe/fotos.txt");
            } catch (MalformedURLException e) {
                System.out.println("MalformedURLException");
            }

            if (url != null){
                try{
                    urlConnection = url.openConnection();
                }catch (java.io.IOException e){
                    System.out.println("java.io.IOException");
                }
            }

            if (urlConnection != null){
                try{
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }catch (java.io.IOException e) {
                    System.out.println("java.io.IOException");
                }
            }

            if (inputStream != null){

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line;
                try{
                    while ((line = bufferedReader.readLine()) != null) {
                        line = line.replace("[e]", "é");
                        resultaat = resultaat+line+"[enter]";
                    }
                }catch (java.io.IOException e) {
                    System.out.println("java.io.IOException");
                }

            }else{
                resultaat = "ERROR";
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            resultaat = resultaat.replace("[enter]", "<br />");
            textView.setText(Html.fromHtml(resultaat));
            progressDialog.dismiss();
        }

    }

}
