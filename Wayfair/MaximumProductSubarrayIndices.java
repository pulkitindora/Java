package Wayfair;

class MaxProduct {
    public int[] findMaxSubarray(int[] nums) {
        int[] result = new int[3];
        long minP, maxP, gP, maxStart, maxEnd, minStart, gStart, gEnd;
        minP = maxP = gP = nums[0];
        maxStart = maxEnd = minStart = gStart = gEnd = 0;
        for(int i=1; i<nums.length; i++) {
            long temp = maxP;
            long tempStart = maxStart;
            if(maxP*nums[i]<=nums[i]) {
                maxStart = i;
                maxEnd = i;
                maxP = nums[i];
            } else {
                maxEnd = i;
                maxP = maxP*nums[i];
            }

            if(maxP<minP*nums[i]) {
                maxStart = minStart;
                maxP = minP*nums[i];
            }

            if(minP*nums[i]>=nums[i]) {
                minStart = i;
                minP = nums[i];
            } else {
                minP = minP*nums[i];
            }

            if(minP>temp*nums[i]) {
                minStart = tempStart;
                minP = temp*nums[i];
            }

            if(maxP>gP) {
                gStart = maxStart;
                gEnd = maxEnd;
                gP = maxP;
            }
        }

        result[0] = (int)gStart;
        result[1] = (int)gEnd;
        result[2] = (int)gP;
        return result;
    }
}

class MaximumProductSubarrayIndices {
    public static void main(String[] args) {
        // int[] input = new int[] {2,3,-2,4};
        // int[] input = new int[] {-2,0,-1};
        // int[] input = new int[] {0,10,10,10,10,10,10,10,10,-10,10,10,10,10,10,10,10,10,10,0};
        int[] input = new int[] {2,3,-2,4,-4};
        MaxProduct mp = new MaxProduct();
        int[] result = mp.findMaxSubarray(input);
        System.out.println("Start: "+result[0]+", End: "+result[1]+", Product: "+result[2]);
    }
}