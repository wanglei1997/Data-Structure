package tree;

import java.util.LinkedList;
import java.util.Stack;

public class RBTree<T extends Comparable<T>> {
	private RBTNode mRoot;
	public final static boolean RED = false;
	public final static boolean BLACK = true;

	public class RBTNode<T extends Comparable<T>> {
		boolean color;
		T key;
		RBTNode<T> left;
		RBTNode<T> right;
		RBTNode<T> parent;

		public RBTNode(boolean color, T key, RBTNode<T> left, RBTNode<T> right, RBTNode<T> parent) {
			this.color = color;
			this.key = key;
			this.left = left;
			this.right = right;
			this.parent = parent;
		}
	}

	private void leftRotate(RBTNode<T> xNode) {
		RBTNode<T> yNode = xNode.right;
		xNode.right = yNode.left;
		if (yNode.left != null)
			yNode.left.parent = xNode;
		yNode.parent = xNode.parent;
		if (yNode.parent == null)
			this.mRoot = yNode;
		else {
			if (xNode.parent.left == xNode) {
				xNode.parent.left = yNode;
			} else {
				xNode.parent.right = yNode;
			}
		}
		xNode.parent = yNode;
		yNode.left = xNode;
	}

	private void rightRotate(RBTNode<T> yNode) {
		RBTNode<T> xNode = yNode.left;
		yNode.left = xNode.right;
		if (xNode.right != null)
			xNode.right.parent = yNode;
		xNode.parent = yNode.parent;
		if (yNode.parent == null)
			this.mRoot = xNode;
		else {
			if (yNode.parent.left == yNode) {
				yNode.parent.left = xNode;
			} else {
				yNode.parent.right = xNode;
			}
		}
		xNode.right = yNode;
		yNode.parent = xNode;
	}

	private void insertFixup(RBTNode<T> zNode) {
		RBTNode<T> parent, gParent;
		RBTNode<T> uncle;
		while (((parent = zNode.parent) != null) && parent.color == RED) {
			gParent = parent.parent;
			if (parent == gParent.left) {
				uncle = gParent.right;
				if (uncle != null && uncle.color == RED) {
					parent.color = BLACK;
					uncle.color = BLACK;
					gParent.color = RED;
					zNode = gParent;
				} else {
					if (zNode == parent.right) {
						zNode = parent;
						leftRotate(zNode);
					}
					parent = zNode.parent;
					gParent = parent.parent;
					parent.color = BLACK;
					gParent.color = RED;
					rightRotate(gParent);
				}
			} else {
				uncle = gParent.left;
				if (uncle != null && uncle.color == RED) {
					parent.color = BLACK;
					uncle.color = BLACK;
					gParent.color = RED;
					zNode = gParent;
				} else {
					if (zNode == parent.left) {
						zNode = parent;
						rightRotate(zNode);
					}
					parent = zNode.parent;
					gParent = parent.parent;
					parent.color = BLACK;
					gParent.color = RED;
					leftRotate(gParent);
				}
			}
		}
		this.mRoot.color = BLACK;
	}

	private void insert(RBTNode<T> node) {
		RBTNode<T> xNode = this.mRoot;
		RBTNode<T> yNode = null;
		int cmp;
		while (xNode != null) {
			yNode = xNode;
			cmp = node.key.compareTo(xNode.key);
			if (cmp < 0)
				xNode = xNode.left;
			else
				xNode = xNode.right;
		}
		if (yNode != null) {
			cmp = node.key.compareTo(yNode.key);
			if (cmp < 0) {
				yNode.left = node;
				node.parent = yNode;
			} else {
				yNode.right = node;
				node.parent = yNode;
			}
		} else {
			this.mRoot = node;
		}
		node.color = RED;
		insertFixup(node);
	}

	private RBTNode<T> MINIMUM(RBTNode<T> node) {
		if (node == null)
			return node;
		while (node.left != null) {
			node = node.left;
		}
		return node;
	}

	private void deleteFixup(RBTNode<T> xnode, RBTNode<T> parent) {
		while (xnode != this.mRoot && (xnode == null || xnode.color == BLACK)) {
			if (xnode == parent.left) {
				RBTNode<T> brother = parent.right;
				if (brother.color == RED) {
					brother.color = BLACK;
					parent.color = RED;
					leftRotate(parent);
					brother = parent.right;
				}

				if ((brother.left == null || brother.left.color == BLACK)
						&& (brother.right == null || brother.right.color == BLACK)) {
					brother.color = RED;
					xnode = parent;
					parent = parent.parent;
				} else {
					if (brother.right == null || brother.right.color == BLACK) {
						brother.left.color = BLACK;
						brother.color = RED;
						rightRotate(brother);
						brother = parent.right;
					}

					brother.color = parent.color;
					parent.color = BLACK;
					brother.right.color = BLACK;
					leftRotate(parent);
					xnode = this.mRoot;
				}
			} else {
				if (xnode == parent.right ){
					RBTNode<T> brother = parent.left;
					if (brother.color == RED) {
						brother.color = BLACK;
						parent.color = RED;
						rightRotate(parent);
						brother = parent.left;
					}

					if ((brother.right == null || brother.right.color == BLACK)
							&& (brother.left == null || brother.left.color == BLACK)) {
						brother.color = RED;
						xnode = parent;
						parent = parent.parent;
					} else {
						if (brother.left == null || brother.left.color == BLACK) {
							brother.right.color = BLACK;
							brother.color = RED;
							leftRotate(brother);
							brother = parent.left;
						}

						brother.color = parent.color;
						parent.color = BLACK;
						brother.left.color = BLACK;
						rightRotate(parent);
						xnode = this.mRoot;
					}
				}
			}
		}
	}

	public void add(T value) {
		RBTNode<T> newNode = new RBTNode<T>(RED, value, null, null, null);
		insert(newNode);
	}

	public void showLevel() {
		LinkedList<RBTNode<T>> list = new LinkedList<>();
		RBTNode<T> node = this.mRoot;
		list.add(node);
		while (!list.isEmpty()) {
			node = list.pollFirst();
			if (node.left != null)
				list.add(node.left);
			if (node.right != null)
				list.add(node.right);
			System.out.printf("%2d(%s) is %2d's child\n", node.key, node.color == RED ? "R" : "B",
					node.parent == null ? 0 : node.parent.key);
		}
	}
}
