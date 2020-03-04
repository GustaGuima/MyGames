
public class Satisfied implements Runnable {

	Integer lenght;
	Integer height;
	Integer width;
	Integer free;
	Integer busy;
	Integer[][] field;
	Integer[] position;

	public Satisfied() {
		super();
		this.lenght = 7;
		this.height = this.width = this.lenght;
		this.free = this.height * this.width;
		this.busy = 0;
		this.field = new Integer[this.height][this.width];
		this.position = new Integer[2];
		this.position[0] = this.position[1] = 0;
		this.init();
	}

	public static void main(String[] args) {
		Satisfied satisfied = new Satisfied();
		satisfied.draw();
		do {
			satisfied.move();
			satisfied.draw();
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (satisfied.free >= 0);
	}

	private void draw() {
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				if (this.position[0] == i && this.position[1] == j) {
					System.out.print("[" + this.field[i][j] + "] ");					
				} else {
					System.out.print(" " + this.field[i][j] + "  ");										
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	private void init() {
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				this.field[i][j] = 0;
			}
		}
		this.field[2][5] = 2;
		this.field[3][4] = 2;
		this.field[4][4] = 2;
	}

	private void move() {
		int x = this.position[0];
		int y = this.position[1];
		if (x < this.width - 1 && this.field[x + 1][y] == 0) {
			position[0] = x + 1;
			position[1] = y;
		} else if (y < this.height - 1 && this.field[x][y + 1] == 0) {
			position[0] = x;
			position[1] = y + 1;
		} else if (x > 0 && this.field[x - 1][y] == 0) {
			position[0] = x - 1;
			position[1] = y;
		} else if (y > 0 && this.field[x][y - 1] == 0) {
			position[0] = x;
			position[1] = y - 1;
		} else {
			 if (y > 0 && this.field[x][y - 1] == 1) {
				position[0] = x;
				position[1] = y - 1;
			} else if (x < this.width - 1 && this.field[x + 1][y] == 1) {
				position[0] = x + 1;
				position[1] = y;
			} else if (y < this.height - 1 && this.field[x][y + 1] == 1) {
				position[0] = x;
				position[1] = y + 1;
			} else if (x > 0 && this.field[x - 1][y] == 1) {
				position[0] = x - 1;
				position[1] = y;
			}
		}
		this.field[x][y] = 1;
		++this.busy;
		--this.free;
	}

	@Override
	public void run() {
	}

}
