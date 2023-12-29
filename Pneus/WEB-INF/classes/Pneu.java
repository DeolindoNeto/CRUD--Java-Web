//Pneu.java
package database;

public class Pneu
{
    private static int id_pneu;
    private static String nome_pneu, marca_pneu, descricao;
    private static double preco;
    private static byte[] imagem;
    
    public Pneu()
    {
        setId(0);
        setNome("");
        setDescricao("");
        setPreco(0.0);
        setMarca("");
        setImagem(null);
    }
    
    public static void setId(int i)
    {
        id_pneu=i;
    }
    public static void setNome(String n)
    {
        nome_pneu=n;
    }
    public static void setDescricao(String d)
    {
        descricao=d;
    }
    public static void setPreco(double p)
    {
        preco=p;
    }
    public static void setMarca(String m)
    {
        marca_pneu=m;
    }
    public static void setImagem(byte[] im)
    {
        imagem=im;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static int getId()
    {
        return id_pneu;
    }
    public static String getNome()
    {
        return nome_pneu;
    }
    public static String getDescricao()
    {
        return descricao;
    }
    public static double getPreco()
    {
        return preco;
    }
    public static String getMarca()
    {
        return marca_pneu;
    }
    public static byte[] getImagem()
    {
        return imagem;
    }
    public void ToString()
	{
		System.out.println("Id: " + getId());
		System.out.println("Nome: " + getNome());
		System.out.println("Marca: " + getMarca());
		System.out.println("Descricao: " + getDescricao());
		System.out.println("Preco: " + getPreco());
		
	}
	
	public void emLinha()
	{
		System.out.print(",\t" + getId());
		System.out.print(",\t" + getNome());
		System.out.print(",\t" + getMarca());
		System.out.print(",\t" + getDescricao());
		System.out.print(",\t" + getPreco());
	}
    
}
