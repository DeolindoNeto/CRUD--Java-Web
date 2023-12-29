package database;

import java.util.*;//scanner, arraylist, formatter
import java.io.*; //file
import javax.swing.*;//JOptionPane
import java.sql.*;//sql
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class BancoSQL {
    
   private static File arqtest;
   private static Scanner arqler, tecla;
   private static ArrayList Lista;

   private static String id_pneu, nome_pneu, marca_pneu, descricao, preco;
   private static String nomearq, nomebanco, nometabela;
   private static byte[] imagem;
   
   private static Connection con;   
   private static String url,drive,fsql, caminho;
   private static ResultSet rs;
   private static PreparedStatement pstmt;

    public static void conectar()
   {
        con=null; 
        rs=null;
        pstmt=null;

        caminho="\\xampp\\tomcat\\webapps\\Pneus\\";
        url="jdbc:sqlite:"+ caminho + "banco\\Pneu.db";
        drive="org.sqlite.JDBC";//drive de conexao
        
        Lista=new ArrayList<>();
        nometabela="PneuDeola";
   
       try {
           Class.forName("org.sqlite.JDBC");
           con=DriverManager.getConnection(url);
           System.out.println("Conexão realizada !!!!");
        } catch (Exception e) {
                System.out.println("erro.conexao="+e);
                return;
       }
   }

    public static void desconectar()
    {
      try {
       if (con.isClosed() == false) {
            con.close();
       }
      } catch (Exception e) {
         System.out.println("erroaodesconectar"+e); 
         return;
      }
      System.out.println("desconectou!!!");
	}	//fim desconectar

    public static ArrayList pegaBanco()
	{
	 try
	 {
		Lista.clear();//limpa tudo
		pstmt = con.prepareStatement("select * from PneuDeola");
		rs = pstmt.executeQuery();
		fsql = "update PneuDeola set nome_pneu=?," + "marca_pneu=?, descriao?," + 
		"preco=?, imagem=? where id_pneu=?"; 
	   
		while (rs.next())
		{ // criando o objeto
		  Lista.add(rs.getLong("id_pneu"));
		  Lista.add(rs.getString("nome_pneu"));
		  Lista.add(rs.getString("marca_pneu"));
		  Lista.add(rs.getString("descricao"));
		  Lista.add(rs.getDouble("preco"));
		  Lista.add(rs.getBytes("imagem"));
		  System.out.println("adicionado=");
		}//while
	   rs.close();
	   pstmt.close();
		   System.out.println("Feita leitura Banco");
		  return Lista;
	  } catch (Exception el) {
		 

	System.out.println( "Erro Arraylist"+el);
			 return null;
	   }
	}
	
	public static void adicionar (Pneu pp)
	{
		fsql="insert into PneuDeola (nome_pneu, descricao, preco, marca_pneu, imagem)" + 
		" values (?, ?, ?, ?, ?)"; 
		try{
			
			pstmt= con.prepareStatement(fsql); 
			pstmt.setString(1, pp.getNome());  
			pstmt.setString(2, pp.getDescricao()); 
			pstmt.setDouble(3,pp.getPreco());
			pstmt.setString(4, pp.getMarca());
			pstmt.setBytes(5,pp.getImagem());
				
			pstmt.execute();
			pstmt.close();
			
			System.out.println("Gravado com sucesso!"); 
		}
		
		catch (Exception ei)
		{
			System.out.println("Erro inclusao= " + ei);
			return;
		}
	
	}


	public static byte[] imageToByte(String image) 
	{
		InputStream is = null;
		byte[] buffer = null;
	try
	{
		  is = new FileInputStream(image);
		  buffer = new byte[is.available()];
		  is.read(buffer);
		  is.close();
	}
	catch (Exception e) {
			System.out.println("erro bytes imagem="+e);
	}
	return buffer;
	}

    public static boolean procura(Pneu xp)
	{
		boolean flag=false;
		fsql="select * from PneuDeola where id_pneu=?";
		try {
		 pstmt = con.prepareStatement(fsql);
		 pstmt.setLong(1,xp.getId()); 
		 rs = pstmt.executeQuery();
		 if(rs.next()) //achou
		 {
			System.out.println("Achou Id="+rs.getLong("id_pneu"));
			flag=true;
		 }
		else
		{
		System.out.println(" nao Achou    Id="+xp.getId());
		}
		rs.close();
		pstmt.close();
		 
		return flag;
		} catch (Exception el) {
			  System.out.println("Erro procura="+el);
				 return false;
		}
	}//////////////////////////////////////////////////////////////////////////////////

   public static void altera(Pneu cc) {
	   
    fsql = "update PneuDeola set nome_pneu=?, descricao=?, preco=?, marca_pneu=?, imagem=? where id_pneu=?";
    try {
        pstmt = con.prepareStatement(fsql);
        pstmt.setString(1, cc.getNome());
        pstmt.setString(2, cc.getDescricao());
        pstmt.setDouble(3, cc.getPreco());
        pstmt.setString(4, cc.getMarca());
        pstmt.setBytes(5, cc.getImagem());
        pstmt.setLong(6, cc.getId());
        pstmt.execute();
        pstmt.close();
        System.out.println("Alterado com Sucesso!");
    } catch (Exception e) {
        System.out.println("Erro Altera=" + e);
        return;
    }
}

    public static void exclui(Pneu cc)
	{
		if(procura(cc)==false)
		{    System.out.println(
			"Pneu nao existe!!!");
			return;
		}
		fsql="delete from PneuDeola where id_pneu=?";
		try
		{
			pstmt = con.prepareStatement(fsql);
			pstmt.setLong(1, cc.getId());
			pstmt.execute();
			pstmt.close();
			System.out.println("Excluido com Sucesso!");
		} 
		catch (Exception e) 
		{
			System.out.println("Erro Exclui="+e);
			return; 
		}
	}////////////////////////

	public static ArrayList<Pneu> getLista()
	{
		try 
		{
			ArrayList<Pneu> vetor = new ArrayList<Pneu>();
			pstmt = con.prepareStatement("select * from PneuDeola");
			rs = pstmt.executeQuery();
			while (rs.next())
			 {
			 // criando o objeto Perfume
				Pneu cc = new Pneu(); 
				cc.setId(rs.getInt("id_pneu"));
				cc.setNome(rs.getString("nome_pneu"));
				cc.setDescricao(rs.getString("descricao"));
				cc.setPreco(rs.getDouble("preco"));
				cc.setMarca(rs.getString("marca_pneu"));
				cc.setImagem(rs.getBytes("imagem"));
				
				vetor.add(cc);
			}
			rs.close();
			pstmt.close();
			System.out.println("Feita leittura Banco");
			return vetor;
		} 
		catch (Exception el) 
		{
			  System.out.println("Erro Arraylist"+el);
			  return null;
		}
	}

	public static void ByteArrayToFileImage(byte[] bbb,String xid)
	 {
		  try
		  {
			  ByteArrayInputStream bis = new ByteArrayInputStream(bbb);
			  BufferedImage bImagex = ImageIO.read(bis);
			  ImageIO.write(bImagex, "jpg", new File("imagem"+xid+".jpg") );
			  System.out.println("image created"+xid);
		  }
		  catch(Exception erroi)
		  { 
			  System.out.println("Erro imagem= " + erroi);
			return;
		  }
	}

    public static void gravandotxt()
	{
		try
		{
			Formatter arquivo = new Formatter ("PneuDeola.txt");
			fsql = "select * from PneuDeola";
			pstmt = con.prepareStatement(fsql);
			pstmt = con.prepareStatement("select * from PneuDeola");
			rs = pstmt.executeQuery();
			while (rs.next())
			 {
			 // criando o objeto Perfume
				Pneu cc = new Pneu(); 
				id_pneu = rs.getString("id_pneu");
				nome_pneu = rs.getString("nome_pneu");
				marca_pneu= rs.getString("marca_pneu");
				descricao = rs.getString("descricao");
				preco = rs.getString("preco");
				imagem = rs.getBytes("imagem");
				System.out.println("Lendo banco = " + id_pneu+ " " + nome_pneu);
				arquivo.format("%s; %s; %s; %s; %s; %s; \n" , id_pneu, nome_pneu, marca_pneu, descricao, preco, "imagem" + id_pneu + ".jpg");
				ByteArrayToFileImage(imagem,id_pneu); //gravando picture
			}
			rs.close();
			pstmt.close();
			arquivo.close();
		}
		
		catch (Exception egrava)
		{
			System.out.println(
			"Erro gravando txt = " + egrava);
			return; 
		}
	}

	public static void lendotxt()
	{
		try
		{
			File arquivo = new File ("PneuDeola.txt");
			if(!arquivo.exists())
			{
				System.out.println ("Arquivo nao existe!!!!!");
					return;
			}
			
			Scanner sc = new Scanner (arquivo);
			sc.useDelimiter("\\s*;\\s*");
			String nomeimagem = ""; 
			
			while (sc.hasNext())
			{
				id_pneu = sc.next();
				nome_pneu = sc.next();
				marca_pneu = sc.next();
				descricao = sc.next();
				preco = sc.next();
				nomeimagem= sc.next();
				System.out.println ("lendo  txt=" + id_pneu + nome_pneu + marca_pneu + 
				descricao);
				Pneu ppp = new Pneu(); 
				ppp.setId(Integer.parseInt(id_pneu));
				ppp.setNome(nome_pneu);
				ppp.setMarca(marca_pneu);
				ppp.setDescricao(descricao);
				double vpreco = Double.parseDouble(preco);
				ppp.setPreco (vpreco);
				imagem = imageToByte(nomeimagem);
			
				if (procura(ppp))
					altera (ppp);
				else
					adicionar(ppp);
			} //while
			sc.close();
		}
		
		catch (Exception eler)
		{
			System.out.println("Erro leitura txt = " + eler);
			return;
		}
	}
	
}//class
