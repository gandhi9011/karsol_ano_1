package com.example.admin.karsol_ano_1;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.karsol_ano_1.TabPdf.ResultsFragments;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admin on 27-04-2017.
 */
public class TitleFragment extends Fragment {

    View rootView;
    Button download;
    android.support.v4.app.FragmentManager fragmentManager;
    android.support.v4.app.FragmentTransaction transaction;
    ResultsFragments resultsFragments = new ResultsFragments();

    TitleFragment titleFragment;


    @BindView(R.id.title_name)
    TextView titleName;

    @BindView(R.id.main_content)
    LinearLayout mainContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_title, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;

    }
    public void onViewCreated(View v, Bundle savedInstanceState) {
        download = (Button) v.findViewById(R.id.download);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                new DownloadFile().execute("https://www.dropbox.com/s/awy35keypuwxwtx/testing%20pdf.pdf?dl=1", "testing.pdf");
            }
        });
    }
    public void setTitle(String title) {
        titleName.setText(title);
        if (title.equalsIgnoreCase("Basic1")) {
            Toast.makeText(getContext(), "basic1", Toast.LENGTH_SHORT).show();
            fragmentManager = getFragmentManager();
            transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_content, resultsFragments, "ResultsFragmentAgentlist");
            transaction.commit();

        }

        else  if (title.equalsIgnoreCase("Basic2")) {
            Toast.makeText(getContext(), "basic2", Toast.LENGTH_SHORT).show();
            fragmentManager = getFragmentManager();
            transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_content, resultsFragments, "ResultsFragmentAgentlist");
            transaction.commit();

        }
    }


    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   //
            String fileName = strings[1];  //
            String extStorageDirectory = getActivity().getCacheDir().toString();
            File folder = new File(extStorageDirectory, "testthreepdf");
            folder.mkdir();
            File pdfFile = new File(folder, fileName);

            try{
                pdfFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);
            return null;
        }
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Toast.makeText(getActivity(), "Download PDf successfully", Toast.LENGTH_SHORT).show();

            Log.d("Download complete", "----------");
        }




    }
}