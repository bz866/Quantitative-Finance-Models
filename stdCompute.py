
# Implementation of vectorize accelerated computing standard deviation
def compute_std(theValues):
	"""
	Vectorize computation 
	std = sqrt(Var[X])) = sqrt( E[X^2] - E[X]^2 )
	"""
	size = len(theValues)
	avgX = sum(theValues) / size # E[X]
	avgXX = sum([i**2 for i in theValues]) / size # E[X^2]

	return (avgXX- avgX^2)**0.5

