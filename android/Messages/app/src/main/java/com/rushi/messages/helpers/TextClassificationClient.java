package com.rushi.messages.helpers;

import android.content.Context;

import org.tensorflow.lite.support.label.Category;
import org.tensorflow.lite.task.core.BaseOptions;
import org.tensorflow.lite.task.text.nlclassifier.BertNLClassifier;
import org.tensorflow.lite.task.text.nlclassifier.NLClassifier;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class TextClassificationClient {
    private static final String MODAL_PATH = "model.tflite";
    private static final String TAG = "CommentSpam";
    private final Context context;
    private BertNLClassifier nlClassifier;
//    private NLClassifier nlClassifier;
    private ExecutorService executorService;

    public TextClassificationClient(Context context, ExecutorService executorService) {
        this.context = context;
        this.executorService=executorService;
    }

    public void load() {
        try {
            BertNLClassifier.BertNLClassifierOptions options =
                    BertNLClassifier.BertNLClassifierOptions.builder()
                            .setBaseOptions(BaseOptions.builder().setNumThreads(4).build())
                            .build();
            nlClassifier = BertNLClassifier.createFromFileAndOptions(context, MODAL_PATH,options);
            /*NLClassifier.NLClassifierOptions options= NLClassifier.NLClassifierOptions.builder()
                    .setBaseOptions(BaseOptions.builder().setNumThreads(4).build())
                    .build();
            nlClassifier=NLClassifier.createFromFileAndOptions(context,MODAL_PATH,options);*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unload() {
        nlClassifier.close();
        nlClassifier = null;
    }

    public List<Category> classify(String text){
        Future<List<Category>> future=executorService.submit(new Callable<List<Category>>() {
            @Override
            public List<Category> call() throws Exception {
                return nlClassifier.classify(text);
            }
        });
        try {
            return future.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }

    }
}
