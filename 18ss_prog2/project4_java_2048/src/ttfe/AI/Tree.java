package ttfe.AI;

import java.util.LinkedList;
import java.util.List;

public class Tree<T> {
	private T data;
	private List<Tree<T>> subtrees; 

	
	public Tree(T data) {
		this.data = data;
		this.subtrees = new LinkedList<Tree<T>>();
	}
	
	public T getData() {
		return this.data;
	}

	public List<Tree<T>> getSubtrees() {
		return this.subtrees;
	}
	
	public void addLeaf(T data) {
		Tree<T> leaf = new Tree<T>(data);
		this.subtrees.add(leaf);
	}
	
	public boolean isLeaf(Tree<T> tree){
		return tree.subtrees == null ? true : false;
	}
}
