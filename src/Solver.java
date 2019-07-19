import java.util.Objects;

public class Solver {

	public static boolean solveRecursive(GameGrid game) {
		Objects.requireNonNull(game);
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 9; column++) {

				if (game.getField(column, row) == 0) {

					for (int number = 1; number <= 9; number++) {

						if (game.setField(column, row, number)) {
							if (solveRecursive(game)) return true;
							else game.clearField(column, row);
						}
					}
					//If none of the numbers worked
					return false;
				}
			}
		}
		return true;
	}
}


