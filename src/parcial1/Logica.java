package parcial1;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;

public class Logica implements Observer {

	private PApplet app;
	private Comunicacion c;
	private final String GROUP_ADDRESS = "226.24.6.7";

	private float r, g, b;
	private int id;
	private int vuelta;
	private boolean darVuelta;

	private int x, y;
	private boolean start;

	public Logica(PApplet app) {
		this.app = app;

		c = new Comunicacion();
		Thread nt = new Thread(c);
		nt.start();

		c.addObserver(this);

		if (c.getId() != 0) {
			id = c.getId();
			r = app.random(180);
			g = app.random(180);
			b = app.random(180);
		}

		if (id == 1) {
			x = app.width / 2;
			y = app.height / 2;
		} else if (id >= 2) {
			x = -50;
			y = -50;
		}

		if (id == 4) {
			try {
				c.enviar(new Movement(id, 1, "start"), GROUP_ADDRESS);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Me color es: " + r + " : " + g + " : " + b);
	}

	public void pintar() {
		app.fill(r, g, b);
		app.noStroke();
		app.rect(0, 0, 100, 25);
		app.rect(app.width - 100, app.height - 25, 100, 25);

		app.fill(255);
		app.text("Nodo " + id, 5, 15);
		app.text("Vueltas: " + vuelta, app.width - 80, app.height - 10);

		app.fill(r, g, b);
		app.ellipse(x, y, 40, 40);

		if (start && id == 1) {
			if (x < app.width + 30 && y == app.height / 2) {
				x++;
			}

			if (y > app.height / 2 && x == app.width / 2) {
				if (!darVuelta) {
					try {
						c.enviar(new Movement(id, 1, "vuelta"), GROUP_ADDRESS);
					} catch (IOException e) {
						e.printStackTrace();
					}
					darVuelta=true;
				}
				y--;
			}

			if (x >= app.width + 30) {
				try {
					c.enviar(new Movement(id, 2, "start"), GROUP_ADDRESS);
				} catch (IOException e) {
					e.printStackTrace();
				}
				start = false;
			}
		}

		if (start && id == 2) {
			if (x < app.width / 2 && y == app.height / 2) {
				x++;
			}

			if (y < app.height + 30 && x == app.width / 2) {
				y++;
			}

			if (y >= app.height + 30) {
				try {
					c.enviar(new Movement(id, 3, "start"), GROUP_ADDRESS);
				} catch (IOException e) {
					e.printStackTrace();
				}
				start = false;
			}
		}

		if (start && id == 3) {

			if (y < app.height / 2 && x == app.width / 2) {
				y++;
			}

			if (x > -30 && y == app.height / 2) {
				x--;
			}

			if (x <= -30) {
				try {
					c.enviar(new Movement(id, 4, "start"), GROUP_ADDRESS);
				} catch (IOException e) {
					e.printStackTrace();
				}
				start = false;
			}
		}

		if (start && id == 4) {
			
			if (x > app.width/2 && y == app.height / 2) {
				x--;
			}

			if (y > -30 && x == app.width / 2) {
				y--;
			}

			if (y <= -30) {
				try {
					c.enviar(new Movement(id, 1, "start"), GROUP_ADDRESS);
				} catch (IOException e) {
					e.printStackTrace();
				}
				start = false;
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Movement) {
			Movement mov = (Movement) arg;

			if (mov.getContenido().contains("vuelta")) {
				vuelta++;
			}

			if (mov.getEmisor() == 4) {
				if (mov.getReceptor() == id) {
					if (mov.getContenido().contains("start")) {
						if (x != app.width / 2 && y != app.height / 2) {
							x = app.width / 2;
							y = app.height + 30;
						}
						start = true;
					}
				}
			}

			if (mov.getEmisor() == 1) {
				if (mov.getReceptor() == id) {
					if (mov.getContenido().contains("start")) {
						start = true;
						x = -30;
						y = app.height / 2;
					}
				}
			}

			if (mov.getEmisor() == 2) {
				if (mov.getReceptor() == id) {
					if (mov.getContenido().contains("start")) {
						start = true;
						x = app.width / 2;
						y = -30;
					}
				}
			}

			if (mov.getEmisor() == 3) {
				if (mov.getReceptor() == id) {
					if (mov.getContenido().contains("start")) {
						start = true;
						x = app.width + 30;
						y = app.height / 2;
					}
				}
			}
		}
	}
}
