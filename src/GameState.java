import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class GameState {
	public static final int    SIZE  = 3;
	public static final String EMPTY = " ";
	
	private List<String> tiles = new ArrayList<>();
	
	public GameState(String[] tiles) {
		this(Arrays.asList(tiles));
		
	}
	
	public GameState(List<String> tiles) {
		super();
		this.tiles = tiles;
	}
	
	public String toString() {
		StringBuilder repr = new StringBuilder();
		for(int i=0; i<tiles.size(); i++) {
			repr.append(" ").append(tiles.get(i)).append(" ");
			if (i % SIZE == SIZE-1 && i<tiles.size()-1) repr.append("\n");
		}
		return repr.toString();
	}

	@Override
	public int hashCode() {
		return tiles.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		GameState other = (GameState) obj;
		if (tiles == null) {
			if (other.tiles != null) return false;
		} else if (!tiles.equals(other.tiles)) return false;
		return true;
	}
	
	public static enum Action {
		NORTH(-SIZE), EAST(1), SOUTH(SIZE), WEST(-1);
		int offset;
		int cost = 1;
		private Action(int offset) {
			this.offset = offset;
		}
	}
	
	public Collection<Action> applicableActions() {
		int indexEmpty = tiles.indexOf(EMPTY);
		List<Action> actions = new ArrayList<>();
		int row = indexEmpty / SIZE;
		if (row > 0) actions.add(Action.NORTH);
		if (row < SIZE-1) actions.add(Action.SOUTH);
		int column = indexEmpty % SIZE;
		if (column > 0) actions.add(Action.WEST);
		if (column < SIZE-1) actions.add(Action.EAST);
		return actions;
	}

	public GameState applyAction(Action action) {
		List<String> newTiles = new ArrayList<>(tiles);
		int indexEmpty = tiles.indexOf(EMPTY);
		int otherIndex = indexEmpty + action.offset;
		String otherTile = newTiles.get(otherIndex);
		newTiles.set(indexEmpty, otherTile);
		newTiles.set(otherIndex, EMPTY);
		return new GameState(newTiles);
	}
	
	public boolean isGoalState() {
		if(heuristic()==0){
			return true;
		}
		return false;
	}
	
	public int heuristic(){
		int h=0;
		for(int i=1;i<10;i++){
			if(i<=8){
				if(!tiles.get(i-1).equals(i+"")){
					h++;
				}
			}
			else{
				if(!tiles.get(i-1).equals(" ")){
					h++;
				}
			}
		}
		return h;
	}
	
	public int heuristic2() {
		int h=0;
		for(int i=0;i<9;i++) {
			if(tiles.get(i).equals(" ")) continue;
			if(!tiles.get(i).equals(i+"")) 
				h+= Math.abs(Integer.parseInt(tiles.get(i))-(i+1)) ;
		}

		System.out.println(h);
		return h;
	}
}
