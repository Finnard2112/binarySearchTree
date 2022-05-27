package implementation;

import java.util.Comparator;
import java.util.TreeSet;

public class BinarySearchTree<K, V> {

	private class Node {
		private K key;
		private V value;
		private Node left, right;

		private Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}

	private Node root;
	private int treeSize, maxEntries;
	private Comparator<K> comparator;

	public BinarySearchTree(Comparator<K> comparator, int maxEntries) {
		if (comparator == null || maxEntries < 1) {
			throw new IllegalArgumentException();
		}
		this.comparator = comparator;
		treeSize = 0;
		this.maxEntries = maxEntries;
	}

	public BinarySearchTree<K, V> add(K key, V value) throws TreeIsFullException {
		if (key == null || value == null) {
			throw new IllegalArgumentException();
		}
		if (isFull()) {
			throw new TreeIsFullException("Full");
		}
		if (root == null) {
			root = new Node(key, value);
			treeSize++;
		} else {
			addAux(key, value, root);
		}
		return this;

	}

	private void addAux(K key, V value, Node rootAux) throws TreeIsFullException {
		int comparison = comparator.compare(key, rootAux.key);
		if (comparison == 0) {
			rootAux.value = value;
		} else if (comparison < 0) {
			if (rootAux.left == null) {
				rootAux.left = new Node(key, value);
				treeSize++;
			} else {
				addAux(key, value, rootAux.left);
			}
		} else {
			if (rootAux.right == null) {
				rootAux.right = new Node(key, value);
				treeSize++;
			} else {
				addAux(key, value, rootAux.right);
			}
		}
	}

	public String toString() {
		if (isEmpty()) {
			return "EMPTY TREE";
		}
		return toStringAux(root);
	}

	private String toStringAux(Node rootAux) {
		return rootAux == null ? ""
				: toStringAux(rootAux.left) + "{" + rootAux.key + ":" + rootAux.value + "}"
						+ toStringAux(rootAux.right);
	}

	public boolean isEmpty() {
		return root == null;
	}

	public int size() {
		return treeSize;
	}

	public boolean isFull() {
		return treeSize == maxEntries;
	}

	public KeyValuePair<K, V> getMinimumKeyValue() throws TreeIsEmptyException {
		if (isEmpty()) {
			throw new TreeIsEmptyException("Empty");
		}
		return minimumAux(root);
	}

	private KeyValuePair<K, V> minimumAux(Node rootAux) {
		if (rootAux.left == null) {
			return new KeyValuePair<K, V>(rootAux.key, rootAux.value);
		} else {
			return minimumAux(rootAux.left);
		}
	}

	public KeyValuePair<K, V> getMaximumKeyValue() throws TreeIsEmptyException {
		if (isEmpty()) {
			throw new TreeIsEmptyException("Empty");
		}
		Node result = maximumAux(root);
		return new KeyValuePair<K, V>(result.key, result.value);
	}

	private Node maximumAux(Node rootAux) {
		if (rootAux.right == null) {
			return new Node(rootAux.key, rootAux.value);
		} else {
			return maximumAux(rootAux.right);
		}
	}

	public KeyValuePair<K, V> find(K key) {
		return findAux(key, root);
	}

	private KeyValuePair<K, V> findAux(K key, Node rootAux) {
		if (rootAux == null) {
			return null;
		} else {
			int comparison = comparator.compare(key, rootAux.key);
			if (comparison == 0) {
				return new KeyValuePair<K, V>(rootAux.key, rootAux.value);
			} else if (comparison < 0) {
				return findAux(key, rootAux.left);
			} else {
				return findAux(key, rootAux.right);
			}
		}

	}

	public BinarySearchTree<K, V> delete(K key) throws TreeIsEmptyException {
		if (isEmpty()) {
			throw new TreeIsEmptyException("Empty");
		}
		if (key == null) {
			throw new IllegalArgumentException();
		}
		deleteAux(key, root, null, false);
		return this;
	}

	private void deleteAux(K key, Node rootAux, Node previous, boolean right) {
		if (rootAux == null) {

		} else {
			int comparison = comparator.compare(key, rootAux.key);
			if (comparison > 0) {
				deleteAux(key, rootAux.right, rootAux, true);
			} else if (comparison < 0) {
				deleteAux(key, rootAux.left, rootAux, false);
			} else {
				if (rootAux.left == null && rootAux.right == null) {
					if (previous == null) {
						root = null;
					} else {
						if (right) {
							previous.right = null;
						} else {
							previous.left = null;
						}
					}
					treeSize--;
				} else if (rootAux.left != null && rootAux.right != null) {
					BinarySearchTree<K, V> subTree = new BinarySearchTree<K, V>(comparator, maxEntries);
					subTree.root = rootAux.left;
					Node maxLeft = subTree.maximumAux(subTree.root);
					deleteAux(maxLeft.key, rootAux.left, rootAux, false);
					if (comparator.compare(root.key, rootAux.key) == 0) {
						root = maxLeft;
					} else {
						if (right) {
							previous.right = maxLeft;
						} else {
							previous.left = maxLeft;
						}
					}
					maxLeft.left = rootAux.left;
					maxLeft.right = rootAux.right;
				} else {
					if (previous == null) {
						if (rootAux.right != null) {
							root = rootAux.right;
						} else {
							root = rootAux.left;
						}
					} else {
						if (rootAux.left != null) {
							if (right) {
								previous.right = rootAux.left;
							} else {
								previous.left = rootAux.left;
							}
						} else {
							if (right) {
								previous.right = rootAux.right;
							} else {
								previous.left = rootAux.right;
							}
						}
					}
					treeSize--;
				}
			}
		}
	}

	public void processInorder(Callback<K, V> callback) {
		if (callback == null) {
			throw new IllegalArgumentException();
		}
		processAux(callback, root);
	}

	private void processAux(Callback<K, V> callback, Node rootAux) {
		if (rootAux == null) {
		} else {
			processAux(callback, rootAux.left);
			callback.process(rootAux.key, rootAux.value);
			processAux(callback, rootAux.right);
		}

	}

	public BinarySearchTree<K, V> subTree(K lowerLimit, K upperLimit) {
		if (lowerLimit == null || upperLimit == null || comparator.compare(lowerLimit, upperLimit) > 0) {
			throw new IllegalArgumentException();
		}
		BinarySearchTree<K, V> subTree = new BinarySearchTree<K, V>(comparator, maxEntries);
		subTreeAux(lowerLimit, upperLimit, root, subTree, true);
		return subTree;
	}

	private Node subTreeAux(K lowerLimit, K upperLimit, Node rootAux, BinarySearchTree<K, V> subTree,
			boolean notInOrder) {
		if (rootAux == null) {
			return null;
		} else {
			int lowerCompare = comparator.compare(lowerLimit, rootAux.key);
			int upperCompare = comparator.compare(upperLimit, rootAux.key);
			if (notInOrder && lowerCompare > 0) {
				subTreeAux(lowerLimit, upperLimit, rootAux.right, subTree, true);
			} else if (notInOrder && upperCompare < 0) {
				subTreeAux(lowerLimit, upperLimit, rootAux.left, subTree, true);
			} else {
				if (comparator.compare(rootAux.key, lowerLimit) < 0) {
					return subTreeAux(lowerLimit, upperLimit, rootAux.right, subTree, false);
				} else if (comparator.compare(rootAux.key, upperLimit) > 0) {
					return subTreeAux(lowerLimit, upperLimit, rootAux.left, subTree, false);
				}
				subTree.treeSize++;
				Node subNode = new Node(rootAux.key, rootAux.value);
				if (subTree.treeSize == 1) {
					subTree.root = subNode;
				}
				subNode.left = subTreeAux(lowerLimit, upperLimit, rootAux.left, subTree, false);
				subNode.right = subTreeAux(lowerLimit, upperLimit, rootAux.right, subTree, false);
				return subNode;
			}
		}
		return null;
	}

	public TreeSet<V> getLeavesValues() {
		TreeSet<V> treeSet = new TreeSet<V>();
		getLeavesAux(treeSet, root);
		return treeSet;
	}

	private void getLeavesAux(TreeSet<V> treeSet, Node rootAux) {
		if (rootAux == null) {
		} else {
			getLeavesAux(treeSet, rootAux.left);
			getLeavesAux(treeSet, rootAux.right);
			if (rootAux.left == null && rootAux.right == null) {
				treeSet.add(rootAux.value);
			}
		}
	}
}
