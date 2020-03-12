package AVL;

import java.util.ArrayList;
import java.util.Iterator;

import arvoreBinaria.InvalidPositionException;
import AVL.nodeAVL;

public class AVL {
	
//	variaveis
	ArrayList <nodeAVL> nodesTree;
	nodeAVL nodeRaiz = new nodeAVL();
	nodeAVL nodeR, novoNode, nodeEncontrado;
	int tamanho=0, elementoT, cont, elementoRemovido, elementoNovo;
	Iterator <nodeAVL> iteratorNodes;
	boolean direcao;
	
//	construtor
	public AVL(int elemento){
		nodeRaiz.setElemento(elemento);
		tamanho=1;
	}
	
//	metodos.
	
//	retorna o node raiz.
	public nodeAVL root() {
		return nodeRaiz;
	}

// retorna o pai do node.
	public nodeAVL parent(nodeAVL node) {	
		
		if (!isRoot(node)){
			nodeAVL nodeT = (nodeAVL) node;
			return (nodeT.getNodePai());
		}
		
		return null;

	}
	
//	public Iterator children(nodeArvoreB v)
//	{
//		nodesArvore = new ArrayList<>();
//		nodesArvore.add(v.getNodeFilhoD());
//		nodesArvore.add(v.getNodeFilhoE());
//		nodesArvoreI = nodesArvore.iterator();
//		return nodesArvoreI;
//	}
//	
	
// retorna um boolean se o node tem filhos.
	public boolean isInternal(nodeAVL node) {
		nodeAVL nodeT = (nodeAVL) node;
		if(hasRight(nodeT) || hasLeft(nodeT)){ //return (n.childrenNumber() > 0);
			return true; 
		} else {
			return false;
		}
	}
	
// retorna um boolean se o node não tem filhos.	
	public boolean isExternal(nodeAVL node) {
		nodeAVL nodeT = (nodeAVL) node;
		if(!hasLeft(nodeT) && !hasRight(nodeT)) //return (n.childrenNumber() == 0);
			return true; 
		else 
			return false;
	}
	
//verifica se o node é raiz.
	public boolean isRoot(nodeAVL node){
		nodeAVL nodeT = (nodeAVL) node;
		//return n == raiz;
		if (nodeT==nodeRaiz)
			return true;
		else 
			return false;
	}

//adiciona filho na árvore.
	public void addChild(nodeAVL node, int elemento) throws InvalidPositionException {
		
		nodeAVL nodePai = (nodeAVL) node;
		elementoT = nodePai.getElemento();
		
//		nao tem filhos, e adiciona de acordo com a restricao da arvore binaria (menor e maior).
		if(isExternal(nodePai)){
			
			novoNode = new nodeAVL();
			novoNode.setElemento(elemento);
			novoNode.setNodePai(nodePai);
			
			if (elemento>elementoT){
				
				nodePai.setNodeFD(novoNode);
				nodePai.setFB(-1);
				tamanho++;
				
			} else {

				nodePai.setNodeFE(novoNode);
				nodePai.setFB(+1);
				tamanho++;
			
			}
			
//			Seto o FB do node do pai que teve filho adicionado, e verifico se ele é filho
//			da esquerda ou da direita do pai, dai chamo a função seguindo a regra (FB==0).
			
//			############################################################ 
			
			if(nodePai!=root() && nodePai.getNodePai().getNodeFD()==nodePai) {
				atualizaFBInsert(nodePai, true);
			} else if(nodePai!=root() && nodePai.getNodePai().getNodeFE()==nodePai) {
				atualizaFBInsert(nodePai, false);
			}
			
//			############################################################ 
			
//	 tem filhos, e adiciona de acordo com a restricao da arvore binaria (menor e maior) recursivamente, achando o lugar correto.			
		} else if (isInternal(nodePai)){
				
			
				if (elemento<elementoT){
					if (hasLeft(nodePai)){
						addChild(nodePai.getNodeFE(), elemento);
					}
					else {
						novoNode = new nodeAVL();
						novoNode.setElemento(elemento);
						novoNode.setNodePai(nodePai);
						nodePai.setNodeFE(novoNode);
						nodePai.setFB(+1);
						direcao = false;
					}
							
				} else if (elemento>elementoT){
					
					if (hasRight(nodePai)){
						addChild(nodePai.getNodeFD(), elemento);
					} else {
						novoNode = new nodeAVL();
						novoNode.setElemento(elemento);
						novoNode.setNodePai(nodePai);
						nodePai.setNodeFD(novoNode);
						nodePai.setFB(-1);
						direcao = true;
					}
				} 
				
		}
		
			
		
	}
	
//	Método para atualizar FB quando inserir um node.
	public void atualizaFBInsert(nodeAVL nodePai, boolean direcao) {
		
		while (nodePai!=root() && nodePai.getFB()!=0) {
			if (direcao==true) {
				nodePai = nodePai.getNodePai();
				nodePai.setFB(-1);
				if (nodePai.getNodePai()!=null) {
					if (nodePai.getNodePai().getNodeFE()==nodePai) {
						direcao = false;
					} else {
						direcao = true;
					}
				}
			} else {
				nodePai = nodePai.getNodePai();
				nodePai.setFB(+1);
				if (nodePai.getNodePai()!=null) {
					if (nodePai.getNodePai().getNodeFE()==nodePai) {
						direcao = false;
					} else {
						direcao = true;
					}
				}
			}
			
//			########################################
			if (nodePai.getFB()==-2 && nodePai.getNodeFD().getFB()<=0) {
				rotacaoEsquerdaSimples(nodePai);
				break;
			}else if (nodePai.getFB()==2 && nodePai.getNodeFE().getFB()>=0) {
				rotacaoDireitaSimples(nodePai);
				break;
			}else if (nodePai.getFB()==-2 && nodePai.getNodeFD().getFB()>=0) {
				System.out.println("Rotação esquerda dupla");
			}else if (nodePai.getFB()==2 && nodePai.getNodeFE().getFB()<=0) {
				System.out.println("Rotação direita dupla");
			}
//			########################################		
		}
	}
	
//	Método para atualizar FB quando remover um node.
	public void atualizaFBRemove(nodeAVL nodePai, boolean direcao) {
	
		
		while (nodePai!=root() && nodePai.getFB()==0) {
			if (direcao==true) {
				nodePai = nodePai.getNodePai();
				nodePai.setFB(+1);
				if (nodePai.getNodePai()!=null) {
					if (nodePai.getNodePai().getNodeFE()==nodePai) {
						direcao = false;
					} else {
						direcao = true;
					}
				}
			} else {
				nodePai = nodePai.getNodePai();
				nodePai.setFB(-1);
				if (nodePai.getNodePai()!=null) {
					if (nodePai.getNodePai().getNodeFE()==nodePai) {
						direcao = false;
					} else {
						direcao = true;
					}
				}
			}
			
//			########################################
			
			if (nodePai.getFB()==-2 && nodePai.getNodeFD().getFB()<=0) {
				rotacaoEsquerdaSimples(nodePai);
				break;
			}else if (nodePai.getFB()==2 && nodePai.getNodeFE().getFB()>=0) {
				rotacaoDireitaSimples(nodePai);
				break;
			}else if (nodePai.getFB()==-2 && nodePai.getNodeFD().getFB()>=0) {
				System.out.println("Rotação esquerda dupla");
			}else if (nodePai.getFB()==2 && nodePai.getNodeFE().getFB()<=0) {
				System.out.println("Rotação direita dupla");
			}
			
//			########################################
			
		}
		
	}
	
	public void rotacaoEsquerdaSimples(nodeAVL node) {
		
		nodeAVL nodeFD = node.getNodeFD();
		
		if (root()==node) {
			nodeFD.setNodePai(null);
			nodeRaiz = nodeFD;
		}else {
			nodeFD.setNodePai(node.getNodePai());
			node.getNodePai().setNodeFD(nodeFD);
		}
		
		if(hasLeft(nodeFD)) {
			nodeFD.getNodeFE().setNodePai(node);
			node.setNodeFD(nodeFD.getNodeFE());
		} else {
			node.setNodeFD(null);
		}
		
		nodeFD.setNodeFE(node);
		node.setNodePai(nodeFD);
//		
	}
	
	public void rotacaoDireitaSimples (nodeAVL node) {
	
		nodeAVL nodeFE = node.getNodeFE();
		
		if (node==root()) {
			nodeFE.setNodePai(null);
			nodeRaiz = nodeFE;
		}else {
			nodeFE.setNodePai(node.getNodePai());
			node.getNodePai().setNodeFE(nodeFE);
		}
		
		if(hasRight(nodeFE)) {
			nodeFE.getNodeFD().setNodePai(node);
			node.setNodeFE(nodeFE.getNodeFD());
		} else {
			node.setNodeFE(null);
		}
		
		nodeFE.setNodeFD(node);
		node.setNodePai(nodeFE);
		
	}

	
// retorna o filho esquerdo do node.	
	public nodeAVL leftChild(nodeAVL node) throws InvalidPositionException{
		if (!hasLeft(node)){
			throw new InvalidPositionException("Filho da esquerda não existe");
		} 
		return node.getNodeFE();
	}
	
// retorna o filho direito do node.		
	public nodeAVL rightChild(nodeAVL node) throws InvalidPositionException {
		if (!hasRight(node)){
			throw new InvalidPositionException("Filho da direita no existe");
		}
		return node.getNodeFD();
	}
	
// verifica se o node tem filho esquerdo.			
	public boolean hasLeft(nodeAVL node){
		if (node.getNodeFE()!=null){
			return true;
		} else {
			return false;
		}
	}
	
// verifica se o node tem filho direito.		
	public boolean hasRight(nodeAVL node){
		if (node.getNodeFD()!=null){
			return true;
		} else {
			return false;
		}
	}

// visita primeiro os filhos da esquerda, depois o própio node e após isso os filhos da direita.	
	public ArrayList <nodeAVL> inOrder(nodeAVL node, boolean siga){
		
		nodeAVL nodeT = (nodeAVL) node;
		
		if (siga==true){
			nodesTree = new ArrayList<>(); 
		}
		
		if(isInternal(nodeT)){
			if(hasLeft(nodeT)){
				nodesTree = inOrder(nodeT.getNodeFE(), false);
			}
		}
		
		nodesTree.add(nodeT);
		
		if(isInternal(nodeT)){
			if (hasRight(nodeT)){
				nodesTree = inOrder(nodeT.getNodeFD(), false);
			}
		}
		
		return nodesTree;

	}
	
// visita primeiro os filhos da direita, depois os filhos da esquerda, e por fim o própio node.	
	public ArrayList <nodeAVL> posOrder(nodeAVL node, boolean siga){
		
		if (siga==true){
			nodesTree = new ArrayList<>();
		}
		
		if (isInternal(node)){
			if (node.getNodeFE()!=null){
				posOrder(node.getNodeFE(), false);
			} 
			if (node.getNodeFD()!=null){
				posOrder(node.getNodeFD(), false);
			}
		}
		
		nodesTree.add(node);
		
		return nodesTree;
	}
	
// visita primeiro o node, depois os filhos da esquerda, e por fim os filhos da direita.	
	public ArrayList<nodeAVL>preOrder(nodeAVL node, boolean siga){
		
		if (siga==true){
			nodesTree = new ArrayList<>();
		}
		
		nodesTree.add(node);
		
		if (isInternal(node)){
			if (node.getNodeFE()!=null){
				nodesTree = preOrder(node.getNodeFE(), false);
			} 
			if (node.getNodeFD()!=null){
				nodesTree = preOrder(node.getNodeFD(), false);
			}
		}
		
		return nodesTree;
	}
	
//	metodo pra encontrar node especifico na arvore. 
	public nodeAVL findNode(int elemento) {
		nodesTree = inOrder(nodeRaiz, true);
		int size = nodesTree.size();
		for (int i=0; i<size; i++) {
			if (nodesTree.get(i).getElemento()==elemento) {
				return nodesTree.get(i);
			}
		}
		return null;
	}
	
//	metodo para remover um node.
	public int remove(int elemento) { 
		
//		node que vai ser removido.
		nodeAVL nodeRemovido = findNode(elemento);
		elementoRemovido = nodeRemovido.getElemento();
		
//		se não tem filho e é raiz.
		if(isRoot(nodeRemovido) && isExternal(nodeRemovido)){
			
			nodeRaiz=null;
			tamanho--;
			
//		se ele tiver pai e nao tiver filho.
		} else if(parent(nodeRemovido)!=null && isExternal(nodeRemovido)){
			
			nodeAVL nodePai;
			nodePai = nodeRemovido.getNodePai();
			
//		verifica se é o filho da esquerda ou se é o filho da direita..
			if (nodePai.getNodeFE()==nodeRemovido){
				
				nodePai.setNodeFE(null);
				nodePai.setFB(-1);
		
				atualizaFBRemove(nodePai, false);
				
			} else if (nodePai.getNodeFD()==nodeRemovido){
				nodePai.setNodeFD(null);
				nodePai.setFB(+1);

				atualizaFBRemove(nodePai, true);
			}
			
			nodeRemovido=null;
			tamanho--;
			
//	se tem apenas 1 filho ou os dois filhos.
			
		} else if (isInternal(nodeRemovido)){
			
			if (hasRight(nodeRemovido) && !hasLeft(nodeRemovido)){
				
				elementoNovo = nodeRemovido.getNodeFD().getElemento();
				nodeRemovido.setElemento(elementoNovo);
				nodeRemovido.setNodeFD(null);
				
//				############################################################ 
				
//				Seto o FB do node que vai ficar no lugar do removido, e verifico se ele é filho
//				da esquerda ou da direita do pai, dai seto o FB do pai e chamo a função seguindo a regra (FB!=0).
				
				nodeRemovido.setFB(+1);
				
				if(nodeRemovido.getNodePai().getNodeFD()==nodeRemovido) {
					nodeRemovido.getNodePai().setFB(+1);
					atualizaFBRemove(nodeRemovido.getNodePai(), true);
				} else {
					nodeRemovido.getNodePai().setFB(-1);
					atualizaFBRemove(nodeRemovido.getNodePai(), false);
				}
				
//				############################################################ 
				
//				falta destruir o nodeFD.
			} else if (hasLeft(nodeRemovido) && !hasRight(nodeRemovido)){
				
				elementoNovo = nodeRemovido.getNodeFE().getElemento();
				nodeRemovido.setElemento(elementoNovo);
				nodeRemovido.setNodeFE(null);
				
//				############################################################  
				
//				Seto o FB do node que vai ficar no lugar do removido, e verifico se ele é filho
//				da esquerda ou da direita do pai, dai seto o FB do pai e chamo a função seguindo a regra (FB!=0).
				
				nodeRemovido.setFB(-1);
				
				if(nodeRemovido.getNodePai().getNodeFD()==nodeRemovido) {
					nodeRemovido.getNodePai().setFB(+1);
					atualizaFBRemove(nodeRemovido.getNodePai(), true);
				} else {
					nodeRemovido.getNodePai().setFB(-1);
					atualizaFBRemove(nodeRemovido.getNodePai(), false);
				}
				
//				############################################################ 				
			
				
			} else if (hasLeft(nodeRemovido) && hasRight(nodeRemovido)){
				
				nodeEncontrado = findNodeForRemove(nodeRemovido.getNodeFD());
				
// 				verificar se o que está sendo removido é o esquerdo ou direito, pra setar a referencia para o pai.
				System.out.println(nodeEncontrado.getElemento());
				
//				mudando as referências caso o node escolhido esteja do lado direito do node escolhido.
				if (nodeEncontrado.getNodePai().getNodeFD()==nodeEncontrado) {
					
					nodeEncontrado.setNodeFE(nodeRemovido.getNodeFE());
					nodeRemovido.getNodeFE().setNodePai(nodeEncontrado);
					
					nodeEncontrado.setNodePai(nodeRemovido.getNodePai());
					nodeRemovido.getNodePai().setNodeFE(nodeEncontrado);
					
//					Seto o FB do node que vai ficar no lugar do removido, e verifico se ele é filho
//					da esquerda ou da direita do pai, dai seto o FB do pai e chamo a função seguindo a regra (FB!=0).
					
					nodeEncontrado.setFB(+1);
					
					if(nodeRemovido.getNodePai().getNodeFD()==nodeRemovido) {
						nodeRemovido.getNodePai().setFB(+1);
						atualizaFBRemove(nodeRemovido.getNodePai(), true);
					} else {
						nodeRemovido.getNodePai().setFB(-1);
						atualizaFBRemove(nodeRemovido.getNodePai(), false);
					}
					
//					############################################################ 
					
					
//					mudando as referências caso o node escolhido seja o filho da esquerda.
				} else if (nodeEncontrado.getNodePai().getNodeFE()==nodeEncontrado) {
					
					nodeEncontrado.setNodeFE(nodeRemovido.getNodeFE());
					nodeRemovido.getNodeFE().setNodePai(nodeEncontrado);
					
					nodeEncontrado.setNodePai(nodeRemovido.getNodePai());
					nodeRemovido.getNodePai().setNodeFD(nodeEncontrado);
					
					nodeRemovido.getNodeFD().setNodePai(nodeEncontrado);
					nodeEncontrado.setNodeFD(nodeRemovido.getNodeFD());
					nodeRemovido.getNodeFD().setNodeFE(null);
					
//					Seto o FB do node que vai ficar no lugar do removido, e verifico se ele é filho
//					da esquerda ou da direita do pai, dai seto o FB do pai e chamo a função seguindo a regra (FB!=0).
					
					nodeRemovido.setFB(-1);
					
					if(nodeRemovido.getNodePai().getNodeFD()==nodeRemovido) {
						nodeRemovido.getNodePai().setFB(+1);
						atualizaFBRemove(nodeRemovido.getNodePai(), true);
					} else {
						nodeRemovido.getNodePai().setFB(-1);
						atualizaFBRemove(nodeRemovido.getNodePai(), false);
					}
					
//					############################################################ 
					
				}

				nodeRemovido.setElemento(nodeEncontrado.getElemento());
				nodeEncontrado = null;
				tamanho--;
			}
			
		}
		return elementoRemovido;
	}
	
	public nodeAVL findNodeForRemove(nodeAVL nodeT){
		if (isExternal(nodeT)){
			nodeR = nodeT;
		} else if (isInternal(nodeT)) {
			if (hasLeft(nodeT)){
				System.out.println("sim");
				nodeR = findNodeForRemove(nodeT.getNodeFE());
			} else {
				nodeR = nodeT;
			}
		}
		return nodeR;
	}
	
	
	public int depth(nodeAVL nodeT)
	{
		nodeAVL nodeW = (nodeAVL) nodeT;
		if (isRoot(nodeW))
			return 0;
		else 
			return 1+depth(nodeW.getNodePai());
	}

	
	public int height(nodeAVL n) {
		if(n == null || isExternal(n)) {
			return 0;
		}
		else {
			return 1 + Math.max(height(n.getNodeFE()), height(n.getNodeFD()));
		}
	}

	
	public int size()
	{
		return tamanho;
	}
	
	public boolean isEmpty()
	{
		return false;
	}
	
	public String viewTree(nodeAVL nodeRaiz){
		
		nodesTree = inOrder(nodeRaiz, true);
		String arvore = " "; 
		
		String [][] tree = new String [height(nodeRaiz)+1][nodesTree.size()];
		for (int k=0; k<nodesTree.size(); k++) {
			nodeR = nodesTree.get(k);
			tree[depth(nodeR)][k] = Integer.toString(nodeR.getElemento());
		}
		
		for (int i=0; i<height(nodeRaiz)+1; i++){
			for (int k=0; k<nodesTree.size(); k++){
				if (tree[i][k]==null){
					arvore+="    ";
				} else {
					arvore+=tree[i][k];
					arvore+="    ";
				}
			}
			arvore+="\n";
		}

		return arvore;

	}
}
