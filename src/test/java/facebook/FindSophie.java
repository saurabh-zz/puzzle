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
package facebook;

import static org.junit.Assert.*;
import graph.TravelingSalesman;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class FindSophie {
	private final String filename;
	private final String result;

	public FindSophie(String filename, String result) {
		this.filename = filename;
		this.result = result;
	}

	@Test
	public void test() {
		InputStream inputStream = FindSophie.class
				.getResourceAsStream(filename);
		double lowestCost = FindSophie.getLowestCost(inputStream);
		String computedResult = String.format("%.2f", lowestCost);
		assertEquals(result, computedResult);
	}

	@Parameters
	public static Collection<Object[]> data() {
		final String basePath = "/facebook/testcase/FindSophie/";
		Object[][] data = new Object[][] { { basePath + "1.txt", "38.20" } };
		return Arrays.asList(data);
	}

	public static double getLowestCost(InputStream inputStream) {
		Scanner in = new Scanner(inputStream);
		int m = in.nextInt(); // number of locations
		final Map<String, Integer> locationMap = new HashMap<String, Integer>(
				m * 2);
		final String[] locationArr = new String[m];
		final double[] probabilityArr = new double[m];
		int end = m - 1;
		for (int i = 0, front = 0; i < m; ++i) {
			String location = in.next("[0-9A-Za-z_]*");
			double probability = in.nextDouble();
			// move locations with 0 probability (except the first location) to
			// list end
			int index = (i != 0 && probability == 0) ? end-- : front++;
			locationMap.put(location, index);
			locationArr[index] = location;
			probabilityArr[index] = probability;
		}
		int c = in.nextInt(); // number of connections
		final int[][] links = new int[m][m];
		for (int i = 0; i < m; ++i) {
			Arrays.fill(links[i], -1);
			links[i][i] = 0;
		}
		for (int i = 0; i < c; ++i) {
			String location1 = in.next("[0-9A-Za-z_]*");
			String location2 = in.next("[0-9A-Za-z_]*");
			int seconds = in.nextInt();
			int location1Index = locationMap.get(location1);
			int location2Index = locationMap.get(location2);
			links[location1Index][location2Index] = seconds;
			links[location2Index][location1Index] = seconds;
		}
		/*
		 * SOLUTION
		 */
		double lowestCost = TravelingSalesman.hamiltonianPath(m, end + 1,
				links, new TravelingSalesman.Cost() {
					@Override
					public double compute(double C, int S, int n, int i, int j,
							int[][] paths) {
						double p = 1;
						for (int k = 0; k < n; ++k) {
							if ((S & (1 << k)) > 0) {
								p -= probabilityArr[k];
							}
						}
						return C + p * paths[i][j];
					}
				});
		return lowestCost;
	}

	public static void main(String[] args) throws Exception {
		double lowestCost = FindSophie.getLowestCost(System.in);
		System.out.println(String.format("%.2f", lowestCost));
	}
}
