package Main;

import DocIndexingManagement.Indexing.Dictionary;
import FileManagement.FileReader;
import Stemmer.StateHandler;

import java.util.HashMap;
import java.util.Vector;

public class Main {
    public static StateHandler stateHandler;
    public static FileReader mainFileReader;
    public static FileReader stopWordsReader;


    public static HashMap<String,Character> hashmap;
    public static Vector<String> words;
    public static Dictionary dictionary;

    public static long stemm_time  ;
    public static long tree_adding ;
    public static long delete_time ;
    public static long sum_time ;
    public static Boolean last_tokens_ended_with_ARTICLE = false;
    public static Boolean article_found = false;
    public static int article_number ;
    public static int last_article_number;
    public static int keyS = 10, valS = 4, halfS = 2, range = 100000;
    public static int word_counter = 0;

    public static void init(){
        stateHandler = new StateHandler();
        mainFileReader = new FileReader("/Users/mohammad/workspace/ISR/PersianStemmer/Phase.txt");
        stopWordsReader = new FileReader("/Users/mohammad/workspace/ISR/PersianStemmer/StopWords.txt");
        words = new Vector<String>();
        dictionary = Dictionary.getIntance();
        stemm_time = tree_adding = delete_time = sum_time = 0;
        article_number = 0;
    }

    public static void createStopWordsSet(){
        hashmap = new HashMap<String, Character>();
        Vector<String> stopWords = new Vector<String>();
        while ((stopWords =stopWordsReader.readWithBufferSize(1000))!= null){
            for(String s : stopWords){
                hashmap.put(s,'@');
            }
        }
    }

    public static void createDictionary(){
        for (String s : words){
            //TODO insert docID instead of 0
            dictionary.insert(s, 0);
        }
    }


    public static void doMainProcess(){
        String article_name = "";
        while ((words =mainFileReader.readWithBufferSize(100000))!= null){
//            System.out.println(words.size());
            if(last_tokens_ended_with_ARTICLE==true){
                //System.out.println(words.elementAt(0));
                if(words.elementAt(words.size()-1).equals("<مقاله>")){
//                    System.out.println("maghale!");
//                    article_name = words.elementAt(0);
                    last_tokens_ended_with_ARTICLE = true;
                    // process in all of article
                }
                else{
                    // continue the way
                    last_tokens_ended_with_ARTICLE = false;
//                    article_name = words.elementAt(0);
                    // process in article is not completed
                }
                words.removeElementAt(words.size()-1);
                article_number++;
            }
            else
            if(last_tokens_ended_with_ARTICLE==false){
                if(words.elementAt(words.size()-1).equals("<مقاله>")){
                    if(article_found == false){
                        last_tokens_ended_with_ARTICLE = true;
                        article_found = true;
                        continue;
                    }
                    last_tokens_ended_with_ARTICLE = true;
                    // process in article is completed
                }
                else{
                    last_tokens_ended_with_ARTICLE = false;
                    // process in article is not completed
                }
            }

//            System.out.println("article ID = " + article_number);
            long a1 = System.currentTimeMillis();
            stemming();
            long a2 = System.currentTimeMillis();
            delete_stop_words();
            long a3 = System.currentTimeMillis();
            createDictionary();
            long a4 = System.currentTimeMillis();
            stemm_time+= (a2-a1);
            delete_time+= (a3-a2);
            tree_adding+= (a4-a3);
            last_article_number = article_number;
        }
    }

    public static void stemming(){
        boolean change = true;
        for (int i = 0; i < words.size()-1; i++) {
            String stemmed_word = "";
            String word = words.elementAt(i);
            if(word.endsWith("،")){
                word = word.substring(0,word.length()-1);
                words.set(i,word);
            }
            if (i == 0 || i == words.size() - 1) {
                stemmed_word = stateHandler.detectType("", word, "" , change);
            } else {
                String next_word = words.elementAt(i+1);
                String last_word = words.elementAt(i-1);
                stemmed_word = stateHandler.detectType(last_word, word, next_word , change);
            }
            if (stemmed_word.equals(word)){
                change = false;
                continue;
            }
            words.set(i,stemmed_word);
            change = true;
        }
    }

    public static void delete_stop_words(){
        for(int i  = words.size()-1 ; i >=0 ; i--){
            String str = words.elementAt(i);
            if(str.endsWith("،")){
//                System.out.println(words.elementAt(i));
                str = str.substring(0,str.length()-1);
//                System.out.println(str);
            }
            if(hashmap.containsKey(str) == true){
//                System.out.println("##  "  + str);
                words.remove(i);
            }
        }
    }

    public static void main(String[] args) {
        long a1 = System.currentTimeMillis();
        init();
//        long a2 = System.currentTimeMillis();
//        System.out.println(a2-a1);
        createStopWordsSet();
//        long a3 = System.currentTimeMillis();
//        System.out.println(a3-a2);
        doMainProcess();
        long a2 = System.currentTimeMillis();
        sum_time = a2-a1;
//        long a4 = System.currentTimeMillis();
//        System.out.println(a4-a3);
        System.out.println("stemm time =  " + stemm_time + "   delete time =   " + delete_time + "   adding time =  " + tree_adding + " read file time = " + (sum_time-(delete_time+tree_adding+stemm_time)) + "  sum time =  " + sum_time);
        System.out.println("number_of_articles = " + article_number);
        System.out.println(word_counter);
        System.out.println("khar!");
    }
}