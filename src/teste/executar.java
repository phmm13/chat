package teste;

import java.util.HashMap;
import java.util.Set;

public class executar {
	public static void main(String args[]){
		String[] x = "ola : tudoasda s d sadsad a dasd  asdsda  : bom".split(":");
		
		System.out.println("x[0] : " + x[0]);

		System.out.println("x[1] : " + x[1]);
		
		System.out.println("x[2] : " + x[2]);
		
		System.out.println("Tamanho : "+x.length);
		HashMap<String, String> mapa = new HashMap<String, String>();
		mapa.put("Diegoo", " Ricardo");
		mapa.put(null, "Teste");
		mapa.put(null, "Outro Teste");
		mapa.put("Diego", "teste1");
		mapa.put("Diego","diego modificado");
		//mapa.remove("Diegoo");
		 Set<String> chaves = mapa.keySet();
		 
		for (String chave : chaves)
		{
			if(chave != null)
				System.out.println(mapa.get(chave));
		}
		
		System.out.println(mapa.get("nao"));
	}
}
