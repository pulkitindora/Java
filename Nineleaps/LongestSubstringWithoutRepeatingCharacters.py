# QUESTION:
# Given a string s, find the first longest substring without repeating characters.
# Input: "abcdeacbdeabb"
# Output: "abcde"
# 
# NOTE: Don't use BigInteger or such large number containers
#

def lengthOfLongestSubstring(s: str) -> int:
    adj = {}
    left = 0
    result = 0
    resultStr = ""
    for right in range(len(s)):
        ch = s[right]
        if ch in adj and left<=adj[ch]:
            left = adj[ch]+1
        
        adj[ch] = right
        if right-left+1>result:
            result = right-left+1
            resultStr = s[left:right+1]

    return resultStr

resultStr = lengthOfLongestSubstring("abcdeacbdeabb")
print("Result:", resultStr)