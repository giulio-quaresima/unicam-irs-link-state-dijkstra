package it.quaresima.unicam.irs.linkstatedijkstra;

/**
 * Esercizio n. 1, slide ch4 - esercizi tutorato, Internet, Reti e Sicurezza, 2020/21, Università di Camerino.
 * 
 * @author Giulio Quaresima (giulio.quaresima--at--gmail.com, giulio.quaresima--at--unipg.it, giulio.quaresima--at--studenti.unicam.it)
 */
public class Esercizio01
{

	public static void main(String[] args)
	{
		Router.link("A", 4, "B");
		Router.link("A", 2, "F");
		Router.link("B", 10, "C");
		Router.link("B", 1, "D");
		Router.link("C", 7, "D");
		Router.link("C", 2, "E");
		Router.link("D", 3, "E");
		Router.link("D", 8, "F");
		Router.link("E", 12, "F");
		
		DijkstraLinkState dijkstraLinkState = new DijkstraLinkState(Router.get("E"));
		dijkstraLinkState.findOptimalRoutes();
		dijkstraLinkState.printRoutingTable();
	}

}
