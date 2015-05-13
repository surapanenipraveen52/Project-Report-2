package kdmprj.umkc.edu.kdmpr1.speechtotext;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import kdmprj.umkc.edu.kdmpr1.R;
import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

/**
 * Created by praveen on 4/25/2015.
 */
public class GetChunks {
    List<String> chunkslist;
    public List<String> getChunks(String paragraph,Context context) {
        ProgressDialog pd;
        TokenizerModel tm= null;
        try {
            System.out.println("para is "+paragraph);
            Log.v("Anala", "hgcg");
            tm = new TokenizerModel(context.getResources().openRawResource(R.raw.en_token));
        TokenizerME t=new TokenizerME(tm);
        InputStream modelIn = context.getResources().openRawResource(R.raw.en_chunker);
        ChunkerModel chunkerModel = new ChunkerModel(modelIn);
        ChunkerME chunkerME = new ChunkerME(chunkerModel);
        String[] tokens=t.tokenize(paragraph);
        chunkslist =new ArrayList<String>();
        POSModel pm = new POSModel(context.getResources().openRawResource(R.raw.en_pos_maxent));
        POSTaggerME posme = new POSTaggerME(pm);
            Log.v("Aaaa","hc5");
        String[] posTags = posme.tag(tokens);
            Log.v("Aaaa","hc5");
        Span[] chunks = chunkerME.chunkAsSpans(tokens, posTags);
        String[] chunkStrings = Span.spansToStrings(chunks, tokens);
            Log.v("Aaaa","hc5");
            for (int i = 0; i < chunks.length; i++) {
            if (chunks[i].getType().equals("NP")) {
                chunkslist.add( chunkStrings[i]);
            }
        }
        } catch (IOException e) {
            System.out.print(e.getStackTrace());
        }
       Log.v("Printing +chunks",chunkslist.toString());
        return chunkslist;
    }
}
