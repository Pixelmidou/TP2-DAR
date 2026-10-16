package clientPackage;

import commonPackage.Operation;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client_2_2 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);

        System.out.print("Premier opérande : ");
        double a = Double.parseDouble(sc.nextLine());
        System.out.print("Opérateur (+ - * /) : ");
        String op = sc.nextLine();
        System.out.print("Deuxième opérande : ");
        double b = Double.parseDouble(sc.nextLine());

        Operation operation = new Operation(a, op, b);

        String ipServeur = "192.168.1.10";
        InetSocketAddress endpoint = new InetSocketAddress(ipServeur, 1234);
        Socket s = new Socket();
        s.connect(endpoint, 5000);

        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(s.getInputStream());

        out.writeObject(operation);

        String reponse = (String) in.readObject();
        System.out.println("Réponse : " + reponse);

        s.close();
        sc.close();
    }
}