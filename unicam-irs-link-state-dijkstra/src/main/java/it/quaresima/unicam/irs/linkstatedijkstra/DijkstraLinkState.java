package it.quaresima.unicam.irs.linkstatedijkstra;

import java.util.Collections;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * 
 * 
 * @author Giulio Quaresima (giulio.quaresima--at--gmail.com, giulio.quaresima--at--unipg.it, giulio.quaresima--at--studenti.unicam.it)
 */
public class DijkstraLinkState
{
	private final NavigableSet<Router> reachableRouters;
	private final NavigableSet<Router> establishedRouters;
	private final NavigableMap<Router, Integer> costs;
	/**
	 * key = target, value = previous node
	 */
	private final NavigableMap<Router, Router> paths;

	public DijkstraLinkState(Router start)
	{
		super();
		this.reachableRouters = Collections.unmodifiableNavigableSet(findReachableRouters(start, new TreeSet<>()));
		this.establishedRouters = new TreeSet<>();
		this.costs = new TreeMap<>();
		this.paths = new TreeMap<>();
		
		for (Router router : reachableRouters)
		{
			costs.put(router, Integer.MAX_VALUE);
		}
		costs.put(start, 0);
		start.getLinks().entrySet().forEach(entry -> {
			costs.put(entry.getKey(), entry.getValue());
			paths.put(entry.getKey(), start);
		});
	}
	
	public void findOptimalRoutes()
	{
		while (!establishedRouters.equals(reachableRouters))
		{
			Router minRouter = null;
			int minWeight = Integer.MAX_VALUE;
			for (Map.Entry<Router, Integer> entry : costs.entrySet())
			{
				if (!establishedRouters.contains(entry.getKey()) && entry.getValue() <= minWeight)
				{
					minWeight = entry.getValue();
					minRouter = entry.getKey();
				}
			}
			assert minRouter != null;
			establishedRouters.add(minRouter);
			
			for (Map.Entry<Router, Integer> entry : minRouter.getLinks().entrySet())
			{
				if (!establishedRouters.contains(entry.getKey()))
				{
					int actualCost = costs.get(entry.getKey());
					int newCost = costs.get(minRouter) + entry.getValue();
					if (newCost <= actualCost)
					{
						costs.put(entry.getKey(), newCost);
						paths.put(entry.getKey(), minRouter);
					}
				}
			}
		}
	}
	
	public void printRoutingTable()
	{
		for (Router target : establishedRouters)
		{
			StringBuilder stringBuilder = new StringBuilder("Route to ");
			stringBuilder.append(target.getName());
			stringBuilder.append(" (w. ");
			stringBuilder.append(costs.get(target));
			stringBuilder.append(") ");
			printRoute(target, stringBuilder);
			System.out.println(stringBuilder);
		}
	}

	private void printRoute(Router target, StringBuilder stringBuilder)
	{
		Router previous = paths.get(target);
		if (previous != null)
		{
			printRoute(previous, stringBuilder);
			stringBuilder.append(" --");
			stringBuilder.append(previous.getLinks().get(target));
			stringBuilder.append("--> ");
		}
		stringBuilder.append(target.getName());
	}

	private NavigableSet<Router> findReachableRouters(Router start, NavigableSet<Router> discoveredRouters)
	{
		if (!discoveredRouters.contains(start))
		{
			discoveredRouters.add(start);
			for (Router to : start.getLinks().keySet())
			{
				findReachableRouters(to, discoveredRouters);
			}
		}
		return discoveredRouters;
	}

}
