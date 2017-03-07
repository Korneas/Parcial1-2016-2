package parcial1;

import java.io.Serializable;

public class Movement implements Serializable {
	public int emisor;
	public int receptor;
	public String data;

	public Movement(int emisor,int receptor,String data) {
		this.emisor=emisor;
		this.receptor=receptor;
		this.data = data;
	}
	
	public int getEmisor() {
		return emisor;
	}

	public void setEmisor(int emisor) {
		this.emisor = emisor;
	}

	public int getReceptor() {
		return receptor;
	}

	public void setReceptor(int receptor) {
		this.receptor = receptor;
	}

	public String getContenido() {
		return data;
	}

	public void setContenido(String data) {
		this.data = data;
	}
}

