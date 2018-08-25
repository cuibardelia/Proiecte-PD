package exchange.rmi.s;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;


public class Conversion {

    public double EuroToRon(double amount, double eur) {
        return eur * amount;
    }

    public double EuroToUsd(double amount, double eur, double usd) {
        return amount * eur / usd;
    }

    public double UsdToRon(double amount, double usd) {
        return amount * usd;
    }

    public double UsdToEuro(double amount, double usd, double eur) {
        return amount * usd / eur;
    }

    public double RonToEuro(double amount, double eur) {
        return amount / eur;
    }

    public double RonToUsd(double amount, double usd) {
        return amount / usd;
    }

    public ArrayList<String> getCurrencyFactor(String xml) {

        ArrayList<String> list = new ArrayList<>();
        try {
            File file = new File(xml);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbf.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            NodeList nList = doc.getElementsByTagName("Rate");

            int j = nList.getLength();
            for (int i = 0; i < j; i++) {

                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String attribute = eElement.getAttribute("currency");
                    if (attribute.equals("EUR")) {
                        String value = eElement.getTextContent();
                        list.add(0, value);
                    }
                    if (attribute.equals("USD")) {
                        String value = eElement.getTextContent();
                        list.add(1, value);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong " + e.getMessage());
        }
        return list;
    }

    public String getOutput(Double amountToConvert, int currency) {
        String response = new String();
        ArrayList<String> currencies = getCurrencyFactor("C:\\Users\\dilaila\\Desktop\\conversie.xml");
        double eur = Double.parseDouble(currencies.get(0));
        double usd = Double.parseDouble(currencies.get(1));
        switch (currency) {
            case 1:
                response = "" + amountToConvert + "EUR" + " = " + EuroToRon(amountToConvert, eur) + "RON" + " si " + EuroToUsd(amountToConvert, eur, usd) + "USD";
                System.out.println(response);
                break;
            case 2:
                response = amountToConvert + "USD" + " = " + UsdToRon(amountToConvert, usd) + "RON" + " si " + UsdToEuro(amountToConvert, usd, eur) + "EUR";
                break;
            case 3:
                response = amountToConvert + "RON" + " = " + RonToUsd(amountToConvert, usd) + "USD" + " si " + RonToEuro(amountToConvert, eur) + "EUR";
                break;
            default:
                response =  "ERROR! Retry using one of the supported currency mapping: 1 - EUR, 2 - USD, 3 - RON.";
                break;
        }
        return response;
    }
}
