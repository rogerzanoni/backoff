package br.com.deadtroll.tests;

public class QuadTree<T> {

	private T value;
	private QuadTree<T> nwNode;
	private QuadTree<T> neNode;
	private QuadTree<T> seNode;
	private QuadTree<T> swNode;
	
	public QuadTree() {
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public QuadTree<T> getNwNode() {
		return nwNode;
	}

	public void setNwNode(QuadTree<T> nwNode) {
		this.nwNode = nwNode;
	}

	public QuadTree<T> getNeNode() {
		return neNode;
	}

	public void setNeNode(QuadTree<T> neNode) {
		this.neNode = neNode;
	}

	public QuadTree<T> getSeNode() {
		return seNode;
	}

	public void setSeNode(QuadTree<T> seNode) {
		this.seNode = seNode;
	}

	public QuadTree<T> getSwNode() {
		return swNode;
	}

	public void setSwNode(QuadTree<T> swNode) {
		this.swNode = swNode;
	}

}