
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html align=>

<%@page language="Java" import="java.sql.*" %>    
<%@page language="Java" import="javax.swing.*" %>
<%@page language="Java" import="database.BancoSQL"%>
<%@page language="Java" import="database.Pneu"%>
<%@page language="Java" import="java.util.*" %>
<%@page language="Java" import="java.util.Base64" %>

<head>
	<title>Cadastro de Pneus</title>
	<meta http-equiv="content-type" content="text/html;charset=utf-8" />
	<meta name="generator" content="Geany 1.38" />
</head>

<body align="center" bgcolor="Wheat">
	
	 <h1> CRUD - Deolindo Scandolera Neto n 12</h1>
	 <h2> Pneus Cadastrados:</h2>
	 <table border="1" align="center">
	   <tr>
		    <td>Id</td>
			<td>Nome</td>
			<td>Descricao</td>
			<td>Preco</td>
			<td>Marca</td>
			<td>Imagem</td>
		   
	   </tr>
	
	 
	 <%
	 try {
			
		BancoSQL.conectar();
		ArrayList vetor = BancoSQL.pegaBanco();
        
        String xid,xnome,xmarca,xgenero,xpreco, saida;
    	String textoSerializado;
    		
    	byte[] xfot;
    		
    	if(!vetor.isEmpty() || vetor !=null){
        for(int j=0;j<vetor.size(); j++)
        {
			xid=""+vetor.get(j); j++;
			xnome=""+vetor.get(j); j++;
			xmarca=""+vetor.get(j); j++;
			xgenero=""+vetor.get(j); j++;
			xpreco=""+vetor.get(j); j++;
			xfot=(byte [])vetor.get(j);
			saida="";
			textoSerializado="";
			if(xfot!=null)
				 {
                   textoSerializado = Base64.getEncoder().encodeToString(xfot);
                   //System.out.println("Texto em Base64: " + textoSerializado);
				 }
           saida="data:image/jpg;base64," + textoSerializado;
		%>
			
			<tr>
			 <td><%=xid %> </td>
			 <td><%=xnome %> </td>
			 <td><%=xmarca %> </td>
			 <td><%=xgenero %> </td>
			 <td><%=xpreco %> </td>
			 <td><%out.println("<figure> <img src='"+saida+"' alt='"+xnome+"' width='60' height='60' ' /> </figure> <br>");%> </td>
			 <td><%out.println("<a href='editar.jsp?id="+xid+"' >Link para editar </a>");%>  </td>
			</tr>
			<%
		 }//for
	 }//if
		else {
           out.println("<br>Banco de dados vazio!!!!!");
	   }	
           
        BancoSQL.desconectar();
      }    /////try
        catch(Exception e)
    	{
    		out.println("ERRO WEB ="+ e);
    		
    	}//catch
	 %>
	  </table>
	  <table border="2">
	  <tr>
	   <td>
	  <a href="adiciona.html">Novo registro </a>
	  </td>
	  <td>
	  <a href="index.html"> Inicio trabalho </a>
	  </td>
	  </tr>
	  </table>
</body>

</html>
