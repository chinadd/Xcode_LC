public class Solution {
	class TrieNode {
		String word;
		TrieNode[] children;

		public TrieNode() {
			children = new TrieNode[26];
		}
	}

	class Trie {
		private TrieNode root;

		public Trie() {
			root = new TrieNode();
		}

		// Inserts a word into the trie.
		public void insert(String word) {
			TrieNode cur = root;
			for (int i = 0; i < word.length(); i++) {
				int index = word.charAt(i) - 'a';
				TrieNode child = cur.children[index];
				if (child == null) {
					child = new TrieNode();
					cur.children[index] = child;
				}

				cur = child;
			}
			cur.word = word;
		}

		public TrieNode searchNextNode(TrieNode node, char c) {
			if (node == null) {
				node = root;
			}
			int index = c - 'a';
			return node.children[index];
		}
	}

	public List<String> findWords(char[][] board, String[] words) {
		List<String> result = new ArrayList<>();

		// build Trie
		Trie trie = new Trie();
		for (String word : words) {
			trie.insert(word);
		}

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				dfsFindWords(board, result, trie, null, i, j);
			}
		}
		return result;
	}

	private void dfsFindWords(char[][] board, List<String> result, Trie trie, TrieNode node, int x, int y) {
		char c = board[x][y];
		TrieNode nextNode = trie.searchNextNode(node, c);
		if (nextNode == null) {
			return;
		}
		if (nextNode.word != null) {
			result.add(nextNode.word);
			nextNode.word = null; // de-duplicate
		}
		board[x][y] = '#';
		for (Point neighbor : getWordSearchNeighbors(board, x, y)) {
			dfsFindWords(board, result, trie, nextNode, neighbor.x, neighbor.y);
		}
		board[x][y] = c;
	}

	private List<Point> getWordSearchNeighbors(char[][] board, int x, int y) {
		List<Point> neighbors = new ArrayList<>(4);
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
