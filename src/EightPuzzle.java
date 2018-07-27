import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

public class EightPuzzle {

	public static void main(String[] args) {
		EightPuzzle puzzle = new EightPuzzle();
		puzzle.solve();
	}

	/* Some example game states:
	 * 
	 * The practical (solution length = 3):	 1 2 3 4 8 5 7 _ 6
	 * 
	 * The shortest non-trivial paths (solution length = 1):
	 * 		1 2 3 4 5 _ 7 8 6
	 * 		1 2 3 4 5 6 7 _ 8
	 * 
	 * One with solution length = 5: 1 _ 3 5 2 6 4 7 8
	 * 
	 * One with solution length = 10: 5 1 _ 2 6 3 4 7 8
	 * 
	 * One with solution length = 16: 1 2 3 4 7 8 5 6 _ 
	 * 
	 * Two with solution length = 28: 
	 * 		8 5 6 7 2 3 4 1 _
	 * 		8 5 4 7 6 3 2 1 _
	 * 
	 * An even longer path (solution length = 30):
	 * 		8 7 6 5 4 3 2 1 _
	 * 
	 * One that is not solvable: 3 2 1 4 5 6 7 8 _
	 * 
	 */

	private static String[] readTiles() {
		int nTiles = GameState.SIZE * GameState.SIZE;
		System.out.println("Enter initial state (use 1..." + (nTiles-1) + " for tiles and _ for the empty tile, separated by spaces):");
		String[] tiles = new String[nTiles];
		Scanner scanner = new Scanner(System.in);
		try {
			for(int i=0; i<tiles.length; i++) {
				String tile = scanner.next();
				if ("_".equals(tile)) tile = GameState.EMPTY;
				tiles[i] = tile;
			}
		} finally {
			scanner.close();
		}
		return tiles;
	}

	private GameState getInitialState() {
		return new GameState(readTiles());
	}

	private void solve() {
		GameState initialState = getInitialState();

		System.out.println("Searching...");

		AStarSearch seeker = new AStarSearch(initialState);
		//	;Node result = seeker.search();

		Callable<Node> run = new Callable<Node>()
		{
			@Override
			public Node call() throws Exception
			{
				// your code to be timed
				return seeker.find();
			}
		};

		RunnableFuture future = new FutureTask(run);
		ExecutorService service = Executors.newSingleThreadExecutor();
		service.execute(future);
		Node result = null;
		try
		{
			try {
				result = (Node) future.get(1, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
		}
		catch (TimeoutException ex)
		{
			// timed out. Try to stop the code if possible.
			future.cancel(true);
		}
		service.shutdown();

		if (result == null) System.out.println("NO SOLUTION FOUND");
		else System.out.println(result);

		System.out.println("Explored " + seeker.getExploredCount() + " states.");
	}

}
