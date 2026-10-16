package serverPackage;

import java.io.*;
import java.net.*;

public class Server_2_1 {
    public static void main(String[] args) throws IOException {
        int port = 1234;

        ServerSocket ss = new ServerSocket();
        ss.bind(new InetSocketAddress("0.0.0.0", port));
        System.out.println("Serveur en écoute sur le port " + port);

        Socket s = ss.accept();
        System.out.println("Client connecté");

        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()), true);

        String reponse;
        try {
            String aStr = in.readLine();
            String op = in.readLine();
            String bStr = in.readLine();

            double a = Double.parseDouble(aStr);
            double b = Double.parseDouble(bStr);

            double resultat;
            switch (op) {
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

        out.println(reponse);

        s.close();
        ss.close();
    }
}