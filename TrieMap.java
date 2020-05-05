//Note: All of your TrieMapInterface method implementations must function recursively
//I have left the method signatures from my own solution, which may be useful hints in how to approach the problem
//You are free to change/remove/etc. any of the methods, as long as your class still supports the TrieMapInterface

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TrieMap implements TrieMapInterface {

    //creating an instance of TrieMapNode
    TrieMapNode root;

    public TrieMap() {
        //initializing root
        root = new TrieMapNode();
    }

    //Indirectly recursive method to meet definition of interface
    @Override
    public void put(String key, String value) {
        //calling put method
        put(root, key, value);
    }

    //Recursive method
    //Note: arguments are only a suggestion, you may use your own if you devise a different recursive solution
    public void put(TrieMapNode current, String curKey, String value) {

        //initializing an instance of a HashMap that is equal to the current TrieMapNodes children
        HashMap<Character, TrieMapNode> children = current.getChildren();
        //initializing TrieMapNode
        TrieMapNode node = new TrieMapNode();

        //if the HashMap, children, contains the curKey strings first character
        if (children.containsKey(curKey.charAt(0))) {
            //then TrieMapNode, node, is now equal to that string
            node = children.get(curKey.charAt(0));
        }
        //if the HashMap, children, does not contain the curKey strings first character
        else {
            //then the put method is called and places the node into the HashMap
            children.put(curKey.charAt(0), node);
        }
        //if the curKey string is at its last character in the word
        if (curKey.length() == 1) {
            //setting value of node to value (the word)
            node.setValue(value);
            return;
        }
        //put is called recursively and the first character form the curKey is removed for the next iteration
        put(node, curKey.substring(1), value);
    }

    //Indirectly recursive method to meet definition of interface
    @Override
    public String get(String key) {
        return get(root, key);
    }

    //Recursive method
    //Note: arguments are only a suggestion, you may use your own if you devise a different recursive solution
    public String get(TrieMapNode current, String curKey) {

        //initializing an instance of a HashMap that is equal to the current TrieMapNodes children
        HashMap<Character, TrieMapNode> children = current.getChildren();

        //if the length of curKey is 1 (if there is only 1 character in the word)
        if (curKey.length() == 1) {
            //if the HashMap, children, contains the character
            if (children.containsKey(curKey.charAt(0))) {
                //then a TrieMapNode, node, is equal to that string
                TrieMapNode node = children.get(curKey.charAt(0));
                //return the value of node (the word)
                return node.getValue();
            }
        }
        //else if the curKey has a length that is not 1
        else {
            //if the HashMap, children, contains the curKey's first character
            if (children.containsKey(curKey.charAt(0))) {
                //call get method recursively and remove first character from curKey for next iteration
                return get(children.get(curKey.charAt(0)), curKey.substring(1));
            }
        }
        //return null if parameters do not reach if and else statements
        return null;
    }

    //Indirectly recursive method to meet definition of interface
    @Override
    public boolean containsKey(String key) {
        //calls containsKey
        return containsKey(root, key);
    }

    //Recursive method
    //Note: arguments are only a suggestion, you may use your own if you devise a different recursive solution
    public boolean containsKey(TrieMapNode current, String curKey) {

        //initializing an instance of a HashMap that is equal to the current TrieMapNodes children
        HashMap<Character, TrieMapNode> children = current.getChildren();

        //if the length of curKey is 1 (if there is only 1 character in the word)
        if (curKey.length() == 1) {
            //if the HashMap, children, contains the character
            if (children.containsKey(curKey.charAt(0))) {
                //then a TrieMapNode, node, is equal to that string
                TrieMapNode node = children.get(curKey.charAt(0));
                //return true if the nodes value does not equal null
                return node.getValue() != null;
            }
        }
        //else if the curKey has a length that is not 1
        else {
            //if the HashMap, children, contains the curKey's first character
            if (children.containsKey(curKey.charAt(0))) {
                //call containsKey method recursively and calls get to get the node (remove first character from curKey for next iteration)
                return containsKey(children.get(curKey.charAt(0)), curKey.substring(1));
            }
        }
        //return false if never true
        return false;

    }

    //Indirectly recursive method to meet definition of interface
    @Override
    public ArrayList<String> getKeysForPrefix(String prefix) {
        //initializing an instance of a HashMap that is equal to what findNode returns
        TrieMapNode findNode = findNode(root, prefix);
        //calls getSubtreeKeys method with parameter findNode
        return getSubtreeKeys(findNode);
    }

    //Recursive helper function to find node that matches a prefix
    //Note: only a suggestion, you may solve the problem is any recursive manner
    public TrieMapNode findNode(TrieMapNode current, String curKey) {

        //initializing an instance of a HashMap that is equal to the current TrieMapNodes children
        HashMap<Character, TrieMapNode> children = current.getChildren();

        //if the length of curKey is 1 (if there is only 1 character in the word)
        if (curKey.length() == 1) {
            //if the HashMap, children, contains the character
            if (children.containsKey(curKey.charAt(0))) {
                //then a TrieMapNode, node, is equal to that string
                TrieMapNode node = children.get(curKey.charAt(0));
                //returns node
                return node;
            }
        }
        //else if the curKey has a length that is not 1
        else {
            //if the HashMap, children, contains the curKey's first character
            if (children.containsKey(curKey.charAt(0))) {
                //call findNode method recursively and calls get to get the node (remove first character from curKey for next iteration)
                return findNode(children.get(curKey.charAt(0)), curKey.substring(1));
            }
        }
        //if node not found return null
        return null;
    }

    public ArrayList<String> getSubtreeKeys(TrieMapNode current) {

        //initializing ArrayList
        ArrayList<String> keys = new ArrayList<>();

        //if current node is not equal null
        if(current == null) {
            //return ArrayList
            return keys;
        }

        //if the current node has no children (is a leaf)
        if (current.getChildren().isEmpty()) {
            //add current nodes value to ArrayList
            keys.add(current.getValue());
        }
        //recursively call getSubtreeKeys
        getSubtreeKeys(current, keys);
        //return ArrayList
        return keys;
    }

    //Recursive helper function to get all keys in a node's subtree
    //Note: only a suggestion, you may solve the problem is any recursive manner
    public void getSubtreeKeys(TrieMapNode current, ArrayList<String> list) {

        //initializing an instance of a HashMap that is equal to what findNode returns
        HashMap<Character, TrieMapNode> children = current.getChildren();

        //loop through each elements children
        for (Map.Entry<Character, TrieMapNode> entrySet : children.entrySet()) {
            //TrieMapNode, node, is equal entrySet's children
            TrieMapNode node = entrySet.getValue();
            //call getSubtreeKeys
            getSubtreeKeys(node, list);
            //if node's value is not null
            if (node.getValue() != null) {
                //add node's value to ArrayList
                list.add(node.getValue());
            }
        }

    }

    //Indirectly recursive method to meet definition of interface
    @Override
    public void print() {
        //calling print method
        print(root);
    }

    //Recursive method to print values in tree
    public void print(TrieMapNode current) {
        //initializing ArrayList
      ArrayList<String> print = new ArrayList<>();
      // //loop through each elements children
      for (Map.Entry<Character, TrieMapNode> nodes: current.getChildren().entrySet()){
          //call getSubtreeKeys
          getSubtreeKeys(nodes.getValue(), print);
      }
      //prints ArrayList
      System.out.println(print);
    }

    public static void main(String[] args) {
        //You can add some code in here to test out your TrieMap initially
        //The TrieMapTester includes a more detailed test

        //initializing TrieMap
        TrieMap map = new TrieMap();
        //initializing String array
        String[] words = new String[]{"maya", "mayapapaya", "maybe"};

        //looping elements in array
        for (String i : words) {
            //putting elements into TrieMap
            map.put(i, i);
        }
        //printing TrieMap
        map.print();

        //testing methods
        System.out.println();
        System.out.println(map.containsKey("maya"));
        System.out.println(map.get("mayapapaya"));
        System.out.println(map.findNode(map.root, "maybe").getValue());

    }
}
