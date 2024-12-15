import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.*;
import java.net.*;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.*;

class Node{
    char ch;
    HashMap<Character,Node> hp;
    boolean isTerminal;
    String meaning;

    Node(char ch, boolean flag, String meaning){
        this.ch = ch;
        this.isTerminal = flag;
        this.meaning = meaning;
        hp = new HashMap<>();
    }
}
class Trie {
    static Node root;

    Trie() {
        root = new Node('.', false, "Start Node");
    }
public void insert(String s, String meaning) {
        int n = s.length();

        Node curr = root;
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            if (curr.hp.containsKey(ch)) {
                curr = curr.hp.get(ch);
            } else {
                Node x = new Node(ch, false, "No Meaning");
                curr.hp.put(ch, x);
                curr = x;
            }
        }
        curr.isTerminal = true;
        curr.meaning = meaning;
    }

    public String search(String s) {
        int n = s.length();
        Node curr = root;

        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            if (curr.hp.containsKey(ch)) {
                curr = curr.hp.get(ch);
            } else {
                return "Invalid Word";
            }
        }
        return curr.meaning;
    }
}

public class Server{

    static Scanner sc = new Scanner(System.in);

    static Trie creatDictionary() throws IOException {
        Trie dictionary = new Trie();
        File f = new File("C:\\Users\\DELL\\OneDrive\\Desktop\\Computer Network\\Input.txt");

        BufferedReader br = new BufferedReader(new FileReader(f));
        String s;
        while ((s = br.readLine()) != null) {
            String x[] = s.split("-");
            String meaning = "";

            for(int i=1; i<x.length; i++){
                meaning+=(x[i]+" ");
            }
            dictionary.insert(x[0],meaning);
        }
        return dictionary;
    }
    public static void main(String[] args) throws Exception  {

        System.out.println("Server has started");
        ServerSocket ss = new ServerSocket(8843);
        String str = "";
        System.out.println("Server is waiting to connect with client");
        Socket cs = ss.accept();
        System.out.println("Client Connected");
        System.out.println();

        DataInputStream ssInput = new DataInputStream(cs.getInputStream());
        DataOutputStream ssOutput = new DataOutputStream(cs.getOutputStream());

        Trie Dictionary = creatDictionary();
while(true) {
            String word = ssInput.readUTF();
            if(word.equals("Stop")){
                System.out.println("Closing Server");
                return;
            }
            System.out.println("Fetching the meaning of " + word);

            String meaning = Dictionary.search(word);
            if(meaning.equals("Invalid Word")){
                System.out.println("Word not found on server!");
            }
            else{
                System.out.println("Sending the meaning of "+  word +" to Client");
            }
            System.out.println();
            ssOutput.writeUTF(meaning);

        }

    }

}





 
