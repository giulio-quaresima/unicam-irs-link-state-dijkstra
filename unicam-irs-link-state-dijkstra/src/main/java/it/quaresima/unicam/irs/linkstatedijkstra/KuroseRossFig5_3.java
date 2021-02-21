package it.quaresima.unicam.irs.linkstatedijkstra;

public class KuroseRossFig5_3
{

	public static void main(String[] args)
	{
		Router.link("u", 5, "w");
		Router.link("u", 2, "v");
		Router.link("u", 1, "x");
		Router.link("v", 3, "w");
		Router.link("v", 2, "x");
		Router.link("x", 3, "w");
		Router.link("x", 1, "y");
		Router.link("w", 1, "y");
		Router.link("w", 5, "z");
		Router.link("y", 2, "z");
		
		DijkstraLinkState dijkstraLinkState = new DijkstraLinkState(Router.get("u"));
		dijkstraLinkState.findOptimalRoutes();
		dijkstraLinkState.printRoutingTable();
	}

}
