##################### Date, Local Date e Instant #####################
```
private static void testeDataLocalDateInstant() {
        Instant now = Instant.now();
        LocalDate localDate = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("now: " + now); //now: 2023-10-12T22:43:21.487464133Z
        System.out.println("localDate: " + localDate); //localDate: 2023-10-12
        System.out.println("localDateTime: " + localDateTime); //localDateTime: 2023-10-12T19:43:21.662118849
    }
```
##################### uni em uma string valores de um array separado por virgula #####################
##################### Uni em uma string valores de um array com aspas simples separado por virgula #####################
```
String[] arrEstados = {"PR", "SP", "SC"};
String[] arrCidCodigos = {"1", "3", "6"};

String cidCodigos = String.join(",", arrCidCodigos);
String estados =  unirStringEntreAspasComVirgula(arrEstados);

public static String unirStringEntreAspasComVirgula(String[] arrEstados){
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < arrEstados.length; i++) {
            resultado.append("'").append(arrEstados[i]).append("'");

            if (i < arrEstados.length - 1) {
                resultado.append(",");
            }
        }

        return resultado.toString();
    }
```
##### mes posterior ######
```
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

LocalDate dataAtual = LocalDate.now();
LocalDate dataMesPosterior = dataAtual.plusMonths(1);

DateTimeFormatter formatoMesAno = DateTimeFormatter.ofPattern("MMyyyy");
return dataAtual.format(formatoMesAno) + "/" + dataMesPosterior.format(formatoMesAno);
```
#####################

# funcional-programming-with-java
Repository for sample of funcional programming

################ PrimeFaces ################
```
link com imagem chamando um metodo no back end e passando um paramentro Long id usando ajax
<h:commandLink actionListener="#{auxiliarBean.teste(23)}">
	<p:graphicImage value="../resources/images/refresh.png" />
	<f:ajax execute="@this" render="@form" />
</h:commandLink>
```
######################## Resques SOAP ###################
```
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class SOAPClient {

    public static void main(String[] args) {
        // Configurar a URL do endpoint do serviço SOAP
        String endpointUrl = "http://klsites.com.br/soap-endpoint";

        // Criar uma instância da fábrica de proxy do Apache CXF
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();

        // Definir a URL do endpoint do serviço
        factory.setAddress(endpointUrl);

        // Definir a classe do serviço
        factory.setServiceClass(YourServiceInterface.class);

        // Criar um proxy para o serviço
        YourServiceInterface service = (YourServiceInterface) factory.create();

        // Chamar o método generateProposal
        String marcaVeiculo = "ExemploMarca";
        String nome = "ExemploNome";
        int ano = 2023;
        String xmlResponse = service.generateProposal(marcaVeiculo, nome, ano);

        // Processar a resposta XML
        try {
            // Criar um documento XML a partir do XML de resposta
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document responseDoc = builder.parse(new InputSource(new StringReader(xmlResponse)));

            // Extrair os dados desejados do XML
            // Exemplo: obter o valor de um elemento específico
            String resultado = responseDoc.getElementsByTagName("resultado").item(0).getTextContent();
            System.out.println("Resultado: " + resultado);

            // Outras operações de processamento do XML podem ser realizadas conforme necessário

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```
