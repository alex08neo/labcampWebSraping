package main;

import esercizi.Esercizio;

public class Main {

	public static void main(String[] args) {

		Esercizio e;
		try {
			e = new Esercizio();
			e.esercizio();
			
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
