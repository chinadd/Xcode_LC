class Solution {
    class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        String word;//pointer to word

        public TrieNode() {
            this.children = new HashMap<Character, TrieNode>();
        }
    }

    class Trie {
        private TrieNode root;

        public Trie() {
            this.root = new TrieNode();
        }

        public void insert(String word) {
            TrieNode cur = this.root;
            for (char c : word.toCharArray()) {
                cur.children.computeIfAbsent(c, k -> new TrieNode());
                cur = cur.children.get(c);
            }
            cur.word = word; //end of word
        }

        public TrieNode searchNextNode(TrieNode node, char c) {
			if (node == null) {
				node = root;
			}
			return node.children.get(c);
		}

    }

    public List<String> findWords(char[][] board, String[] words) {
        List<String> result = new ArrayList<>();
        //build trie
        Trie trie = new Trie();
        for (String word: words) {
            trie.insert(word);
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                dfsFindWords(board, result, trie, null, i, j);
            }
        }
        return result;
    }

    public void dfsFindWords(char[][] board, List<String> result, Trie trie, TrieNode node, int x, int y) {
        char c = board[x][y];
        TrieNode nextNode = trie.searchNextNode(node, c);
        if (nextNode == null) return;
        if (nextNode.word != null) {
            result.add(nextNode.word);
            nextNode.word = null;
        }
        board[x][y] = '#'; //back tracking
        for (Point neighbor : getWordSearchNeighbors(board, x, y)) {
            dfsFindWords(board, result, trie, nextNode, neighbor.x, neighbor.y);
        }
        board[x][y] = 'c';
    }

    private List<Point> getWordSearchNeighbors(char[][] board, int x, int y) {
        List<Point> neighbors = new ArrayList<>();
        for (Point candidate : new Point[] { new Point(x - 1, y), new Point(x + 1, y), new Point(x, y - 1),
			    new Point(x, y + 1)
		}) {
			if (0 <= candidate.x && candidate.x < board.length && 0 <= candidate.y && candidate.y < board[0].length
			        && board[candidate.x][candidate.y] != '#') {
				neighbors.add(candidate);
			}
		}
		return neighbors;
    }
}
