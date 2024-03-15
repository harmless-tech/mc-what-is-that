package tech.harmless.mc.whatwasthat.util;

public class MutTuple<X, Y> {
	public X x;
	public Y y;

	public MutTuple(X x, Y y) {
		this.x = x;
		this.y = y;
	}

	public X x() {
		return x;
	}

	public Y y() {
		return y;
	}
}
