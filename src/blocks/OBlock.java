package blocks;

import java.awt.Color;

public class OBlock extends Block {

	public OBlock(int color_blind, int pattern) {
		shape = new int[][] { 
			{1, 1}, 
			{1, 1}
		};
		if(color_blind==1) color = new Color(240,228,66);
		else color = Color.YELLOW;
		if(pattern==1) text="O";
		else text="O";

		rotate_status=1;
	}
}
