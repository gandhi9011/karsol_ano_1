package com.example.admin.karsol_ano_1.TabPdf;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.karsol_ano_1.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Admin on 19-07-2017.
 */

public class GujratiFrag extends Fragment
{
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    public static EnglishFrag newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        EnglishFrag fragment = new EnglishFrag();
        fragment.setArguments(args);
        return fragment;
    }
    private static final String STATE_CURRENT_PAGE_INDEX = "current_page_index";
    private static final String FILENAME = "testing.pdf";
    private float currentZoomLevel = 12;
    private ParcelFileDescriptor FileDescriptor;
    private android.graphics.pdf.PdfRenderer PdfRenderer;
    private android.graphics.pdf.PdfRenderer.Page CurrentPage;
    private android.widget.ImageView ImageView;
    private Button Previous,Next;
    private ImageView zoomin,zoomout;
    private int PageIndex;

    public View onCreateView(LayoutInflater li, ViewGroup vg, Bundle b)
    {
        return li.inflate(R.layout.frag_pdf, vg, false);
    }

    public void onViewCreated(View v, Bundle savedInstanceState)
    {
        ImageView = (ImageView)v.findViewById(R.id.image);
        Previous = (Button)v.findViewById(R.id.previous);
        Next = (Button)v.findViewById(R.id.next);
        PageIndex = 0;
        if (null != savedInstanceState) {
            PageIndex = savedInstanceState.getInt(STATE_CURRENT_PAGE_INDEX, 0);
        }
        Previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
//                currentZoomLevel = 12;
                showPage(CurrentPage.getIndex() - 1);
            }
        });
        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
//                currentZoomLevel = 12;
                showPage(CurrentPage.getIndex() + 1);
            }
        });

        ImageView.setOnTouchListener(new Touch());

    }
    public void onStart() {
        super.onStart();
        try {
            openRenderer(getActivity());
            showPage(PageIndex);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onStop() {
        try {
            closeRenderer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onStop();
    }




    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (null != CurrentPage) {
            outState.putInt(STATE_CURRENT_PAGE_INDEX, CurrentPage.getIndex());
        }

    }

    private void openRenderer(Context context) throws IOException {
        // In this sample, we read a PDF from the assets directory.
        File file = new File(context.getCacheDir(), "/testthreepdf/"+FILENAME);
        if (!file.exists()) {
            // Since PdfRenderer cannot handle the compressed asset file directly, we copy it into
            // the cache directory.
            InputStream asset = context.getAssets().open(FILENAME);
            FileOutputStream output = new FileOutputStream(file);
            final byte[] buffer = new byte[1024];
            int size;
            while ((size = asset.read(buffer)) != -1) {
                output.write(buffer, 0, size);
            }
            asset.close();
            output.close();
        }
        FileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
        // This is the PdfRenderer we use to render the PDF.
        if (FileDescriptor != null) {
            PdfRenderer = new PdfRenderer(FileDescriptor);
        }
    }

    /**
     * Closes the {@link android.graphics.pdf.PdfRenderer} and related resources.
     *
     * @throws java.io.IOException When the PDF file cannot be closed.
     */
    private void closeRenderer() throws IOException {
        if (null != CurrentPage) {
            CurrentPage.close();
        }
        PdfRenderer.close();
        FileDescriptor.close();
    }

    private void showPage(int index) {
        if (PdfRenderer.getPageCount() <= index) {
            return;
        }
        // Make sure to close the current page before opening another one.
        if (null != CurrentPage) {
            CurrentPage.close();
        }
        // Use `openPage` to open a specific page in PDF.
        CurrentPage = PdfRenderer.openPage(index);
        // Important: the destination bitmap must be ARGB (not RGB).
        int newWidth = (int) (getResources().getDisplayMetrics().widthPixels * CurrentPage.getWidth() / 72 * currentZoomLevel / 40);
        int newHeight = (int) (getResources().getDisplayMetrics().heightPixels * CurrentPage.getHeight() / 72 * currentZoomLevel / 64);
        Bitmap bitmap = Bitmap.createBitmap(CurrentPage.getWidth(),CurrentPage.getHeight(),
                Bitmap.Config.ARGB_8888);
        // Here, we render the page onto the Bitmap.
        // To render a portion of the page, use the second and third parameter. Pass nulls to get
        // the default result.
        // Pass either RENDER_MODE_FOR_DISPLAY or RENDER_MODE_FOR_PRINT for the last parameter.
        CurrentPage.render(bitmap, null, null, android.graphics.pdf.PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        // We are ready to show the Bitmap to user.
        ImageView.setImageBitmap(bitmap);
        updateUi();
    }

    private void updateUi() {
        int index = CurrentPage.getIndex();
        int pageCount = PdfRenderer.getPageCount();
        Previous.setEnabled(0 != index);
        Next.setEnabled(index + 1 < pageCount);
    }



}
