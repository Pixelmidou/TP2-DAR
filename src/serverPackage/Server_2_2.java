package serverPackage;

import commonPackage.Operation;
import java.io.*;
import java.net.*;

public class Server_2_2 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int port = 1234;

        ServerSocket ss = new ServerSocket();
        ss.bind(new InetSocketAddress("0.0.0.0", port));
        System.out.println("Serveur en écoute sur le port " + port);

        Socket s = ss.accept();
        System.out.println("Client connecté");

        ObjectInputStream in = new ObjectInputStream(s.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());

        Operation operation = (Operation) in.readObject();

        String reponse;
        try {
            double a = operation.getOperand1();
            double b = operation.getOperand2();
            double resultat;

            switch (operation.getOperator()) {
                case "+": resultat = a + b; break;
                case "-": resultat = a - b; break;
                case "*": resultat = a * b; break;
                case "/":
                    if (b == 0) throw new Exception("Division par zéro");
                    resultat = a / b;
                    break;
                default: throw new Exception("Opérateur inconnu");
            }
            reponse = String.valueOf(resultat);
        } catch (Exception e) {
            reponse = "Erreur : " + e.getMessage();
        }

        out.writeObject(reponse);

        s.close();
        ss.close();
    }
}