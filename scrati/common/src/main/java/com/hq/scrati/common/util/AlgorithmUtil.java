package com.hq.scrati.common.util;

/**
 * Created by Zale on 2016/12/7.
 */
public interface AlgorithmUtil {
      class Luhn {
        private static int[] convertStrToInArr(String cardNo) {
            if (null == cardNo)
                throw new IllegalArgumentException();
            int index = cardNo.length();
            int[] cardNoArr = new int[cardNo.length()];
            for (char c : cardNo.toCharArray()) {
                cardNoArr[--index] = c;
            }
            return cardNoArr;
        }

        public static int getCheckNo(String nums) {
            return getCheckNo(convertStrToInArr(nums));
        }

        public static int getCheckNo(int[] nums) {
            for (int i = 0; i < nums.length; i += 2) {
                nums[i] <<= 1;
                nums[i] = nums[i] / 10 + nums[i] % 10;
            }
            int sum = 0;
            for (int i = 0; i < nums.length; i++) {
                sum += nums[i];
                //System.out.print(cardNoArr[i]);
            }
            return sum * 9 % 10;
        }

        public static boolean check(String nums) {
            return check(convertStrToInArr(nums));
        }

        private static boolean check(int[] nums) {
            for (int i = 1; i < nums.length; i += 2) {
                nums[i] <<= 1;
                nums[i] = nums[i] / 10 + nums[i] % 10;
            }
            int sum = 0;
            for (int i = 0; i < nums.length; i++) {
                sum += nums[i];
            }

            return sum % 10 == 0;
        }

    }
}
