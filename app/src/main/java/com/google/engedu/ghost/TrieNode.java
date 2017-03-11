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

import java.util.HashMap;
import java.util.Iterator;


public class TrieNode {
    private HashMap<String, TrieNode> children;
    private boolean isWord;

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
    }

    public void add(String s) {
        int i;
        TrieNode temp=null;
        for(i=1; i<s.length(); i++){
            TrieNode temp2 = children.get(s.substring(0,i));
            if(temp2 !=null){
                temp= temp2;
            }
            else{
                break;
            }
        }
        if(i==s.length()){
            temp.isWord = true;
            return;
        }

        TrieNode prev=temp;
        TrieNode next = null;
        for(int j=i; j<s.length(); j++){
            next = new TrieNode();
            if (prev != null) {
                prev.children.put(s.substring(0, j), next);
                prev = next;
            }
        }
        prev.isWord= true;
    }

    public boolean isWord(String s) {
        int i=0;
        TrieNode temp=null;
        for(i=1; i<s.length(); i++){
            TrieNode temp2 = children.get(s.substring(0,i));
            if(temp2 !=null){
                temp= temp2;
            }
            else{
                break;
            }
        }
        if(temp!=null) {
            return temp.isWord;
        }
        else{
            return false;
        }
    }

    public String getAnyWordStartingWith(String s) {
        TrieNode temp=null;
        int i=0;
        for(i=1; i<s.length(); i++){
            TrieNode temp2 = children.get(s.substring(0,i));
            if(temp2 !=null){
                temp= temp2;
            }
            else{
                break;
            }
        }
        String y =null;
        if(temp!=null) {
            for (String x : temp.children.keySet()) {
                y = x;
                break;
            }
        }
        return y;
    }

    public String getGoodWordStartingWith(String s) {
        return null;
    }
}
