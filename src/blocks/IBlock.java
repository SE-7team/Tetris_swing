package blocks;

import java.awt.Color;

public class IBlock extends Block {
	
	public IBlock(int color_blind, int pattern) {
		//블럭 생성자 호출시 색맹모드와 무늬모드를 위한
		//color_blind와 pattern를 받는다
		shape = new int[][] {
				{1, 1, 1, 1}
		};
		if(color_blind==1) color = new Color(6,158,115);
		else color = Color.CYAN;
		if(pattern==1) text="O"; //text="I";
		else text="O";

		rotate_status=1;
	}
	
	
	//IBlock 회전 시 변경되어야 할 x, y 위치
	@Override
	public int rotate_x(){
		int rotate_x=0;
		switch (rotate_status){
			case 1:
				rotate_x=2;
				break;
			case 2:
				rotate_x=-2;
				break;
			case 3:
				rotate_x=+1;
				break;
			case 4:
				rotate_x=-1;
				break;
		}
		return rotate_x;
	}

	@Override
	public int rotate_y(){
		int rotate_y=0;
		switch (rotate_status){
			case 1:
				rotate_y=-1;
				break;
			case 2:
				rotate_y=2;
				break;
			case 3:
				rotate_y=-2;
				break;
			case 4:
				rotate_y=1;
				break;
		}
		return rotate_y;
	}
}
