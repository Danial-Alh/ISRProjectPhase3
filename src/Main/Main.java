package Main;

import DocIndexingManagement.Indexing.Dictionary;
import FileManagement.FileReader;
import Primitives.*;
import Stemmer.StateHandler;
import Ui.GUI;

import java.util.HashMap;
import java.util.Vector;

public class Main {
    public static StateHandler stateHandler;
    public static FileReader mainFileReader;
    public static FileReader stopWordsReader;
    public static GUI gui;


    public static HashMap<String,Character> hashmap;
    public static Vector<String> words;
    public static Dictionary dictionary;
    public static Dictionary userDictionary;

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
        gui = new GUI();

        gui.setVisible(true);
        words = new Vector<String>();
        dictionary = Dictionary.getIntance(1);
        //userDictionary = Dictionary.getIntance();
        stemm_time = tree_adding = delete_time = sum_time = 0;
        article_number = 1;
    }

    public static void process_user_query(String query){
        userDictionary = Dictionary.getIntance(2);
        String[] tokens = query.split(" +");
        Vector<String> words = new Vector<String>();
        for(int i = 0 ; i < tokens.length ; i++){
            words.add(tokens[i]);
        }
        stemming(words);
        delete_stop_words(words);
        // create dictionary for user command
        for(String s : words){
            userDictionary.insert(s,1);
        }
        TermPosting termPosting;
        while ((termPosting = userDictionary.getNextTermPosting()) != null){
            Vector<TermDocDetail> termDocDetails = termPosting.getData();
            TermVector termVector = new TermVector(termPosting.getTerm());
            // calculate weight of query words
            for(int i = 0 ; i < termDocDetails.size() ; i++){
                TermDocDetail termDocDetail = termDocDetails.elementAt(i);
                double Nij = (double)termDocDetail.getOccurences();
                double NmaxJ = 10;
                double N = (double)1;
                double Ni = (double)termDocDetails.size();
                double weight = (Nij/NmaxJ)/(Math.log(N/Ni)/Math.log(2));
                TermFreqVector termFreqVector = termVector.getFreqWeight();
                FreqWeight freqWeight = new FreqWeight();
                freqWeight.setFreq((long) weight);
                termFreqVector.addNewFreqWeight(freqWeight,i);
            }
        }
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

    public static void createDictionary(Vector<String> words , int article_number){
        for (String s : words){
            //TODO insert docID instead of 0
            dictionary.insert(s, article_number);
        }
    }

    public static void calculate_words_weight(){
        TermPosting termPosting;
        while ((termPosting =  dictionary.getNextTermPosting()) != null){
            Vector<TermDocDetail> termDocDetails = termPosting.getData();
            TermVector termVector = new TermVector(termPosting.getTerm());
            for(int i = 0 ; i < termDocDetails.size() ; i++){
            TermDocDetail termDocDetail = termDocDetails.elementAt(i);
                double Nij = (double)termDocDetail.getOccurences();
                double NmaxJ = 10;
                double N = (double)article_number;
                double Ni = (double)termDocDetails.size();
                double weight = (Nij/NmaxJ)/(Math.log(N/Ni)/Math.log(2));
                TermFreqVector termFreqVector = termVector.getFreqWeight();
                FreqWeight freqWeight = new FreqWeight();
                freqWeight.setFreq((long) weight);
                termFreqVector.addNewFreqWeight(freqWeight,i);
            }
        }
    }

    public static void doMainProcess(){
        String article_name = "";
        while ((words =mainFileReader.readWithBufferSize(1000000))!= null){
////            System.out.println(words.size());
//            if(last_tokens_ended_with_ARTICLE==true){
//                //System.out.println(words.elementAt(0));
//                if(words.elementAt(words.size()-1).equals("<مقاله>")){
////                    System.out.println("maghale!");
////                    article_name = words.elementAt(0);
//                    last_tokens_ended_with_ARTICLE = true;
//                    // process in all of article
//                }
//                else{
//                    // continue the way
//                    last_tokens_ended_with_ARTICLE = false;
////                    article_name = words.elementAt(0);
//                    // process in article is not completed
//                }
//                words.removeElementAt(words.size()-1);
//                article_number++;
//            }
//            else
//                if(words.elementAt(words.size()-1).equals("<مقاله>")){
//                    if(article_found == false){
//                        last_tokens_ended_with_ARTICLE = true;
//                        article_found = true;
//                        continue;
//                    }
//                    last_tokens_ended_with_ARTICLE = true;
//                    // process in article is completed
//                }
//                else{
//                    last_tokens_ended_with_ARTICLE = false;
//                    // process in article is not completed
//                }

            System.out.println("article ID = " + article_number);
            long a1 = System.currentTimeMillis();
            stemming(words);
            long a2 = System.currentTimeMillis();
            delete_stop_words(words);
            long a3 = System.currentTimeMillis();
            createDictionary(words,article_number);
            long a4 = System.currentTimeMillis();
            stemm_time+= (a2-a1);
            delete_time+= (a3-a2);
            tree_adding+= (a4-a3);
            article_number++;
        }
    }

    public static void stemming(Vector<String> words){
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

    public static void delete_stop_words(Vector<String> words){
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