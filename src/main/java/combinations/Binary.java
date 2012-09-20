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
package combinations;

import numbertheory.BitOperation;

public class Binary {
	/**
	 * Returns the first combination for selecting 't' elements out of 'n'. <br/>
	 * The elements are supposed to be arranged in some order and the
	 * first_combination method returns the 't' lowest elements.
	 * 
	 * @param t
	 *            number of elements to be selected
	 * @param n
	 *            total number of elements
	 * @return first combination
	 */
	public static int firstCombination(int t, int n) {
		return (1 << t) - 1;
	}

	/**
	 * Returns the next combination, after combination 'c', for selecting 't'
	 * elements out of 'n'. <br/>
	 * The elements are supposed to be arranged in some order and the
	 * next_combination method returns the next lowest combination after 'c'.
	 * 
	 * @param c
	 *            previous combination
	 * @param t
	 *            number of elements to be selected
	 * @param n
	 *            total number of elements
	 * @return next combination or -1 if all combinations are over
	 */
	public static int nextCombination(int c, int t, int n) {
		int finalC = ((1 << t) - 1) << (n - t);
		if (c == finalC) {
			return -1;
		}
		int search = 1;
		int searchMask = 3;
		int searchFound = 0;
		while (true) {
			if ((c & searchMask) == search) {
				int mask = (1 << searchFound) - 1;
				int remainder = c & mask;
				int bits = BitOperation.count1s(remainder);
				c = ((c & (~mask)) ^ (searchMask)) | ((1 << bits) - 1);
				return c;
			}
			search = (search << 1);
			searchMask = (searchMask << 1);
			searchFound++;
		}
	}
}
