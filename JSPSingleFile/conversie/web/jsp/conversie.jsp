<%@ page import="java.io.File,java.util.ArrayList,javax.xml.parsers.DocumentBuilder,javax.xml.parsers.DocumentBuilderFactory,org.w3c.dom.Document,org.w3c.dom.Element,org.w3c.dom.Node,org.w3c.dom.NodeList,java.io.PrintWriter,java.io.IOException" %>
<html>
  <body>
    <H1> Conversie </H1>
    <%!
       public double EuroToRon(double amount, double eur){
		return eur*amount;
	}
	public double EuroToUsd(double amount,double eur, double usd){
		return amount * eur/usd;
	}
	
	public double UsdToRon(double amount, double usd){
		return amount * usd;
	}
	public double UsdToEuro(double amount,double usd, double eur){
		return amount *usd/eur;
	}
	public double RonToEuro(double amount,double eur){
		return amount/eur;
	}
	public double RonToUsd(double amount,double usd){
		return amount/usd;
	}
public ArrayList<String> getCurrencyFactor(String xml){
	
	ArrayList<String> list = new ArrayList<>();
	try {
		File file = new File(xml);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbf.newDocumentBuilder();
		Document  doc = dBuilder.parse(file);
		
		NodeList  nList = doc.getElementsByTagName("Rate");

		int j = nList.getLength();
		for (int i = 0; i < j; i++) {
			
			Node nNode = nList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String attribute = eElement.getAttribute("currency");
					if (attribute.equals("EUR")){
					String value = eElement.getTextContent();
					list.add(0,value);
					}
					if (attribute.equals("USD")){
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
		public String GetOutput(String s, String v){
		String mystr = "test";
		ArrayList<String> currencies = getCurrencyFactor("C:\\Users\\dilaila\\Desktop\\conversie.xml");
		double eur = Double.parseDouble(currencies.get(0));
		double usd = Double.parseDouble(currencies.get(1));
		double suma = Double.parseDouble(s);
		switch (v) {
		case "EUR":
			mystr = suma + "EUR" + " = " + EuroToRon(suma,eur) + "RON" + " si "  + EuroToUsd(suma,eur,usd) + "USD"; 
			break;
		case "USD":
			mystr = suma + "USD" + " = " + UsdToRon(suma,usd) + "RON" + " si "  + UsdToEuro(suma,usd,eur) + "EUR";
			break;	
		case "RON":
			mystr = suma + "RON" + " = " + RonToUsd(suma,usd) + "USD" + " si "  + RonToEuro(suma,eur) + "EUR" ;
			break;	
		default:
			break;
		}
		return mystr;
		}
		
    %>
    Rezultatul este 
    <% 
		String param1 = request.getParameter("suma");
		String param2=request.getParameter("valuta");
		out.println(GetOutput(param1,param2));
    %>
    </body>
</html>
