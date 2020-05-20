# Implementation of calculations

# - VaR
# - CVaR


import numpy as np
from scipy.stats import binom

# Calculate the VaR given a discrete distribution
def calculate_VaR(percentile, n, q):
    """
    Calculate the value at risk(VaR) given confidence level and discrete distribution
    @percentile::float: confidence level, 0 <= percentile <= 1
    @n::int; maximum number of trials
    @q::int; the probability of getting heads
    
    Return::int: the VaR 
    """
    cumulateProb = 0
    VaRNeg = 0 - n * q # centered binomial distribution, left shift n * q
    mu = n * q
    
    while VaRNeg <= n - n * q:
        x = VaRNeg + mu # right shift to standard binomial distribution
        cumulateProb += binom.pmf(x, n, q) # probability of x heads given B(n, q)
        if cumulateProb > 1 - percentile:
            VaR = -VaRNeg 
            return VaR
        VaRNeg += 1
    
    raise ValueError('VaR not found.')
    
# Calculate the cVaR given a discrete distribution
def calculate_cVaR(percentile, n, q):
    """
    Calculate the conditional value at risk(cVaR) given confidence level and discrete distribution
    @percentile::float: confidence level, 0 <= percentile <= 1
    @n::int; maximum number of trials
    @q::int; the probability of getting heads
    
    Return::int: the cVaR 
    """
    cVaR = 0
    mul = -1 / (1 - percentile) # the multiplier in the front
    mu = n * q
    currVaR = 0 - n * q # right shift to standard binomial distribution

    while currVaR <= -calculate_VaR(percentile, n, q):
        x = currVaR + mu # shift back
        cVaR += mul * currVaR * binom.pmf(x, n, q)
        currVaR += 1
    
    return cVaR
    
    
    