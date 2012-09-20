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

import combinations.Binary;

public class TravelingSalesman {
	public static interface Cost {
		public double compute(double C, int S, int n, int i, int j,
				int[][] paths);
	}

	public static double hamiltonianPath(int n, int nToVisit, int[][] links,
			Cost cost) {
		ShortestPathAllPair.floydwarshallWithoutPath(n, links);
		// check if any path is < 0
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				if (links[i][j] < 0) {
					return -1;
				}
			}
		}
		// held karp algorithm
		n = nToVisit;
		int pow2n = 1 << n;
		double[][] C = new double[pow2n][n];
		C[1][0] = 0;
		for (int s = 1; s < n; ++s) {
			int S_iteration = Binary.firstCombination(s, n - 1);
			while (S_iteration != -1) {
				int S = (S_iteration << 1) | 1;
				C[S][0] = Integer.MAX_VALUE / 2;
				for (int j = 1; j < n; ++j) {
					// path last node at j (!=0)
					if ((S & (1 << j)) == 0)
						continue;
					int S_j = S & ((pow2n - 1) ^ (1 << j));
					C[S][j] = cost.compute(C[S_j][0], S_j, n, 0, j, links);
					for (int i = 1; i < n; ++i) {
						// path second-to-last node at i (!=j)
						if (i == j || (S & (1 << i)) == 0)
							continue;
						C[S][j] = Math.min(C[S][j],
								cost.compute(C[S_j][i], S_j, n, i, j, links));
					}
				}
				// next combination
				S_iteration = Binary.nextCombination(S_iteration, s, n - 1);
			}
		}
		// lowest cost
		double min = C[pow2n - 1][0];
		for (int i = 1; i < n; ++i) {
			if (C[pow2n - 1][i] < min) {
				min = C[pow2n - 1][i];
			}
		}
		return min;
	}
}
