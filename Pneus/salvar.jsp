<html>
	
<%@page language="Java" import="java.sql.*" %>    
<%@page language="Java" import="javax.swing.*" %>
<%@page language="Java" import="database.BancoSQL"%>
<%@page language="Java" import="database.Pneu"%>
<%@page language="Java" import="java.util.*" %>
<%@page language="Java" import="java.util.Base64" %>

<body background="stockcar.jpg">
	
	   <%-- comentario em JSP aqui: nossa primeira pagina JSP --%>
       
       <%
       
		String znome = request.getParameter("nome");
		String zmarca = request.getParameter("marca");
		String zdescricao = request.getParameter("descricao");
		String zpreco = request.getParameter("preco");
		String nomefoto = request.getParameter("imagem");
		String caminhofoto="c://xampp//tomcat//webapps//Pneus//foto//"+nomefoto;
		
		byte[] bytefoto=BancoSQL.imageToByte(caminhofoto);
		
		Pneu ppp = new Pneu();

		ppp.setNome(znome);
		ppp.setMarca(zmarca);
		ppp.setDescricao(zdescricao);
		double xpreco = Double.parseDouble(zpreco);
		ppp.setPreco(xpreco);
		ppp.setImagem(bytefoto);
		
		
		BancoSQL.conectar();
	   	BancoSQL.adicionar(ppp);
	   	BancoSQL.desconectar();
      %>


<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>

<form action="pneus.jsp" method="get">

<font color="#000">
<b><n> ------------------------------------------------------ Cadastrar novos  ------------------------------------------------------</n></b>
<br>
<br>
<br>
<font color="#FF0000" style='font-weight:bold;'>
Dados do Pneu:  <%
    String saida = "";
    String textoSerializado = "";
    textoSerializado = Base64.getEncoder().encodeToString(bytefoto);
    saida = "data:image/png;base64," + textoSerializado;
    out.println("|" + znome + "|" + zmarca + "|" + zdescricao + "|" + zpreco + "|<br> <br>");
    out.println("<figure> <img src='" + saida + "'  width='100' height='100' ' /> </figure> <br>");
%> Adicionado com sucesso!!

 <input 

type="submit" name="teste" value="Continuar Cadastrando >" align="top" /><br>


<br>
<br>

<br>
<br>
</form>

</center>

</body>
</html>
