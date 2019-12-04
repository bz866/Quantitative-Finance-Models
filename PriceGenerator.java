
// Create price generator that expose generic API

public PriceGenerator(double r, long n, double sigma, double price) {
	this.r = r;
	this.n = n;
	this.sigma = sigma;
	this.price = price;
	random = new Random();
}

public double price() {
	var z = random.nextGaussian() * sigma * Math.sqrt(n);
	return price * (1 + z + (n * r));
}

private double r;
private long n;
private double sigmaOne;
private double sigmaTwo;
private double price;
private Random random;

public MixturePriceGenerator(double r, long n, double sigmaOne, double sigmaTwo, double price) {
	this.r = r;
	this.n = n;
	this.sigmaOne = sigmaOne;
	this.sigmaTwo = sigmaTwo;
	this.price = price;
	random = new Random();
}

public double price() {
	var z = 0.5 * (random.nextGaussian() * sigmaOne + random.nextGaussian() * sigmaTwo) * Math.sqrt(n);
	return price * (1 + z + (n * r));
}

// combine the priceGenerator and the SimpleEuropeanCallOption
public static void main(String[] args) {
	var priceGenerator = new MixturePriceGenerator(0.0001. 252, 0.001. 0.004, 100);
	var payoutCall = new SimpleEuropeanCallOption(priceGenerator::price, 100);
	var averager = new BestAverage();
	averager.wrap(payoutCall::payout, 0.001);
	System.out.println(averager.average());
	var payoutPut = new SimpleEuropeanCallOption(priceGenerator::price, 100);
	averager.wrap(payoutPut::payout, 0.001);
	System.out.println(averager.average());
}