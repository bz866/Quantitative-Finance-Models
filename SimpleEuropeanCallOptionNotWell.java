
// Optimized Note
// 1. The payout classes should accept generic input ang exposes 
// generic out the outside world

private DoubleSupplier priceSupplier;
private double strike;

public SimpleEuropeanCallOption(DoubleSupplier priceSupplier, double strike) {
	this.strike = strike;
	this.priceSupplier = priceSupplier;
}

public double payout() {
	var terminalPrice = priceSupplier.getAsDouble();
	return terminalPrice > strike ? terminalPrice - strike : 0;
}

public static void main(String[] args) {
	var priceGenerator = new PriceGenerator(0.0001, 252, 0.001, 100);
	var payout = new SimpleEuropeanCallOption(priceGenerator::price, 100);
	var averager = new BestAverage();
	averager.wrap(payout::payout, 0.001);
	System.out.println(averager.averager());
}

