package Wayfair;

import java.util.*;

class Solution {
    class Node {
        int par;
        int level;
        public Node(int par, int level) {
            this.par = par;
            this.level = level;
        }

        public int getPar() {
            return this.par;
        }

        public int getLevel() {
            return this.level;
        }
    }

    Map<Integer, List<Node>> map;
    Map<Integer, List<Node>> map2;
    
    public void helper(int node, int level, List<Node> nodes) {
        if(map.get(node).size()<1) return;
        for(Node par: map.get(node)) {
            helper(par.getPar(), level+1, nodes);
            nodes.add(new Node(par.getPar(), level));
        } return;
    }

    public Solution(List<List<Integer>> input, int range) {
        map = new HashMap<Integer, List<Node>>();
        map2 = new HashMap<Integer, List<Node>>();
        for(int i=1; i<=range; i++) {
            map.put(i, new ArrayList<Node>());
        }

        for(List<Integer> cur: input) {
            int par = cur.get(0);
            int child = cur.get(1);
            map.get(child).add(new Node(par, 0));
        }

        for(int i=1; i<=range; i++) {
            if(map.get(i).size()<1) continue;
            List<Node> temp = new ArrayList<>();
            helper(i, 0, temp);
            temp.sort(new Comparator<>(){
                @Override
                public int compare(Node n1, Node n2) {
                    return n2.getLevel()-n1.getLevel();
                }
            });
            map2.put(i, temp);
        }
    }

    public int findAncestor(int node) {
        List<Node> temp = map2.get(node);
        if(temp==null || temp.size()<1 || temp.get(0)==null) return -1;
        return temp.get(0).getPar();
    }
}

class EarliestAncestor {
    public static void main(String[] args) {
        List<List<Integer>> input = Arrays.asList(
            Arrays.asList(1, 3),
            Arrays.asList(2, 3), 
            Arrays.asList(3, 6), 
            Arrays.asList(5, 6), 
            Arrays.asList(5, 7), 
            Arrays.asList(4, 5), 
            Arrays.asList(4, 8), 
            Arrays.asList(8, 9), 
            Arrays.asList(10,2)
        );

        int range = 10; // total nodes in graph
        Solution sc = new Solution(input, range);
        System.out.println("Node: 8, Ancestor: "+sc.findAncestor(8)); // -> 4
        System.out.println("Node: 7, Ancestor: "+sc.findAncestor(7)); // -> 4
        System.out.println("Node: 6, Ancestor: "+sc.findAncestor(6)); // -> 10
        System.out.println("Node: 3, Ancestor: "+sc.findAncestor(3)); // -> 10
        System.out.println("Node: 9, Ancestor: "+sc.findAncestor(9)); // -> 4
        System.out.println("Node: 5, Ancestor: "+sc.findAncestor(5)); // -> 4
        System.out.println("Node: 2, Ancestor: "+sc.findAncestor(2)); // -> 10
        System.out.println("Node: 1, Ancestor: "+sc.findAncestor(1)); // -> -1
        System.out.println("Node: 4, Ancestor: "+sc.findAncestor(4)); // -> -1
        System.out.println("Node: 10, Ancestor: "+sc.findAncestor(10)); // -> -1
    }
}