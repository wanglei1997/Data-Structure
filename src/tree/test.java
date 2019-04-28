package tree;

public class test {
	public static void main(String args[]) {
		RBTree<Integer> tree=new RBTree<Integer>();
		int array[]={10, 40, 30, 60, 90, 70, 20, 50, 80,100,150,140,120,160,130,180};
		for(int i=0;i<array.length;i++) {
			tree.add(array[i]);
		}
		tree.showLevel();
		System.out.println();
		
		tree.delete(100);
		tree.showLevel();
	}
}
