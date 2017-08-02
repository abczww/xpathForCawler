package com.cnki.ksp.core;

public enum ArticleType {
	PILICY(5), DYNAMIC(1), PROBLEM(2), REFIT(3), COMPETITOR(4);

	private final int type;

	private ArticleType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
}
