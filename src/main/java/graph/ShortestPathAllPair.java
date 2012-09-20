/*******************************************************************************
 * Copyright 2012 Saurabh Raje
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package graph;

public class ShortestPathAllPair {
	public static void floydwarshallWithPath(int n, int[][] links, int[][] next) {
		assert (links.length == n);
		assert (next.length == n);
		for (int i = 0; i < n; ++i) {
			assert (links[i].length == n);
			assert (next[i].length == n);
			for (int j = 0; j < n; ++j) {
				if (links[i][j] >= 0) {
					next[i][j] = j;
				}
			}
		}
		for (int k = 0; k < n; ++k) {
			for (int i = 0; i < n; ++i) {
				for (int j = 0; j < n; ++j) {
					// path from i to j using k exists
					if ((links[i][k] >= 0 && links[k][j] >= 0)
					// new path is shorter or is the only path
							&& (links[i][k] + links[k][j] < links[i][j] || links[i][j] < 0)) {
						links[i][j] = links[i][k] + links[k][j];
						next[i][j] = k;
					}
				}
			}
		}
	}

	public static void floydwarshallWithoutPath(int n, int[][] links) {
		assert (links.length == n);
		for (int i = 0; i < n; ++i) {
			assert (links[i].length == n);
		}
		for (int k = 0; k < n; ++k) {
			for (int i = 0; i < n; ++i) {
				for (int j = 0; j < n; ++j) {
					// path from i to j using k exists
					if ((links[i][k] >= 0 && links[k][j] >= 0)
					// new path is shorter or is the only path
							&& (links[i][k] + links[k][j] < links[i][j] || links[i][j] < 0)) {
						links[i][j] = links[i][k] + links[k][j];
					}
				}
			}
		}
	}
}
