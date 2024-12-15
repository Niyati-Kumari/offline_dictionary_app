import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client{
    public static void main(String[] args)  throws Exception
    {
        Scanner sc = new Scanner(System.in);
        Socket cs = new Socket("localhost",8843);
        DataInputStream csInput = new DataInputStream(cs.getInputStream());
               DataOutputStream csOutput = new DataOutputStream(cs.getOutputStream());
        System.out.println("Please type 'Stop' to abort! ");
        System.out.println();
        while(true){
            System.out.println("Enter the Word");
            String word = sc.next();
            csOutput.writeUTF(word);
            if(word.equals("Stop")){
                System.out.println("Thank for Using the Dictionary! :)");
                System.out.println("BYE!");
                return;
            }
            String meaning = csInput.readUTF();
            if(meaning.equals("Invalid Word")){
                System.out.println("OOPS, It's an invalid word! :(");
            }else {
                System.out.println("Definition of \"" + word + "\": " + meaning);
            }
            System.out.println();
        }


    }
}

 




 
