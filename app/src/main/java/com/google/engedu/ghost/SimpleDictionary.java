/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.ghost;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {
        int low = 0;
        int high = words.size()-1;
        int mid,index=0;
        String sampleWord = null;
        while(low < high){
            mid = (low+high)/2;
            String p = words.get(mid);
            int x = p.compareTo(prefix);

            if(p.startsWith(prefix)){
                sampleWord = p;
                index = mid;
            }

            if(x < 0) {
                //later half
                low = mid + 1;
            }
            else{
                //previous half
                high = mid;
            }
        }
        //String y = getGoodWordStartingWith(prefix);
        if(sampleWord!=null){
            Log.i("Simple Dictionary",sampleWord + " -- Index : " + index);
        }
        else{
            Log.i("Simple Dictionary "," NULL");
        }
        return sampleWord;
    }

    public int getAnyWordStartingWithIndex(String prefix) {
        int low = 0;
        int high = words.size()-1;
        int mid=-1,index=-1;
        String sampleWord = null;
        while(low < high){
            mid = (low+high)/2;
            String p = words.get(mid);
            int x = p.compareTo(prefix);

            if(p.startsWith(prefix)){
                sampleWord = p;
                index=mid;
            }

            if(x < 0) {
                //later half
                low = mid + 1;
            }
            else{
                //previous half
                high = mid;
            }
        }
        return index;
    }

    @Override
    public String getGoodWordStartingWith(String prefix) {

        String selected = null;
        int k = getAnyWordStartingWithIndex(prefix);
        if(k==-1){
            return null;
        }
        int low=k,high=k;
        while(low>0 && words.get(low-1).startsWith(prefix)){
            low--;
        }
        while(high < words.size()-1 && words.get(high+1).startsWith(prefix) ){
            high++;
        }
        ArrayList<String> even = new ArrayList<>();
        ArrayList<String> odd = new ArrayList<>();
        for(int i=low; i<=high; i++){
            String p = words.get(i);
            if(p.length()%2==0){
                //first turn was computer
                even.add(p);
            }
            else if(p.length()%2!=0){
                odd.add(p);
            }
        }

        Random R = new Random();
        int s;
        if(prefix.length()%2==0){
            if(even.size()!=0){
                s = R.nextInt(even.size());
                selected = even.get(s);
            }
            else{
                s = R.nextInt(odd.size());
                selected = odd.get(s);
            }
        }
        else{
            if(odd.size()!=0){
                s = R.nextInt(odd.size());
                selected = odd.get(s);
            }
            else{
                s = R.nextInt(even.size());
                selected = even.get(s);
            }
        }
        //Log.i("Simple Dictionary ",k+" - Word index. Low " + low + " : " + words.get(low) +  "  High "+high + " : "+words.get(high) );
        Log.i("Simple Dictionary ","New Word - "+selected +" Random int - "+s);
        return selected;
    }
}
