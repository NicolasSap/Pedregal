package pedre;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import pedre.DistDemException;

public class Pedregal {
	private boolean[][] terreno;
	private int frente;
	private int largo;
	private int peñascos;
	
	@SuppressWarnings("resource")
	public Pedregal(String path) throws DistDemException, IOException {
		Scanner sc = new Scanner(new File(path+ ".in"));
		terreno = new boolean[sc.nextInt()][sc.nextInt()];
		frente = sc.nextInt();
		largo = sc.nextInt();
		if((terreno.length >= frente && terreno[0].length >= largo )||(terreno.length >= largo && terreno[0].length >= frente )){
			peñascos = sc.nextInt();
			if(((terreno.length == frente && terreno[0].length == largo )||(terreno.length == largo && terreno[0].length == frente ))&& peñascos != 0){
				this.imprimirSalida(path, "No", 0, 0, "S");
				throw new DistDemException("El terreno no es suficientemente grande para que entre la casa y los peñascos");
			}
			while (sc.hasNextLine())
			terreno[sc.nextInt()-1][sc.nextInt()-1] = true;
		}
		else{
			throw new DistDemException("La casa es más grande que el terreno");
		}
		sc.close();
	}
	
	public void mostrarPedregal() {
		System.out.println("Pedregal: ");
		for (int i = 0; i < terreno.length; i++) {
			System.out.println(Arrays.toString(terreno[i]));
		}
	}

	public int getFrente() {
		return frente;
	}

	public void setFrente(int frente) {
		this.frente = frente;
	}

	public int getLargo() {
		return largo;
	}

	public void setLargo(int largo) {
		this.largo = largo;
	}

	public int getPeñascos() {
		return peñascos;
	}

	public void setPeñascos(int peñascos) {
		this.peñascos = peñascos;
	}
	
	public void resolver(String path) throws IOException{
		if(!buscarHorizontal(path)){
			if(!buscarVertical(path)){
				this.imprimirSalida(path, "No", 0, 0, "S");
			}
		}	
	}

	private boolean buscarHorizontal(String path) throws IOException {
		for (int j = 0; j < terreno[0].length-this.largo+1; j++) {
			for (int i = 0; i < terreno.length-this.frente+1; i++) {
				if(!terreno[i][j]){
					if(this.esUbicableHorizontal(i,j)){
						this.imprimirSalida(path, "Si", i, j, "S");
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean buscarVertical(String path) throws IOException {
		for (int j = 0; j < terreno[0].length-this.frente+1; j++) {
			for (int i = 0; i < terreno.length-this.largo+1; i++) {
				if(!terreno[i][j]){
					if(this.esUbicableVertical(i,j)){
						this.imprimirSalida(path, "Si", i, j, "O");
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean esUbicableHorizontal(int origeni, int origenj) {
		for (int j = 0; j < this.largo; j++) {
			for (int i = 0; i < this.frente; i++) {
				if(terreno[i + origeni][j + origenj]){
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean esUbicableVertical(int origeni, int origenj) {
		for (int j = 0; j < this.frente; j++) {
			for (int i = 0; i < this.largo; i++) {
				if(terreno[i + origeni][j + origenj]){
					return false;
				}
			}
		}
		return true;
		
	}
	public void imprimirSalida(String path, String resp, int a, int b, String pC) throws IOException{
		FileWriter archivo = new FileWriter(path + ".out");
		PrintWriter fichero = new PrintWriter(archivo);
		fichero.println(resp);
		if(resp.equals("Si")){
			fichero.println(String.valueOf(a+1) + " "+ String.valueOf(b+1));
			fichero.println(pC);
		}
		fichero.close();
	}
}
