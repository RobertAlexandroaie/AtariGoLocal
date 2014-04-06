package atarigo;

import Pair;

public interface Go {
	
	int validate(Pair<Integer,Integer> playerMove);
	int nextMove();
	void draw();
	
}
