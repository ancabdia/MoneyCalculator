package moneycalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * Programa que permite realizar un cambio de divisas de cualquier tipo.
 * Para ello se utiliza la api currecyconverter, a la cual le introducimos los
 * datos de entrada y salida y obtenemos la divisa convertida.
 * @author Andres Cabrera Diaz
 * @version v.2. Permite cualquier cambio de divisa
 */
public class MoneyCalculator {
    
    double amount; //cantidad a convertir
    double exchangerate; //almacenar resultado metodo exchangerate()
    String currencyFrom; //moneda de la que convertir
    String currencyTo; //moneda a devolver

    public static void main(String[] args) throws IOException {
        MoneyCalculator moneyCalculator = new MoneyCalculator();
        moneyCalculator.control();
    }
    
    private void control() throws IOException {
        input();
        process();
        output();
    }
    
    //Datos de entrada
    private void input() {
        System.out.println("Introduce una cantidad: ");
        Scanner scanner = new Scanner(System.in);
        amount = scanner.nextDouble();

        System.out.println("Introduce moneda inicial: ");
        currencyFrom = scanner.next().toUpperCase();
        
        System.out.println("Introduce moneda resultado: ");
        currencyTo = scanner.next().toUpperCase();
        
        //Comprobar que las divisas a convertir no sean las mismas
        while(currencyTo.equals(currencyFrom)){
            System.out.println("No puedes convertir dos monedas iguales.");
            System.out.println("Introduce moneda resultado: ");
            currencyTo = scanner.next().toUpperCase();
        }
    }
    
    //Parte que realiza el proceso (llamada a proceso)
    private void process() throws IOException{
        exchangerate = getExchangeRate(currencyFrom,currencyTo);
    }

    //Datos salida
    private void output() {
        System.out.println(amount + " " + currencyFrom + " = " + 
                amount*exchangerate + " " + currencyTo);
    }

    private static double getExchangeRate(String from, String to) throws IOException {
        URL url = 
            new URL("http://free.currencyconverterapi.com/api/v5/convert?q=" +
                    from + "_" + to + "&compact=y");
        URLConnection connection = url.openConnection();
        try (BufferedReader reader = 
                new BufferedReader(
                        new InputStreamReader(connection.getInputStream()))) {
            String line = reader.readLine(); //leer cadena completa
            String line1 = line.substring(line.indexOf(to)+12, line.indexOf("}"));
            return Double.parseDouble(line1);
        }
    }
}