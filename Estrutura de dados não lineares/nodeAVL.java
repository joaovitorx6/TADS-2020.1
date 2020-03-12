package AVL;

public class nodeAVL {
	
	int elemento;
	nodeAVL nodePai;
	nodeAVL nodeFE;
	nodeAVL nodeFD;
	int FB = 0;
	
	public int getElemento() {
		return elemento;
	}
	public void setElemento(int elemento) {
		this.elemento = elemento;
	}
	public nodeAVL getNodePai() {
		return nodePai;
	}
	public void setNodePai(nodeAVL nodePai) {
		this.nodePai = nodePai;
	}
	public nodeAVL getNodeFE() {
		return nodeFE;
	}
	public void setNodeFE(nodeAVL nodeFE) {
		this.nodeFE = nodeFE;
	}
	public nodeAVL getNodeFD() {
		return nodeFD;
	}
	public void setNodeFD(nodeAVL nodeFD) {
		this.nodeFD = nodeFD;
	}
	public int getFB() {
		return FB;
	}
	public void setFB(int fB) {
		FB = FB + fB;
	}
	
}
