<%@page language="java" import="java.sql.*" %>    
<%@page language="java" import="javax.swing.*" %>
<%@page language="java" import="database.BancoSQL"%>
<%@page language="java" import="database.Pneu"%>
<%@page language="java" import="java.util.*" %>
<%@page language="java" import="java.util.Base64" %>

<html>
<head>
    <link rel="icon" href="unesp.png" type="image/jpg" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>:: Alterar / Excluir ::</title>
</head>

<body bgcolor="MediumSlateBlue">
    <center>
        <form action="pneus.jsp" method="post">
            <font color="#000">
                <b><n> ----------------- ALTERA/EXCLUI ---------------------</n></b>
                <br>
                <br>
                <font color="#FF0000" style='font-weight:bold;'>
                <%
                    try {
                        String zid = request.getParameter("id");
                        String znome = request.getParameter("nome");
                        String zdesc = request.getParameter("descricao");
                        String zpreco = request.getParameter("preco");
                        String zmarca = request.getParameter("marca");
                        String znovafoto = request.getParameter("novafoto");
                        String caminhofoto = "C://xampp//tomcat//webapps//Pneus//foto//";
                        byte[] xfotorig = null;

                        BancoSQL.conectar();
                        ArrayList vetor = BancoSQL.pegaBanco();
                        vetor=BancoSQL.pegaBanco();
                        long lnumero = Long.parseLong(zid);

                        int jj = vetor.indexOf(lnumero);
                        System.out.println("posicao do vetor=" + jj);
                        String xid = "" + vetor.get(jj);
                        jj++;
                        String xnome = "" + vetor.get(jj);
                        jj++;
                        String xmarca = "" + vetor.get(jj);
                        jj++;
                        String xdesc = "" + vetor.get(jj);
                        jj++;
                        String xpreco = "" + vetor.get(jj);
                        jj++;
                        xfotorig = (byte[]) vetor.get(jj);

                        if (znovafoto.length() != 0) {
                            caminhofoto = caminhofoto + znovafoto;
                            xfotorig = BancoSQL.imageToByte(caminhofoto);
                        }

                        String altexc = request.getParameter("altexc");

                        Pneu ppp = new Pneu();
                        ppp.setId(Integer.parseInt(zid));
                        ppp.setNome(znome);
                        ppp.setDescricao(zdesc);
                        double vpreco = Double.parseDouble(zpreco);
                        ppp.setPreco(vpreco);
                        ppp.setMarca(zmarca);
                        ppp.setImagem(xfotorig);

                        if (altexc.equals("excluir")) {
                            BancoSQL.exclui(ppp);
                            out.println("\n<h1> Dados " + znome + " Excluidos com sucesso !</h1>");
                        } else {
                            BancoSQL.altera(ppp);
                            out.println("\n <h1>Dados " + znome + " Alterados com sucesso !</h1>");
                        }
                        BancoSQL.desconectar();
                    } catch (Exception e) {
							e.printStackTrace();
							out.println("<h1>Error processing the request: " + e.getMessage() + "</h1>");                  
							}
                %>
                <input type="submit" value="--> Continuar Alterando ou Excluindo <--" align="top" /><br>
                <br>
                <br>
            </font>
        </form>
    </center>
</body>
</html>
