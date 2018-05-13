import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class AStarSearch {

	private GameState initialState;
	private int explored = 0;
	public AStarSearch(GameState initialState) {
		// FIXME: implement this
		this.initialState = initialState;
		initialState.heuristic();
	}
	
	public Node search() {
		/*open <- []
				next <- start

				while next is not goal {
				    add all successors of next to open
				    next <- select one node from open
				    remove next from open
				}

				return next
		*/
		
		/*
		 * Let pq be an empty min priority queue
  
  	g(start) = 0
  f(start) = h(start)
  path(start) = []
  pq.push(start, f(start))
  
  while not pq.empty():
    top = pq.pop()
    if isGoal(top):
      return f(top), path(top)
    foreach next in succ(top):
      g(next) = g(top) + cost(top, next)
      f(next) = g(next) + h(next)
      path(next) = path(top).append(next)
      pq.push(next, f(next))
		 */
		PriorityQueue<Node> openList = new PriorityQueue<Node>(new Comparator<Node>(){
			
			@Override
			public int compare(Node o1, Node o2) {
				if (o1.f < o2.f)
					return -1;
				if (o2.f < o1.f)
					return 1;
				return 0;
			}
			
		});Node start = new Node(this.initialState,null,0,null);
		start.g = 0;
		
		start.f=initialState.heuristic();
		openList.add(start);
		explored++;
		
		while(!openList.isEmpty()){
			Node top = openList.poll();
			if(top.getState().isGoalState()){
				System.out.println("Hello");
				System.out.println(top.getState().heuristic());
				return top;
			}
			for(Node successor:top.expand()){
				successor.g = top.g + explored;
				successor.f = successor.f + successor.getState().heuristic();
				successor.setParent(top);	
				
				openList.add(successor);
			}
		}
		return null;
	}

	public int getExploredCount() {
		
		return explored;
	}
	
}
