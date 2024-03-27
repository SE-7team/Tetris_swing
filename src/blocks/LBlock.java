package blocks;

import java.awt.Color;

public class LBlock extends Block {
	
	public LBlock(int color_blind, int pattern) {
		shape = new int[][] {
				{1, 1, 1},
				{1, 0, 0}
		};
		if(color_blind==1) color = new Color(230,159,0);
		else color = Color.ORANGE;
		if(pattern==1) text="G"; //text="L";
		else text="O";

		rotate_status=1;
	}

	//LBlock 회전 시 변경되어야 할 x, y 위치
	@Override
	public int rotate_x(){
		int rotate_x=0;
		switch (rotate_status){
			case 1:
				rotate_x=1;
				break;
			case 2:
				rotate_x=-1;
				break;
			case 3:
				rotate_x=0;
				break;
			case 4:
				rotate_x=0;
				break;
		}
		return rotate_x;
	}

	@Override
	public int rotate_y(){
		int rotate_y=0;
		switch (rotate_status){
			case 1:
				rotate_y=0;
				break;
			case 2:
				rotate_y=1;
				break;
			case 3:
				rotate_y=-1;
				break;
			case 4:
				rotate_y=0;
				break;
		}
		return rotate_y;
	}

}
