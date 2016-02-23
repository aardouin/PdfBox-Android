package org.apache.pdfboxandroid.sample;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.apache.pdfbox.pdfwriter.COSWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDSimpleFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImage;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.PDFBoxResourceLoader;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PDFBoxResourceLoader.init(this);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tryPdf();
            }
        });
    }

    private void tryPdf() {

        PDDocument document = new PDDocument();

        PDPage page = new PDPage(PDRectangle.A4_LANDSCAPE);
        PDRectangle pageSize = page.getMediaBox();
        PDPageContentStream content = null;
        try {
            content = new PDPageContentStream(document, page);


            Bitmap bitamp = ((BitmapDrawable)getDrawable(R.drawable.graph_test)).getBitmap();
            PDImageXObject image = JPEGFactory.createFromImage(document, bitamp);
            content.drawImage(image,0,0,pageSize.getWidth(),pageSize.getHeight()/2);

            content.beginText();
            content.newLineAtOffset(20,pageSize.getHeight()*3/4);
            content.setFont(PDType1Font.HELVETICA_BOLD,30f);
            content.setStrokingColor(0,0,0);
            content.showText("Test Title");
            content.endText();

        } catch (IOException e) {
            Log.e("PDF", "Pdf creation error",e);
            e.printStackTrace();
        }finally{
            if( content != null) {
                try {
                    content.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        document.addPage(page);


        page = new PDPage(PDRectangle.A4_LANDSCAPE);
        pageSize = page.getMediaBox();
        content = null;
        try {
            content = new PDPageContentStream(document, page);


            content.moveTo(20,pageSize.getHeight()*3/4);

            content.addRect(20,pageSize.getHeight()*3/4,300,100);
            content.setNonStrokingColor(150,0,150);
            content.fill();
            content.moveTo(0,0);
            content.beginText();
            content.newLineAtOffset(20,pageSize.getHeight()*3/4);
            content.setFont(PDType1Font.HELVETICA_BOLD,30f);
            content.setStrokingColor(0,0,0);
            content.setNonStrokingColor(0,0,0);
            content.showText("Test Title");
            content.endText();

        } catch (IOException e) {
            Log.e("PDF", "Pdf creation error",e);
            e.printStackTrace();
        }finally{
            if( content != null) {
                try {
                    content.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        document.addPage(page);

        try {
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS),"test.pdf");
            document.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
