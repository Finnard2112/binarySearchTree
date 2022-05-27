package tests;

import static org.junit.Assert.*;

import java.util.Comparator;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import implementation.BinarySearchTree;
import implementation.GetDataIntoArrays;
import implementation.KeyValuePair;
import implementation.TreeIsFullException;

/* The following directive executes tests in sorted order */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class StudentTests {
	
	/* Remove the following test and add your tests */
	@Test
	public void addTest() {
		Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
		int maxEntries = 10;
		BinarySearchTree<String, Integer> bst = new BinarySearchTree<String, Integer>(comparator, maxEntries);
		System.out.println(bst);
		System.out.println("Empty Tree?: " + bst.isEmpty());
		try {
			bst.add("Frank", 1);
			System.out.println(bst);
			System.out.println("Tree Size: " + bst.size());
			bst.add("Oliver", 1000).add("Arlene", 50000).add("Terry", 60);
			System.out.println(bst);
			System.out.println("Tree Size: " + bst.size());
			bst.add("Terry", 69).add("Arlene", 50001).add("Oliver", 500).add("Finn", 2003);
			System.out.println("After updating: \n" + bst);
			System.out.println("Tree Size: " + bst.size());
			System.out.println("Empty Tree?: " + bst.isEmpty());
		} catch (TreeIsFullException e) {
			System.out.println("full tree");
		}
		
		bst = new BinarySearchTree<String, Integer>(comparator, maxEntries);
		try {
			bst.add("a", 1).add("b", 1).add("c", 1).add("d", 1).add("e", 1)
			.add("f", 1).add("g", 1).add("h", 1).add("i", 1).add("j", 1);
			bst.add("a", 2).add("b", 2).add("c", 2).add("d", 2).add("e", 2)
			.add("f", 2).add("g", 2).add("h", 2).add("i", 2).add("j", 2);
			System.out.println(bst);
			System.out.println(bst.size());
		} catch (TreeIsFullException e) {
			System.out.println("full tree");
		}
		bst = new BinarySearchTree<String, Integer>(comparator, maxEntries);
		try {
			bst.add(null, null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void getMinimumTest() {
		Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
		int maxEntries = 10;
		BinarySearchTree<String, Integer> bst = new BinarySearchTree<String, Integer>(comparator, maxEntries);
		try {
			bst.add("d", 1).add("c", 1).add("b", 1).add("a", 1);
			System.out.println(bst);
			KeyValuePair<String, Integer> minimum = bst.getMinimumKeyValue();
			System.out.println(minimum.getKey());
			System.out.println(minimum.getValue());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	@Test
	public void getMaximumTest() {
		Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
		int maxEntries = 10;
		BinarySearchTree<String, Integer> bst = new BinarySearchTree<String, Integer>(comparator, maxEntries);
		try {
			bst.add("Frank", 1).add("Oliver", 1000).add("Arlene", 50000).add("Terry", 60).add("Finn", 2003);
			System.out.println(bst);
			KeyValuePair<String, Integer> maximum = bst.getMaximumKeyValue();
			System.out.println(maximum.getKey());
			System.out.println(maximum.getValue());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void findTest() {
		Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
		int maxEntries = 10;
		BinarySearchTree<String, Integer> bst = new BinarySearchTree<String, Integer>(comparator, maxEntries);
		try {
			bst.add("Frank", 1).add("Oliver", 1000).add("Arlene", 50000).add("Terry", 60).add("Finn", 2003);
			System.out.println(bst);
			KeyValuePair<String, Integer> maximum = bst.find("Frank");
			System.out.println(maximum.getKey());
			System.out.println(maximum.getValue());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void callBackTest() {
		Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
		int maxEntries = 10;
		BinarySearchTree<String, Integer> bst = new BinarySearchTree<String, Integer>(comparator, maxEntries);
		GetDataIntoArrays<String, Integer> callback = new GetDataIntoArrays<String, Integer>();
		try {
			bst.add("A", 1000);
			bst.processInorder(callback);
			System.out.println("Keys: " + callback.getKeys());
			System.out.println("Values: " + callback.getValues());
			bst.add("B", 1000).add("c", 1000).add("d", 1000).add("e", 1000)
			.add("f", 1000).add("g", 1000).add("h", 1000);
			bst.processInorder(callback);
			System.out.println("Keys: " + callback.getKeys());
			System.out.println("Values: " + callback.getValues());
		} catch (TreeIsFullException e) {
			System.out.println("full tree");
		}	
	}
	
	@Test
	public void subTreeTest() {
		Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
		int maxEntries = 10;
		BinarySearchTree<String, Integer> bst = new BinarySearchTree<String, Integer>(comparator, maxEntries);
		try {
			bst.add("e", 1000);
			System.out.println(bst);
			BinarySearchTree<String, Integer> subtree = bst.subTree("b", "z");
			System.out.println(subtree);
			System.out.println(subtree.size());
		} catch (TreeIsFullException e) {
			System.out.println("full tree");
		}	
	}
	
	@Test
	public void deleteTest() {
		Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;
		int maxEntries = 10;
		BinarySearchTree<String, Integer> bst = new BinarySearchTree<String, Integer>(comparator, maxEntries);
		try {
			bst.add("a", 1000).add("b", 0).add("c", 0).add("d", 0).add("e", 0)
			.add("f", 0).add("g", 0).add("h", 0).add("i", 0);
			System.out.println(bst);
			bst.delete("c");
			System.out.println(bst);
			System.out.println(bst.size());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}	
	}
}
