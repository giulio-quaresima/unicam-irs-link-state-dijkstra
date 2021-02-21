package it.quaresima.unicam.irs.linkstatedijkstra;

import java.util.Collections;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.TreeMap;

/**
 * 
 * 
 * @author Giulio Quaresima (giulio.quaresima--at--gmail.com, giulio.quaresima--at--unipg.it, giulio.quaresima--at--studenti.unicam.it)
 */
public class Router implements Comparable<Router>
{
	private final String name;
	private final NavigableMap<Router, Integer> links;
	private static final NavigableMap<String, Router> ROUTERS = new TreeMap<>();

	private Router(String name)
	{
		super();
		this.name = Objects.requireNonNull(name);
		links = new TreeMap<>();
	}
	
	public static NavigableMap<String, Router> getRouters()
	{
		return ROUTERS;
	}

	public static void link(String from, int weight, String to)
	{
		ROUTERS.computeIfAbsent(from, Router::new).addLink(ROUTERS.computeIfAbsent(to, Router::new), weight);
	}
	
	public static Router get(String name)
	{
		return ROUTERS.get(name);
	}
	
	public String getName()
	{
		return name;
	}

	public NavigableMap<Router, Integer> getLinks()
	{
		return Collections.unmodifiableNavigableMap(links);
	}
	
	/**
	 * Add a symmetric link
	 * 
	 * @param target
	 * @param weight
	 */
	public void addLink(Router target, int weight)
	{
		if (weight < 0)
		{
			throw new IllegalArgumentException("negative weight");
		}
		if (!equals(target))
		{
			links.put(target, weight);
			target.links.put(this, weight);
		}
	}
	
	public int getWeight(Router router)
	{
		if (equals(router))
		{
			return 0;
		}
		
		if (links.containsKey(router))
		{
			return links.get(router);
		}
		
		return Integer.MAX_VALUE;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Router other = (Router) obj;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int compareTo(Router o)
	{
		return getName().compareTo(o.getName());
	}
	
}
