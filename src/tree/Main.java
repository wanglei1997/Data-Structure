package tree;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {
	public class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
		}
	}

	class Solution {
		public double findMedianSortedArrays(int[] nums1, int[] nums2) {
			int array[] = new int[nums1.length + nums2.length];
			int i = 0, j = 0;
			while (i < nums1.length && j < nums2.length) {
				if (nums1[i] < nums2[j]) {
					array[i + j] = nums1[i];
					i++;
				} else {
					array[i + j] = nums2[j];
					j++;
				}
			}
			if (i < nums1.length) {
				while (i + j < nums1.length + nums2.length) {
					array[i + j] = nums1[i];
					i++;
				}
			} else {
				while (i + j < nums1.length + nums2.length) {
					array[i + j] = nums2[j];
					j++;
				}
			}
			if(array.length%2==0) {
				return (array[array.length/2-1]+array[array.length/2])/2.0;
			}
			else
				return array[array.length/2]/1.0;
		}
	}
}