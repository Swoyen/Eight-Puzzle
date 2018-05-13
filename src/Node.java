import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Node {
	private GameState state;
	private GameState.Action action;
	private int pathCost;
	private Node parent;
	public int f;
	public int h;
	public int g;
	
	public Node(GameState state, GameState.Action action, int pathCost, Node parent) {
		super();
		this.state = state;
		this.action = action;
		this.pathCost = pathCost;
		this.parent = parent;
	}
	
	public GameState getState() {
		return state;
	}

	public GameState.Action getAction() {
		return action;
	}

	public int getPathCost() {
		return pathCost;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent){
		this.parent = parent;
	}
	public String toString() {
		StringBuilder repr = new StringBuilder();
		repr.append( "Path:      " ).append( getPath() ).append("\n");
		repr.append( "Path cost: " ).append( getPathCost() ).append("\n");
		repr.append( "State:\n" ).append( getState() );
		return repr.toString();
	}

	public List<GameState.Action> getPath() {
		return buildPathList(new ArrayList<GameState.Action>());
	}
	
	private List<GameState.Action> buildPathList(List<GameState.Action> path) {
		if (parent != null) {
			parent.buildPathList(path);
			path.add(action);
		}
		return path;
	}
	
	public Collection<Node> expand() {
		ArrayList<Node> returnNodes = new ArrayList<Node>();
		for(GameState.Action action:state.applicableActions()){
			Node addNode = new Node(state.applyAction(action),action,getPathCost()+1,this);
			returnNodes.add(addNode);
		}
		return returnNodes;
	}
}
