import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class AStarSearch {

	private GameState initialState;

	private int explored = 0;

	public AStarSearch(GameState initialState) {
		this.initialState = initialState;
		initialState.heuristic2();
	}

	public Node search() {

		PriorityQueue<Node> openList = new PriorityQueue<Node>(new Comparator<Node>(){

			@Override
			public int compare(Node o1, Node o2) {
				if (o1.f < o2.f)
					return -1;
				if (o2.f < o1.f)
					return 1;
				return 0;
			}

		});
		Node start = new Node(this.initialState,null,0,null);
		start.g = 0;
		start.f=initialState.heuristic();
		openList.add(start);
		explored++;
		System.out.println(explored);

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

	public Node find() {
		int order = 0;
		Node startN = new Node(this.initialState,null,0,null);

		PriorityQueue<Node> openList = new PriorityQueue<Node>(new Comparator<Node>(){

			@Override
			public int compare(Node o1, Node o2) {
				if (o1.f <  o2.f)
					return -1;
				else if(o1.f > o2.f)
					return 1;

				return 0;


			}

		});

		Set<Node> closedList = new HashSet<Node>();

		startN.f = initialState.heuristic();
		startN.order = order;
		openList.add(startN);

		while(!openList.isEmpty()){
			Node q = openList.poll();
			explored++;

			if(q.getState().isGoalState()){

				return q;
			}

			closedList.add(q);

			for(Node neighbor:q.expand()){

				order +=0.0001;

				System.out.println();

				if(hasNode(closedList,neighbor) || hasNode(openList,neighbor)){
					continue;
				}

				int tentativeG = neighbor.g + q.g;

				System.out.println(openList.size());
				System.out.println(closedList.size());

				if(!hasNode(openList,neighbor)){

					neighbor.g = tentativeG;
					neighbor.f = neighbor.g + neighbor.getState().heuristic() + order; 

					openList.add(neighbor);

					for(Node n:openList) {
						System.out.print(n.f + " ");
					}
				}
				else if(tentativeG >= neighbor.g ){
					continue;
				}
			}
		}

		return null;

	}

	private boolean hasNode(PriorityQueue<Node> openList,Node toCompare) {
		for(Node node:openList) {
			if (node.getState().equals(toCompare.getState())) {
				//System.out.println("true");
				return true;
			}
		}
		return false;
	}

	private boolean hasNode(Set<Node> List,Node toCompare) {
		for(Node node:List) {
			if (node.getState().equals(toCompare.getState())) {
				//System.out.println("true");
				return true;
			}
		}
		return false;
	}


	public int getExploredCount() {

		return explored;
	}

}
